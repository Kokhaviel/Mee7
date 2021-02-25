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
import zone.nora.slothpixel.player.stats.paintball.Paintball;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class PaintballStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "paintball")) {

			if(args.length == 1) {
				channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "paintball <Player>").queue(
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
			final Paintball paintball = player.getStats().getPaintball();
			channel.sendMessage(getPaintballStats(player, paintball).build()).queue();
			channel.sendMessage(getPaintballPerksStats(player, paintball).build()).queue();
		}
	}

	private EmbedBuilder getPaintballStats(Player player, Paintball paintball) {
		EmbedBuilder paintballEmbed = new EmbedBuilder();

		paintballEmbed.setAuthor("Paintball Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		paintballEmbed.setColor(new Color(85, 85, 255));
		paintballEmbed.setTitle(player.getUsername() + " Stats");
		paintballEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		paintballEmbed.addField("Coins : ", String.valueOf(paintball.getCoins()), true);
		paintballEmbed.addField("Shots Fired : ", String.valueOf(paintball.getShotsFired()), true);

		paintballEmbed.addBlankField(false);
		paintballEmbed.addField("Kills : ", String.valueOf(paintball.getKills()), true);
		paintballEmbed.addField("Deaths : ", String.valueOf(paintball.getDeaths()), true);
		paintballEmbed.addField("KDR : ", String.valueOf(paintball.getKd()), true);
		paintballEmbed.addField("Wins : ", String.valueOf(paintball.getWins()), true);
		paintballEmbed.addField("KillStreak : ", String.valueOf(paintball.getKillstreaks()), true);

		return paintballEmbed;
	}


	private EmbedBuilder getPaintballPerksStats(Player player, Paintball paintball) {
		EmbedBuilder paintballPerksEmbed = new EmbedBuilder();

		paintballPerksEmbed.setAuthor("Paintball Perks", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		paintballPerksEmbed.setColor(new Color(85, 85, 255));
		paintballPerksEmbed.setTitle(player.getUsername() + " Stats");
		paintballPerksEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		paintballPerksEmbed.addField("Adrenaline Perk : ", String.valueOf(paintball.getPerks().getAdrenaline()), true);
		paintballPerksEmbed.addField("Endurance perk : ", String.valueOf(paintball.getPerks().getEndurance()), true);
		paintballPerksEmbed.addField("Fortune Perk : ", String.valueOf(paintball.getPerks().getFortune()), true);
		paintballPerksEmbed.addField("God Father Perk : ", String.valueOf(paintball.getPerks().getGodfather()), true);
		paintballPerksEmbed.addField("Super Luck perk : ", String.valueOf(paintball.getPerks().getSuperluck()), true);
		paintballPerksEmbed.addField("Transfusion perk : ", String.valueOf(paintball.getPerks().getTransfusion()), true);

		return paintballPerksEmbed;
	}
}
