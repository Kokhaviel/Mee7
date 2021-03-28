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

import com.google.gson.JsonObject;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import fr.kokhaviel.bot.music.GuildMusicManager;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@SuppressWarnings("ConstantConditions")
public class NowPlayingCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("music_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject MUSIC_OBJECT = LANG_OBJECT.get("music").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();
		final Member member = event.getMember();
		final Guild guild = event.getGuild();
		final Member selfMember = guild.getSelfMember();

		if(args[0].equalsIgnoreCase(prefix + "nowplaying")) {

			final GuildVoiceState selfVoiceState = selfMember.getVoiceState();
			final GuildVoiceState voiceState = member.getVoiceState();
			message.delete().queue();

			if(!voiceState.inVoiceChannel()) {
				channel.sendMessage(MUSIC_OBJECT.get("not_in_a_voice_channel").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage(MUSIC_OBJECT.get("not_connected").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!voiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage(MUSIC_OBJECT.get("not_same_channel").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
			final AudioPlayer audioPlayer = musicManager.audioPlayer;
			final AudioTrack playingTrack = audioPlayer.getPlayingTrack();
			final AudioTrackInfo trackInfo = playingTrack.getInfo();

			if(playingTrack == null) {
				channel.sendMessage(MUSIC_OBJECT.get("no_track_playing").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			channel.sendMessageFormat("%s %s %s %s !",
					MUSIC_OBJECT.get("now_playing").getAsString(),
					trackInfo.title,
					MUSIC_OBJECT.get("by").getAsString(),
					trackInfo.author).queue();

			channel.sendMessageFormat(
					"%s : `[%s]`\n%s : `[%s]`",
					MUSIC_OBJECT.get("time_elapsed").getAsString(),
					timeFormat(playingTrack.getPosition()),
					MUSIC_OBJECT.get("time_left").getAsString(),
					timeFormat(playingTrack.getDuration() - playingTrack.getPosition())
			).queue();
		}
	}

	private String timeFormat(long timeMillis) {

		final long hours = timeMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeMillis / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
