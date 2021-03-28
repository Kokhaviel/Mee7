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
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class PlayCommand extends ListenerAdapter {

	@SuppressWarnings("ConstantConditions")
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

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();
		final Member selfMember = event.getGuild().getSelfMember();
		final Member member = event.getMember();

		if(args[0].equalsIgnoreCase(prefix + "play")) {

			final GuildVoiceState selfVoiceState = selfMember.getVoiceState();
			final GuildVoiceState voiceState = member.getVoiceState();
			message.delete().queue();

			if(args.length < 2) {
				channel.sendMessage(MUSIC_OBJECT.get("play_no_link").getAsString()).queue(
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
			String link = args[1];
			if(!isUrl(link)) {
				channel.sendMessage(MUSIC_OBJECT.get("play_not_link").getAsString()).queue();
			}

			PlayerManager.getInstance().loadAndPlay(channel, link, event);
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
