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
import fr.kokhaviel.api.hypixel.player.stats.TrueCombat;
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

public class CrazyWallsCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "crazywalls")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "crazywalls <Player>").queue(
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
			channel.sendMessage(getCrazyWallsStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getCrazyWallsStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		TrueCombat trueCombat = player.getStats().getTrueCombat();
		EmbedBuilder tCEmbed = new EmbedBuilder();
		tCEmbed.setAuthor(format("Hypixel Crazy Walls %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		tCEmbed.setColor(new Color(240, 197, 85));
		tCEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		tCEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		tCEmbed.addField("Coins : ", String.valueOf(trueCombat.getCoins()), true);
		tCEmbed.addField("Golden Skulls : ", String.valueOf(trueCombat.getGoldenSkulls()), true);
		tCEmbed.addField("Enchanted Items : ", String.valueOf(trueCombat.getItemsEnchanted()), true);
		tCEmbed.addField("Arrows Shot : ", String.valueOf(trueCombat.getArrowsShot()), true);
		tCEmbed.addField("Arrows Hit : ", String.valueOf(trueCombat.getArrowsHit()), true);
		tCEmbed.addField("Kills : ", String.valueOf(trueCombat.getKills()), true);
		tCEmbed.addField("Deaths : ", String.valueOf(trueCombat.getDeaths()), true);
		tCEmbed.addField("Survived Players : ", String.valueOf(trueCombat.getSurvivedPlayers()), true);
		tCEmbed.addField("Skulls Gathered : ", String.valueOf(trueCombat.getSkullsGathered()), true);
		tCEmbed.addField("Wins : ", String.valueOf(trueCombat.getWins()), true);
		tCEmbed.addField("Winstreak : ", String.valueOf(trueCombat.getWinStreak()), true);
		tCEmbed.addField("Losses : ", String.valueOf(trueCombat.getLosses()), true);
		tCEmbed.addField("Games : ", String.valueOf(trueCombat.getGames()), true);
		tCEmbed.addField("Gold Dust : ", String.valueOf(trueCombat.getGoldDust()), true);

		return tCEmbed;
	}
}
