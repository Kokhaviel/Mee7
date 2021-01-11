package fr.kokhaviel.bot.commands.moderation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ClearCommand extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		TextChannel channel = (TextChannel) event.getChannel();
		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "clear")) {
			
			if(args.length < 2 ) { 
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "clear <Int>").queue(
						delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});
			
			} else if(args.length > 2) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "clear <Int>").queue(
						delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});
				
			} else {
		
			int numToDelete = Integer.parseInt(args[1]);
				
			List<Message> toDelete = channel.getHistory().retrievePast(numToDelete).complete();
		
			event.getTextChannel().deleteMessages(toDelete).queue(
				success -> {channel.sendMessage("Successfully Delete " + numToDelete + " Messages !").queue(
					delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});});
			
			}

		}
		
	}

}
