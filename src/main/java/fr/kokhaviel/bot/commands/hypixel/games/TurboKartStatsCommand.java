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

package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.tkr.Tkr;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class TurboKartStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "turbokart")) {

            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "turbokart <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {
                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();
                    final Player player = sloth.getPlayer(args[1]);
                    final Tkr tkr = player.getStats().getTkr();
                    channel.sendMessage(getTkrStats(event, player, tkr).build()).queue();
                    channel.sendMessage(getMapStats(event, player, tkr).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getTkrStats(MessageReceivedEvent event, Player player, Tkr tkr) {
        EmbedBuilder tkrEmbed = new EmbedBuilder();
        tkrEmbed.setAuthor("Tkr Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        tkrEmbed.setColor(Color.MAGENTA);
        tkrEmbed.setTitle(player.getUsername() + " Stats");
        tkrEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        tkrEmbed.addField("Coins : ", String.valueOf(tkr.getCoins()), true);
        tkrEmbed.addField("Coins Pickup : ", String.valueOf(tkr.getCoinPickups()), true);
        tkrEmbed.addField("Wins : ", String.valueOf(tkr.getWins()), true);
        tkrEmbed.addField("Laps : ", String.valueOf(tkr.getLaps()), true);
        tkrEmbed.addField("Box pickup : ", String.valueOf(tkr.getBoxPickups()), true);
        tkrEmbed.addField("Banana Sent : ", String.valueOf(tkr.getBananasSent()), true);
        tkrEmbed.addField("Banana Received : ", String.valueOf(tkr.getBananasReceived()), true);
        tkrEmbed.addField("Banana Ratio : ", String.valueOf(tkr.getBananaRatio()), true);

        tkrEmbed.addBlankField(false);
        tkrEmbed.addField("Gold Trophies : ", String.valueOf(tkr.getTrophies().getGold()), true);
        tkrEmbed.addField("Silver Trophies : ", String.valueOf(tkr.getTrophies().getSilver()), true);
        tkrEmbed.addField("Bronze Trophies : ", String.valueOf(tkr.getTrophies().getBronze()), true);

        return tkrEmbed;
    }

    private EmbedBuilder getMapStats(MessageReceivedEvent event, Player player, Tkr tkr) {
        EmbedBuilder tkrEmbed = new EmbedBuilder();
        tkrEmbed.setAuthor("Tkr Map Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        tkrEmbed.setColor(Color.MAGENTA);
        tkrEmbed.setTitle(player.getUsername() + " Stats");
        tkrEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        tkrEmbed.addField("Retro Games : ", String.valueOf(tkr.getMaps().getRetro().getGames()), true);
        tkrEmbed.addField("Retro Win Ratio : ", String.valueOf(tkr.getMaps().getRetro().getWinRatio()), true);
        tkrEmbed.addField("Retro Trophies : ", String.valueOf(tkr.getMaps().getRetro().getTrophies().getGold() + tkr.getMaps().getRetro().getTrophies().getSilver() + tkr.getMaps().getRetro().getTrophies().getBronze()), true);

        tkrEmbed.addBlankField(false);
        tkrEmbed.addField("Hypixel Gp Games : ", String.valueOf(tkr.getMaps().getHypixelgp().getGames()), true);
        tkrEmbed.addField("Hypixel Gp Win Ratio : ", String.valueOf(tkr.getMaps().getHypixelgp().getWinRatio()), true);
        tkrEmbed.addField("Hypixel Gp Trophies : ", String.valueOf(tkr.getMaps().getHypixelgp().getTrophies().getGold() + tkr.getMaps().getHypixelgp().getTrophies().getSilver() + tkr.getMaps().getHypixelgp().getTrophies().getBronze()), true);

        tkrEmbed.addBlankField(false);
        tkrEmbed.addField("Jungle Rush Games : ", String.valueOf(tkr.getMaps().getJunglerush().getGames()), true);
        tkrEmbed.addField("Jungle Rush Win Ratio : ", String.valueOf(tkr.getMaps().getJunglerush().getWinRatio()), true);
        tkrEmbed.addField("Jungle Rush Trophies : ", String.valueOf(tkr.getMaps().getJunglerush().getTrophies().getGold() + tkr.getMaps().getJunglerush().getTrophies().getSilver() + tkr.getMaps().getJunglerush().getTrophies().getBronze()), true);

        tkrEmbed.addBlankField(false);
        tkrEmbed.addField("Olympus Games : ", String.valueOf(tkr.getMaps().getOlympus().getGames()), true);
        tkrEmbed.addField("Olympus Win Ratio : ", String.valueOf(tkr.getMaps().getOlympus().getWinRatio()), true);
        tkrEmbed.addField("Olympus Trophies : ", String.valueOf(tkr.getMaps().getOlympus().getTrophies().getGold() + tkr.getMaps().getOlympus().getTrophies().getSilver() + tkr.getMaps().getOlympus().getTrophies().getBronze()), true);

        tkrEmbed.addBlankField(false);
        tkrEmbed.addField("Canyon Games : ", String.valueOf(tkr.getMaps().getCanyon().getGames()), true);
        tkrEmbed.addField("Canyon Win Ratio : ", String.valueOf(tkr.getMaps().getCanyon().getWinRatio()), true);
        tkrEmbed.addField("Canyon Trophies : ", String.valueOf(tkr.getMaps().getCanyon().getTrophies().getGold() + tkr.getMaps().getCanyon().getTrophies().getSilver() + tkr.getMaps().getCanyon().getTrophies().getBronze()), true);

        return tkrEmbed;
    }
}
