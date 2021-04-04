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

public class Bedwars {

	@SerializedName("Experience")
	int experience = 0;

	@SerializedName("final_deaths_bedwars")
	int finalDeaths = 0;

	@SerializedName("resources_collected_bedwars")
	int resourcesCollected = 0;

	@SerializedName("iron_resources_collected_bedwars")
	int ironCollected = 0;

	@SerializedName("gold_resources_collected_bedwars")
	int goldCollected = 0;

	@SerializedName("diamond_resources_collected_bedwars")
	int diamondCollected = 0;

	@SerializedName("emerald_resources_collected_bedwars")
	int emeraldCollected = 0;

	@SerializedName("beds_lost_bedwars")
	int bedsLost = 0;

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("losses_bedwars")
	int losses = 0;

	@SerializedName("games_played_bedwars")
	int gamesPlayed = 0;

	@SerializedName("deaths_bedwars")
	int deaths = 0;

	@SerializedName("void_deaths_bedwars")
	int voidDeaths = 0;

	@SerializedName("void_final_deaths_bedwars")
	int voidFinalDeaths = 0;

	@SerializedName("items_purchased_bedwars")
	int itemsPurchased = 0;

	@SerializedName("activeDeathCry")
	String deathCry = "";

	@SerializedName("kills_bedwars")
	int kills = 0;

	@SerializedName("void_kills_bedwars")
	int voidKills = 0;

	@SerializedName("final_kills_bedwars")
	int finalKills = 0;

	@SerializedName("beds_broken_bedwars")
	int bedsBroken =0;

	@SerializedName("winstreak")
	int winstreak = 0;

	@SerializedName("wins_bedwars")
	int wins = 0;

	@SerializedName("void_final_kills_bedwars")
	int voidFinalKills = 0;

	@SerializedName("fall_final_deaths_bedwars")
	int fallFinalDeaths = 0;

	@SerializedName("fall_deaths_bedwars")
	int fallDeaths = 0;

	@SerializedName("fall_kills_bedwars")
	int fallKills = 0;

	@SerializedName("projectile_deaths_bedwars")
	int projectileDeaths = 0;

	@SerializedName("projectile_kills_bedwars")
	int projectileKills = 0;

	@SerializedName("magic_deaths_bedwars")
	int magicDeaths = 0;

	@SerializedName("magic_final_deaths_bedwars")
	int magicFinalDeaths = 0;

	@SerializedName("magic_kills_bedwars")
	int magicKills = 0;

	@SerializedName("magic_final_kills_bedwars")
	int magicFinalKills = 0;

	@SerializedName("fall_final_kills_bedwars")
	int fallFinalKills = 0;

	@SerializedName("activeSprays")
	String spray = "";

	@SerializedName("activeKillEffect")
	String killEffect = "";

	@SerializedName("activeKillMessages")
	String killMessage = "";

	@SerializedName("activeVictoryDance")
	String victoryDance = "";

	@SerializedName("activeGlyph")
	String glyph = "";

	@SerializedName("activeProjectileTrail")
	String projectileTrail = "";

	@SerializedName("practice")
	Practice practice = new Practice();


	public int getExperience() {
		return experience;
	}

	public int getFinalDeaths() {
		return finalDeaths;
	}

	public int getResourcesCollected() {
		return resourcesCollected;
	}

	public int getIronCollected() {
		return ironCollected;
	}

	public int getGoldCollected() {
		return goldCollected;
	}

	public int getDiamondCollected() {
		return diamondCollected;
	}

	public int getEmeraldCollected() {
		return emeraldCollected;
	}

	public int getBedsLost() {
		return bedsLost;
	}

	public int getCoins() {
		return coins;
	}

	public int getLosses() {
		return losses;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getVoidDeaths() {
		return voidDeaths;
	}

	public int getVoidFinalDeaths() {
		return voidFinalDeaths;
	}

	public int getItemsPurchased() {
		return itemsPurchased;
	}

	public String getDeathCry() {
		return deathCry;
	}

	public int getKills() {
		return kills;
	}

	public int getVoidKills() {
		return voidKills;
	}

	public int getFinalKills() {
		return finalKills;
	}

	public int getBedsBroken() {
		return bedsBroken;
	}

	public int getWinstreak() {
		return winstreak;
	}

	public int getWins() {
		return wins;
	}

	public int getVoidFinalKills() {
		return voidFinalKills;
	}

	public int getFallFinalDeaths() {
		return fallFinalDeaths;
	}

	public int getFallDeaths() {
		return fallDeaths;
	}

	public int getFallKills() {
		return fallKills;
	}

	public int getProjectileDeaths() {
		return projectileDeaths;
	}

	public int getProjectileKills() {
		return projectileKills;
	}

	public int getMagicDeaths() {
		return magicDeaths;
	}

	public int getMagicFinalDeaths() {
		return magicFinalDeaths;
	}

	public int getMagicKills() {
		return magicKills;
	}

	public int getMagicFinalKills() {
		return magicFinalKills;
	}

	public int getFallFinalKills() {
		return fallFinalKills;
	}

	public String getSpray() {
		return spray;
	}

	public String getKillEffect() {
		return killEffect;
	}

	public String getKillMessage() {
		return killMessage;
	}

	public String getVictoryDance() {
		return victoryDance;
	}

	public String getGlyph() {
		return glyph;
	}

	public String getProjectileTrail() {
		return projectileTrail;
	}

	public Practice getPractice() {
		return practice;
	}
}
