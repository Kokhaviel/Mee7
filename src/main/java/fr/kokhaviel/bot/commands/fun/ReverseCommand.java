package fr.kokhaviel.bot.commands.fun;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
public class ReverseCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final TextChannel channel = (TextChannel) event.getChannel();
        final Message message = event.getMessage();
        final String contentMessage = message.getContentRaw();
        final String[] args = contentMessage.split("\\s+");

        if(args[0].equalsIgnoreCase(Config.PREFIX + "reverse")) {

            StringBuilder result = new StringBuilder(contentMessage.substring(9));

            channel.sendMessage(result.reverse()).queue();

        }
    }
}