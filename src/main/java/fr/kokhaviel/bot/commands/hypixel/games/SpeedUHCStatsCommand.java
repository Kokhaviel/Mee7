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

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.speeduhc.SpeedUHC;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class SpeedUHCStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "speeduhc")) {

			if(args.length == 1) {
				channel.sendMessage("You need to specify a player : " + prefix + "speeduhc <Player>").queue(
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
			final SpeedUHC speedUHC = player.getStats().getSpeedUHC();
			channel.sendMessage(getSpeedUHCStats(player, speedUHC).build()).queue();
			channel.sendMessage(getSpeedUHCSolo(player, speedUHC).build()).queue();
			channel.sendMessage(getTeamsStats(player, speedUHC).build()).queue();
			channel.sendMessage(getActiveKit(player, speedUHC).build()).queue();
		}
	}

	private EmbedBuilder getSpeedUHCStats(Player player, SpeedUHC speedUHC) {
		EmbedBuilder speedUHCEmbed = new EmbedBuilder();
		speedUHCEmbed.setAuthor("SpeedUHC Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		speedUHCEmbed.setColor(Color.YELLOW);
		speedUHCEmbed.setTitle(player.getUsername() + " Stats");
		speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		speedUHCEmbed.addField("Coins :", String.valueOf(speedUHC.getCoins()), true);
		speedUHCEmbed.addField("Wins : ", String.valueOf(speedUHC.getWins()), true);
		speedUHCEmbed.addField("Highest Win Streak : ", String.valueOf(speedUHC.getHighestWinstreak()), true);
		speedUHCEmbed.addField("Win Loss : ", String.valueOf(speedUHC.getWinLoss()), true);
		speedUHCEmbed.addField("Win Percentage : ", String.valueOf(speedUHC.getWinPercentage()), true);
		speedUHCEmbed.addField("Kills : ", String.valueOf(speedUHC.getKills()), true);
		speedUHCEmbed.addField("Highest Kill Streak : ", String.valueOf(speedUHC.getHighestKillstreak()), true);
		speedUHCEmbed.addField("Losses : ", String.valueOf(speedUHC.getLosses()), true);
		speedUHCEmbed.addField("Deaths : ", String.valueOf(speedUHC.getDeaths()), true);
		speedUHCEmbed.addField("Assists : ", String.valueOf(speedUHC.getAssists()), true);
		speedUHCEmbed.addField("KDR : ", String.valueOf(speedUHC.getKd()), true);
		speedUHCEmbed.addField("Games : ", String.valueOf(speedUHC.getGames()), true);
		speedUHCEmbed.addField("Enderpearl Thrown : ", String.valueOf(speedUHC.getEnderpearlsThrown()), true);
		speedUHCEmbed.addField("Arrows Shot", String.valueOf(speedUHC.getArrowsShot()), true);
		speedUHCEmbed.addField("Arrows Hit : ", String.valueOf(speedUHC.getArrowsHit()), true);
		speedUHCEmbed.addField("Blocks Placed : ", String.valueOf(speedUHC.getBlocksPlaced()), true);
		speedUHCEmbed.addField("Blocks Broken : ", String.valueOf(speedUHC.getBlocksBroken()), true);
		speedUHCEmbed.addField("Items Enchanted : ", String.valueOf(speedUHC.getItemsEnchanted()), true);
		speedUHCEmbed.addField("Salt : ", String.valueOf(speedUHC.getSalt()), true);
		speedUHCEmbed.addField("Tears : ", String.valueOf(speedUHC.getTears()), true);

		return speedUHCEmbed;
	}

	private EmbedBuilder getSpeedUHCSolo(Player player, SpeedUHC speedUHC) {
		EmbedBuilder speedUHCEmbed = new EmbedBuilder();
		speedUHCEmbed.setAuthor("SpeedUHC Solo Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		speedUHCEmbed.setColor(Color.YELLOW);
		speedUHCEmbed.setTitle(player.getUsername() + " Stats");
		speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		speedUHCEmbed.addField("Wins : ", String.valueOf(speedUHC.getGamemodes().getSolo().getWins()), true);
		speedUHCEmbed.addField("Wins Streak : ", String.valueOf(speedUHC.getGamemodes().getSolo().getWinstreak()), true);
		speedUHCEmbed.addField("Kills : ", String.valueOf(speedUHC.getGamemodes().getSolo().getKills()), true);
		speedUHCEmbed.addField("Kill Streak : ", String.valueOf(speedUHC.getGamemodes().getSolo().getKillstreaks()), true);
		speedUHCEmbed.addField("Losses : ", String.valueOf(speedUHC.getGamemodes().getSolo().getLosses()), true);
		speedUHCEmbed.addField("Deaths : ", String.valueOf(speedUHC.getGamemodes().getSolo().getDeaths()), true);
		speedUHCEmbed.addField("Assists : ", String.valueOf(speedUHC.getGamemodes().getSolo().getAssists()), true);
		speedUHCEmbed.addField("Games : ", String.valueOf(speedUHC.getGamemodes().getSolo().getGames()), true);
		speedUHCEmbed.addField("KDR : ", String.valueOf(speedUHC.getGamemodes().getSolo().getKd()), true);
		speedUHCEmbed.addField("Wins Percentage : ", String.valueOf(speedUHC.getGamemodes().getSolo().getWinPercentage()), true);

		return speedUHCEmbed;
	}

	private EmbedBuilder getTeamsStats(Player player, SpeedUHC speedUHC) {
		EmbedBuilder speedUHCEmbed = new EmbedBuilder();
		speedUHCEmbed.setAuthor("SpeedUHC Teams Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		speedUHCEmbed.setColor(Color.YELLOW);
		speedUHCEmbed.setTitle(player.getUsername() + " Stats");
		speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		speedUHCEmbed.addField("Wins : ", String.valueOf(speedUHC.getGamemodes().getTeams().getWins()), true);
		speedUHCEmbed.addField("Wins Streak : ", String.valueOf(speedUHC.getGamemodes().getTeams().getWinstreak()), true);
		speedUHCEmbed.addField("Kills : ", String.valueOf(speedUHC.getGamemodes().getTeams().getKills()), true);
		speedUHCEmbed.addField("Kill Streak : ", String.valueOf(speedUHC.getGamemodes().getTeams().getKillstreaks()), true);
		speedUHCEmbed.addField("Losses : ", String.valueOf(speedUHC.getGamemodes().getTeams().getLosses()), true);
		speedUHCEmbed.addField("Deaths : ", String.valueOf(speedUHC.getGamemodes().getTeams().getDeaths()), true);
		speedUHCEmbed.addField("Assists : ", String.valueOf(speedUHC.getGamemodes().getTeams().getAssists()), true);
		speedUHCEmbed.addField("Games : ", String.valueOf(speedUHC.getGamemodes().getTeams().getGames()), true);
		speedUHCEmbed.addField("KDR : ", String.valueOf(speedUHC.getGamemodes().getTeams().getKd()), true);
		speedUHCEmbed.addField("Wins Percentage : ", String.valueOf(speedUHC.getGamemodes().getTeams().getWinPercentage()), true);

		return speedUHCEmbed;
	}

	private EmbedBuilder getActiveKit(Player player, SpeedUHC speedUHC) {
		EmbedBuilder speedUHCEmbed = new EmbedBuilder();
		speedUHCEmbed.setAuthor("SpeedUHC Active Kit", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		speedUHCEmbed.setColor(Color.YELLOW);
		speedUHCEmbed.setTitle(player.getUsername() + " Stats");
		speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		speedUHCEmbed.addField("Active Mastery : ", speedUHC.getMastery().getActiveMastery(), true);
		speedUHCEmbed.addField("Active Normal kit : ", speedUHC.getGamemodes().getNormal().getActiveKit(), true);
		speedUHCEmbed.addField("Active Insane Kit : ", speedUHC.getGamemodes().getInsane().getActiveKit(), true);

		return speedUHCEmbed;
	}
}
