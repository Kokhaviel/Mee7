package fr.kokhaviel.bot.commands.fun;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SayCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {


        final Message message = event.getMessage();
        final MessageChannel channel = event.getChannel();
        final String[] args = message.getContentRaw().split("\\s+");


        StringBuilder sayBuilder = new StringBuilder();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "say")) {


            if (args.length < 2) {

                message.delete().queue();

                channel.sendMessage("Missing Arguments : Please Specify At Least One Word To Say !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                for (String arg : args) {

                    if (arg == args[0]) continue;

                    sayBuilder.append(arg).append(" ");
                }

                message.delete().queue();

                channel.sendMessage(sayBuilder).queue();

            }

        }

    }

}
