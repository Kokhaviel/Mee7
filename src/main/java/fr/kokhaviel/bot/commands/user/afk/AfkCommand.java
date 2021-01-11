package fr.kokhaviel.bot.commands.user.afk;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.Mee7;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AfkCommand extends ListenerAdapter {

	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "afk")) {
			
			
			if(!Mee7.afkIDs.contains(event.getMember().getId())) {
				
				Mee7.afkIDs.add(event.getMember().getId());
								
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage("Successfully Set Your AFK !").queue(delete -> {delete.delete().queueAfter(2, TimeUnit.SECONDS);});

				
				
			} else {
				
				Mee7.afkIDs.remove(event.getMember().getId());
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage("Successfully Removed Your AKF !").queue(delete -> {delete.delete().queueAfter(2, TimeUnit.SECONDS);});
				
			}
			
		}
		
		
	}
	
	

}
