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

public class Smash {

	//Database Name : SuperSmash
	//Clean Name : Smash Heroes

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("win_streak")
	int winstreak = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("games")
	int games = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("quits")
	int quits = 0;

	@SerializedName("active_class")
	String activeClass = "";

	@SerializedName("damage_dealt")
	int damageDealt = 0;

	@SerializedName("smasher")
	int smasher = 0;

	@SerializedName("smashed")
	int smashed = 0;

	@SerializedName("smashLevel")
	int smashLevel = 0;


	public int getCoins() {
		return coins;
	}

	public int getWins() {
		return wins;
	}

	public int getWinstreak() {
		return winstreak;
	}

	public int getLosses() {
		return losses;
	}

	public int getGames() {
		return games;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getQuits() {
		return quits;
	}

	public String getActiveClass() {
		return activeClass;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public int getSmasher() {
		return smasher;
	}

	public int getSmashed() {
		return smashed;
	}

	public int getSmashLevel() {
		return smashLevel;
	}
}
