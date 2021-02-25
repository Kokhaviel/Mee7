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

package fr.kokhaviel.bot.commands.funcraft.games;

import com.google.gson.Gson;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class InfectedStatsCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.FUNCRAFT_PREFIX + "infected")) {

			if(args.length < 2) {
				message.delete().queue();

				channel.sendMessage("Missing Arguments : Please Specify A Player !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if(!args[1].matches("^\\w{3,16}$")) {
				channel.sendMessage("You must specify a valid Minecraft username !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			final String url = "https://lordmorgoth.net/APIs/stats?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1] + "&mode=infecte&periode=always";

			try {

				message.delete().queue();
				Gson gson = new Gson();
				Infected infected = gson.fromJson(JsonUtilities.readJson(new URL(url)), Infected.class);

				if(infected.exit_code.equals("0"))
					channel.sendMessage(getInfectedStats(infected, channel).build()).queue();

			} catch(IOException e) {
				channel.sendMessage("An exception occurred : File doesn't exist !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getInfectedStats(Infected infected, TextChannel channel) {

		EmbedBuilder infectedEmbed = new EmbedBuilder();

		if(infected.exit_code.equals("0")) {
			infectedEmbed.setAuthor("Funcraft Player Stats", null, "https://pbs.twimg.com/profile_images/1083667374379855872/kSsOCKM7_400x400.jpg");
			infectedEmbed.setColor(Color.RED);
			infectedEmbed.setThumbnail(infected.skin);
			infectedEmbed.setTitle(String.format("%s Infected Stats", infected.pseudo));
			infectedEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

			infectedEmbed.addField("Rank : ", infected.rang, false);

			infectedEmbed.addBlankField(false);
			infectedEmbed.addField("Points : ", infected.data.points, true);
			infectedEmbed.addField("Games : ", infected.data.parties, true);
			infectedEmbed.addField("Victories : ", infected.data.victoires, true);
			infectedEmbed.addField("Defeats : ", infected.data.defaites, true);
			infectedEmbed.addField("Played Time : ", infected.data.temps_jeu + " minutes", true);
			infectedEmbed.addField("Kills : ", infected.data.kills, true);
			infectedEmbed.addField("Deaths : ", infected.data.morts, true);

			infectedEmbed.addBlankField(false);
			infectedEmbed.addField("Winrate : ", infected.stats.winrate + "%", true);
			infectedEmbed.addField("KDR : ", infected.stats.kd, true);
			infectedEmbed.addField("Average Kills / Games : ", infected.stats.kills_game, true);
			infectedEmbed.addField("Average Deaths / Games : ", infected.stats.morts_game, true);
			infectedEmbed.addField("Average Time / Games : ", infected.stats.temps_partie + " s", true);
		}

		if(infected.exit_code.equals("0")) {
			channel.sendMessage(JsonUtilities.getErrorCode(infected.exit_code)).queue();
		}

		return infectedEmbed;
	}

	static class Data {
		String points;
		String parties;
		String victoires;
		String defaites;
		String temps_jeu;
		String kills;
		String morts;
	}

	static class Stats {
		String winrate;
		String kd;
		String kills_game;
		String morts_game;
		String temps_partie;
	}

	static class Infected {
		String exit_code;
		String pseudo;
		String mode_jeu;
		String rang;
		Data data;
		Stats stats;
		String skin;
	}
}
