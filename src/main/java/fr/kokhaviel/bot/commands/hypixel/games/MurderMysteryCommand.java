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
import fr.kokhaviel.api.hypixel.player.stats.MurderMystery;
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

public class MurderMysteryCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "murdermystery")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "murdermystery <Player>").queue(
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
			channel.sendMessage(getMurderMysteryStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getMurderMysteryStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		MurderMystery murderMystery = player.getStats().getMurderMystery();
		EmbedBuilder mmEmbed = new EmbedBuilder();
		mmEmbed.setAuthor(format("Hypixel Murder Mystery %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		mmEmbed.setColor(new Color(137, 103, 39));
		mmEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		mmEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		mmEmbed.addField("Coins : ", String.valueOf(murderMystery.getCoins()), true);
		mmEmbed.addField("Coins Picked Up : ", String.valueOf(murderMystery.getCoinsPickedUp()), true);
		mmEmbed.addField("Games : ", String.valueOf(murderMystery.getGames()), true);
		mmEmbed.addField("Wins : ", String.valueOf(murderMystery.getWins()), true);
		mmEmbed.addField("Murderer Wins : ", String.valueOf(murderMystery.getWinsMurderer()), true);
		mmEmbed.addField("Detective Wins : ", String.valueOf(murderMystery.getWinsDetective()), true);
		mmEmbed.addField("Hero : ", String.valueOf(murderMystery.getWinsHero()), true);
		mmEmbed.addField("Kills : ", String.valueOf(murderMystery.getKills()), true);
		mmEmbed.addField("Deaths : ", String.valueOf(murderMystery.getDeaths()), true);
		mmEmbed.addField("Detective Chance : ", String.valueOf(murderMystery.getDetectiveChance()), true);
		mmEmbed.addField("Bow Kills : ", String.valueOf(murderMystery.getBowKills()), true);
		mmEmbed.addField("Trap Kills : ", String.valueOf(murderMystery.getTrapKills()), true);
		mmEmbed.addField("Longest Time as Survivor : ", String.valueOf(murderMystery.getLongestTimeSurvivor()), true);
		mmEmbed.addField("Murderer Chance : ", String.valueOf(murderMystery.getMurderChance()), true);
		mmEmbed.addField("Knife Kills : ", String.valueOf(murderMystery.getKnifeKills()), true);
		mmEmbed.addField("Thrown Knife Kills : ", String.valueOf(murderMystery.getThrownKnifeKills()), true);

		return mmEmbed;
	}
}
