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

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

import java.awt.*;
import java.io.File;
import java.util.Random;

import static java.lang.String.format;

public class HelpCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        JsonObject prefix = JsonUtilities.readJson(new File("guild_settings.json"))
                .getAsJsonObject().get(event.getGuild().getId())
                .getAsJsonObject();

        final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
        assert LANG_FILE != null;
        final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
        final JsonObject GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
        final JsonObject COMMANDS_OBJECT = LANG_OBJECT.get("commands").getAsJsonObject();
        final JsonObject HELP_OBJECT = LANG_OBJECT.get("help").getAsJsonObject();

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Member member = event.getMember();
        final JDA jda = event.getJDA();
        final TextChannel channel = (TextChannel) event.getChannel();
        final User author = event.getAuthor();

        if (args[0].equalsIgnoreCase(prefix + "help")) {

            message.delete().queue();
            assert member != null;
            final User user = member.getUser();
            final int rd1 = new Random().nextInt(255);
            final int rd2 = new Random().nextInt(255);
            final int rd3 = new Random().nextInt(255);

            if(args.length == 1) {
                getHelp(rd1, rd2, rd3, prefix.get("prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
            } else {
                switch (args[1]) {
                    case "server":
                        getServerHelp(rd1, rd2, rd3, prefix.get("prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "util":
                        getUtilHelp(rd1, rd2, rd3, prefix.get("prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "fun":
                        getFunHelp(rd1, rd2, rd3, prefix.get("prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "user":
                        getUserHelp(rd1, rd2, rd3, prefix.get("prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "moderation":
                        getModerationHelp(rd1, rd2, rd3, prefix.get("prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "music":
                        getMusicHelp(rd1, rd2, rd3, prefix.get("music_prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "hypixel":
                        getHypixelHelp(rd1, rd2, rd3, prefix.get("hypixel_prefix").getAsString(), jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "funcraft":
                        getFuncraftHelp(rd1, rd2, rd3, prefix.get("funcraft_prefix").getAsString(), jda, event, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "wikipedia":
                        getWikipediaHelp(rd1, rd2, rd3, prefix.get("wikipedia_prefix").getAsString(), jda, event, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "giveaways":
                        getGiveawaysHelp(rd1, rd2, rd3, prefix.get("giveaways_prefix").getAsString(), jda, event, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "covid":
                        getCovidHelp(rd1, rd2, rd3, prefix.get("covid_prefix").getAsString(), jda, event, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    case "minecraft":
                        getMinecraftHelp(rd1, rd2, rd3, prefix.get("minecraft_prefix").getAsString(), jda, event, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                    default:
                        getHelp(rd1, rd2, rd3, prefix.get("prefix").getAsString(), event, jda, channel, author, user, GENERAL_OBJECT, COMMANDS_OBJECT, HELP_OBJECT);
                        break;
                }
            }
        }
    }

    private void getHelp(int rd1, int rd2, int rd3, String prefix, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder helpEmbed = new EmbedBuilder();

        helpEmbed.setTitle(helpObject.get("help_menu").getAsString());
        helpEmbed.setColor(new Color(rd1, rd2, rd3));
        helpEmbed.setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl());
        helpEmbed.setDescription(format("%s help %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()));
        helpEmbed.setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR);


        helpEmbed.addField(format("Util %s : ", commandObject.get("command").getAsString()), prefix + "help util", false);
        helpEmbed.addField(format("User %s : ", commandObject.get("command").getAsString()), prefix + "help user", false);
        helpEmbed.addField(format("Server %s : ", commandObject.get("command").getAsString()), prefix + "help server", false);
        helpEmbed.addField(format("Moderation %s : ", commandObject.get("command").getAsString()), prefix + "help moderation", false);
        helpEmbed.addField(format("Fun %s : ", commandObject.get("command").getAsString()), prefix + "help fun", false);
        helpEmbed.addField(format("Music %s : ", commandObject.get("command").getAsString()), prefix + "help music", false);
        helpEmbed.addField(format("Funcraft %s : ", commandObject.get("command").getAsString()), prefix + "help funcraft", false);
        helpEmbed.addField(format("Hypixel %s : ", commandObject.get("command").getAsString()), prefix + "help hypixel", false);
        helpEmbed.addField(format("Wikipedia %s : ", commandObject.get("command").getAsString()), prefix + "help wikipedia", false);
        helpEmbed.addField(format("Giveaways %s : ", commandObject.get("command").getAsString()), prefix + "help giveaways", false);
        helpEmbed.addField(format("Covid %s : ", commandObject.get("command").getAsString()), prefix + "help covid", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(helpEmbed.build()).queue();
    }

    private void getServerHelp(int rd1, int rd2, int rd3, String prefix, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder serverEmbed = new EmbedBuilder();

        serverEmbed.setTitle(format("%s Server %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s server %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Role Info %s : ", commandObject.get("command").getAsString()), prefix + "roleinfo <@Role>", false)
                .addField(format("Server Info %s : ", commandObject.get("command").getAsString()), prefix + "serverinfo", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(serverEmbed.build()).queue();
    }

    private void getUtilHelp(int rd1, int rd2, int rd3, String prefix, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder utilEmbed = new EmbedBuilder();

        utilEmbed.setTitle(format("%s Util %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s util %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)


                .addField(format("Help %s : ", commandObject.get("command").getAsString()), prefix + "help <category>", false)
                .addField(format("About %s : ", commandObject.get("command").getAsString()), prefix + "about", false)
                .addField(format("Ping %s : ", commandObject.get("command").getAsString()), prefix + "ping", false)
                .addField(format("Reboot %s (%s) : ", commandObject.get("command").getAsString(), helpObject.get("owner_only").getAsString()), prefix + "reboot", false)
                .addField(format("Repo %s : ", commandObject.get("command").getAsString()), prefix + "repo", false)
                .addField(format("Shutdown %s (%s) : ", commandObject.get("command").getAsString(), helpObject.get("owner_only").getAsString()), prefix + "shutdown", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(utilEmbed.build()).queue();
    }

    private void getFunHelp(int rd1, int rd2, int rd3, String prefix, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder funEmbed = new EmbedBuilder();

        funEmbed.setTitle(format("%s Fun %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s fun %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Random %s : ", commandObject.get("command").getAsString()), prefix + "random <Number1> <Number2>", false)
                .addField(format("Say %s : ", commandObject.get("command").getAsString()), prefix + "say <Something To Say>", false)
                .addField(format("8Ball %s : ", commandObject.get("command").getAsString()), prefix + "8ball <Question?>", false)
                .addField(format("Reverse %s : ", commandObject.get("commands").getAsString()), prefix + "reverse <Text>", false)
                .addField(format("Damedane %s : ", commandObject.get("command").getAsString()), prefix + "damedane", false)
                .addField(format("Meme %s : ", commandObject.get("command").getAsString()), prefix + "meme", false)
                .addField(format("Love %s : ", commandObject.get("command").getAsString()), prefix + "love <People 1> <People 2>", false)
                .addField(format("Joke %s : ", commandObject.get("command").getAsString()), prefix + "joke", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(funEmbed.build()).queue();
    }

    private void getUserHelp(int rd1, int rd2, int rd3, String prefix, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder userEmbed = new EmbedBuilder();

        userEmbed.setTitle(format("%s User %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s user %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Avatar %s : ", commandObject.get("command").getAsString()), prefix + "avatar <User ID>", false)
                .addField(format("User Info %s : ", commandObject.get("command").getAsString()), prefix + "userinfo <User ID>", false)
                .addField(format("AFK %s : ", commandObject.get("command").getAsString()), prefix + "afk", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(userEmbed.build()).queue();
    }

    private void getModerationHelp(int rd1, int rd2, int rd3, String prefix, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder modEmbed = new EmbedBuilder();

        modEmbed.setTitle(format("%s Moderation %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s moderation %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Ban %s : ", commandObject.get("command").getAsString()), prefix + "ban <@User>", false)
                .addField(format("Unban %s : ", commandObject.get("command").getAsString()), prefix + "unban <User ID>", false)
                .addField(format("Kick %s : ", commandObject.get("command").getAsString()), prefix + "kick <@User>", false)
                .addField(format("Clear %s : ", commandObject.get("command").getAsString()), prefix + "clear <NumberMessageToDel>", false)
                .addField(format("Mute %s : ", commandObject.get("command").getAsString()), prefix + "mute <@User>", false)
                .addField(format("Unmute %s : ", commandObject.get("command").getAsString()), prefix + "unmute <@User>", false)
                .addField(format("MassKick %s : ", commandObject.get("command").getAsString()), prefix + "masskick <All @User>", false)
                .addField(format("MassBan %s : ", commandObject.get("command").getAsString()), prefix + "massban <All @User>", false);


        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(modEmbed.build()).queue();
    }

    private void getMusicHelp(int rd1, int rd2, int rd3, String prefix, MessageReceivedEvent event, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder musicEmbed = new EmbedBuilder();

        musicEmbed.setTitle(format("%s Music %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s music %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Join %s : ", commandObject.get("command").getAsString()), prefix + "join", false)
                .addField(format("Leave %s : ", commandObject.get("command").getAsString()), prefix + "leave", false)
                .addField(format("NowPlaying %s : ", commandObject.get("command").getAsString()), prefix + "nowplaying", false)
                .addField(format("Pause %s : ", commandObject.get("command").getAsString()), prefix + "pause", false)
                .addField(format("Play %s : ", commandObject.get("command").getAsString()), prefix + "play <Link>", false)
                .addField(format("Queue %s : ", commandObject.get("command").getAsString()), prefix + "queue", false)
                .addField(format("Repeat %s : ", commandObject.get("command").getAsString()), prefix + "repeat", false)
                .addField(format("Skip %s : ", commandObject.get("command").getAsString()), prefix + "skip", false)
                .addField(format("Stop %s : ", commandObject.get("command").getAsString()), prefix + "stop", false)
                .addField(format("Volume %s : ", commandObject.get("command").getAsString()), prefix + "volume", false)
                .addField(format("Forward %s : ", commandObject.get("command").getAsString()), prefix + "forward <Time in sec>", false)
                .addField(format("Backward %s : ", commandObject.get("command").getAsString()), prefix + "backward <Time in sec>", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(musicEmbed.build()).queue();
    }

    private void getHypixelHelp(int rd1, int rd2, int rd3, String prefix, JDA jda, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder hypixelEmbed = new EmbedBuilder();
        EmbedBuilder hypixel2Embed = new EmbedBuilder();

        hypixelEmbed.setTitle(format("%s Hypixel %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s hypixel %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)", Config.DEVELOPER_AVATAR)

                .addField(format("QuakeCraft %s : ", commandObject.get("stats").getAsString()), prefix + "quakecraft <Player>", false)
                .addField(format("Walls %s : ", commandObject.get("stats").getAsString()), prefix + "walls <Player>", false)
                .addField(format("Blitz %s : ", commandObject.get("stats").getAsString()), prefix + "blitz <Player>", false)
                .addField(format("Blitz Kit %s : ", commandObject.get("stats").getAsString()), prefix + "blitzstats kitstats <Player>", false)
                .addField(format("Blitz Kit %s : ", commandObject.get("level").getAsString()), prefix + "blitzstats kitlevel <Player>", false)
                .addField(format("Tnt Games %s : ", commandObject.get("stats").getAsString()), prefix + "tnt <Player>", false)
                .addField(format("VampireZ %s : ", commandObject.get("stats").getAsString()), prefix + "vampirez <Player>", false)
                .addField(format("MegaWalls %s : ", commandObject.get("stats").getAsString()), prefix + "megawalls <Player>", false)
                .addField(format("Arcade %s : ", commandObject.get("stats").getAsString()), prefix + "arcade <Player>", false)
                .addField(format("Arena %s : ", commandObject.get("stats").getAsString()), prefix + "arena <Player>", false)
                .addField(format("UHC %s : ", commandObject.get("stats").getAsString()), prefix + "uhc <Player>", false)
                .addField(format("Cops And Crims %s : ", commandObject.get("stats").getAsString()), prefix + "copsandcrims <Player>", true);

        hypixel2Embed.setTitle(format("%s Hypixel %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s hypixel %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)", Config.DEVELOPER_AVATAR)

                .addField(format("Warlords %s : ", commandObject.get("stats").getAsString()), prefix + "warlords <Player>", false)
                .addField(format("Smash %s : ", commandObject.get("stats").getAsString()), prefix + "smash <Player>", false)
                .addField(format("Tkr %s : ", commandObject.get("stats").getAsString()), prefix + "turbokart <Player>", true)
                .addField(format("Skywars %s : ", commandObject.get("stats").getAsString()), prefix + "skywars <Player>", false)
                .addField(format("Crazy Walls %s : ", commandObject.get("stats").getAsString()), prefix + "crazywalls <Player>", false)
                .addField(format("Speed UHC %s : ", commandObject.get("stats").getAsString()), prefix + "speeduhc <Player>", false)
                .addField(format("Skyclash %s : ", commandObject.get("stats").getAsString()), prefix + "skyclash <Player>", false)
                .addField(format("Bedwars %s : ", commandObject.get("stats").getAsString()), prefix + "bedwars <Player>", false)
                .addField(format("Murder Mystery %s : ", commandObject.get("stats").getAsString()), prefix + "murdermystery <Player>", false)
                .addField(format("Build Battle %s : ", commandObject.get("stats").getAsString()), prefix + "buildbattle <Player>", false)
                .addField(format("Duels %s : ", commandObject.get("stats").getAsString()), prefix + "duels <Player>", false)
                .addBlankField(false)
                .addField(format("Bans %s : ", commandObject.get("stats").getAsString()), prefix + "bans", false)
                .addField(format("Player %s : ", commandObject.get("stats").getAsString()), prefix + "player <Player>", false);


        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(hypixelEmbed.build()).queue();
        ((UserImpl) user).getPrivateChannel().sendMessage(hypixel2Embed.build()).queue();
    }

    private void getFuncraftHelp(int rd1, int rd2, int rd3, String prefix, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {
        EmbedBuilder funcraftEmbed = new EmbedBuilder();
        funcraftEmbed.setTitle(format("%s Funcraft %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s funcraft %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Player %s : ", commandObject.get("stats").getAsString()), prefix + "stats <Player>", false)
                .addField(format("Hikabrain %s : ", commandObject.get("stats").getAsString()), prefix + "hikabrain <Player>", false)
                .addField(format("Infected %s : ", commandObject.get("stats").getAsString()), prefix + "infected <Player>", false)
                .addField(format("Octogone %s : ", commandObject.get("stats").getAsString()), prefix + "octogone <Player>", false)
                .addField(format("PvpSmash %s : ", commandObject.get("stats").getAsString()), prefix + "pvpsmash <Player>", false)
                .addField(format("Rush %s : ", commandObject.get("stats").getAsString()), prefix + "rush <Player>", false)
                .addField(format("Shootcraft %s : ", commandObject.get("stats").getAsString()), prefix + "shootcraft <Player>", false)
                .addField(format("Skywars %s : ", commandObject.get("stats").getAsString()), prefix + "skywars <Player>", false)
                .addField(format("Survival %s : ", commandObject.get("stats").getAsString()), prefix + "survival <Player>", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(funcraftEmbed.build()).queue();
    }

    private void getWikipediaHelp(int rd1, int rd2, int rd3, String prefix, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {

        EmbedBuilder wikipediaEmbed = new EmbedBuilder();
        wikipediaEmbed.setTitle(format("%s Wikipedia %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s wikipedia %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Search %s : ", commandObject.get("command").getAsString()), prefix + "search <Wikipedia Page>", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(wikipediaEmbed.build()).queue();
    }

    private void getGiveawaysHelp(int rd1, int rd2, int rd3, String prefix, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {

        EmbedBuilder giveawaysEmbed = new EmbedBuilder();
        giveawaysEmbed.setTitle(format("%s Giveaways %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s giveaways %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Create %s : ", commandObject.get("command").getAsString()), prefix + "create\n" + helpObject.get("follow_instructions").getAsString(), false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(giveawaysEmbed.build()).queue();

    }

    private void getCovidHelp(int rd1, int rd2, int rd3, String prefix, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {

        EmbedBuilder covidEmbed = new EmbedBuilder();
        covidEmbed.setTitle(format("%s Covid %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s covid %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by").getAsString() + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Covid %s : ", commandObject.get("command").getAsString()), prefix + "stats", false)
                .addField(format("Covid Country %s : ", commandObject.get("command").getAsString()), prefix + "stats <Country>", false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(covidEmbed.build()).queue();

    }

    private void getMinecraftHelp(int rd1, int rd2, int rd3, String prefix, JDA jda, MessageReceivedEvent event, TextChannel channel, User author, User user, JsonObject generalObject, JsonObject commandObject, JsonObject helpObject) {

        EmbedBuilder minecraftEmbed = new EmbedBuilder();
        minecraftEmbed.setTitle(format("%s Minecraft %s", helpObject.get("help").getAsString(), commandObject.get("commands").getAsString()))
                .setColor(new Color(rd1, rd2, rd3))
                .setAuthor(helpObject.get("help").getAsString(), null, jda.getSelfUser().getAvatarUrl())
                .setDescription(format("%s minecraft %s", helpObject.get("display_help").getAsString(), commandObject.get("commands").getAsString()))
                .setFooter(generalObject.get("developed_by") + Config.DEVELOPER_TAG + "\n" + generalObject.get("action_generated_on").getAsString() + event.getGuild().getName(), Config.DEVELOPER_AVATAR)

                .addField(format("Server %s : ", commandObject.get("command").getAsString()), prefix + "server <Server IP>", false)
                .addField(format("Skin %s : ", commandObject.get("command").getAsString()), prefix + "skin <Username>", false)
                .addField(format("Achievement %s : ", commandObject.get("command").getAsString()), prefix + "achievements\n" + helpObject.get("follow_instructions").getAsString(), false);

        channel.sendMessage(format("%s, %s", author.getAsMention(), helpObject.get("success_guild_message").getAsString())).queue();
        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        ((UserImpl) user).getPrivateChannel().sendMessage(minecraftEmbed.build()).queue();

    }
}
