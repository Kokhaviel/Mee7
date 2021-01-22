package fr.kokhaviel.bot.commands.server;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoleInfoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();
        final JDA jda = event.getJDA();


        if (args[0].equalsIgnoreCase(Config.PREFIX + "roleinfo")) {

            message.delete().queue();

            if (args.length < 2) {

                channel.sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "roleinfo <@Role> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (args.length > 2) {

                channel.sendMessage("Too Arguments : Please Use " + Config.PREFIX + "roleinfo <@Role> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Role> roleMentioned = message.getMentionedRoles();
                Role target = roleMentioned.get(0);

                channel.sendMessage(getRoleInfo(guild, jda, target).build()).queue();

            }
        }

    }

    private EmbedBuilder getRoleInfo(Guild guild, JDA jda, Role target) {

        EmbedBuilder roleinfoEmbed = new EmbedBuilder();

        roleinfoEmbed.setTitle(target.getName() + " Role Info")
                .setColor(Color.CYAN)
                .setThumbnail(guild.getIconUrl())
                .setAuthor("User Info", null, jda.getSelfUser().getAvatarUrl());

        roleinfoEmbed.addField("ID : ", target.getId(), false);
        roleinfoEmbed.addField("Time Create : ", String.valueOf(target.getTimeCreated()), false);
        roleinfoEmbed.addField("Color", target.getColor().toString().replace("java.awt.Color", ""), false);
        roleinfoEmbed.addField("Hoist : ", String.valueOf(target.isHoisted()), false);
        roleinfoEmbed.addField("Mentionnable", String.valueOf(target.isMentionable()), false);

        return roleinfoEmbed;
    }

}
