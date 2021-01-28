package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.buildbattle.BuildBattle;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BuildBattleStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {



        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "buildbattle")) {


            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "buildbattle <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final BuildBattle bb = player.getStats().getBuildBattle();

                    channel.sendMessage(getBuildBattleStats(event, player, bb, channel).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getBuildBattleStats(MessageReceivedEvent event, Player player, BuildBattle buildBattle, TextChannel channel) {

        EmbedBuilder buildBattleEmbed = new EmbedBuilder();
        buildBattleEmbed.setAuthor("Build Battle Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        buildBattleEmbed.setColor(Color.BLUE);
        buildBattleEmbed.setTitle(player.getUsername() + " Stats");
        buildBattleEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        buildBattleEmbed.addField("Coins : ", String.valueOf(buildBattle.getCoins()), true);
        buildBattleEmbed.addField("Score : ", String.valueOf(buildBattle.getScore()), true);
        buildBattleEmbed.addField("Wins : ", String.valueOf(buildBattle.getWins()), true);
        buildBattleEmbed.addField("Games : ", String.valueOf(buildBattle.getGamesPlayed()), true);
        buildBattleEmbed.addField("Total Votes : ", String.valueOf(buildBattle.getTotalVotes()), true);
        buildBattleEmbed.addField("Super Votes : ", String.valueOf(buildBattle.getSuperVotes()), true);

        buildBattleEmbed.addBlankField(false);

        try {

            buildBattleEmbed.addField("Selected Hat : ", buildBattle.getSelectedHat(), true);
            buildBattleEmbed.addField("Selected Victory Dance : ", buildBattle.getSelectedVictoryDance(), true);
            buildBattleEmbed.addField("Selected Suit : ", buildBattle.getSelectedSuit(), true);
            buildBattleEmbed.addField("Selected Movement Trail : ", buildBattle.getSelectedMovementTrail(), true);
            buildBattleEmbed.addField("Selected Backdrop : ", buildBattle.getSelectedBackdrop(), true);

            //Not working for the moment ...

        } catch (IllegalArgumentException e) {

            channel.sendMessage("An exception occurred (This will be fixed later) :(").queue();
            e.printStackTrace();

        }

        return buildBattleEmbed;
    }

}
