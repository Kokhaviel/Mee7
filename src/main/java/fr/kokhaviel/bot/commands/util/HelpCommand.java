package fr.kokhaviel.bot.commands.util;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

import java.awt.*;

public class HelpCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Member member = event.getMember();
        final JDA jda = event.getJDA();
        final MessageChannel channel = event.getChannel();
        final User author = event.getAuthor();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "help")) {

            message.delete().queue();
            User user = member.getUser();

            if (args.length == 1) {

                EmbedBuilder helpEmbed = new EmbedBuilder();

                helpEmbed.setTitle("Help Menu");
                helpEmbed.setColor(Color.CYAN);
                helpEmbed.setAuthor("Help", null, jda.getSelfUser().getAvatarUrl());
                helpEmbed.setDescription("Display all help commands");

                helpEmbed.addField("Util Commands : ", Config.PREFIX + "help util", false);
                helpEmbed.addField("User Commands : ", Config.PREFIX + "help user", false);
                helpEmbed.addField("Moderation Commands : ", Config.PREFIX + "help moderation", false);

                channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

                if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

                ((UserImpl) user).getPrivateChannel().sendMessage(helpEmbed.build()).queue();


            }


            if (args.length == 2) {

                if (args[1].equalsIgnoreCase("server")) {

                    EmbedBuilder serverEmbed = new EmbedBuilder();

                    serverEmbed.setTitle("Help Server Commands")
                            .setColor(Color.RED)
                            .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                            .setDescription("Display all server commands")

                            .addField("Role Info Command : ", Config.PREFIX + "roleinfo <@Role>", false)
                            .addField("Server Info Command : ", Config.PREFIX + "serverinfo", false);

                    channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

                    if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

                    ((UserImpl) user).getPrivateChannel().sendMessage(serverEmbed.build()).queue();

                }

                if (args[1].equalsIgnoreCase("util")) {

                    EmbedBuilder utilEmbed = new EmbedBuilder();

                    utilEmbed.setTitle("Help Util Commands")
                            .setColor(Color.GREEN)
                            .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                            .setDescription("Display all util commands")

                            .addField("Help Command : ", Config.PREFIX + "help <category>", false)
                            .addField("About Command : ", Config.PREFIX + "about", false)
                            .addField("Ping Command : ", Config.PREFIX + "ping", false)
                            .addField("Reboot Command : ", Config.PREFIX + "reboot (Owner Only)", false)
                            .addField("Repo Command : ", Config.PREFIX + "repo", false)
                            .addField("Shutdown Command : ", Config.PREFIX + "shutdown (Owner Only)", false);

                    channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

                    if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

                    ((UserImpl) user).getPrivateChannel().sendMessage(utilEmbed.build()).queue();

                }

                if (args[1].equalsIgnoreCase("fun")) {

                    EmbedBuilder funEmbed = new EmbedBuilder();

                    funEmbed.setTitle("Help Fun Commands")
                            .setColor(Color.BLUE)
                            .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                            .setDescription("Display all fun commands")

                            .addField("Random Command : ", Config.PREFIX + "random <Number1> <Number2>", false)
                            .addField("Say Command : ", Config.PREFIX + "say <Something To Say>", false)
                            .addField("8Ball Command : ", Config.PREFIX + "8ball <Question?>", false);

                    channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

                    if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

                    ((UserImpl) user).getPrivateChannel().sendMessage(funEmbed.build()).queue();

                }

                if (args[1].equalsIgnoreCase("user")) {

                    EmbedBuilder userEmbed = new EmbedBuilder();

                    userEmbed.setTitle("Help User Commands")
                            .setColor(Color.MAGENTA)
                            .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                            .setDescription("Display all user commands")

                            .addField("Avatar Command : ", Config.PREFIX + "avatar <User ID>", false)
                            .addField("User Info Command : ", Config.PREFIX + "userinfo <User ID>", false)
                            .addField("AFK Command : ", Config.PREFIX + "afk", false);

                    channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

                    if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

                    ((UserImpl) user).getPrivateChannel().sendMessage(userEmbed.build()).queue();

                }


                if (args[1].equalsIgnoreCase("moderation")) {

                    EmbedBuilder modEmbed = new EmbedBuilder();

                    modEmbed.setTitle("Help Moderation Commands")
                            .setColor(Color.ORANGE)
                            .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                            .setDescription("Display all moderation commands")

                            .addField("Ban Command : ", Config.PREFIX + "ban <@User>", false)
                            .addField("Unban Command : ", Config.PREFIX + "unban <User ID>", false)
                            .addField("Kick Command : ", Config.PREFIX + "kick <@User>", false)
                            .addField("Clear Command : ", Config.PREFIX + "clear <NumberMessageToDel>", false)
                            .addField("Mute Command : ", Config.PREFIX + "mute <@User>", false)
                            .addField("Unmute Command : ", Config.PREFIX + "unmute <@User>", false)
                            .addField("MassKick Command : ", Config.PREFIX + "masskick <All @User>", false)
                            .addField("MassBan Command", Config.PREFIX + "massban <All @User>", false);


                    channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

                    if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

                    ((UserImpl) user).getPrivateChannel().sendMessage(modEmbed.build()).queue();

                } else if (args[1].equalsIgnoreCase("music")) {

                    EmbedBuilder musicEmbed = new EmbedBuilder();

                    musicEmbed.setTitle("Help Music Commands")
                            .setColor(Color.GREEN)
                            .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                            .setDescription("Display all music commands")

                            .addField("Join Command : ", Config.MUSIC_PREFIX + "join", false)
                            .addField("Leave Command : ", Config.MUSIC_PREFIX + "leave", false)
                            .addField("NowPlaying Command : ", Config.MUSIC_PREFIX + "nowplaying", false)
                            .addField("Pause Command : ", Config.MUSIC_PREFIX + "pause", false)
                            .addField("Play Command : ", Config.MUSIC_PREFIX + "play <Link>", false)
                            .addField("Queue Command : ", Config.MUSIC_PREFIX + "queue", false)
                            .addField("Repeat Command : ", Config.MUSIC_PREFIX + "repeat", false)
                            .addField("Skip Command", Config.MUSIC_PREFIX + "skip", false)
                            .addField("Stop Command", Config.MUSIC_PREFIX + "stop", false)
                            .addField("Volume Command", Config.MUSIC_PREFIX + "volume", false);


                    channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

                    if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

                    ((UserImpl) user).getPrivateChannel().sendMessage(musicEmbed.build()).queue();


                }
            }
        }
    }
}
