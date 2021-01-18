package fr.kokhaviel.bot.commands.fun;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DameDaneCommand extends ListenerAdapter {

    List<String> urlList = Arrays.asList("https://cdn.discordapp.com/attachments/585734993922359297/796482442142679050/dame-da-ne-full-song.mp4",
            "https://cdn.discordapp.com/attachments/585734993922359297/796118169361448990/generated_3.mp4",
            "https://cdn.discordapp.com/attachments/585734993922359297/796110237702553600/devvebaka.mp4");

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.PREFIX + "damedane")) {

            int rd = new Random().nextInt(urlList.size());

            channel.sendMessage(urlList.get(rd)).queue();

        }

    }
}
