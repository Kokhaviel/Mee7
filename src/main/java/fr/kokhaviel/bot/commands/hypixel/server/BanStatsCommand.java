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

package fr.kokhaviel.bot.commands.hypixel.server;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.bans.Bans;

import java.awt.*;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BanStatsCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "bans")) {

            message.delete().queue();


            Bans bans = sloth.getBans();

            channel.sendMessage(getBansStats(event, bans).build()).queue();

        }
    }

    private EmbedBuilder getBansStats(MessageReceivedEvent event, Bans bans) {

        EmbedBuilder bansEmbed = new EmbedBuilder();
        bansEmbed.setAuthor("Hypixel Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        bansEmbed.setColor(Color.RED);
        bansEmbed.setTitle("Bans Stats");
        bansEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        bansEmbed.addField("Last Minute Watchdog Bans : ", String.valueOf(bans.getWatchdog().getLastMinute()), true);
        bansEmbed.addField("Daily Watchdog Bans : ", String.valueOf(bans.getWatchdog().getDaily()), true);
        bansEmbed.addField("Total Watchdog Bans : ", String.valueOf(bans.getWatchdog().getTotal()), true);

        bansEmbed.addBlankField(false);
        bansEmbed.addField("Daily Staff Bans : ", String.valueOf(bans.getStaff().getDaily()), true);
        bansEmbed.addField("Total Staff Bans : ", String.valueOf(bans.getStaff().getTotal()), true);

        return bansEmbed;
    }

}
