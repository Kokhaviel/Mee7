package fr.kokhaviel.bot.commands.util;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

public class HelpCommand extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
	
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "help")) {
			
			User user = event.getMember().getUser();
			
			if(args.length == 1) {
				
				EmbedBuilder helpEmbed = new EmbedBuilder();
				
				helpEmbed.setTitle("Help Menu");
				helpEmbed.setColor(Color.CYAN);
				helpEmbed.setAuthor("Help", null, event.getJDA().getSelfUser().getAvatarUrl());
				helpEmbed.setDescription("Display all help commands");
				
				helpEmbed.addField("Util Commands : ", Config.PREFIX + "help util", false);
				helpEmbed.addField("User Commands : ", Config.PREFIX + "help user", false);
				helpEmbed.addField("Moderation Commands : ", Config.PREFIX + "help moderation", false);
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", an help message will be send to your DM !").queue();
								
				if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
				
				((UserImpl) user).getPrivateChannel().sendMessage(helpEmbed.build()).queue();	
				
				
			}
				
				
				
				if(args.length == 2) {

					if(args[1].equalsIgnoreCase("server")) {

						EmbedBuilder serverEmbed = new EmbedBuilder();

						serverEmbed.setTitle("Help Server Commands")
								.setColor(Color.RED)
								.setAuthor("Help", null, event.getJDA().getSelfUser().getAvatarUrl())
								.setDescription("Display all server commands")

							.addField("Role Info Command : ", Config.PREFIX + "roleinfo <@Role>", false)
							.addField("Server Info Command : ", Config.PREFIX + "serverinfo",false);

						event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

						event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", an help message will be send to your DM !").queue();

						if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();

						((UserImpl) user).getPrivateChannel().sendMessage(serverEmbed.build()).queue();

					}
					
					if(args[1].equalsIgnoreCase("util")) {
						
						EmbedBuilder utilEmbed = new EmbedBuilder();
						
						utilEmbed.setTitle("Help Util Commands")
								.setColor(Color.GREEN)
								.setAuthor("Help", null, event.getJDA().getSelfUser().getAvatarUrl())
								.setDescription("Display all util commands")
								
							.addField("Help Command : ", Config.PREFIX + "help <category>", false)
							.addField("About Command : ", Config.PREFIX + "about", false)
							.addField("Ping Command : ", Config.PREFIX + "ping", false)
							.addField("Reboot Command : ", Config.PREFIX + "reboot (Owner Only)", false)
							.addField("Repo Command : ", Config.PREFIX + "repo", false)
							.addField("Shutdown Command : ", Config.PREFIX + "shutdown (Owner Only)", false);
						
						event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

						event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", an help message will be send to your DM !").queue();

						if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
						
						((UserImpl) user).getPrivateChannel().sendMessage(utilEmbed.build()).queue();	
						
					}
					
					if(args[1].equalsIgnoreCase("fun")) {
						
						EmbedBuilder funEmbed = new EmbedBuilder();
						
						funEmbed.setTitle("Help Fun Commands")
								.setColor(Color.BLUE)
								.setAuthor("Help", null, event.getJDA().getSelfUser().getAvatarUrl())
								.setDescription("Display all fun commands")
						
							.addField("Random Command : ", Config.PREFIX + "random <Number1> <Number2>", false)
							.addField("Say Command : ", Config.PREFIX + "say <Something To Say>", false)
							.addField("8Ball Command : ", Config.PREFIX + "8ball <Question?>", false);
							
						event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

						event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", an help message will be send to your DM !").queue();

						if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
						
						((UserImpl) user).getPrivateChannel().sendMessage(funEmbed.build()).queue();	
						
					}
					
					if(args[1].equalsIgnoreCase("user")) {
						
						EmbedBuilder userEmbed = new EmbedBuilder();
						
						userEmbed.setTitle("Help User Commands")
								.setColor(Color.MAGENTA)
								.setAuthor("Help", null, event.getJDA().getSelfUser().getAvatarUrl())
								.setDescription("Display all user commands")
						
							.addField("Avatar Command : ", Config.PREFIX + "avatar <User ID>", false)
							.addField("User Info Command : ", Config.PREFIX + "userinfo <User ID>", false)
							.addField("AFK Command : ", Config.PREFIX + "afk", false);
							
						event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

						event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", an help message will be send to your DM !").queue();

						if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
						
						((UserImpl) user).getPrivateChannel().sendMessage(userEmbed.build()).queue();	
						
					}
					
					
					if(args[1].equalsIgnoreCase("moderation")) {
						
						EmbedBuilder modEmbed = new EmbedBuilder();
						
						modEmbed.setTitle("Help Moderation Commands")
								.setColor(Color.ORANGE)
								.setAuthor("Help", null, event.getJDA().getSelfUser().getAvatarUrl())
								.setDescription("Display all moderation commands")
							
							.addField("Ban Command : ", Config.PREFIX + "ban <@User>", false)
							.addField("Unban Command : ", Config.PREFIX + "unban <User ID>", false)
							.addField("Kick Command : ", Config.PREFIX + "kick <@User>", false)
							.addField("Clear Command : ", Config.PREFIX + "clear <NumberMessageToDel>", false)
							.addField("Mute Command : ", Config.PREFIX + "mute <@User>", false)
							.addField("Unmute Command : ", Config.PREFIX + "unmute <@User>", false)
							.addField("MassKick Command : ", Config.PREFIX + "masskick <All @User>", false);
						
						
						event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

						event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", an help message will be send to your DM !").queue();

						if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
						
						((UserImpl) user).getPrivateChannel().sendMessage(modEmbed.build()).queue();				
						
					}
					
					
					
			}
			
		}
	
	}
	

}
