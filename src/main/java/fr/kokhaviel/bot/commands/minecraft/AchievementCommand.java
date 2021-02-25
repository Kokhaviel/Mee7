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

package fr.kokhaviel.bot.commands.minecraft;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static fr.kokhaviel.bot.Mee7.getCurrentAchievements;

public class AchievementCommand extends ListenerAdapter {

	Achievements achievements;
	User achieveUserCreate;
	TextChannel achieveChannelCreate;
	Message achieveMessageCreate;

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {


		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.MINECRAFT_PREFIX + "achievement")) {

			if(args.length > 1){
				channel.sendMessage("Too Arguments ...").queue();
				return;
			}

			if(getCurrentAchievements().contains(event.getGuild().getIdLong())) {
				channel.sendMessage("You are already setting up an achievement image").queue();
				return;
			}

			achievements = new Achievements();
			getCurrentAchievements().add(event.getGuild().getIdLong());
			achievements.startSetup(achievements, event);
			this.achieveUserCreate = event.getAuthor();
			this.achieveChannelCreate = (TextChannel) event.getChannel();
			this.achieveMessageCreate = event.getMessage();
		}

		if(isSameUser(event) && achievements.isTitleSetup() && !achievements.isStringSetup()) {
			achievements.stringSetup(achievements, event);
		}

		if(isSameUser(event) && achievements.isBlockSetup() && !achievements.isTitleSetup()) {
			achievements.titleSetup(achievements, event);
		}

		if(isSameUser(event) && achievements.isSetupStarted() && !achievements.isBlockSetup()) {
			achievements.blockSetup(achievements, event);
		}

	}

	private boolean isSameUser(MessageReceivedEvent event) {

		return event.getAuthor().equals(this.achieveUserCreate)
				&& event.getChannel().equals(this.achieveChannelCreate)
				&& !event.getMessage().equals(this.achieveMessageCreate);
	}
}
