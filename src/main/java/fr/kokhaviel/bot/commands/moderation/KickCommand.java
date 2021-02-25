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

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class KickCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();
		final Guild guild = event.getGuild();
		final Member author = message.getMember();

		List<Member> mentionedMembers = message.getMentionedMembers();

		if(args[0].equalsIgnoreCase(Config.PREFIX + "kick")) {

			if(args.length < 3) {
				message.delete().queue();
				channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "kick <@User> <Reason>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(args.length > 3) {
				message.delete().queue();
				channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "kick <@User> <Reason>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			message.delete().queue();

			assert author != null;
			if(!author.hasPermission(Permission.KICK_MEMBERS)) {
				channel.sendMessage("You don't have the permission to kick member !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(mentionedMembers.isEmpty()) {
				channel.sendMessage("You must mention the member you want to kick !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			Member target = mentionedMembers.get(0);
			guild.kick(target, args[2]).queue(
					success -> channel.sendMessage("Successfully Kicked " + target.getUser().getAsTag()).queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
					error -> channel.sendMessage("Unable To Kick " + target.getUser().getAsTag()).queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS))
			);
		}
	}
}