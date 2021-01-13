package fr.kokhaviel.bot.commands.util;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        long time = System.currentTimeMillis();

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();


        if (args[0].equalsIgnoreCase(Config.PREFIX + "ping")) {

            channel.sendMessage("Pong").queue(
                    reponse -> reponse.editMessageFormat("Pong : %d ms", System.currentTimeMillis() - time).queue());

            message.delete().queueAfter(2, TimeUnit.SECONDS);

        }


    }

}