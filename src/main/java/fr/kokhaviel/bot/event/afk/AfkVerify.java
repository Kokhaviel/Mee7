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

import java.awt.Color;
import java.util.List;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.Mee7;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AfkVerify extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        List<Member> members = event.getMessage().getMentionedMembers();

        for (Member member : members) {

            if (Mee7.afkIDs.contains(member.getId())) {

                EmbedBuilder afkMentionEmbed = new EmbedBuilder();

                afkMentionEmbed.setTitle("Afk Member Mention")
                        .setDescription("This user is afk ...")
                        .setColor(Color.RED)
                        .setThumbnail(member.getUser().getAvatarUrl())
                        .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + guild.getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                        .addField("Member Mentioned : ", member.getUser().getAsTag(), false);

                channel.sendMessage(afkMentionEmbed.build()).queue();


            }

        }

    }

}
