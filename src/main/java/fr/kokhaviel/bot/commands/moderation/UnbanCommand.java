package fr.kokhaviel.bot.commands.moderation;

import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UnbanCommand extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
	
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		TextChannel channel = (TextChannel) event.getChannel();
		
		Guild guild = event.getGuild();
		
		Member author = event.getMessage().getMember();	
	
		if(args[0].equalsIgnoreCase(Config.PREFIX + "unban")) {
			
			if(guild == null) {

				channel.sendMessage("You must execute this command on server !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

			}
			
			else if(!author.hasPermission(Permission.BAN_MEMBERS)) {

				channel.sendMessage("You dont have the permission to unban member !").queue(
						delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

			}
			
			else if(args.length < 2) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "unban <User ID>").queue(
						delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});
			
			} else if(args.length > 2) {
				
				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
				
				event.getChannel().sendMessage("Too Arguments : Please Use " + Config.PREFIX + "unban <User ID>").queue(
						delete -> {delete.delete().queueAfter(5, TimeUnit.SECONDS);});
				
			} else {

				User target = event.getJDA().getUserById(args[1]);

				guild.unban(target).queue(
						success -> {
							channel.sendMessage("Successfully Unban " + target.getAsTag()).queue(
									delete -> {
										delete.delete().queueAfter(5, TimeUnit.SECONDS);
									});
						},
						error -> {
							channel.sendMessage("Unable To Unban " + target.getAsTag()).queue(
									delete -> {
										delete.delete().queueAfter(5, TimeUnit.SECONDS);
									});
						}
				);

				event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
			}
			
		}
		
		
	}
}
