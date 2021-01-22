package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.paintball.Paintball;
import zone.nora.slothpixel.player.stats.walls.Walls;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class PaintballStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "paintball")) {

            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "paintball <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final Paintball paintball = player.getStats().getPaintball();

                    channel.sendMessage(getpaintballStats(event, player, paintball).build()).queue();
                    channel.sendMessage(getPaintballPeksStats(event, player, paintball).build()).queue();

                }
            }
        }
    }

    private EmbedBuilder getpaintballStats(MessageReceivedEvent event, Player player, Paintball paintball) {

        EmbedBuilder paintballEmbed = new EmbedBuilder();


        paintballEmbed.setAuthor("Paintball Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        paintballEmbed.setColor(new Color(85, 85, 255));
        paintballEmbed.setTitle(player.getUsername() + " Stats");
        paintballEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        paintballEmbed.addField("Coins : ", String.valueOf(paintball.getCoins()), true);
        paintballEmbed.addField("Shots Fired : ", String.valueOf(paintball.getShotsFired()), true);

        paintballEmbed.addBlankField(false);
        paintballEmbed.addField("Kills : ", String.valueOf(paintball.getKills()), true);
        paintballEmbed.addField("Deaths : ", String.valueOf(paintball.getDeaths()), true);
        paintballEmbed.addField("KDR : ", String.valueOf(paintball.getKd()), true);
        paintballEmbed.addField("Wins : ", String.valueOf(paintball.getWins()), true);
        paintballEmbed.addField("KillStreak : ", String.valueOf(paintball.getKillstreaks()), true);

        return paintballEmbed;
    }


    private EmbedBuilder getPaintballPeksStats(MessageReceivedEvent event, Player player, Paintball paintball) {

        EmbedBuilder paintballPerksEmbed = new EmbedBuilder();

        paintballPerksEmbed.setAuthor("Paintball Perks", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        paintballPerksEmbed.setColor(new Color(85, 85, 255));
        paintballPerksEmbed.setTitle(player.getUsername() + " Stats");
        paintballPerksEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        paintballPerksEmbed.addField("Adrenaline Perk : ", String.valueOf(paintball.getPerks().getAdrenaline()), true);
        paintballPerksEmbed.addField("Endurance perk : ", String.valueOf(paintball.getPerks().getEndurance()), true);
        paintballPerksEmbed.addField("Fortune Perk : ", String.valueOf(paintball.getPerks().getFortune()), true);
        paintballPerksEmbed.addField("God Father Perk : ", String.valueOf(paintball.getPerks().getGodfather()), true);
        paintballPerksEmbed.addField("Super Luck perk : ", String.valueOf(paintball.getPerks().getSuperluck()), true);
        paintballPerksEmbed.addField("Transfusion perk : ", String.valueOf(paintball.getPerks().getTransfusion()), true);

        return paintballPerksEmbed;
    }

}
