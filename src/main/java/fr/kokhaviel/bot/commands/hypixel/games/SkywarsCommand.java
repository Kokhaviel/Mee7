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
import fr.kokhaviel.api.hypixel.player.stats.Skywars;
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

public class SkywarsCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "skywars")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "skywars <Player>").queue(
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
			channel.sendMessage(getSkywarsStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getSkywarsGameStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getSkywarsGameStats2(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getLabSkywarsStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getMegaSkywarsStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getRankedSkywarsStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getHeadsSkywars(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getSkywarsStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Skywars skywars = player.getStats().getSkywars();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(130, 201, 100));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Coins : ", String.valueOf(skywars.getCoins()), true);
		skywarsEmbed.addField("Souls : ", String.valueOf(skywars.getSouls()), true);
		skywarsEmbed.addField("Opals : ", String.valueOf(skywars.getOpals()), true);
		skywarsEmbed.addField("Shards : ", String.valueOf(skywars.getShards()), true);
		skywarsEmbed.addField("Tokens : ", String.valueOf(skywars.getTokens()), true);
		skywarsEmbed.addField("Experience : ", String.valueOf(skywars.getExperience()), true);
		skywarsEmbed.addField("Last Mode : ", skywars.getLastMode(), true);

		return skywarsEmbed;
	}

	private EmbedBuilder getSkywarsGameStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Skywars skywars = player.getStats().getSkywars();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(130, 201, 100));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Wins : ", String.valueOf(skywars.getWins()), true);
		skywarsEmbed.addField("Winstreak : ", String.valueOf(skywars.getWinstreak()), true);
		skywarsEmbed.addField("Fastest Win : ", String.valueOf(skywars.getFastestWin()), true);
		skywarsEmbed.addField("Losses : ", String.valueOf(skywars.getLosses()), true);
		skywarsEmbed.addField("Games Played : ", String.valueOf(skywars.getGamesPlayed()), true);
		skywarsEmbed.addField("Time Played : ", String.valueOf(skywars.getTimePlayed()), true);
		skywarsEmbed.addField("Solo Wins : ", String.valueOf(skywars.getSoloWins()), true);
		skywarsEmbed.addField("Solo Games : ", String.valueOf(skywars.getSoloGames()), true);
		skywarsEmbed.addField("Solo Time Played : ", String.valueOf(skywars.getTimePlayedSolo()), true);
		skywarsEmbed.addField("Team Wins : ", String.valueOf(skywars.getTeamWins()), true);
		skywarsEmbed.addField("Team Games : ", String.valueOf(skywars.getTeamGames()), true);
		skywarsEmbed.addField("Team Time Played : ", String.valueOf(skywars.getTimePlayedTeam()), true);
		skywarsEmbed.addField("Survived Players : ", String.valueOf(skywars.getSurvivedPlayers()), true);
		skywarsEmbed.addField("Chests Opened : ", String.valueOf(skywars.getChestsOpened()), true);
		skywarsEmbed.addField("Blocks Placed : ", String.valueOf(skywars.getBlocksPlaced()), true);
		skywarsEmbed.addField("Blocks Broken : ", String.valueOf(skywars.getBlocksBroken()), true);
		skywarsEmbed.addField("Souls Gathered : ", String.valueOf(skywars.getSoulsGathered()), true);
		skywarsEmbed.addField("Items Enchanted : ", String.valueOf(skywars.getEnchants()), true);
		skywarsEmbed.addField("Egg Thrown : ", String.valueOf(skywars.getEggThrown()), true);
		skywarsEmbed.addField("Enderpearl Thrown : ", String.valueOf(skywars.getEnderpearlThrown()), true);

		return skywarsEmbed;
	}

	private EmbedBuilder getSkywarsGameStats2(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Skywars skywars = player.getStats().getSkywars();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(130, 201, 100));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Kills : ", String.valueOf(skywars.getKills()), true);
		skywarsEmbed.addField("Killstreak : ", String.valueOf(skywars.getKillstreak()), true);
		skywarsEmbed.addField("Melee Kills : ", String.valueOf(skywars.getMeleeKills()), true);
		skywarsEmbed.addField("Void Kills : ", String.valueOf(skywars.getVoidKills()), true);
		skywarsEmbed.addField("Fall Kills : ", String.valueOf(skywars.getFallKills()), true);
		skywarsEmbed.addField("Bow Kills : ", String.valueOf(skywars.getBowKills()), true);
		skywarsEmbed.addField("Most Kills / Game : ", String.valueOf(skywars.getMostKillsGame()), true);
		skywarsEmbed.addField("Deaths : ", String.valueOf(skywars.getDeaths()), true);
		skywarsEmbed.addField("Assists ", String.valueOf(skywars.getAssists()), true);
		skywarsEmbed.addField("Quits ", String.valueOf(skywars.getQuits()), true);
		skywarsEmbed.addField("Arrows Shot ", String.valueOf(skywars.getArrowsShot()), true);
		skywarsEmbed.addField("Arrows Hit ", String.valueOf(skywars.getArrowsHit()), true);
		skywarsEmbed.addField("Longest Bow Shot ", String.valueOf(skywars.getLongestBowShot()), true);
		skywarsEmbed.addField("Longest Bow Kill ", String.valueOf(skywars.getLongestBowKill()), true);

		return skywarsEmbed;
	}
	private EmbedBuilder getLabSkywarsStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Skywars skywars = player.getStats().getSkywars();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars Laboratory %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(255, 85, 255));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Lab Coins Gained : ", String.valueOf(skywars.getLabCoins()), true);
		skywarsEmbed.addField("Lab Wins : ", String.valueOf(skywars.getLabWins()), true);
		skywarsEmbed.addField("Lab Winstreak : ", String.valueOf(skywars.getLabWinstreak()), true);
		skywarsEmbed.addField("Lab Fastest Win : ", String.valueOf(skywars.getLabFastestWin()), true);
		skywarsEmbed.addField("Lab Losses : ", String.valueOf(skywars.getLabLosses()), true);
		skywarsEmbed.addField("Lab Games Played : ", String.valueOf(skywars.getLabGames()), true);
		skywarsEmbed.addField("Lab Time Played : ", String.valueOf(skywars.getLabTimePlayed()), true);
		skywarsEmbed.addField("Lab Kills : ", String.valueOf(skywars.getLabKills()), true);
		skywarsEmbed.addField("Lab Deaths : ", String.valueOf(skywars.getLabDeaths()), true);
		skywarsEmbed.addField("Lab Assists : ", String.valueOf(skywars.getLabAssists()), true);
		skywarsEmbed.addField("Lab Quits : ", String.valueOf(skywars.getLabQuits()), true);
		skywarsEmbed.addField("Lab Survived Players : ", String.valueOf(skywars.getLabSurvivedPlayers()), true);
		skywarsEmbed.addField("Lab Souls Gathered : ", String.valueOf(skywars.getLabSoulsGathered()), true);
		skywarsEmbed.addField("Lab Chests Opened : ", String.valueOf(skywars.getLabChestsOpened()), true);
		skywarsEmbed.addField("Lab Blocks Placed : ", String.valueOf(skywars.getLabBlocksPlaced()), true);
		skywarsEmbed.addField("Lab Blocks Broken : ", String.valueOf(skywars.getLabBlocksBroken()), true);
		skywarsEmbed.addField("Lab Arrows Shot : ", String.valueOf(skywars.getLabArrowsShot()), true);
		skywarsEmbed.addField("Lab Arrows Hit : ", String.valueOf(skywars.getLabArrowsHit()), true);
		skywarsEmbed.addField("Lab Egg Thrown : ", String.valueOf(skywars.getLabEggThrown()), true);
		skywarsEmbed.addField("Lab Enderpearl Thrown : ", String.valueOf(skywars.getLabEnderpearlThrown()), true);

		return skywarsEmbed;
	}

	private EmbedBuilder getMegaSkywarsStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Skywars skywars = player.getStats().getSkywars();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars Mega %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(206, 130, 45));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Mega Wins : ", String.valueOf(skywars.getMegaWins()), true);
		skywarsEmbed.addField("Mega Losses : ", String.valueOf(skywars.getMegaLosses()), true);
		skywarsEmbed.addField("Mega Kills : ", String.valueOf(skywars.getMegaKills()), true);
		skywarsEmbed.addField("Mega Deaths : ", String.valueOf(skywars.getMegaKills()), true);
		skywarsEmbed.addField("Mega Time Played : ", String.valueOf(skywars.getMegaTimePlayed()), true);
		skywarsEmbed.addField("Mega Arrows Shot : ", String.valueOf(skywars.getMegaArrowsShot()), true);
		skywarsEmbed.addField("Mega Arrows Hit : ", String.valueOf(skywars.getMegaArrowsHit()), true);
		skywarsEmbed.addField("Mega Survived Players : ", String.valueOf(skywars.getMegaSurvivedPlayers()), true);
		skywarsEmbed.addField("Mega Longest Bow Kill : ", String.valueOf(skywars.getMegaLongestBowKill()), true);
		skywarsEmbed.addField("Mega Chests Opened : ", String.valueOf(skywars.getMegaChestsOpened()), true);

		return skywarsEmbed;
	}


	private EmbedBuilder getRankedSkywarsStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Skywars skywars = player.getStats().getSkywars();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars Ranked %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(47, 182, 189));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Ranked Wins : ", String.valueOf(skywars.getRankedWins()), true);
		skywarsEmbed.addField("Ranked Fastest Win : ", String.valueOf(skywars.getFastestWinRanked()), true);
		skywarsEmbed.addField("Ranked Losses : ", String.valueOf(skywars.getRankedLosses()), true);
		skywarsEmbed.addField("Ranked Games : ", String.valueOf(skywars.getRankedGames()), true);
		skywarsEmbed.addField("Ranked Kills : ", String.valueOf(skywars.getRankedKills()), true);
		skywarsEmbed.addField("Ranked Killstreak : ", String.valueOf(skywars.getRankedKillstreak()), true);
		skywarsEmbed.addField("Ranked Deaths : ", String.valueOf(skywars.getRankedDeaths()), true);
		skywarsEmbed.addField("Ranked Assists : ", String.valueOf(skywars.getRankedAssists()), true);
		skywarsEmbed.addField("Ranked Time Played : ", String.valueOf(skywars.getRankedTimePlayed()), true);
		skywarsEmbed.addField("Ranked Arrows Shot : ", String.valueOf(skywars.getRankedArrowsShot()), true);
		skywarsEmbed.addField("Ranked Arrows Hit : ", String.valueOf(skywars.getRankedArrowsHit()), true);
		skywarsEmbed.addField("Ranked Survived Players : ", String.valueOf(skywars.getRankedSurvivedPlayers()), true);
		skywarsEmbed.addField("Ranked Longest Bow Kill : ", String.valueOf(skywars.getRankedLongestBowKill()), true);
		skywarsEmbed.addField("Ranked Chests Opened : ", String.valueOf(skywars.getRankedChestsOpened()), true);
		skywarsEmbed.addField("Ranked Shards : ", String.valueOf(skywars.getRankedShards()), true);

		return skywarsEmbed;
	}


	private EmbedBuilder getHeadsSkywars(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Skywars skywars = player.getStats().getSkywars();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars Heads %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(157, 0, 157));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Heads : ", String.valueOf(skywars.getHeads()), true);
		skywarsEmbed.addField("Heads Tasty : ", String.valueOf(skywars.getHeadsTasty()), true);
		skywarsEmbed.addField("Heads Decent : ", String.valueOf(skywars.getHeadsDecent()), true);
		skywarsEmbed.addField("Heads Meh : ", String.valueOf(skywars.getHeadsMeh()), true);
		skywarsEmbed.addField("Heads Succulent : ", String.valueOf(skywars.getHeadsSucculent()), true);
		skywarsEmbed.addField("Heads Eww : ", String.valueOf(skywars.getHeadsEww()), true);
		skywarsEmbed.addField("Heads Salty : ", String.valueOf(skywars.getHeadsSalty()), true);
		skywarsEmbed.addField("Heads Yucky : ", String.valueOf(skywars.getHeadsYucky()), true);
		skywarsEmbed.addField("Heads Divine : ", String.valueOf(skywars.getHeadsDivine()), true);
		skywarsEmbed.addField("Heads Heavenly : ", String.valueOf(skywars.getHeadsHeavenly()), true);

		return skywarsEmbed;
	}

	private EmbedBuilder getSkywarsQuests(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Challenges.ChallengesAllTime challenges = player.getChallenges().getChallengesAllTime();
		EmbedBuilder skywarsEmbed = new EmbedBuilder();
		skywarsEmbed.setAuthor(format("Hypixel Skywars %s", hypixelObject.get("quests").getAsString()), null, Config.HYPIXEL_ICON);
		skywarsEmbed.setColor(new Color(130, 201, 100));
		skywarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("quests").getAsString()));
		skywarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		skywarsEmbed.addField("Feeding The Void Challenge : ", String.valueOf(challenges.getSkywarsFeedingTheVoidChallenge()), false);
		skywarsEmbed.addField("Rush Challenge : ", String.valueOf(challenges.getSkywarsRushChallenge()), false);
		skywarsEmbed.addField("Ranked Challenge : ", String.valueOf(challenges.getSkywarsRankedChallenge()), false);
		skywarsEmbed.addField("Enderman Challenge : ", String.valueOf(challenges.getSkywarsEndermanChallenge()), false);

		return skywarsEmbed;
	}
}
