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
import fr.kokhaviel.api.hypixel.player.stats.Bedwars;
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

public class BedwarsCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "bedwars")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "bedwars <Player>").queue(
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
			channel.sendMessage(getBedwarsStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT ).build()).queue();
			channel.sendMessage(getBedwarsStats2(player, GENERAL_OBJECT, HYPIXEL_OBJECT ).build()).queue();
		}
	}

	private EmbedBuilder getBedwarsStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Bedwars bedwars = player.getStats().getBedwars();
		EmbedBuilder bedwarsEmbed = new EmbedBuilder();
		bedwarsEmbed.setAuthor(format("Hypixel Bedwars %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		bedwarsEmbed.setColor(new Color(190, 46, 46));
		bedwarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		bedwarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		bedwarsEmbed.addField("Coins : ", String.valueOf(bedwars.getCoins()), true);
		bedwarsEmbed.addField("Level : ", String.valueOf(player.getAchievements().getBedwarsLevel()), true);
		bedwarsEmbed.addField("Experience : ", String.valueOf(bedwars.getExperience()), true);
		bedwarsEmbed.addBlankField(false);
		bedwarsEmbed.addField("Death Cry : ", bedwars.getDeathCry().replace("deathcry_", ""), true);
		bedwarsEmbed.addField("Spray : ", bedwars.getSpray().replace("sprays_", ""), true);
		bedwarsEmbed.addField("Kill Effect : ", bedwars.getKillEffect().replace("killeffect_", ""), true);
		bedwarsEmbed.addField("Kill Message : ", bedwars.getKillMessage().replace("killmessages_", ""), true);
		bedwarsEmbed.addField("Victory Dance : ", bedwars.getVictoryDance().replace("victorydance_", ""), true);
		bedwarsEmbed.addField("Glyph : ", bedwars.getGlyph().replace("glyph_", ""), true);
		bedwarsEmbed.addField("Projectile Trail : ", bedwars.getProjectileTrail().replace("projectiletrail_", ""), true);
		bedwarsEmbed.addBlankField(false);
		bedwarsEmbed.addField("Wins : ", String.valueOf(bedwars.getWins()), true);
		bedwarsEmbed.addField("Win Streak : ", String.valueOf(bedwars.getWinstreak()), true);
		bedwarsEmbed.addField("Losses : ", String.valueOf(bedwars.getLosses()), true);
		bedwarsEmbed.addField("Games Played : ", String.valueOf(bedwars.getGamesPlayed()), true);
		bedwarsEmbed.addBlankField(false);
		bedwarsEmbed.addField("Resources Collected : ", String.valueOf(bedwars.getResourcesCollected()), true);
		bedwarsEmbed.addField("Iron Collected : ", String.valueOf(bedwars.getIronCollected()), true);
		bedwarsEmbed.addField("Gold Collected : ", String.valueOf(bedwars.getGoldCollected()), true);
		bedwarsEmbed.addField("Diamond Collected : ", String.valueOf(bedwars.getDiamondCollected()), true);
		bedwarsEmbed.addField("Emerald Collected : ", String.valueOf(bedwars.getEmeraldCollected()), true);
		bedwarsEmbed.addField("Items Purchased : ", String.valueOf(bedwars.getItemsPurchased()), true);

		return bedwarsEmbed;
	}

	private EmbedBuilder getBedwarsStats2(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Bedwars bedwars = player.getStats().getBedwars();
		EmbedBuilder bedwarsEmbed = new EmbedBuilder();
		bedwarsEmbed.setAuthor(format("Hypixel Bedwars %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		bedwarsEmbed.setColor(new Color(240, 197, 85));
		bedwarsEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		bedwarsEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		bedwarsEmbed.addField("Kills : ", String.valueOf(bedwars.getKills()), true);
		bedwarsEmbed.addField("Deaths : ", String.valueOf(bedwars.getDeaths()), true);
		bedwarsEmbed.addField("Beds Broken : ", String.valueOf(bedwars.getBedsBroken()), true);
		bedwarsEmbed.addField("Beds Lost : ", String.valueOf(bedwars.getBedsLost()), true);
		bedwarsEmbed.addField("Final Kills : ", String.valueOf(bedwars.getFinalKills()), true);
		bedwarsEmbed.addField("Final Deaths : ", String.valueOf(bedwars.getFinalDeaths()), true);
		bedwarsEmbed.addField("Void Kills : ", String.valueOf(bedwars.getVoidKills()), true);
		bedwarsEmbed.addField("Void Final Kills : ", String.valueOf(bedwars.getVoidFinalKills()), true);
		bedwarsEmbed.addField("Void Deaths : ", String.valueOf(bedwars.getVoidDeaths()), true);
		bedwarsEmbed.addField("Void Final Deaths : ", String.valueOf(bedwars.getVoidFinalDeaths()), true);
		bedwarsEmbed.addField("Fall Kills : ", String.valueOf(bedwars.getFallKills()), true);
		bedwarsEmbed.addField("Fall Final Kills : ", String.valueOf(bedwars.getFallFinalKills()), true);
		bedwarsEmbed.addField("Fall Deaths : ", String.valueOf(bedwars.getFallDeaths()), true);
		bedwarsEmbed.addField("Fall Final Deaths : ", String.valueOf(bedwars.getFallFinalDeaths()), true);
		bedwarsEmbed.addField("Projectile Kills : ", String.valueOf(bedwars.getProjectileKills()), true);
		bedwarsEmbed.addField("Projectile Deaths : ", String.valueOf(bedwars.getProjectileDeaths()), true);
		bedwarsEmbed.addField("Magic Kills : ", String.valueOf(bedwars.getMagicKills()), true);
		bedwarsEmbed.addField("Magic Final Kills : ", String.valueOf(bedwars.getMagicFinalKills()), true);
		bedwarsEmbed.addField("Magic Deaths : ", String.valueOf(bedwars.getMagicDeaths()), true);
		bedwarsEmbed.addField("Magic Final Deaths : ", String.valueOf(bedwars.getMagicFinalDeaths()), true);

		return bedwarsEmbed;
	}
}
