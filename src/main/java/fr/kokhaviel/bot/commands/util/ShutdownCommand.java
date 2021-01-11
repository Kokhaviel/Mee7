package fr.kokhaviel.bot.commands.util;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShutdownCommand extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
	
	
	String[] args = event.getMessage().getContentRaw().split("\\s+");
	
	User author = event.getMessage().getAuthor();

	if(args[0].equalsIgnoreCase(Config.PREFIX + "shutdown") && author.getId().equals(Config.OWNER_ID)) {
		
		event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
		
		event.getJDA().shutdown();
		
		
		
	}
		
		
		
	}

}
