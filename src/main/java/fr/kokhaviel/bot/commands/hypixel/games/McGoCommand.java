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
import fr.kokhaviel.api.hypixel.player.stats.McGo;
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

public class McGoCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "mcgo")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "mcgo <Player>").queue(
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
			channel.sendMessage(getMCGOStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getMcGoQuests(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getMCGOStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		McGo mcGo = player.getStats().getMcgo();
		EmbedBuilder mcGoEmbed = new EmbedBuilder();
		mcGoEmbed.setAuthor(format("Hypixel McGo %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		mcGoEmbed.setColor(new Color(158, 154, 171));
		mcGoEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		mcGoEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		mcGoEmbed.addField("Coins : ", String.valueOf(mcGo.getCoins()), true);
		mcGoEmbed.addField("Wins : ", String.valueOf(mcGo.getWins()), true);
		mcGoEmbed.addField("Deathmatch Wins : ", String.valueOf(mcGo.getDeathmatchWins()), true);
		mcGoEmbed.addField("Rounds Wins : ", String.valueOf(mcGo.getRoundsWins()), true);
		mcGoEmbed.addField("Games Played : ", String.valueOf(mcGo.getGamesPlayed()), true);
		mcGoEmbed.addField("Kills : ", String.valueOf(mcGo.getKills()), true);
		mcGoEmbed.addField("Deathmatch Kills : ", String.valueOf(mcGo.getDeathmatchKills()), true);
		mcGoEmbed.addField("Headshot Kills : ", String.valueOf(mcGo.getHeadshotsKills()), true);
		mcGoEmbed.addField("Cop Kills : ", String.valueOf(mcGo.getCopKills()), true);
		mcGoEmbed.addField("Criminal Kills : ", String.valueOf(mcGo.getCriminalKills()), true);
		mcGoEmbed.addField("Grenade Kills : ", String.valueOf(mcGo.getGrenadeKills()), true);
		mcGoEmbed.addField("Deaths : ", String.valueOf(mcGo.getDeaths()), true);
		mcGoEmbed.addField("Deathmatch Deaths : ", String.valueOf(mcGo.getDeathmatchDeaths()), true);
		mcGoEmbed.addField("Assists : ", String.valueOf(mcGo.getAssists()), true);
		mcGoEmbed.addField("Shots Fired : ", String.valueOf(mcGo.getShotsFired()), true);
		mcGoEmbed.addField("Planted : ", String.valueOf(mcGo.getPlanted()), true);
		mcGoEmbed.addField("Defused : ", String.valueOf(mcGo.getDefused()), true);

		return mcGoEmbed;
	}

	private EmbedBuilder getMcGoQuests(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Challenges.ChallengesAllTime challenges = player.getChallenges().getChallengesAllTime();
		EmbedBuilder mcGoEmbed = new EmbedBuilder();
		mcGoEmbed.setAuthor(format("Hypixel McGo %s", hypixelObject.get("quests").getAsString()), null, Config.HYPIXEL_ICON);
		mcGoEmbed.setColor(new Color(158, 154, 171));
		mcGoEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("quests").getAsString()));
		mcGoEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		mcGoEmbed.addField("Pistol Challenge : ", String.valueOf(challenges.getMcgoPistolChallenge()), false);
		mcGoEmbed.addField("Knife Challenge : ", String.valueOf(challenges.getMcgoKnifeChallenge()), false);
		mcGoEmbed.addField("Grenade Challenge : ", String.valueOf(challenges.getMcgoGrenadeChallenge()), false);
		mcGoEmbed.addField("Killing Spree Challenge : ", String.valueOf(challenges.getMcgoKillingSpreeChallenge()), false);

		return mcGoEmbed;
	}
}
