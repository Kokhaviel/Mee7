package fr.kokhaviel.bot.commands.moderation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BanCommand extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		

		
		if(args[0].equalsIgnoreCase(Config.PREFIX + "ban")) {

			TextChannel channel = (TextChannel) event.getChannel();

			Guild guild = event.getGuild();

			Member author = event.getMessage().getMember();


			if(args.length < 3) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "ban <@User> <Reason>").queue(
						delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));
				
				
			} else if(args.length > 3) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "ban <@User> <Reason>").queue(
						delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));
				
			} else {
				
				List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
				
				Member target = mentionedMembers.get(0);
				
				if(guild == null) channel.sendMessage("You must execute this command on server !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
				
				else {
					assert author != null;
					if(!author.hasPermission(Permission.BAN_MEMBERS)) channel.sendMessage("You dont have the permission to ban member !").queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

					 else if(author.getRoles().get(0).getPosition() <= target.getRoles().get(0).getPosition()) channel.sendMessage("You Cannot Ban This Member").queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

					 else {
						guild.ban(target, 3, args[2]).queue(
								succes -> channel.sendMessage("Successfully Banned " + target.getUser().getAsTag() + " !").queue(
										delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)),
								error -> channel.sendMessage("Unable To Ban " + target.getUser().getAsTag() + " !").queue(
										delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS))
						);

					}
				}
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
			}
			
			
			
			
			
			
			
			
			
		}
		
	}
	

}
