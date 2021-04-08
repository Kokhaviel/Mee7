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

import com.google.gson.Gson;
import fr.kokhaviel.api.hypixel.HypixelAPI;
import fr.kokhaviel.bot.commands.covid.CovidCommand;
import fr.kokhaviel.bot.commands.fun.*;
import fr.kokhaviel.bot.commands.funcraft.games.*;
import fr.kokhaviel.bot.commands.giveaways.GiveawayCommands;
import fr.kokhaviel.bot.commands.guild.LanguageCommand;
import fr.kokhaviel.bot.commands.guild.PrefixCommand;
import fr.kokhaviel.bot.commands.hypixel.games.*;
import fr.kokhaviel.bot.commands.hypixel.player.MediasCommand;
import fr.kokhaviel.bot.commands.hypixel.player.OutfitCommand;
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
import fr.kokhaviel.bot.event.afk.AfkVerify;
import fr.kokhaviel.bot.event.logs.Logs;
import fr.kokhaviel.bot.event.moderation.AutoModerator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mee7 {

    public static List<String> afkIDs = new ArrayList<>();

    public static JDA jda;

    public static HypixelAPI hypixelAPI = new HypixelAPI();


    //TODO : Don't Forget Hypixel Quests

	public static Gson gson = new Gson();

	private static final Set<Long> currentGiveaways = new HashSet<>();

    private static final Set<Long> currentAchievements = new HashSet<>();

    public Mee7() {

        try {

            jda = JDABuilder
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

                    .addEventListeners(new AboutCommand())
                    .addEventListeners(new AchievementCommand())
                    .addEventListeners(new AfkCommand())
                    .addEventListeners(new AfkVerify())
                    .addEventListeners(new ArcadeCommand())
                    .addEventListeners(new ArenaCommand())
                    .addEventListeners(new AutoModerator())
                    .addEventListeners(new AvatarCommand())
                    .addEventListeners(new BackwardCommand())
                    .addEventListeners(new BanCommand())
                    .addEventListeners(new BedwarsCommand())
                    .addEventListeners(new BlitzCommand())
                    .addEventListeners(new BuildBattleCommand())
                    .addEventListeners(new ClassicGamesCommand())
                    .addEventListeners(new ClearCommand())
                    .addEventListeners(new CovidCommand())
                    .addEventListeners(new GiveawayCommands())
                    .addEventListeners(new DameDaneCommand())
                    .addEventListeners(new DuelsCommand())
                    .addEventListeners(new EightBallCommand())
                    .addEventListeners(new ForwardCommand())
                    .addEventListeners(new HelpCommand())
                    .addEventListeners(new HikabrainStatsCommand())
                    .addEventListeners(new InfectedStatsCommand())
                    .addEventListeners(new InfoCommand())
                    .addEventListeners(new JoinCommand())
                    .addEventListeners(new JokeCommand())
                    .addEventListeners(new KickCommand())
                    .addEventListeners(new LanguageCommand())
                    .addEventListeners(new LeaveCommand())
                    .addEventListeners(new Logs())
                    .addEventListeners(new LoveCommand())
                    .addEventListeners(new MassBanCommand())
                    .addEventListeners(new MassKickCommand())
					.addEventListeners(new MediasCommand())
                    .addEventListeners(new MemeCommand())
                    .addEventListeners(new MuteCommand())
                    .addEventListeners(new NowPlayingCommand())
                    .addEventListeners(new OctogoneStatsCommand())
                    .addEventListeners(new OutfitCommand())
                    .addEventListeners(new PauseCommand())
                    .addEventListeners(new PingCommand())
                    .addEventListeners(new PlayCommand())
                    .addEventListeners(new fr.kokhaviel.bot.commands.funcraft.PlayerStatsCommand())
                    .addEventListeners(new fr.kokhaviel.bot.commands.hypixel.player.PlayerStatsCommand())
                    .addEventListeners(new PrefixCommand())
                    .addEventListeners(new PvpSmashStatsCommand())
                    .addEventListeners(new QueueCommand())
                    .addEventListeners(new RandomCommand())
                    .addEventListeners(new RebootCommand())
                    .addEventListeners(new RepeatCommand())
                    .addEventListeners(new RepoCommand())
                    .addEventListeners(new ReverseCommand())
                    .addEventListeners(new RoleInfoCommand())
                    .addEventListeners(new RushStatsCommand())
                    .addEventListeners(new SayCommand())
                    .addEventListeners(new ServerInfoCommand())
                    .addEventListeners(new ServerStatsCommand())
                    .addEventListeners(new Settings())
                    .addEventListeners(new ShootcraftStatsCommand())
                    .addEventListeners(new ShutdownCommand())
                    .addEventListeners(new SkinCommand())
                    .addEventListeners(new SkipCommand())
                    .addEventListeners(new fr.kokhaviel.bot.commands.funcraft.games.SkywarsStatsCommand())
                    .addEventListeners(new StopCommand())
                    .addEventListeners(new SurvivalStatsCommand())
                    .addEventListeners(new TurboKartRacerCommand())
                    .addEventListeners(new UnbanCommand())
                    .addEventListeners(new UnmuteCommand())
                    .addEventListeners(new VolumeCommand())
                    .addEventListeners(new WarlordCommand())

                    //.addEventListeners(new ThatPersonDoesNotExistCommand())
                    .build();

        } catch (LoginException le) {
            le.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new Mee7();
    }

    public static Set<Long> getCurrentGiveaways() {
        return currentGiveaways;
    }

    public static Set<Long> getCurrentAchievements() {
        return currentAchievements;
    }
}


