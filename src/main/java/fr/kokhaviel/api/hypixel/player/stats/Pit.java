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
import fr.kokhaviel.api.hypixel.player.stats.pit.PitProfile;
import fr.kokhaviel.api.hypixel.player.stats.pit.PitStats;

public class Pit {

	@SerializedName("profile")
	PitProfile pitProfile = new PitProfile();

	@SerializedName("pit_stats_pt1")
	PitStats pitStats = new PitStats();


	public PitProfile getPitProfile() {
		return pitProfile;
	}

	public PitStats getPitStats() {
		return pitStats;
	}
}

