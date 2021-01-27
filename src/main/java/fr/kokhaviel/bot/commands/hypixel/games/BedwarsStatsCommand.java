package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.bedwars.BedWars;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BedwarsStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "bedwars")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "bedwars <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final BedWars bedwars = player.getStats().getBedWars();

                    channel.sendMessage(getBedwarsStats(event, player, bedwars).build()).queue();
                    channel.sendMessage(getRessourcesBW(event, player, bedwars).build()).queue();
                    channel.sendMessage(getGamemodesStats(event, player, bedwars).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getBedwarsStats(MessageReceivedEvent event, Player player, BedWars bedwars) {

        EmbedBuilder bedwarsEmbed = new EmbedBuilder();
        bedwarsEmbed.setAuthor("Bedwars Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        bedwarsEmbed.setColor(Color.RED);
        bedwarsEmbed.setTitle(player.getUsername() + " Stats");
        bedwarsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        bedwarsEmbed.addField("Coins : ", String.valueOf(bedwars.getCoins()), true);
        bedwarsEmbed.addField("Level : ", String.valueOf(bedwars.getLevel()), true);
        bedwarsEmbed.addField("Experience : ", String.valueOf(bedwars.getExp()), true);
        bedwarsEmbed.addField("Wins : ", String.valueOf(bedwars.getWins()), true);
        bedwarsEmbed.addField("Losses : ", String.valueOf(bedwars.getLosses()), true);
        bedwarsEmbed.addField("Games : ", String.valueOf(bedwars.getGamesPlayed()), true);
        bedwarsEmbed.addField("Win Loss : ", String.valueOf(bedwars.getWL()), true);
        bedwarsEmbed.addField("Win Streak : ", String.valueOf(bedwars.getWinstreak()), true);
        bedwarsEmbed.addField("Kills : ", String.valueOf(bedwars.getKills()), true);
        bedwarsEmbed.addField("Void Kills : ", String.valueOf(bedwars.getVoidKills()), true);
        bedwarsEmbed.addField("Final Kills : ", String.valueOf(bedwars.getFinalKills()), true);
        bedwarsEmbed.addField("Beds Broken : ", String.valueOf(bedwars.getBedsBroken()), true);
        bedwarsEmbed.addField("Deaths : ", String.valueOf(bedwars.getDeaths()), true);
        bedwarsEmbed.addField("Void Deaths : ", String.valueOf(bedwars.getVoidDeaths()), true);
        bedwarsEmbed.addField("Final Deaths : ", String.valueOf(bedwars.getFinalDeaths()), true);
        bedwarsEmbed.addField("Beds Lost : ", String.valueOf(bedwars.getBedsLost()), true);
        bedwarsEmbed.addField("KDR : ", String.valueOf(bedwars.getKD()), true);

        return bedwarsEmbed;
    }

    private EmbedBuilder getRessourcesBW(MessageReceivedEvent event, Player player, BedWars bedwars) {

        EmbedBuilder bedwarsEmbed = new EmbedBuilder();
        bedwarsEmbed.setAuthor("Bedwars Resources Collected", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        bedwarsEmbed.setColor(Color.RED);
        bedwarsEmbed.setTitle(player.getUsername() + " Stats");
        bedwarsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        bedwarsEmbed.addField("Iron Collected : ", String.valueOf(bedwars.getResourcesCollected().getIron()), false);
        bedwarsEmbed.addField("Gold Collected : ", String.valueOf(bedwars.getResourcesCollected().getGold()), false);
        bedwarsEmbed.addField("Diamond Collected : ", String.valueOf(bedwars.getResourcesCollected().getDiamond()), false);
        bedwarsEmbed.addField("Emerald Collected : ", String.valueOf(bedwars.getResourcesCollected().getEmerald()), false);

        return bedwarsEmbed;
    }

    private EmbedBuilder getGamemodesStats(MessageReceivedEvent event, Player player, BedWars bedwars) {

        EmbedBuilder bedwarsEmbed = new EmbedBuilder();
        bedwarsEmbed.setAuthor("Bedwars Gamemodes Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        bedwarsEmbed.setColor(Color.RED);
        bedwarsEmbed.setTitle(player.getUsername() + " Stats");
        bedwarsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        bedwarsEmbed.addField("Solo Wins : ", String.valueOf(bedwars.getGamemodes().getSolo().getWins()), true);
        bedwarsEmbed.addField("Solo Losses : ", String.valueOf(bedwars.getGamemodes().getSolo().getLosses()), true);
        bedwarsEmbed.addField("Solo Games : ", String.valueOf(bedwars.getGamemodes().getSolo().getGamesPlayed()), true);
        bedwarsEmbed.addField("Solo Beds Broken : ", String.valueOf(bedwars.getGamemodes().getSolo().getBedsBroken()), true);
        bedwarsEmbed.addField("Solo Resources Collected : ", String.valueOf(bedwars.getGamemodes().getSolo().getResourcesCollected()), true);

        bedwarsEmbed.addBlankField(false);
        bedwarsEmbed.addField("Doubles Wins : ", String.valueOf(bedwars.getGamemodes().getDoubles().getWins()), true);
        bedwarsEmbed.addField("Doubles Losses : ", String.valueOf(bedwars.getGamemodes().getDoubles().getLosses()), true);
        bedwarsEmbed.addField("Doubles Games : ", String.valueOf(bedwars.getGamemodes().getDoubles().getGamesPlayed()), true);
        bedwarsEmbed.addField("Doubles Beds Broken : ", String.valueOf(bedwars.getGamemodes().getDoubles().getBedsBroken()), true);
        bedwarsEmbed.addField("Doubles Resources Collected : ", String.valueOf(bedwars.getGamemodes().getDoubles().getResourcesCollected()), true);

        bedwarsEmbed.addBlankField(false);
        bedwarsEmbed.addField("3v3 Wins : ", String.valueOf(bedwars.getGamemodes().getThrees().getWins()), true);
        bedwarsEmbed.addField("3v3 Losses : ", String.valueOf(bedwars.getGamemodes().getThrees().getLosses()), true);
        bedwarsEmbed.addField("3v3 Games : ", String.valueOf(bedwars.getGamemodes().getThrees().getGamesPlayed()), true);
        bedwarsEmbed.addField("3v3 Beds Broken : ", String.valueOf(bedwars.getGamemodes().getThrees().getBedsBroken()), true);
        bedwarsEmbed.addField("3v3 Resources Collected : ", String.valueOf(bedwars.getGamemodes().getThrees().getResourcesCollected()), true);

        bedwarsEmbed.addBlankField(false);
        bedwarsEmbed.addField("4v4 Wins : ", String.valueOf(bedwars.getGamemodes().getFours().getWins()), true);
        bedwarsEmbed.addField("4v4 Losses : ", String.valueOf(bedwars.getGamemodes().getFours().getLosses()), true);
        bedwarsEmbed.addField("4v4 Games : ", String.valueOf(bedwars.getGamemodes().getFours().getGamesPlayed()), true);
        bedwarsEmbed.addField("4v4 Beds Broken : ", String.valueOf(bedwars.getGamemodes().getFours().getBedsBroken()), true);
        bedwarsEmbed.addField("4v4 Resources Collected : ", String.valueOf(bedwars.getGamemodes().getFours().getResourcesCollected()), true);

        return bedwarsEmbed;
    }

}
