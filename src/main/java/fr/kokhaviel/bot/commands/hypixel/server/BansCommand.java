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
import fr.kokhaviel.api.hypixel.server.bans.Bans;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

import static fr.kokhaviel.bot.Mee7.hypixelAPI;
import static java.lang.String.format;

public class BansCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject HYPIXEL_OBJECT = LANG_OBJECT.get("hypixel").getAsJsonObject();


		final Message message = event.getMessage();
		final MessageChannel channel = event.getChannel();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(prefix + "bans")) {

			message.delete().queue();

			Bans bans = null;
			try {
				bans = hypixelAPI.getBans();
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			assert bans != null;
			if(!bans.isSuccess()) {
				channel.sendMessage(bans.getCause()).queue();
				return;
			}

			channel.sendMessage(getBansStats(bans, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getBansStats(Bans bans, JsonObject generalObject, JsonObject hypixelObject) {
		EmbedBuilder bansEmbed = new EmbedBuilder();
		bansEmbed.setAuthor(format("Hypixel %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		bansEmbed.setColor(new Color(187, 40, 40));
		bansEmbed.setTitle(format("Hypixel Bans %s", hypixelObject.get("stats").getAsString()));
		bansEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		bansEmbed.addField("Staff Daily : ", String.valueOf(bans.getStaffDaily()), false);
		bansEmbed.addField("Staff Total : ", String.valueOf(bans.getStaffTotal()), false);
		bansEmbed.addBlankField(false);
		bansEmbed.addField("Watchdog Last Minute : ", String.valueOf(bans.getWdLastMinute()), false);
		bansEmbed.addField("Watchdog Daily : ", String.valueOf(bans.getWdDaily()), false);
		bansEmbed.addField("Watchdog Total : ", String.valueOf(bans.getWdTotal()), false);

		return bansEmbed;
	}
}
