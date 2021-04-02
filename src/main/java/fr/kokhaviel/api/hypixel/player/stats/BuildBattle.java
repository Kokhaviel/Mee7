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

public class BuildBattle {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("games_played")
	int gamesPlayed = 0;

	@SerializedName("score")
	int score = 0;

	@SerializedName("total_votes")
	int totalVotes = 0;

	@SerializedName("solo_most_points")
	int soloMostPoints = 0;

	@SerializedName("teams_most_points")
	int teamMostPoints = 0;


	public int getCoins() {
		return coins;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getScore() {
		return score;
	}

	public int getTotalVotes() {
		return totalVotes;
	}

	public int getSoloMostPoints() {
		return soloMostPoints;
	}

	public int getTeamMostPoints() {
		return teamMostPoints;
	}
}
