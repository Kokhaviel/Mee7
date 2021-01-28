package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.duels.Duels;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class DuelsStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "duels")) {

            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "duels <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {


                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final Duels duels = player.getStats().getDuels();

                    channel.sendMessage(getDuelsStats(event, player, duels).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getDuelsStats(MessageReceivedEvent event, Player player, Duels duels) {

        EmbedBuilder duelsEmbed = new EmbedBuilder();
        duelsEmbed.setAuthor("Duels Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        duelsEmbed.setColor(Color.WHITE);
        duelsEmbed.setTitle(player.getUsername() + " Stats");
        duelsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        duelsEmbed.addField("Coins : ", String.valueOf(duels.getCoins()), true);
        duelsEmbed.addField("Wins : ", String.valueOf(duels.getWins()), true);
        duelsEmbed.addField("Losses : ", String.valueOf(duels.getLosses()), true);
        duelsEmbed.addField("Deaths : ", String.valueOf(duels.getDeaths()), true);
        duelsEmbed.addField("Current WinStreak : ", String.valueOf(duels.getCurrentWinstreak()), true);
        duelsEmbed.addField("Best WinStreak : ", String.valueOf(duels.getBestOverallWinstreak()), true);
        duelsEmbed.addField("Damage Dealt : ", String.valueOf(duels.getDamageDealt()), true);
        duelsEmbed.addField("Amount Healed : ", String.valueOf(duels.getAmountHealed()), true);
        duelsEmbed.addField("Bow Shots : ", String.valueOf(duels.getBowShots()), true);
        duelsEmbed.addField("Bow Hits : ", String.valueOf(duels.getBowHits()), true);

        return duelsEmbed;
    }

}
