package fr.kokhaviel.bot;

import fr.kokhaviel.bot.commands.fun.*;
import fr.kokhaviel.bot.commands.hypixel.games.*;
import fr.kokhaviel.bot.commands.hypixel.games.blitz.BlitzKitLevelCommand;
import fr.kokhaviel.bot.commands.hypixel.games.blitz.BlitzKitStatsCommand;
import fr.kokhaviel.bot.commands.hypixel.games.blitz.BlitzStatsCommand;
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
                    .addEventListeners(new BanCommand())
                    .addEventListeners(new BedwarsStatsCommand())
                    .addEventListeners(new BlitzKitLevelCommand())
                    .addEventListeners(new BlitzKitStatsCommand())
                    .addEventListeners(new BlitzStatsCommand())
                    .addEventListeners(new ClearCommand())
                    .addEventListeners(new CopsAndCrimsStatsCommand())
                    .addEventListeners(new CrazyWallsStatsCommand())
                    .addEventListeners(new DameDaneCommand())
                    .addEventListeners(new EightBallCommand())
                    .addEventListeners(new HelpCommand())
                    .addEventListeners(new InfoCommand())
                    .addEventListeners(new JoinCommand())
                    .addEventListeners(new KickCommand())
                    .addEventListeners(new LeaveCommand())
                    .addEventListeners(new Logs())
                    .addEventListeners(new MassKickCommand())
                    .addEventListeners(new MassBanCommand())
                    .addEventListeners(new MegaWallsStatsCommand())
                    .addEventListeners(new MuteCommand())
                    .addEventListeners(new NowPlayingCommand())
                    .addEventListeners(new PaintballStatsCommand())
                    .addEventListeners(new PauseCommand())
                    .addEventListeners(new PingCommand())
                    .addEventListeners(new PlayCommand())
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
                    .addEventListeners(new UnbanCommand())
                    .addEventListeners(new UHCStatsCommand())
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
