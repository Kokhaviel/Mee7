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

public class BattleGround {

	//Database Name : Battleground
	//Clean Name : Warlord

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("chosen_class")
	String chosenClass = "";

	@SerializedName("win_streak")
	int winStreak = 0;

	@SerializedName("play_streak")
	int playStreak = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("damage")
	int damage = 0;

	@SerializedName("assists")
	int assists = 0;

	@SerializedName("heal")
	int heal = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("repaired")
	int repaired = 0;

	@SerializedName("magic_dust")
	int magicDust = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("void_shards")
	int voidShards = 0;

	@SerializedName("crafted")
	int crafted= 0;


	public int getCoins() {
		return coins;
	}

	public String getChosenClass() {
		return chosenClass;
	}

	public int getWinStreak() {
		return winStreak;
	}

	public int getPlayStreak() {
		return playStreak;
	}

	public int getLosses() {
		return losses;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getDamage() {
		return damage;
	}

	public int getAssists() {
		return assists;
	}

	public int getHeal() {
		return heal;
	}

	public int getKills() {
		return kills;
	}

	public int getRepaired() {
		return repaired;
	}

	public int getMagicDust() {
		return magicDust;
	}

	public int getWins() {
		return wins;
	}

	public int getVoidShards() {
		return voidShards;
	}

	public int getCrafted() {
		return crafted;
	}
}
