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
import fr.kokhaviel.api.hypixel.player.stats.Arcade;
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

public class ArcadeCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "arcade")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "arcade <Player>").queue(
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
			channel.sendMessage(getArcadeStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getArcadeStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Arcade arcade = player.getStats().getArcade();
		EmbedBuilder arcadeEmbed = new EmbedBuilder();
		arcadeEmbed.setAuthor(format("Hypixel Arcade %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		arcadeEmbed.setColor(new Color(133, 216, 148));
		arcadeEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		arcadeEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		arcadeEmbed.addField("Coins : ", String.valueOf(arcade.getCoins()), true);
		arcadeEmbed.addBlankField(false);
		arcadeEmbed.addField("Wins Mini Walls : ", String.valueOf(arcade.getWinsMiniWalls()), true);
		arcadeEmbed.addField("Wins Simon Says : ", String.valueOf(arcade.getWinsSimonSays()), true);
		arcadeEmbed.addField("Wins Party : ", String.valueOf(arcade.getWinsParty()), true);
		arcadeEmbed.addField("Wins Day One : ", String.valueOf(arcade.getWinsDaysone()), true);
		arcadeEmbed.addField("Wins Farm Hunt : ", String.valueOf(arcade.getWinsFarmHunt()), true);
		arcadeEmbed.addField("Wins Hole In The Wall : ", String.valueOf(arcade.getWinsHoleWall()), true);
		arcadeEmbed.addField("Wins Soccer : ", String.valueOf(arcade.getWinsSoccer()), true);
		arcadeEmbed.addField("Wins One In The Quiver : ", String.valueOf(arcade.getWinsOneQuiver()), true);
		arcadeEmbed.addField("Wins Dragon Wars : ", String.valueOf(arcade.getWinsDragonWars()), true);
		arcadeEmbed.addField("Wins Zombies : ", String.valueOf(arcade.getWinsZombies()), true);
		arcadeEmbed.addField("Wins Pixel Painters : ", String.valueOf(arcade.getWinsDrawTheirThings()), true);
		arcadeEmbed.addField("Wins Throw Out : ", String.valueOf(arcade.getWinsThrowOut()), true);
		arcadeEmbed.addField("Wins Grinch Simulator : ", String.valueOf(arcade.getWinsGrinchSimul()), true);

		return arcadeEmbed;
	}
}
