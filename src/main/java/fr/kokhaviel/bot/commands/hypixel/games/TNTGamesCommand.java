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

package fr.kokhaviel.bot.commands.hypixel.games;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.player.Player;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.hypixel.player.stats.TNTGames;
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
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.hypixelAPI;
import static java.lang.String.format;

public class TNTGamesCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "tnt")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "tnt <Player>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage(format("%s !", HYPIXEL_OBJECT.get("invalid_username").getAsString())).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			message.delete().queue();

			PlayerData data = null;
			try {
				data = hypixelAPI.getData(args[1]);
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			assert data != null;
			if(!data.isSuccess()) {
				channel.sendMessage(data.getCause()).queue();
				return;
			}

			Player player = data.getPlayer();
			channel.sendMessage(getTntStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getTntStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		TNTGames tntGames = player.getStats().getTntGames();
		EmbedBuilder tntEmbed = new EmbedBuilder();
		tntEmbed.setAuthor(format("Hypixel Tnt Games %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		tntEmbed.setColor(new Color(210, 47, 26));
		tntEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		tntEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		tntEmbed.addField("Coins : ", String.valueOf(tntGames.getCoins()), true);
		tntEmbed.addField("Wins : ", String.valueOf(tntGames.getWins()), true);
		tntEmbed.addField("Winstreak : ", String.valueOf(tntGames.getWinstreak()), true);
		tntEmbed.addField("Kills PvPRun : ", String.valueOf(tntGames.getPvprunKills()), true);
		tntEmbed.addField("Kills TntTag : ", String.valueOf(tntGames.getTnttagKills()), true);
		tntEmbed.addField("Deaths Bowspleef : ", String.valueOf(tntGames.getBowspleefDeaths()), true);
		tntEmbed.addField("Deaths TntRun : ", String.valueOf(tntGames.getTntrunDeaths()), true);
		tntEmbed.addField("Deaths PvPRun : ", String.valueOf(tntGames.getPvprunDeaths()), true);
		tntEmbed.addField("Record TntRun : ", String.valueOf(tntGames.getTnttunRecord()), true);
		tntEmbed.addField("Record PvPRun : ", String.valueOf(tntGames.getPvprunRecord()), true);
		tntEmbed.addField("Potion Splashed : ", String.valueOf(tntGames.getPotionsSplashed()), true);
		tntEmbed.addField("Tags Bowspleef : ", String.valueOf(tntGames.getBowspleefTags()), true);

		return tntEmbed;
	}
}
