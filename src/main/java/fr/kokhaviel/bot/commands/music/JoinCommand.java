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

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class JoinCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		final TextChannel channel = (TextChannel) event.getChannel();
		final Member selfMember = event.getGuild().getSelfMember();
		final GuildVoiceState voiceState = selfMember.getVoiceState();
		final Member member = event.getMember();
		final AudioManager audioManager = event.getGuild().getAudioManager();
		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "join")) {

			final GuildVoiceState memberVoiceState = member.getVoiceState();
			final VoiceChannel memberChannel = memberVoiceState.getChannel();
			message.delete().queue();

			if(voiceState.inVoiceChannel()) {
				channel.sendMessage("I'm Already Connected To A Voice Channel !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!memberVoiceState.inVoiceChannel()) {
				channel.sendMessage("You Need To Be In A Voice Channel For This Command Works !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}


			audioManager.openAudioConnection(memberChannel);
			channel.sendMessage("Successfully Connected To " + memberChannel.getName()).queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
		}
	}
}
