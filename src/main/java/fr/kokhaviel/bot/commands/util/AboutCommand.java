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

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

import java.awt.*;

public class AboutCommand extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Member member = event.getMember();
        final JDA jda = event.getJDA();
        final MessageChannel channel = event.getChannel();
        final User author = event.getAuthor();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "about")) {

            message.delete().queue();
            User user = member.getUser();
            channel.sendMessage(author.getAsMention() + ", a message will be send to your DM !").queue();
            if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
            ((UserImpl) user).getPrivateChannel().sendMessage(getAbout(jda).build()).queue();
        }
    }

    private EmbedBuilder getAbout(JDA jda) {

        EmbedBuilder aboutEmbed = new EmbedBuilder();

        aboutEmbed.setTitle("About Menu")
                .setColor(Color.GREEN)
                .setAuthor("Mee7 : A Simple Java Discord Bot ")
                .setThumbnail(jda.getSelfUser().getAvatarUrl())

                .addField("Developer : ", "Kokhaviel.java#0001", false)
                .addField("Github : ", "[Github Repository](https://github.com/Kokhaviel/Mee7)", false)
                .addField("Prefix : ", Config.PREFIX, false)
                .addField("Help : ", Config.PREFIX + "help", false)
                .addField("License : ", "[GPLv3](https://fsf.org/)", false)
                .addField("Libraries : ", "[JDA](https://github.com/DV8FromTheWorld/JDA), [LavaPlayer](https://github.com/sedmelluq/lavaplayer), [Slothpixel](https://docs.slothpixel.me/) ", false);

        return aboutEmbed;
    }
}
