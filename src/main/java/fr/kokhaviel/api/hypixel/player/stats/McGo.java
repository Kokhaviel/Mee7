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

public class McGo {

	//Database Name : MCGO
	//Clean Name : Cops and Crims

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("game_wins")
	int wins = 0;

	@SerializedName("games_wins_deathmatch")
	int deathmatchWins = 0;

	@SerializedName("rounds_wins")
	int roundsWins = 0;

	@SerializedName("game_plays")
	int gamesPlayed = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("kills_deathmatch")
	int deathmatchKills = 0;

	@SerializedName("headshots_kills")
	int headshotsKills = 0;

	@SerializedName("cop_kills")
	int copKills = 0;

	@SerializedName("criminal_kills")
	int criminalKills = 0;

	@SerializedName("grenade_kills")
	int grenadeKills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("deaths_deathmatch")
	int deathmatchDeaths = 0;

	@SerializedName("assists")
	int assists = 0;

	@SerializedName("shots_fired")
	int shotsFired = 0;

	@SerializedName("bombs_planted")
	int planted = 0;

	@SerializedName("bombs_defused")
	int defused = 0;


	public int getCoins() {
		return coins;
	}

	public int getWins() {
		return wins;
	}

	public int getDeathmatchWins() {
		return deathmatchWins;
	}

	public int getRoundsWins() {
		return roundsWins;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getKills() {
		return kills;
	}

	public int getDeathmatchKills() {
		return deathmatchKills;
	}

	public int getHeadshotsKills() {
		return headshotsKills;
	}

	public int getCopKills() {
		return copKills;
	}

	public int getCriminalKills() {
		return criminalKills;
	}

	public int getGrenadeKills() {
		return grenadeKills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getDeathmatchDeaths() {
		return deathmatchDeaths;
	}

	public int getAssists() {
		return assists;
	}

	public int getShotsFired() {
		return shotsFired;
	}

	public int getPlanted() {
		return planted;
	}

	public int getDefused() {
		return defused;
	}
}
