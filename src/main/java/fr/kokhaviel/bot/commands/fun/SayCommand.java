package fr.kokhaviel.bot.commands.fun;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SayCommand extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		StringBuilder sayBuilder = new StringBuilder();
		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "say")) {
			
			
			if(args.length < 2) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage("Missing Arguments : Please Speficy At Least One Word To Say !").queue(
						delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});
				
			} else {
				
				for(String arg : args) {
					
					if(arg == args[0]) continue;
					
					sayBuilder.append(arg + " ");
				}	
				
			event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
			event.getChannel().sendMessage(sayBuilder).queue();
				
			}
			
		}
		
	}

}
