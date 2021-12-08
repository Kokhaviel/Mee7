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

package fr.kokhaviel.bot;
//FIXME : HYPIXEL API & COMMANDS

import com.google.gson.Gson;
import fr.kokhaviel.api.hypixel.HypixelAPI;
import fr.kokhaviel.bot.commands.covid.CovidCommand;
import fr.kokhaviel.bot.commands.fun.*;
import fr.kokhaviel.bot.commands.giveaways.GiveawayCommand;
import fr.kokhaviel.bot.commands.guild.LanguageCommand;
import fr.kokhaviel.bot.commands.guild.PrefixCommand;
import fr.kokhaviel.bot.commands.hypixel.guild.GuildCommand;
import fr.kokhaviel.bot.commands.hypixel.player.MediasCommand;
import fr.kokhaviel.bot.commands.hypixel.player.PlayerCommand;
import fr.kokhaviel.bot.commands.hypixel.player.RecentGamesCommand;
import fr.kokhaviel.bot.commands.hypixel.player.StatusCommand;
import fr.kokhaviel.bot.commands.hypixel.server.BansCommand;
import fr.kokhaviel.bot.commands.hypixel.skyblock.SkyblockCommand;
import fr.kokhaviel.bot.commands.hypixel.stats.*;
import fr.kokhaviel.bot.commands.minecraft.AchievementCommand;
import fr.kokhaviel.bot.commands.minecraft.ServerStatsCommand;
import fr.kokhaviel.bot.commands.minecraft.SkinCommand;
import fr.kokhaviel.bot.commands.moderation.*;
import fr.kokhaviel.bot.commands.music.*;
import fr.kokhaviel.bot.commands.server.RoleInfoCommand;
import fr.kokhaviel.bot.commands.server.ServerInfoCommand;
import fr.kokhaviel.bot.commands.user.AvatarCommand;
import fr.kokhaviel.bot.commands.user.InfoCommand;
import fr.kokhaviel.bot.commands.user.afk.AfkCommand;
import fr.kokhaviel.bot.commands.util.*;
import fr.kokhaviel.bot.commands.wikipedia.WikipediaSearchCommand;
import fr.kokhaviel.bot.event.afk.AfkVerify;
import fr.kokhaviel.bot.event.logs.JoinEvent;
import fr.kokhaviel.bot.event.logs.Logs;
import fr.kokhaviel.bot.event.moderation.AutoModerator;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mee7 {

	private static final Set<Long> currentGiveaways = new HashSet<>();
	private static final Set<Long> currentAchievements = new HashSet<>();
	public static List<String> afkIDs = new ArrayList<>();
	public static final HypixelAPI HYPIXEL_API = new HypixelAPI(Config.HYPIXEL_API_KEY);
	public static final Gson GSON = new Gson();


	//Commands
	private final ListenerAdapter[] COVID = new ListenerAdapter[] {new CovidCommand()};
	private final ListenerAdapter[] FUN = new ListenerAdapter[] {new EightBallCommand(), new JokeCommand(),
			new LoveCommand(), new MemeCommand(), new RandomCommand(), new ReverseCommand(), new SayCommand()/*, new ThatPersonDoesNotExistCommand()*/};
	private final ListenerAdapter[] GIVEAWAYS = new ListenerAdapter[] {new GiveawayCommand()};
	private final ListenerAdapter[] GUILD = new ListenerAdapter[] {new LanguageCommand(), new PrefixCommand()};
	private final ListenerAdapter[] HYPIXEL = new ListenerAdapter[] {new PlayerCommand(), new MediasCommand(), new ArcadeCommand(),
			new BedwarsCommand(), new BuildBattleCommand(), new DuelsCommand(), new MCGOCommand(), new MurderMysteryCommand(), new PitCommand(),
			new SkywarsCommand(), new SpeedUHCCommand(), new UHCCommand(), new RecentGamesCommand(), new StatusCommand(), new GuildCommand(),
			new SkyblockCommand(), new BansCommand()};
	private final ListenerAdapter[] MINECRAFT = new ListenerAdapter[] {new AchievementCommand(), new ServerStatsCommand(), new SkinCommand()};
	private final ListenerAdapter[] MODERATION = new ListenerAdapter[] {new BanCommand(), new ClearCommand(), new KickCommand(),
			new MassBanCommand(), new MassKickCommand(), new MuteCommand(), new UnbanCommand(), new UnmuteCommand()};
	private final ListenerAdapter[] MUSIC = new ListenerAdapter[] {new BackwardCommand(), new ForwardCommand(), new JoinCommand(),
			new LeaveCommand(), new NowPlayingCommand(), new PauseCommand(), new PlayCommand(), new QueueCommand(),
			new RepeatCommand(), new SkipCommand(), new StopCommand(), new VolumeCommand()};
	private final ListenerAdapter[] SERVER = new ListenerAdapter[] {new RoleInfoCommand(), new ServerInfoCommand()};
	private final ListenerAdapter[] USER = new ListenerAdapter[] {new AfkCommand(), new AvatarCommand(), new InfoCommand()};
	private final ListenerAdapter[] UTIL = new ListenerAdapter[] {new AboutCommand(), new HelpCommand(), new PingCommand(),
			new RebootCommand(), new RepoCommand(), new ShutdownCommand()};
	private final ListenerAdapter[] WIKIPEDIA = new ListenerAdapter[] {new WikipediaSearchCommand()};


	//Events
	private final ListenerAdapter[] AFK = new ListenerAdapter[] {new AfkVerify()};
	private final ListenerAdapter[] LOGS = new ListenerAdapter[] {new Logs(), new JoinEvent()};
	private final ListenerAdapter[] MODERATOR = new ListenerAdapter[] {new AutoModerator()};
	private JDABuilder jdaBuilder;

	public static Set<Long> getCurrentGiveaways() {
		return currentGiveaways;
	}

	public static Set<Long> getCurrentAchievements() {
		return currentAchievements;
	}

	public static void main(String[] args) throws LoginException {
		Mee7 mee7 = new Mee7();
		mee7.setConfig().addAllCommandAndEvents().run();
	}

	public Mee7 setConfig() {
		jdaBuilder = JDABuilder
				.create(Config.BOT_TOKEN,
						GatewayIntent.GUILD_MEMBERS,
						GatewayIntent.GUILD_BANS,
						GatewayIntent.GUILD_EMOJIS,
						GatewayIntent.GUILD_INVITES,
						GatewayIntent.GUILD_PRESENCES,
						GatewayIntent.GUILD_MESSAGES,
						GatewayIntent.GUILD_MESSAGE_REACTIONS,
						GatewayIntent.GUILD_MESSAGE_TYPING,
						GatewayIntent.GUILD_VOICE_STATES,
						GatewayIntent.GUILD_WEBHOOKS)

				.enableCache(CacheFlag.VOICE_STATE)
				.setActivity(Activity.watching("documentation"));

		return this;
	}

	public Mee7 addCovidCommands() {
		jdaBuilder.addEventListeners((Object[]) COVID);
		return this;
	}

	public Mee7 addFunCommands() {
		jdaBuilder.addEventListeners((Object[]) FUN);
		return this;
	}

	public Mee7 addGiveawaysCommands() {
		jdaBuilder.addEventListeners((Object[]) GIVEAWAYS);
		return this;
	}

	public Mee7 addGuildCommands() {
		jdaBuilder.addEventListeners((Object[]) GUILD);
		return this;
	}

	public Mee7 addHypixelCommands() {
		jdaBuilder.addEventListeners((Object[]) HYPIXEL);
		return this;
	}

	public Mee7 addMinecraftCommands() {
		jdaBuilder.addEventListeners((Object[]) MINECRAFT);
		return this;
	}

	public Mee7 addModerationCommands() {
		jdaBuilder.addEventListeners((Object[]) MODERATION);

		return this;
	}

	public Mee7 addMusicCommands() {
		jdaBuilder.addEventListeners((Object[]) MUSIC);

		return this;
	}

	public Mee7 addServerCommands() {
		jdaBuilder.addEventListeners((Object[]) SERVER);

		return this;
	}

	public Mee7 addUserCommands() {
		jdaBuilder.addEventListeners((Object[]) USER);

		return this;
	}

	public Mee7 addUtilCommands() {
		jdaBuilder.addEventListeners((Object[]) UTIL);

		return this;
	}

	public Mee7 addWikipediaCommands() {
		jdaBuilder.addEventListeners((Object[]) WIKIPEDIA);

		return this;
	}

	public Mee7 addAfkEvent() {
		jdaBuilder.addEventListeners((Object[]) AFK);

		return this;
	}

	public Mee7 addLogs() {
		jdaBuilder.addEventListeners((Object[]) LOGS);

		return this;
	}

	public Mee7 addAutoModerator() {
		jdaBuilder.addEventListeners((Object[]) MODERATOR);

		return this;
	}

	public Mee7 addAllCommandAndEvents() {
		this.addCovidCommands().addFunCommands().addGiveawaysCommands()
				.addGuildCommands().addHypixelCommands()
				.addMinecraftCommands().addModerationCommands()
				.addMusicCommands().addServerCommands()
				.addUserCommands().addUtilCommands()
				.addWikipediaCommands().addAfkEvent()
				.addLogs().addAutoModerator();

		return this;
	}

	public void run() throws LoginException {
		jdaBuilder.build();
	}
}


