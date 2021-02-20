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
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HikabrainStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.FUNCRAFT_PREFIX + "hikabrain")) {

            if (args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Specify A Player !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    final String url = "https://lordmorgoth.net/APIs/stats?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1] + "&mode=hikabrain&periode=always";

                    try {
                        message.delete().queue();
                        Gson gson = new Gson();
                        Hikabrain hikabrain = gson.fromJson(JsonUtilities.readJson(new URL(url)), Hikabrain.class);

                        if(hikabrain.exit_code.equals("0")) channel.sendMessage(getHikabrainStats(hikabrain, channel).build()).queue();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private EmbedBuilder getHikabrainStats(Hikabrain hikabrain, TextChannel channel) {

        EmbedBuilder hikabrainEmbed = new EmbedBuilder();

        if (hikabrain.exit_code.equals("0")) {
            hikabrainEmbed.setAuthor("Funcraft Player Stats", null, "https://pbs.twimg.com/profile_images/1083667374379855872/kSsOCKM7_400x400.jpg");
            hikabrainEmbed.setColor(Color.RED);
            hikabrainEmbed.setThumbnail(hikabrain.skin);
            hikabrainEmbed.setTitle(String.format("%s Hikabrain Stats", hikabrain.pseudo));
            hikabrainEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

            hikabrainEmbed.addField("Rank : ", hikabrain.rang, true);

            hikabrainEmbed.addBlankField(false);
            hikabrainEmbed.addField("Points : ", hikabrain.data.points, true);
            hikabrainEmbed.addField("Games : ", hikabrain.data.parties, true);
            hikabrainEmbed.addField("Victories : ", hikabrain.data.victoires, true);
            hikabrainEmbed.addField("Defeats : ", hikabrain.data.defaites, true);
            hikabrainEmbed.addField("Played Time : ", hikabrain.data.temps_jeu + " minutes", true);
            hikabrainEmbed.addField("Kills : ", hikabrain.data.kills, true);
            hikabrainEmbed.addField("Deaths : ", hikabrain.data.morts, true);

            hikabrainEmbed.addBlankField(false);
            hikabrainEmbed.addField("Winrate : ", hikabrain.stats.winrate + "%", true);
            hikabrainEmbed.addField("KDR : ", hikabrain.stats.kd, true);
            hikabrainEmbed.addField("Average Kills / Games : ", hikabrain.stats.kills_game, true);
            hikabrainEmbed.addField("Average Deaths / Games : ", hikabrain.stats.morts_game, true);
            hikabrainEmbed.addField("Average Time / Games : ", hikabrain.stats.temps_partie + "s", true);

        }

        if(!hikabrain.exit_code.equals("0")) {
            channel.sendMessage(JsonUtilities.getErrorCode(hikabrain.exit_code)).queue();
        }

        return hikabrainEmbed;
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

    static class Hikabrain {
        String exit_code;
        String pseudo;
        String mode_jeu;
        String rang;
        Data data;
        Stats stats;
        String skin;
    }
}