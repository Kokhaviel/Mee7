/*
 * Copyright (C) 2021 Kokhaviel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.kokhaviel.bot.commands.funcraft.games;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Mee7;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class InfectedStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("funcraft_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject FUNCRAFT_OBJECT = LANG_OBJECT.get("funcraft").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "infected")) {

			if(args.length < 2) {
				message.delete().queue();

				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("missing_arguments").getAsString(),
						FUNCRAFT_OBJECT.get("no_player_specified").getAsString()))
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage(FUNCRAFT_OBJECT.get("not_valid_username").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			final String url = "https://lordmorgoth.net/APIs/stats?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1] + "&mode=infecte&periode=always";

			try {

				message.delete().queue();
				Infected infected = Mee7.gson.fromJson(JsonUtilities.readJson(new URL(url)), Infected.class);

				if(infected.exit_code.equals("0"))
					channel.sendMessage(getInfectedStats(infected, channel, GENERAL_OBJECT, FUNCRAFT_OBJECT).build()).queue();

			} catch(IOException e) {
				channel.sendMessage(format("%s : %s",
						FUNCRAFT_OBJECT.get("exception").getAsString(),
						FUNCRAFT_OBJECT.get("file_doesnt_exist").getAsString()))
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getInfectedStats(Infected infected, TextChannel channel, JsonObject generalObject, JsonObject funcraftObject) {

		EmbedBuilder infectedEmbed = new EmbedBuilder();

		if(infected.exit_code.equals("0")) {
			infectedEmbed.setAuthor("Funcraft Player Stats", null, Config.FUNCRAFT_ICON);
			infectedEmbed.setColor(Color.RED);
			infectedEmbed.setThumbnail(infected.skin);
			infectedEmbed.setTitle(format("%s Infected Stats", infected.pseudo));
			infectedEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", Config.DEVELOPER_AVATAR);

			infectedEmbed.addField("Rank : ", infected.rang, false);

			infectedEmbed.addBlankField(false);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("points").getAsString()), infected.data.points, true);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("games").getAsString()), infected.data.parties, true);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("victories").getAsString()), infected.data.victoires, true);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("defeats").getAsString()), infected.data.defaites, true);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("played_time").getAsString()), infected.data.temps_jeu + " minutes", true);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("kills").getAsString()), infected.data.kills, true);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("deaths").getAsString()), infected.data.morts, true);

			infectedEmbed.addBlankField(false);
			infectedEmbed.addField(format("%s : ", funcraftObject.get("winrate").getAsString()), infected.stats.winrate + "%", true);
			infectedEmbed.addField("KDR : ", infected.stats.kd, true);
			infectedEmbed.addField(format("%s / %s : ", funcraftObject.get("average_kills").getAsString(), funcraftObject.get("games").getAsString()), infected.stats.kills_game, true);
			infectedEmbed.addField(format("%s / %s : ", funcraftObject.get("average_deaths").getAsString(), funcraftObject.get("games").getAsString()), infected.stats.morts_game, true);
			infectedEmbed.addField(format("%s / %s : ", funcraftObject.get("average_time").getAsString(), funcraftObject.get("games").getAsString()), infected.stats.temps_partie + " s", true);
		}

		if(infected.exit_code.equals("0")) {
			channel.sendMessage(JsonUtilities.getErrorCode(infected.exit_code)).queue();
		}

		return infectedEmbed;
	}

	static class Data {
		String points;
		String parties;
		String victoires;
		String defaites;
		String temps_jeu;
		String kills;
		String morts;
	}

	static class Stats {
		String winrate;
		String kd;
		String kills_game;
		String morts_game;
		String temps_partie;
	}

	static class Infected {
		String exit_code;
		String pseudo;
		String mode_jeu;
		String rang;
		Data data;
		Stats stats;
		String skin;
	}
}
