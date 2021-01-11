package fr.kokhaviel.bot.commands.user;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AvatarCommand extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		

		String[] args = event.getMessage().getContentRaw().split("\\s+");	
		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "avatar")) {

			
			
			if(args.length < 2) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage("Missing Arguments : Please Use !avatar <User ID> !").queue(
						delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});
				
			} else if(args.length > 2) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage("Too Arguments : Please Use !avatar <User ID> !").queue(
						delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});
				
			} else {
				
				Member target = event.getGuild().getMemberById(args[1]);
		
				EmbedBuilder avatarEmbed = new EmbedBuilder();
			
				avatarEmbed.setTitle("Avatar of " + target.getUser().getAsTag());
				avatarEmbed.setColor(Color.YELLOW);
				avatarEmbed.setAuthor("Avatar");
				avatarEmbed.setThumbnail(target.getUser().getAvatarUrl());
		
				event.getChannel().sendMessage(avatarEmbed.build()).queue();
			
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
			
			}
		
		}
		
		
	}

}
