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

package fr.kokhaviel.bot.commands.hypixel.skyblock;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.player.PlayerData;
import fr.kokhaviel.api.hypixel.resources.skyblock.SkyblockProfiles;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.HYPIXEL_API;
import static java.lang.String.format;

public class SkyblockCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "skyblock")) {
			message.delete().queue();

			if(args.length < 3) {


				channel.sendMessage(format("%s : No Player Specified or No Profile Specified !",
								COMMANDS_OBJECT.get("missing_arguments").getAsString()))
						.queue(
								delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			int profileNum = Integer.parseInt(args[2]);

			if(profileNum < 1 || profileNum > 5) channel.sendMessage("Profile Number Must be Between 1 and 5").queue();

			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage(HYPIXEL_OBJECT.get("not_valid_username").getAsString()).queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}


			PlayerData.Player player;

			try {
				player = HYPIXEL_API.getPlayerData(args[1]).getPlayer();
				channel.sendMessageEmbeds(getSkyblockStats(player, GENERAL_OBJECT, profileNum).build()).queue();
			} catch(MalformedURLException e) {
				channel.sendMessage("Player " + args[1] + " not found").queue();
			}

		}
	}

	public EmbedBuilder getSkyblockStats(PlayerData.Player player, JsonObject generalObject, int profile) throws MalformedURLException {
		EmbedBuilder hypixelEmbed = new EmbedBuilder();

		hypixelEmbed.setAuthor("Hypixel Player Skyblock Stats", null, Config.HYPIXEL_ICON);
		hypixelEmbed.setColor(Color.GREEN);
		hypixelEmbed.setTitle(format("[%s] [%s] %s Stats",
				player.getRank(), player.getServerRank().equals("NONE") ? "NO RANK" : player.getServerRank(), player.getDisplayName()));
		hypixelEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG
				+ "\nHypixel API by Kokhaviel (https://github.com/Kokhaviel/HypixelAPI/)", Config.DEVELOPER_AVATAR);

		SkyblockProfiles.Member member = null;

		final SkyblockProfiles.Profile skyblockProfile = HYPIXEL_API.getSkyblockData(player.getDisplayName()).getProfile(profile);
		final List<String> membersList = skyblockProfile.membersList;
		int i = 1;

		for(String memberUUID : membersList) {
			if(memberUUID.equals(player.getUuid())) {
				member = skyblockProfile.getMember(i);
				break;
			}
			i++;
		}

		assert member != null;
		hypixelEmbed.addField("Coins : ", String.valueOf(member.getCoinPurse()), true);
		hypixelEmbed.addField("Fairy Souls : ", String.valueOf(member.getCollectedFairySouls()), true);
		hypixelEmbed.addField("Deaths : ", String.valueOf(member.getDeaths()), true);

		hypixelEmbed.addBlankField(false);

		hypixelEmbed.addField("Farming Exp : ", String.valueOf(member.getFarmingExp()), true);
		hypixelEmbed.addField("Mining Exp : ", String.valueOf(member.getMiningExp()), true);
		hypixelEmbed.addField("Combat Exp : ", String.valueOf(member.getCombatExp()), true);
		hypixelEmbed.addField("Foraging Exp : ", String.valueOf(member.getForagingExp()), true);
		hypixelEmbed.addField("Fishing Exp : ", String.valueOf(member.getFishingExp()), true);
		hypixelEmbed.addField("Enchanting Exp : ", String.valueOf(member.getEnchantingExp()), true);
		hypixelEmbed.addField("Alchemy Exp : ", String.valueOf(member.getAlchemyExp()), true);
		hypixelEmbed.addField("RuneCrafting Exp : ", String.valueOf(member.getRuneCraftingExp()), true);
		hypixelEmbed.addField("Taming Exp : ", String.valueOf(member.getTamingExp()), true);

		hypixelEmbed.addBlankField(false);

		SkyblockProfiles.Stats stats = member.getStats();

		hypixelEmbed.addField("Kills : ", String.valueOf(stats.getKills()), true);
		hypixelEmbed.addField("Highest Damage Given : ", String.valueOf(stats.getHighestDamageGiven()), true);
		hypixelEmbed.addField("Deaths : ", String.valueOf(stats.getDeaths()), true);
		hypixelEmbed.addField("Void Deaths : ", String.valueOf(stats.getVoidDeaths()), true);
		hypixelEmbed.addField("Items Fished : ", String.valueOf(stats.getItemsFished()), true);

		return hypixelEmbed;
	}
}
