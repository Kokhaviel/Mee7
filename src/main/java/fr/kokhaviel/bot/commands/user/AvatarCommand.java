package fr.kokhaviel.bot.commands.user;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AvatarCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {


        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "avatar")) {

            message.delete().queue();

            if (args.length < 2) {

                channel.sendMessage("Missing Arguments : Please Use !avatar <User ID> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 2) {

                channel.sendMessage("Too Arguments : Please Use !avatar <User ID> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                Member target = guild.getMemberById(args[1]);

                EmbedBuilder avatarEmbed = new EmbedBuilder();

                avatarEmbed.setTitle("Avatar of " + target.getUser().getAsTag());
                avatarEmbed.setColor(Color.YELLOW);
                avatarEmbed.setAuthor("Avatar");
                avatarEmbed.setThumbnail(target.getUser().getAvatarUrl());

                channel.sendMessage(avatarEmbed.build()).queue();

            }

        }


    }

}
