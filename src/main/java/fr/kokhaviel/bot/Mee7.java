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

import fr.kokhaviel.bot.commands.fun.*;
import fr.kokhaviel.bot.commands.hypixel.games.*;
import fr.kokhaviel.bot.commands.hypixel.games.blitz.BlitzKitLevelCommand;
import fr.kokhaviel.bot.commands.hypixel.games.blitz.BlitzKitStatsCommand;
import fr.kokhaviel.bot.commands.hypixel.games.blitz.BlitzStatsCommand;
import fr.kokhaviel.bot.commands.hypixel.player.PlayerStatsCommand;
import fr.kokhaviel.bot.commands.hypixel.server.BanStatsCommand;
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
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import zone.nora.slothpixel.Slothpixel;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class Mee7 {

    public static List<String> afkIDs = new ArrayList<>();

    public static Slothpixel sloth = new Slothpixel();

    public static JDA jda;

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
                    .addEventListeners(new AfkCommand())
                    .addEventListeners(new AfkVerify())
                    .addEventListeners(new ArcadeStatsCommand())
                    .addEventListeners(new ArenaStatsCommand())
                    .addEventListeners(new AutoModerator())
                    .addEventListeners(new AvatarCommand())
                    .addEventListeners(new BackwardCommand())
                    .addEventListeners(new BanCommand())
                    .addEventListeners(new BanStatsCommand())
                    .addEventListeners(new BedwarsStatsCommand())
                    .addEventListeners(new BlitzKitLevelCommand())
                    .addEventListeners(new BlitzKitStatsCommand())
                    .addEventListeners(new BlitzStatsCommand())
                    .addEventListeners(new BuildBattleStatsCommand())
                    .addEventListeners(new ClearCommand())
                    .addEventListeners(new CopsAndCrimsStatsCommand())
                    .addEventListeners(new CrazyWallsStatsCommand())
                    .addEventListeners(new DameDaneCommand())
                    .addEventListeners(new DuelsStatsCommand())
                    .addEventListeners(new EightBallCommand())
                    .addEventListeners(new ForwardCommand())
                    .addEventListeners(new HelpCommand())
                    .addEventListeners(new InfoCommand())
                    .addEventListeners(new JoinCommand())
                    .addEventListeners(new KickCommand())
                    .addEventListeners(new LeaveCommand())
                    .addEventListeners(new Logs())
                    .addEventListeners(new MassBanCommand())
                    .addEventListeners(new MassKickCommand())
                    .addEventListeners(new MegaWallsStatsCommand())
                    .addEventListeners(new MurderMysteryStatsCommand())
                    .addEventListeners(new MuteCommand())
                    .addEventListeners(new NowPlayingCommand())
                    .addEventListeners(new PaintballStatsCommand())
                    .addEventListeners(new PauseCommand())
                    .addEventListeners(new PingCommand())
                    .addEventListeners(new PitStatsCommand())
                    .addEventListeners(new PlayCommand())
                    .addEventListeners(new PlayerStatsCommand())
                    .addEventListeners(new QuakeCraftStatsCommand())
                    .addEventListeners(new QueueCommand())
                    .addEventListeners(new RandomCommand())
                    .addEventListeners(new RebootCommand())
                    .addEventListeners(new RepeatCommand())
                    .addEventListeners(new RepoCommand())
                    .addEventListeners(new ReverseCommand())
                    .addEventListeners(new RoleInfoCommand())
                    .addEventListeners(new SayCommand())
                    .addEventListeners(new ServerInfoCommand())
                    .addEventListeners(new ShutdownCommand())
                    .addEventListeners(new SkipCommand())
                    .addEventListeners(new SkyClashStatsCommand())
                    .addEventListeners(new SkywarsStatsCommand())
                    .addEventListeners(new SmashStatsCommand())
                    .addEventListeners(new SpeedUHCStatsCommand())
                    .addEventListeners(new StopCommand())
//                  .addEventListeners(new ThatPersonDoesNotExistCommand())
                    .addEventListeners(new TntGamesStatsCommand())
                    .addEventListeners(new TurboKartStatsCommand())
                    .addEventListeners(new UHCStatsCommand())
                    .addEventListeners(new UnbanCommand())
                    .addEventListeners(new UnmuteCommand())
                    .addEventListeners(new VampireZStatsCommand())
                    .addEventListeners(new VolumeCommand())
                    .addEventListeners(new WallsStatsCommand())
                    .addEventListeners(new WarlordsStatsCommand())

                    .setActivity(Activity.watching("la doc avant de poser une question..."))

                    .build();

        } catch (LoginException le) {
            le.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new Mee7();
    }
}
