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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LanguageCommand extends ListenerAdapter {

	List<String> supportedLanguages = new ArrayList<>(Arrays.asList("de", "us", "es", "fr", "it", "ja", "lt", "pt", "ru"));

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String oldPrefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(oldPrefix + "language")) {

			if(args.length < 2) {
				channel.sendMessage("You must specify a new language").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
				);
				return;
			}

			if(!supportedLanguages.contains(args[1])) {
				EmbedBuilder languagesEmbed = new EmbedBuilder();
				languagesEmbed.setTitle("Supported Languages");
				languagesEmbed.addField("English", "us", false);
				languagesEmbed.addField("Francais", "fr", false);
				languagesEmbed.addField("Español", "es", false);
				languagesEmbed.addField("Deutsch", "de", false);
				languagesEmbed.addField("Italiano", "it", false);
				languagesEmbed.addField("日本語", "ja", false);
				languagesEmbed.addField("Lietuvis", "lt", false);
				languagesEmbed.addField("русский", "ru", false);
				languagesEmbed.addField("Português", "pt", false);
				channel.sendMessage("This language is not supported !").queue();
				channel.sendMessage(languagesEmbed.build()).queue();
				return;
			}

			File file = new File("guild_settings.json");

			JsonObject fileContent = JsonUtilities.readJson(file).getAsJsonObject();
			String prefix = fileContent.get(event.getGuild().getId()).getAsJsonObject().get("prefix").getAsString();
			FileWriter fw = null;
			try {
				fw = new FileWriter(file, false);
			} catch(IOException e) {
				e.printStackTrace();
			}
			assert fw != null;
			fileContent.remove(event.getGuild().getId());
			JsonObject guildObject = new JsonObject();

			String lang = args[1];

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

			event.getChannel().sendMessage("Successfully set language to : " + lang).queue(
					delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS)
			);
		}
	}
}
