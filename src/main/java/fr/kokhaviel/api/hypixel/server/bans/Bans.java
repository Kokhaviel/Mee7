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

package fr.kokhaviel.api.hypixel.server.bans;

import com.google.gson.annotations.SerializedName;

public class Bans {

	@SerializedName("success")
	boolean success = false;

	@SerializedName("cause")
	String cause = "";

	@SerializedName("watchdog_lastMinute")
	int wdLastMinute = 0;

	@SerializedName("staff_rollingDaily")
	int staffDaily = 0;

	@SerializedName("watchdog_total")
	int wdTotal = 0;

	@SerializedName("watchdog_rollingDaily")
	int wdDaily = 0;

	@SerializedName("staff_total")
	int staffTotal = 0;

	public boolean isSuccess() {
		return success;
	}

	public String getCause() {
		return cause;
	}

	public int getWdLastMinute() {
		return wdLastMinute;
	}

	public int getStaffDaily() {
		return staffDaily;
	}

	public int getWdTotal() {
		return wdTotal;
	}

	public int getWdDaily() {
		return wdDaily;
	}

	public int getStaffTotal() {
		return staffTotal;
	}
}
