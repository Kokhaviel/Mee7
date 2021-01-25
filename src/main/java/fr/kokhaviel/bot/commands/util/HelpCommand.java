package fr.kokhaviel.bot.commands.util;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

import java.awt.*;
import java.util.Random;

public class HelpCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Member member = event.getMember();
        final JDA jda = event.getJDA();
        final TextChannel channel = (TextChannel) event.getChannel();
        final User author = event.getAuthor();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "help")) {

            message.delete().queue();
            final User user = member.getUser();
            final int rd1 = new Random().nextInt(255);
            final int rd2 = new Random().nextInt(255);
            final int rd3 = new Random().nextInt(255);

            if(args.length == 1) {

                getHelp(rd1, rd2, rd3, jda, channel, author, user);

            } else {
                switch (args[1]) {

                    case "server":
                        getServerHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    case "util":
                        getUtilHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    case "fun":
                        getFunHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    case "user":
                        getUserHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    case "moderation":
                        getModerationHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    case "music":
                        getMusicHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    case "hypixel":
                        getHypixelHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    default:
                        getHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                }
            }
        }
    }

    private void getHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder helpEmbed = new EmbedBuilder();

        helpEmbed.setTitle("Help Menu");
        helpEmbed.setColor(new Color(rd1, rd2, rd3));
        helpEmbed.setAuthor("Help", null, jda.getSelfUser().getAvatarUrl());
        helpEmbed.setDescription("Display all help commands");

        helpEmbed.addField("Util Commands : ", Config.PREFIX + "help util", false);
        helpEmbed.addField("User Commands : ", Config.PREFIX + "help user", false);
        helpEmbed.addField("Server Commands : ", Config.PREFIX + "help server", false);
        helpEmbed.addField("Moderation Commands : ", Config.PREFIX + "help moderation", false);
        helpEmbed.addField("Fun Commands : ", Config.PREFIX + "help fun", false);
        helpEmbed.addField("Music Commands : ", Config.PREFIX + "help music", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

        ((UserImpl) user).getPrivateChannel().sendMessage(helpEmbed.build()).queue();
    }

    private void getServerHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder serverEmbed = new EmbedBuilder();

        serverEmbed.setTitle("Help Server Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all server commands")

                .addField("Role Info Command : ", Config.PREFIX + "roleinfo <@Role>", false)
                .addField("Server Info Command : ", Config.PREFIX + "serverinfo", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

        ((UserImpl) user).getPrivateChannel().sendMessage(serverEmbed.build()).queue();


    }

    private void getUtilHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder utilEmbed = new EmbedBuilder();

        utilEmbed.setTitle("Help Util Commands")
                .setColor(new Color(rd1, rd2, rd3))
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

    private void getFunHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder funEmbed = new EmbedBuilder();

        funEmbed.setTitle("Help Fun Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all fun commands")

                .addField("Random Command : ", Config.PREFIX + "random <Number1> <Number2>", false)
                .addField("Say Command : ", Config.PREFIX + "say <Something To Say>", false)
                .addField("8Ball Command : ", Config.PREFIX + "8ball <Question?>", false)
                .addField("Reverse Command : ", Config.PREFIX + "reverse <Text>", false)
                .addField("DameDane Command", Config.PREFIX + "damedane", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

        ((UserImpl) user).getPrivateChannel().sendMessage(funEmbed.build()).queue();
    }

    private void getUserHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder userEmbed = new EmbedBuilder();

        userEmbed.setTitle("Help User Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all user commands")

                .addField("Avatar Command : ", Config.PREFIX + "avatar <User ID>", false)
                .addField("User Info Command : ", Config.PREFIX + "userinfo <User ID>", false)
                .addField("AFK Command : ", Config.PREFIX + "afk", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

        ((UserImpl) user).getPrivateChannel().sendMessage(userEmbed.build()).queue();
    }

    private void getModerationHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder modEmbed = new EmbedBuilder();

        modEmbed.setTitle("Help Moderation Commands")
                .setColor(new Color(rd1, rd2, rd3))
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
    }

    private void getMusicHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder musicEmbed = new EmbedBuilder();

        musicEmbed.setTitle("Help Music Commands")
                .setColor(new Color(rd1, rd2, rd3))
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

    private void getHypixelHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {

        EmbedBuilder hypixelEmbed = new EmbedBuilder();

        hypixelEmbed.setTitle("Help Music Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all hypixel commands")

                .addField("Quakecraft Stats : ", Config.HYPIXEL_PREFIX + "quakecraft <Player>", false)
                .addField("Walls Stats : ", Config.HYPIXEL_PREFIX + "walls <Player>", false)
                .addField("Blitz Stats : ", Config.HYPIXEL_PREFIX + "blitz <Player>", false)
                .addField("Blitz Kit Stats : ", Config.HYPIXEL_PREFIX + "blitzstats kitstats <Player>", false)
                .addField("Blitz Kit Level : ", Config.HYPIXEL_PREFIX + "blitzstats kitlevel <Player>", false)
                .addField("Tnt Games Stats : ", Config.HYPIXEL_PREFIX + "tnt <Player>", false)
                .addField("VampireZ Stats : ", Config.HYPIXEL_PREFIX + "vampirez <Player>", false)
                .addField("MegaWalls Stats : ", Config.HYPIXEL_PREFIX + "megawalls <Player>", false)
                .addField("Arcade Stats : ", Config.HYPIXEL_PREFIX + "arcade <Player>", false)
                .addField("Arena Stats : ", Config.HYPIXEL_PREFIX + "arena <Player>", false)
                .addField("UHC Stats : ", Config.HYPIXEL_PREFIX + "uhc <Player>", false)
                .addField("Cops And Crims Stats : ", Config.HYPIXEL_PREFIX + "copsandcrims <Player>", true)
                .addField("Warlords Stats : ", Config.HYPIXEL_PREFIX + "warlords <Player>", false)
                .addField("Smash Stats : ", Config.HYPIXEL_PREFIX + "smash <Player>", false)
                .addField("Tkr Stats : ", Config.HYPIXEL_PREFIX + "turbokart <Player>", true)
                .addField("Skywars Stats : ", Config.HYPIXEL_PREFIX + "skywars <Player>", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();

        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

        ((UserImpl) user).getPrivateChannel().sendMessage(hypixelEmbed.build()).queue();

    }

}
