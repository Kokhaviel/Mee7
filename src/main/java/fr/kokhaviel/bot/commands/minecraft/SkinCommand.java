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
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;

public class SkinCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.MINECRAFT_PREFIX + "skin")) {

			if(args.length < 2) {

				channel.sendMessage("You must specify a Minecraft player username !").queue();
				return;
			}

			final String url = "https://minecraft-api.com/api/skins/" + args[1] + "/body/10.5/10/json";

			try {
				JsonObject object = JsonUtilities.readJson(new URL(url)).getAsJsonObject();
				final String image = object.get("skin").getAsString();
				byte[] decoded = Base64.getDecoder().decode(image);
				channel.sendMessage("Here is the " + args[1] + " skin !").addFile(decoded, "Skin.png").queue();

			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
