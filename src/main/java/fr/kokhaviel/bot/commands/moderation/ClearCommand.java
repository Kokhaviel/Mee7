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

import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ClearCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Member member = event.getMember();
        final TextChannel textChannel = event.getTextChannel();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "clear")) {

            if (args.length < 2) {
                message.delete().queue();
                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "clear <Int>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else if (args.length > 2) {
                message.delete().queue();
                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "clear <Int>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {
                assert member != null;
                if (!member.hasPermission(Permission.MESSAGE_MANAGE)) {
                    message.delete().queue();
                    channel.sendMessage("Missing Permission : You Cannot Manage Messages !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    int numToDelete = Integer.parseInt(args[1]);
                    List<Message> toDelete = channel.getHistory().retrievePast(numToDelete).complete();
                    textChannel.deleteMessages(toDelete).queue(
                            success -> channel.sendMessage("Successfully Delete " + numToDelete + " Messages !").queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));

                }
            }
        }
    }
}
