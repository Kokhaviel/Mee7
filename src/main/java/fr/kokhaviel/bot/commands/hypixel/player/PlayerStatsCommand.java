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
import fr.kokhaviel.api.hypixel.player.*;
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
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.hypixelAPI;
import static java.lang.String.format;

public class PlayerStatsCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "player")) {

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
				data = hypixelAPI.getPlayerData(args[1]);
			} catch(MalformedURLException e) {
				e.printStackTrace();
			} catch(IllegalStateException e) {
				channel.sendMessage("This Username Doesn't Exists !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
				);
			}

			assert data != null;
			if(!data.isSuccess()) {
				channel.sendMessage(data.getCause()).queue();
				return;
			}

			Player player = data.getPlayer();
			channel.sendMessage(getPlayerStats(player, HYPIXEL_OBJECT, GENERAL_OBJECT ).build()).queue();
		}
	}

	private EmbedBuilder getPlayerStats(Player player, JsonObject hypixelObject, JsonObject generalObject) {

		EmbedBuilder playerEmbed = new EmbedBuilder();

		final LocalDateTime dateFirstLogin = HypixelAPI.convertTimestampToDateTime(player.getFirstLogin());
		final LocalDateTime dateLastLogin = HypixelAPI.convertTimestampToDateTime(player.getLastLogin());
		final LocalDateTime dateLastLogout = HypixelAPI.convertTimestampToDateTime(player.getLastLogout());

		playerEmbed.setAuthor(format("Hypixel Player %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		playerEmbed.setColor(new Color(240, 197, 85));
		playerEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		playerEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		playerEmbed.addField(format("%s : ", hypixelObject.get("username").getAsString()), player.getDisplayName(), true);
		playerEmbed.addField("UUID : ", player.getUuid(), true);
		playerEmbed.addBlankField(false);
		playerEmbed.addField(format("%s : ", hypixelObject.get("first_login").getAsString()), format("%d-%d-%d at %d:%d:%d",
																dateFirstLogin.getYear(), dateFirstLogin.getMonthValue(), dateFirstLogin.getDayOfMonth(),
																dateFirstLogin.getHour(), dateFirstLogin.getMinute(), dateFirstLogin.getSecond()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("last_login").getAsString()), format("%d-%d-%d at %d:%d:%d",
																dateLastLogin.getYear(), dateLastLogin.getMonthValue(), dateLastLogin.getDayOfMonth(),
																dateLastLogin.getHour(), dateLastLogin.getMinute(), dateLastLogin.getSecond()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("last_logout").getAsString()), format("%d-%d-%d at %d:%d:%d",
																dateLastLogout.getYear(), dateLastLogout.getMonthValue(), dateLastLogout.getDayOfMonth(),
																dateLastLogout.getHour(), dateLastLogout.getMinute(), dateLastLogout.getSecond()), true);
		playerEmbed.addBlankField(false);
		playerEmbed.addField(format("%s : ", hypixelObject.get("last_game").getAsString()), player.getRecentGame(), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("minecraft_version").getAsString()), player.getLastMcVersion(), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("language").getAsString()), player.getLanguage(), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("achievement_points").getAsString()), String.valueOf(player.getAchievementPoints()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("network_exp").getAsString()), String.valueOf(player.getNetworkExperience()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("karma").getAsString()), String.valueOf(player.getKarma()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("lobby_gadget").getAsString()), player.getGadget(), true);
		playerEmbed.addBlankField(false);
		playerEmbed.addField("General Coins : ", String.valueOf(player.getAchievements().getGeneralCoins()), true);
		playerEmbed.addField("General Wins : ", String.valueOf(player.getAchievements().getGeneralWins()), true);
		playerEmbed.addBlankField(false);
		playerEmbed.addField(format("%s : ", hypixelObject.get("reward_streak").getAsString()), String.valueOf(player.getRewardStreak()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("reward_score").getAsString()), String.valueOf(player.getRewardScore()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("reward_high_score").getAsString()), String.valueOf(player.getRewardHighScore()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("total_daily_rewards").getAsString()), String.valueOf(player.getTotalDailyRewards()), true);
		playerEmbed.addField(format("%s : ", hypixelObject.get("total_rewards").getAsString()), String.valueOf(player.getTotalRewards()), true);

		return playerEmbed;
	}
}
