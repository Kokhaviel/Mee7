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

public class Pit {

	@SerializedName("profile")
	PitProfile pitProfile = new PitProfile();

	@SerializedName("pit_stats_pt1")
	PitStats pitStats = new PitStats();


	public PitProfile getPitProfile() {
		return pitProfile;
	}

	public PitStats getPitStats() {
		return pitStats;
	}
}

class PitProfile {

	@SerializedName("cash")
	int cash = 0;

	@SerializedName("xp")
	int xp = 0;


	public int getCash() {
		return cash;
	}

	public int getXp() {
		return xp;
	}
}

class PitStats {

	@SerializedName("joins")
	int joins = 0;

	@SerializedName("playtime_minutes")
	int playedTime = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("assists")
	int assists = 0;

	@SerializedName("max_streak")
	int maxStreak = 0;

	@SerializedName("cash_earned")
	int cashEarned = 0;

	@SerializedName("damage_dealt")
	int damageDealt = 0;

	@SerializedName("damage_received")
	int damageReceived = 0;

	@SerializedName("melee_damage_dealt")
	int meleeDamageDealt = 0;

	@SerializedName("melee_damage_received")
	int meleeDamageReceived = 0;

	@SerializedName("jumped_into_pit")
	int jumpedPit = 0;

	@SerializedName("launched_by_launchers")
	int launchedLaunchers = 0;

	@SerializedName("left_clicks")
	int left_clicks = 0;

	@SerializedName("sword_hits")
	int swordHits = 0;

	@SerializedName("arrow_hits")
	int arrowHits = 0;

	@SerializedName("gapple_eaten")
	int gappleEaten = 0;

	@SerializedName("ghead_eaten")
	int gheadEaten = 0;

	@SerializedName("lava_bucket_emptied")
	int lavaUsed = 0;

	@SerializedName("blocks_placed")
	int blocksPlaced = 0;


	public int getJoins() {
		return joins;
	}

	public int getPlayedTime() {
		return playedTime;
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

	public int getMaxStreak() {
		return maxStreak;
	}

	public int getCashEarned() {
		return cashEarned;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public int getDamageReceived() {
		return damageReceived;
	}

	public int getMeleeDamageDealt() {
		return meleeDamageDealt;
	}

	public int getMeleeDamageReceived() {
		return meleeDamageReceived;
	}

	public int getJumpedPit() {
		return jumpedPit;
	}

	public int getLaunchedLaunchers() {
		return launchedLaunchers;
	}

	public int getLeft_clicks() {
		return left_clicks;
	}

	public int getSwordHits() {
		return swordHits;
	}

	public int getArrowHits() {
		return arrowHits;
	}

	public int getGappleEaten() {
		return gappleEaten;
	}

	public int getGheadEaten() {
		return gheadEaten;
	}

	public int getLavaUsed() {
		return lavaUsed;
	}

	public int getBlocksPlaced() {
		return blocksPlaced;
	}
}