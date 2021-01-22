package fr.kokhaviel.bot.commands.moderation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BanCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final String[] args = event.getMessage().getContentRaw().split("\\s+");


        if (args[0].equalsIgnoreCase(Config.PREFIX + "ban")) {

            final MessageChannel channel = event.getChannel();
            final Guild guild = event.getGuild();
            final Message message = event.getMessage();
            final Member author = message.getMember();


            if (args.length < 3) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "ban <@User> <Reason>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));


            } else if (args.length > 3) {

                message.delete().queue();

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "ban <@User> <Reason>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Member> mentionedMembers = message.getMentionedMembers();

                Member target = mentionedMembers.get(0);

                message.delete().queue();


                assert author != null;
                if (!author.hasPermission(Permission.BAN_MEMBERS)) {

                    channel.sendMessage("You don't have the permission to ban member !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else if (author.getRoles().get(0).getPosition() <= target.getRoles().get(0).getPosition()) {

                    channel.sendMessage("You Cannot Ban This Member").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else {
                    guild.ban(target, 3, args[2]).queue(
                            success -> channel.sendMessage("Successfully Banned " + target.getUser().getAsTag() + " !").queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                            error -> channel.sendMessage("Unable To Ban " + target.getUser().getAsTag() + " !").queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));

                }
            }
        }
    }
}
