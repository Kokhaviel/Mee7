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

package fr.kokhaviel.bot.music;

import com.google.gson.JsonObject;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayerManager {

    public static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager playerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerLocalSource(this.playerManager);
        AudioSourceManagers.registerRemoteSources(this.playerManager);
    }


    public GuildMusicManager getMusicManager(Guild guild) {

        return this.musicManagers.computeIfAbsent(guild.getIdLong(), guildID -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.playerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl, MessageReceivedEvent event) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject MUSIC_OBJECT = LANG_OBJECT.get("music").getAsJsonObject();

        this.playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);

                channel.sendMessage(MUSIC_OBJECT.get("adding_queue").getAsString())
                        .append(track.getInfo().title)
                        .append(MUSIC_OBJECT.get("single_music_added_author").getAsString())
                        .append(track.getInfo().author)
                        .queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
                        );
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

                channel.sendMessage(MUSIC_OBJECT.get("adding_queue").getAsString())
                        .append(String.valueOf(tracks.size()))
                        .append(MUSIC_OBJECT.get("playlist_music_from").getAsString())
                        .append(playlist.getName())
                        .queue(
                                delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
                        );

                for (AudioTrack track : tracks ) {
                    musicManager.scheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {  }

            @Override
            public void loadFailed(FriendlyException exception) {  }
        });
    }

    public static PlayerManager getInstance() {
        if(INSTANCE == null) INSTANCE = new PlayerManager();

        return INSTANCE;
    }
}
