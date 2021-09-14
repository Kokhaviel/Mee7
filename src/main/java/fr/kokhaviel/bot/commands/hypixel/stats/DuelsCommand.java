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

package fr.kokhaviel.bot.commands.hypixel.stats;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.games.Duels;
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
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.HYPIXEL_API;
import static java.lang.String.format;

public class DuelsCommand extends ListenerAdapter {

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


		if(args[0].equalsIgnoreCase(prefix + "duels")) {
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
				channel.sendMessageEmbeds(getDuelsStats(player, GENERAL_OBJECT).build()).queue();
			} catch(MalformedURLException e) {
				channel.sendMessage("Player " + args[1] + " not found").queue();
			}
		}
	}

	public EmbedBuilder getDuelsStats(PlayerData.Player player, JsonObject generalObject) {
		EmbedBuilder hypixelEmbed = new EmbedBuilder();

		Duels duels = player.getStats().getDuels();

		hypixelEmbed.setAuthor("Hypixel Player Duels Stats", null, Config.HYPIXEL_ICON);
		hypixelEmbed.setColor(new Color(160,82,45));
		hypixelEmbed.setTitle(format("[%s] %s Stats",
				player.getRank(), player.getDisplayName()));
		hypixelEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG
				+ "\nHypixel API by Kokhaviel (https://github.com/Kokhaviel/HypixelAPI/)", Config.DEVELOPER_AVATAR);


		hypixelEmbed.addField("Coins : ", String.valueOf(duels.getCoins()), true);
		hypixelEmbed.addField("Status : ", duels.getStatus(), true);
		hypixelEmbed.addField("Wins : ", String.valueOf(duels.getWins()), true);
		hypixelEmbed.addField("Kills : ", String.valueOf(duels.getKills()), true);
		hypixelEmbed.addField("Games Played : ", String.valueOf(duels.getGamesPlayed()), true);
		hypixelEmbed.addField("Losses : ", String.valueOf(duels.getLosses()), true);
		hypixelEmbed.addField("Deaths : ", String.valueOf(duels.getDeaths()), true);
		hypixelEmbed.addField("Health Regenerated : ", String.valueOf(duels.getHealthRegenerated()), true);
		hypixelEmbed.addField("Damage Dealt : ", String.valueOf(duels.getDamageDealt()), true);
		hypixelEmbed.addField("Melee Hits : ", String.valueOf(duels.getMeleeHits()), true);
		hypixelEmbed.addField("Gapple Eaten : ", String.valueOf(duels.getGappleEaten()), true);
		hypixelEmbed.addField("Bridge Goals : ", String.valueOf(duels.getBridgeGoals()), true);

		return hypixelEmbed;
	}
}
