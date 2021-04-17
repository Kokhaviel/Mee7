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

package fr.kokhaviel.bot.commands.hypixel.server;

import com.google.gson.JsonObject;
import fr.kokhaviel.api.hypixel.server.count.PlayerCount;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.MalformedURLException;

import static fr.kokhaviel.bot.Mee7.hypixelAPI;

public class PlayerCountCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {

		String prefix = JsonUtilities.readJson(new File("guild_settings.json"))
				.getAsJsonObject().get(event.getGuild().getId())
				.getAsJsonObject().get("hypixel_prefix").getAsString();

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		final JsonObject HYPIXEL_OBJECT = LANG_OBJECT.get("hypixel").getAsJsonObject();


		final Message message = event.getMessage();
		final MessageChannel channel = event.getChannel();
		final String[] args = message.getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(prefix + "count")) {

			message.delete().queue();

			PlayerCount playerCount = null;
			try {
				playerCount = hypixelAPI.getPlayerCount();
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}

			if(args.length == 1) {
				assert playerCount != null;
				channel.sendMessage("There are " + playerCount.getPlayerCount() + " players actually connected to Hypixel Network !").queue();
			}

			if(args.length == 2) {
				assert playerCount != null;
				switch(args[1]) {
					case "lobby":
						channel.sendMessage("There are " + playerCount.getGamesCount().getMainLobby().getPlayers() + " players actually connected in Main Lobby !").queue();
						break;
					case "tournament":
						channel.sendMessage("There are " + playerCount.getGamesCount().getTournamentLobby().getPlayers() + " players actually connected in Tournament Lobby !").queue();
						break;
					case "murder":
						channel.sendMessage("There are " + playerCount.getGamesCount().getMurderMystery().getPlayers() + " players actually connected in Murder Mystery !").queue();
						break;
					case "classic":
						channel.sendMessage("There are " + playerCount.getGamesCount().getLegacy().getPlayers() + " players actually connected in Classic Games !").queue();
						break;
					case "hunger":
						channel.sendMessage("There are " + playerCount.getGamesCount().getSurvivalGames().getPlayers() + " players actually connected in Hunger Games !").queue();
						break;
					case "replay":
						channel.sendMessage("There are " + playerCount.getGamesCount().getReplay().getPlayers() + " players actually connected in Replay !").queue();
						break;
					case "megawalls":
						channel.sendMessage("There are " + playerCount.getGamesCount().getMegaWalls().getPlayers() + " players actually connected in Mega Walls !").queue();
						break;
					case "arcade":
						channel.sendMessage("There are " + playerCount.getGamesCount().getArcade().getPlayers() + " players actually connected in Arcade !").queue();
						break;
					case "build":
						channel.sendMessage("There are " + playerCount.getGamesCount().getBuildBattle().getPlayers() + " players actually connected in Build Battle !").queue();
						break;
					case "housing":
						channel.sendMessage("There are " + playerCount.getGamesCount().getHousing().getPlayers() + " players actually connected in Housing !").queue();
						break;
					case "prototype":
						channel.sendMessage("There are " + playerCount.getGamesCount().getPrototype().getPlayers() + " players actually connected !").queue();
						break;
					case "smash":
						channel.sendMessage("There are " + playerCount.getGamesCount().getSmash().getPlayers() + " players actually connected in Smash !").queue();
						break;
					case "skywars":
						channel.sendMessage("There are " + playerCount.getGamesCount().getSkywars().getPlayers() + " players actually connected in Skywars !").queue();
						break;
					case "skyblock":
						channel.sendMessage("There are " + playerCount.getGamesCount().getSkyblock().getPlayers() + " players actually connected in Skyblock !").queue();
						break;
					case "mcgo":
						channel.sendMessage("There are " + playerCount.getGamesCount().getMcgo().getPlayers() + " players actually connected in McGo !").queue();
						break;
					case "uhc":
						channel.sendMessage("There are " + playerCount.getGamesCount().getUhc().getPlayers() + " players actually connected in UHC !").queue();
						break;
					case "bedwars":
						channel.sendMessage("There are " + playerCount.getGamesCount().getBedwars().getPlayers() + " players actually connected in Bedwars !").queue();
						break;
					case "battleground":
						channel.sendMessage("There are " + playerCount.getGamesCount().getBattleground().getPlayers() + " players actually connected in BattleGround !").queue();
						break;
					case "pit":
						channel.sendMessage("There are " + playerCount.getGamesCount().getPit().getPlayers() + " players actually connected in Pit !").queue();
						break;
					case "speeduhc":
						channel.sendMessage("There are " + playerCount.getGamesCount().getSpeedUHC().getPlayers() + " players actually connected in SpeedUHC !").queue();
						break;
					case "tnt":
						channel.sendMessage("There are " + playerCount.getGamesCount().getTntGames().getPlayers() + " players actually connected in Tnt Games !").queue();
						break;
					case "duels":
						channel.sendMessage("There are " + playerCount.getGamesCount().getDuels().getPlayers() + " players actually connected in Duels !").queue();
						break;
					case "limbo":
						channel.sendMessage("There are " + playerCount.getGamesCount().getLimbo().getPlayers() + " players actually connected in Limbo !").queue();
						break;
					case "idle":
						channel.sendMessage("There are " + playerCount.getGamesCount().getIdle().getPlayers() + " players actually Idle !").queue();
						break;
					case "queue":
						channel.sendMessage("There are " + playerCount.getGamesCount().getQueue().getPlayers() + " players actually connected to Queue !").queue();
						break;
					default:
						channel.sendMessage("Invalid Game : Available Games are lobby, tournament, murder, classic, "
								+ "hunger, replay, megawalls, arcade, build, housing, prototype, smash, skywars, skyblock, mcgo, "
								+ "uhc, bedwars, battleground, pit, speeduhc, tnt, duels, limbo, idle and queue !").queue();
				}
			}
		}
	}
}
