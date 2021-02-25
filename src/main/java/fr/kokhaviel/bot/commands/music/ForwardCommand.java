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
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class ForwardCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final TextChannel channel = (TextChannel) event.getChannel();
		final Guild guild = event.getGuild();
		final Member member = event.getMember();
		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "forward")) {

			final GuildVoiceState memberVoiceState = member.getVoiceState();
			message.delete().queue();

			if(!memberVoiceState.inVoiceChannel()) {
				channel.sendMessage("You Need To Be In A Voice Channel For This Command Works !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(args.length < 2) {
				channel.sendMessage("Missing Arguments : Specify a duration to advance in seconds").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
			final AudioPlayer audioPlayer = musicManager.audioPlayer;
			final AudioTrack playingTrack = audioPlayer.getPlayingTrack();

			final int toForward = Integer.parseInt(args[1]);

			if(toForward < 10) {
				channel.sendMessage("Cannot Forward Less Than 10 seconds").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			playingTrack.setPosition(playingTrack.getPosition() + toForward * 1000L);
		}
	}
}
