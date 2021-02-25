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

// Funcraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)

package fr.kokhaviel.bot.commands.funcraft;

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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerStatsCommand extends ListenerAdapter {


	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {


		final Message message = event.getMessage();
		final String[] args = message.getContentRaw().split("\\s+");
		final TextChannel channel = (TextChannel) event.getChannel();

		if(args[0].equalsIgnoreCase(Config.FUNCRAFT_PREFIX + "stats")) {

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

			final String url = "https://lordmorgoth.net/APIs/infos?key=" + Config.FUNCRAFT_API_KEY + "&joueur=" + args[1];

			try {
				message.delete().queue();
				Gson gson = new Gson();
				Stats stats = gson.fromJson(JsonUtilities.readJson(new URL(url)), Stats.class);
				channel.sendMessage(getFuncraftStats(stats).build()).queue();
				channel.sendMessage(getPlayerFriends(stats).build()).queue();

			} catch(IOException e) {
				channel.sendMessage("An exception occurred : File doesn't exist !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

				e.printStackTrace();
			}
		}
	}

	private EmbedBuilder getFuncraftStats(Stats stats) {

		EmbedBuilder funcraftEmbed = new EmbedBuilder();

		funcraftEmbed.setAuthor("Funcraft Player Stats", null, "https://pbs.twimg.com/profile_images/1083667374379855872/kSsOCKM7_400x400.jpg");
		funcraftEmbed.setColor(Color.RED);
		funcraftEmbed.setThumbnail(stats.skin);
		funcraftEmbed.setTitle(String.format("[%s] %s Stats", stats.grade, stats.pseudo));
		funcraftEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

		funcraftEmbed.addField("Inscription : ", stats.inscription, true);
		funcraftEmbed.addField("Last Connection : ", "Owo ... Not working for the moment", true);
		//FIXME : Last Connection
		funcraftEmbed.addField("Glories : ", String.valueOf(stats.gloires), true);
		funcraftEmbed.addField("Games : ", stats.parties, true);
		funcraftEmbed.addField("Points : ", stats.points, true);
		funcraftEmbed.addField("Victories : ", stats.victoires, true);
		funcraftEmbed.addField("Defeats : ", stats.defaites, true);
		funcraftEmbed.addField("Played Time : ", stats.temps_jeu + " minutes", true);
		funcraftEmbed.addField("Kills : ", stats.kills, true);
		funcraftEmbed.addField("Deaths : ", stats.morts, true);
		funcraftEmbed.addField("Ban : ", stats.ban, true);

		return funcraftEmbed;
	}

	private EmbedBuilder getPlayerFriends(Stats stats) {

		EmbedBuilder friendsEmbed = new EmbedBuilder();

		friendsEmbed.setAuthor("Funcraft Player Friends", null, "https://pbs.twimg.com/profile_images/1083667374379855872/kSsOCKM7_400x400.jpg");
		friendsEmbed.setColor(Color.RED);
		friendsEmbed.setThumbnail(stats.skin);
		friendsEmbed.setTitle(String.format("[%s] %s Friends", stats.grade, stats.pseudo));
		friendsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nFuncraft API by LordMorgoth (https://lordmorgoth.net/APIs/funcraft)", "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

		for(Friend friend : stats.amis) {
			friendsEmbed.addField(friend.nom, "Skin : [" + friend.nom + " Head](" + friend.skin + ")", false);
		}

		return friendsEmbed;
	}


	static class Friend {
		String nom;
		String skin;
	}

	static class Stats {
		String grade;
		String pseudo;
		String skin;
		String inscription;
		String gloires;
		String parties;
		String points;
		String victoires;
		String defaites;
		String temps_jeu;
		String kills;
		String morts;
		List<Friend> amis;
		String ban;
	}
}