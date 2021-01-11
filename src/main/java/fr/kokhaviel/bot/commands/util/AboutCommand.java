package fr.kokhaviel.bot.commands.util;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

public class AboutCommand extends ListenerAdapter {
	
	
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "about")) {
			
			User user = event.getMember().getUser();
			
			EmbedBuilder aboutEmbed = new EmbedBuilder();
			
			aboutEmbed.setTitle("About Menu")
					.setColor(Color.GREEN)
					.setAuthor("Mee7 : A Simple Java Discord Bot ")
					.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
					
				.addField("Developer : ", "Kokhaviel.java#0001", false)
				.addField("Github : ", "[Github Repository](https://github.com/Kokhaviel/Mee7)", false)
				.addField("Prefix : ", Config.PREFIX, false)
				.addField("Help : ", Config.PREFIX + "help", false)
				.addField("Library : ", "[JDA](https://github.com/DV8FromTheWorld/JDA)", false);
			
			event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
			
			event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", a message will be send to your DM !").queue();
			
			if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
			
			((UserImpl) user).getPrivateChannel().sendMessage(aboutEmbed.build()).queue();
			
				
			
			
		}
		
	}

}
