package fr.kokhaviel.bot.commands.fun;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RandomCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final MessageChannel channel = event.getChannel();
        final String[] args = message.getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Config.PREFIX + "random")) {

            int first = Integer.parseInt(args[1]);

            int second = Integer.parseInt(args[2]);

            if (args.length < 3) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "random <Int1> <Int2>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 3) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "random <Int1> <Int2>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                channel.sendMessage("Giving you a number between " + first + " and " + second + " : " + new Random().ints(first, second).findFirst().getAsInt()).queue();

                message.delete().queueAfter(2, TimeUnit.SECONDS);

            }

        }

    }

}
