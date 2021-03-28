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

package fr.kokhaviel.bot.commands.hypixel.games.blitz;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.blitz.Blitz;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BlitzStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "blitz")) {

			if(args.length == 1) {
				channel.sendMessage("You need to specify a player : " + prefix + "blitz <Player>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage("You must specify a valid Minecraft username !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			message.delete().queue();
			final Player player = sloth.getPlayer(args[1]);
			final Blitz blitz = player.getStats().getBlitz();
			channel.sendMessage(getBlitzStats(player, blitz).build()).queue();
		}
	}

	private EmbedBuilder getBlitzStats(Player player, Blitz blitz) {
		EmbedBuilder blitzEmbed = new EmbedBuilder();

		blitzEmbed.setAuthor("Blitz Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		blitzEmbed.setColor(new Color(73, 240, 255));
		blitzEmbed.setTitle(player.getUsername() + " Stats");
		blitzEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		blitzEmbed.addField("Coins : ", String.valueOf(blitz.getCoins()), true);
		blitzEmbed.addField("Games Played : ", String.valueOf(blitz.getGamesPlayed()), true);
		blitzEmbed.addField("Time Played : ", blitz.getTimePlayed() / 60 + " minutes", true);

		blitzEmbed.addBlankField(false);
		blitzEmbed.addField("Kills : ", String.valueOf(blitz.getKills()), true);
		blitzEmbed.addField("Deaths : ", String.valueOf(blitz.getDeaths()), true);
		blitzEmbed.addField("KDR : ", String.valueOf(blitz.getKD()), true);
		blitzEmbed.addField("Wins : ", String.valueOf(blitz.getWins()), true);
		blitzEmbed.addField("Team Wins : ", String.valueOf(blitz.getTeamWins()), true);
		blitzEmbed.addField("Win Loss : ", String.valueOf(blitz.getWinLoss()), true);
		blitzEmbed.addField("Win percentage : ", String.valueOf(blitz.getWinPercentage()), true);

		blitzEmbed.addBlankField(false);
		blitzEmbed.addField("Damage : ", String.valueOf(blitz.getDamage()), true);
		blitzEmbed.addField("Damage Taken : ", String.valueOf(blitz.getDamageTaken()), true);
		blitzEmbed.addField("Arrows Fired : ", String.valueOf(blitz.getArrowsFired()), true);
		blitzEmbed.addField("Arrows Hit : ", String.valueOf(blitz.getArrowsHit()), true);
		blitzEmbed.addField("Chests Opened : ", String.valueOf(blitz.getChestsOpened()), true);
		blitzEmbed.addField("Mob Spawned : ", String.valueOf(blitz.getMobsSpawned()), true);
		blitzEmbed.addField("Potions Drunk : ", String.valueOf(blitz.getPotionsDrunk()), true);
		blitzEmbed.addField("Potions Thrown : ", String.valueOf(blitz.getPotionsThrown()), true);

		return blitzEmbed;
	}
}
