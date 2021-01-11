package fr.kokhaviel.bot.commands.util;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingCommand extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		long time = System.currentTimeMillis();
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "ping")) {
			
			 event.getChannel().sendMessage("Pong").queue(
					 reponse -> {reponse.editMessageFormat("Pong : %d ms", System.currentTimeMillis() - time).queue();});
			 
			 event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
			 
		}
		
		
		
	}

}