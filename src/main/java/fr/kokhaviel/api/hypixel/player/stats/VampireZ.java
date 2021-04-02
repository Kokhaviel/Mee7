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

public class VampireZ {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("gold_bought")
	int goldBought = 0;

	@SerializedName("human_wins")
	int humanWins = 0;

	@SerializedName("zombie_kills")
	int zombiesKills = 0;

	@SerializedName("vampire_kills")
	int vampireKills = 0;

	@SerializedName("most_vampire_kills")
	int mostVampireKills = 0;

	@SerializedName("human_deaths")
	int humanDeaths = 0;

	@SerializedName("vampire_deaths")
	int vampireDeaths = 0;


	public int getCoins() {
		return coins;
	}

	public int getGoldBought() {
		return goldBought;
	}

	public int getHumanWins() {
		return humanWins;
	}

	public int getZombiesKills() {
		return zombiesKills;
	}

	public int getVampireKills() {
		return vampireKills;
	}

	public int getMostVampireKills() {
		return mostVampireKills;
	}

	public int getHumanDeaths() {
		return humanDeaths;
	}

	public int getVampireDeaths() {
		return vampireDeaths;
	}
}
