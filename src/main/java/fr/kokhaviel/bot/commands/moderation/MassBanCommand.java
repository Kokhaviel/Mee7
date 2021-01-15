package fr.kokhaviel.bot.commands.moderation;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MassBanCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "massban")) {

            if (args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : You must mention at least one member to ban !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Member> mentionedMembers = message.getMentionedMembers();

                String reason = args[args.length - 1];

                message.delete().queue();

                for (Member member : mentionedMembers) {

                    guild.ban(member, 3, reason).queue(
                            success -> channel.sendMessage("Successfully Banned " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                            error -> channel.sendMessage("Unable To Ban " + member.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)));
                }
            }
        }
    }
}
