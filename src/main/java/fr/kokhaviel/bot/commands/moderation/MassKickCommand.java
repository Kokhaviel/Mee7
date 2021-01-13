package fr.kokhaviel.bot.commands.moderation;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MassKickCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.PREFIX + "masskick")) {

            if(args.length < 2) {

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                event.getChannel().sendMessage("Missing Arguments : You must mention at least one member to kick !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Member> mentionesMembers = event.getMessage().getMentionedMembers();

                String reason = args[args.length -1];

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                for (Member member : mentionesMembers) {

                    event.getGuild().kick(member, reason).queue(
                            success -> event.getChannel().sendMessage("Successfully Kicked " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                            error -> event.getChannel().sendMessage("Unable To Kick " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));

                }
            }
        }
    }
}
