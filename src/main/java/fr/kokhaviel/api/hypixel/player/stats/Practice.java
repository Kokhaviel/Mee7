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

public class Practice {

	@SerializedName("selected")
	String practiceSelected = "";

	@SerializedName("bridging")
	Bridging bridging = new Bridging();

	@SerializedName("mlg")
	MLG mlg = new MLG();

	@SerializedName("fireball_jumping")
	FireBall fireBall = new FireBall();


	public String getPracticeSelected() {
		return practiceSelected;
	}

	public Bridging getBridging() {
		return bridging;
	}

	public MLG getMlg() {
		return mlg;
	}

	public FireBall getFireBall() {
		return fireBall;
	}
}

class Bridging {

	@SerializedName("successful_attempts")
	int successAttempts = 0;

	@SerializedName("failed_attempts")
	int failAttempts = 0;

	@SerializedName("blocks_placed")
	int blocksPlaced = 0;


	public int getSuccessAttempts() {
		return successAttempts;
	}

	public int getFailAttempts() {
		return failAttempts;
	}

	public int getBlocksPlaced() {
		return blocksPlaced;
	}
}

class MLG {

	@SerializedName("successful_attempts")
	int successAttempts = 0;

	@SerializedName("failed_attempts")
	int failAttempts = 0;


	public int getSuccessAttempts() {
		return successAttempts;
	}

	public int getFailAttempts() {
		return failAttempts;
	}
}

class FireBall {

	@SerializedName("successful_attempts")
	int successAttempts = 0;

	@SerializedName("failed_attempts")
	int failAttempts = 0;


	public int getSuccessAttempts() {
		return successAttempts;
	}

	public int getFailAttempts() {
		return failAttempts;
	}
}
