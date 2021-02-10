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
import zone.nora.slothpixel.player.stats.smash.Smash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class SmashStatsCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "smash")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "smash <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {
                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();
                    final Player player = sloth.getPlayer(args[1]);
                    final Smash smash = player.getStats().getSmash();
                    channel.sendMessage(getSmashStats(player, smash).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getSmashStats(Player player, Smash smash) {
        EmbedBuilder smashEmbed = new EmbedBuilder();
        smashEmbed.setAuthor("Smash Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        smashEmbed.setColor(Color.RED);
        smashEmbed.setTitle(player.getUsername() + " Stats");
        smashEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

        smashEmbed.addField("Coins : ", String.valueOf(smash.getCoins()), true);

        return smashEmbed;
    }
}
