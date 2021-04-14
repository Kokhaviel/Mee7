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

package fr.kokhaviel.api.hypixel.status;

import com.google.gson.annotations.SerializedName;

public class Status {

	@SerializedName("success")
	boolean success = false;

	@SerializedName("cause")
	String cause = "";

	@SerializedName("uuid")
	String uuid = "";

	@SerializedName("session")
	Session session = new Session();


	public boolean isSuccess() {
		return success;
	}

	public String getCause() {
		return cause;
	}

	public String getUuid() {
		return uuid;
	}

	public Session getSession() {
		return session;
	}
}