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

package fr.kokhaviel.api.hypixel.player;

import com.google.gson.annotations.SerializedName;
import fr.kokhaviel.api.hypixel.player.stats.*;

public class Stats {

	@SerializedName("Arcade")
	Arcade arcade = new Arcade();

	@SerializedName("Arena")
	Arena arena = new Arena();

	@SerializedName("Battleground")
	BattleGround battleGround = new BattleGround();

	@SerializedName("Bedwars")
	Bedwars bedwars = new Bedwars();

	@SerializedName("BuildBattle")
	BuildBattle buildBattle = new BuildBattle();

	@SerializedName("Duels")
	Duels duels = new Duels();

	@SerializedName("GingerBread")
	GingerBread gingerBread = new GingerBread();

	@SerializedName("HungerGames")
	HungerGames hungerGames = new HungerGames();

	@SerializedName("Legacy")
	Legacy legacy = new Legacy();

	@SerializedName("MCGO")
	McGo mcgo = new McGo();

	@SerializedName("MurderMystery")
	MurderMystery murderMystery = new MurderMystery();

	@SerializedName("Paintball")
	Paintball paintball = new Paintball();

	@SerializedName("Pit")
	Pit pit = new Pit();

	@SerializedName("Quake")
	Quake quake = new Quake();

	@SerializedName("SkyClash")
	SkyClash skyClash = new SkyClash();

	@SerializedName("Skywars")
	Skywars skywars = new Skywars();

	@SerializedName("SuperSmash")
	Smash smash = new Smash();

	@SerializedName("SpeedUHC")
	SpeedUHC speedUHC = new SpeedUHC();

	@SerializedName("TNTGames")
	TNTGames tntGames = new TNTGames();

	@SerializedName("TrueCombat")
	TrueCombat trueCombat = new TrueCombat();

	@SerializedName("UHC")
	UHC uhc = new UHC();

	@SerializedName("VampireZ")
	VampireZ vampireZ = new VampireZ();

	@SerializedName("Walls")
	Walls walls = new Walls();

	@SerializedName("Walls3")
	MegaWalls megaWalls = new MegaWalls();

	public Arcade getArcade() {
		return arcade;
	}

	public Arena getArena() {
		return arena;
	}

	public BattleGround getBattleGround() {
		return battleGround;
	}

	public Bedwars getBedwars() {
		return bedwars;
	}

	public BuildBattle getBuildBattle() {
		return buildBattle;
	}

	public Duels getDuels() {
		return duels;
	}

	public GingerBread getGingerBread() {
		return gingerBread;
	}

	public HungerGames getHungerGames() {
		return hungerGames;
	}

	public Legacy getLegacy() {
		return legacy;
	}

	public McGo getMcgo() {
		return mcgo;
	}

	public MurderMystery getMurderMystery() {
		return murderMystery;
	}

	public Paintball getPaintball() {
		return paintball;
	}

	public Pit getPit() {
		return pit;
	}

	public Quake getQuake() {
		return quake;
	}

	public SkyClash getSkyClash() {
		return skyClash;
	}

	public Skywars getSkywars() {
		return skywars;
	}

	public Smash getSmash() {
		return smash;
	}

	public SpeedUHC getSpeedUHC() {
		return speedUHC;
	}

	public TNTGames getTntGames() {
		return tntGames;
	}

	public TrueCombat getTrueCombat() {
		return trueCombat;
	}

	public UHC getUhc() {
		return uhc;
	}

	public VampireZ getVampireZ() {
		return vampireZ;
	}

	public Walls getWalls() {
		return walls;
	}

	public MegaWalls getMegaWalls() {
		return megaWalls;
	}
}
