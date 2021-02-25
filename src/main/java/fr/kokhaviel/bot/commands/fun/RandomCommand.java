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

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final MessageChannel channel = event.getChannel();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(Config.PREFIX + "random")) {

			if(args.length < 3) {
				message.delete().queue();
				channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "random <Int1> <Int2>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(args.length > 3) {
				message.delete().queue();
				channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "random <Int1> <Int2>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			int first = Integer.parseInt(args[1]);
			int second = Integer.parseInt(args[2]);
			channel.sendMessage("Giving you a number between " + first + " and " + second + " : " + new Random().ints(first, second).findFirst().getAsInt()).queue();
			message.delete().queue();

		}
	}
}