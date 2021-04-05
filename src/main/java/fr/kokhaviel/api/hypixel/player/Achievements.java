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

package fr.kokhaviel.api.hypixel.player;

import com.google.gson.annotations.SerializedName;

public class Achievements {

	@SerializedName("bedwars_level")
	int bedwarsLevel = 0;

	@SerializedName("general_coins")
	int generalCoins = 0;

	@SerializedName("general_wins")
	int generalWins = 0;

	@SerializedName("pit_gold")
	int pitGold = 0;

	@SerializedName("pit_kills")
	int pitKills = 0;


	public int getBedwarsLevel() {
		return bedwarsLevel;
	}

	public int getGeneralCoins() {
		return generalCoins;
	}

	public int getGeneralWins() {
		return generalWins;
	}

	public int getPitGold() {
		return pitGold;
	}

	public int getPitKills() {
		return pitKills;
	}
}
