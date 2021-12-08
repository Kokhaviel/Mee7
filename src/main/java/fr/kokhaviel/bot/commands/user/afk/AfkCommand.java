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

package fr.kokhaviel.bot.commands.user.afk;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Mee7;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AfkCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String prefix = Settings.getGuildPrefix(event.getGuild().getId(), "prefix");

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject AFK_OBJECT = LANG_OBJECT.get("afk").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final Member member = event.getMember();
		final MessageChannel channel = event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "afk")) {
			message.delete().queue();
			assert member != null;
			if(!Mee7.afkIDs.contains(member.getId())) {
				Mee7.afkIDs.add(member.getId());
				channel.sendMessage(AFK_OBJECT.get("success_set_afk").getAsString()).queue(delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));
				return;
			}
			Mee7.afkIDs.remove(member.getId());
			channel.sendMessage(AFK_OBJECT.get("success_remove_afk").getAsString()).queue(delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));
		}
	}
}
