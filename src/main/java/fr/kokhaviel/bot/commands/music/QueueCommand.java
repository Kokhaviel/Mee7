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

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final Guild guild = event.getGuild();
		final TextChannel channel = (TextChannel) event.getChannel();
		final String iconUrl = event.getGuild().getIconUrl();
		final String[] args = message.getContentRaw().split("\\s+");


		if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "queue")) {

			final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
			final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
			message.delete().queue();
			final int trackCount = Math.min(queue.size(), 10);
			final List<AudioTrack> trackList = new ArrayList<>(queue);

			if(queue.isEmpty()) {
				channel.sendMessage("Queue is currently empty !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			channel.sendMessage(getMusicQueue(event, iconUrl, trackCount, trackList).build()).queue();
		}
	}

	private String timeFormat(long timeMillis) {

		final long hours = timeMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeMillis / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	private EmbedBuilder getMusicQueue(MessageReceivedEvent event, String iconUrl, int trackCount, List<AudioTrack> trackList) {
		EmbedBuilder queueEmbed = new EmbedBuilder();

		queueEmbed.setTitle("Current Queue :");
		queueEmbed.setColor(Color.ORANGE);
		queueEmbed.setAuthor("Queue Command", null, iconUrl);
		queueEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

		for(int i = 0; i < trackCount; i++) {

			final AudioTrack track = trackList.get(i);
			final String title = track.getInfo().title;
			final String author = track.getInfo().author;

			queueEmbed.addField(title + " `[" + timeFormat(track.getDuration()) + "]`", author, false);
		}

		if(trackList.size() > trackCount) {
			queueEmbed.addField("And " + (trackList.size() - trackCount) + " More ...", "", false);
		}

		return queueEmbed;
	}
}
