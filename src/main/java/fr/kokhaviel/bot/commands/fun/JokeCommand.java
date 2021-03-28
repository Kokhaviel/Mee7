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

public class JokeCommand extends ListenerAdapter {

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
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject FUN_OBJECT = LANG_OBJECT.get("fun").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "joke")) {

			if(args.length> 1) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("missing_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "joke").
						queue();
				return;
			}

			final String URL = "https://apis.duncte123.me/joke";
			JsonObject object = null;

			try {
				object = JsonUtilities.readJson(new URL(URL)).getAsJsonObject();
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			assert object != null;
			channel.sendMessage(getJoke(object, GENERAL_OBJECT, FUN_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getJoke(JsonObject object, JsonObject generalObject, JsonObject funObject) {

		JsonObject data = object.get("data").getAsJsonObject();

		EmbedBuilder jokeEmbed = new EmbedBuilder();
		jokeEmbed.setColor(new Color(27, 209, 54));
		jokeEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nAPI : https://docs.duncte123.com/", Config.DEVELOPER_AVATAR);
		jokeEmbed.setTitle(funObject.get("joke").getAsString());

		jokeEmbed.addField(data.get("title").getAsString(), data.get("body").getAsString(), false);
		jokeEmbed.addField("Link : ", data.get("url").getAsString(), false);

		return jokeEmbed;
	}

}
