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
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import fr.kokhaviel.bot.music.GuildMusicManager;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class QueueCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("music_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject MUSIC_OBJECT = LANG_OBJECT.get("music").getAsJsonObject();

		final Message message = event.getMessage();
		final Guild guild = event.getGuild();
		final TextChannel channel = (TextChannel) event.getChannel();
		final String iconUrl = event.getGuild().getIconUrl();
		final String[] args = message.getContentRaw().split("\\s+");


		if(args[0].equalsIgnoreCase(prefix + "queue")) {

			final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
			final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
			message.delete().queue();
			final int trackCount = Math.min(queue.size(), 10);
			final List<AudioTrack> trackList = new ArrayList<>(queue);

			if(queue.isEmpty()) {
				channel.sendMessage(MUSIC_OBJECT.get("empty_queue").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			channel.sendMessage(getMusicQueue(event, iconUrl, trackCount, trackList, GENERAL_OBJECT, COMMANDS_OBJECT,  MUSIC_OBJECT).build()).queue();
		}
	}

	private String timeFormat(long timeMillis) {

		final long hours = timeMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeMillis / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	private EmbedBuilder getMusicQueue(MessageReceivedEvent event, String iconUrl, int trackCount, List<AudioTrack> trackList, JsonObject generalObject, JsonObject commandsObject, JsonObject musicObject) {
		EmbedBuilder queueEmbed = new EmbedBuilder();

		queueEmbed.setTitle(musicObject.get("current_queue").getAsString());
		queueEmbed.setColor(Color.ORANGE);
		queueEmbed.setAuthor(format("%s %s",musicObject.get("queue").getAsString(),commandsObject.get("command").getAsString()), null, iconUrl);
		queueEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nApi : LavaPlayer by sedmelluq", Config.DEVELOPER_AVATAR);

		for(int i = 0; i < trackCount; i++) {

			final AudioTrack track = trackList.get(i);
			final String title = track.getInfo().title;
			final String author = track.getInfo().author;

			queueEmbed.addField(title + " `[" + timeFormat(track.getDuration()) + "]`", author, false);
		}

		if(trackList.size() > trackCount) {
			queueEmbed.addField(format("%s %d %s ...", musicObject.get("and").getAsString(), trackList.size() - trackCount, musicObject.get("more").getAsString()), "", false);
		}

		return queueEmbed;
	}
}
