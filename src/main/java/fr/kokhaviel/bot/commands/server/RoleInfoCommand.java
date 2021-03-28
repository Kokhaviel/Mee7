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

package fr.kokhaviel.bot.commands.server;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class RoleInfoCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject ROLES_OBJECT = LANG_OBJECT.get("roles").getAsJsonObject();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final MessageChannel channel = event.getChannel();
		final Guild guild = event.getGuild();
		final JDA jda = event.getJDA();


		if(args[0].equalsIgnoreCase(prefix + "roleinfo")) {
			message.delete().queue();
			if(args.length < 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("missing_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "roleinfo <@Role> !")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(args.length > 2) {
				channel.sendMessage(format("%s : %s",
						COMMANDS_OBJECT.get("too_arguments").getAsString(),
						COMMANDS_OBJECT.get("please_use").getAsString()) + prefix + "roleinfo <@Role> !")
						.queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			List<Role> roleMentioned = message.getMentionedRoles();
			Role target = roleMentioned.get(0);

			channel.sendMessage(getRoleInfo(guild, jda, target,ROLES_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getRoleInfo(Guild guild, JDA jda, Role target, JsonObject rolesObject) {
		EmbedBuilder roleinfoEmbed = new EmbedBuilder();

		roleinfoEmbed.setTitle(target.getName() + " Role Info")
				.setColor(Color.CYAN)
				.setThumbnail(guild.getIconUrl())
				.setAuthor("Role Info", null, jda.getSelfUser().getAvatarUrl());

		roleinfoEmbed.addField("ID : ", target.getId(), false);
		roleinfoEmbed.addField(format("%s : ", rolesObject.get("time_create").getAsString()), String.valueOf(target.getTimeCreated()).replace("T", " ").replace("Z", " "), false);
		roleinfoEmbed.addField(format("%s : ", rolesObject.get("color").getAsString()), format("R : %s, G : %s, B : %s", Objects.requireNonNull(target.getColor()).getRed(), target.getColor().getGreen(), target.getColor().getBlue()),false);
		roleinfoEmbed.addField(format("%s : ", rolesObject.get("hoist").getAsString()), String.valueOf(target.isHoisted()), false);
		roleinfoEmbed.addField(format("%s : ", rolesObject.get("mentionable").getAsString()), String.valueOf(target.isMentionable()), false);

		return roleinfoEmbed;
	}
}
