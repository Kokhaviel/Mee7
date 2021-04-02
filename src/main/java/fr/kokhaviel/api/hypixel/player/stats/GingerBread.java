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

public class GingerBread {

	//Database Name : GingerBread
	//Clean Name : Turbo Kart Racer

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("coins_picked_up")
	int coinsPickedUp = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("gold_trophy")
	int goldTrophies = 0;

	@SerializedName("silver_trophy")
	int silverTrophies = 0;

	@SerializedName("bronze_trophy")
	int bronzeTrophies = 0;

	@SerializedName("laps_completed")
	int laps = 0;

	@SerializedName("helmet_active")
	String helmet = "";

	@SerializedName("jacket_active")
	String jacket = "";

	@SerializedName("pants_active")
	String pants = "";

	@SerializedName("shoes_active")
	String shoes = "";

	@SerializedName("box_pickups")
	int boxPickups = 0;


	public int getCoins() {
		return coins;
	}

	public int getCoinsPickedUp() {
		return coinsPickedUp;
	}

	public int getWins() {
		return wins;
	}

	public int getGoldTrophies() {
		return goldTrophies;
	}

	public int getSilverTrophies() {
		return silverTrophies;
	}

	public int getBronzeTrophies() {
		return bronzeTrophies;
	}

	public int getLaps() {
		return laps;
	}

	public String getHelmet() {
		return helmet;
	}

	public String getJacket() {
		return jacket;
	}

	public String getPants() {
		return pants;
	}

	public String getShoes() {
		return shoes;
	}

	public int getBoxPickups() {
		return boxPickups;
	}
}
