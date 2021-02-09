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

package fr.kokhaviel.bot.commands.user;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AvatarCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "avatar")) {
            message.delete().queue();
            if (args.length < 2) {
                channel.sendMessage("Missing Arguments : Please Use !avatar <User ID> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else if (args.length > 2) {
                channel.sendMessage("Too Arguments : Please Use !avatar <User ID> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {
                Member target = guild.getMemberById(args[1]);
                assert target != null;
                channel.sendMessage(getAvatar(target).build()).queue();
            }
        }
    }

    private EmbedBuilder getAvatar(Member target) {

        EmbedBuilder avatarEmbed = new EmbedBuilder();
        avatarEmbed.setTitle("Avatar of " + target.getUser().getAsTag());
        avatarEmbed.setColor(Color.YELLOW);
        avatarEmbed.setAuthor("Avatar");
        avatarEmbed.setThumbnail(target.getUser().getAvatarUrl());

        return avatarEmbed;
    }
}
