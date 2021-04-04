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

package fr.kokhaviel.api.hypixel.player.stats;

import com.google.gson.annotations.SerializedName;

public class Skywars {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("souls")
	int souls = 0;

	@SerializedName("opals")
	int opals = 0;

	@SerializedName("shard")
	int shards = 0;

	@SerializedName("cosmetic_tokens")
	int tokens = 0;

	@SerializedName("lastMode")
	String lastMode = "";

	@SerializedName("skywars_experience")
	int experience = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("games_played_skywars")
	int gamesPlayed = 0;

	@SerializedName("time_played")
	int timePlayed = 0;

	@SerializedName("wins_solo")
	int soloWins = 0;

	@SerializedName("games_solo")
	int soloGames = 0;

	@SerializedName("time_played_solo")
	int timePlayedSolo = 0;

	@SerializedName("wins_team")
	int teamWins = 0;

	@SerializedName("games_team")
	int teamGames = 0;

	@SerializedName("time_played_team")
	int timePlayedTeam = 0;

	@SerializedName("winstreak")
	int winstreak = 0;

	@SerializedName("fastest_win")
	int fastestWin = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("melee_kills")
	int meleeKills = 0;

	@SerializedName("void_kills")
	int voidKills = 0;

	@SerializedName("fall_kills")
	int fallKills = 0;

	@SerializedName("bow_kills")
	int bowKills = 0;

	@SerializedName("killstreak")
	int killstreak = 0;

	@SerializedName("most_kills_game")
	int mostKillsGame = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("assists")
	int assists = 0;

	@SerializedName("quits")
	int quits = 0;

	@SerializedName("survived_players")
	int survivedPlayers = 0;

	@SerializedName("chests_opened")
	int chestsOpened = 0;

	@SerializedName("arrows_shot")
	int arrowsShot = 0;

	@SerializedName("arrows_hit")
	int arrowsHit = 0;

	@SerializedName("longest_bow_shot")
	int longestBowShot = 0;

	@SerializedName("longest_bow_kill")
	int longestBowKill = 0;

	@SerializedName("blocks_placed")
	int blocksPlaced = 0;

	@SerializedName("blocks_broken")
	int blocksBroken = 0;

	@SerializedName("souls_gathered")
	int soulsGathered = 0;

	@SerializedName("items_enchanted")
	int enchants = 0;

	@SerializedName("egg_thrown")
	int eggThrown = 0;

	@SerializedName("enderpearls_thrown")
	int enderpearlThrown = 0;



	@SerializedName("coins_gained_lab")
	int labCoins = 0;

	@SerializedName("wins_lab")
	int labWins = 0;

	@SerializedName("games_lab")
	int labGames = 0;

	@SerializedName("win_streak_lab")
	int labWinstreak = 0;

	@SerializedName("losses_lab")
	int labLosses = 0;

	@SerializedName("kills_lab")
	int labKills = 0;

	@SerializedName("deaths_lab")
	int labDeaths = 0;

	@SerializedName("quits_lab")
	int labQuits = 0;

	@SerializedName("time_played_lab")
	int labTimePlayed = 0;

	@SerializedName("survived_players_lab")
	int labSurvivedPlayers = 0;

	@SerializedName("souls_gathered_lab")
	int labSoulsGathered = 0;

	@SerializedName("chests_opened_lab")
	int labChestsOpened = 0;

	@SerializedName("assists_lab")
	int labAssists = 0;

	@SerializedName("fastest_win_lab")
	int labFastestWin = 0;

	@SerializedName("blocks_placed_lab")
	int labBlocksPlaced = 0;

	@SerializedName("blocks_broken_lab")
	int labBlocksBroken = 0;

	@SerializedName("arrows_shot_lab")
	int labArrowsShot = 0;

	@SerializedName("arrows_hit_lab")
	int labArrowsHit = 0;

	@SerializedName("egg_thrown_lab")
	int labEggThrown = 0;

	@SerializedName("enderpearls_thrown_lab")
	int labEnderpearlThrown = 0;



	@SerializedName("wins_mega")
	int megaWins = 0;

	@SerializedName("losses_mega")
	int megaLosses = 0;

	@SerializedName("kills_mega")
	int megaKills = 0;

	@SerializedName("deaths_mega")
	int megaDeaths = 0;

	@SerializedName("time_played_mega")
	int megaTimePlayed = 0;

	@SerializedName("arrows_shot_mega")
	int megaArrowsShot = 0;

	@SerializedName("arrows_hit_mega")
	int megaArrowsHit = 0;

	@SerializedName("survived_players_mega")
	int megaSurvivedPlayers = 0;

	@SerializedName("longest_bow_kill_mega")
	int megaLongestBowKill = 0;

	@SerializedName("chests_opened_mega")
	int megaChestsOpened = 0;


	@SerializedName("heads")
	int heads = 0;

	@SerializedName("heads_tasty")
	int headsTasty = 0;

	@SerializedName("heads_decent")
	int headsDecent = 0;

	@SerializedName("heads_meh")
	int headsMeh = 0;

	@SerializedName("heads_succulent")
	int headsSucculent = 0;

	@SerializedName("heads_eww")
	int headsEww = 0;

	@SerializedName("heads_salty")
	int headsSalty = 0;

	@SerializedName("heads_yucky")
	int headsYucky = 0;

	@SerializedName("heads_divine")
	int headsDivine = 0;

	@SerializedName("heads_heavenly")
	int headsHeavenly = 0;



	@SerializedName("wins_ranked")
	int rankedWins = 0;

	@SerializedName("fastest_win_ranked")
	int fastestWinRanked = 0;

	@SerializedName("losses_ranked")
	int rankedLosses = 0;

	@SerializedName("games_ranked")
	int rankedGames = 0;

	@SerializedName("kills_ranked")
	int rankedKills = 0;

	@SerializedName("killstreak_ranked")
	int rankedKillstreak = 0;

	@SerializedName("deaths_ranked")
	int rankedDeaths = 0;

	@SerializedName("assists_ranked")
	int rankedAssists = 0;

	@SerializedName("time_played_ranked")
	int rankedTimePlayed = 0;

	@SerializedName("arrows_shot_ranked")
	int rankedArrowsShot = 0;

	@SerializedName("arrows_hit_ranked")
	int rankedArrowsHit = 0;

	@SerializedName("survived_players_ranked")
	int rankedSurvivedPlayers = 0;

	@SerializedName("longest_bow_kill_ranked")
	int rankedLongestBowKill = 0;

	@SerializedName("chests_opened_ranked")
	int rankedChestsOpened = 0;

	@SerializedName("shards_ranked")
	int rankedShards = 0;


	public int getCoins() {
		return coins;
	}

	public int getSouls() {
		return souls;
	}

	public int getOpals() {
		return opals;
	}

	public int getShards() {
		return shards;
	}

	public int getTokens() {
		return tokens;
	}

	public String getLastMode() {
		return lastMode;
	}

	public int getExperience() {
		return experience;
	}

	public int getWins() {
		return wins;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getTimePlayed() {
		return timePlayed;
	}

	public int getSoloWins() {
		return soloWins;
	}

	public int getSoloGames() {
		return soloGames;
	}

	public int getTimePlayedSolo() {
		return timePlayedSolo;
	}

	public int getTeamWins() {
		return teamWins;
	}

	public int getTeamGames() {
		return teamGames;
	}

	public int getTimePlayedTeam() {
		return timePlayedTeam;
	}

	public int getWinstreak() {
		return winstreak;
	}

	public int getFastestWin() {
		return fastestWin;
	}

	public int getLosses() {
		return losses;
	}

	public int getKills() {
		return kills;
	}

	public int getMeleeKills() {
		return meleeKills;
	}

	public int getVoidKills() {
		return voidKills;
	}

	public int getFallKills() {
		return fallKills;
	}

	public int getBowKills() {
		return bowKills;
	}

	public int getKillstreak() {
		return killstreak;
	}

	public int getMostKillsGame() {
		return mostKillsGame;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getAssists() {
		return assists;
	}

	public int getQuits() {
		return quits;
	}

	public int getSurvivedPlayers() {
		return survivedPlayers;
	}

	public int getChestsOpened() {
		return chestsOpened;
	}

	public int getArrowsShot() {
		return arrowsShot;
	}

	public int getArrowsHit() {
		return arrowsHit;
	}

	public int getLongestBowShot() {
		return longestBowShot;
	}

	public int getLongestBowKill() {
		return longestBowKill;
	}

	public int getBlocksPlaced() {
		return blocksPlaced;
	}

	public int getBlocksBroken() {
		return blocksBroken;
	}

	public int getSoulsGathered() {
		return soulsGathered;
	}

	public int getEnchants() {
		return enchants;
	}

	public int getEggThrown() {
		return eggThrown;
	}

	public int getEnderpearlThrown() {
		return enderpearlThrown;
	}

	public int getLabCoins() {
		return labCoins;
	}

	public int getLabWins() {
		return labWins;
	}

	public int getLabGames() {
		return labGames;
	}

	public int getLabWinstreak() {
		return labWinstreak;
	}

	public int getLabLosses() {
		return labLosses;
	}

	public int getLabKills() {
		return labKills;
	}

	public int getLabDeaths() {
		return labDeaths;
	}

	public int getLabQuits() {
		return labQuits;
	}

	public int getLabTimePlayed() {
		return labTimePlayed;
	}

	public int getLabSurvivedPlayers() {
		return labSurvivedPlayers;
	}

	public int getLabSoulsGathered() {
		return labSoulsGathered;
	}

	public int getLabChestsOpened() {
		return labChestsOpened;
	}

	public int getLabAssists() {
		return labAssists;
	}

	public int getLabFastestWin() {
		return labFastestWin;
	}

	public int getLabBlocksPlaced() {
		return labBlocksPlaced;
	}

	public int getLabBlocksBroken() {
		return labBlocksBroken;
	}

	public int getLabArrowsShot() {
		return labArrowsShot;
	}

	public int getLabArrowsHit() {
		return labArrowsHit;
	}

	public int getLabEggThrown() {
		return labEggThrown;
	}

	public int getLabEnderpearlThrown() {
		return labEnderpearlThrown;
	}

	public int getMegaWins() {
		return megaWins;
	}

	public int getMegaLosses() {
		return megaLosses;
	}

	public int getMegaKills() {
		return megaKills;
	}

	public int getMegaDeaths() {
		return megaDeaths;
	}

	public int getMegaTimePlayed() {
		return megaTimePlayed;
	}

	public int getMegaArrowsShot() {
		return megaArrowsShot;
	}

	public int getMegaArrowsHit() {
		return megaArrowsHit;
	}

	public int getMegaSurvivedPlayers() {
		return megaSurvivedPlayers;
	}

	public int getMegaLongestBowKill() {
		return megaLongestBowKill;
	}

	public int getMegaChestsOpened() {
		return megaChestsOpened;
	}

	public int getHeads() {
		return heads;
	}

	public int getHeadsTasty() {
		return headsTasty;
	}

	public int getHeadsDecent() {
		return headsDecent;
	}

	public int getHeadsMeh() {
		return headsMeh;
	}

	public int getHeadsSucculent() {
		return headsSucculent;
	}

	public int getHeadsEww() {
		return headsEww;
	}

	public int getHeadsSalty() {
		return headsSalty;
	}

	public int getHeadsYucky() {
		return headsYucky;
	}

	public int getHeadsDivine() {
		return headsDivine;
	}

	public int getHeadsHeavenly() {
		return headsHeavenly;
	}

	public int getRankedWins() {
		return rankedWins;
	}

	public int getFastestWinRanked() {
		return fastestWinRanked;
	}

	public int getRankedLosses() {
		return rankedLosses;
	}

	public int getRankedGames() {
		return rankedGames;
	}

	public int getRankedKills() {
		return rankedKills;
	}

	public int getRankedKillstreak() {
		return rankedKillstreak;
	}

	public int getRankedDeaths() {
		return rankedDeaths;
	}

	public int getRankedAssists() {
		return rankedAssists;
	}

	public int getRankedTimePlayed() {
		return rankedTimePlayed;
	}

	public int getRankedArrowsShot() {
		return rankedArrowsShot;
	}

	public int getRankedArrowsHit() {
		return rankedArrowsHit;
	}

	public int getRankedSurvivedPlayers() {
		return rankedSurvivedPlayers;
	}

	public int getRankedLongestBowKill() {
		return rankedLongestBowKill;
	}

	public int getRankedChestsOpened() {
		return rankedChestsOpened;
	}

	public int getRankedShards() {
		return rankedShards;
	}
}
