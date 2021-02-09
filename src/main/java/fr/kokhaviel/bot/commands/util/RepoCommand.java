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

package fr.kokhaviel.bot.commands.util;

import fr.kokhaviel.bot.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class RepoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final JDA jda = event.getJDA();
        final MessageChannel channel = event.getChannel();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "repo")) {
            message.delete().queue();
            EmbedBuilder repoEmbed = new EmbedBuilder();

            repoEmbed.setTitle("Repository Links");
            repoEmbed.setColor(Color.magenta);
            repoEmbed.setAuthor("Repo Menu", null, jda.getSelfUser().getAvatarUrl());
            repoEmbed.setDescription("Display Repository Links");

            repoEmbed.addField("GitHub : ", "[GitHub Link](https://github.com/Kokhaviel/Mee7)", false);
            repoEmbed.addField("Git : ", "git clone https://github.com/Kokhaviel/Mee7.git", false);
            repoEmbed.addField("SSH : ", "git@github.com:Kokhaviel/Mee7.git", false);

            repoEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nCommand Requested by : " + message.getAuthor(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

            channel.sendMessage(repoEmbed.build()).queue();
        }
    }
}
