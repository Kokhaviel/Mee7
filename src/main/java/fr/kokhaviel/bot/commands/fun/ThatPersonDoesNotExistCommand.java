package fr.kokhaviel.bot.commands.fun;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ThatPersonDoesNotExistCommand extends ListenerAdapter {

    //DOESN'T WORK FOR THE MOMENT

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.PREFIX + "tpdne")) {

            channel.sendMessage("This Person Doesn't Exist !").queue();

            File file = new File("thispersondoesnotexist.com/image");
            channel.sendFile(file, "tpdne.png").append("Okay :)").queue();

        }
    }


}
