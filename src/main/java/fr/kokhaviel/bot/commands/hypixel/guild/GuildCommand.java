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

package fr.kokhaviel.bot.commands.hypixel.guild;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.HypixelAPI;
import fr.kokhaviel.api.hypixel.guild.Guild;
import fr.kokhaviel.api.hypixel.guild.GuildStats;
import fr.kokhaviel.api.hypixel.player.Player;
import fr.kokhaviel.api.hypixel.player.PlayerData;
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
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.hypixelAPI;
import static java.lang.String.format;

public class GuildCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "guild")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "player <Player>").queue(
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
			GuildStats guildStats = null;
			try {
				data = hypixelAPI.getPlayerData(args[1]);
				guildStats = hypixelAPI.getGuildStats(args[1]);
			} catch(MalformedURLException e) {
				e.printStackTrace();
			} catch(IllegalStateException e) {
				channel.sendMessage("This Username Doesn't Exists !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
				);
			}

			assert data != null;
			assert guildStats != null;
			if(!data.isSuccess()) {
				channel.sendMessage(data.getCause()).queue();
				return;
			}

			Player player = data.getPlayer();
			Guild guild = guildStats.getGuild();
			channel.sendMessage(getGuildStats(player, guild, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
			channel.sendMessage(getGuildRanks(guild, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getGuildStats(Player player, Guild guild, JsonObject generalObject, JsonObject hypixelObject) {
		final LocalDateTime creationDate = HypixelAPI.convertTimestampToDateTime(guild.getCreated());
		EmbedBuilder guildEmbed = new EmbedBuilder();
		guildEmbed.setAuthor(format("Hypixel Guild %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		guildEmbed.setColor(new Color(240, 197, 85));
		guildEmbed.setTitle(format("[%s] %s Guild", player.getServerRank(), player.getDisplayName()));
		guildEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		guildEmbed.addField("Name : ", guild.getName(), false);
		guildEmbed.addField("Description : ", guild.getDescription(), false);
		guildEmbed.addField("Tag : ", guild.getTag(), false);
		guildEmbed.addField("Tag Color : ", guild.getTagColor(), false);
		guildEmbed.addField("Coins : ", String.valueOf(guild.getCoins()), false);
		guildEmbed.addField("Total Coins : ", String.valueOf(guild.getCoinsEver()), false);
		guildEmbed.addField("Total Coins : ", String.valueOf(guild.getCoinsEver()), false);
		guildEmbed.addField("Creation : ", format("%d - %d - %d", creationDate.getYear(),
																		creationDate.getMonthValue(),
																		creationDate.getDayOfMonth()), false);
		guildEmbed.addField("Experience : ", String.valueOf(guild.getExp()), false);
		guildEmbed.addField("Members : ", String.valueOf(guild.getMembers().size()), false);

		return guildEmbed;
	}

	private EmbedBuilder getGuildRanks(Guild guild, JsonObject generalObject, JsonObject hypixelObject) {
		EmbedBuilder guildEmbed = new EmbedBuilder();
		guildEmbed.setAuthor(format("Hypixel Guild %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		guildEmbed.setColor(new Color(240, 197, 85));
		guildEmbed.setTitle("Hypixel Guild Ranks");
		guildEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		guild.getRanks().forEach(rank -> {
			final LocalDateTime creationDate = HypixelAPI.convertTimestampToDateTime(rank.getCreated());
			guildEmbed.addField(format("[%d] %s", rank.getPriority(), rank.getName()), format("%d - %d - %d", creationDate.getYear(), creationDate.getMonthValue(), creationDate.getDayOfMonth()), false);
		});

		return guildEmbed;
	}
}
