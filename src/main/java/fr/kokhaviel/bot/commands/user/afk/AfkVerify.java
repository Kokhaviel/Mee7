package fr.kokhaviel.bot.commands.user.afk;

import java.awt.Color;
import java.util.List;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.Mee7;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AfkVerify extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {


		List<Member> members = event.getMessage().getMentionedMembers();
		
		
		for(Member member : members) {
			
			if(Mee7.afkIDs.contains(member.getId())) {
				
				EmbedBuilder afkMentionEmbed = new EmbedBuilder();
				
				afkMentionEmbed.setTitle("Afk Member Mention")
						.setDescription("This user is afk ...")
						.setColor(Color.RED)
						.setThumbnail(member.getUser().getAvatarUrl())
						.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
						
					.addField("Member Mentionned : ", member.getUser().getAsTag(), false );
				
				event.getChannel().sendMessage(afkMentionEmbed.build()).queue();				
				
				
			}
			
		}
		
	}

}
