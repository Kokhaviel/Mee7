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

package fr.kokhaviel.bot.commands.moderation;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class UnbanCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject MODERATION_OBJECT = LANG_OBJECT.get("moderation").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final Guild guild = event.getGuild();
		final MessageChannel channel = event.getChannel();
		final Member author = message.getMember();

		if(args[0].equalsIgnoreCase(prefix + "unban")) {

			message.delete().queue();

			if(args.length < 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("missing_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "unban <User ID>")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(args.length > 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("too_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "unban <User ID>")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			assert author != null;
			if(!author.hasPermission(Permission.BAN_MEMBERS)) {
				channel.sendMessage(MODERATION_OBJECT.get("no_unban_perm").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			guild.unban(args[1]).queue(
					success -> channel.sendMessage(format("%s %s", MODERATION_OBJECT.get("success_unban").getAsString(), args[1])).queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
					error -> channel.sendMessage(format("%s %s", MODERATION_OBJECT.get("unable_unban").getAsString(), args[1])).queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS))
			);
		}
	}
}