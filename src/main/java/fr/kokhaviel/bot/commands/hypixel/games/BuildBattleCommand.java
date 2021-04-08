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
import fr.kokhaviel.api.hypixel.player.stats.BuildBattle;
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

public class BuildBattleCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "buildbattle")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "buildbattle <Player>").queue(
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
			channel.sendMessage(getBuildBattleStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getBuildBattleStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		BuildBattle buildBattle = player.getStats().getBuildBattle();
		EmbedBuilder bBEmbed = new EmbedBuilder();
		bBEmbed.setAuthor(format("Hypixel Build Battle %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		bBEmbed.setColor(new Color(148, 120, 73));
		bBEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		bBEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		bBEmbed.addField("Coins : ", String.valueOf(buildBattle.getCoins()), true);
		bBEmbed.addField("Score : ", String.valueOf(buildBattle.getScore()), true);
		bBEmbed.addField("Games Played : ", String.valueOf(buildBattle.getGamesPlayed()), true);
		bBEmbed.addField("Total Votes : ", String.valueOf(buildBattle.getTotalVotes()), true);
		bBEmbed.addField("Solo Most Points : ", String.valueOf(buildBattle.getTotalVotes()), true);
		bBEmbed.addField("Team Most Points : ", String.valueOf(buildBattle.getTotalVotes()), true);

		return bBEmbed;
	}
}
