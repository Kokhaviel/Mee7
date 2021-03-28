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
import zone.nora.slothpixel.player.stats.skywars.SkyWars;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class SkywarsStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(prefix + "skywars")) {

			if(args.length == 1) {
				channel.sendMessage("You need to specify a player : " + prefix + "skywars <Player>").queue(
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
			final SkyWars sw = player.getStats().getSkyWars();
			channel.sendMessage(getSwStats(player, sw).build()).queue();
		}
	}

	private EmbedBuilder getSwStats(Player player, SkyWars sw) {
		EmbedBuilder swEmbed = new EmbedBuilder();
		swEmbed.setAuthor("Skywars Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
		swEmbed.setColor(Color.GREEN);
		swEmbed.setTitle(player.getUsername() + " Stats");
		swEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

		swEmbed.addField("Coins : ", String.valueOf(sw.getCoins()), true);

		return swEmbed;
	}
}
