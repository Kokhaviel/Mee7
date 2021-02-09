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

package fr.kokhaviel.bot.commands.moderation;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MassBanCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "massban")) {

            if (args.length < 2) {
                message.delete().queue();
                channel.sendMessage("Missing Arguments : You must mention at least one member to ban !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {

                List<Member> mentionedMembers = message.getMentionedMembers();
                String reason = args[args.length - 1];
                message.delete().queue();

                for (Member member : mentionedMembers) {
                    guild.ban(member, 3, reason).queue(
                            success -> channel.sendMessage("Successfully Banned " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                            error -> channel.sendMessage("Unable To Ban " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));
                }
            }
        }
    }
}