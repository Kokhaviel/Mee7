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
import zone.nora.slothpixel.player.stats.blitz.kits.BlitzKits;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BlitzKitLevelCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "blitzstats") && args[1].equalsIgnoreCase("kitlevel")) {

			if(args.length == 2) {
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
			final Player player = sloth.getPlayer(args[2]);
			final BlitzKits blitzLevelsStats = player.getStats().getBlitz().getKitsLevels();

			channel.sendMessage(getBlitzKit1Level(player, blitzLevelsStats).build()).queue();
			channel.sendMessage(getBlitzKit2Level(player, blitzLevelsStats).build()).queue();
		}
	}

	private EmbedBuilder getBlitzKit1Level(Player player, BlitzKits blitzLevelsStats) {
		EmbedBuilder blitzLevelsEmbed = new EmbedBuilder();

		blitzLevelsEmbed.setAuthor("Blitz Kit Levels (1)", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		blitzLevelsEmbed.setColor(new Color(73, 240, 255));
		blitzLevelsEmbed.setTitle(player.getUsername() + " Stats");
		blitzLevelsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		blitzLevelsEmbed.addField("Arachnologist : ", String.valueOf(blitzLevelsStats.getArachnologist()), true);
		blitzLevelsEmbed.addField("Archer : ", String.valueOf(blitzLevelsStats.getArcher()), true);
		blitzLevelsEmbed.addField("Armorer : ", String.valueOf(blitzLevelsStats.getArmorer()), true);
		blitzLevelsEmbed.addField("Astronaut : ", String.valueOf(blitzLevelsStats.getAstronaut()), true);
		blitzLevelsEmbed.addField("Blaze : ", String.valueOf(blitzLevelsStats.getBlaze()), true);
		blitzLevelsEmbed.addField("Creeper Tamer : ", String.valueOf(blitzLevelsStats.getCreepertamer()), true);
		blitzLevelsEmbed.addField("Diver : ", String.valueOf(blitzLevelsStats.getDiver()), true);
		blitzLevelsEmbed.addField("Farmer : ", String.valueOf(blitzLevelsStats.getFarmer()), true);
		blitzLevelsEmbed.addField("Fisherman : ", String.valueOf(blitzLevelsStats.getFisherman()), true);
		blitzLevelsEmbed.addField("Florist : ", String.valueOf(blitzLevelsStats.getFlorist()), true);
		blitzLevelsEmbed.addField("Golem : ", String.valueOf(blitzLevelsStats.getGolem()), true);
		blitzLevelsEmbed.addField("Guardian : ", String.valueOf(blitzLevelsStats.getGuardian()), true);
		blitzLevelsEmbed.addField("Horse Tamer : ", String.valueOf(blitzLevelsStats.getHorsetamer()), true);
		blitzLevelsEmbed.addField("Hunter : ", String.valueOf(blitzLevelsStats.getHunter()), true);
		blitzLevelsEmbed.addField("Hype Train : ", String.valueOf(blitzLevelsStats.getHypeTrain()), true);
		blitzLevelsEmbed.addField("Jockey : ", String.valueOf(blitzLevelsStats.getJockey()), true);
		blitzLevelsEmbed.addField("Knight : ", String.valueOf(blitzLevelsStats.getKnight()), true);
		blitzLevelsEmbed.addField("Meat Master : ", String.valueOf(blitzLevelsStats.getMeatmaster()), true);
		blitzLevelsEmbed.addField("Necromancer : ", String.valueOf(blitzLevelsStats.getNecromancer()), true);
		blitzLevelsEmbed.addField("Paladin : ", String.valueOf(blitzLevelsStats.getPaladin()), true);
		blitzLevelsEmbed.addField("Pigman : ", String.valueOf(blitzLevelsStats.getPigman()), true);
		blitzLevelsEmbed.addField("Rambo : ", String.valueOf(blitzLevelsStats.getRambo()), true);
		blitzLevelsEmbed.addField("Random : ", String.valueOf(blitzLevelsStats.getRandom()), true);
		blitzLevelsEmbed.addField("Reaper : ", String.valueOf(blitzLevelsStats.getReaper()), true);

		return blitzLevelsEmbed;
	}


	private EmbedBuilder getBlitzKit2Level(Player player, BlitzKits blitzLevelsStats) {
		EmbedBuilder blitzLevelsEmbed = new EmbedBuilder();

		blitzLevelsEmbed.setAuthor("Blitz Kit Levels (2)", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		blitzLevelsEmbed.setColor(new Color(73, 240, 255));
		blitzLevelsEmbed.setTitle(player.getUsername() + " Stats");
		blitzLevelsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		blitzLevelsEmbed.addField("Red Dragon : ", String.valueOf(blitzLevelsStats.getReddragon()), true);
		blitzLevelsEmbed.addField("Rogue : ", String.valueOf(blitzLevelsStats.getRogue()), true);
		blitzLevelsEmbed.addField("Scout : ", String.valueOf(blitzLevelsStats.getScout()), true);
		blitzLevelsEmbed.addField("Shadow Knight : ", String.valueOf(blitzLevelsStats.getShadowKnight()), true);
		blitzLevelsEmbed.addField("Slimey Slime : ", String.valueOf(blitzLevelsStats.getSlimeyslime()), true);
		blitzLevelsEmbed.addField("Snowman : ", String.valueOf(blitzLevelsStats.getSnowman()), true);
		blitzLevelsEmbed.addField("Speleologist : ", String.valueOf(blitzLevelsStats.getSpeleologist()), true);
		blitzLevelsEmbed.addField("Tim : ", String.valueOf(blitzLevelsStats.getTim()), true);
		blitzLevelsEmbed.addField("Toxicologist : ", String.valueOf(blitzLevelsStats.getToxicologist()), true);
		blitzLevelsEmbed.addField("Troll : ", String.valueOf(blitzLevelsStats.getTroll()), true);
		blitzLevelsEmbed.addField("Viking : ", String.valueOf(blitzLevelsStats.getViking()), true);
		blitzLevelsEmbed.addField("Warlock : ", String.valueOf(blitzLevelsStats.getWarlock()), true);
		blitzLevelsEmbed.addField("Wolf Tamer : ", String.valueOf(blitzLevelsStats.getWolftamer()), true);

		return blitzLevelsEmbed;
	}
}
