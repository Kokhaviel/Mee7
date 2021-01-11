package fr.kokhaviel.bot.event.logs;

import java.awt.Color;
import java.util.List;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNSFWEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNewsEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateSlowmodeEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateBitrateEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateUserLimitEvent;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateRolesEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkChannelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkTimeoutEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBannerEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostCountEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostTierEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateDescriptionEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateOwnerEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateSystemChannelEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceStreamEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateHoistedEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateMentionableEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Logs extends ListenerAdapter {
			
	@Override
	public void onReady(ReadyEvent event) {
		System.out.println( "Bot Successfully Connected !");
	}
	
	@Override
	public void onShutdown(ShutdownEvent event) {
		System.out.println("Bot Successfully Disconncetd !");
	}
	
	@Override
	public void onException(ExceptionEvent event) {
		
		System.out.println("Exception : " + "\nClass : " + event.getClass().getName() + "\nCause : " + event.getCause());
		
		List<TextChannel> textChannels = ((GuildChannel) event).getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder exceptionEmbed = new EmbedBuilder();
		
		exceptionEmbed.setTitle("An Exception Occured")
				.setColor(Color.RED)
				.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + ((GuildChannel) event).getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
		
			.addField("Exception : ", event.getClass().getSimpleName() + "\n" + event.getCause(), false);
		
		logsChannel.sendMessage(exceptionEmbed.build()).queue();
		
	}
	
	@Override
	public void onGatewayPing(GatewayPingEvent event) {
		
		if(Config.pingCount%2 == 0) System.out.println("Ping !");
		
		if(Config.pingCount%2 == 1) System.out.println("Pong !");
		
		Config.pingCount++;
		
	}
	
	@Override
	public void onCategoryCreate(CategoryCreateEvent event) {
		
		System.out.println("Category Created : " + event.getCategory().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder catCreEmebed = new EmbedBuilder();
		
		catCreEmebed.setTitle("Category Created")
				.setColor(Color.GREEN)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("Category Created :", event.getCategory().getName() + "\nID : " + event.getId(), true);
		
		logsChannel.sendMessage(catCreEmebed.build()).queue();
		
	}
	
	@Override
	public void onCategoryDelete(CategoryDeleteEvent event) {
		
		System.out.println("Category Deleted : " + event.getCategory().getName());
			
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder catDelEmbed = new EmbedBuilder();
		
		catDelEmbed.setTitle("Category Delete")
				.setColor(Color.RED)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
		
			.addField("Category Deleted : ", event.getCategory().getName() + "\nID : " + event.getId(), false);
		
		logsChannel.sendMessage(catDelEmbed.build()).queue();
	}
		
	@Override
	public void onCategoryUpdateName(CategoryUpdateNameEvent event) {
		
		System.out.println("Category Name Update : " + event.getOldName() + " -> " + event.getNewName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);	
		
		EmbedBuilder catNameUpdateEmbed = new EmbedBuilder();
		
		catNameUpdateEmbed.setTitle("Category Name update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("Category updated : ", event.getOldName() + " -> " + event.getNewName() + "\nID : " + event.getCategory().getId(), false);

		logsChannel.sendMessage(catNameUpdateEmbed.build()).queue();
	}
	
	@Override
	public void onEmoteAdded(EmoteAddedEvent event) {
		
		System.out.println("Emote Added : " + event.getEmote().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder emoteAddEmbed = new EmbedBuilder();
		
		emoteAddEmbed.setTitle("Emote Add")
				.setColor(Color.GREEN)
				.setThumbnail(event.getEmote().getImageUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("Emote Added : ", event.getEmote().getName() + "\n(ID : " + event.getEmote().getId() + ")", false);
		
		logsChannel.sendMessage(emoteAddEmbed.build()).queue();
	}
	
	@Override
	public void onEmoteRemoved(EmoteRemovedEvent event) {
		
		System.out.println("Emote Removed : " + event.getEmote().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder emoteRmEmbed = new EmbedBuilder();
		
		emoteRmEmbed.setTitle("Emote Removed")
				.setColor(Color.RED)
				.setThumbnail(event.getEmote().getImageUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
	
			.addField("Emote Removed : ", event.getEmote().getName() + "\n(ID : " + event.getEmote().getId() + ")", false);
		
		logsChannel.sendMessage(emoteRmEmbed.build()).queue();
	}
	
	@Override
	public void onEmoteUpdateName(EmoteUpdateNameEvent event) {
		
		System.out.println("Emote Name Update : " + event.getOldName() + " -> " + event.getNewName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder emoteUpdateNameEmbed = new EmbedBuilder();
		
		emoteUpdateNameEmbed.setTitle("Emote Name Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getEmote().getImageUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("Emote Updated : ", event.getOldName() + " -> " + event.getNewName() + "\n(ID : " + event.getEmote().getId(), false);
	
		logsChannel.sendMessage(emoteUpdateNameEmbed.build()).queue();
	}
	
	@Override
	public void onEmoteUpdateRoles(EmoteUpdateRolesEvent event) {
		
		System.out.println("Update Roles Emote : " + event.getOldRoles() + " -> " + event.getNewRoles());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder emoteRolesEmbed = new EmbedBuilder();
		
		emoteRolesEmbed.setTitle("Emote Roles Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("Roles : ", event.getOldRoles() + " -> " + event.getNewRoles(), false);
		
		logsChannel.sendMessage(emoteRolesEmbed.build()).queue();
		
	}
	
	@Override
	public void onGuildBan(GuildBanEvent event) {
		
		System.out.println("User Banned : " + event.getUser().getAsTag() + "  (Member ID : " + event.getUser().getId() + ")");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder banEmbed = new EmbedBuilder();
		
		banEmbed.setTitle("User Ban")
				.setColor(Color.RED)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("User Banned : ", event.getUser().getAsTag() + "\n(Member ID : " + event.getUser().getId() + ")",false);

		logsChannel.sendMessage(banEmbed.build()).queue();
	}
	
	@Override
	public void onGuildInviteCreate(GuildInviteCreateEvent event) {
		
		System.out.println("Invite Created : " + event.getUrl() );
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder inviteCreEmbed = new EmbedBuilder();
		
		inviteCreEmbed.setTitle("Invite Create")
				.setColor(Color.GREEN)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Invite Created : ", event.getUrl(), false);
		
		logsChannel.sendMessage(inviteCreEmbed.build()).queue();
			
	}
	
	@Override
	public void onGuildInviteDelete(GuildInviteDeleteEvent event) {
		
		System.out.println("Invite Deleted : " + event.getUrl());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder inviteDelEmbed = new EmbedBuilder();
		
		inviteDelEmbed.setTitle("Invite Delete")
				.setColor(Color.RED)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Invite Deleted : ", event.getUrl(), false);
		
		logsChannel.sendMessage(inviteDelEmbed.build()).queue();
		
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		
		System.out.println("User Joined : " + event.getUser().getAsTag() + "  (Member ID : " + event.getUser().getId() + ")");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder joinEmbed = new EmbedBuilder();
		
		joinEmbed.setTitle("User Join")
				.setColor(Color.GREEN)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("User Joined : ", event.getUser().getAsTag() + "\n(Member ID : " + event.getUser().getId() + ")", false);		
		
		logsChannel.sendMessage(joinEmbed.build()).queue();
	}
	
	@Override
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		
		System.out.println("User Leave : " + event.getUser().getAsTag() + "  (Member ID : " + event.getUser().getId() + ")");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder leaveEmbed = new EmbedBuilder();
		
		leaveEmbed.setTitle("User Leave")
				.setColor(Color.RED)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("User Leaved : ", event.getUser().getAsTag() + "\n(Member ID : " + event.getUser().getId() + ")", false);
			
		logsChannel.sendMessage(leaveEmbed.build()).queue();
	}
	
	@Override
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		
		System.out.println("Role Added : " + event.getRoles() + " to " + event.getUser().getAsTag());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleAddEmbed = new EmbedBuilder();
				
		roleAddEmbed.setTitle("Role Add")
				.setColor(Color.GREEN)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Added : ", event.getRoles() + "\nTo " + event.getUser().getAsTag(), false);
		
		logsChannel.sendMessage(roleAddEmbed.build()).queue();
		
	}
	
	@Override
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
		
		System.out.println("Role Removed : " + event.getRoles() + " to " + event.getUser().getAsTag());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleRemEmbed = new EmbedBuilder();
		
		roleRemEmbed.setTitle("Role Remove")
				.setColor(Color.RED)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Removed : ", event.getRoles() + "\nTo " + event.getUser().getAsTag(), false);
		
		logsChannel.sendMessage(roleRemEmbed.build()).queue();
	}
	
	@Override
	public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
		
		System.out.println(event.getOldTimeBoosted() + " -> " + event.getNewTimeBoosted() + "\nBy : " + event.getUser().getAsTag());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder boostTimeUpdateEmbed = new EmbedBuilder();
		
		boostTimeUpdateEmbed.setTitle("Boost Time Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Boost Time : ", event.getOldTimeBoosted() + " -> " + event.getNewTimeBoosted() + "\nBy : " + event.getUser().getAsTag(), false);
		
		logsChannel.sendMessage(boostTimeUpdateEmbed.build()).queue();
		
	}
	
	@Override
	public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
		
		System.out.println("User Update Nickname : " + event.getUser().getAsTag() + "\t" + event.getOldNickname() + " -> " + event.getNewNickname());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder nicknameUpdateEmbed = new EmbedBuilder();
		
		nicknameUpdateEmbed.setTitle("NickName Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Nickname Updated", event.getUser().getAsTag() + "\n" + event.getOldNickname() + " -> " + event.getNewNickname(), false);
		
		logsChannel.sendMessage(nicknameUpdateEmbed.build()).queue();
	}
	
//	@Override
//	public void onGuildMessageDelete(GuildMessageDeleteEvent event) {
//		
//		System.out.println("Message Deleted : " + event.getMessageId() + "\nIn : " + event.getChannel().getName() );
//		
//		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
//		TextChannel logsChannel = textChannels.get(0);
//		
//		EmbedBuilder messageDeleteEmbed = new EmbedBuilder();
//		
//		messageDeleteEmbed.setTitle("Message Delete")
//				.setColor(Color.RED)
//				.setThumbnail(event.getGuild().getIconUrl())
//				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
//				
//			.addField("Message Deleted : ", event.getMessageId() + "\nIn : " + event.getChannel().getAsMention(), false);
//		
//		if(!event.getChannel().equals(logsChannel)) {
//		
//			logsChannel.sendMessage(messageDeleteEmbed.build()).queue();	
//		
//		}
//	}
	
	@Override
	public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
		
		System.out.println("Message Updated : " + event.getMessage().getId() + " By " + event.getMember().getUser().getAsTag());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder messageUpdateEmbed = new EmbedBuilder();
		
		messageUpdateEmbed.setTitle("Message Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Message Updated : ",  event.getMessage().getId() + " By " + event.getMember().getUser().getAsTag(), false);
		
		logsChannel.sendMessage(messageUpdateEmbed.build()).queue();		
	}
	
	@Override
	public void onGuildUnban(GuildUnbanEvent event) {
		
		System.out.println("User Unbanned : " + event.getUser().getAsTag());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder unbanEmbed = new EmbedBuilder();
		
		unbanEmbed.setTitle("User Unban")
				.setColor(Color.GREEN)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("User Unbanned : ", event.getUser().getAsTag(), false);
		
		logsChannel.sendMessage(unbanEmbed.build()).queue();
	}
	
	@Override
	public void onGuildUpdateAfkChannel(GuildUpdateAfkChannelEvent event) {
		
		System.out.println("Afk Channel Updated : " + event.getOldAfkChannel().getName() + " -> " + event.getNewAfkChannel().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder afkUpdateEmbed = new EmbedBuilder();
		
		afkUpdateEmbed.setTitle("AFK Channel Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("AFK CHannel : ", event.getOldAfkChannel().getName() + " -> " + event.getNewAfkChannel().getName(), false);
		
		logsChannel.sendMessage(afkUpdateEmbed.build()).queue();
	}
	
	@Override
	public void onGuildUpdateAfkTimeout(GuildUpdateAfkTimeoutEvent event) {
		
		System.out.println("Afk Timeout Updated : " + event.getOldAfkTimeout() + " -> " + event.getNewAfkTimeout());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder afkTOUpdateEmbed = new EmbedBuilder();
		
		afkTOUpdateEmbed.setTitle("AFK Timeout Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("AFK Timeout : ", event.getOldAfkTimeout() + " -> " + event.getNewAfkTimeout(), false);
		
		logsChannel.sendMessage(afkTOUpdateEmbed.build()).queue();		
	}
	
	@Override
	public void onGuildUpdateBanner(GuildUpdateBannerEvent event) {
		
		System.out.println("Banner Updated : " + event.getOldBannerUrl() + " -> " + event.getNewBannerUrl());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder bannerUpdateEmbed = new EmbedBuilder();
		
		bannerUpdateEmbed.setTitle("Banner Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Banner Updated : ", event.getOldBannerUrl() + " -> " + event.getNewBannerUrl(), false);
		
		logsChannel.sendMessage(bannerUpdateEmbed.build()).queue();
	}
	
	@Override
	public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent event) {
		
		System.out.println("Boost Count Updated : " + event.getNewBoostCount() );
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder boostCount = new EmbedBuilder();
		
		boostCount.setTitle("Boost Count Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Server Has Now ", event.getNewBoostCount() + " Boost !", false);
		
		logsChannel.sendMessage(boostCount.build()).queue();
	}
	
	@Override
	public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent event) {
		
		System.out.println("Boost Tier Updated : " + event.getOldBoostTier() + " -> " + event.getNewBoostTier());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder boostTierUpdate = new EmbedBuilder();
		
		boostTierUpdate.setTitle("Boost Tier Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Boost Tier : ", event.getOldBoostTier() + " -> " + event.getNewBoostTier(), false);
		
		logsChannel.sendMessage(boostTierUpdate.build()).queue();
	}
	
	@Override
	public void onGuildUpdateDescription(GuildUpdateDescriptionEvent event) {
		
		System.out.println("Description Updated : " + event.getOldDescription() + " -> " + event.getNewDescription());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder descriptionUpdateEmbed = new EmbedBuilder();
		
		descriptionUpdateEmbed.setTitle("Description Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Description Updated : ", event.getOldDescription() + " -> " + event.getNewDescription(), false);
		
		logsChannel.sendMessage(descriptionUpdateEmbed.build()).queue();		
	}
	
	@Override
	public void onGuildUpdateIcon(GuildUpdateIconEvent event) {
		
		System.out.println("Icon Updated : " + event.getOldIconUrl() + " -> " + event.getNewIconUrl());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder iconUpdateEmbed = new EmbedBuilder();
		
		iconUpdateEmbed.setTitle("Icon Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Icon Updated : ", event.getOldIconUrl() + " -> " + event.getNewIconUrl(), false);
		
		logsChannel.sendMessage(iconUpdateEmbed.build()).queue();
	}
	
	@Override
	public void onGuildUpdateName(GuildUpdateNameEvent event) {
		
		System.out.println("Server Name Updated : " + event.getOldName() + " -> " + event.getNewName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder nameUpdateEmbed = new EmbedBuilder();
		
		nameUpdateEmbed.setTitle("Server Name Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Name Updated : ", event.getOldName() + " -> " + event.getNewName(), false);
		
		logsChannel.sendMessage(nameUpdateEmbed.build()).queue();		
	}
	
	@Override
	public void onGuildUpdateOwner(GuildUpdateOwnerEvent event) {
		
		System.out.println("Owner Updated : " + event.getOldOwner().getUser().getAsTag() + " -> " + event.getNewOwner().getUser().getAsTag());
			
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder ownerUpdateEmbed = new EmbedBuilder();
		
		ownerUpdateEmbed.setTitle("Owner Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Owner : ", event.getOldOwner().getUser().getAsMention() + " -> " + event.getNewOwner().getUser().getAsMention(), false);
		
		logsChannel.sendMessage(ownerUpdateEmbed.build()).queue();			
	}
	
	@Override
	public void onGuildUpdateSystemChannel(GuildUpdateSystemChannelEvent event) {
		
		System.out.println("System Channel Updated : " + event.getOldSystemChannel().getName() + " -> " + event.getNewSystemChannel().getName() );
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder sysChannelEmbed = new EmbedBuilder();
		
		sysChannelEmbed.setTitle("System Channel Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getOldSystemChannel().getAsMention() + " -> " + event.getNewSystemChannel().getAsMention(), false);
		
		logsChannel.sendMessage(sysChannelEmbed.build()).queue();		
	}
	
	@Override
	public void onGuildVoiceGuildDeafen(GuildVoiceGuildDeafenEvent event) {
		
		System.out.println("User Deafened : " + event.getMember().getUser().getAsTag() + "In : " + event.getVoiceState().getChannel().getName());
			
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder deafenEmbed = new EmbedBuilder();
		
		EmbedBuilder undeafenEmbed = new EmbedBuilder();
		
		deafenEmbed.setTitle("Voice User Deafen")
				.setColor(Color.RED)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("User Deafened : ", event.getMember().getUser().getAsTag() + " In : " + event.getVoiceState().getChannel().getName(), false);
		
		undeafenEmbed.setTitle("Voice User Undeafen")
				.setColor(Color.GREEN)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
		
			.addField("User Undeafened : ", event.getMember().getUser().getAsTag() + " In : " + event.getVoiceState().getChannel().getName(), false);
				
		if(event.isGuildDeafened()) logsChannel.sendMessage(deafenEmbed.build()).queue();	
		
		if(!event.isGuildDeafened()) logsChannel.sendMessage(undeafenEmbed.build()).queue();
	}
		
	@Override
	public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
		
		System.out.println("User Muted : " + event.getMember().getUser().getAsTag() + "In : " + event.getVoiceState().getChannel().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder muteEmbed = new EmbedBuilder();
		
		EmbedBuilder unmuteEmbed = new EmbedBuilder();
		
		muteEmbed.setTitle("Voice User Mute")
				.setColor(Color.RED)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("User Muted : ", event.getMember().getUser().getAsTag() + " In : " + event.getVoiceState().getChannel().getName(), false);
		
		unmuteEmbed.setTitle("Voice User Unmute")
				.setColor(Color.GREEN)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("User Unmuted : ", event.getMember().getUser().getAsTag() + " In : " + event.getVoiceState().getChannel().getName(), false);
		
		if(event.isGuildMuted()) logsChannel.sendMessage(muteEmbed.build()).queue();
	
		if(!event.isGuildMuted()) logsChannel.sendMessage(unmuteEmbed.build()).queue();
	}
	
	@Override
	public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
		
		System.out.println("User Moved : " + event.getMember().getUser().getAsTag() + "\n" + event.getChannelLeft() + " -> " + event.getChannelJoined());
			
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder moveEmbed = new EmbedBuilder();
		
		moveEmbed.setTitle("Voice User Move")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("User Moved : ", event.getMember().getUser().getAsTag() + "\n" + event.getChannelLeft() + " -> " + event.getChannelJoined(), false);
		
		logsChannel.sendMessage(moveEmbed.build()).queue();		
	}
		
	@Override
	public void onGuildVoiceStream(GuildVoiceStreamEvent event) {
		
		System.out.println("Stream Started : " + event.getMember().getUser().getAsTag() + "\nIn : " + event.getVoiceState().getChannel().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder streamEmbed = new EmbedBuilder();
		
		EmbedBuilder streamEndEmbed = new EmbedBuilder();
		
		streamEmbed.setTitle("Stream Started")
				.setColor(Color.GREEN)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Stream : ", event.getMember().getUser().getAsTag() + "\nIn : " + event.getVoiceState().getChannel().getName(), false);
		
		streamEndEmbed.setTitle("Stream End")
				.setColor(Color.RED)
				.setThumbnail(event.getMember().getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

			.addField("Stream : ", event.getMember().getUser().getAsTag() + "\nIn : " + event.getVoiceState().getChannel().getName(), false);
				
		if(event.isStream()) logsChannel.sendMessage(streamEmbed.build()).queue();		
		
		if(!event.isStream()) logsChannel.sendMessage(streamEndEmbed.build()).queue();
	}
		
	@Override
	public void onRoleCreate(RoleCreateEvent event) {
		
		
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleCreateEmbed = new EmbedBuilder();
		
		roleCreateEmbed.setTitle("Role Create")
				.setColor(Color.GREEN)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Created : ", event.getRole().getName(), false);
		
		logsChannel.sendMessage(roleCreateEmbed.build()).queue();	
		
		System.out.println("Role Created : " + event.getRole().getName());
	}
		
	@Override
	public void onRoleDelete(RoleDeleteEvent event) {
		
		System.out.println("Role Deleted : " + event.getRole().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleDelEmbed = new EmbedBuilder();
		
		roleDelEmbed.setTitle("Role Delete")
				.setColor(Color.RED)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Deleted : ", event.getRole().getName(), false);
		
		logsChannel.sendMessage(roleDelEmbed.build()).queue();		
	}
		
	@Override
	public void onRoleUpdateColor(RoleUpdateColorEvent event) {
		
		System.out.println("Role Color Updated : " + event.getRole().getName() + "\nColor : " + event.getOldColor() + " -> " + event.getNewColor());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleColorEmbed = new EmbedBuilder();
		
		roleColorEmbed.setTitle("Role Color Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Updated : ", event.getRole().getName() + "\nColor : " + event.getOldColor() + " -> " + event.getNewColor(), false);
		
		logsChannel.sendMessage(roleColorEmbed.build()).queue();		
	}
		
	@Override
	public void onRoleUpdateHoisted(RoleUpdateHoistedEvent event) {
		
		System.out.println("Role Hoist Updated : " + event.getRole().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleHoistedEmbed = new EmbedBuilder();
		
		roleHoistedEmbed.setTitle("Role Hoist Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Updated : ", event.getRole().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue(), false);
		
		logsChannel.sendMessage(roleHoistedEmbed.build()).queue();		
	}
		
	@Override
	public void onRoleUpdateMentionable(RoleUpdateMentionableEvent event) {
		
		System.out.println("Role Mentionnable Updated : " + event.getRole().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleMentionEmbed = new EmbedBuilder();
		
		roleMentionEmbed.setTitle("Role Mentionnable Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Updated : ", event.getRole().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue(), false);
		
		logsChannel.sendMessage(roleMentionEmbed.build()).queue();
	}
		
	@Override
	public void onRoleUpdateName(RoleUpdateNameEvent event) {
		
		System.out.println("Role name Updated : " + event.getRole().getName() + "\n" + event.getOldName() + " -> " + event.getNewName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder roleNameUpdate = new EmbedBuilder();
		
		roleNameUpdate.setTitle("Role Name Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Role Updated : ", event.getRole().getName() + "\n" + event.getOldName() + " -> " + event.getNewName(), false);
		
		logsChannel.sendMessage(roleNameUpdate.build()).queue();		
	}
			
	@Override
	public void onTextChannelCreate(TextChannelCreateEvent event) {
		
		System.out.println("Text Channel Created : " + event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder textCreEmbed = new EmbedBuilder();
		
		textCreEmbed.setTitle("Text Channel Create")
				.setColor(Color.GREEN)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Created : ", event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")", false);
		
		logsChannel.sendMessage(textCreEmbed.build()).queue();
	}
		
	@Override
	public void onTextChannelDelete(TextChannelDeleteEvent event) {
		
		System.out.println("Text Channel Deleted : " + event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder textDelEmbed = new EmbedBuilder();
		
		textDelEmbed.setTitle("Text Channel Delete")
				.setColor(Color.RED)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Deleted : ", event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")", false);
		
		logsChannel.sendMessage(textDelEmbed.build()).queue();		
	}
		
	@Override
	public void onTextChannelUpdateName(TextChannelUpdateNameEvent event) {
		
		System.out.println("Text Channel Name Updated : " + event.getChannel().getAsMention() + "\n" + event.getOldName() + " -> " + event.getNewName() );
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder textNameUpdateEmbed = new EmbedBuilder();
		
		textNameUpdateEmbed.setTitle("Text Channel Name Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getAsMention() + "\n" + event.getOldName() + " -> " + event.getNewName(), false);
		
		logsChannel.sendMessage(textNameUpdateEmbed.build()).queue();		
	}
		
	@Override
	public void onTextChannelUpdateNews(TextChannelUpdateNewsEvent event) {
		
		System.out.println("Text Channel News Updated : " + event.getChannel().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder textNewsUpdateEmbed = new EmbedBuilder();
		
		textNewsUpdateEmbed.setTitle("Text Channel Update News")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue(), false);
		
		logsChannel.sendMessage(textNewsUpdateEmbed.build()).queue();		
	}
		
	@Override
	public void onTextChannelUpdateNSFW(TextChannelUpdateNSFWEvent event) {
		
		System.out.println("Text Channel NSFW Updated : " + event.getChannel().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue() );
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder textNSFWUpdateEmbed = new EmbedBuilder();
		
		textNSFWUpdateEmbed.setTitle("Text Channel NSFW Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue(), false);
		
		logsChannel.sendMessage(textNSFWUpdateEmbed.build()).queue();		
	}
		
	@Override
	public void onTextChannelUpdateSlowmode(TextChannelUpdateSlowmodeEvent event) {
		
		System.out.println("Text Channel Slowmode Updated : " + event.getChannel().getName() + "\n" + event.getOldSlowmode() + " s -> " + event.getNewSlowmode() + "s");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder textSMUpdateEmbed = new EmbedBuilder();
		
		textSMUpdateEmbed.setTitle("Text Channel SlowMode Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "\n" + event.getOldSlowmode() + " s -> " + event.getNewSlowmode() + "s", false);
		
		logsChannel.sendMessage(textSMUpdateEmbed.build()).queue();		
	}
		
	@Override
	public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent event) {
		
		System.out.println("Text Channel Topic Updated : " + event.getChannel().getName() + "\n" + event.getOldTopic() + " -> " + event.getNewTopic());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder textTopicUpdateEmbe = new EmbedBuilder();
		
		textTopicUpdateEmbe.setTitle("Text Channel Topic Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "\n" + event.getOldTopic() + " -> " + event.getNewTopic(), false);
		
		logsChannel.sendMessage(textTopicUpdateEmbe.build()).queue();		
	}
		
	@Override
	public void onUserActivityStart(UserActivityStartEvent event) {
		
		System.out.println("Activity Started : " + event.getUser().getAsTag() + " : " + event.getNewActivity().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("activities", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder activityStartEmbed = new EmbedBuilder();
		
		activityStartEmbed.setTitle("Activity Started")
				.setColor(Color.GREEN)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField(event.getUser().getName(), event.getUser().getAsTag() + " : " + event.getNewActivity().getName(), false);
		
		logsChannel.sendMessage(activityStartEmbed.build()).queue();		
	}
		
	@Override
	public void onUserActivityEnd(UserActivityEndEvent event) {
		
		System.out.println("Activity Ended : " + event.getUser().getAsTag() + " : " + event.getOldActivity().getName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("activities", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder activityEndEmbed = new EmbedBuilder();
		
		activityEndEmbed.setTitle("Activity Ended")
				.setColor(Color.RED)
				.setThumbnail(event.getUser().getAvatarUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField(event.getUser().getName(), event.getUser().getAsTag() + " : " + event.getOldActivity().getName(), false);
		
		logsChannel.sendMessage(activityEndEmbed.build()).queue();		
	}
		
	@Override
	public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
		
		System.out.println("Voice Channel Created : " + event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder voiceCreEmbed = new EmbedBuilder();
		
		voiceCreEmbed.setTitle("Voice Channel Create")
				.setColor(Color.GREEN)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")", false);
		
		logsChannel.sendMessage(voiceCreEmbed.build()).queue();		
	}
	
	@Override
	public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
		
		System.out.println("Voice Channel Deleted : " + event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder voiceDelEmbed = new EmbedBuilder();
		
		voiceDelEmbed.setTitle("Voice Channel Delete")
				.setColor(Color.RED)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")", false);
		
		logsChannel.sendMessage(voiceDelEmbed.build()).queue();		
	}
	
	@Override
	public void onVoiceChannelUpdateBitrate(VoiceChannelUpdateBitrateEvent event) {
		
		System.out.println("Voice Channel Bitrate Updated : " + event.getChannel().getName() + "\n" + event.getOldBitrate() + " bps -> " + event.getNewBitrate() + " bps");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder voiceBitRateUpdateEmbed = new EmbedBuilder();
		
		voiceBitRateUpdateEmbed.setTitle("Voice Channel Bitrate Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "\n" + event.getOldBitrate() + " bps -> " + event.getNewBitrate() + " bps", false);
		
		logsChannel.sendMessage(voiceBitRateUpdateEmbed.build()).queue();		
	}
	
	@Override
	public void onVoiceChannelUpdateName(VoiceChannelUpdateNameEvent event) {
		
		System.out.println("Voice Channel Name Updated : " + event.getChannel().getName() + "\n" + event.getOldName() + " -> " + event.getNewName());
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder voiceNameUpdateEmbed = new EmbedBuilder();
		
		voiceNameUpdateEmbed.setTitle("Voice Channel Name Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "\n" + event.getOldName() + " -> " + event.getNewName(), false);
		
		logsChannel.sendMessage(voiceNameUpdateEmbed.build()).queue();		
	}
	
	@Override
	public void onVoiceChannelUpdateUserLimit(VoiceChannelUpdateUserLimitEvent event) {
		
		System.out.println("Voice Channel User Limit Updated : " + event.getChannel().getName() + "\n" + event.getOldUserLimit() + " user(s) -> " + event.getNewUserLimit() + " user(s)");
		
		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);
		
		EmbedBuilder voiceUserLimitUpdateEmbed = new EmbedBuilder();
		
		voiceUserLimitUpdateEmbed.setTitle("Voice Channel User Limit Update")
				.setColor(Color.ORANGE)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter("Developped by " + Config.DEVELOPER_TAG +"\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")
				
			.addField("Channel Updated : ", event.getChannel().getName() + "\n" + event.getOldUserLimit() + " user(s) -> " + event.getNewUserLimit() + " user(s)", false);
		
		logsChannel.sendMessage(voiceUserLimitUpdateEmbed.build()).queue();		
	}
	
}
