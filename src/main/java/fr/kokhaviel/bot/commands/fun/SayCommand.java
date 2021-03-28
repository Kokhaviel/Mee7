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

package fr.kokhaviel.bot.commands.fun;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class SayCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject FUN_OBJECT = LANG_OBJECT.get("fun").getAsJsonObject();

		final Message message = event.getMessage();
		final MessageChannel channel = event.getChannel();
		final String[] args = message.getContentRaw().split("\\s+");

		StringBuilder sayBuilder = new StringBuilder();
		if(args[0].equalsIgnoreCase(prefix + "say")) {

			if(args.length < 2) {
				message.delete().queue();
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("missing_arguments").getAsString(),
						FUN_OBJECT.get("no_word_specified"))).queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			for(String arg : args) {
				if(arg.equals(args[0])) continue;
				sayBuilder.append(arg).append(" ");
			}
			message.delete().queue();
			channel.sendMessage(sayBuilder).queue();

		}
	}
}
