package fr.kokhaviel.bot.commands.hypixel.games.blitz;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.blitz.Blitz;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BlitzStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "blitz")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "blitz <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final Blitz blitz = player.getStats().getBlitz();

                    channel.sendMessage(getBlitzStats(event, player, blitz).build()).queue();

                }
            }
        }
    }

    private EmbedBuilder getBlitzStats(MessageReceivedEvent event, Player player, Blitz blitz) {

        EmbedBuilder blitzEmbed = new EmbedBuilder();

        blitzEmbed.setAuthor("Blitz Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        blitzEmbed.setColor(new Color(73, 240, 255));
        blitzEmbed.setTitle(player.getUsername() + " Stats");
        blitzEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        blitzEmbed.addField("Coins : ", String.valueOf(blitz.getCoins()), true);
        blitzEmbed.addField("Games Played : ", String.valueOf(blitz.getGamesPlayed()), true);
        blitzEmbed.addField("Time Played : ", blitz.getTimePlayed() / 60 + " minutes", true);

        blitzEmbed.addBlankField(false);
        blitzEmbed.addField("Kills : ", String.valueOf(blitz.getKills()), true);
        blitzEmbed.addField("Deaths : ", String.valueOf(blitz.getDeaths()), true);
        blitzEmbed.addField("KDR : ", String.valueOf(blitz.getKD()), true);
        blitzEmbed.addField("Wins : ", String.valueOf(blitz.getWins()), true);
        blitzEmbed.addField("Team Wins : ", String.valueOf(blitz.getTeamWins()), true);
        blitzEmbed.addField("Win Loss : ", String.valueOf(blitz.getWinLoss()), true);
        blitzEmbed.addField("Win percentage : ", String.valueOf(blitz.getWinPercentage()), true);

        blitzEmbed.addBlankField(false);
        blitzEmbed.addField("Damage : ", String.valueOf(blitz.getDamage()), true);
        blitzEmbed.addField("Damage Taken : ", String.valueOf(blitz.getDamageTaken()), true);
        blitzEmbed.addField("Arrows Fired : ", String.valueOf(blitz.getArrowsFired()), true);
        blitzEmbed.addField("Arrows Hit : ", String.valueOf(blitz.getArrowsHit()), true);
        blitzEmbed.addField("Chests Opened : ", String.valueOf(blitz.getChestsOpened()), true);
        blitzEmbed.addField("Mob Spawned : ", String.valueOf(blitz.getMobsSpawned()), true);
        blitzEmbed.addField("Potions Drunk : ", String.valueOf(blitz.getPotionsDrunk()), true);
        blitzEmbed.addField("Potions Thrown : ", String.valueOf(blitz.getPotionsThrown()), true);

        return blitzEmbed;
    }

}
