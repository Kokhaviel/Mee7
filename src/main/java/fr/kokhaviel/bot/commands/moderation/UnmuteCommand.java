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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class UnmuteCommand extends ListenerAdapter {

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
		final MessageChannel channel = event.getChannel();
		final Guild guild = event.getGuild();

		if(args[0].equalsIgnoreCase(prefix + "unmute")) {

			message.delete().queue();
			if(args.length < 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("missing_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "unmute <@User>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(args.length > 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("too_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "unmute <@User>")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			List<Member> mentionedMembers = message.getMentionedMembers();
			List<Role> roles = guild.getRolesByName("Muted", true);
			Member target = mentionedMembers.get(0);
			Role role;

			if(roles.isEmpty()) {
				channel.sendMessage(MODERATION_OBJECT.get("no_muted_role").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			role = roles.get(0);
			guild.removeRoleFromMember(target.getId(), role).queue();
			channel.sendMessage(format("%s %s", MODERATION_OBJECT.get("success_unmute").getAsString(), target.getUser())).queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

		}
	}
}
