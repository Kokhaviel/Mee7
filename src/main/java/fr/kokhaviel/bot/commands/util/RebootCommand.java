package fr.kokhaviel.bot.commands.util;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.Mee7;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RebootCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final User author = message.getAuthor();
        final JDA jda = event.getJDA();


        if (args[0].equalsIgnoreCase(Config.PREFIX + "reboot") && author.getId().equals(Config.OWNER_ID)) {

            message.delete().queue();

            jda.shutdown();

            new Mee7();

        }


    }


}
