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

public class LoveCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.PREFIX + "love")) {

			if(args.length > 3) {
				channel.sendMessage("You must specify 2 people to form a loveship").queue();
				return;
			}

			final String url = "https://apis.duncte123.me/love/" + args[1] + "/" + args[2];
			JsonObject object = null;

			try {
				object = JsonUtilities.readJson(new URL(url)).getAsJsonObject();
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			assert object != null;
			channel.sendMessage(getResult(object).build()).queue();
		}
	}

	private EmbedBuilder getResult(JsonObject object) {


		JsonObject data = object.get("data").getAsJsonObject();

		EmbedBuilder loveEmbed = new EmbedBuilder();
		loveEmbed.setColor(new Color(217, 20, 200));
		loveEmbed.setAuthor("Loveship");
		loveEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI : https://docs.duncte123.com/", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");
		loveEmbed.setTitle(data.get("names").getAsString() + " love <3");

		loveEmbed.addField("Score : " + data.get("score_int").getAsString(), data.get("score").getAsString(), false);
		loveEmbed.addField(" ", data.get("message").getAsString() + "\n ", false);

		return loveEmbed;
	}

}
