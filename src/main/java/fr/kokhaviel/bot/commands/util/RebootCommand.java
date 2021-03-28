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
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class RebootCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
                .getAsJsonObject().get(event.getGuild().getId())
                .getAsJsonObject().get("prefix").getAsString();

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final User author = message.getAuthor();
        final JDA jda = event.getJDA();


        if (args[0].equalsIgnoreCase(prefix + "reboot") && author.getId().equals(Config.OWNER_ID)) {
            message.delete().queue();
            jda.shutdown();
            new Mee7();
        }
    }
}
