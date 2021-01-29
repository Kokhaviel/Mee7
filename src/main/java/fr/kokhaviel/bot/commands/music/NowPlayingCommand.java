/*
 * Copyright (C) 2021 Kokhaviel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.kokhaviel.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.GuildMusicManager;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class NowPlayingCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Member member = event.getMember();
        final Guild guild = event.getGuild();
        final Member selfMember = guild.getSelfMember();

        if (args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "nowplaying")) {

            final GuildVoiceState selfVoiceState = selfMember.getVoiceState();
            final GuildVoiceState voiceState = member.getVoiceState();

            message.delete().queue();

            if (!voiceState.inVoiceChannel()) {

                channel.sendMessage("You need to be in a voice channel to this command works").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (!selfVoiceState.inVoiceChannel()) {

                channel.sendMessage("I need to be in a voice channel to this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (!voiceState.getChannel().equals(selfVoiceState.getChannel())) {

                channel.sendMessage("You need to be in the same voice channel as me for this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
                final AudioPlayer audioPlayer = musicManager.audioPlayer;
                final AudioTrack playingTrack = audioPlayer.getPlayingTrack();
                final AudioTrackInfo trackInfo = playingTrack.getInfo();

                if (playingTrack == null) {

                    channel.sendMessage("THere is no track playing currently !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    channel.sendMessage("Now Playing '" + trackInfo.title + "' by '" + trackInfo.author + "' !").queue();

                    channel.sendMessageFormat(
                            "Current Track Time Elapsed : `[%s]`\nCurrent Track Time Left : `[%s]`",
                            timeFormat(playingTrack.getPosition()),
                            timeFormat(playingTrack.getDuration() - playingTrack.getPosition())
                    ).queue();
                }
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
