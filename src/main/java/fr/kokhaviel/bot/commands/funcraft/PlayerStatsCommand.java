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

// Funcraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)

package fr.kokhaviel.bot.commands.funcraft;

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
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class PlayerStatsCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "stats")) {

			if(args.length < 2) {

				message.delete().queue();

				channel.sendMessage(format("%s : %s !",
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

			final String URL = "https://lordmorgoth.net/APIs/infos?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1];

			try {
				message.delete().queue();
				Stats stats = Mee7.gson.fromJson(JsonUtilities.readJson(new URL(URL)), Stats.class);
				channel.sendMessage(getFuncraftStats(stats, GENERAL_OBJECT, FUNCRAFT_OBJECT).build()).queue();
				channel.sendMessage(getPlayerFriends(stats, GENERAL_OBJECT).build()).queue();

			} catch(IOException e) {
				channel.sendMessage(format("%s : %s !",
						FUNCRAFT_OBJECT.get("exception").getAsString(),
						FUNCRAFT_OBJECT.get("file_doesnt_exist").getAsString()))
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getFuncraftStats(Stats stats, JsonObject generalObject, JsonObject funcraftObject) {

		EmbedBuilder funcraftEmbed = new EmbedBuilder();

		funcraftEmbed.setAuthor("Funcraft Player Stats", null, Config.FUNCRAFT_ICON);
		funcraftEmbed.setColor(Color.RED);
		funcraftEmbed.setThumbnail(stats.skin);
		funcraftEmbed.setTitle(format("[%s] %s Stats", stats.grade, stats.pseudo));
		funcraftEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", Config.DEVELOPER_AVATAR);

		funcraftEmbed.addField(format("%s : ", funcraftObject.get("inscription").getAsString()), stats.inscription, true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("last_connection").getAsString()), "Owo ... Not working for the moment", true);
		//FIXME : Last Connection
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("glories").getAsString()), String.valueOf(stats.gloires), true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("games").getAsString()), stats.parties, true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("points").getAsString()), stats.points, true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("victories").getAsString()), stats.victoires, true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("defeats").getAsString()), stats.defaites, true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("played_time").getAsString()), stats.temps_jeu + " minutes", true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("kills").getAsString()), stats.kills, true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("deaths").getAsString()), stats.morts, true);
		funcraftEmbed.addField(format("%s : ", funcraftObject.get("ban").getAsString()), stats.ban, true);

		return funcraftEmbed;
	}

	private EmbedBuilder getPlayerFriends(Stats stats, JsonObject generalObject) {

		EmbedBuilder friendsEmbed = new EmbedBuilder();

		friendsEmbed.setAuthor("Funcraft Player Friends", null, Config.FUNCRAFT_ICON);
		friendsEmbed.setColor(Color.RED);
		friendsEmbed.setThumbnail(stats.skin);
		friendsEmbed.setTitle(format("[%s] %s Friends", stats.grade, stats.pseudo));
		friendsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", Config.DEVELOPER_AVATAR);

		for(Friend friend : stats.amis) {
			friendsEmbed.addField(friend.nom, "Skin : [" + friend.nom + " Head](" + friend.skin + ")", false);
		}

		return friendsEmbed;
	}


	static class Friend {
		String nom;
		String skin;
	}

	static class Stats {
		String grade;
		String pseudo;
		String skin;
		String inscription;
		String gloires;
		String parties;
		String points;
		String victoires;
		String defaites;
		String temps_jeu;
		String kills;
		String morts;
		List<Friend> amis;
		String ban;
	}
}