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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MemeCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.PREFIX + "meme")) {

			final String url = "https://apis.duncte123.me/meme";
			JsonObject object = null;

			try {
				object = JsonUtilities.readJson(new URL(url)).getAsJsonObject();
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			assert object != null;
			channel.sendMessage(getMeme(object, event).build()).queue();

		}
	}

	private EmbedBuilder getMeme(JsonObject object, MessageReceivedEvent event) {

		JsonObject data = object.get("data").getAsJsonObject();

		EmbedBuilder memeEmbed = new EmbedBuilder();
		memeEmbed.setColor(new Color(20, 82, 217));
		memeEmbed.setAuthor("Meme");
		memeEmbed.setImage(data.get("image").getAsString());
		memeEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI : https://apis.duncte123.me/meme", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");
		memeEmbed.setTitle(data.get("title").getAsString());

		memeEmbed.addField("Link : ", data.get("url").getAsString(), false);

		return memeEmbed;
	}
}
