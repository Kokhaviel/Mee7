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

public class PvpSmashStatsCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "pvpsmash")) {

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
			final String url = "https://lordmorgoth.net/APIs/stats?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1] + "&mode=pvpsmash&periode=always";

			try {

				message.delete().queue();
				PvpSmash pvpsmash = Mee7.gson.fromJson(JsonUtilities.readJson(new URL(url)), PvpSmash.class);

				if(pvpsmash.exit_code.equals("0"))
					channel.sendMessage(getPvpSmashStats(pvpsmash, channel, GENERAL_OBJECT, FUNCRAFT_OBJECT).build()).queue();

			} catch(IOException e) {

				channel.sendMessage(format("%s : %s !",
						FUNCRAFT_OBJECT.get("exception").getAsString(),
						FUNCRAFT_OBJECT.get("file_doesnt_exist")))
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getPvpSmashStats(PvpSmash pvpSmash, TextChannel channel, JsonObject generalObject, JsonObject funcraftObject) {

		EmbedBuilder pvpsmashEmbed = new EmbedBuilder();

		if(pvpSmash.exit_code.equals("0")) {
			pvpsmashEmbed.setAuthor("Funcraft Player Stats", null, Config.FUNCRAFT_ICON);
			pvpsmashEmbed.setColor(Color.RED);
			pvpsmashEmbed.setThumbnail(pvpSmash.skin);
			pvpsmashEmbed.setTitle(format("%s PvpSmash Stats", pvpSmash.pseudo));
			pvpsmashEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", Config.DEVELOPER_AVATAR);

			pvpsmashEmbed.addField("Rank : ", pvpSmash.rang, true);

			pvpsmashEmbed.addBlankField(false);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("points").getAsString()), pvpSmash.data.points, true);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("games").getAsString()), pvpSmash.data.parties, true);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("victories").getAsString()), pvpSmash.data.victoires, true);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("defeats").getAsString()), pvpSmash.data.defaites, true);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("played_time").getAsString()), pvpSmash.data.temps_jeu, true);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("kills").getAsString()), pvpSmash.data.kills, true);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("deaths").getAsString()), pvpSmash.data.morts, true);

			pvpsmashEmbed.addBlankField(false);
			pvpsmashEmbed.addField(format("%s : ", funcraftObject.get("winrate").getAsString()), pvpSmash.stats.winrate + "%", true);
			pvpsmashEmbed.addField("KDR : ", pvpSmash.stats.kd, true);
			pvpsmashEmbed.addField(format("%s / %s : ", funcraftObject.get("average_kills").getAsString(), funcraftObject.get("games").getAsString()), pvpSmash.stats.kills_game, true);
			pvpsmashEmbed.addField(format("%s / %s : ", funcraftObject.get("average_deaths").getAsString(), funcraftObject.get("games").getAsString()), pvpSmash.stats.morts_game, true);
			pvpsmashEmbed.addField(format("%s / %s : ", funcraftObject.get("average_time").getAsString(), funcraftObject.get("games").getAsString()), pvpSmash.stats.temps_partie + " s", true);

		}

		if(!pvpSmash.exit_code.equals("0")) {
			channel.sendMessage(JsonUtilities.getErrorCode(pvpSmash.exit_code)).queue();
		}

		return pvpsmashEmbed;
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

	static class PvpSmash {
		String exit_code;
		String pseudo;
		String mode_jeu;
		String rang;
		Data data;
		Stats stats;
		String skin;
	}

}
