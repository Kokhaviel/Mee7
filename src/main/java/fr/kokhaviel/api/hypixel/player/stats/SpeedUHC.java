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

public class SpeedUHC {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("score")
	int score = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("winstreak")
	int winstreak = 0;
	
	@SerializedName("highestWinstreak")
	int highestWinstreak = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("games")
	int games = 0;

	@SerializedName("quits")
	int quits = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("assists")
	int assists = 0;

	@SerializedName("killstreak")
	int killstreak = 0;

	@SerializedName("blocks_placed")
	int blocksPlaced = 0;

	@SerializedName("blocks_broken")
	int blocksBroken = 0;

	@SerializedName("survived_players")
	int survivedPlayers = 0;

	@SerializedName("items_enchanted")
	int enchantedItems = 0;


	public int getCoins() {
		return coins;
	}

	public int getScore() {
		return score;
	}

	public int getWins() {
		return wins;
	}

	public int getWinstreak() {
		return winstreak;
	}

	public int getHighestWinstreak() {
		return highestWinstreak;
	}

	public int getLosses() {
		return losses;
	}

	public int getGames() {
		return games;
	}

	public int getQuits() {
		return quits;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getAssists() {
		return assists;
	}

	public int getKillstreak() {
		return killstreak;
	}

	public int getBlocksPlaced() {
		return blocksPlaced;
	}

	public int getBlocksBroken() {
		return blocksBroken;
	}

	public int getSurvivedPlayers() {
		return survivedPlayers;
	}

	public int getEnchantedItems() {
		return enchantedItems;
	}
}
