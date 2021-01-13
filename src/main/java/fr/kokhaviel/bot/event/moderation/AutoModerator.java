package fr.kokhaviel.bot.event.moderation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import fr.kokhaviel.bot.Config;

public class AutoModerator extends ListenerAdapter {

    List<String> badWords = new ArrayList<>(Arrays.asList("Fuck", "fdp"));

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        for (String arg : args) {

            if (badWords.contains(arg)) {


                EmbedBuilder badwordDetect = new EmbedBuilder();

                badwordDetect.setTitle("Badword Detected :");
                badwordDetect.setColor(Color.red);
                badwordDetect.setThumbnail("https://cdn.discordapp.com/avatars/585419690411819060/4f6dc909ca93e98f8610dce9087ed747.webp?size=128");
                badwordDetect.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + guild.getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

                badwordDetect.addField("Badword : ", arg, false);

                channel.sendMessage(badwordDetect.build()).queue();

                message.delete().queue();

            }

        }


    }

}
