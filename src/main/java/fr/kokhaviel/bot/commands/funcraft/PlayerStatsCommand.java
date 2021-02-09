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

// Funcraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)

package fr.kokhaviel.bot.commands.funcraft;

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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerStatsCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.FUNCRAFT_PREFIX + "player")) {

            if(args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Specify A Player !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    final String url = "https://lordmorgoth.net/APIs/infos?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1];

                    try {

                        message.delete().queue();

                        Gson gson = new Gson();

                        Stats stats = gson.fromJson(readJson(new URL(url)), Stats.class);

                        System.out.println(stats.inscription);


                        channel.sendMessage(getFuncraftStats(stats).build()).queue();
                        channel.sendMessage(getPlayerFriends(stats).build()).queue();

                    } catch (Exception e) {
                        channel.sendMessage("File Not Found : Player Does Not Exist !").queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private EmbedBuilder getFuncraftStats(Stats stats) {

        EmbedBuilder funcraftEmbed = new EmbedBuilder();

        funcraftEmbed.setAuthor("Funcraft Player Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        funcraftEmbed.setColor(Color.RED);
        funcraftEmbed.setThumbnail(stats.skin);
        funcraftEmbed.setTitle(String.format("[%s] %s Stats", stats.grade, stats.pseudo));
        funcraftEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        funcraftEmbed.setAuthor("Funcraft Player Friends", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        funcraftEmbed.setColor(Color.RED);
        funcraftEmbed.setThumbnail(stats.skin);
        funcraftEmbed.setTitle(String.format("[%s] %s Friends", stats.grade, stats.pseudo));
        funcraftEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");


        funcraftEmbed.addField("Inscription : ", stats.inscription, true);
        funcraftEmbed.addField("Last Connection : ", "Owo ... Not working for the moment", false);
        //FIXME : Last Connection
        funcraftEmbed.addField("Glories : ", String.valueOf(stats.gloires), false);
        funcraftEmbed.addField("Games : ", stats.parties, false);
        funcraftEmbed.addField("Points : ", stats.points, false);
        funcraftEmbed.addField("Victories : ", stats.victoires, false);
        funcraftEmbed.addField("Defeats : ", stats.defaites, false);
        funcraftEmbed.addField("Game Time : ", stats.temps_jeu, false);
        funcraftEmbed.addField("Kills : ", stats.kills, false);
        funcraftEmbed.addField("Deaths : ", stats.morts, false);
        funcraftEmbed.addField("Ban : ", stats.ban, false);

        return funcraftEmbed;
    }

    private EmbedBuilder getPlayerFriends(Stats stats) {

        EmbedBuilder friendsEmbed = new EmbedBuilder();

        friendsEmbed.setAuthor("Funcraft Player Friends", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        friendsEmbed.setColor(Color.RED);
        friendsEmbed.setThumbnail(stats.skin);
        friendsEmbed.setTitle(String.format("[%s] %s Friends", stats.grade, stats.pseudo));
        friendsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        for (Friend friend : stats.amis) {
            friendsEmbed.addField(friend.nom, "Skin : [" + friend.nom + " Head](" + friend.skin + ")", false);
        }

        return friendsEmbed;
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


    static class Friend {

        String nom;
        String skin;


    }

    static class Stats {
        String grade;
        String pseudo;
        String skin;
        String inscription;
        String gloires;
        String parties;
        String points;
        String victoires;
        String defaites;
        String temps_jeu;
        String kills;
        String morts;
        List<Friend> amis;
        String ban;
    }
}