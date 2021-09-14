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

package fr.kokhaviel.bot.commands.hypixel.player;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.HypixelAPI;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.HYPIXEL_API;
import static java.lang.String.format;

public class PlayerCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		String prefix = Settings.getGuildPrefix(event.getGuild().getId(), "hypixel_prefix");

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
		final JsonObject HYPIXEL_OBJECT = LANG_OBJECT.get("hypixel").getAsJsonObject();


		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "player")) {

			message.delete().queue();

			if(args.length < 2) {


				channel.sendMessage(format("%s : No Player Specified !",
								COMMANDS_OBJECT.get("missing_arguments").getAsString()))
						.queue(
								delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage(HYPIXEL_OBJECT.get("not_valid_username").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}


			PlayerData.Player player;

			try {
				player = HYPIXEL_API.getPlayerData(args[1]).getPlayer();
				channel.sendMessageEmbeds(getPlayerStats(player, GENERAL_OBJECT).build()).queue();
			} catch(MalformedURLException e) {
				channel.sendMessage("Player " + args[1] + " not found").queue();
			}

		}


	}

	public EmbedBuilder getPlayerStats(PlayerData.Player player, JsonObject generalObject) throws MalformedURLException {
		EmbedBuilder hypixelEmbed = new EmbedBuilder();

		hypixelEmbed.setAuthor("Hypixel Player Stats", null, Config.HYPIXEL_ICON);
		hypixelEmbed.setColor(Color.YELLOW);
		hypixelEmbed.setTitle(format("[%s] [%s] %s Stats",
				player.getRank(), player.getServerRank().equals("NONE") ? "NO RANK" : player.getServerRank(), player.getDisplayName()));
		hypixelEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG
				+ "\nHypixel API by Kokhaviel (https://github.com/Kokhaviel/HypixelAPI/)", Config.DEVELOPER_AVATAR);

		LocalDateTime firstLogin = HypixelAPI.convertTimestampToDateTime(player.getFirstLogin());
		LocalDateTime lastLogin = HypixelAPI.convertTimestampToDateTime(player.getLastLogin());

		hypixelEmbed.addField("First Login : ",
				firstLogin.getYear() + "/" + firstLogin.getMonthValue() + "/" + firstLogin.getDayOfMonth(), false);
		hypixelEmbed.addField("Last Login : ",
				lastLogin.getYear() + "/" + firstLogin.getMonthValue() + "/" + lastLogin.getDayOfMonth(), false);
		hypixelEmbed.addField("Achievement Points : ", String.valueOf(player.getAchievementPoints()), false);
		hypixelEmbed.addField("Network Experience : ", String.valueOf(player.getNetworkExperience()), false);
		hypixelEmbed.addField("Network Level : ", String.valueOf(HYPIXEL_API.getLevel(player.getDisplayName()).getNetworkLevel()), false);
		hypixelEmbed.addField("Karma : ", String.valueOf(player.getKarma()), false);
		hypixelEmbed.addField("Minecraft Version : ", player.getLastMcVersion(), false);
		hypixelEmbed.addField("Language : ", player.getLanguage(), false);
		hypixelEmbed.addField("Gadget : ", player.getGadget(), false);
		hypixelEmbed.addField("Pet : ", player.getPet(), false);

		return hypixelEmbed;
	}
}
