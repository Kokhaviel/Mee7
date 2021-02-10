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
import fr.kokhaviel.bot.commands.funcraft.JsonUtilities;
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

public class RushStatsCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.FUNCRAFT_PREFIX + "rush")) {

            if(args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Specify A Player !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    final String url = "https://lordmorgoth.net/APIs/stats?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1] + "&mode=rush&periode=always";

                    try {

                        message.delete().queue();
                        Gson gson = new Gson();
                        Rush rush = gson.fromJson(JsonUtilities.readJson(new URL(url)), Rush.class);

                        if(rush.exit_code.equals("0")) channel.sendMessage(getRushStats(rush, channel).build()).queue();


                    } catch (IOException e) {

                        channel.sendMessage("An exception occurred : File doesn't exist !").queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private EmbedBuilder getRushStats(Rush rush, TextChannel channel) {

        EmbedBuilder rushEmbed = new EmbedBuilder();

        if(rush.exit_code.equals("0")) {
            rushEmbed.setAuthor("Funcraft Player Stats", null, "https://pbs.twimg.com/profile_images/1083667374379855872/kSsOCKM7_400x400.jpg");
            rushEmbed.setColor(Color.RED);
            rushEmbed.setThumbnail(rush.skin);
            rushEmbed.setTitle(String.format("%s Rush Stats", rush.pseudo));
            rushEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

            rushEmbed.addField("Rank : ", rush.rang, true);

            rushEmbed.addBlankField(false);
            rushEmbed.addField("Points : ", rush.data.points, true);
            rushEmbed.addField("Games : ", rush.data.parties, true);
            rushEmbed.addField("Victories : ", rush.data.victoires, true);
            rushEmbed.addField("Defeats : ", rush.data.defaites, true);
            rushEmbed.addField("Played Time : ", rush.data.temps_jeu + " minutes", true);
            rushEmbed.addField("Kills : ", rush.data.kills, true);
            rushEmbed.addField("Deaths : ", rush.data.morts, true);
            rushEmbed.addField("Beds Destroyed : ", rush.data.lits_detruits, true);

            rushEmbed.addBlankField(false);
            rushEmbed.addField("Winrate : ", rush.stats.winrate + "%", true);
            rushEmbed.addField("KDR : ", rush.stats.kd, true);
            rushEmbed.addField("Average Kills / Games : ", rush.stats.kills_game, true);
            rushEmbed.addField("Average Deaths / Games : ", rush.stats.morts_game, true);
            rushEmbed.addField("Average Time / Games : ", rush.stats.temps_partie + " s", true);

        }

        if(!rush.exit_code.equals("0")) {
            channel.sendMessage(JsonUtilities.getErrorCode(rush.exit_code)).queue();
        }
        return rushEmbed;
    }

    static class Data {
        String points;
        String parties;
        String victoires;
        String defaites;
        String temps_jeu;
        String kills;
        String morts;
        String lits_detruits;
    }

    static class Stats {
        String winrate;
        String kd;
        String kills_game;
        String morts_game;
        String temps_partie;
    }

    static class Rush {
        String exit_code;
        String pseudo;
        String mode_jeu;
        String rang;
        Data data;
        Stats stats;
        String skin;
    }


}
