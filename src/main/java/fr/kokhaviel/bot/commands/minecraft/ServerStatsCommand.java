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
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

import static java.lang.String.format;

public class ServerStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("minecraft_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject MINECRAFT_OBJECT = LANG_OBJECT.get("minecraft").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "server")) {

			if(args.length < 2) {

				channel.sendMessage(format("%s %s !", MINECRAFT_OBJECT.get("no_ip_specified").getAsString(), "(Ex : mc.hypixel.net)")).queue();
				return;
			}

			final String url = "https://minecraft-api.com/api/ping/" + args[1] + "/25565/json";


			try {

				JsonObject object = JsonUtilities.readJson(new URL(url)).getAsJsonObject();
				String serverIcon = object.get("favicon").getAsString();
				byte[] decoded = Base64.getDecoder().decode(serverIcon.substring(22));

				try (OutputStream stream = new FileOutputStream("server.png")) {
					stream.write(decoded);
				}
				File file = new File("server.png");
				final Message[] serverMessage = new Message[1];

				channel.sendMessage("Here is the server Icon ^^").addFile(file, "server.png").queue(
						message1 -> serverMessage[0] = message1);
				Thread.sleep(1000);
				channel.sendMessage(getStats(object, GENERAL_OBJECT, MINECRAFT_OBJECT, args, serverMessage[0]).build()).queue();
				Thread.sleep(100);
				serverMessage[0].delete().queue();

				Files.deleteIfExists(Paths.get("server.png"));

			} catch(IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getStats(JsonObject object, JsonObject generalObject, JsonObject minecraftObject, String[] args, Message message) throws IOException {

		JsonObject playersObj = object.get("players").getAsJsonObject();

		String descObj = null;
		try {
			descObj = object.get("description").getAsString();
		} catch(UnsupportedOperationException e) {
			e.printStackTrace();
		}


		EmbedBuilder serverEmbed = new EmbedBuilder();
		serverEmbed.setAuthor(format("Minecraft %s Stats", minecraftObject.get("server").getAsString()), null, Config.MINECRAFT_ICON);
		serverEmbed.setColor(Color.GREEN);
		serverEmbed.setThumbnail(message.getAttachments().get(0).getUrl());
		serverEmbed.setTitle(format("%s Stats", args[1]));
		serverEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nAPI : https://minecraft-api.com/", Config.DEVELOPER_AVATAR);

		serverEmbed.addField(minecraftObject.get("online_players").getAsString(), playersObj.get("online").getAsString(), false);
		serverEmbed.addField(minecraftObject.get("max_players").getAsString(), playersObj.get("max").getAsString(), false);

		serverEmbed.addBlankField(false);
		serverEmbed.addField(minecraftObject.get("description").getAsString(), String.valueOf(Objects.nonNull(descObj)), false);

		return serverEmbed;
	}
}
