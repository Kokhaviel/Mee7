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

public class Arena {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("offensive")
	String offensive = "";

	@SerializedName("utility")
	String utility = "";

	@SerializedName("support")
	String support = "";

	@SerializedName("ultimate")
	String ultimate = "";

	@SerializedName("active_rune")
	String activeRune = "";

	@SerializedName("selected_sword")
	String selectedSword = "";

	@SerializedName("rune_level_energy")
	int runeLevelEnergy = 0;

	@SerializedName("lvl_damage")
	int levelDamage = 0;

	@SerializedName("lvl_health")
	int levelHealth = 0;

	@SerializedName("lvl_energy")
	int levelEnergy = 0;

	@SerializedName("lvl_cooldown")
	int levelCooldown = 0;

	@SerializedName("keys")
	int keys = 0;

	@SerializedName("magical_chest")
	int magicalChest = 0;


	public int getCoins() {
		return coins;
	}

	public String getOffensive() {
		return offensive;
	}

	public String getUtility() {
		return utility;
	}

	public String getSupport() {
		return support;
	}

	public String getUltimate() {
		return ultimate;
	}

	public String getActiveRune() {
		return activeRune;
	}

	public String getSelectedSword() {
		return selectedSword;
	}

	public int getRuneLevelEnergy() {
		return runeLevelEnergy;
	}

	public int getLevelDamage() {
		return levelDamage;
	}

	public int getLevelHealth() {
		return levelHealth;
	}

	public int getLevelEnergy() {
		return levelEnergy;
	}

	public int getLevelCooldown() {
		return levelCooldown;
	}

	public int getKeys() {
		return keys;
	}

	public int getMagicalChest() {
		return magicalChest;
	}
}
