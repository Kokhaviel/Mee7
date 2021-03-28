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

package fr.kokhaviel.bot.event.moderation;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoModerator extends ListenerAdapter {

	List<String> badWords = new ArrayList<>(Arrays.asList("Fuck", "fdp"));

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject AUTOMOD_OBJECT = LANG_OBJECT.get("automoderator").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final MessageChannel channel = event.getChannel();
		final Guild guild = event.getGuild();

		for(String arg : args) {
			for(String word : badWords) {

				if(arg.equalsIgnoreCase(word)) {

					EmbedBuilder badwordDetect = new EmbedBuilder();

					badwordDetect.setTitle(AUTOMOD_OBJECT.get("automoderator_embed_title").getAsString());
					badwordDetect.setColor(Color.red);
					badwordDetect.setThumbnail(event.getAuthor().getAvatarUrl());
					badwordDetect.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + guild.getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

					badwordDetect.addField(AUTOMOD_OBJECT.get("automoderator_badword_field").getAsString(), arg, false);

					channel.sendMessage(badwordDetect.build()).queue();
					message.delete().queue();
				}
			}
		}
	}
}
