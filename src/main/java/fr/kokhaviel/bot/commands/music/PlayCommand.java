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
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class PlayCommand extends ListenerAdapter {

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();
		final Member selfMember = event.getGuild().getSelfMember();
		final Member member = event.getMember();

		if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "play")) {

			final GuildVoiceState selfVoiceState = selfMember.getVoiceState();
			final GuildVoiceState voiceState = member.getVoiceState();
			message.delete().queue();

			if(args.length < 2) {
				channel.sendMessage("Please Use : " + Config.MUSIC_PREFIX + "play <Youtube Link>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage("I need to be in a voice channel to this command works !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!voiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage("You need to be in the same voice channel as me for this command works !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			String link = args[1];
			if(!isUrl(link)) {
				channel.sendMessage("You must use a link to play music !").queue();
			}

			PlayerManager.getInstance().loadAndPlay(channel, link);
		}
	}

	private boolean isUrl(String url) {
		try {
			new URI(url);
			return true;
		} catch(URISyntaxException use) {
			return false;
		}
	}
}
