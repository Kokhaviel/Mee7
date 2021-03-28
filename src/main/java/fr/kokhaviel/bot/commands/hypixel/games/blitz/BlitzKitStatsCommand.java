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
import zone.nora.slothpixel.player.stats.blitz.kits.BlitzKitsStats;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BlitzKitStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "blitzstats") && args[1].equalsIgnoreCase("kitstats")) {

			if(args.length == 2) {
				channel.sendMessage("You need to specify a player : " + prefix + "blitz kitstats <Player>").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}
			if(!args[2].matches("^\\w{3,16}$")) {
				channel.sendMessage("You must specify a valid Minecraft username !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			message.delete().queue();
			final Player player = sloth.getPlayer(args[2]);
			final BlitzKitsStats blitzKitStats = player.getStats().getBlitz().getKitsStats();

			channel.sendMessage(getArachnologistStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getArcherStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getArmorerStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getAstronautStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getBlazeStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getCreeperTamerStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getDiverStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getFarmerStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getFishermanStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getFloristStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getGolemStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getGuardianStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getHorseTamerStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getHunterStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getHypeTrainStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getJockeyStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getKnightStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getMeatMasterStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getNecromancerStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getPaladinStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getPigmanStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getRamboStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getRandomStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getReaperStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getRedDragonStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getRogueStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getScoutStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getShadowKnightStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getSmileySlimeStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getSnowmanStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getSpeleologistStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getTimStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getToxicologistStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getTrollStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getVikingStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getWarlockStats(player, blitzKitStats).build()).queue();
			channel.sendMessage(getWolfTamerStats(player, blitzKitStats).build()).queue();
		}
	}

	private EmbedBuilder getArachnologistStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Arachnologist Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getArachnologist()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getArachnologist() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getArachnologist()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getArachnologist() + stats.getWins().getTeams().getArachnologist()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getArachnologist()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getArachnologist()), true);

		return embed;
	}

	private EmbedBuilder getArcherStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Archer Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getArcher()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getArcher() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getArcher()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getArcher() + stats.getWins().getTeams().getArcher()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getArcher()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getArcher()), true);

		return embed;
	}

	private EmbedBuilder getArmorerStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Armorer Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getArmorer()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getArmorer() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getArmorer()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getArmorer() + stats.getWins().getTeams().getArmorer()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getArmorer()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getArmorer()), true);

		return embed;
	}

	private EmbedBuilder getAstronautStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Astronaut Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getAstronaut()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getAstronaut() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getAstronaut()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getAstronaut() + stats.getWins().getTeams().getAstronaut()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getAstronaut()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getAstronaut()), true);

		return embed;
	}

	private EmbedBuilder getBlazeStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Blaze Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getBlaze()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getBlaze() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getBlaze()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getBlaze() + stats.getWins().getTeams().getBlaze()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getBlaze()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getBlaze()), true);

		return embed;
	}

	private EmbedBuilder getCreeperTamerStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("CreeperTamer Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getCreepertamer()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getCreepertamer() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getCreepertamer()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getCreepertamer() + stats.getWins().getTeams().getBlaze()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getCreepertamer()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getCreepertamer()), true);

		return embed;
	}

	private EmbedBuilder getDiverStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Diver Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getDiver()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getDiver() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getDiver()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getDiver() + stats.getWins().getTeams().getDiver()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getDiver()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getDiver()), true);

		return embed;
	}

	private EmbedBuilder getFarmerStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Farmer Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getFarmer()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getFarmer() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getFarmer()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getFarmer() + stats.getWins().getTeams().getFarmer()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getFarmer()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getFarmer()), true);

		return embed;
	}

	private EmbedBuilder getFishermanStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Fisherman Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getFisherman()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getFisherman() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getFisherman()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getFisherman() + stats.getWins().getTeams().getFisherman()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getFisherman()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getFisherman()), true);

		return embed;
	}

	private EmbedBuilder getFloristStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Florist Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getFlorist()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getFlorist() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getFlorist()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getFlorist() + stats.getWins().getTeams().getFlorist()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getFlorist()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getFlorist()), true);

		return embed;
	}

	private EmbedBuilder getGolemStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Golem Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getGolem()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getGolem() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getGolem()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getGolem() + stats.getWins().getTeams().getGolem()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getGolem()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getGolem()), true);

		return embed;
	}

	private EmbedBuilder getGuardianStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Guardian Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getGuardian()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getGuardian() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getGuardian()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getFisherman() + stats.getWins().getTeams().getGuardian()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getGuardian()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getGuardian()), true);

		return embed;
	}

	private EmbedBuilder getHorseTamerStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("HorseTamer Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getHorsetamer()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getHorsetamer() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getHorsetamer()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getHorsetamer() + stats.getWins().getTeams().getHorsetamer()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getHorsetamer()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getHorsetamer()), true);

		return embed;
	}

	private EmbedBuilder getHunterStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Hunter Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getHunter()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getHunter() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getHunter()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getHunter() + stats.getWins().getTeams().getHunter()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getHunter()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getHunter()), true);

		return embed;
	}

	private EmbedBuilder getHypeTrainStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Hype Train Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getHypeTrain()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getHypeTrain() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getHypeTrain()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getHypeTrain() + stats.getWins().getTeams().getHypeTrain()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getHypeTrain()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getHypeTrain()), true);

		return embed;
	}

	private EmbedBuilder getJockeyStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Jockey Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getJockey()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getJockey() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getJockey()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getJockey() + stats.getWins().getTeams().getJockey()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getJockey()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getJockey()), true);

		return embed;
	}

	private EmbedBuilder getKnightStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Knight Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getKnight()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getKnight() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getKnight()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getKnight() + stats.getWins().getTeams().getKnight()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getKnight()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getKnight()), true);

		return embed;
	}

	private EmbedBuilder getMeatMasterStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Meat Master Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getMeatmaster()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getMeatmaster() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getMeatmaster()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getMeatmaster() + stats.getWins().getTeams().getMeatmaster()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getMeatmaster()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getMeatmaster()), true);

		return embed;
	}

	private EmbedBuilder getNecromancerStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Necromancer Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getNecromancer()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getNecromancer() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getNecromancer()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getNecromancer() + stats.getWins().getTeams().getNecromancer()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getNecromancer()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getNecromancer()), true);

		return embed;
	}

	private EmbedBuilder getPaladinStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Paladin Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getPaladin()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getPaladin() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getPaladin()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getPaladin() + stats.getWins().getTeams().getPaladin()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getPaladin()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getPaladin()), true);

		return embed;
	}

	private EmbedBuilder getPigmanStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Pigman Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getPigman()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getPigman() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getPigman()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getPigman() + stats.getWins().getTeams().getPigman()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getPigman()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getPigman()), true);

		return embed;
	}

	private EmbedBuilder getRamboStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Rambo Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getRambo()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getRambo() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getRambo()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getRambo() + stats.getWins().getTeams().getRambo()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getRambo()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getRambo()), true);

		return embed;
	}

	private EmbedBuilder getRandomStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Random Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getRandom()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getRandom() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getRandom()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getRandom() + stats.getWins().getTeams().getRandom()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getRandom()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getRandom()), true);

		return embed;
	}

	private EmbedBuilder getReaperStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Reaper Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getReaper()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getReaper() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getReaper()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getReaper() + stats.getWins().getTeams().getReaper()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getReaper()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getReaper()), true);

		return embed;
	}

	private EmbedBuilder getRedDragonStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("RedDragon Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getReddragon()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getReddragon() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getReddragon()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getReddragon() + stats.getWins().getTeams().getReddragon()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getReddragon()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getReddragon()), true);

		return embed;
	}

	private EmbedBuilder getRogueStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Rogue Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getRogue()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getRogue() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getRogue()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getRogue() + stats.getWins().getTeams().getRogue()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getRogue()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getRogue()), true);

		return embed;
	}

	private EmbedBuilder getScoutStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Scout Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getScout()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getScout() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getScout()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getScout() + stats.getWins().getTeams().getScout()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getScout()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getScout()), true);

		return embed;
	}

	private EmbedBuilder getShadowKnightStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("ShadowKnight Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getShadowKnight()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getShadowKnight() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getShadowKnight()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getShadowKnight() + stats.getWins().getTeams().getShadowKnight()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getShadowKnight()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getShadowKnight()), true);

		return embed;
	}

	private EmbedBuilder getSmileySlimeStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("SmileySlime Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getSlimeyslime()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getSlimeyslime() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getSlimeyslime()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getSlimeyslime() + stats.getWins().getTeams().getSlimeyslime()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getSlimeyslime()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getSlimeyslime()), true);

		return embed;
	}

	private EmbedBuilder getSnowmanStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Snowman Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getSnowman()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getSnowman() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getSnowman()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getSnowman() + stats.getWins().getTeams().getSnowman()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getSnowman()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getSnowman()), true);

		return embed;
	}

	private EmbedBuilder getSpeleologistStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Speleologist Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getSpeleologist()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getSpeleologist() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getSpeleologist()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getSpeleologist() + stats.getWins().getTeams().getSpeleologist()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getSpeleologist()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getSpeleologist()), true);

		return embed;
	}

	private EmbedBuilder getTimStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Tim Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getTim()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getTim() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getTim()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getTim() + stats.getWins().getTeams().getTim()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getTim()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getTim()), true);

		return embed;
	}

	private EmbedBuilder getToxicologistStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Toxicologist Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getToxicologist()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getToxicologist() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getToxicologist()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getToxicologist() + stats.getWins().getTeams().getToxicologist()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getToxicologist()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getToxicologist()), true);

		return embed;
	}

	private EmbedBuilder getTrollStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Troll Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getTroll()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getTroll() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getTroll()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getTroll() + stats.getWins().getTeams().getTroll()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getTroll()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getTroll()), true);

		return embed;
	}

	private EmbedBuilder getVikingStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Viking Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getViking()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getViking() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getViking()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getViking() + stats.getWins().getTeams().getViking()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getViking()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getViking()), true);

		return embed;
	}

	private EmbedBuilder getWarlockStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("Warlock Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getWarlock()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getWarlock() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getWarlock()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getWarlock() + stats.getWins().getTeams().getWarlock()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getWarlock()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getWarlock()), true);

		return embed;
	}

	private EmbedBuilder getWolfTamerStats(Player player, BlitzKitsStats stats) {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setAuthor("WolfTamer Kit Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		embed.setColor(new Color(73, 240, 255));
		embed.setTitle(player.getUsername() + " Stats");
		embed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		embed.addField("Games Played : ", String.valueOf(stats.getGamesPlayed().getWolftamer()), true);
		embed.addField("Time Played : ", String.valueOf(stats.getTimePlayed().getWolftamer() / 60), true);
		embed.addField("Kill : ", String.valueOf(stats.getKills().getWolftamer()), true);
		embed.addField("Wins : ", String.valueOf(stats.getWins().getSolo().getWolftamer() + stats.getWins().getTeams().getWolftamer()), true);
		embed.addField("Damage Taken : ", String.valueOf(stats.getDamageTaken().getWolftamer()), true);
		embed.addField("Blocks Travelled : ", String.valueOf(stats.getMisc().getBlocksTraveled().getWolftamer()), true);

		return embed;
	}
}
