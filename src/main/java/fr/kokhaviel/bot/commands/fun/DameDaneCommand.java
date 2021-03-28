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

package fr.kokhaviel.bot.commands.fun;

import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DameDaneCommand extends ListenerAdapter {

    List<String> urlList = Arrays.asList("https://cdn.discordapp.com/attachments/585734993922359297/796482442142679050/dame-da-ne-full-song.mp4",
            "https://cdn.discordapp.com/attachments/585734993922359297/796118169361448990/generated_3.mp4",
            "https://cdn.discordapp.com/attachments/585734993922359297/796110237702553600/devvebaka.mp4");

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
                .getAsJsonObject().get(event.getGuild().getId())
                .getAsJsonObject().get("prefix").getAsString();

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(prefix + "damedane")) {
            int rd = new Random().nextInt(urlList.size());
            channel.sendMessage(urlList.get(rd)).queue();
        }
    }
}
