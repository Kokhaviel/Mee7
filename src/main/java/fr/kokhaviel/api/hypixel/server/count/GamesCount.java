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

package fr.kokhaviel.api.hypixel.server.count;

import com.google.gson.annotations.SerializedName;

public class GamesCount {

	@SerializedName("MAIN_LOBBY")
	PlayersPerGames mainLobby = new PlayersPerGames();

	@SerializedName("TOURNAMENT_LOBBY")
	PlayersPerGames tournamentLobby = new PlayersPerGames();

	@SerializedName("MURDER_MYSTERY")
	PlayersPerGames murderMystery = new PlayersPerGames();

	@SerializedName("LEGACY")
	PlayersPerGames legacy = new PlayersPerGames();

	@SerializedName("SURVIVAL_GAMES")
	PlayersPerGames survivalGames = new PlayersPerGames();

	@SerializedName("REPLAY")
	PlayersPerGames replay = new PlayersPerGames();

	@SerializedName("WALLS3")
	PlayersPerGames megaWalls = new PlayersPerGames();

	@SerializedName("ARCADE")
	PlayersPerGames arcade = new PlayersPerGames();

	@SerializedName("BUILD_BATTLE")
	PlayersPerGames buildBattle = new PlayersPerGames();

	@SerializedName("HOUSING")
	PlayersPerGames housing = new PlayersPerGames();

	@SerializedName("PROTOTYPE")
	PlayersPerGames prototype = new PlayersPerGames();

	@SerializedName("SUPER_SMASH")
	PlayersPerGames smash = new PlayersPerGames();

	@SerializedName("SKYWARS")
	PlayersPerGames skywars = new PlayersPerGames();

	@SerializedName("SKYBLOCK")
	PlayersPerGames skyblock = new PlayersPerGames();

	@SerializedName("MCGO")
	PlayersPerGames mcgo = new PlayersPerGames();

	@SerializedName("UHC")
	PlayersPerGames uhc = new PlayersPerGames();

	@SerializedName("BEDWARS")
	PlayersPerGames bedwars = new PlayersPerGames();

	@SerializedName("BATTLEGROUND")
	PlayersPerGames battleground = new PlayersPerGames();

	@SerializedName("PIT")
	PlayersPerGames pit = new PlayersPerGames();

	@SerializedName("SPEED_UHC")
	PlayersPerGames speedUHC = new PlayersPerGames();

	@SerializedName("TNTGAMES")
	PlayersPerGames tntGames = new PlayersPerGames();

	@SerializedName("DUELS")
	PlayersPerGames duels = new PlayersPerGames();

	@SerializedName("LIMBO")
	PlayersPerGames limbo = new PlayersPerGames();

	@SerializedName("IDLE")
	PlayersPerGames idle = new PlayersPerGames();

	@SerializedName("QUEUE")
	PlayersPerGames queue = new PlayersPerGames();


	public PlayersPerGames getMainLobby() {
		return mainLobby;
	}

	public PlayersPerGames getTournamentLobby() {
		return tournamentLobby;
	}

	public PlayersPerGames getMurderMystery() {
		return murderMystery;
	}

	public PlayersPerGames getLegacy() {
		return legacy;
	}

	public PlayersPerGames getSurvivalGames() {
		return survivalGames;
	}

	public PlayersPerGames getReplay() {
		return replay;
	}

	public PlayersPerGames getMegaWalls() {
		return megaWalls;
	}

	public PlayersPerGames getArcade() {
		return arcade;
	}

	public PlayersPerGames getBuildBattle() {
		return buildBattle;
	}

	public PlayersPerGames getHousing() {
		return housing;
	}

	public PlayersPerGames getPrototype() {
		return prototype;
	}

	public PlayersPerGames getSmash() {
		return smash;
	}

	public PlayersPerGames getSkywars() {
		return skywars;
	}

	public PlayersPerGames getSkyblock() {
		return skyblock;
	}

	public PlayersPerGames getMcgo() {
		return mcgo;
	}

	public PlayersPerGames getUhc() {
		return uhc;
	}

	public PlayersPerGames getBedwars() {
		return bedwars;
	}

	public PlayersPerGames getBattleground() {
		return battleground;
	}

	public PlayersPerGames getPit() {
		return pit;
	}

	public PlayersPerGames getSpeedUHC() {
		return speedUHC;
	}

	public PlayersPerGames getTntGames() {
		return tntGames;
	}

	public PlayersPerGames getDuels() {
		return duels;
	}

	public PlayersPerGames getLimbo() {
		return limbo;
	}

	public PlayersPerGames getIdle() {
		return idle;
	}

	public PlayersPerGames getQueue() {
		return queue;
	}
}



