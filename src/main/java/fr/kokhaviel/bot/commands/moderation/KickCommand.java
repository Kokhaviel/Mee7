package fr.kokhaviel.bot.commands.moderation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class KickCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Guild guild = event.getGuild();
        final Member author = message.getMember();

        List<Member> mentionedMembers = message.getMentionedMembers();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "kick")) {

            if (args.length < 3) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "kick <@User> <Reason>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 3) {

                message.delete().queue();

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "kick <@User> <Reason>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                message.delete().queue();

                if (guild == null) {

                    channel.sendMessage("You must execute this command on server !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else if (!author.hasPermission(Permission.KICK_MEMBERS)) {

                    channel.sendMessage("You dont have the permission to kick member !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else if (mentionedMembers.isEmpty()) {

                    channel.sendMessage("You must mention the member you want to kick !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

                } else {

                    Member target = mentionedMembers.get(0);

                    guild.kick(target, args[2]).queue(
                            sucess -> channel.sendMessage("Successfully Kicked " + target.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
                            error -> channel.sendMessage("Unable To Kick " + target.getUser().getAsTag()).queue(
                                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS))

                    );
                }
            }
        }
    }
}
