package fr.kokhaviel.bot.commands.user.afk;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.Mee7;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AfkCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Member member = event.getMember();
        final MessageChannel channel = event.getChannel();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "afk")) {

            message.delete().queue();

            assert member != null;
            if (!Mee7.afkIDs.contains(member.getId())) {

                Mee7.afkIDs.add(member.getId());

                channel.sendMessage("Successfully Set Your AFK !").queue(delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));


            } else {

                Mee7.afkIDs.remove(member.getId());

                channel.sendMessage("Successfully Removed Your AKF !").queue(delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));

            }

        }


    }


}
