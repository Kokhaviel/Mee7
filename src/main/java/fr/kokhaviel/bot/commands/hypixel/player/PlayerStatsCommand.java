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

package fr.kokhaviel.bot.commands.hypixel.player;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class PlayerStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "player")) {

			if(args.length == 1) {
				channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "player <Player>").queue(
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
			channel.sendMessage(getPlayerStats(event, player).build()).queue();
			channel.sendMessage(getPlayer2Stats(event, player).build()).queue();
		}
	}

	private EmbedBuilder getPlayerStats(MessageReceivedEvent event, Player player) {
		EmbedBuilder playerEmbed = new EmbedBuilder();
		playerEmbed.setAuthor("Hypixel Player Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		playerEmbed.setColor(Color.PINK);
		playerEmbed.setTitle(player.getUsername() + " Stats");
		playerEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		playerEmbed.addField("UUID : ", player.getUuid(), true);
		playerEmbed.addField("Online : ", player.getOnline() ? "Yes" : "No", true);
		playerEmbed.addField("First Login : ", String.valueOf(player.getFirstLogin()), true);
		playerEmbed.addField("Last Login : ", String.valueOf(player.getLastLogin()), true);
		playerEmbed.addField("Last Game : ", player.getLastGame(), true);
		playerEmbed.addField("Minecraft Version : ", player.getMcVersion(), true);
		playerEmbed.addField("Language : ", player.getLanguage(), true);
		playerEmbed.addField("Rank : ", player.getRankFormatted(), true);
		playerEmbed.addField("Level : ", String.valueOf(player.getLevel()), true);
		playerEmbed.addField("Exp : ", String.valueOf(player.getExp()), true);
		playerEmbed.addField("Achievement Points : ", String.valueOf(player.getAchievementPoints()), true);
		playerEmbed.addField("Karma : ", String.valueOf(player.getKarma()), true);
		playerEmbed.addField("Quest Completed : ", String.valueOf(player.getQuestsCompleted()), true);
		playerEmbed.addField("Gift Sent : ", String.valueOf(player.getGiftsSent()), true);
		playerEmbed.addField("Gift Received : ", String.valueOf(player.getGiftsReceived()), true);
		playerEmbed.addField("Total Coins : ", String.valueOf(player.getTotalCoins()), true);
		playerEmbed.addField("Total Wins : ", String.valueOf(player.getTotalWins()), true);
		playerEmbed.addField("Total Kills : ", String.valueOf(player.getTotalKills()), true);

		return playerEmbed;
	}

	private EmbedBuilder getPlayer2Stats(MessageReceivedEvent event, Player player) {
		EmbedBuilder playerEmbed = new EmbedBuilder();
		playerEmbed.setAuthor("Hypixel Player Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		playerEmbed.setColor(Color.PINK);
		playerEmbed.setTitle(player.getUsername() + " Stats");
		playerEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		playerEmbed.addField("Reward Claimed : ", String.valueOf(player.getRewards().getClaimed()), true);
		playerEmbed.addField("Daily Reward Claimed : ", String.valueOf(player.getRewards().getClaimedDaily()), true);
		playerEmbed.addField("Reward Current Streak : ", String.valueOf(player.getRewards().getStreakCurrent()), true);
		playerEmbed.addField("Reward Best Streak : ", String.valueOf(player.getRewards().getStreakBest()), true);

		playerEmbed.addBlankField(false);
		if(player.getLinks().getTwitter() != null)
			playerEmbed.addField("Twitter : ", player.getLinks().getTwitter(), true);
		if(player.getLinks().getYoutube() != null)
			playerEmbed.addField("Youtube : ", player.getLinks().getYoutube(), true);
		if(player.getLinks().getInstagram() != null)
			playerEmbed.addField("Instagram : ", player.getLinks().getInstagram(), true);
		if(player.getLinks().getTwitch() != null)
			playerEmbed.addField("Twitch : ", player.getLinks().getTwitch(), true);
		if(player.getLinks().getMixer() != null)
			playerEmbed.addField("Mixer : ", player.getLinks().getMixer(), true);
		if(player.getLinks().getDiscord() != null)
			playerEmbed.addField("Discord : ", player.getLinks().getDiscord(), true);
		if(player.getLinks().getHypixel() != null)
			playerEmbed.addField("Hypixel Forums : ", player.getLinks().getHypixel(), true);

		return playerEmbed;
	}

}