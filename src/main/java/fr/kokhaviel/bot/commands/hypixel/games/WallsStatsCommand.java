package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.walls.Walls;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class WallsStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "walls")) {

            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "walls <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final Walls walls = player.getStats().getWalls();

                    EmbedBuilder wallsEmbed = new EmbedBuilder();
                    wallsEmbed.setAuthor("Walls Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
                    wallsEmbed.setColor(Color.YELLOW);
                    wallsEmbed.setTitle(player.getUsername() + " Stats");
                    wallsEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

                    wallsEmbed.addField("Coins : ", String.valueOf(walls.getCoins()), true);

                    wallsEmbed.addBlankField(false);
                    wallsEmbed.addField("Kills : ", String.valueOf(walls.getKills()), true);
                    wallsEmbed.addField("Deaths : ", String.valueOf(walls.getDeaths()), true);
                    wallsEmbed.addField("Assists : ", String.valueOf(walls.getAssists()), true);
                    wallsEmbed.addField("KDR : ", String.valueOf(walls.getKd()), true);
                    wallsEmbed.addField("Wins :", String.valueOf(walls.getWins()), true);
                    wallsEmbed.addField("Losses : ", String.valueOf(walls.getLosses()), true);
                    wallsEmbed.addField("WinLoss : ", String.valueOf(walls.getWinLoss()), true);
                    wallsEmbed.addField("Win Percentetage : ", String.valueOf(walls.getWinPercentage()), true);

                    EmbedBuilder wallsPerksEmbed = new EmbedBuilder();
                    wallsPerksEmbed.setAuthor("Walls Perks", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
                    wallsPerksEmbed.setColor(Color.YELLOW);
                    wallsPerksEmbed.setTitle(player.getUsername() + " Stats");
                    wallsPerksEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

                    wallsPerksEmbed.addField("Boss Kills Perk : ", String.valueOf(walls.getPerks().getBossSkills()), true);
                    wallsPerksEmbed.addField("Boss Digger Perk : ", String.valueOf(walls.getPerks().getBossDigger()), true);
                    wallsPerksEmbed.addField("Boss Guardian Perk : ", String.valueOf(walls.getPerks().getBossGuardian()), true);
                    wallsPerksEmbed.addField("Scotman Perk : ", String.valueOf(walls.getPerks().getScotsman()), true);
                    wallsPerksEmbed.addField("Insane Farmer Perk : ", String.valueOf(walls.getPerks().getInsaneFarmer()), true);
                    wallsPerksEmbed.addField("Thats Hot Perk : ", String.valueOf(walls.getPerks().getThatsHot()), true);
                    wallsPerksEmbed.addField("Gold Rush Perk : ", String.valueOf(walls.getPerks().getGoldRush()), true);
                    wallsPerksEmbed.addField("Leather Worker Perk : ", String.valueOf(walls.getPerks().getLeatherWorker()), true);
                    wallsPerksEmbed.addField("Soup Drinker Perk : ", String.valueOf(walls.getPerks().getSoupDrinker()), true);
                    wallsPerksEmbed.addField("Really Shiny Perk : ", String.valueOf(walls.getPerks().getReallyShiny()), true);
                    wallsPerksEmbed.addField("Pyromaniac Perk : ", String.valueOf(walls.getPerks().getPyromaniac()), true);
                    wallsPerksEmbed.addField("Einstein Perk : ", String.valueOf(walls.getPerks().getEinstein()), true);
                    wallsPerksEmbed.addField("Skybase King Perk : ", String.valueOf(walls.getPerks().getSkybaseKing()), true);
                    wallsPerksEmbed.addField("Burn Baby Burn Perk : ", String.valueOf(walls.getPerks().getBurnBabyBurn()), true);
                    wallsPerksEmbed.addField("Very Fortunate Perk : ", String.valueOf(walls.getPerks().getVeryFortunate()), true);
                    wallsPerksEmbed.addField("Get To The Choppar Perk : ", String.valueOf(walls.getPerks().getGetToTheChoppa()), true);
                    wallsPerksEmbed.addField("Champion Perk : ", String.valueOf(walls.getPerks().getChampion()), true);


                    channel.sendMessage(wallsEmbed.build()).queue();
                    channel.sendMessage(wallsPerksEmbed.build()).queue();

                }
            }

        }

    }

}
