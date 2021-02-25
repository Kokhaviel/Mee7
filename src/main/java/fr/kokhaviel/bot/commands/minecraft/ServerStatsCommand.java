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
import java.util.Base64;
import java.util.Objects;

public class ServerStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.MINECRAFT_PREFIX + "server")) {

			if(args.length < 2) {

				channel.sendMessage("You must specify the ip address of the server (Ex : mc.hypixel.net) !").queue();
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

				channel.sendMessage("Here is the server Icon ^^").addFile(file, "server.png").queue(message1 -> {
					serverMessage[0] = message1;
				});
				Thread.sleep(1000);
				channel.sendMessage(getStats(object, args, serverMessage[0]).build()).queue();
				Thread.sleep(100);
				serverMessage[0].delete().queue();



			} catch(IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getStats(JsonObject object, String[] args, Message message) throws IOException {

		JsonObject playersObj = object.get("players").getAsJsonObject();

		String descObj = null;
		try {
			descObj = object.get("description").getAsString();
		} catch(UnsupportedOperationException e) {
			e.printStackTrace();
		}


		EmbedBuilder serverEmbed = new EmbedBuilder();
		serverEmbed.setAuthor("Minecraft Server Stats", null, "https://assets.stickpng.com/images/580b57fcd9996e24bc43c2f5.png");
		serverEmbed.setColor(Color.GREEN);
		serverEmbed.setThumbnail(message.getAttachments().get(0).getUrl());
		serverEmbed.setTitle(String.format("%s Stats", args[1]));
		serverEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI : https://minecraft-api.com/", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

		serverEmbed.addField("Online Players : ", playersObj.get("online").getAsString(), false);
		serverEmbed.addField("Maximum Players : ", playersObj.get("max").getAsString(), false);

		serverEmbed.addBlankField(false);
		serverEmbed.addField("Description : ", String.valueOf(Objects.nonNull(descObj)), false);

		return serverEmbed;
	}
}
