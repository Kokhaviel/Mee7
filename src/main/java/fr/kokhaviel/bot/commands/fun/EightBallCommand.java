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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EightBallCommand extends ListenerAdapter {

	List<String> answer = Arrays.asList(
			"It is certain.",
			"As I see it, yes.",
			"Reply hazy, try again.",
			"Don't count on it.",
			"It is decidedly so.",
			"Most likely.",
			"Ask again later.",
			"My reply is no.",
			"Without a doubt.",
			"Outlook good.",
			"Better not tell you now.",
			"My sources say no.",
			"Yes – definitely.",
			"Yes.",
			"Cannot predict now.",
			"Outlook not so good.",
			"You may rely on it.",
			"Signs point to yes.",
			"Concentrate and ask again.",
			"Very doubtful.");

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final MessageChannel channel = event.getChannel();

		if(args[0].equalsIgnoreCase(Config.PREFIX + "8ball")) {

			if(args.length < 2) {

				message.delete().queue();
				channel.sendMessage("Missing Argument : Please Use " + Config.PREFIX + "8ball <Question?>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			int ballRandom = new Random().nextInt(20);
			message.delete().queue();
			channel.sendMessage(answer.get(ballRandom)).queue();

		}
	}
}