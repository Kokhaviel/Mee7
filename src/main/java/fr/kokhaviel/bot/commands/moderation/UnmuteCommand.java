package fr.kokhaviel.bot.commands.moderation;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnmuteCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "unmute")) {

            message.delete().queue();

            if (args.length < 2) {

                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "unmute <@User>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 2) {

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "unmute <@User>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Member> mentionedMembers = message.getMentionedMembers();

                List<Role> roles = guild.getRolesByName("Muted", true);

                Member target = mentionedMembers.get(0);

                Role role;

                if (roles.isEmpty()) {

                    channel.sendMessage("I Don't Found a Role Named \"Muted\" ... ").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else {

                    role = roles.get(0);

                    guild.removeRoleFromMember(target.getId(), role).queue();

                    channel.sendMessage("Successfully Muted " + target.getAsMention()).queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                }
            }
        }

    }
}
