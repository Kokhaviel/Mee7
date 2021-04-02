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

public class SkyClash {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("win_streak")
	int winstreak = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("void_kills")
	int voidKills = 0;

	@SerializedName("most_kills_game")
	int mostKillsGame = 0;

	@SerializedName("killstreak")
	int killstreak = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("assists")
	int assists = 0;

	@SerializedName("quits")
	int quits = 0;

	@SerializedName("games_played")
	int gamesPlayed = 0;

	@SerializedName("longest_bow_shot")
	int longestBowShot = 0;

	@SerializedName("melee_hits")
	int meleeHits = 0;

	@SerializedName("bow_kills")
	int bowKills = 0;


	public int getCoins() {
		return coins;
	}

	public int getWins() {
		return wins;
	}

	public int getWinstreak() {
		return winstreak;
	}

	public int getLosses() {
		return losses;
	}

	public int getKills() {
		return kills;
	}

	public int getVoidKills() {
		return voidKills;
	}

	public int getMostKillsGame() {
		return mostKillsGame;
	}

	public int getKillstreak() {
		return killstreak;
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

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getLongestBowShot() {
		return longestBowShot;
	}

	public int getMeleeHits() {
		return meleeHits;
	}

	public int getBowKills() {
		return bowKills;
	}
}
