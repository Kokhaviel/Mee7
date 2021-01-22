package fr.kokhaviel.bot.commands.moderation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ClearCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Member member = event.getMember();
        final TextChannel textChannel = event.getTextChannel();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "clear")) {

            if (args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "clear <Int>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 2) {

                message.delete().queue();

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "clear <Int>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {
                assert member != null;
                if (!member.hasPermission(Permission.MESSAGE_MANAGE)) {

                    message.delete().queue();

                    channel.sendMessage("Missing Permission : You Cannot Manage Messages !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else {

                    int numToDelete = Integer.parseInt(args[1]);

                    List<Message> toDelete = channel.getHistory().retrievePast(numToDelete).complete();

                    textChannel.deleteMessages(toDelete).queue(
                            success -> channel.sendMessage("Successfully Delete " + numToDelete + " Messages !").queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));

                }
            }

        }

    }

}
