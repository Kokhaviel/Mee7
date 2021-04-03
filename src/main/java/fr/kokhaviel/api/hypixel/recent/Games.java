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

package fr.kokhaviel.api.hypixel.recent;

import com.google.gson.annotations.SerializedName;

public class Games {

	@SerializedName("date")
	int date = 0;

	@SerializedName("gameType")
	String game = "";

	@SerializedName("mode")
	String mode = "";

	@SerializedName("map")
	String map = "";

	public int getDate() {
		return date;
	}

	public String getGame() {
		return game;
	}

	public String getMode() {
		return mode;
	}

	public String getMap() {
		return map;
	}
}
