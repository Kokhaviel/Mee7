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
import zone.nora.slothpixel.player.stats.pit.Pit;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class PitStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "pit")) {

            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "quakecraft <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {
                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();
                    final Player player = sloth.getPlayer(args[1]);
                    final Pit pit = player.getStats().getPit();
                    channel.sendMessage(getPitStats(player, pit).build()).queue();
                    channel.sendMessage(getPvPStats(player, pit).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getPitStats(Player player, Pit pit) {
        EmbedBuilder pitEmbed = new EmbedBuilder();
        pitEmbed.setAuthor("Pit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        pitEmbed.setColor(new Color(82, 53, 33));
        pitEmbed.setTitle(player.getUsername() + " Stats");
        pitEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

        pitEmbed.addField("Gold : ", String.valueOf(pit.getGold()), true);
        pitEmbed.addField("Gold Earned : ", String.valueOf(pit.getGoldEarned()), true);
        pitEmbed.addField("XP : ", String.valueOf(pit.getXp()), true);
        pitEmbed.addField("Prestige : ", String.valueOf(pit.getPrestige()), true);
        pitEmbed.addField("Play Time : ", pit.getPlaytimeMinutes() + " minutes", true);
        pitEmbed.addField("Kills : ", String.valueOf(pit.getKills()), true);
        pitEmbed.addField("Assists : ", String.valueOf(pit.getAssists()), true);
        pitEmbed.addField("Deaths : ", String.valueOf(pit.getDeaths()), true);
        pitEmbed.addField("KDR : ", String.valueOf(pit.getKdRatio()), true);
        pitEmbed.addField("Diamond Items Purchased : ", String.valueOf(pit.getDiamondItemsPurchased()), true);
        pitEmbed.addField("Jump into Pit : ", String.valueOf(pit.getJumpedIntoPit() + pit.getLaunchedByLaunchers()), true);

        return pitEmbed;
    }

    private EmbedBuilder getPvPStats(Player player, Pit pit) {
        EmbedBuilder pitEmbed = new EmbedBuilder();
        pitEmbed.setAuthor("Pit PvP Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        pitEmbed.setColor(new Color(82, 53, 33));
        pitEmbed.setTitle(player.getUsername() + " Stats");
        pitEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

        pitEmbed.addField("Sword Hits : ", String.valueOf(pit.getSwordHits()), true);
        pitEmbed.addField("Blocks Placed : ", String.valueOf(pit.getBlocksPlaced()), true);
        pitEmbed.addField("Blocks Broken : ", String.valueOf(pit.getBlockBroken()), true);
        pitEmbed.addField("Fishing Rod Launched : ", String.valueOf(pit.getFishingRodLaunched()), true);
        pitEmbed.addField("Arrows Fired : ", String.valueOf(pit.getArrowsFired()), true);
        pitEmbed.addField("Arrows Hits : ", String.valueOf(pit.getArrowHits()), true);
        pitEmbed.addField("GApple Eaten : ", String.valueOf(pit.getGappleEaten()), true);
        pitEmbed.addField("GHead Eaten : ", String.valueOf(pit.getGheadEaten()), true);

        pitEmbed.addBlankField(false);
        pitEmbed.addField("Melee Damage Dealt : ", String.valueOf(pit.getDamageDealt().getMelee()), true);
        pitEmbed.addField("Bow Damage Dealt : ", String.valueOf(pit.getDamageDealt().getBow()), true);

        pitEmbed.addBlankField(false);
        pitEmbed.addField("Melee Damage Taken : ", String.valueOf(pit.getDamageTaken().getMelee()), true);
        pitEmbed.addField("Bow Damage Taken : ", String.valueOf(pit.getDamageTaken().getBow()), true);

        return pitEmbed;
    }
}
