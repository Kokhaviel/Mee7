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

package fr.kokhaviel.bot;

public class ConfigExample {

	// Copy the contents of this file to a new one named Config.java
	// Replace the 'BOT TOKEN', 'HYPIXEL API_KEY', 'FUNCRAFT API_KEY', 'OWNER_ID', 'DEVELOPER_TAG' and 'DEVELOPER_AVATAR' fields with your own

	public static final String
			BOT_TOKEN = "You Bot Token Here",
			// To get a Discord Bot Token, go to https://discord.com/developers/applications to create a new application.
			// Then go to the Settings/Bot tab on the left of the page, and click on 'Add Bot' button. The Token will appear under the Bot username.

			HYPIXEL_API_KEY = "Your Hypixel API Key Here",
			// To get a Hypixel Api Key, you need to connect to the Hypixel Minecraft Server (mc.hypixel.net) and to type the '/api new' command.

			FUNCRAFT_API_KEY = "Your Funcraft API Key Here";
			// To get a Funcraft Api Key, go to https://lordmorgoth.net and create an account with the 'Connexion' button.
			// Then go to your 'Profil' (Top Right of the Page) and send a request to get an api key


	public static final String
			OWNER_ID = "Your owner id",
			// To get your owner id on Discord, turn on the developer mode (Settings -> Advanced -> Developer Mode).
			// Then right click on your profile and click on 'Copy ID'

			DEVELOPER_TAG = "You discord tag Here",
			DEVELOPER_AVATAR = "You discord Avatar Link";
			// To get your Discord Avatar, (It's a bit more complicated ^^')
			// Go on discord and press in the same time CTRL + SHIFT + I, then CTRL + SHIFT + C.
			// Select your avatar with your mouse
			// The link will appear highlighted in the right panel.

	public static final String
			EARTH_ICON = "https://media.npr.org/assets/img/2013/03/06/bluemarble3k-smaller-nasa_custom-644f0b7082d6d0f6814a9e82908569c07ea55ccb-s800-c85.jpg",
			COVID_ICON = "https://static.data.gouv.fr/avatars/30/3624ff029d41908d4b3d2c11dfa349.png",
			FUNCRAFT_ICON = "https://pbs.twimg.com/profile_images/1083667374379855872/kSsOCKM7_400x400.jpg",
			GIVEAWAYS_ICON = "https://www.pinclipart.com/picdir/big/107-1079672_bing-christmas-clip-art-ndash-discord-tada-emoji.png",
			MINECRAFT_ICON = "https://assets.stickpng.com/images/580b57fcd9996e24bc43c2f5.png",
			WIKIPEDIA_ICON = "https://upload.wikimedia.org/wikipedia/commons/0/06/Wikipedia-logo_ka.png",
			HYPIXEL_ICON = "https://cdn.discordapp.com/icons/489529070913060867/f7df056de15eabfc0a0e178d641f812b.webp?size=128";

	public static int pingCount = 0;
}
