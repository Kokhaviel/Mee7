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

package fr.kokhaviel.bot.commands.fun;

import com.google.gson.JsonObject;
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
import java.net.URL;

import static java.lang.String.format;

public class LoveCommand extends ListenerAdapter {

	//TODO : java.io.IOException: Server returned HTTP response code: 403 for URL:

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject FUN_OBJECT = LANG_OBJECT.get("fun").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "love")) {

			if(args.length > 3) {
				channel.sendMessage(FUN_OBJECT.get("missing_two_people").getAsString()).queue();
				return;
			}

			final String URL = "https://apis.duncte123.me/love/" + args[1] + "/" + args[2];
			JsonObject object = null;

			try {
				object = JsonUtilities.readJson(new URL(URL)).getAsJsonObject();
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			assert object != null;
			channel.sendMessage(getResult(object, GENERAL_OBJECT, FUN_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getResult(JsonObject object, JsonObject generalObject, JsonObject funObject) {

		JsonObject data = object.get("data").getAsJsonObject();

		EmbedBuilder loveEmbed = new EmbedBuilder();
		loveEmbed.setColor(new Color(217, 20, 200));
		loveEmbed.setAuthor(funObject.get("loveship").getAsString());
		loveEmbed.setFooter(generalObject.get("developed_by") + Config.DEVELOPER_TAG + "\nAPI : https://docs.duncte123.com/", Config.DEVELOPER_AVATAR);
		loveEmbed.setTitle(data.get("names").getAsString() + " love <3");

		loveEmbed.addField(format("%s : ", funObject.get("score").getAsString()) + data.get("score_int").getAsString(), data.get("score").getAsString(), false);
		loveEmbed.addField("", data.get("message").getAsString() + "\n ", false);

		return loveEmbed;
	}
}
