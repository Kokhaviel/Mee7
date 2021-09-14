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
import fr.kokhaviel.api.hypixel.games.Skywars;
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

public class SkywarsCommand extends ListenerAdapter {

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


		if(args[0].equalsIgnoreCase(prefix + "skywars")) {

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
				channel.sendMessageEmbeds(getSkywarsStats(player, GENERAL_OBJECT).build()).queue();
			} catch(MalformedURLException e) {
				channel.sendMessage("Player " + args[1] + " not found").queue();
			}
		}
	}

	public EmbedBuilder getSkywarsStats(PlayerData.Player player, JsonObject generalObject) {
		EmbedBuilder hypixelEmbed = new EmbedBuilder();

		Skywars skywars = player.getStats().getSkywars();

		hypixelEmbed.setAuthor("Hypixel Player Skywars Stats", null, Config.HYPIXEL_ICON);
		hypixelEmbed.setColor(new Color(17, 133, 36));
		hypixelEmbed.setTitle(format("[%s] %s Stats",
				player.getRank(), player.getDisplayName()));
		hypixelEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG
				+ "\nHypixel API by Kokhaviel (https://github.com/Kokhaviel/HypixelAPI/)", Config.DEVELOPER_AVATAR);

		hypixelEmbed.addField("Coins : ", String.valueOf(skywars.getCoins()), true);
		hypixelEmbed.addField("Souls : ", String.valueOf(skywars.getSouls()), true);
		hypixelEmbed.addField("Opals : ", String.valueOf(skywars.getOpals()), true);
		hypixelEmbed.addField("Shard : ", String.valueOf(skywars.getShard()), true);
		hypixelEmbed.addField("Wins : ", String.valueOf(skywars.getWins()), true);
		hypixelEmbed.addField("WinStreak : ", String.valueOf(skywars.getWinstreak()), true);
		hypixelEmbed.addField("Kills : ", String.valueOf(skywars.getKills()), true);
		hypixelEmbed.addField("Assists : ", String.valueOf(skywars.getAssists()), true);
		hypixelEmbed.addField("Losses : ", String.valueOf(skywars.getLosses()), true);
		hypixelEmbed.addField("Deaths : ", String.valueOf(skywars.getDeaths()), true);
		hypixelEmbed.addField("Quits : ", String.valueOf(skywars.getQuits()), true);
		hypixelEmbed.addField("Games Played : ", String.valueOf(skywars.getWinstreak()), true);
		hypixelEmbed.addField("Arrows Hit : ", String.valueOf(skywars.getArrowsHit()), true);
		hypixelEmbed.addField("Last Mode : ", skywars.getLastMode(), true);

		hypixelEmbed.addBlankField(false);

		hypixelEmbed.addField("Total Heads : ", String.valueOf(skywars.getHeads()), true);
		hypixelEmbed.addField("Heads Meh : ", String.valueOf(skywars.getHeadsMeh()), true);
		hypixelEmbed.addField("Heads Eww : ", String.valueOf(skywars.getHeadsEww()), true);
		hypixelEmbed.addField("Heads Salty : ", String.valueOf(skywars.getHeadsSalty()), true);
		hypixelEmbed.addField("Heads Sweet : ", String.valueOf(skywars.getWinstreak()),  true);
		hypixelEmbed.addField("Heads Tasty : ", String.valueOf(skywars.getHeadsTasty()), true);
		hypixelEmbed.addField("Heads Decent : ", String.valueOf(skywars.getHeadsDecent()), true);
		hypixelEmbed.addField("Heads Succulent : ", String.valueOf(skywars.getHeadsSucculent()), true);
		hypixelEmbed.addField("Heads Yucky : ", String.valueOf(skywars.getHeadsYucky()), true);
		hypixelEmbed.addField("Heads Divine : ", String.valueOf(skywars.getHeadsDivine()), true);
		hypixelEmbed.addField("Heads Heavenly : ", String.valueOf(skywars.getHeadsHeavenly()),  true);

		return hypixelEmbed;
	}
}
