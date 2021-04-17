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

package fr.kokhaviel.bot.commands.hypixel.recentgames;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.player.Player;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.hypixel.recent.Games;
import fr.kokhaviel.api.hypixel.recent.RecentGames;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.hypixelAPI;
import static java.lang.String.format;

public class RecentGamesCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject HYPIXEL_OBJECT = LANG_OBJECT.get("hypixel").getAsJsonObject();


		final Message message = event.getMessage();
		final MessageChannel channel = event.getChannel();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(prefix + "recent")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "player <Player>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage(format("%s !", HYPIXEL_OBJECT.get("invalid_username").getAsString())).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			message.delete().queue();

			RecentGames recentGames = null;
			PlayerData data = null;
			try {
				recentGames = hypixelAPI.getRecentGames(args[1]);
				data = hypixelAPI.getPlayerData(args[1]);
			} catch(MalformedURLException e) {
				e.printStackTrace();
			} catch(IllegalStateException e) {
				channel.sendMessage("This Username Doesn't Exists !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
				);
			}

			assert recentGames != null;
			assert data != null;
			Player player = data.getPlayer();
			channel.sendMessage(getRecentGames(recentGames, player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getRecentGames(RecentGames games, Player player, JsonObject generalObject, JsonObject hypixelObject) {
		final List<Games> recentGames = games.getRecentGames();
		EmbedBuilder recentGamesEmbed = new EmbedBuilder();
		recentGamesEmbed.setAuthor(format("Hypixel Player %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		recentGamesEmbed.setColor(new Color(240, 197, 85));
		recentGamesEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		recentGamesEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		for(int i = 0; i < 5; i++) {
			Games game = recentGames.get(i);
			recentGamesEmbed.addField("Game : " + game.getGame(), "Mode : " + game.getMode() + "\nMap : " + game.getMap(), false);
		}

		return recentGamesEmbed;
	}
}
