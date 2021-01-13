package fr.kokhaviel.bot.commands.moderation;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UnbanCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Guild guild = event.getGuild();
        final MessageChannel channel = event.getChannel();
        final Member author = message.getMember();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "unban")) {

            if (guild == null) {

                channel.sendMessage("You must execute this command on server !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (!author.hasPermission(Permission.BAN_MEMBERS)) {

                channel.sendMessage("You dont have the permission to unban member !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length < 2) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "unban <User ID>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 2) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "unban <User ID>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                guild.unban(args[1]).queue(
                        success -> channel.sendMessage("Successfully Unban !").queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                        error -> channel.sendMessage("Unable To Unban").queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS))
                );

                message.delete().queueAfter(2, TimeUnit.SECONDS);
            }

        }


    }
}
