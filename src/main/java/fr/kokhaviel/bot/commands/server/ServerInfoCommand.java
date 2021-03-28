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

package fr.kokhaviel.bot.commands.server;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class ServerInfoCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject SERVER_OBJECT = LANG_OBJECT.get("server").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final MessageChannel channel = event.getChannel();
		final JDA jda = event.getJDA();

		if(args[0].equalsIgnoreCase(prefix + "serverinfo")) {
			message.delete().queue();
			Guild guild = event.getGuild();
			if(args.length > 1) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("too_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "serverinfo")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			channel.sendMessage(getServerInfo(guild, jda, SERVER_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getServerInfo(Guild guild, JDA jda, JsonObject serverObject) {
		EmbedBuilder serverinfoEmbed = new EmbedBuilder();

		serverinfoEmbed.setTitle(guild.getName() + serverObject.get("server_info").getAsString())
				.setColor(Color.CYAN)
				.setThumbnail(guild.getIconUrl())
				.setAuthor(serverObject.get("server_info").getAsString(), null, jda.getSelfUser().getAvatarUrl());

		serverinfoEmbed.addField(format("%s : ", serverObject.get("name").getAsString()), guild.getName(), false);
		serverinfoEmbed.addField(format("%s : ", serverObject.get("owner").getAsString()), guild.getOwner().getUser().getAsTag(), false);
		if(guild.getIconUrl() != null) serverinfoEmbed.addField(format("%s : ", serverObject.get("icon").getAsString()), "[Here](" + guild.getIconUrl() + ")", false);
		if(guild.getBannerUrl() != null) serverinfoEmbed.addField(format("%s : ", serverObject.get("banner").getAsString()), "[Here](" + guild.getBannerUrl() + ")", false);
		serverinfoEmbed.addField(format("%s : ", serverObject.get("region").getAsString()), guild.getRegion().getName(), false);
		if(guild.getDescription() != null)
			serverinfoEmbed.addField(format("%s : ", serverObject.get("description").getAsString()), guild.getDescription(), false);
		serverinfoEmbed.addField(format("%s : ", serverObject.get("boost_tier").getAsString()), guild.getBoostTier().name(), false);
		if(guild.getSystemChannel() != null)
			serverinfoEmbed.addField(format("%s : ", serverObject.get("system_channel").getAsString()), guild.getSystemChannel().getAsMention(), false);

		return serverinfoEmbed;
	}

}