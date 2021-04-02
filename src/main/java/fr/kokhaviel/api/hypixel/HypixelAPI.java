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

package fr.kokhaviel.api.hypixel;

import com.google.gson.Gson;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.mojang.MojangUUID;
import fr.kokhaviel.bot.Config;

import java.util.UUID;

public class HypixelAPI {

	Gson gson = new Gson();

	public PlayerData getData(String player) {
		String baseMojangUrl = "https://api.mojang.com/users/profiles/minecraft/";
		String mojangUrl = baseMojangUrl + player;

		MojangUUID mojangUUID = gson.fromJson(mojangUrl, MojangUUID.class);
		String uuid = mojangUUID.getId();

		return getData(UUID.fromString(uuid));
	}


	public PlayerData getData(UUID uuid) {
		String baseHypixelUrl = "https://api.hypixel.net/";
		String hypixelUrl = baseHypixelUrl + uuid + "&key=" + Config.HYPIXEL_API_KEY;

		return gson.fromJson(hypixelUrl, PlayerData.class);
	}
}
