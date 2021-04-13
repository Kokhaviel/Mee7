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

package fr.kokhaviel.bot.commands.hypixel.games;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.player.Player;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.hypixel.player.stats.Pit;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.hypixelAPI;
import static java.lang.String.format;

public class PitCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject HYPIXEL_OBJECT = LANG_OBJECT.get("hypixel").getAsJsonObject();


		final Message message = event.getMessage();
		final MessageChannel channel = event.getChannel();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(prefix + "pit")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "player <Player>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage(format("%s !", HYPIXEL_OBJECT.get("invalid_username").getAsString())).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			message.delete().queue();

			PlayerData data = null;
			try {
				data = hypixelAPI.getData(args[1]);
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			assert data != null;
			if(!data.isSuccess()) {
				channel.sendMessage(data.getCause()).queue();
				return;
			}

			Player player = data.getPlayer();
			channel.sendMessage(getPitStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getPitStats2(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getPitStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Pit.PitProfile pitProfile = player.getStats().getPit().getPitProfile();
		EmbedBuilder pitEmbed = new EmbedBuilder();
		pitEmbed.setAuthor(format("Hypixel Pit %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		pitEmbed.setColor(new Color(147, 103, 70));
		pitEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		pitEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		pitEmbed.addField("Cash : ", String.valueOf(pitProfile.getCash()), true);
		pitEmbed.addField("Gold : ", String.valueOf(player.getAchievements().getPitGold()), true);
		pitEmbed.addField("XP : ", String.valueOf(pitProfile.getXp()), true);

		return pitEmbed;
	}

	private EmbedBuilder getPitStats2(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Pit.PitStats pitStats = player.getStats().getPit().getPitStats();
		EmbedBuilder pitEmbed = new EmbedBuilder();
		pitEmbed.setAuthor(format("Hypixel Pit %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		pitEmbed.setColor(new Color(147, 103, 70));
		pitEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		pitEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		pitEmbed.addField("Joins : ", String.valueOf(pitStats.getJoins()), true);
		pitEmbed.addField("Playtime : ", String.valueOf(pitStats.getPlayedTime()), true);
		pitEmbed.addField("Kills : ", String.valueOf(pitStats.getKills()), true);
		pitEmbed.addField("Deaths : ", String.valueOf(pitStats.getDeaths()), true);
		pitEmbed.addField("Assists : ", String.valueOf(pitStats.getAssists()), true);
		pitEmbed.addField("Max Streak : ", String.valueOf(pitStats.getMaxStreak()), true);
		pitEmbed.addField("Cash Earned : ", String.valueOf(pitStats.getCashEarned()), true);
		pitEmbed.addField("Damage Dealt : ", String.valueOf(pitStats.getDamageDealt()), true);
		pitEmbed.addField("Damage Received : ", String.valueOf(pitStats.getDamageReceived()), true);
		pitEmbed.addField("Melee Damage Dealt : ", String.valueOf(pitStats.getMeleeDamageDealt()), true);
		pitEmbed.addField("Melee Damage Received : ", String.valueOf(pitStats.getMeleeDamageReceived()), true);
		pitEmbed.addField("Jump into Pit : ", String.valueOf(pitStats.getJumpedPit()), true);
		pitEmbed.addField("Launched by Launchers : ", String.valueOf(pitStats.getLaunchedLaunchers()), true);
		pitEmbed.addField("Left Clicks : ", String.valueOf(pitStats.getLeft_clicks()), true);
		pitEmbed.addField("Sword Hits : ", String.valueOf(pitStats.getSwordHits()), true);
		pitEmbed.addField("Arrows Hits : ", String.valueOf(pitStats.getArrowHits()), true);
		pitEmbed.addField("Gapple Eaten : ", String.valueOf(pitStats.getGappleEaten()), true);
		pitEmbed.addField("GHead Eaten : ", String.valueOf(pitStats.getGheadEaten()), true);
		pitEmbed.addField("Lava Bucket Emptied : ", String.valueOf(pitStats.getLaunchedLaunchers()), true);
		pitEmbed.addField("Blocks Placed : ", String.valueOf(pitStats.getBlocksPlaced()), true);

		return pitEmbed;
	}
}
