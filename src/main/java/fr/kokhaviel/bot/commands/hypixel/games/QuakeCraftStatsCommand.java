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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.quake.Quake;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class QuakeCraftStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "quakecraft")) {

			if(args.length == 1) {
				channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "quakecraft <Player>").queue(
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
			final Quake quake = player.getStats().getQuake();
			channel.sendMessage(getQuakeStats(player, quake).build()).queue();
		}
	}

	private EmbedBuilder getQuakeStats(Player player, Quake quake) {
		EmbedBuilder quakecraftEmbed = new EmbedBuilder();
		quakecraftEmbed.setAuthor("QuakeCraft Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		quakecraftEmbed.setColor(Color.MAGENTA);
		quakecraftEmbed.setTitle(player.getUsername() + " Stats");
		quakecraftEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		quakecraftEmbed.addField("Coins : ", String.valueOf(quake.getCoins()), true);
		quakecraftEmbed.addField("Dash Cooldown : ", quake.getDashCooldown() + "s", true);
		quakecraftEmbed.addField("Dash Power : ", String.valueOf(quake.getDashPower()), true);

		quakecraftEmbed.addBlankField(false);
		quakecraftEmbed.addField("Solo Deaths : ", String.valueOf(quake.getGamemodes().getSolo().getDeaths()), true);
		quakecraftEmbed.addField("Solo Kills : ", String.valueOf(quake.getGamemodes().getSolo().getKills()), true);
		quakecraftEmbed.addField("Solo Wins : ", String.valueOf(quake.getGamemodes().getSolo().getWins()), true);
		quakecraftEmbed.addField("Solo Headshots : ", String.valueOf(quake.getGamemodes().getSolo().getHeadshots()), true);
		quakecraftEmbed.addField("KDR : ", String.valueOf(quake.getGamemodes().getSolo().getKd()), true);
		quakecraftEmbed.addField("Shots Fired : ", String.valueOf(quake.getGamemodes().getSolo().getShotsFired()), true);
		quakecraftEmbed.addField("Highest KillStreak : ", String.valueOf(quake.getHighestKillstreak()), true);

		quakecraftEmbed.addBlankField(false);
		quakecraftEmbed.addField("Teams Deaths : ", String.valueOf(quake.getGamemodes().getTeams().getDeaths()), true);
		quakecraftEmbed.addField("Teams Kills : ", String.valueOf(quake.getGamemodes().getTeams().getKills()), true);
		quakecraftEmbed.addField("Teams Wins : ", String.valueOf(quake.getGamemodes().getTeams().getWins()), true);
		quakecraftEmbed.addField("Teams Headshots : ", String.valueOf(quake.getGamemodes().getTeams().getHeadshots()), true);
		quakecraftEmbed.addField("KDR : ", String.valueOf(quake.getGamemodes().getTeams().getKd()), true);

		quakecraftEmbed.addBlankField(false);
		quakecraftEmbed.addField("Barrel : ", quake.getEquippedCosmetics().getBarrel(), true);
		quakecraftEmbed.addField("Beam : ", quake.getEquippedCosmetics().getBeam(), true);
		quakecraftEmbed.addField("Case : ", quake.getEquippedCosmetics().getCase(), true);
		quakecraftEmbed.addField("Kill Sound : ", quake.getEquippedCosmetics().getKillsound(), true);
		quakecraftEmbed.addField("Muzzle : ", quake.getEquippedCosmetics().getMuzzle(), true);
		quakecraftEmbed.addField("Sight : ", quake.getEquippedCosmetics().getSight(), true);
		quakecraftEmbed.addField("Trigger : ", quake.getEquippedCosmetics().getTrigger(), true);

		return quakecraftEmbed;
	}
}
