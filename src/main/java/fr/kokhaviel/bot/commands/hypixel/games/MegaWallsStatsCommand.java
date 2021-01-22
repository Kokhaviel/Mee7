package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.megawalls.MegaWalls;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class MegaWallsStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "megawalls")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "megawalls <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {


                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {


                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final MegaWalls megawalls = player.getStats().getMegaWalls();

                    channel.sendMessage(getMegaWallsStats(event, player, megawalls).build()).queue();

                }
            }
        }
    }

    private EmbedBuilder getMegaWallsStats(MessageReceivedEvent event, Player player, MegaWalls megawalls) {

        EmbedBuilder megaWallsEmbed = new EmbedBuilder();

        megaWallsEmbed.setAuthor("Mega Walls Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        megaWallsEmbed.setColor(new Color(111, 78, 104));
        megaWallsEmbed.setTitle(player.getUsername() + " Stats");
        megaWallsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        megaWallsEmbed.addField("Coins : ", String.valueOf(megawalls.getCoins()), true);

        return megaWallsEmbed;
    }

}
