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
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.skyclash.SkyClash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class SkyClashStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "skyclash")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "skyclash <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final SkyClash skyclash = player.getStats().getSkyClash();

                    channel.sendMessage(getSkyClashStats(event, player, skyclash).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getSkyClashStats(MessageReceivedEvent event, Player player, SkyClash skyclash) {

        EmbedBuilder skyclashEmbed = new EmbedBuilder();
        skyclashEmbed.setAuthor("Skyclash Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        skyclashEmbed.setColor(Color.GREEN);
        skyclashEmbed.setTitle(player.getUsername() + " Stats");
        skyclashEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        skyclashEmbed.addField("Coins : ", String.valueOf(skyclash.getCoins()), true);

        return skyclashEmbed;
    }

}
