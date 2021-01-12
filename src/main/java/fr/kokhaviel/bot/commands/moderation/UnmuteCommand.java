package fr.kokhaviel.bot.commands.moderation;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnmuteCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.PREFIX + "unmute")) {

            if(args.length < 2) {

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                event.getChannel().sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "unmute <@User>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if(args.length > 2) {

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                event.getChannel().sendMessage("Too Arguments : Please Use " + Config.PREFIX + "unmute <@User>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

                List<Role> roles = event.getGuild().getRolesByName("Muted", true);

                Member target = mentionedMembers.get(0);

                Role role;

                if (roles.isEmpty()) {
                    event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                    event.getChannel().sendMessage("I Don't Found a Role Named \"Muted\" ... ").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else {

                    role = roles.get(0);

                    event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                    event.getGuild().removeRoleFromMember(target.getId(), role).queue();

                    event.getChannel().sendMessage("Successfully Muted " + target.getAsMention()).queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                }
            }
        }

    }
}
