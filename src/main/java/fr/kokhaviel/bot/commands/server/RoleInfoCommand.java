package fr.kokhaviel.bot.commands.server;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoleInfoCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.PREFIX + "roleinfo")) {

            if(args.length < 2) {

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                event.getChannel().sendMessage("Missing Arguments : Please Use " + Config.PREFIX + "roleinfo <@Role> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if(args.length > 2) {

                event.getMessage().delete().queueAfter(2, TimeUnit.SECONDS);

                event.getChannel().sendMessage("Too Arguments : Please Use " + Config.PREFIX + "roleinfo <@Role> !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                List<Role> roleMentioned = event.getMessage().getMentionedRoles();

                Role target = roleMentioned.get(0);

                EmbedBuilder roleinfoEmbed = new EmbedBuilder();

                roleinfoEmbed.setTitle(target.getName() + " Role Info")
                        .setColor(Color.CYAN)
                        .setThumbnail(event.getGuild().getIconUrl())
                        .setAuthor("User Info", null, event.getJDA().getSelfUser().getAvatarUrl());

                roleinfoEmbed.addField("ID : ", target.getId(), false);
                roleinfoEmbed.addField("Time Create : ", String.valueOf(target.getTimeCreated()), false);
                roleinfoEmbed.addField("Color", target.getColor().toString().replace("java.awt.Color", ""), false);
                roleinfoEmbed.addField("Hoist : ", String.valueOf(target.isHoisted()), false);
                roleinfoEmbed.addField("Mentionnable", String.valueOf(target.isMentionable()), false);

                event.getChannel().sendMessage(roleinfoEmbed.build()).queue();

            }
        }

    }
}
