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

package fr.kokhaviel.bot.commands.hypixel.server;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.server.Bans;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

import static fr.kokhaviel.bot.Mee7.HYPIXEL_API;

public class BansCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = Settings.getGuildPrefix(event.getGuild().getId(), "hypixel_prefix");

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject HYPIXEL_OBJECT = LANG_OBJECT.get("hypixel").getAsJsonObject();


		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "bans")) {
			message.delete().queue();

			Bans bans;

			try {
				bans = HYPIXEL_API.getBansData();
				channel.sendMessageEmbeds(getBansData(bans, GENERAL_OBJECT).build()).queue();
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public EmbedBuilder getBansData(Bans bans, JsonObject generalObject) {
		EmbedBuilder hypixelEmbed = new EmbedBuilder();

		hypixelEmbed.setAuthor("Hypixel Player Stats", null, Config.HYPIXEL_ICON);
		hypixelEmbed.setColor(Color.RED);
		hypixelEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG
				+ "\nHypixel API by Kokhaviel (https://github.com/Kokhaviel/HypixelAPI/)", Config.DEVELOPER_AVATAR);

		hypixelEmbed.addField("Total Watchdog Bans : ", String.valueOf(bans.getWatchdogTotal()), true);
		hypixelEmbed.addField("Staff Total Bans : ", String.valueOf(bans.getStaffTotal()), true);
		hypixelEmbed.addField("Watchdog Daily Bans : ", String.valueOf(bans.getWatchdogDaily()), true);
		hypixelEmbed.addField("Staff Daily Bans : ", String.valueOf(bans.getStaffDaily()), true);
		hypixelEmbed.addField("Last Minute Watchdog Bans : ", String.valueOf(bans.getWatchdogLastMinute()), true);

		return hypixelEmbed;
	}
}
