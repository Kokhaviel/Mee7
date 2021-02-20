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

import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class WikipediaSearchCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.WIKIPEDIA_PREFIX + "search")) {

            message.delete().queue();

            if (args.length < 2) {

                channel.sendMessage("You must specify an article to search !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                Gson gson = new Gson();

                try {

                    ArrayList<String> research = new ArrayList<>(Arrays.asList(args));
                    research.remove(0);
                    StringBuilder finalResearch = new StringBuilder();

                    for (String s : research) {
                        finalResearch.append(s).append("_");
                    }
                    final String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + finalResearch;
                    WikipediaContent content = gson.fromJson(JsonUtilities.readJson(new URL(url)), WikipediaContent.class);

                    channel.sendMessage(getContentPage(content).build()).queue();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private EmbedBuilder getContentPage(WikipediaContent content) {
        EmbedBuilder wikiEmbed = new EmbedBuilder();
        wikiEmbed.setAuthor("Wikipedia Search", null, "https://upload.wikimedia.org/wikipedia/commons/0/06/Wikipedia-logo_ka.png");
        wikiEmbed.setColor(Color.BLACK);
        wikiEmbed.setThumbnail(content.thumbnail.source);
        wikiEmbed.setTitle(content.title + " Wikipedia Page");
        wikiEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG, "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128" );

        wikiEmbed.addField("Description : ", content.description, false);
        wikiEmbed.addField("Article Content : ", content.extract, false);

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
