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

package fr.kokhaviel.bot.event.afk;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Mee7;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.List;

public class AfkVerify extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();
        final List<Member> members = event.getMessage().getMentionedMembers();

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject AFK_OBJECT = LANG_OBJECT.get("afk").getAsJsonObject();

        for (Member member : members) {
            if (Mee7.afkIDs.contains(member.getId())) {
                EmbedBuilder afkMentionEmbed = new EmbedBuilder();

                afkMentionEmbed.setTitle(AFK_OBJECT.get("afk_embed_title").getAsString())
                        .setDescription(AFK_OBJECT.get("afk_embed_description").getAsString())
                        .setColor(Color.RED)
                        .setThumbnail(member.getUser().getAvatarUrl())
                        .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + guild.getName(), Config.DEVELOPER_AVATAR)

                        .addField(AFK_OBJECT.get("afk_member_mentioned").getAsString(), member.getUser().getAsTag(), false);

                channel.sendMessage(afkMentionEmbed.build()).queue();
            }
        }
    }
}
