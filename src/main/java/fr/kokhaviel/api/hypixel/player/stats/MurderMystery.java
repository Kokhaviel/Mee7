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

public class MurderMystery {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("coins_pickedup")
	int coinsPickedUp = 0;

	@SerializedName("games")
	int games = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("murderer_wins")
	int winsMurderer = 0;

	@SerializedName("detective_wins")
	int winsDetective = 0;

	@SerializedName("was_hero")
	int winsHero = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("detective_chance")
	int detectiveChance = 0;

	@SerializedName("bow_kills")
	int bowKills = 0;

	@SerializedName("trap_kills")
	int trapKills = 0;

	@SerializedName("longest_time_as_survivor")
	int longestTimeSurvivor = 0;

	@SerializedName("murder_chance")
	int murderChance = 0;

	@SerializedName("knife_kills")
	int knifeKills = 0;

	@SerializedName("thrown_knife_kills")
	int thrownKnifeKills = 0;


	public int getCoins() {
		return coins;
	}

	public int getCoinsPickedUp() {
		return coinsPickedUp;
	}

	public int getGames() {
		return games;
	}

	public int getWins() {
		return wins;
	}

	public int getWinsMurderer() {
		return winsMurderer;
	}

	public int getWinsDetective() {
		return winsDetective;
	}

	public int getWinsHero() {
		return winsHero;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getDetectiveChance() {
		return detectiveChance;
	}

	public int getBowKills() {
		return bowKills;
	}

	public int getTrapKills() {
		return trapKills;
	}

	public int getLongestTimeSurvivor() {
		return longestTimeSurvivor;
	}

	public int getMurderChance() {
		return murderChance;
	}

	public int getKnifeKills() {
		return knifeKills;
	}

	public int getThrownKnifeKills() {
		return thrownKnifeKills;
	}
}
