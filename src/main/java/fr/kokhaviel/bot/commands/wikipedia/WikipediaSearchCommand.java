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

package fr.kokhaviel.bot.commands.wikipedia;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Mee7;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class WikipediaSearchCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("wikipedia_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject WIKIPEDIA_OBJECT = LANG_OBJECT.get("wikipedia").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "search")) {

			message.delete().queue();

			if(args.length < 2) {

				channel.sendMessage(format("%s : %s", COMMANDS_OBJECT.get("missing_arguments").getAsString(), WIKIPEDIA_OBJECT.get("missing_arguments").getAsString())).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			try {

				ArrayList<String> research = new ArrayList<>(Arrays.asList(args));
				research.remove(0);
				StringBuilder finalResearch = new StringBuilder();

				for(String s : research) {
					finalResearch.append(s).append("_");
				}
				final String URL = "https://en.wikipedia.org/api/rest_v1/page/summary/" + finalResearch;
				WikipediaContent content = Mee7.GSON.fromJson(JsonUtilities.readJson(new URL(URL)), WikipediaContent.class);

				channel.sendMessageEmbeds(getContentPage(content, GENERAL_OBJECT, COMMANDS_OBJECT, WIKIPEDIA_OBJECT).build()).queue();

			} catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getContentPage(WikipediaContent content, JsonObject generalObject, JsonObject commandObject, JsonObject wikipediaObject) {
		EmbedBuilder wikiEmbed = new EmbedBuilder();
		wikiEmbed.setAuthor(wikipediaObject.get("wikipedia_search").getAsString(), null, Config.WIKIPEDIA_ICON);
		wikiEmbed.setColor(Color.BLACK);
		wikiEmbed.setThumbnail(content.thumbnail.source);
		wikiEmbed.setTitle(format("%s %s", content.title, wikipediaObject.get("wikipedia_page").getAsString()));
		wikiEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		wikiEmbed.addField(format("%s %s : ", wikipediaObject.get("description").getAsString(), commandObject.get("command").getAsString()), content.description, false);
		wikiEmbed.addField(format("%s %s : ", wikipediaObject.get("article_content").getAsJsonObject(), commandObject.get("command").getAsString()), content.extract, false);

		return wikiEmbed;
	}

	static class Image {
		String source;
	}

	static class WikipediaContent {
		String title;
		Image thumbnail;
		String description;
		String extract;
	}
}
