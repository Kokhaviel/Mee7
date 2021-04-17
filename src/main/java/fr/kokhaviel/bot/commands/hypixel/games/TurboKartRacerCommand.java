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
import fr.kokhaviel.api.hypixel.player.Challenges;
import fr.kokhaviel.api.hypixel.player.Player;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.hypixel.player.stats.GingerBread;
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

public class TurboKartRacerCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "tkr")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "tkr <Player>").queue(
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
			channel.sendMessage(getTkrStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getTkrStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		GingerBread tkr = player.getStats().getGingerBread();
		Challenges.ChallengesAllTime challenges = player.getChallenges().getChallengesAllTime();
		EmbedBuilder tkrEmbed = new EmbedBuilder();
		tkrEmbed.setAuthor(format("Hypixel Turbo Kart Racer %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		tkrEmbed.setColor(new Color(85, 255, 85));
		tkrEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		tkrEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		tkrEmbed.addField("Coins : ", String.valueOf(tkr.getCoins()), true);
		tkrEmbed.addField("Coins Picked Up : ", String.valueOf(tkr.getCoinsPickedUp()), true);
		tkrEmbed.addField("Box Picked Up : ", String.valueOf(tkr.getBoxPickups()), true);
		tkrEmbed.addField("Wins : ", String.valueOf(tkr.getWins()), true);
		tkrEmbed.addField("Laps : ", String.valueOf(tkr.getLaps()), true);
		tkrEmbed.addField("Gold Trophies : ", String.valueOf(tkr.getGoldTrophies()), true);
		tkrEmbed.addField("Silver Trophies : ", String.valueOf(tkr.getSilverTrophies()), true);
		tkrEmbed.addField("Bronze Trophies : ", String.valueOf(tkr.getBronzeTrophies()), true);
		tkrEmbed.addBlankField(false);
		tkrEmbed.addField("Helmet : ", String.valueOf(tkr.getHelmet()), true);
		tkrEmbed.addField("Jacket : ", String.valueOf(tkr.getJacket()), true);
		tkrEmbed.addField("Pants : ", String.valueOf(tkr.getPants()), true);
		tkrEmbed.addField("Shoes : ", String.valueOf(tkr.getShoes()), true);
		tkrEmbed.addBlankField(false);
		tkrEmbed.addField("Coin Challenge : ", String.valueOf(challenges.getGingerBreadCoinChallenge()), false);
		tkrEmbed.addField("First Place Challenge : ", String.valueOf(challenges.getGingerBreadFirstPlaceChallenge()), false);
		tkrEmbed.addField("Banana Challenge : ", String.valueOf(challenges.getGingerBreadBananaChallenge()), false);
		tkrEmbed.addField("Leaderboard Challenge : ", String.valueOf(challenges.getGingerBreadLeaderboardChallenge()), false);
		return tkrEmbed;
	}
}
