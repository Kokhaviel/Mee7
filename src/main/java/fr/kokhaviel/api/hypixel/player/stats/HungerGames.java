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

public class HungerGames {

	//Database Name : HungerGames
	//Clean Name : Blitz Survival Games

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("games_played")
	int gamesPlayed = 0;

	@SerializedName("time_played")
	int timePlayed = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("wins_solo_normal")
	int winsSoloNormal = 0;

	@SerializedName("wins_teams_normal")
	int winsTeamsNormal = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("kills_solo_normal")
	int killsSoloNormal = 0;

	@SerializedName("kills_teams_normal")
	int killsTeamsNormal = 0;

	@SerializedName("damage")
	int damage = 0;

	@SerializedName("damage_taken")
	int damageTaken = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("arrows_fired")
	int arrowsFired = 0;

	@SerializedName("arrows_hit")
	int arrowsHit = 0;

	@SerializedName("chests_opened")
	int openedChests = 0;

	@SerializedName("potions_drunk")
	int potionsDrunk = 0;

	@SerializedName("potions_thrown")
	int potionsThrown = 0;

	@SerializedName("afterkill")
	String afterKill = "";


	public int getCoins() {
		return coins;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getTimePlayed() {
		return timePlayed;
	}

	public int getWins() {
		return wins;
	}

	public int getWinsSoloNormal() {
		return winsSoloNormal;
	}

	public int getWinsTeamsNormal() {
		return winsTeamsNormal;
	}

	public int getKills() {
		return kills;
	}

	public int getKillsSoloNormal() {
		return killsSoloNormal;
	}

	public int getKillsTeamsNormal() {
		return killsTeamsNormal;
	}

	public int getDamage() {
		return damage;
	}

	public int getDamageTaken() {
		return damageTaken;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getArrowsFired() {
		return arrowsFired;
	}

	public int getArrowsHit() {
		return arrowsHit;
	}

	public int getOpenedChests() {
		return openedChests;
	}

	public int getPotionsDrunk() {
		return potionsDrunk;
	}

	public int getPotionsThrown() {
		return potionsThrown;
	}

	public String getAfterKill() {
		return afterKill;
	}
}
