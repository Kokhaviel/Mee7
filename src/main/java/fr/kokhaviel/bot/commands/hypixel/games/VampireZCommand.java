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
import fr.kokhaviel.api.hypixel.player.stats.VampireZ;
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

public class VampireZCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "vampirez")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "vampirez <Player>").queue(
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
			channel.sendMessage(getVampireZStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getVampireZStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		VampireZ vampireZ = player.getStats().getVampireZ();
		Challenges.ChallengesAllTime challenges = player.getChallenges().getChallengesAllTime();
		EmbedBuilder vampireZEmbed = new EmbedBuilder();
		vampireZEmbed.setAuthor(format("Hypixel VampireZ %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		vampireZEmbed.setColor(new Color(255, 85, 85));
		vampireZEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		vampireZEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		vampireZEmbed.addField("Coins : ", String.valueOf(vampireZ.getCoins()), true);
		vampireZEmbed.addField("Gold Bought : ", String.valueOf(vampireZ.getGoldBought()), true);
		vampireZEmbed.addField("Human Wins : ", String.valueOf(vampireZ.getHumanWins()), true);
		vampireZEmbed.addField("Zombie Kills : ", String.valueOf(vampireZ.getZombiesKills()), true);
		vampireZEmbed.addField("Vampire Kills : ", String.valueOf(vampireZ.getVampireKills()), true);
		vampireZEmbed.addField("Most Vampire Kills : ", String.valueOf(vampireZ.getMostVampireKills()), true);
		vampireZEmbed.addField("Human Deaths : ", String.valueOf(vampireZ.getHumanDeaths()), true);
		vampireZEmbed.addField("Vampire Deaths : ", String.valueOf(vampireZ.getVampireDeaths()), true);
		vampireZEmbed.addBlankField(false);
		vampireZEmbed.addField("Fang Challenge : ", String.valueOf(challenges.getVampireZFangChallenge()), false);
		vampireZEmbed.addField("Gold Challenge : ", String.valueOf(challenges.getVampireZGoldChallenge()), false);
		vampireZEmbed.addField("Purify Challenge : ", String.valueOf(challenges.getVampireZPurifyingChallenge()), false);
		vampireZEmbed.addField("Last Stand Challenge : ", String.valueOf(challenges.getVampireZLastStandChallenge()), false);

		return vampireZEmbed;
	}
}
