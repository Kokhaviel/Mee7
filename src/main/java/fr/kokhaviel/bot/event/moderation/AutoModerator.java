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

package fr.kokhaviel.bot.event.moderation;

import java.awt.Color;
import java.util.*;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import fr.kokhaviel.bot.Config;

public class AutoModerator extends ListenerAdapter {

    List<String> badWords = new ArrayList<>(Arrays.asList("Fuck", "fdp"));

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        for (String arg : args) {
            if (badWords.contains(arg)) {
                EmbedBuilder badwordDetect = new EmbedBuilder();

                badwordDetect.setTitle("Badword Detected :");
                badwordDetect.setColor(Color.red);
                badwordDetect.setThumbnail("https://cdn.discordapp.com/avatars/585419690411819060/4f6dc909ca93e98f8610dce9087ed747.webp?size=128");
                badwordDetect.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + guild.getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

                badwordDetect.addField("Badword : ", arg, false);

                channel.sendMessage(badwordDetect.build()).queue();
                message.delete().queue();
            }
        }
    }
}
