package fr.kokhaviel.bot.commands.moderation;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MassBanCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.PREFIX + "massban")) {

            if(args.length < 2) {

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                event.getChannel().sendMessage("Missing Arguments : You must mention at least one member to ban !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

                String reason = args[args.length -1];

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                for (Member member : mentionedMembers) {

                    event.getGuild().ban(member, 3, reason).queue(
                        success -> event.getChannel().sendMessage("Successfully Banned " + member.getUser().getAsTag()).queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                        error -> event.getChannel().sendMessage("Unable To Ban " + member.getUser().getAsTag()).queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));
                }
            }
        }
    }
}
