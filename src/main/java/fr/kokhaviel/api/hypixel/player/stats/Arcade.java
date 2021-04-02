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

public class Arcade {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("wins_mini_walls")
	int winsMiniWalls = 0;

	@SerializedName("wins_simon_says")
	int winsSimonSays = 0;

	@SerializedName("wins_party")
	int winsParty = 0;

	@SerializedName("wins_dayone")
	int winsDaysone = 0;

	@SerializedName("wins_farm_hunt")
	int winsFarmHunt = 0;

	@SerializedName("wins_hole_in_the_wall")
	int winsHoleWall = 0;

	@SerializedName("wins_soccer")
	int winsSoccer = 0;

	@SerializedName("wins_ender")
	int winsEnder = 0;

	@SerializedName("wins_oneinthequiver")
	int winsOneQuiver = 0;

	@SerializedName("wins_dragonwars")
	int winsDragonWars = 0;

	@SerializedName("wins_zombies")
	int winsZombies = 0;

	@SerializedName("wins_draw_their_thing")
	int winsDrawTheirThings = 0;

	@SerializedName("wins_throw_out")
	int winsThrowOut = 0;

	@SerializedName("wins_grinch_simulator_v2")
	int winsGrinchSimul = 0;


	public int getCoins() {
		return coins;
	}

	public int getWinsMiniWalls() {
		return winsMiniWalls;
	}

	public int getWinsSimonSays() {
		return winsSimonSays;
	}

	public int getWinsParty() {
		return winsParty;
	}

	public int getWinsDaysone() {
		return winsDaysone;
	}

	public int getWinsFarmHunt() {
		return winsFarmHunt;
	}

	public int getWinsHoleWall() {
		return winsHoleWall;
	}

	public int getWinsSoccer() {
		return winsSoccer;
	}

	public int getWinsEnder() {
		return winsEnder;
	}

	public int getWinsOneQuiver() {
		return winsOneQuiver;
	}

	public int getWinsDragonWars() {
		return winsDragonWars;
	}

	public int getWinsZombies() {
		return winsZombies;
	}

	public int getWinsDrawTheirThings() {
		return winsDrawTheirThings;
	}

	public int getWinsThrowOut() {
		return winsThrowOut;
	}

	public int getWinsGrinchSimul() {
		return winsGrinchSimul;
	}
}
