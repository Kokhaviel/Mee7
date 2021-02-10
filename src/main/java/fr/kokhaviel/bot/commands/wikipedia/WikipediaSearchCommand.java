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

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
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

                HttpActionClient client = HttpActionClient.builder() //
                        .withUrl("https://en.wikipedia.org/w/") //
                        .withUserAgent("Mee7", "1.0", "Kokhaviel") //
                        .withRequestsPerUnit(10, TimeUnit.MINUTES) //
                        .build();

                MediaWikiBot bot = new MediaWikiBot(client);

                Article article;
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("Wikipedia Search", null, "https://en.wikipedia.org/wiki/Wikipedia#/media/File:Wikipedia-logo-v2.svg");
                embed.setColor(Color.WHITE);
                embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nWikipedia API by eldur (https://github.com/eldur/jwbf)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

                try {

                    ArrayList<String> researchArgs = new ArrayList<>(Arrays.asList(args));
                    researchArgs.remove(0);
                    String[] strings = researchArgs.toArray(new String[0]);
                    StringBuilder sb = new StringBuilder();
                    for (String string : strings) {
                        sb.append(string).append(" ");
                    }
                    article = new Article(bot, sb.toString());
                    System.out.println(article.getText());
                    embed.setTitle(article.getTitle());
                    String[] searches = article.getText().split("\n");
                    for (String search : searches) {
                        if (search.contains("Short description") || search.contains("short description")) {

                            final String[] split = search.split("\\|");
                            embed.addField("Short Description",
                                    split[1].replace("}", ""),
                                    false);

                        }

                        if (search.contains("Distinguish")) {

                            final String replace = search.replace("{", "")
                                    .replace("}", "")
                                    .replace("Distinguish|", "");

                            embed.addField("Distinguish : ", replace, false);
                        }
                        if (search.contains("Logo") || search.contains("logo")) {

                            System.out.println("Logo : " + search);
                            String[] logoResearch = search
                                    .replace("| logo = ", "")
                                    .replace("| Logo = ", "")
                                    .replace("[[", "")
                                    .replace("]]", "")
                                    .replace(" ", "_")
                                    .split("\\|");

                            System.out.println("Logo Research" + Arrays.toString(logoResearch));

                            String url = "https://en.wikipedia.org/wiki/";
                            if(logoResearch.length> 0) {
                                String fileName = logoResearch[0];
                                String finalUrl = url + fileName;
                                System.out.println("Final URL" + finalUrl);

                                embed.setThumbnail(finalUrl);
                            }


                        }
                    }

                    channel.sendMessage(embed.build()).queue();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
