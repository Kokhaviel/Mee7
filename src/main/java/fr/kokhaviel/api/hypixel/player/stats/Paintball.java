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

public class Paintball {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("shots_fired")
	int shotsFired = 0;

	@SerializedName("killstreak")
	int killstreak = 0;

	@SerializedName("fortune")
	int fortune = 0;

	@SerializedName("hat")
	String hat = "";

	@SerializedName("superluck")
	int superLuck = 0;

	@SerializedName("headstart")
	int headStart = 0;

	@SerializedName("endurance")
	int endurance = 0;

	@SerializedName("godfather")
	int godFather = 0;

	@SerializedName("transfusion")
	int transfusion = 0;

	@SerializedName("adrenaline")
	int adrenaline = 0;


	public int getCoins() {
		return coins;
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

	public int getShotsFired() {
		return shotsFired;
	}

	public int getKillstreak() {
		return killstreak;
	}

	public int getFortune() {
		return fortune;
	}

	public String getHat() {
		return hat;
	}

	public int getSuperLuck() {
		return superLuck;
	}

	public int getHeadStart() {
		return headStart;
	}

	public int getEndurance() {
		return endurance;
	}

	public int getGodFather() {
		return godFather;
	}

	public int getTransfusion() {
		return transfusion;
	}

	public int getAdrenaline() {
		return adrenaline;
	}
}
