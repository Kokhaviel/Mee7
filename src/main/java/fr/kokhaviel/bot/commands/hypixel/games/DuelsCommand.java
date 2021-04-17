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
import fr.kokhaviel.api.hypixel.player.stats.Duels;
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

public class DuelsCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "duels")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "duels <Player>").queue(
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
			channel.sendMessage(getDuelsStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getBridgeDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getSkywarsDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getBowSpleefDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getUhcDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getUhcMeetupStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getClassicDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getOpDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getBowDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getSumoDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getComboDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getPotionDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getMegaWallsDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getBlitzDuelStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getDuelsQuests(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getDuelsStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(137, 103, 39));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Coins : ", String.valueOf(duels.getCoins()), true);
		duelsEmbed.addField("Wins : ", String.valueOf(duels.getWins()), true);
		duelsEmbed.addField("Current Winstreak : ", String.valueOf(duels.getCurrentWinstreak()), true);
		duelsEmbed.addField("Best Winstreak : ", String.valueOf(duels.getBestWinstreak()), true);
		duelsEmbed.addField("Cosmetic Title : ", duels.getCosmeticTitle(), true);
		duelsEmbed.addField("Status : ", duels.getStatus(), true);
		duelsEmbed.addField("Chat Enabled : ", duels.getChatEnabled(), true);
		duelsEmbed.addField("Losses : ", String.valueOf(duels.getLosses()), true);
		duelsEmbed.addField("Kills : ", String.valueOf(duels.getKills()), true);
		duelsEmbed.addField("Deaths : ", String.valueOf(duels.getDeaths()), true);
		duelsEmbed.addField("Games Played : ", String.valueOf(duels.getGamesPlayed()), true);
		duelsEmbed.addField("Blocks Placed : ", String.valueOf(duels.getBlocksPlaced()), true);
		duelsEmbed.addField("Melee Hits : ", String.valueOf(duels.getMeleeHits()), true);
		duelsEmbed.addField("Bow Shots : ", String.valueOf(duels.getBowShots()), true);
		duelsEmbed.addField("Bow Hits : ", String.valueOf(duels.getBowHits()), true);
		duelsEmbed.addField("Health Regenerated : ", String.valueOf(duels.getHealthRegenerated()), true);
		duelsEmbed.addField("Damage Dealt : ", String.valueOf(duels.getDamageDealt()), true);
		duelsEmbed.addField("Gapple Eaten : ", String.valueOf(duels.getGappleEaten()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getBridgeDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Bridge Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(74, 60, 91));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Current Bridge Winstreak : ", String.valueOf(duels.getCurrentBridgeWinstreak()), true);
		duelsEmbed.addField("Bridge Goals : ", String.valueOf(duels.getBridgeGoals()), true);
		duelsEmbed.addField("Bridge Losses : ", String.valueOf(duels.getBridgeLosses()), true);
		duelsEmbed.addField("Bridge Kills : ", String.valueOf(duels.getBridgeKills()), true);
		duelsEmbed.addField("Bridge Deaths : ", String.valueOf(duels.getBridgeDeaths()), true);
		duelsEmbed.addField("Bridge Melee Hits : ", String.valueOf(duels.getBridgeMeleeHits()), true);
		duelsEmbed.addField("Bridge Bow Shots : ", String.valueOf(duels.getBridgeBowShots()), true);
		duelsEmbed.addField("Bridge Health Regenerated : ", String.valueOf(duels.getBridgeHealthRegenerated()), true);
		duelsEmbed.addField("Bridge Damage Dealt : ", String.valueOf(duels.getBridgeDamageDealt()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getSkywarsDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Skywars Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(118, 182, 71));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Current Skywars Winstreak : ", String.valueOf(duels.getCurrentSkywarsWinstreak()), true);
		duelsEmbed.addField("Best Skywars Winstreak : ", String.valueOf(duels.getBestSkywarsWinstreak()), true);
		duelsEmbed.addField("Skywars Wins : ", String.valueOf(duels.getSkywarsWins()), true);
		duelsEmbed.addField("Skywars Losses : ", String.valueOf(duels.getSkywarsLosses()), true);
		duelsEmbed.addField("Skywars Kills : ", String.valueOf(duels.getSkywarsKills()), true);
		duelsEmbed.addField("Skywars Deaths : ", String.valueOf(duels.getSkywarsDeaths()), true);
		duelsEmbed.addField("Skywars Melee Hits : ", String.valueOf(duels.getSkywarsMeleeHits()), true);
		duelsEmbed.addField("Skywars Bow Shots : ", String.valueOf(duels.getSkywarsBowShots()), true);
		duelsEmbed.addField("Skywars Bow Hits : ", String.valueOf(duels.getSkywarsBowHits()), true);
		duelsEmbed.addField("Skywars Health Regenerated : ", String.valueOf(duels.getSkywarsHealthRegenerated()), true);
		duelsEmbed.addField("Skywars Damage Dealt : ", String.valueOf(duels.getSkywarsDamageDealt()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getBowSpleefDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Bow Spleef Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(219, 68, 26));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);
		duelsEmbed.addField("Bowspleef Wins : ", String.valueOf(duels.getBowspleefWins()), true);
		duelsEmbed.addField("Bowspleef Losses : ", String.valueOf(duels.getBowspleefLosses()), true);
		duelsEmbed.addField("Bowspleef Bow Shots : ", String.valueOf(duels.getBowspleefBowShots()), true);
		duelsEmbed.addField("Bowspleef Rounds Played : ", String.valueOf(duels.getBowspleefRoundsPlayed()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getUhcDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel UHC Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(255, 191, 0));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Current UHC Winstreak : ", String.valueOf(duels.getCurrentUHCWinstreak()), true);
		duelsEmbed.addField("Best UHC Winstreak : ", String.valueOf(duels.getBestUHCWinstreak()), true);
		duelsEmbed.addField("UHC Wins : ", String.valueOf(duels.getUhcWins()), true);
		duelsEmbed.addField("UHC Losses : ", String.valueOf(duels.getUhcLosses()), true);
		duelsEmbed.addField("UHC Rounds Played : ", String.valueOf(duels.getUhcRoundsPlayed()), true);
		duelsEmbed.addField("UHC Kills : ", String.valueOf(duels.getUhcKills()), true);
		duelsEmbed.addField("UHC Melee Hits : ", String.valueOf(duels.getUhcMeleeHits()), true);
		duelsEmbed.addField("UHC Bow Shots : ", String.valueOf(duels.getUhcBowShots()), true);
		duelsEmbed.addField("UHC Bow Hits : ", String.valueOf(duels.getUhcBowHits()), true);
		duelsEmbed.addField("UHC Health Regenerated : ", String.valueOf(duels.getUhcHealthRegenerated()), true);
		duelsEmbed.addField("UHC Damage Dealt : ", String.valueOf(duels.getUhcDamageDealt()), true);
		duelsEmbed.addField("UHC Blocks Placed : ", String.valueOf(duels.getUhcBlocksPlaced()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getUhcMeetupStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel UHC Meetup %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(255, 191, 0));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("UHC Meetup Losses : ", String.valueOf(duels.getUhcMeetupLosses()), true);
		duelsEmbed.addField("UHC Meetup Rounds Played : ", String.valueOf(duels.getUhcMeetupRoundsPlayed()), true);
		duelsEmbed.addField("UHC Meetup Kills : ", String.valueOf(duels.getUhcMeetupKills()), true);
		duelsEmbed.addField("UHC Meetup Deaths : ", String.valueOf(duels.getUhcMeetupDeaths()), true);
		duelsEmbed.addField("UHC Meetup Melee Hits : ", String.valueOf(duels.getUhcMeetupMeleeHits()), true);
		duelsEmbed.addField("UHC Meetup Bow Shots : ", String.valueOf(duels.getUhcMeetupBowShots()), true);
		duelsEmbed.addField("UHC Meetup Bow Hits : ", String.valueOf(duels.getUhcMeetupBowHits()), true);
		duelsEmbed.addField("UHC Meetup Health Regenerated : ", String.valueOf(duels.getUhcMeetupHealthRegenerated()), true);
		duelsEmbed.addField("UHC Meetup Damage Dealt : ", String.valueOf(duels.getUhcMeetupDamageDealt()), true);
		duelsEmbed.addField("UHC Meetup Blocks Placed : ", String.valueOf(duels.getUhcMeetupBlocksPlaced()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getClassicDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Classic Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(137, 103, 39));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Current Classic Winstreak : ", String.valueOf(duels.getCurrentClassicWinstreak()), true);
		duelsEmbed.addField("Best Classic Winstreak : ", String.valueOf(duels.getBestClassicWinstreak()), true);
		duelsEmbed.addField("Classic Wins : ", String.valueOf(duels.getClassicWins()), true);
		duelsEmbed.addField("Classic Losses : ", String.valueOf(duels.getClassicLosses()), true);
		duelsEmbed.addField("Classic Rounds Played : ", String.valueOf(duels.getClassicRoundsPlayed()), true);
		duelsEmbed.addField("Classic Kills : ", String.valueOf(duels.getClassicKills()), true);
		duelsEmbed.addField("Classic Melee Hits : ", String.valueOf(duels.getClassicMeleeHits()), true);
		duelsEmbed.addField("Classic Bow Hits : ", String.valueOf(duels.getClassicBowHits()), true);
		duelsEmbed.addField("Classic Health Regenerated : ", String.valueOf(duels.getClassicHealthRegenerated()), true);
		duelsEmbed.addField("Classic Damage Dealt : ", String.valueOf(duels.getClassicDamageDealt()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getOpDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Op Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(51, 235, 203));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Current Op Winstreak : ", String.valueOf(duels.getCurrentOpWinstreak()), true);
		duelsEmbed.addField("Best Op Winstreak : ", String.valueOf(duels.getBestOpWinstreak()), true);
		duelsEmbed.addField("Op Wins : ", String.valueOf(duels.getOpWins()), true);
		duelsEmbed.addField("Op Losses : ", String.valueOf(duels.getOpLosses()), true);
		duelsEmbed.addField("Op Rounds Played : ", String.valueOf(duels.getOpRoundsPlayed()), true);
		duelsEmbed.addField("Op Kills : ", String.valueOf(duels.getOpKills()), true);
		duelsEmbed.addField("Op Melee Hits : ", String.valueOf(duels.getOpMeleeHits()), true);
		duelsEmbed.addField("Op Health Regenerated : ", String.valueOf(duels.getOpHealthRegenerated()), true);
		duelsEmbed.addField("Op Damage Dealt : ", String.valueOf(duels.getOpDamageDealt()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getBowDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Bow Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(137, 103, 39));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Current Bow Winstreak : ", String.valueOf(duels.getCurrentBowWinstreak()), true);
		duelsEmbed.addField("Bow Wins : ", String.valueOf(duels.getBowWins()), true);
		duelsEmbed.addField("Bow Losses : ", String.valueOf(duels.getBowLosses()), true);
		duelsEmbed.addField("Bow Rounds Played : ", String.valueOf(duels.getBowRoundsPlayed()), true);
		duelsEmbed.addField("Bow Bow Shots : ", String.valueOf(duels.getBowBowShots()), true);
		duelsEmbed.addField("Bow Bow Hits : ", String.valueOf(duels.getBowBowHits()), true);
		duelsEmbed.addField("Bow Health Regenerated : ", String.valueOf(duels.getBowHealthRegenerated()), true);
		duelsEmbed.addField("Bow Damage Dealt : ", String.valueOf(duels.getBowDamageDealt()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getSumoDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Sumo Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(125, 200, 115));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Current Sumo Winstreak : ", String.valueOf(duels.getCurrentSumoWinstreak()), true);
		duelsEmbed.addField("Best Sumo Winstreak : ", String.valueOf(duels.getBestSumoWinstreak()), true);
		duelsEmbed.addField("Sumo Wins : ", String.valueOf(duels.getSumoWins()), true);
		duelsEmbed.addField("Sumo Losses : ", String.valueOf(duels.getSumoLosses()), true);
		duelsEmbed.addField("Sumo Rounds Played : ", String.valueOf(duels.getSumoRoundsPlayed()), true);
		duelsEmbed.addField("Sumo Kills : ", String.valueOf(duels.getSumoKills()), true);
		duelsEmbed.addField("Sumo Melee Hits : ", String.valueOf(duels.getSumoMeleeHits()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getComboDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Combo Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(179, 45, 189));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Combo Rounds Played : ", String.valueOf(duels.getComboRoundsPlayed()), true);
		duelsEmbed.addField("Combo Melee Hits : ", String.valueOf(duels.getComboMeleeHits()), true);
		duelsEmbed.addField("Combo Gapple Eaten : ", String.valueOf(duels.getComboGapplesEaten()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getPotionDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Potion Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(252, 140, 228));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Potion Rounds Played : ", String.valueOf(duels.getPotionRoundsPlayed()), true);
		duelsEmbed.addField("Potion Melee Hits : ", String.valueOf(duels.getPotionMeleeHits()), true);
		duelsEmbed.addField("Potion Damage Dealt : ", String.valueOf(duels.getPotionDamageDealt()), true);
		duelsEmbed.addField("Potion Health Regenerated : ", String.valueOf(duels.getPotionHealthRegenerated()), true);
		duelsEmbed.addField("Potion Pots Used : ", String.valueOf(duels.getPotionPotsUsed()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getMegaWallsDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Mega Walls Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(18, 18, 18));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Mega Walls Class : ", duels.getMegaWallsClass(), true);
		duelsEmbed.addField("Mega Walls Rounds Played : ", String.valueOf(duels.getMegaWallsRoundsPlayed()), true);
		duelsEmbed.addField("Mega Walls Melee Hits : ", String.valueOf(duels.getMegaWallsMeleeHits()), true);
		duelsEmbed.addField("Mega Walls Bow Shots : ", String.valueOf(duels.getMegaWallsBowShots()), true);
		duelsEmbed.addField("Mega Walls Bow Hits : ", String.valueOf(duels.getMegaWallsBowHits()), true);
		duelsEmbed.addField("Mega Walls Health Regenerated : ", String.valueOf(duels.getMegaWallsHealthRegenerated()), true);
		duelsEmbed.addField("Mega Walls Damage Dealt : ", String.valueOf(duels.getMegaWallsDamageDealt()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getBlitzDuelStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Duels duels = player.getStats().getDuels();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Blitz Duels %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(51, 235, 203));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Blitz Kit : ", duels.getBlitzKit(), true);
		duelsEmbed.addField("Current Blitz Winstreak : ", String.valueOf(duels.getCurrentBlitzWinstreak()), true);
		duelsEmbed.addField("Best Blitz Winstreak : ", String.valueOf(duels.getBestBlitzWinstreak()), true);
		duelsEmbed.addField("Blitz Wins : ", String.valueOf(duels.getBlitzWins()), true);
		duelsEmbed.addField("Blitz Losses : ", String.valueOf(duels.getBlitzLosses()), true);
		duelsEmbed.addField("Blitz Rounds Played : ", String.valueOf(duels.getBlitzRoundsPlayed()), true);
		duelsEmbed.addField("Blitz Kills : ", String.valueOf(duels.getBlitzKills()), true);
		duelsEmbed.addField("Blitz Melee Hits : ", String.valueOf(duels.getBlitzMeleeHits()), true);
		duelsEmbed.addField("Blitz Health Regenerated : ", String.valueOf(duels.getBlitzHealthRegenerated()), true);
		duelsEmbed.addField("Blitz Damage Dealt : ", String.valueOf(duels.getBlitzDamageDealt()), true);
		duelsEmbed.addField("Blitz Blocks Placed : ", String.valueOf(duels.getBlitzBlocksPlaced()), true);

		return duelsEmbed;
	}

	private EmbedBuilder getDuelsQuests(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Challenges.ChallengesAllTime challenges = player.getChallenges().getChallengesAllTime();
		EmbedBuilder duelsEmbed = new EmbedBuilder();
		duelsEmbed.setAuthor(format("Hypixel Duels %s", hypixelObject.get("quests").getAsString()), null, Config.HYPIXEL_ICON);
		duelsEmbed.setColor(new Color(137, 103, 39));
		duelsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("quests").getAsString()));
		duelsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		duelsEmbed.addField("Feed The Void Challenge : ", String.valueOf(challenges.getDuelsFeedTheVoid()), false);
		duelsEmbed.addField("Teams Challenge : ", String.valueOf(challenges.getDuelsTeams()), false);
		duelsEmbed.addField("Target Practice Challenge : ", String.valueOf(challenges.getDuelsChallengeTargetPractice()), false);

		return duelsEmbed;
	}
}
