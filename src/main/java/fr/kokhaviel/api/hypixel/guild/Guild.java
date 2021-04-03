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

package fr.kokhaviel.api.hypixel.guild;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Guild {

	@SerializedName("name")
	String name = "";

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("coinsEver")
	int coinsEver = 0;

	@SerializedName("created")
	long created = 0;

	@SerializedName("exp")
	int exp = 0;

	@SerializedName("description")
	String description = "";

	@SerializedName("tag")
	String tag = "";

	@SerializedName("tagColor")
	String tagColor = "";

	@SerializedName("ranks")
	List<Ranks> ranks = new ArrayList<>();

	@SerializedName("guildExpByGameType")
	GuildExp guildExp = new GuildExp();


	public String getName() {
		return name;
	}

	public int getCoins() {
		return coins;
	}

	public int getCoinsEver() {
		return coinsEver;
	}

	public long getCreated() {
		return created;
	}

	public int getExp() {
		return exp;
	}

	public String getDescription() {
		return description;
	}

	public String getTag() {
		return tag;
	}

	public String getTagColor() {
		return tagColor;
	}

	public List<Ranks> getRanks() {
		return ranks;
	}

	public GuildExp getGuildExp() {
		return guildExp;
	}
}
