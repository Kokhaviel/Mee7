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
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@SuppressWarnings("ConstantConditions")
public class JoinCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("music_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject MUSIC_OBJECT = LANG_OBJECT.get("music").getAsJsonObject();

		final TextChannel channel = (TextChannel) event.getChannel();
		final Member selfMember = event.getGuild().getSelfMember();
		final GuildVoiceState voiceState = selfMember.getVoiceState();
		final Member member = event.getMember();
		final AudioManager audioManager = event.getGuild().getAudioManager();
		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(prefix + "join")) {

			final GuildVoiceState memberVoiceState = member.getVoiceState();
			final VoiceChannel memberChannel = memberVoiceState.getChannel();
			message.delete().queue();

			if(voiceState.inVoiceChannel()) {
				channel.sendMessage(MUSIC_OBJECT.get("already_connected").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!memberVoiceState.inVoiceChannel()) {
				channel.sendMessage(MUSIC_OBJECT.get("not_in_a_voice_channel").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}


			audioManager.openAudioConnection(memberChannel);
			channel.sendMessage(format("%s %s", MUSIC_OBJECT.get("success_connect").getAsString(), memberChannel.getName())).queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
		}
	}
}
