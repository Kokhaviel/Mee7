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
import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.guild.GuildStats;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.hypixel.recent.RecentGames;
import fr.kokhaviel.api.hypixel.server.bans.Bans;
import fr.kokhaviel.api.hypixel.server.count.PlayerCount;
import fr.kokhaviel.api.hypixel.status.Status;
import fr.kokhaviel.api.mojang.MojangUUID;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;

import java.net.MalformedURLException;
import java.net.URL;

public class HypixelAPI {

	Gson gson = new Gson();

	public PlayerData getData(String player) throws MalformedURLException {
		String baseHypixelUrl = "https://api.hypixel.net/player?uuid=";
		String hypixelURL = baseHypixelUrl + getMojangUUID(player) + "&key=" + Config.HYPIXEL_API_KEY;

		JsonObject hypixelObject = JsonUtilities.readJson(new URL(hypixelURL)).getAsJsonObject();

		return gson.fromJson(hypixelObject, PlayerData.class);
	}

	public RecentGames getRecentGames(String player) throws MalformedURLException {
		String baseHypixelUrl = "https://api.hypixel.net/recentgames?uuid=";
		String hypixelURL = baseHypixelUrl + getMojangUUID(player) + "&key=" + Config.HYPIXEL_API_KEY;

		JsonObject hypixelObject = JsonUtilities.readJson(new URL(hypixelURL)).getAsJsonObject();

		return gson.fromJson(hypixelObject, RecentGames.class);
	}

	public Status getStatus(String player) throws MalformedURLException {
		String baseHypixelUrl = "https://api.hypixel.net/status?uuid=";
		String hypixelUrl = baseHypixelUrl + getMojangUUID(player) + "&key=" + Config.HYPIXEL_API_KEY;

		JsonObject hypixelObject = JsonUtilities.readJson(new URL(hypixelUrl)).getAsJsonObject();

		return gson.fromJson(hypixelObject, Status.class);
	}

	public GuildStats getGuildStats(String player) throws MalformedURLException {
		String baseHypixelUrl = "https://api.hypixel.net/guild?player=";
		String hypixelUrl = baseHypixelUrl + getMojangUUID(player) + "&key=" + Config.HYPIXEL_API_KEY;

		JsonObject hypixelObject = JsonUtilities.readJson(new URL(hypixelUrl)).getAsJsonObject();

		return gson.fromJson(hypixelObject, GuildStats.class);
	}

	public PlayerCount getPlayerCount() throws MalformedURLException {
		String hypixelUrl = "https://api.hypixel.net/counts?key=" + Config.HYPIXEL_API_KEY;

		JsonObject hypixelObject = JsonUtilities.readJson(new URL(hypixelUrl)).getAsJsonObject();

		return gson.fromJson(hypixelObject, PlayerCount.class);
	}

	public Bans getBans() throws MalformedURLException {
		String hypixelUrl = "https://api.hypixel.net/punishmentstats?key=" + Config.HYPIXEL_API_KEY;

		JsonObject hypixelObject = JsonUtilities.readJson(new URL(hypixelUrl)).getAsJsonObject();

		return gson.fromJson(hypixelObject, Bans.class);
	}

	private String getMojangUUID(String player) throws MalformedURLException {
		String baseMojangUrl = "https://api.mojang.com/users/profiles/minecraft/";
		String mojangUrl = baseMojangUrl + player;

		JsonObject mojangFile = JsonUtilities.readJson(new URL(mojangUrl)).getAsJsonObject();
		MojangUUID mojangUUID = gson.fromJson(mojangFile, MojangUUID.class);
		return mojangUUID.getId();
	}
}
