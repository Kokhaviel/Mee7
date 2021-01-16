package fr.kokhaviel.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.GuildMusicManager;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class TimeLeftCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Guild guild = event.getGuild();

        if (args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "timeleft")) {

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            final AudioTrack playingTrack = audioPlayer.getPlayingTrack();

            if (playingTrack == null) {

                channel.sendMessage("Queue is currently empty !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {


                channel.sendMessageFormat("Current Track Time Elapsed : `[%s]`\nCurrent Track Time Left : `[%s]`", timeFormat(playingTrack.getPosition()), timeFormat(playingTrack.getDuration() - playingTrack.getPosition())).queue();
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
