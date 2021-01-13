package fr.kokhaviel.bot.commands.user;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InfoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final JDA jda = event.getJDA();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "userinfo")) {


            TextChannel channel = (TextChannel) event.getChannel();

            if (args.length < 2) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Missing Argument : Please Use " + Config.PREFIX + "userinfo <@User> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 2) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "userinfo <@User> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));


            } else {


                List<Member> mentionedMembers = message.getMentionedMembers();

                Member target = mentionedMembers.get(0);

                EmbedBuilder userInfoEmbed = new EmbedBuilder();

                userInfoEmbed.setTitle(target.getUser().getName() + " User Info")
                        .setColor(Color.CYAN)
                        .setThumbnail(target.getUser().getAvatarUrl())
                        .setAuthor("User Info", null, jda.getSelfUser().getAvatarUrl());

                userInfoEmbed.addField("ID : ", target.getId(), false);

                if (target.getNickname() != null) userInfoEmbed.addField("Nickname : ", target.getNickname(), false);

                userInfoEmbed.addField("Joined on ", target.getTimeJoined().toString(), false);
                userInfoEmbed.addField("Account Created on ", target.getTimeCreated().toString(), false);
                userInfoEmbed.addField("Status", target.getOnlineStatus().getKey(), false);
                userInfoEmbed.addField("Activities : ", target.getActivities().toString(), false);

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage(userInfoEmbed.build()).queue();

            }


        }


    }

}
