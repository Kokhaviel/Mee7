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

package fr.kokhaviel.bot.commands.user;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class InfoCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject USER_OBJECT = LANG_OBJECT.get("user").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final JDA jda = event.getJDA();

		if(args[0].equalsIgnoreCase(prefix + "userinfo")) {
			message.delete().queue();
			TextChannel channel = (TextChannel) event.getChannel();
			if(args.length < 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("missing_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "userinfo <@User> !")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(args.length > 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("too_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "userinfo <@User> !")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			List<Member> mentionedMembers = message.getMentionedMembers();
			Member target = mentionedMembers.get(0);

			channel.sendMessage(getUserInfo(jda, target, USER_OBJECT).build()).queue();

		}
	}

	private EmbedBuilder getUserInfo(JDA jda, Member target, JsonObject userObject) {
		EmbedBuilder userInfoEmbed = new EmbedBuilder();

		userInfoEmbed.setTitle(target.getUser().getName() + " User Info")
				.setColor(Color.CYAN)
				.setThumbnail(target.getUser().getAvatarUrl())
				.setAuthor("User Info", null, jda.getSelfUser().getAvatarUrl());

		userInfoEmbed.addField("ID : ", target.getId(), false);

		if(target.getNickname() != null) userInfoEmbed.addField("Nickname : ", target.getNickname(), false);

		userInfoEmbed.addField(userObject.get("joined_on").getAsString(), target.getTimeJoined().toString(), false);
		userInfoEmbed.addField(userObject.get("account_created_on").getAsString(), target.getTimeCreated().toString(), false);
		userInfoEmbed.addField(userObject.get("status").getAsString(), target.getOnlineStatus().getKey(), false);


		return userInfoEmbed;
	}
}
