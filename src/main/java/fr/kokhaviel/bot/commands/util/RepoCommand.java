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

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;

public class RepoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
                .getAsJsonObject().get(event.getGuild().getId())
                .getAsJsonObject().get("prefix").getAsString();

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject REPO_OBJECT = LANG_OBJECT.get("repo").getAsJsonObject();

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final JDA jda = event.getJDA();
        final MessageChannel channel = event.getChannel();

        if (args[0].equalsIgnoreCase(prefix + "repo")) {
            message.delete().queue();
            EmbedBuilder repoEmbed = new EmbedBuilder();

            repoEmbed.setTitle(REPO_OBJECT.get("repo_links").getAsString());
            repoEmbed.setColor(Color.MAGENTA);
            repoEmbed.setAuthor(REPO_OBJECT.get("repo_menu").getAsString(), null, jda.getSelfUser().getAvatarUrl());
            repoEmbed.setDescription(REPO_OBJECT.get("repo_display").getAsString());

            repoEmbed.addField("GitHub : ", "[GitHub Link](https://github.com/Kokhaviel/Mee7)", false);
            repoEmbed.addField("Git : ", "git clone https://github.com/Kokhaviel/Mee7.git", false);
            repoEmbed.addField("SSH : ", "git@github.com:Kokhaviel/Mee7.git", false);

            repoEmbed.setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("command_requested_by").getAsString() + message.getAuthor().getAsTag(), Config.DEVELOPER_AVATAR);

            channel.sendMessage(repoEmbed.build()).queue();
        }
    }
}
