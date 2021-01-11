package fr.kokhaviel.bot.event.moderation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import fr.kokhaviel.bot.Config;

public class AutoModerator extends ListenerAdapter {
	
	List<String> badWords = new ArrayList<>(Arrays.asList("Fuck", "fdp"));
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		for(String arg : args) {
			
			if(badWords.contains(arg)) {
				
				
				EmbedBuilder badwordDetect = new EmbedBuilder();
				
				badwordDetect.setTitle("Badword Detected :");
				badwordDetect.setColor(Color.red);
				badwordDetect.setThumbnail("https://cdn.discordapp.com/avatars/585419690411819060/4f6dc909ca93e98f8610dce9087ed747.webp?size=128");
				badwordDetect.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

				badwordDetect.addField("Badword : ", arg, false);

				event.getChannel().sendMessage(badwordDetect.build()).queue();
				
				event.getMessage().delete().queue();
				
			}
			
		}

		
	}

}
