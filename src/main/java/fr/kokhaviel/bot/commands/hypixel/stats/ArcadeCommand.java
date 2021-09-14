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
import fr.kokhaviel.api.hypixel.games.Arcade;
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

public class ArcadeCommand extends ListenerAdapter {

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


		if(args[0].equalsIgnoreCase(prefix + "arcade")) {

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
				channel.sendMessageEmbeds(getArcadeStats(player, GENERAL_OBJECT).build()).queue();
			} catch(MalformedURLException e) {
				channel.sendMessage("Player " + args[1] + " not found").queue();
			}

		}

	}

	public EmbedBuilder getArcadeStats(PlayerData.Player player, JsonObject generalObject) {
		EmbedBuilder hypixelEmbed = new EmbedBuilder();

		Arcade arcade = player.getStats().getArcade();

		hypixelEmbed.setAuthor("Hypixel Player Arcade Stats", null, Config.HYPIXEL_ICON);
		hypixelEmbed.setColor(Color.GREEN);
		hypixelEmbed.setTitle(format("[%s] %s Stats",
				player.getRank(), player.getDisplayName()));
		hypixelEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG
				+ "\nHypixel API by Kokhaviel (https://github.com/Kokhaviel/HypixelAPI/)", Config.DEVELOPER_AVATAR);

		hypixelEmbed.addField("Coins : ", String.valueOf(arcade.getCoins()), true);
		hypixelEmbed.addField("Dayone Wins : ", String.valueOf(arcade.getDayOneWins()), true);
		hypixelEmbed.addField("Mini Walls Wins : ", String.valueOf(arcade.getMiniWallsWins()), true);
		hypixelEmbed.addField("Soccer Wins : ", String.valueOf(arcade.getSoccerWins()), true);
		hypixelEmbed.addField("Farm Hunt Wins : ", String.valueOf(arcade.getFarmHuntWins()), true);
		hypixelEmbed.addField("Hide and Seek Wins : ", String.valueOf(arcade.getHideAndSeekHiderWins()
																			+ arcade.getHideAndSeekSeekerWins()), true);
		hypixelEmbed.addField("Zombies Wins : ", String.valueOf(arcade.getZombiesWins()), true);
		hypixelEmbed.addField("Party Games Wins : ", String.valueOf(arcade.getPartyGamesWins()), true);
		hypixelEmbed.addField("Hole in the Wall Wins : ", String.valueOf(arcade.getHoleInTheWallWins()), true);
		hypixelEmbed.addField("Simon Says Wins : ", String.valueOf(arcade.getHoleInTheWallWins()), true);
		hypixelEmbed.addField("One in the Quiver Wins : ", String.valueOf(arcade.getHoleInTheWallWins()), true);
		hypixelEmbed.addField("Ender Spleef Wins : ", String.valueOf(arcade.getEnderSpleefWins()), true);
		hypixelEmbed.addField("Dragon Wars Wins : ", String.valueOf(arcade.getDragonWarsWins()), true);
		hypixelEmbed.addField("Pixel Painters Wins : ", String.valueOf(arcade.getPixelPaintersWins()), true);
		hypixelEmbed.addField("Throw Out Wins : ", String.valueOf(arcade.getThrownOutWins()), true);

		return hypixelEmbed;
	}
}
