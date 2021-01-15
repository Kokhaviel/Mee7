package fr.kokhaviel.bot.commands.util;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RepoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final JDA jda = event.getJDA();
        final MessageChannel channel = event.getChannel();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "repo")) {

            message.delete().queue();

            EmbedBuilder repoEmbed = new EmbedBuilder();

            repoEmbed.setTitle("Repository Links");
            repoEmbed.setColor(Color.magenta);
            repoEmbed.setAuthor("Repo Menu", null, jda.getSelfUser().getAvatarUrl());
            repoEmbed.setDescription("Display Repository Links");

            repoEmbed.addField("GitHub : ", "[GitHub Link](https://github.com/Kokhaviel/Mee7)", false);
            repoEmbed.addField("Git : ", "git clone https://github.com/Kokhaviel/Mee7.git", false);
            repoEmbed.addField("SSH : ", "git@github.com:Kokhaviel/Mee7.git", false);

            repoEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nCommand Requested by : " + message.getAuthor(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

            channel.sendMessage(repoEmbed.build()).queue();

        }

    }
}
