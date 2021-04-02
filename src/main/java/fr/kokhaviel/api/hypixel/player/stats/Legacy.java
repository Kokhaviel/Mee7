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

public class Legacy {

	//Database Name : Legacy
	//Clean Name : Classic Games

	@SerializedName("tokens")
	int tokens = 0;

	@SerializedName("total_tokens")
	int totalTokens = 0;

	@SerializedName("vampirez_tokens")
	int vampireZTokens = 0;

	@SerializedName("paintball_tokens")
	int paintballTokens = 0;

	@SerializedName("gingerbread_tokens")
	int gingerBreadTokens = 0;

	@SerializedName("walls_tokens")
	int wallsTokens = 0;

	@SerializedName("arena_tokens")
	int arenaTokens = 0;


	public int getTokens() {
		return tokens;
	}

	public int getTotalTokens() {
		return totalTokens;
	}

	public int getVampireZTokens() {
		return vampireZTokens;
	}

	public int getPaintballTokens() {
		return paintballTokens;
	}

	public int getGingerBreadTokens() {
		return gingerBreadTokens;
	}

	public int getWallsTokens() {
		return wallsTokens;
	}

	public int getArenaTokens() {
		return arenaTokens;
	}
}
