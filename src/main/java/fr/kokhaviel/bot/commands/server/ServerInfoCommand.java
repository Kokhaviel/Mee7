package fr.kokhaviel.bot.commands.server;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ServerInfoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final JDA jda = event.getJDA();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "serverinfo")) {

            Guild guild = event.getGuild();

            if (args.length > 1) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "serverinfo").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                EmbedBuilder serverinfoEmbed = new EmbedBuilder();

                serverinfoEmbed.setTitle(guild.getName() + " Server Info")
                        .setColor(Color.CYAN)
                        .setThumbnail(guild.getIconUrl())
                        .setAuthor("Server Info", null, jda.getSelfUser().getAvatarUrl());

                serverinfoEmbed.addField("Name : ", guild.getName(), false);
                serverinfoEmbed.addField("Owner : ", guild.getOwner().getUser().getAsTag(), false);
                if (guild.getIconUrl() != null) serverinfoEmbed.addField("Icon : ", guild.getIconUrl(), false);
                if (guild.getBannerUrl() != null) serverinfoEmbed.addField("Banner : ", guild.getBannerUrl(), false);
                serverinfoEmbed.addField("Region : ", guild.getRegion().getName(), false);
                if (guild.getDescription() != null)
                    serverinfoEmbed.addField("Description : ", guild.getDescription(), false);
                serverinfoEmbed.addField("Boost Tier : ", guild.getBoostTier().name(), false);
                if (guild.getSystemChannel() != null)
                    serverinfoEmbed.addField("System Channel : ", guild.getSystemChannel().getAsMention(), false);

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage(serverinfoEmbed.build()).queue();

            }

        }


    }
}
