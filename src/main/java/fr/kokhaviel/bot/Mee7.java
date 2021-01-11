package fr.kokhaviel.bot;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import fr.kokhaviel.bot.commands.fun.EightBallCommand;
import fr.kokhaviel.bot.commands.fun.RandomCommand;
import fr.kokhaviel.bot.commands.fun.SayCommand;
import fr.kokhaviel.bot.commands.moderation.BanCommand;
import fr.kokhaviel.bot.commands.moderation.ClearCommand;
import fr.kokhaviel.bot.commands.moderation.KickCommand;
import fr.kokhaviel.bot.commands.moderation.UnbanCommand;
import fr.kokhaviel.bot.commands.user.AvatarCommand;
import fr.kokhaviel.bot.commands.user.InfoCommand;
import fr.kokhaviel.bot.commands.user.afk.AfkCommand;
import fr.kokhaviel.bot.commands.user.afk.AfkVerify;
import fr.kokhaviel.bot.commands.util.AboutCommand;
import fr.kokhaviel.bot.commands.util.HelpCommand;
import fr.kokhaviel.bot.commands.util.PingCommand;
import fr.kokhaviel.bot.commands.util.RebootCommand;
import fr.kokhaviel.bot.commands.util.RepoCommand;
import fr.kokhaviel.bot.commands.util.ShutdownCommand;
import fr.kokhaviel.bot.event.logs.Logs;
import fr.kokhaviel.bot.event.moderation.AutoModerator;

public class Mee7 {
	
	public static List<String> afkIDs = new ArrayList<>();
	
	public Mee7() {
		
		try {
			@SuppressWarnings("unused")
			JDA jda = JDABuilder
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
					.addEventListeners(new Logs())
					.addEventListeners(new AutoModerator())
					.addEventListeners(new BanCommand(), new UnbanCommand(), new KickCommand(), new ClearCommand())
					.addEventListeners(new PingCommand(), new RebootCommand(), new RepoCommand(), new ShutdownCommand())
					.addEventListeners(new HelpCommand(), new AboutCommand())
					.addEventListeners(new AfkCommand(), new AfkVerify())
					.addEventListeners(new AvatarCommand(), new InfoCommand())
					.addEventListeners(new RandomCommand(), new SayCommand(), new EightBallCommand())
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