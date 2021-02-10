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

public class ShootcraftStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.FUNCRAFT_PREFIX + "shootcraft")) {

            if(args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Specify A Player !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    final String url = "https://lordmorgoth.net/APIs/stats?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1] + "&mode=shootcraft&periode=always";

                    try {

                        message.delete().queue();
                        Gson gson = new Gson();
                        Shootcraft shootcraft = gson.fromJson(JsonUtilities.readJson(new URL(url)), Shootcraft.class);

                        if(shootcraft.exit_code.equals("0")) channel.sendMessage(getShootcraftStats(shootcraft, channel).build()).queue();

                    } catch (IOException e) {

                        channel.sendMessage("An exception occurred : File doesn't exist !").queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private EmbedBuilder getShootcraftStats(Shootcraft shootcraft, TextChannel channel) {

        EmbedBuilder shootcraftEmbed = new EmbedBuilder();

        if(shootcraft.exit_code.equals("0")) {
            shootcraftEmbed.setAuthor("Funcraft Player Stats", null, "https://pbs.twimg.com/profile_images/1083667374379855872/kSsOCKM7_400x400.jpg");
            shootcraftEmbed.setColor(Color.RED);
            shootcraftEmbed.setThumbnail(shootcraft.skin);
            shootcraftEmbed.setTitle(String.format("%s Shootcraft Stats", shootcraft.pseudo));
            shootcraftEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

            shootcraftEmbed.addField("Rank : ", shootcraft.rang, true);

            shootcraftEmbed.addBlankField(false);
            shootcraftEmbed.addField("Points : ", shootcraft.data.points, true);
            shootcraftEmbed.addField("Games : ", shootcraft.data.parties, true);
            shootcraftEmbed.addField("Victories : ", shootcraft.data.victoires, true);
            shootcraftEmbed.addField("Defeats : ", shootcraft.data.defaites, true);
            shootcraftEmbed.addField("Played Time : ", shootcraft.data.temps_jeu, true);
            shootcraftEmbed.addField("Kills : ", shootcraft.data.kills, true);
            shootcraftEmbed.addField("Deaths : ", shootcraft.data.morts, true);

            shootcraftEmbed.addBlankField(false);
            shootcraftEmbed.addField("Winrate : ", shootcraft.stats.winrate + "%", false);
            shootcraftEmbed.addField("KDR : ", shootcraft.stats.kd, false);
            shootcraftEmbed.addField("Average Kills / Games : ", shootcraft.stats.kills_game, true);
            shootcraftEmbed.addField("Average Deaths / Games : ", shootcraft.stats.morts_game, true);
            shootcraftEmbed.addField("Average Time / Games : ", shootcraft.stats.temps_partie + " s", true);
        }

        if(!shootcraft.exit_code.equals("0")) {
            channel.sendMessage(JsonUtilities.getErrorCode(shootcraft.exit_code)).queue();
        }
        return shootcraftEmbed;
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

    static class Shootcraft {
        String exit_code;
        String pseudo;
        String mode_jeu;
        String rang;
        Data data;
        Stats stats;
        String skin;
    }
}
