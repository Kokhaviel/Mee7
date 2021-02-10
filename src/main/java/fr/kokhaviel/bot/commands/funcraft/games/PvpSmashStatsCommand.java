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

package fr.kokhaviel.bot.commands.funcraft.games;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PvpSmashStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.FUNCRAFT_PREFIX + "pvpsmash")) {

            if(args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Specify A Player !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {
                    final String url = "https://lordmorgoth.net/APIs/stats?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1] + "&mode=pvpsmash&periode=always";

                    try {

                        message.delete().queue();
                        Gson gson = new Gson();
                        PvpSmash pvpsmash = gson.fromJson(readJson(new URL(url)), PvpSmash.class);

                        channel.sendMessage(getPvpSmashStats(pvpsmash).build()).queue();


                    } catch (IOException e) {

                        channel.sendMessage("An exception occurred : File doesn't exist !").queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private EmbedBuilder getPvpSmashStats(PvpSmash pvpSmash) {

        EmbedBuilder pvpsmashEmbed = new EmbedBuilder();
        pvpsmashEmbed.setAuthor("Funcraft Player Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        pvpsmashEmbed.setColor(Color.RED);
        pvpsmashEmbed.setThumbnail(pvpSmash.skin);
        pvpsmashEmbed.setTitle(String.format("%s PvpSmash Stats", pvpSmash.pseudo));
        pvpsmashEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        pvpsmashEmbed.addField("Rank : ", pvpSmash.rang, true);

        pvpsmashEmbed.addBlankField(false);
        pvpsmashEmbed.addField("Points : ", pvpSmash.data.points, true);
        pvpsmashEmbed.addField("Games : ", pvpSmash.data.parties, true);
        pvpsmashEmbed.addField("Victories : ", pvpSmash.data.victoires, true);
        pvpsmashEmbed.addField("Defeats : ", pvpSmash.data.defaites, true);
        pvpsmashEmbed.addField("Played Time : ", pvpSmash.data.temps_jeu, true);
        pvpsmashEmbed.addField("Kills : ", pvpSmash.data.kills, true);
        pvpsmashEmbed.addField("Deaths : ", pvpSmash.data.morts, true);

        pvpsmashEmbed.addBlankField(false);
        pvpsmashEmbed.addField("Winrate : ", pvpSmash.stats.winrate + "%", true);
        pvpsmashEmbed.addField("KDR : ", pvpSmash.stats.kd, true);
        pvpsmashEmbed.addField("Average Kills / Games : ", pvpSmash.stats.kills_game,true);
        pvpsmashEmbed.addField("Average Deaths / Games : ", pvpSmash.stats.morts_game, true);
        pvpsmashEmbed.addField("Average Time / Games : ", pvpSmash.stats.temps_partie + " s", true);

        return pvpsmashEmbed;
    }

    private static JsonElement readJson(URL jsonURL) {

        try {

            return readJson(catchForbidden(jsonURL));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return JsonNull.INSTANCE;
    }

    private static JsonElement readJson(InputStream inputStream) {

        JsonElement element = JsonNull.INSTANCE;
        try(InputStream stream = new BufferedInputStream(inputStream)) {

            final Reader reader = new BufferedReader(new InputStreamReader(stream));
            final StringBuilder sb = new StringBuilder();

            int character;
            while ((character = reader.read()) != -1) sb.append((char)character);

            element =  JsonParser.parseString(sb.toString());
        } catch (IOException e) {

            e.printStackTrace();
        }

        return element.getAsJsonObject();
    }

    private static InputStream catchForbidden(URL url) throws IOException {

        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
        connection.setInstanceFollowRedirects(true);
        return connection.getInputStream();
    }

    static class Data {
        String points;
        String parties;
        String victoires;
        String defaites;
        String temps_jeu;
        String kills;
        String morts;
    }

    static class Stats {
        String winrate;
        String kd;
        String kills_game;
        String morts_game;
        String temps_partie;
    }

    static class PvpSmash {

        String pseudo;
        String mode_jeu;
        String rang;
        Data data;
        Stats stats;
        String skin;
    }

}
