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

public class UHC {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("equippedKit")
	String equippedKit = "";

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("score")
	int score = 0;

	@SerializedName("heads_eaten")
	int headsEaten = 0;


	public int getCoins() {
		return coins;
	}

	public String getEquippedKit() {
		return equippedKit;
	}

	public int getWins() {
		return wins;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getScore() {
		return score;
	}

	public int getHeadsEaten() {
		return headsEaten;
	}
}
