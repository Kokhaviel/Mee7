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
import fr.kokhaviel.api.hypixel.games.MCGO;
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

public class MCGOCommand extends ListenerAdapter {

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


		if(args[0].equalsIgnoreCase(prefix + "mcgo")) {
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
				channel.sendMessageEmbeds(getMCGOStats(player, GENERAL_OBJECT).build()).queue();
			} catch(MalformedURLException e) {
				channel.sendMessage("Player " + args[1] + " not found").queue();
			}
		}
	}

	public EmbedBuilder getMCGOStats(PlayerData.Player player, JsonObject generalObject) {
		EmbedBuilder hypixelEmbed = new EmbedBuilder();

		MCGO mcgo = player.getStats().getMcgo();

		hypixelEmbed.setAuthor("Hypixel Player MCGO Stats", null, Config.HYPIXEL_ICON);
		hypixelEmbed.setColor(new Color(150, 255, 127));
		hypixelEmbed.setTitle(format("[%s] %s Stats",
				player.getRank(), player.getDisplayName()));
		hypixelEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG
				+ "\nHypixel API by Kokhaviel (https://github.com/Kokhaviel/HypixelAPI/)", Config.DEVELOPER_AVATAR);

		hypixelEmbed.addField("Coins : ", String.valueOf(mcgo.getCoins()), true);
		hypixelEmbed.addField("Wins : ", String.valueOf(mcgo.getWins()), true);
		hypixelEmbed.addField("Kills : ", String.valueOf(mcgo.getKills()), true);
		hypixelEmbed.addField("Headshots : ", String.valueOf(mcgo.getHeadshots()), true);
		hypixelEmbed.addField("Cop Kills : ", String.valueOf(mcgo.getCopKills()), true);
		hypixelEmbed.addField("Criminal Kills : ", String.valueOf(mcgo.getCriminalKills()), true);
		hypixelEmbed.addField("Assists : ", String.valueOf(mcgo.getAssists()), true);
		hypixelEmbed.addField("Bombs Planted : ", String.valueOf(mcgo.getBombsPlanted()), true);
		hypixelEmbed.addField("Bombs Defused : ", String.valueOf(mcgo.getBombsDefused()), true);
		hypixelEmbed.addField("Shots Fired : ", String.valueOf(mcgo.getShotsFired()), true);
		hypixelEmbed.addField("Deaths : ", String.valueOf(mcgo.getDeaths()), true);

		return hypixelEmbed;
	}
}
