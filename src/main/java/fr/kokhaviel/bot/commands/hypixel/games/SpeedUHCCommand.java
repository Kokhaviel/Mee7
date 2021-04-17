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
import fr.kokhaviel.api.hypixel.player.Challenges;
import fr.kokhaviel.api.hypixel.player.Player;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.hypixel.player.stats.SpeedUHC;
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

public class SpeedUHCCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "speeduhc")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "speeduhc <Player>").queue(
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
				data = hypixelAPI.getPlayerData(args[1]);
			} catch(MalformedURLException e) {
				e.printStackTrace();
			} catch(IllegalStateException e) {
				channel.sendMessage("This Username Doesn't Exists !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
				);
			}

			assert data != null;
			if(!data.isSuccess()) {
				channel.sendMessage(data.getCause()).queue();
				return;
			}

			Player player = data.getPlayer();
			channel.sendMessage(getSpeedUHCStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getSpeedUHCStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		SpeedUHC speedUHC = player.getStats().getSpeedUHC();
		EmbedBuilder speedUhcEmbed = new EmbedBuilder();
		speedUhcEmbed.setAuthor(format("Hypixel Speed UHC %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		speedUhcEmbed.setColor(new Color(255, 255, 0));
		speedUhcEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		speedUhcEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		speedUhcEmbed.addField("Coins : ", String.valueOf(speedUHC.getCoins()), true);
		speedUhcEmbed.addField("Score : ", String.valueOf(speedUHC.getScore()), true);
		speedUhcEmbed.addField("Wins : ", String.valueOf(speedUHC.getWins()), true);
		speedUhcEmbed.addField("Winstreak : ", String.valueOf(speedUHC.getWinstreak()), true);
		speedUhcEmbed.addField("Highest Winstreak : ", String.valueOf(speedUHC.getHighestWinstreak()), true);
		speedUhcEmbed.addField("Losses : ", String.valueOf(speedUHC.getLosses()), true);
		speedUhcEmbed.addField("Games : ", String.valueOf(speedUHC.getGames()), true);
		speedUhcEmbed.addField("Quits : ", String.valueOf(speedUHC.getQuits()), true);
		speedUhcEmbed.addField("Kills : ", String.valueOf(speedUHC.getKills()), true);
		speedUhcEmbed.addField("Killstreak : ", String.valueOf(speedUHC.getKillstreak()), true);
		speedUhcEmbed.addField("Deaths : ", String.valueOf(speedUHC.getDeaths()), true);
		speedUhcEmbed.addField("Assists : ", String.valueOf(speedUHC.getAssists()), true);
		speedUhcEmbed.addField("Blocks Placed : ", String.valueOf(speedUHC.getBlocksPlaced()), true);
		speedUhcEmbed.addField("Blocks Broken : ", String.valueOf(speedUHC.getBlocksBroken()), true);
		speedUhcEmbed.addField("Survived Players : ", String.valueOf(speedUHC.getSurvivedPlayers()), true);
		speedUhcEmbed.addField("Items Enchanted : ", String.valueOf(speedUHC.getEnchantedItems()), true);

		return speedUhcEmbed;
	}

	private EmbedBuilder getSpeedUHCQuests(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Challenges.ChallengesAllTime challenges = player.getChallenges().getChallengesAllTime();
		EmbedBuilder speedUhcEmbed = new EmbedBuilder();
		speedUhcEmbed.setAuthor(format("Hypixel Speed UHC %s", hypixelObject.get("quests").getAsString()), null, Config.HYPIXEL_ICON);
		speedUhcEmbed.setColor(new Color(255, 255, 0));
		speedUhcEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("quests").getAsString()));
		speedUhcEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		speedUhcEmbed.addField("Alchemist Challenge : ", String.valueOf(challenges.getSpeedUHCAlchemistChallenge()), false);
		speedUhcEmbed.addField("Wizard Challenge : ", String.valueOf(challenges.getSpeedUHCWizardChallenge()), false);
		speedUhcEmbed.addField("Marksman Challenge : ", String.valueOf(challenges.getSpeedUHCMarksmanChallenge()), false);
		speedUhcEmbed.addField("Nether Challenge : ", String.valueOf(challenges.getSpeedUHCNetherChallenge()), false);

		return speedUhcEmbed;
	}
}
