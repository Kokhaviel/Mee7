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

public class TrueCombat {

	//Database Name : TrueCombat
	//Clean Name : Crazy Walls

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("golden_skulls")
	int goldenSkulls = 0;

	@SerializedName("items_enchanted")
	int itemsEnchanted = 0;

	@SerializedName("arrows_shot")
	int arrowsShot = 0;

	@SerializedName("arrows_hit")
	int arrowsHit = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("survived_players")
	int survivedPlayers = 0;

	@SerializedName("skulls_gathered")
	int skullsGathered = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("gold_dust")
	int goldDust = 0;

	@SerializedName("win_streak")
	int winStreak = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("games")
	int games = 0;

	@SerializedName("deaths")
	int deaths = 0;


	public int getCoins() {
		return coins;
	}

	public int getGoldenSkulls() {
		return goldenSkulls;
	}

	public int getItemsEnchanted() {
		return itemsEnchanted;
	}

	public int getArrowsShot() {
		return arrowsShot;
	}

	public int getArrowsHit() {
		return arrowsHit;
	}

	public int getKills() {
		return kills;
	}

	public int getSurvivedPlayers() {
		return survivedPlayers;
	}

	public int getSkullsGathered() {
		return skullsGathered;
	}

	public int getWins() {
		return wins;
	}

	public int getGoldDust() {
		return goldDust;
	}

	public int getWinStreak() {
		return winStreak;
	}

	public int getLosses() {
		return losses;
	}

	public int getGames() {
		return games;
	}

	public int getDeaths() {
		return deaths;
	}
}
