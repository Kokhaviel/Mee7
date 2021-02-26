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
                getHelp(rd1, rd2, rd3, event, jda, channel, author, user);
            } else {
                switch (args[1]) {
                    case "server":
                        getServerHelp(rd1, rd2, rd3, event, jda, channel, author, user);
                        break;
                    case "util":
                        getUtilHelp(rd1, rd2, rd3, event, jda, channel, author, user);
                        break;
                    case "fun":
                        getFunHelp(rd1, rd2, rd3, event, jda, channel, author, user);
                        break;
                    case "user":
                        getUserHelp(rd1, rd2, rd3, event, jda, channel, author, user);
                        break;
                    case "moderation":
                        getModerationHelp(rd1, rd2, rd3, event, jda, channel, author, user);
                        break;
                    case "music":
                        getMusicHelp(rd1, rd2, rd3, event, jda, channel, author, user);
                        break;
                    case "hypixel":
                        getHypixelHelp(rd1, rd2, rd3, jda, channel, author, user);
                        break;
                    case "funcraft":
                        getFuncraftHelp(rd1, rd2, rd3, jda, event, channel, author, user);
                        break;
                    case "wikipedia":
                        getWikipediaHelp(rd1, rd2, rd3, jda, event, channel, author, user);
                        break;
                    case "giveaways":
                        getGiveawaysHelp(rd1, rd2, rd3, jda, event, channel, author, user);
                        break;
                    case "covid":
                        getCovidHelp(rd1, rd2, rd3, jda, event, channel, author, user);
                        break;
                    case "minecraft":
                        getMinecraftHelp(rd1, rd2, rd3, jda, event, channel, author, user);
                        break;
                    default:
                        getHelp(rd1, rd2, rd3, event, jda, channel, author, user);
                        break;
                }
            }
        }
    }

    private void getHelp(int rd1, int rd2, int rd3, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder helpEmbed = new EmbedBuilder();

        helpEmbed.setTitle("Help Menu");
        helpEmbed.setColor(new Color(rd1, rd2, rd3));
        helpEmbed.setAuthor("Help", null, jda.getSelfUser().getAvatarUrl());
        helpEmbed.setDescription("Display all help commands");
        helpEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");


        helpEmbed.addField("Util Commands : ", Config.PREFIX + "help util", false);
        helpEmbed.addField("User Commands : ", Config.PREFIX + "help user", false);
        helpEmbed.addField("Server Commands : ", Config.PREFIX + "help server", false);
        helpEmbed.addField("Moderation Commands : ", Config.PREFIX + "help moderation", false);
        helpEmbed.addField("Fun Commands : ", Config.PREFIX + "help fun", false);
        helpEmbed.addField("Music Commands : ", Config.PREFIX + "help music", false);
        helpEmbed.addField("Funcraft Commands : ", Config.PREFIX + "help funcraft", false);
        helpEmbed.addField("Hypixel Commands : ", Config.PREFIX + "help hypixel", false);
        helpEmbed.addField("Wikipedia Commands : ", Config.PREFIX + "help wikipedia", false);
        helpEmbed.addField("Giveaways Commands : ", Config.PREFIX + "help giveaways", false);
        helpEmbed.addField("Covid Commands : ", Config.PREFIX + "help covid", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(helpEmbed.build()).queue();
    }

    private void getServerHelp(int rd1, int rd2, int rd3, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder serverEmbed = new EmbedBuilder();

        serverEmbed.setTitle("Help Server Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all server commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Role Info Command : ", Config.PREFIX + "roleinfo <@Role>", false)
                .addField("Server Info Command : ", Config.PREFIX + "serverinfo", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(serverEmbed.build()).queue();
    }

    private void getUtilHelp(int rd1, int rd2, int rd3, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder utilEmbed = new EmbedBuilder();

        utilEmbed.setTitle("Help Util Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all util commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")


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

    private void getFunHelp(int rd1, int rd2, int rd3, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder funEmbed = new EmbedBuilder();

        funEmbed.setTitle("Help Fun Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all fun commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Random Command : ", Config.PREFIX + "random <Number1> <Number2>", false)
                .addField("Say Command : ", Config.PREFIX + "say <Something To Say>", false)
                .addField("8Ball Command : ", Config.PREFIX + "8ball <Question?>", false)
                .addField("Reverse Command : ", Config.PREFIX + "reverse <Text>", false)
                .addField("DameDane Command : ", Config.PREFIX + "damedane", false)
                .addField("Meme Command : ", Config.PREFIX + "meme", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(funEmbed.build()).queue();
    }

    private void getUserHelp(int rd1, int rd2, int rd3, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder userEmbed = new EmbedBuilder();

        userEmbed.setTitle("Help User Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all user commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Avatar Command : ", Config.PREFIX + "avatar <User ID>", false)
                .addField("User Info Command : ", Config.PREFIX + "userinfo <User ID>", false)
                .addField("AFK Command : ", Config.PREFIX + "afk", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(userEmbed.build()).queue();
    }

    private void getModerationHelp(int rd1, int rd2, int rd3, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder modEmbed = new EmbedBuilder();

        modEmbed.setTitle("Help Moderation Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all moderation commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

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

    private void getMusicHelp(int rd1, int rd2, int rd3, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder musicEmbed = new EmbedBuilder();

        musicEmbed.setTitle("Help Music Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all music commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Join Command : ", Config.MUSIC_PREFIX + "join", false)
                .addField("Leave Command : ", Config.MUSIC_PREFIX + "leave", false)
                .addField("NowPlaying Command : ", Config.MUSIC_PREFIX + "nowplaying", false)
                .addField("Pause Command : ", Config.MUSIC_PREFIX + "pause", false)
                .addField("Play Command : ", Config.MUSIC_PREFIX + "play <Link>", false)
                .addField("Queue Command : ", Config.MUSIC_PREFIX + "queue", false)
                .addField("Repeat Command : ", Config.MUSIC_PREFIX + "repeat", false)
                .addField("Skip Command", Config.MUSIC_PREFIX + "skip", false)
                .addField("Stop Command", Config.MUSIC_PREFIX + "stop", false)
                .addField("Volume Command", Config.MUSIC_PREFIX + "volume", false)
                .addField("Forward Command : ", Config.MUSIC_PREFIX + "forward <Time in sec>", false)
                .addField("Backward Command : ", Config.MUSIC_PREFIX + "backward <Time in sec>", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(musicEmbed.build()).queue();
    }

    private void getHypixelHelp(int rd1, int rd2, int rd3, JDA jda, TextChannel channel, User author, User user) {
        EmbedBuilder hypixelEmbed = new EmbedBuilder();
        EmbedBuilder hypixel2Embed = new EmbedBuilder();

        hypixelEmbed.setTitle("Help Hypixel Games Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all hypixel commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)")

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
                .addField("Cops And Crims Stats : ", Config.HYPIXEL_PREFIX + "copsandcrims <Player>", true);

        hypixel2Embed.setTitle("Help Hypixel Games Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all hypixel commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)")

                .addField("Warlords Stats : ", Config.HYPIXEL_PREFIX + "warlords <Player>", false)
                .addField("Smash Stats : ", Config.HYPIXEL_PREFIX + "smash <Player>", false)
                .addField("Tkr Stats : ", Config.HYPIXEL_PREFIX + "turbokart <Player>", true)
                .addField("Skywars Stats : ", Config.HYPIXEL_PREFIX + "skywars <Player>", false)
                .addField("Crazy Walls Stats : ", Config.HYPIXEL_PREFIX + "crazywalls <Player>", false)
                .addField("Speed UHC Stats : ", Config.HYPIXEL_PREFIX + "speeduhc <Player>", false)
                .addField("Skyclash Stats : ", Config.HYPIXEL_PREFIX + "skyclash <Player>", false)
                .addField("Bedwars Stats : ", Config.HYPIXEL_PREFIX + "bedwars <Player>", false)
                .addField("Murder Mystery Stats : ", Config.HYPIXEL_PREFIX + "murdermystery <Player>", false)
                .addField("Build Battle Stats : ", Config.HYPIXEL_PREFIX + "buildbattle <Player>", false)
                .addField("Duels Stats : ", Config.HYPIXEL_PREFIX + "duels <Player>", false)
                .addBlankField(false)
                .addField("Bans Stats : ", Config.HYPIXEL_PREFIX + "bans", false)
                .addField("Player Stats : ", Config.HYPIXEL_PREFIX + "player <Player>", false);


        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(hypixelEmbed.build()).queue();
        ((UserImpl) user).getPrivateChannel().sendMessage(hypixel2Embed.build()).queue();
    }

    private void getFuncraftHelp(int rd1, int rd2, int rd3, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user) {
        EmbedBuilder funcraftEmbed = new EmbedBuilder();
        funcraftEmbed.setTitle("Help Funcraft Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all funcraft commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Player Stats : ", Config.FUNCRAFT_PREFIX + "stats <Player>", false)
                .addField("Hikabrain Stats : ", Config.FUNCRAFT_PREFIX + "hikabrain <Player>", false)
                .addField("Infected Stats : ", Config.FUNCRAFT_PREFIX + "infected <Player>", false)
                .addField("Octogone Stats : ", Config.FUNCRAFT_PREFIX + "octogone <Player>", false)
                .addField("PvpSmash Stats : ", Config.FUNCRAFT_PREFIX + "pvpsmash <Player>", false)
                .addField("Rush Stats : ", Config.FUNCRAFT_PREFIX + "rush <Player>", false)
                .addField("Shootcraft Stats : ", Config.FUNCRAFT_PREFIX + "shootcraft <Player>", false)
                .addField("Skywars Stats : ", Config.FUNCRAFT_PREFIX + "skywars <Player>", false)
                .addField("Survival Stats : ", Config.FUNCRAFT_PREFIX + "survival <Player>", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(funcraftEmbed.build()).queue();
    }

    private void getWikipediaHelp(int rd1, int rd2, int rd3, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user) {

        EmbedBuilder wikipediaEmbed = new EmbedBuilder();
        wikipediaEmbed.setTitle("Help Wikipedia Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all wikipedia commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Search Command : ", Config.WIKIPEDIA_PREFIX + "search <Wikipedia Page>", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(wikipediaEmbed.build()).queue();
    }

    private void getGiveawaysHelp(int rd1, int rd2, int rd3, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user) {

        EmbedBuilder giveawaysEmbed = new EmbedBuilder();
        giveawaysEmbed.setTitle("Help Giveaways Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all giveaways commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Create Command : ", Config.GIVEAWAYS_PREFIX + "create\nAnd Follow Instructions Given", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(giveawaysEmbed.build()).queue();

    }


    private void getCovidHelp(int rd1, int rd2, int rd3, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user) {

        EmbedBuilder giveawaysEmbed = new EmbedBuilder();
        giveawaysEmbed.setTitle("Help Covid Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all covid commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Covid Command : ", Config.COVID_PREFIX + "stats", false)
                .addField("Covid Country Command : ", Config.COVID_PREFIX + "stats <Country>", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(giveawaysEmbed.build()).queue();

    }

    private void getMinecraftHelp(int rd1, int rd2, int rd3, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user) {

        EmbedBuilder minecraftEmbed = new EmbedBuilder();
        minecraftEmbed.setTitle("Help Minecraft Commands")
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor("Help", null, jda.getSelfUser().getAvatarUrl())
                .setDescription("Display all minecraft commands")
                .setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128")

                .addField("Server Command : ", Config.MINECRAFT_PREFIX + "server <Server IP>", false)
                .addField("Skin Command : ", Config.MINECRAFT_PREFIX + "skin <Username>", false)
                .addField("Achievement Command : ", Config.MINECRAFT_PREFIX + "achievements\nAnd Follow Instructions Given", false);

        channel.sendMessage(author.getAsMention() + ", an help message will be send to your DM !").queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(minecraftEmbed.build()).queue();

    }
}
