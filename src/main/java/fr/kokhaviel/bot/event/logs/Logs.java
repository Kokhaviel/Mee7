/*
 * Copyright (C) 2021 Kokhaviel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.kokhaviel.bot.event.logs;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.channel.category.*;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.text.*;
import net.dv8tion.jda.api.events.channel.text.update.*;
import net.dv8tion.jda.api.events.channel.voice.*;
import net.dv8tion.jda.api.events.channel.voice.update.*;
import net.dv8tion.jda.api.events.emote.*;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.*;
import net.dv8tion.jda.api.events.guild.invite.*;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.*;
import net.dv8tion.jda.api.events.guild.update.*;
import net.dv8tion.jda.api.events.guild.voice.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.role.*;
import net.dv8tion.jda.api.events.role.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class Logs extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Bot Successfully Connected !");
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        System.out.println("Bot Successfully Disconnected !");
    }

    @Override
    public void onGatewayPing(@NotNull GatewayPingEvent event) {
        if (Config.pingCount % 2 == 0) System.out.println("Ping !");
        if (Config.pingCount % 2 == 1) System.out.println("Pong !");
        Config.pingCount++;
    }

    @Override
    public void onCategoryCreate(CategoryCreateEvent event) {
        System.out.println("Category Created : " + event.getCategory().getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder catCreEmbed = new EmbedBuilder();

        catCreEmbed.setTitle(LOGS_OBJECT.get("category_created").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("category_created").getAsString(), event.getCategory().getName() + "\nID : " + event.getId(), true);

        logsChannel.sendMessageEmbeds(catCreEmbed.build()).queue();
    }

    @Override
    public void onCategoryDelete(CategoryDeleteEvent event) {
        System.out.println("Category Deleted : " + event.getCategory().getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder catDelEmbed = new EmbedBuilder();

        catDelEmbed.setTitle(LOGS_OBJECT.get("category_deleted").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("category_deleted").getAsString(), event.getCategory().getName() + "\nID : " + event.getId(), false);

        logsChannel.sendMessageEmbeds(catDelEmbed.build()).queue();
    }

    @Override
    public void onCategoryUpdateName(CategoryUpdateNameEvent event) {
        System.out.println("Category Name Update : " + event.getOldName() + " -> " + event.getNewName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder catNameUpdateEmbed = new EmbedBuilder();

        catNameUpdateEmbed.setTitle(LOGS_OBJECT.get("category_name_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("category_name_updated").getAsString(), event.getOldName() + " -> " + event.getNewName() + "\nID : " + event.getCategory().getId(), false);

        logsChannel.sendMessageEmbeds(catNameUpdateEmbed.build()).queue();
    }

    @Override
    public void onEmoteAdded(EmoteAddedEvent event) {
        System.out.println("Emote Added : " + event.getEmote().getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder emoteAddEmbed = new EmbedBuilder();

        emoteAddEmbed.setTitle(LOGS_OBJECT.get("emote_added").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getEmote().getImageUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("emote_added").getAsString(), event.getEmote().getName() + "\n(ID : " + event.getEmote().getId() + ")", false);

        logsChannel.sendMessageEmbeds(emoteAddEmbed.build()).queue();
    }

    @Override
    public void onEmoteRemoved(EmoteRemovedEvent event) {
        System.out.println("Emote Removed : " + event.getEmote().getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder emoteRmEmbed = new EmbedBuilder();

        emoteRmEmbed.setTitle(LOGS_OBJECT.get("emote_removed").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getEmote().getImageUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("emote_removed").getAsString(), event.getEmote().getName() + "\n(ID : " + event.getEmote().getId() + ")", false);

        logsChannel.sendMessageEmbeds(emoteRmEmbed.build()).queue();
    }

    @Override
    public void onEmoteUpdateName(EmoteUpdateNameEvent event) {
        System.out.println("Emote Name Update : " + event.getOldName() + " -> " + event.getNewName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder emoteUpdateNameEmbed = new EmbedBuilder();

        emoteUpdateNameEmbed.setTitle(LOGS_OBJECT.get("emote_name_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getEmote().getImageUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("emote_name_updated").getAsString(), event.getOldName() + " -> " + event.getNewName() + "\n(ID : " + event.getEmote().getId(), false);

        logsChannel.sendMessageEmbeds(emoteUpdateNameEmbed.build()).queue();
    }

   /* @Override
    public void onEmoteUpdateRoles(EmoteUpdateRolesEvent event) {
        System.out.println("Update Roles Emote : " + event.getOldRoles() + " -> " + event.getNewRoles());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder emoteRolesEmbed = new EmbedBuilder();

        emoteRolesEmbed.setTitle(LOGS_OBJECT.get("emote_roles_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("emote_roles_updated").getAsString(), event.getOldRoles() + " -> " + event.getNewRoles(), false);

        logsChannel.sendMessageEmbeds(emoteRolesEmbed.build()).queue();
    }*/

    @Override
    public void onGuildBan(GuildBanEvent event) {
        System.out.println("User Banned : " + event.getUser().getAsTag() + "  (Member ID : " + event.getUser().getId() + ")");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder banEmbed = new EmbedBuilder();

        banEmbed.setTitle(LOGS_OBJECT.get("user_banned").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("user_banned").getAsString(), event.getUser().getAsTag() + "\n(Member ID : " + event.getUser().getId() + ")", false);

        logsChannel.sendMessageEmbeds(banEmbed.build()).queue();
    }

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent event) {
        System.out.println("Invite Created : " + event.getUrl());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder inviteCreEmbed = new EmbedBuilder();

        inviteCreEmbed.setTitle(LOGS_OBJECT.get("invite_created").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("invite_created").getAsString(), event.getUrl(), false);

        logsChannel.sendMessageEmbeds(inviteCreEmbed.build()).queue();
    }

    @Override
    public void onGuildInviteDelete(GuildInviteDeleteEvent event) {
        System.out.println("Invite Deleted : " + event.getUrl());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder inviteDelEmbed = new EmbedBuilder();

        inviteDelEmbed.setTitle(LOGS_OBJECT.get("invite_deleted").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("invite_deleted").getAsString(), event.getUrl(), false);

        logsChannel.sendMessageEmbeds(inviteDelEmbed.build()).queue();
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        System.out.println("User Joined : " + event.getUser().getAsTag() + "  (Member ID : " + event.getUser().getId() + ")");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder joinEmbed = new EmbedBuilder();

        joinEmbed.setTitle(LOGS_OBJECT.get("member_joined").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("member_joined").getAsString(), event.getUser().getAsTag() + "\n(Member ID : " + event.getUser().getId() + ")", false);

        logsChannel.sendMessageEmbeds(joinEmbed.build()).queue();
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        System.out.println("User Leave : " + event.getUser().getAsTag() + "  (Member ID : " + event.getUser().getId() + ")");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder leaveEmbed = new EmbedBuilder();

        leaveEmbed.setTitle(LOGS_OBJECT.get("member_left").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("member_left").getAsString(), event.getUser().getAsTag() + "\n(Member ID : " + event.getUser().getId() + ")", false);

        logsChannel.sendMessageEmbeds(leaveEmbed.build()).queue();
    }

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        System.out.println("Role Added : " + event.getRoles() + " to " + event.getUser().getAsTag());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleAddEmbed = new EmbedBuilder();

        roleAddEmbed.setTitle(LOGS_OBJECT.get("role_added").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_added").getAsString(), event.getRoles() + "\n" + GENERAL_OBJECT.get("to").getAsString() + event.getUser().getAsTag(), false);

        logsChannel.sendMessageEmbeds(roleAddEmbed.build()).queue();
    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        System.out.println("Role Removed : " + event.getRoles() + " to " + event.getUser().getAsTag());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleRemEmbed = new EmbedBuilder();

        roleRemEmbed.setTitle(LOGS_OBJECT.get("role_removed").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_removed").getAsString(), event.getRoles() + "\n" + GENERAL_OBJECT.get("to").getAsString() + event.getUser().getAsTag(), false);

        logsChannel.sendMessageEmbeds(roleRemEmbed.build()).queue();
    }

    @Override
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
        System.out.println(event.getOldTimeBoosted() + " -> " + event.getNewTimeBoosted() + "\nBy : " + event.getUser().getAsTag());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder boostTimeUpdateEmbed = new EmbedBuilder();

        boostTimeUpdateEmbed.setTitle(LOGS_OBJECT.get("boost_time_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("boost_time_update").getAsString(), event.getOldTimeBoosted() + " -> " + event.getNewTimeBoosted() + "\n" + GENERAL_OBJECT.get("by").getAsString() + event.getUser().getAsTag(), false);

        logsChannel.sendMessageEmbeds(boostTimeUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
        System.out.println("User Update Nickname : " + event.getUser().getAsTag() + "\t" + event.getOldNickname() + " -> " + event.getNewNickname());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder nicknameUpdateEmbed = new EmbedBuilder();

        nicknameUpdateEmbed.setTitle(LOGS_OBJECT.get("nickname_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("nickname_updated").getAsString(), event.getUser().getAsTag() + "\n" + event.getOldNickname() + " -> " + event.getNewNickname(), false);

        logsChannel.sendMessageEmbeds(nicknameUpdateEmbed.build()).queue();
    }

/*
	@Override
	public void onGuildMessageDelete(GuildMessageDeleteEvent event) {

		System.out.println("Message Deleted : " + event.getMessageId() + "\nIn : " + event.getChannel().getName() );

		List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
		TextChannel logsChannel = textChannels.get(0);

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

		EmbedBuilder messageDeleteEmbed = new EmbedBuilder();

		messageDeleteEmbed.setTitle("Message Delete")
				.setColor(Color.RED)
				.setThumbnail(event.getGuild().getIconUrl())
				.setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

			.addField("Message Deleted : ", event.getMessageId() + "\nIn : " + event.getChannel().getAsMention(), false);

		if(!event.getChannel().equals(logsChannel)) {

			logsChannel.sendMessage(messageDeleteEmbed.build()).queue();

		}
	}
*/

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
        System.out.println("Message Updated : " + event.getMessage().getId() + " By " + Objects.requireNonNull(event.getMember()).getUser().getAsTag());

        if (!event.getAuthor().equals(event.getJDA().getSelfUser())) {
            List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
            TextChannel logsChannel = textChannels.get(0);

            final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
            assert LANG_FILE != null;
            final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
            final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
            final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

            EmbedBuilder messageUpdateEmbed = new EmbedBuilder();

            messageUpdateEmbed.setTitle(LOGS_OBJECT.get("message_updated").getAsString())
                    .setColor(Color.ORANGE)
                    .setThumbnail(event.getMember().getUser().getAvatarUrl())
                    .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                    .addField(LOGS_OBJECT.get("message_updated").getAsString(), event.getMessage().getId() + GENERAL_OBJECT.get("by").getAsString() + event.getMember().getUser().getAsTag(), false);

            logsChannel.sendMessageEmbeds(messageUpdateEmbed.build()).queue();
        }
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent event) {
        System.out.println("User Unbanned : " + event.getUser().getAsTag());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder unbanEmbed = new EmbedBuilder();

        unbanEmbed.setTitle(LOGS_OBJECT.get("member_unbanned").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("member_unbanned").getAsString(), event.getUser().getAsTag(), false);

        logsChannel.sendMessageEmbeds(unbanEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateAfkChannel(GuildUpdateAfkChannelEvent event) {
        System.out.println("Afk Channel Updated : " + Objects.requireNonNull(event.getOldAfkChannel()).getName() + " -> " + Objects.requireNonNull(event.getNewAfkChannel()).getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder afkUpdateEmbed = new EmbedBuilder();

        afkUpdateEmbed.setTitle(LOGS_OBJECT.get("afk_channel_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("afk_channel_updated").getAsString(), event.getOldAfkChannel().getName() + " -> " + event.getNewAfkChannel().getName(), false);

        logsChannel.sendMessageEmbeds(afkUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateAfkTimeout(GuildUpdateAfkTimeoutEvent event) {
        System.out.println("Afk Timeout Updated : " + event.getOldAfkTimeout() + " -> " + event.getNewAfkTimeout());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder afkTOUpdateEmbed = new EmbedBuilder();

        afkTOUpdateEmbed.setTitle(LOGS_OBJECT.get("afk_timeout_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("afk_timeout_updated").getAsString(), event.getOldAfkTimeout() + " -> " + event.getNewAfkTimeout(), false);

        logsChannel.sendMessageEmbeds(afkTOUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateBanner(GuildUpdateBannerEvent event) {
        System.out.println("Banner Updated : " + event.getOldBannerUrl() + " -> " + event.getNewBannerUrl());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder bannerUpdateEmbed = new EmbedBuilder();

        bannerUpdateEmbed.setTitle(LOGS_OBJECT.get("banner_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("banner_updated").getAsString(), format("[%s](%s) -> [%s](%s)", GENERAL_OBJECT.get("before").getAsString(),event.getOldBannerUrl(), GENERAL_OBJECT.get("after").getAsString(), event.getNewBannerUrl()), false);

        logsChannel.sendMessageEmbeds(bannerUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent event) {
        System.out.println("Boost Count Updated : " + event.getNewBoostCount());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder boostCount = new EmbedBuilder();

        boostCount.setTitle(LOGS_OBJECT.get("boost_count_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("server_has_now").getAsString(), event.getNewBoostCount() + LOGS_OBJECT.get("boosts").getAsString(), false);

        logsChannel.sendMessageEmbeds(boostCount.build()).queue();
    }

    @Override
    public void onGuildUpdateBoostTier(GuildUpdateBoostTierEvent event) {
        System.out.println("Boost Tier Updated : " + event.getOldBoostTier() + " -> " + event.getNewBoostTier());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder boostTierUpdate = new EmbedBuilder();

        boostTierUpdate.setTitle(LOGS_OBJECT.get("boost_tier_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("boost_tier").getAsString(), event.getOldBoostTier() + " -> " + event.getNewBoostTier(), false);

        logsChannel.sendMessageEmbeds(boostTierUpdate.build()).queue();
    }

    @Override
    public void onGuildUpdateDescription(GuildUpdateDescriptionEvent event) {
        System.out.println("Description Updated : " + event.getOldDescription() + " -> " + event.getNewDescription());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder descriptionUpdateEmbed = new EmbedBuilder();

        descriptionUpdateEmbed.setTitle(LOGS_OBJECT.get("description_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("description_updated").getAsString(), event.getOldDescription() + " -> " + event.getNewDescription(), false);

        logsChannel.sendMessageEmbeds(descriptionUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateIcon(GuildUpdateIconEvent event) {
        System.out.println("Icon Updated : " + event.getOldIconUrl() + " -> " + event.getNewIconUrl());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder iconUpdateEmbed = new EmbedBuilder();

        iconUpdateEmbed.setTitle(LOGS_OBJECT.get("icon_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("icon_updated").getAsString(), format("[%s](%s) -> [%s](%s)", GENERAL_OBJECT.get("before").getAsString(), event.getOldIconUrl(), GENERAL_OBJECT.get("after").getAsString(), event.getNewIconUrl()), false);

        logsChannel.sendMessageEmbeds(iconUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateName(GuildUpdateNameEvent event) {
        System.out.println("Server Name Updated : " + event.getOldName() + " -> " + event.getNewName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder nameUpdateEmbed = new EmbedBuilder();

        nameUpdateEmbed.setTitle(LOGS_OBJECT.get("server_name_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("server_name_updated").getAsString(), event.getOldName() + " -> " + event.getNewName(), false);

        logsChannel.sendMessageEmbeds(nameUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateOwner(GuildUpdateOwnerEvent event) {
        System.out.println("Owner Updated : " + Objects.requireNonNull(event.getOldOwner()).getUser().getAsTag() + " -> " + Objects.requireNonNull(event.getNewOwner()).getUser().getAsTag());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder ownerUpdateEmbed = new EmbedBuilder();

        ownerUpdateEmbed.setTitle(LOGS_OBJECT.get("owner_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("owner_update").getAsString(), event.getOldOwner().getUser().getAsMention() + " -> " + event.getNewOwner().getUser().getAsMention(), false);

        logsChannel.sendMessageEmbeds(ownerUpdateEmbed.build()).queue();
    }

    @Override
    public void onGuildUpdateSystemChannel(GuildUpdateSystemChannelEvent event) {
        System.out.println("System Channel Updated : " + Objects.requireNonNull(event.getOldSystemChannel()).getName() + " -> " + Objects.requireNonNull(event.getNewSystemChannel()).getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder sysChannelEmbed = new EmbedBuilder();

        sysChannelEmbed.setTitle(LOGS_OBJECT.get("system_channel_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("system_channel_updated").getAsString(), event.getOldSystemChannel().getAsMention() + " -> " + event.getNewSystemChannel().getAsMention(), false);

        logsChannel.sendMessageEmbeds(sysChannelEmbed.build()).queue();
    }

    @Override
    public void onGuildVoiceGuildDeafen(GuildVoiceGuildDeafenEvent event) {
        System.out.println("User Deafened : " + event.getMember().getUser().getAsTag() + "In : " + Objects.requireNonNull(event.getVoiceState().getChannel()).getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder deafenEmbed = new EmbedBuilder();
        EmbedBuilder undeafenEmbed = new EmbedBuilder();

        deafenEmbed.setTitle(LOGS_OBJECT.get("voice_user_deafen").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("user_deafened").getAsString(), event.getMember().getUser().getAsTag() + GENERAL_OBJECT.get("in").getAsString() + event.getVoiceState().getChannel().getName(), false);

        undeafenEmbed.setTitle(LOGS_OBJECT.get("voice_user_undeafen").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("user_undeafened").getAsString(), event.getMember().getUser().getAsTag() + GENERAL_OBJECT.get("in").getAsString() + event.getVoiceState().getChannel().getName(), false);

        if (event.isGuildDeafened()) logsChannel.sendMessageEmbeds(deafenEmbed.build()).queue();
        if (!event.isGuildDeafened()) logsChannel.sendMessageEmbeds(undeafenEmbed.build()).queue();
    }

    @Override
    public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
        System.out.println("User Muted : " + event.getMember().getUser().getAsTag() + "In : " + Objects.requireNonNull(event.getVoiceState().getChannel()).getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder muteEmbed = new EmbedBuilder();
        EmbedBuilder unmuteEmbed = new EmbedBuilder();

        muteEmbed.setTitle(LOGS_OBJECT.get("voice_user_mute").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("user_muted").getAsString(), event.getMember().getUser().getAsTag() + GENERAL_OBJECT.get("in").getAsString() + event.getVoiceState().getChannel().getName(), false);

        unmuteEmbed.setTitle(LOGS_OBJECT.get("voice_user_unmute").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("user_unmuted").getAsString(), event.getMember().getUser().getAsTag() + GENERAL_OBJECT.get("in").getAsString() + event.getVoiceState().getChannel().getName(), false);

        if (event.isGuildMuted()) logsChannel.sendMessageEmbeds(muteEmbed.build()).queue();
        if (!event.isGuildMuted()) logsChannel.sendMessageEmbeds(unmuteEmbed.build()).queue();
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        System.out.println("User Moved : " + event.getMember().getUser().getAsTag() + "\n" + event.getChannelLeft() + " -> " + event.getChannelJoined());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder moveEmbed = new EmbedBuilder();

        moveEmbed.setTitle(LOGS_OBJECT.get("voice_user_move").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("user_moved").getAsString(), event.getMember().getUser().getAsTag() + "\n" + event.getChannelLeft() + " -> " + event.getChannelJoined(), false);

        logsChannel.sendMessageEmbeds(moveEmbed.build()).queue();
    }

    @Override
    public void onGuildVoiceStream(GuildVoiceStreamEvent event) {
        System.out.println("Stream Started : " + event.getMember().getUser().getAsTag() + "\nIn : " + Objects.requireNonNull(event.getVoiceState().getChannel()).getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder streamEmbed = new EmbedBuilder();
        EmbedBuilder streamEndEmbed = new EmbedBuilder();

        streamEmbed.setTitle(LOGS_OBJECT.get("stream_started").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("stream_started").getAsString(), GENERAL_OBJECT.get("by").getAsString() + event.getMember().getUser().getAsTag() + "\n" + GENERAL_OBJECT.get("in").getAsString() + event.getVoiceState().getChannel().getName(), false);

        streamEndEmbed.setTitle(LOGS_OBJECT.get("stream_ended").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("stream_ended").getAsString(), GENERAL_OBJECT.get("by").getAsString() + event.getMember().getUser().getAsTag() + "\n" + GENERAL_OBJECT.get("in").getAsString() + event.getVoiceState().getChannel().getName(), false);

        if (event.isStream()) logsChannel.sendMessageEmbeds(streamEmbed.build()).queue();
        if (!event.isStream()) logsChannel.sendMessageEmbeds(streamEndEmbed.build()).queue();
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleCreateEmbed = new EmbedBuilder();

        roleCreateEmbed.setTitle(LOGS_OBJECT.get("role_created").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_created").getAsString(), event.getRole().getName(), false);

        logsChannel.sendMessageEmbeds(roleCreateEmbed.build()).queue();

        System.out.println("Role Created : " + event.getRole().getName());
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        System.out.println("Role Deleted : " + event.getRole().getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleDelEmbed = new EmbedBuilder();

        roleDelEmbed.setTitle(LOGS_OBJECT.get("role_deleted").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_deleted").getAsString(), event.getRole().getName(), false);

        logsChannel.sendMessageEmbeds(roleDelEmbed.build()).queue();
    }

    @Override
    public void onRoleUpdateColor(RoleUpdateColorEvent event) {
        System.out.println("Role Color Updated : " + event.getRole().getName() + "\nColor : " + event.getOldColor() + " -> " + event.getNewColor());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleColorEmbed = new EmbedBuilder();

        roleColorEmbed.setTitle(LOGS_OBJECT.get("role_color_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_updated").getAsString(), event.getRole().getName() + "\n" + event.getOldColor() + " -> " + event.getNewColor(), false);

        logsChannel.sendMessageEmbeds(roleColorEmbed.build()).queue();
    }

    @Override
    public void onRoleUpdateHoisted(RoleUpdateHoistedEvent event) {
        System.out.println("Role Hoist Updated : " + event.getRole().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleHoistedEmbed = new EmbedBuilder();

        roleHoistedEmbed.setTitle(LOGS_OBJECT.get("role_hoist_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_updated").getAsString(), event.getRole().getName() + "\n" + (event.getOldValue() ? GENERAL_OBJECT.get("yes").getAsString() : GENERAL_OBJECT.get("no").getAsString()) + " -> " +
                        (event.getNewValue() ? GENERAL_OBJECT.get("yes").getAsString() : LOGS_OBJECT.get("no").getAsString()), false);

        logsChannel.sendMessageEmbeds(roleHoistedEmbed.build()).queue();
    }

    @Override
    public void onRoleUpdateMentionable(RoleUpdateMentionableEvent event) {
        System.out.println("Role Mentionable Updated : " + event.getRole().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleMentionEmbed = new EmbedBuilder();

        roleMentionEmbed.setTitle(LOGS_OBJECT.get("role_mentionable_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_updated").getAsString(), event.getRole().getName() + "\n" + (event.getOldValue() ? GENERAL_OBJECT.get("yes").getAsString() : GENERAL_OBJECT.get("no").getAsString()) + " -> " +
                        (event.getNewValue() ? GENERAL_OBJECT.get("yes").getAsString() : GENERAL_OBJECT.get("no").getAsString()), false);

        logsChannel.sendMessageEmbeds(roleMentionEmbed.build()).queue();
    }

    @Override
    public void onRoleUpdateName(RoleUpdateNameEvent event) {
        System.out.println("Role Name Updated : " + event.getRole().getName() + "\n" + event.getOldName() + " -> " + event.getNewName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder roleNameUpdate = new EmbedBuilder();

        roleNameUpdate.setTitle(LOGS_OBJECT.get("role_name_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("role_updated").getAsString(), event.getRole().getName() + "\n" + event.getOldName() + " -> " + event.getNewName(), false);

        logsChannel.sendMessageEmbeds(roleNameUpdate.build()).queue();
    }

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        System.out.println("Text Channel Created : " + event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder textCreEmbed = new EmbedBuilder();

        textCreEmbed.setTitle(LOGS_OBJECT.get("text_channel_created").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("text_channel_created").getAsString(), event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")", false);

        logsChannel.sendMessageEmbeds(textCreEmbed.build()).queue();
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        System.out.println("Text Channel Deleted : " + event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder textDelEmbed = new EmbedBuilder();

        textDelEmbed.setTitle(LOGS_OBJECT.get("text_channel_deleted").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("text_channel_deleted").getAsString(), event.getChannel().getName() + "\n(ID : " + event.getChannel().getId() + ")", false);

        logsChannel.sendMessageEmbeds(textDelEmbed.build()).queue();
    }

    @Override
    public void onTextChannelUpdateName(TextChannelUpdateNameEvent event) {
        System.out.println("Text Channel Name Updated : " + event.getChannel().getAsMention() + "\n" + event.getOldName() + " -> " + event.getNewName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder textNameUpdateEmbed = new EmbedBuilder();

        textNameUpdateEmbed.setTitle(LOGS_OBJECT.get("text_channel_name_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getAsMention() + "\n" + event.getOldName() + " -> " + event.getNewName(), false);

        logsChannel.sendMessageEmbeds(textNameUpdateEmbed.build()).queue();
    }

    @Override
    public void onTextChannelUpdateNews(TextChannelUpdateNewsEvent event) {
        System.out.println("Text Channel News Updated : " + event.getChannel().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder textNewsUpdateEmbed = new EmbedBuilder();

        textNewsUpdateEmbed.setTitle(LOGS_OBJECT.get("text_channel_news_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue(), false);

        logsChannel.sendMessageEmbeds(textNewsUpdateEmbed.build()).queue();
    }

    @Override
    public void onTextChannelUpdateNSFW(TextChannelUpdateNSFWEvent event) {
        System.out.println("Text Channel NSFW Updated : " + event.getChannel().getName() + "\n" + event.getOldValue() + " -> " + event.getNewValue());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder textNSFWUpdateEmbed = new EmbedBuilder();

        textNSFWUpdateEmbed.setTitle(LOGS_OBJECT.get("text_channel_nsfw_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" +
                        GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getName() + "\n" +
                        (Boolean.TRUE.equals(event.getOldValue()) ? GENERAL_OBJECT.get("yes").getAsString() : GENERAL_OBJECT.get("no").getAsString()) +
                        " -> " + (Boolean.TRUE.equals(event.getNewValue()) ? GENERAL_OBJECT.get("yes").getAsString() : GENERAL_OBJECT.get("no").getAsString()),
                        false);

        logsChannel.sendMessageEmbeds(textNSFWUpdateEmbed.build()).queue();
    }

    @Override
    public void onTextChannelUpdateSlowmode(TextChannelUpdateSlowmodeEvent event) {
        System.out.println("Text Channel Slowmode Updated : " + event.getChannel().getName() + "\n" + event.getOldSlowmode() + " s -> " + event.getNewSlowmode() + "s");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder textSMUpdateEmbed = new EmbedBuilder();

        textSMUpdateEmbed.setTitle(LOGS_OBJECT.get("text_channel_slowmode_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getName() + "\n" + event.getOldSlowmode() + " s -> " + event.getNewSlowmode() + "s", false);

        logsChannel.sendMessageEmbeds(textSMUpdateEmbed.build()).queue();
    }

    @Override
    public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent event) {
        System.out.println("Text Channel Topic Updated : " + event.getChannel().getName() + "\n" + event.getOldTopic() + " -> " + event.getNewTopic());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder textTopicUpdateEmbed = new EmbedBuilder();

        textTopicUpdateEmbed.setTitle(LOGS_OBJECT.get("text_channel_topic_update").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getName() + "\n" + event.getOldTopic() + " -> " + event.getNewTopic(), false);

        logsChannel.sendMessageEmbeds(textTopicUpdateEmbed.build()).queue();
    }

    /*@Override
    public void onUserActivityStart(UserActivityStartEvent event) {
        System.out.println("Activity Started : " + event.getUser().getAsTag() + " : " + event.getNewActivity().getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("activities", true);
        if (textChannels.isEmpty()) {
            Objects.requireNonNull(event.getGuild().getSystemChannel()).sendMessage("You need to create a text channel named \"activities\"").queue(
                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
        } else {
            TextChannel logsChannel = textChannels.get(0);

            final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
            assert LANG_FILE != null;
            final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
            final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
            final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

            EmbedBuilder activityStartEmbed = new EmbedBuilder();

            activityStartEmbed.setTitle(LOGS_OBJECT.get("activity_started").getAsString())
                    .setColor(Color.GREEN)
                    .setThumbnail(event.getUser().getAvatarUrl())
                    .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                    .addField(event.getUser().getName(), event.getUser().getAsTag() + " : " + event.getNewActivity().getName(), false);

            logsChannel.sendMessage(activityStartEmbed.build()).queue();
        }
    }

    @Override
    public void onUserActivityEnd(UserActivityEndEvent event) {
        System.out.println("Activity Ended : " + event.getUser().getAsTag() + " : " + event.getOldActivity().getName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("activities", true);
        if (textChannels.isEmpty()) {
            Objects.requireNonNull(event.getGuild().getSystemChannel()).sendMessage("You need to create a text channel named \"activities\"").queue(
                    delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
        } else {
            TextChannel logsChannel = textChannels.get(0);

            final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
            assert LANG_FILE != null;
            final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
            final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
            final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

            EmbedBuilder activityEndEmbed = new EmbedBuilder();

            activityEndEmbed.setTitle(LOGS_OBJECT.get("activity_ended").getAsString())
                    .setColor(Color.RED)
                    .setThumbnail(event.getUser().getAvatarUrl())
                    .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                    .addField(event.getUser().getName(), event.getUser().getAsTag() + " : " + event.getOldActivity().getName(), false);

            logsChannel.sendMessage(activityEndEmbed.build()).queue();
        }
    }*/

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        System.out.println("Voice Channel Created : " + event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder voiceCreEmbed = new EmbedBuilder();

        voiceCreEmbed.setTitle(LOGS_OBJECT.get("voice_channel_created").getAsString())
                .setColor(Color.GREEN)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("voice_channel_created").getAsString(), event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")", false);

        logsChannel.sendMessageEmbeds(voiceCreEmbed.build()).queue();
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        System.out.println("Voice Channel Deleted : " + event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder voiceDelEmbed = new EmbedBuilder();

        voiceDelEmbed.setTitle(LOGS_OBJECT.get("voice_channel_deleted").getAsString())
                .setColor(Color.RED)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("voice_channel_deleted").getAsString(), event.getChannel().getName() + "(ID : " + event.getChannel().getId() + ")", false);

        logsChannel.sendMessageEmbeds(voiceDelEmbed.build()).queue();
    }

    @Override
    public void onVoiceChannelUpdateBitrate(VoiceChannelUpdateBitrateEvent event) {
        System.out.println("Voice Channel Bitrate Updated : " + event.getChannel().getName() + "\n" + event.getOldBitrate() + " bps -> " + event.getNewBitrate() + " bps");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder voiceBitRateUpdateEmbed = new EmbedBuilder();

        voiceBitRateUpdateEmbed.setTitle(LOGS_OBJECT.get("voice_channel_bitrate_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getName() + "\n" + event.getOldBitrate() + " bps -> " + event.getNewBitrate() + " bps", false);

        logsChannel.sendMessageEmbeds(voiceBitRateUpdateEmbed.build()).queue();
    }

    @Override
    public void onVoiceChannelUpdateName(VoiceChannelUpdateNameEvent event) {
        System.out.println("Voice Channel Name Updated : " + event.getChannel().getName() + "\n" + event.getOldName() + " -> " + event.getNewName());

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder voiceNameUpdateEmbed = new EmbedBuilder();

        voiceNameUpdateEmbed.setTitle(LOGS_OBJECT.get("voice_channel_name_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getName() + "\n" + event.getOldName() + " -> " + event.getNewName(), false);

        logsChannel.sendMessageEmbeds(voiceNameUpdateEmbed.build()).queue();
    }

    @Override
    public void onVoiceChannelUpdateUserLimit(VoiceChannelUpdateUserLimitEvent event) {
        System.out.println("Voice Channel User Limit Updated : " + event.getChannel().getName() + "\n" + event.getOldUserLimit() + " user(s) -> " + event.getNewUserLimit() + " user(s)");

        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName("logs", true);
        TextChannel logsChannel = textChannels.get(0);

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject LOGS_OBJECT = LANG_OBJECT.get("logs").getAsJsonObject();

        EmbedBuilder voiceUserLimitUpdateEmbed = new EmbedBuilder();

        voiceUserLimitUpdateEmbed.setTitle(LOGS_OBJECT.get("voice_channel_user_limit_updated").getAsString())
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + GENERAL_OBJECT.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(LOGS_OBJECT.get("channel_updated").getAsString(), event.getChannel().getName() + "\n" + event.getOldUserLimit() + GENERAL_OBJECT.get("user").getAsString() + "  -> " + event.getNewUserLimit() + GENERAL_OBJECT.get("user").getAsString(), false);

        logsChannel.sendMessageEmbeds(voiceUserLimitUpdateEmbed.build()).queue();
    }

}
