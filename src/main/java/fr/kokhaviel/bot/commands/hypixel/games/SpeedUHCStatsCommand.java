package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.speeduhc.SpeedUHC;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class SpeedUHCStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "speeduhc")) {

            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "speeduhc <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final SpeedUHC speedUHC = player.getStats().getSpeedUHC();

                    channel.sendMessage(getSpeedUHCStats(event, player, speedUHC).build()).queue();
                    channel.sendMessage(getSpeedUHCSolo(event, player, speedUHC).build()).queue();
                    channel.sendMessage(getTeamsStats(event, player, speedUHC).build()).queue();
                    channel.sendMessage(getActiveKit(event, player, speedUHC).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getSpeedUHCStats(MessageReceivedEvent event, Player player, SpeedUHC speedUHC) {

        EmbedBuilder speedUHCEmbed = new EmbedBuilder();
        speedUHCEmbed.setAuthor("SpeedUHC Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        speedUHCEmbed.setColor(Color.YELLOW);
        speedUHCEmbed.setTitle(player.getUsername() + " Stats");
        speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        speedUHCEmbed.addField("Coins :", String.valueOf(speedUHC.getCoins()), true);
        speedUHCEmbed.addField("Wins : ", String.valueOf(speedUHC.getWins()), true);
        speedUHCEmbed.addField("Highest Win Streak : ", String.valueOf(speedUHC.getHighestWinstreak()), true);
        speedUHCEmbed.addField("Win Loss : ", String.valueOf(speedUHC.getWinLoss()), true);
        speedUHCEmbed.addField("Win Percentage : ", String.valueOf(speedUHC.getWinPercentage()), true);
        speedUHCEmbed.addField("Kills : ", String.valueOf(speedUHC.getKills()), true);
        speedUHCEmbed.addField("Highest Kill Streak : ", String.valueOf(speedUHC.getHighestKillstreak()), true);
        speedUHCEmbed.addField("Losses : ", String.valueOf(speedUHC.getLosses()), true);
        speedUHCEmbed.addField("Deaths : ", String.valueOf(speedUHC.getDeaths()), true);
        speedUHCEmbed.addField("Assists : ", String.valueOf(speedUHC.getAssists()), true);
        speedUHCEmbed.addField("KDR : ", String.valueOf(speedUHC.getKd()), true);
        speedUHCEmbed.addField("Games : ", String.valueOf(speedUHC.getGames()), true);
        speedUHCEmbed.addField("Enderpearl Thrown : ", String.valueOf(speedUHC.getEnderpearlsThrown()), true);
        speedUHCEmbed.addField("Arrows Shot", String.valueOf(speedUHC.getArrowsShot()), true);
        speedUHCEmbed.addField("Arrows Hit : ", String.valueOf(speedUHC.getArrowsHit()), true);
        speedUHCEmbed.addField("Blocks Placed : ", String.valueOf(speedUHC.getBlocksPlaced()), true);
        speedUHCEmbed.addField("Blocks Broken : ", String.valueOf(speedUHC.getBlocksBroken()), true);
        speedUHCEmbed.addField("Items Enchanted : ", String.valueOf(speedUHC.getItemsEnchanted()), true);
        speedUHCEmbed.addField("Salt : ", String.valueOf(speedUHC.getSalt()), true);
        speedUHCEmbed.addField("Tears : ", String.valueOf(speedUHC.getTears()), true);

        return speedUHCEmbed;
    }

    private EmbedBuilder getSpeedUHCSolo(MessageReceivedEvent event, Player player, SpeedUHC speedUHC) {
        EmbedBuilder speedUHCEmbed = new EmbedBuilder();
        speedUHCEmbed.setAuthor("SpeedUHC Solo Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        speedUHCEmbed.setColor(Color.YELLOW);
        speedUHCEmbed.setTitle(player.getUsername() + " Stats");
        speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        speedUHCEmbed.addField("Wins : ", String.valueOf(speedUHC.getGamemodes().getSolo().getWins()), true);
        speedUHCEmbed.addField("Wins Streak : ", String.valueOf(speedUHC.getGamemodes().getSolo().getWinstreak()), true);
        speedUHCEmbed.addField("Kills : ", String.valueOf(speedUHC.getGamemodes().getSolo().getKills()), true);
        speedUHCEmbed.addField("Kill Streak : ", String.valueOf(speedUHC.getGamemodes().getSolo().getKillstreaks()), true);
        speedUHCEmbed.addField("Losses : ", String.valueOf(speedUHC.getGamemodes().getSolo().getLosses()), true);
        speedUHCEmbed.addField("Deaths : ", String.valueOf(speedUHC.getGamemodes().getSolo().getDeaths()), true);
        speedUHCEmbed.addField("Assists : ", String.valueOf(speedUHC.getGamemodes().getSolo().getAssists()), true);
        speedUHCEmbed.addField("Games : ", String.valueOf(speedUHC.getGamemodes().getSolo().getGames()), true);
        speedUHCEmbed.addField("KDR : ", String.valueOf(speedUHC.getGamemodes().getSolo().getKd()), true);
        speedUHCEmbed.addField("Wins Percentage : ", String.valueOf(speedUHC.getGamemodes().getSolo().getWinPercentage()), true);

        return speedUHCEmbed;
    }

    private EmbedBuilder getTeamsStats(MessageReceivedEvent event, Player player, SpeedUHC speedUHC) {

        EmbedBuilder speedUHCEmbed = new EmbedBuilder();
        speedUHCEmbed.setAuthor("SpeedUHC Teams Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        speedUHCEmbed.setColor(Color.YELLOW);
        speedUHCEmbed.setTitle(player.getUsername() + " Stats");
        speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        speedUHCEmbed.addField("Wins : ", String.valueOf(speedUHC.getGamemodes().getTeams().getWins()), true);
        speedUHCEmbed.addField("Wins Streak : ", String.valueOf(speedUHC.getGamemodes().getTeams().getWinstreak()), true);
        speedUHCEmbed.addField("Kills : ", String.valueOf(speedUHC.getGamemodes().getTeams().getKills()), true);
        speedUHCEmbed.addField("Kill Streak : ", String.valueOf(speedUHC.getGamemodes().getTeams().getKillstreaks()), true);
        speedUHCEmbed.addField("Losses : ", String.valueOf(speedUHC.getGamemodes().getTeams().getLosses()), true);
        speedUHCEmbed.addField("Deaths : ", String.valueOf(speedUHC.getGamemodes().getTeams().getDeaths()), true);
        speedUHCEmbed.addField("Assists : ", String.valueOf(speedUHC.getGamemodes().getTeams().getAssists()), true);
        speedUHCEmbed.addField("Games : ", String.valueOf(speedUHC.getGamemodes().getTeams().getGames()), true);
        speedUHCEmbed.addField("KDR : ", String.valueOf(speedUHC.getGamemodes().getTeams().getKd()), true);
        speedUHCEmbed.addField("Wins Percentage : ", String.valueOf(speedUHC.getGamemodes().getTeams().getWinPercentage()), true);

        return speedUHCEmbed;
    }

    private EmbedBuilder getActiveKit(MessageReceivedEvent event, Player player, SpeedUHC speedUHC) {


        EmbedBuilder speedUHCEmbed = new EmbedBuilder();
        speedUHCEmbed.setAuthor("SpeedUHC Active Kit", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        speedUHCEmbed.setColor(Color.YELLOW);
        speedUHCEmbed.setTitle(player.getUsername() + " Stats");
        speedUHCEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        speedUHCEmbed.addField("Active Mastery : ", speedUHC.getMastery().getActiveMastery(), true);
        speedUHCEmbed.addField("Active Normal kit : ", speedUHC.getGamemodes().getNormal().getActiveKit(), true);
        speedUHCEmbed.addField("Active Insane Kit : ", speedUHC.getGamemodes().getInsane().getActiveKit(), true);

        return speedUHCEmbed;
    }

}
