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

public class Quake {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("killsound")
	String killSound = "";

	@SerializedName("barrel")
	String barrel = "";

	@SerializedName("case")
	String case2 = "";

	@SerializedName("trigger")
	String trigger = "";

	@SerializedName("sight")
	String sight = "";

	@SerializedName("muzzle")
	String muzzle = "";

	@SerializedName("beam")
	String beam = "";

	@SerializedName("dash_cooldown")
	String dash = "";

	@SerializedName("highest_killstreak")
	int highestKillstreak = 0;

	@SerializedName("shots_fired")
	int shotsFired = 0;

	@SerializedName("distance_travelled")
	int travelled = 0;


	public int getCoins() {
		return coins;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public String getKillSound() {
		return killSound;
	}

	public String getBarrel() {
		return barrel;
	}

	public String getCase() {
		return case2;
	}

	public String getTrigger() {
		return trigger;
	}

	public String getSight() {
		return sight;
	}

	public String getMuzzle() {
		return muzzle;
	}

	public String getBeam() {
		return beam;
	}

	public String getDash() {
		return dash;
	}

	public int getHighestKillstreak() {
		return highestKillstreak;
	}

	public int getShotsFired() {
		return shotsFired;
	}

	public int getTravelled() {
		return travelled;
	}
}
