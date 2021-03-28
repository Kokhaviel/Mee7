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

package fr.kokhaviel.bot.commands.guild;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PrefixCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String oldPrefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(oldPrefix + "prefix")) {

			if(args.length < 2) {
				channel.sendMessage("You must specify a new prefix").queue();
				return;
			}

			if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_SERVER)) {
				channel.sendMessage("You must have the 'Manage Server' Permission").queue();
				return;
			}

			File file = new File("guild_settings.json");

			JsonObject fileContent = JsonUtilities.readJson(file).getAsJsonObject();
			String lang = fileContent.get(event.getGuild().getId()).getAsJsonObject().get("language").getAsString();
			FileWriter fw = null;
			try {
				fw = new FileWriter(file, false);
			} catch(IOException e) {
				e.printStackTrace();
			}
			assert fw != null;
			fileContent.remove(event.getGuild().getId());
			JsonObject guildObject = new JsonObject();

			String prefix = args[1];

			guildObject.addProperty("language", lang);
			guildObject.addProperty("prefix", prefix);
			guildObject.addProperty("music_prefix", "m" + prefix);
			guildObject.addProperty("hypixel_prefix", "h" + prefix);
			guildObject.addProperty("funcraft_prefix", "f" + prefix);
			guildObject.addProperty("wikipedia_prefix", "w" + prefix);
			guildObject.addProperty("giveaways_prefix", "g" + prefix);
			guildObject.addProperty("covid_prefix", "c" + prefix);
			guildObject.addProperty("minecraft_prefix", "mc" + prefix);

			fileContent.add(event.getGuild().getId(), guildObject);
			try {
				fw.write(String.valueOf(fileContent));
				fw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}

			event.getChannel().sendMessage("Succcessfully set the prefix to : " + prefix).queue(
					delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS)
			);
		}
	}
}
