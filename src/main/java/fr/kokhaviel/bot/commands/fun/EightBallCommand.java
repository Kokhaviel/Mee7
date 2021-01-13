package fr.kokhaviel.bot.commands.fun;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EightBallCommand extends ListenerAdapter {

    List<String> answer = Arrays.asList(
            "It is certain.",
            "As I see it, yes.",
            "Reply hazy, try again.",
            "Don't count on it.",
            "It is decidedly so.",
            "Most likely.",
            "Ask again later.",
            "My reply is no.",
            "Without a doubt.",
            "Outlook good.",
            "Better not tell you now.",
            "My sources say no.",
            "Yes – definitely.",
            "Yes.",
            "Cannot predict now.",
            "Outlook not so good.",
            "You may rely on it.",
            "Signs point to yes.",
            "Concentrate and ask again.",
            "Very doubtful.");


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();


        if (args[0].equalsIgnoreCase(Config.PREFIX + "8ball")) {

            if (args.length < 2) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Missing Argument : Please Use " + Config.PREFIX + "8ball <Question?>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                int ballRandom = new Random().nextInt(20);

                channel.sendMessage(answer.get(ballRandom)).queue();
            }
        }
    }
}
