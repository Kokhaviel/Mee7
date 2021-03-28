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
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

import java.awt.*;
import java.io.File;

import static java.lang.String.format;

public class AboutCommand extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
                .getAsJsonObject().get(event.getGuild().getId())
                .getAsJsonObject().get("prefix").getAsString();

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject ABOUT_OBJECT = LANG_OBJECT.get("about").getAsJsonObject();

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Member member = event.getMember();
        final JDA jda = event.getJDA();
        final MessageChannel channel = event.getChannel();
        final User author = event.getAuthor();

        if (args[0].equalsIgnoreCase(prefix + "about")) {

            message.delete().queue();
            User user = member.getUser();
            channel.sendMessage(author.getAsMention() + ABOUT_OBJECT.get("success_guild_message").getAsString()).queue();
            if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
            ((UserImpl) user).getPrivateChannel().sendMessage(getAbout(jda, prefix, ABOUT_OBJECT).build()).queue();
        }
    }

    private EmbedBuilder getAbout(JDA jda, String prefix, JsonObject aboutObject) {

        EmbedBuilder aboutEmbed = new EmbedBuilder();

        aboutEmbed.setTitle(aboutObject.get("about_menu").getAsString())
                .setColor(Color.GREEN)
                .setAuthor(aboutObject.get("mee7_description").getAsString())
                .setThumbnail(jda.getSelfUser().getAvatarUrl())

                .addField(format("%s : ", aboutObject.get("developer").getAsString()), "Kokhaviel.java#0001", false)
                .addField("Github : ", "[Github Repository](https://github.com/Kokhaviel/Mee7)", false)
                .addField("Prefix : ", prefix, false)
                .addField("Help : ", prefix + "help", false)
                .addField(format("%s : ", aboutObject.get("license").getAsString()), "[GPLv3](https://fsf.org/)", false)
                .addField(format("%s : ", aboutObject.get("libraries")), "[JDA](https://github.com/DV8FromTheWorld/JDA), [LavaPlayer](https://github.com/sedmelluq/lavaplayer), [Slothpixel](https://docs.slothpixel.me/) ", false);

        return aboutEmbed;
    }
}
