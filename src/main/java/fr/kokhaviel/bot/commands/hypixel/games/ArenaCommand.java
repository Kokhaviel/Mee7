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
import fr.kokhaviel.api.hypixel.player.stats.Arena;
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

public class ArenaCommand extends ListenerAdapter {

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

		if(args[0].equalsIgnoreCase(prefix + "arena")) {
			if(args.length < 2) {
				channel.sendMessage(format("%s : ", HYPIXEL_OBJECT.get("no_username").getAsString()) + prefix + "arena <Player>").queue(
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
			channel.sendMessage(getArenaStats(player, GENERAL_OBJECT, HYPIXEL_OBJECT).build()).queue();
		}
	}

	private EmbedBuilder getArenaStats(Player player, JsonObject generalObject, JsonObject hypixelObject) {
		Arena arena = player.getStats().getArena();
		EmbedBuilder arenaEmbed = new EmbedBuilder();
		arenaEmbed.setAuthor(format("Hypixel Arena %s", hypixelObject.get("stats").getAsString()), null, Config.HYPIXEL_ICON);
		arenaEmbed.setColor(new Color(255, 170, 0));
		arenaEmbed.setTitle(format("[%s] %s %s", player.getServerRank(), player.getDisplayName(), hypixelObject.get("stats").getAsString()));
		arenaEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		arenaEmbed.addField("Coins : ", String.valueOf(arena.getCoins()), true);
		arenaEmbed.addField("Keys : ", String.valueOf(arena.getKeys()), true);
		arenaEmbed.addField("Magical Chest : ", String.valueOf(arena.getMagicalChest()), true);
		arenaEmbed.addBlankField(false);
		arenaEmbed.addField("Offensive : ", arena.getOffensive(), true);
		arenaEmbed.addField("Utility : ", arena.getUtility(), true);
		arenaEmbed.addField("Support : ", arena.getSupport(), true);
		arenaEmbed.addField("Ultimate : ", arena.getUltimate(), true);
		arenaEmbed.addField("Active Rune : ", arena.getActiveRune(), true);
		arenaEmbed.addField("Selected Sword : ", arena.getSelectedSword(), true);
		arenaEmbed.addBlankField(false);
		arenaEmbed.addField("Rune Level Energy : ", String.valueOf(arena.getRuneLevelEnergy()), true);
		arenaEmbed.addField("Damage Level : ", String.valueOf(arena.getLevelDamage()), true);
		arenaEmbed.addField("Health Level : ", String.valueOf(arena.getLevelHealth()), true);
		arenaEmbed.addField("Energy Level : ", String.valueOf(arena.getLevelEnergy()), true);
		arenaEmbed.addField("Cooldown Level : ", String.valueOf(arena.getLevelCooldown()), true);

		return arenaEmbed;
	}
}
