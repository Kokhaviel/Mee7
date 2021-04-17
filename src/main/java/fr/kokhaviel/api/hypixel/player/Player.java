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

public class Player {

	@SerializedName("uuid")
	String uuid= "";

	@SerializedName("firstLogin")
	long firstLogin = 0;

	@SerializedName("lastLogin")
	long lastLogin = 0;

	@SerializedName("lastLogout")
	long lastLogout = 0;

	@SerializedName("displayname")
	String displayName = "";

	@SerializedName("achievementPoints")
	int achievementPoints = 0;

	@SerializedName("networkExp")
	int networkExperience = 0;

	@SerializedName("karma")
	int karma = 0;

	@SerializedName("mcVersionRp")
	String lastMcVersion = "";

	@SerializedName("newPackageRank")
	String serverRank = "PLAYER";

	@SerializedName("userLanguage")
	String language = "";

	@SerializedName("rewardHighScore")
	int rewardHighScore = 0;

	@SerializedName("rewardScore")
	int rewardScore = 0;

	@SerializedName("rewardStreak")
	int rewardStreak = 0;

	@SerializedName("totalDailyRewards")
	int totalDailyRewards = 0;

	@SerializedName("totalRewards")
	int totalRewards = 0;

	@SerializedName("currentGadget")
	String gadget = "";

	@SerializedName("mostRecentGameType")
	String recentGame = "";

	@SerializedName("stats")
	Stats stats = new Stats();

	@SerializedName("challenges")
	Challenges challenges = new Challenges();

	@SerializedName("achievements")
	Achievements achievements = new Achievements();

	@SerializedName("outfit")
	Outfit outfit = new Outfit();

	@SerializedName("socialMedia")
	SocialMedias socialMedias = new SocialMedias();


	public String getUuid() {
		return uuid;
	}

	public long getFirstLogin() {
		return firstLogin;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public long getLastLogout() {
		return lastLogout;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getAchievementPoints() {
		return achievementPoints;
	}

	public int getNetworkExperience() {
		return networkExperience;
	}

	public int getKarma() {
		return karma;
	}

	public String getLastMcVersion() {
		return lastMcVersion;
	}

	public String getServerRank() {
		return serverRank;
	}

	public String getLanguage() {
		return language;
	}

	public int getRewardHighScore() {
		return rewardHighScore;
	}

	public int getRewardScore() {
		return rewardScore;
	}

	public int getRewardStreak() {
		return rewardStreak;
	}

	public int getTotalDailyRewards() {
		return totalDailyRewards;
	}

	public int getTotalRewards() {
		return totalRewards;
	}

	public String getGadget() {
		return gadget;
	}

	public String getRecentGame() {
		return recentGame;
	}

	public Stats getStats() {
		return stats;
	}

	public Challenges getChallenges() {
		return challenges;
	}

	public Achievements getAchievements() {
		return achievements;
	}

	public Outfit getOutfit() {
		return outfit;
	}

	public SocialMedias getSocialMedias() {
		return socialMedias;
	}
}
