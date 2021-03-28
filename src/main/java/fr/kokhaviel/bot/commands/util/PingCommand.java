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

import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class PingCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
                .getAsJsonObject().get(event.getGuild().getId())
                .getAsJsonObject().get("prefix").getAsString();

        long time = System.currentTimeMillis();

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();


        if (args[0].equalsIgnoreCase(prefix + "ping")) {
            message.delete().queue();
            channel.sendMessage("Pong").queue(
                    response -> response.editMessageFormat("Pong : %d ms", System.currentTimeMillis() - time).queue());

        }
    }
}