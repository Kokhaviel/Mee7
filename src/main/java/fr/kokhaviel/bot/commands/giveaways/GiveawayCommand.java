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

package fr.kokhaviel.bot.commands.giveaways;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.getCurrentGiveaways;
import static java.lang.String.format;


public class GiveawayCommand extends ListenerAdapter {

	Giveaways giveaway;
	User giveawayUserCreate;
	TextChannel giveawayChannelCreate;
	Message giveawayMessageCreate;

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("giveaways_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject GIVEAWAYS_OBJECT = LANG_OBJECT.get("giveaways").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "create")) {

			if(args.length > 1) {
				message.delete().queue();

				channel.sendMessage(format("%s ...", COMMANDS_OBJECT.get("too_arguments").getAsString())).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(getCurrentGiveaways().contains(event.getGuild().getIdLong())) {
				channel.sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("already_setting_up").getAsString())).queue();
				return;
			}

			giveaway = new Giveaways(event);

			getCurrentGiveaways().add(event.getGuild().getIdLong());
			giveaway.startSetup(giveaway, event);
			this.giveawayUserCreate = event.getAuthor();
			this.giveawayChannelCreate = (TextChannel) event.getChannel();
			this.giveawayMessageCreate = event.getMessage();

		}

		if(isSameUser(event) && giveaway.isWinnersSetUp() && !giveaway.isPrizeSetup()) {
			try {
				giveaway.prizeSetup(giveaway, event);
			} catch(InterruptedException | ParseException e) {
				e.printStackTrace();
			}
		}

		if(isSameUser(event) && giveaway.isDurationSetUp() && !giveaway.isWinnersSetUp()) {
			giveaway.winnersSetup(giveaway, event);
		}

		if(isSameUser(event) && giveaway.isChannelSetUp() && !giveaway.isDurationSetUp()) {
			giveaway.durationSetup(giveaway, event);
		}

		if(isSameUser(event) && giveaway.isSetupStarted() && !giveaway.isChannelSetUp()) {
			giveaway.channelSetup(giveaway, event);
		}


		if(args[0].equalsIgnoreCase(prefix + "cancel")) {

			giveaway.cancelGiveaway(giveaway, event);
		}

		if(args[0].equalsIgnoreCase(prefix + "end")) {

			giveaway.forceEnd(giveaway, event);
		}

		if(args[0].equalsIgnoreCase(prefix + "reroll")) {

			giveaway.rerollGiveaway(giveaway, event);
		}
	}

	private boolean isSameUser(MessageReceivedEvent event) {

		return event.getAuthor().equals(this.giveawayUserCreate)
				&& event.getChannel().equals(this.giveawayChannelCreate)
				&& !event.getMessage().equals(this.giveawayMessageCreate);
	}

	@Override
	public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

		if(!getCurrentGiveaways().contains(event.getGuild().getIdLong())) return;

		if(event.getMessageId().equals(giveaway.getGiveawayMessage().getId())) {
			giveaway.getParticipants().add(event.getUser());
		}
	}

	@Override
	public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {

		if(!getCurrentGiveaways().contains(event.getGuild().getIdLong())) return;

		if(event.getMessageId().equals(giveaway.getGiveawayMessage().getId())) {
			giveaway.getParticipants().remove(event.getUser());
		}
	}
}
