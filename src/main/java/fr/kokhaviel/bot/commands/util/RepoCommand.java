package fr.kokhaviel.bot.commands.util;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RepoCommand extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if(args[0].equalsIgnoreCase(Config.PREFIX + "repo")) {
			
			EmbedBuilder repoEmbed = new EmbedBuilder();
			
			repoEmbed.setTitle("Repository Links");
			repoEmbed.setColor(Color.magenta);
			repoEmbed.setAuthor("Repo Menu", null, event.getJDA().getSelfUser().getAvatarUrl());
			repoEmbed.setDescription("Display Repository Links");
			
			repoEmbed.addField("GitHub : ", "[GitHub Link](https://github.com/Kokhaviel/Mee7)", false );
			repoEmbed.addField("Git : ", "git clone https://github.com/Kokhaviel/Mee7.git", false);
			repoEmbed.addField("SSH : ", "git@github.com:Kokhaviel/Mee7.git", false);
			
			repoEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nCommand Requested by : " + event.getMessage().getAuthor(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

			event.getChannel().sendMessage(repoEmbed.build()).queue();
			
			event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);
			
		}
	
	}
}
