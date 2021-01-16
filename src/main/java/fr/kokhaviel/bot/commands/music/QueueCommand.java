package fr.kokhaviel.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.GuildMusicManager;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final Guild guild = event.getGuild();
        final TextChannel channel = (TextChannel) event.getChannel();
        final String iconUrl = event.getGuild().getIconUrl();
        final String[] args = message.getContentRaw().split("\\s+");



        if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "queue")) {

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            event.getMessage().delete().queue();

            final int trackCount = Math.min(queue.size(), 10);
            final List<AudioTrack> trackList = new ArrayList<>(queue);

            if(queue.isEmpty()) {

                channel.sendMessage("Queue is currently empty !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {

                EmbedBuilder queueEmbed = new EmbedBuilder();

                queueEmbed.setTitle("Current Queue :");
                queueEmbed.setColor(Color.ORANGE);
                queueEmbed.setAuthor("Queue Command", null, iconUrl);
                queueEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");



                for (int i = 0; i < trackCount; i++) {

                    final AudioTrack track = trackList.get(i);
                    final String title = track.getInfo().title;
                    final String author = track.getInfo().author;

                    queueEmbed.addField(title + " `[" + timeFormat(track.getDuration()) + "]`", author, false);

                }

                if(trackList.size() > trackCount) {

                    queueEmbed.addField("And " + String.valueOf(trackList.size() - trackCount) +" More ...", "", false);

                }

            channel.sendMessage(queueEmbed.build()).queue();

            }
        }
    }

    private String timeFormat(long timeMillis) {

        final long hours = timeMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
