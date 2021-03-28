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

package fr.kokhaviel.bot.commands.minecraft;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import static java.lang.String.format;

public class SkinCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("minecraft_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject MINECRAFT_OBJECT = LANG_OBJECT.get("minecraft").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "skin")) {

			if(args.length < 2) {

				channel.sendMessage(MINECRAFT_OBJECT.get("no_mc_username").getAsString()).queue();
				return;
			}

			final String url = "https://minecraft-api.com/api/skins/" + args[1] + "/body/10.5/10/json";

			try {
				JsonObject object = JsonUtilities.readJson(new URL(url)).getAsJsonObject();
				final String image = object.get("skin").getAsString();
				byte[] decoded = Base64.getDecoder().decode(image);
				channel.sendMessage(format("%s %s %s !", MINECRAFT_OBJECT.get("here_is").getAsString(),
						args[1],
						MINECRAFT_OBJECT.get("skin").getAsString()
				)).addFile(decoded, "skin.png").queue();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
