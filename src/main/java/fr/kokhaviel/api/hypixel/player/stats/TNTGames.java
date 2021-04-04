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

public class TNTGames {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("winstreak")
	int winstreak = 0;

	@SerializedName("kills_pvprun")
	int pvprunKills = 0;

	@SerializedName("kills_tntag")
	int tnttagKills = 0;

	@SerializedName("deaths_bowspleef")
	int bowspleefDeaths = 0;

	@SerializedName("deaths_tntrun")
	int tntrunDeaths = 0;

	@SerializedName("deaths_pvprun")
	int pvprunDeaths = 0;

	@SerializedName("record_tntrun")
	int tnttunRecord = 0;

	@SerializedName("record_pvprun")
	int pvprunRecord = 0;

	@SerializedName("run_potions_splashed_on_players")
	int potionsSplashed = 0;

	@SerializedName("tags_bowspleef")
	int bowspleefTags = 0;


	public int getCoins() {
		return coins;
	}

	public int getWins() {
		return wins;
	}

	public int getWinstreak() {
		return winstreak;
	}

	public int getPvprunKills() {
		return pvprunKills;
	}

	public int getTnttagKills() {
		return tnttagKills;
	}

	public int getBowspleefDeaths() {
		return bowspleefDeaths;
	}

	public int getTntrunDeaths() {
		return tntrunDeaths;
	}

	public int getPvprunDeaths() {
		return pvprunDeaths;
	}

	public int getTnttunRecord() {
		return tnttunRecord;
	}

	public int getPvprunRecord() {
		return pvprunRecord;
	}

	public int getPotionsSplashed() {
		return potionsSplashed;
	}

	public int getBowspleefTags() {
		return bowspleefTags;
	}
}
