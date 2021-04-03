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

public class SocialMedias {

	@SerializedName("links")
	Links links = new Links();

	public Links getLinks() {
		return links;
	}
}

class Links {

	@SerializedName("TWITTER")
	String twitter = "";

	@SerializedName("YOUTUBE")
	String youtube = "";

	@SerializedName("INSTAGRAM")
	String instagram = "";

	@SerializedName("TWITCH")
	String twitch = "";

	@SerializedName("DISCORD")
	String discord = "";

	@SerializedName("HYPIXEL")
	String hypixel = "";

	public String getTwitter() {
		return twitter;
	}

	public String getYoutube() {
		return youtube;
	}

	public String getInstagram() {
		return instagram;
	}

	public String getTwitch() {
		return twitch;
	}

	public String getDiscord() {
		return discord;
	}

	public String getHypixel() {
		return hypixel;
	}
}
