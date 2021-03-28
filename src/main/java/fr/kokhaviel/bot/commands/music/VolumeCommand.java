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

@SuppressWarnings("ConstantConditions")
public class VolumeCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "volume")) {

			final GuildVoiceState voiceState = member.getVoiceState();
			final GuildVoiceState selfVoiceState = selfMember.getVoiceState();
			final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
			final AudioPlayer audioPlayer = musicManager.audioPlayer;

			if(args.length == 1) {
				channel.sendMessageFormat("%s : %d%% \n`%s`",
						MUSIC_OBJECT.get("current_volume").getAsString(),
						audioPlayer.getVolume(),
						MUSIC_OBJECT.get("hint_volume").getAsString())
						.queue();
			}

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
			audioPlayer.setVolume(Integer.parseInt(args[1]));

			channel.sendMessageFormat("%s %s%% !",
					MUSIC_OBJECT.get("volume_now").getAsString(),
					args[1])
					.queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
		}
	}
}
