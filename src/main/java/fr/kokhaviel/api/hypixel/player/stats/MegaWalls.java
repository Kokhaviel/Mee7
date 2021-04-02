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

public class MegaWalls {

	//Database Name : Walls3
	//Clean Name : Mega Walls

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("chosen_class")
	String chosenClass = "";

	@SerializedName("total_kills")
	int kills = 0;

	@SerializedName("total_deaths")
	int deaths = 0;

	@SerializedName("games_played")
	int gamesPlayed = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("assists")
	int assists = 0;

	@SerializedName("time_played")
	int timePlayed = 0;

	@SerializedName("activations")
	int activations = 0;

	@SerializedName("amount_healed")
	int heal = 0;

	@SerializedName("meters_fallen")
	int fallen = 0;

	@SerializedName("meters_walked")
	int walked = 0;

	@SerializedName("blocks_broken")
	int blocksBroken = 0;

	@SerializedName("blocks_placed")
	int blocksPlaced = 0;

	@SerializedName("iron_ore_broken")
	int ironBroken = 0;

	@SerializedName("treasures_found")
	int treasuresFound = 0;

	@SerializedName("wood_chopped")
	int woodChopped = 0;


	public int getCoins() {
		return coins;
	}

	public String getChosenClass() {
		return chosenClass;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getLosses() {
		return losses;
	}

	public int getAssists() {
		return assists;
	}

	public int getTimePlayed() {
		return timePlayed;
	}

	public int getActivations() {
		return activations;
	}

	public int getHeal() {
		return heal;
	}

	public int getFallen() {
		return fallen;
	}

	public int getWalked() {
		return walked;
	}

	public int getBlocksBroken() {
		return blocksBroken;
	}

	public int getBlocksPlaced() {
		return blocksPlaced;
	}

	public int getIronBroken() {
		return ironBroken;
	}

	public int getTreasuresFound() {
		return treasuresFound;
	}

	public int getWoodChopped() {
		return woodChopped;
	}
}
