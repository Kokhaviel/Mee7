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

package fr.kokhaviel.bot.commands.server;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ServerInfoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final JDA jda = event.getJDA();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "serverinfo")) {
            message.delete().queue();
            Guild guild = event.getGuild();
            if (args.length > 1) {
                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "serverinfo").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {
                channel.sendMessage(getServerInfo(guild, jda).build()).queue();
            }
        }
    }

    private EmbedBuilder getServerInfo(Guild guild, JDA jda) {
        EmbedBuilder serverinfoEmbed = new EmbedBuilder();

        serverinfoEmbed.setTitle(guild.getName() + " Server Info")
                .setColor(Color.CYAN)
                .setThumbnail(guild.getIconUrl())
                .setAuthor("Server Info", null, jda.getSelfUser().getAvatarUrl());

        serverinfoEmbed.addField("Name : ", guild.getName(), false);
        serverinfoEmbed.addField("Owner : ", guild.getOwner().getUser().getAsTag(), false);
        if (guild.getIconUrl() != null) serverinfoEmbed.addField("Icon : ", guild.getIconUrl(), false);
        if (guild.getBannerUrl() != null) serverinfoEmbed.addField("Banner : ", guild.getBannerUrl(), false);
        serverinfoEmbed.addField("Region : ", guild.getRegion().getName(), false);
        if (guild.getDescription() != null)
            serverinfoEmbed.addField("Description : ", guild.getDescription(), false);
        serverinfoEmbed.addField("Boost Tier : ", guild.getBoostTier().name(), false);
        if (guild.getSystemChannel() != null)
            serverinfoEmbed.addField("System Channel : ", guild.getSystemChannel().getAsMention(), false);

        return serverinfoEmbed;
    }

}