package fr.kokhaviel.bot.commands.moderation;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MassKickCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "masskick")) {

            if (args.length < 2) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Missing Arguments : You must mention at least one member to kick !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Member> mentionedMembers = message.getMentionedMembers();

                String reason = args[args.length - 1];

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                for (Member member : mentionedMembers) {

                    guild.kick(member, reason).queue(
                            success -> channel.sendMessage("Successfully Kicked " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                            error -> channel.sendMessage("Unable To Kick " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));

                }
            }
        }
    }
}
