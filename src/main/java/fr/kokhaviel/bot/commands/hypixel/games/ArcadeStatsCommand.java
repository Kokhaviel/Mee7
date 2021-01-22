package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.arcade.Arcade;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class ArcadeStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "arcade")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "arcade <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {
                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final Arcade arcade = player.getStats().getArcade();

                    channel.sendMessage(getArcade1Stats(event, player, arcade).build()).queue();
                    channel.sendMessage(getArcade2Stats(event, player, arcade).build()).queue();
                    channel.sendMessage(getArcade3Stats(event, player, arcade).build()).queue();

                }
            }


        }
    }

    private EmbedBuilder getArcade1Stats(MessageReceivedEvent event, Player player, Arcade arcade) {

        EmbedBuilder arcadeEmbed = new EmbedBuilder();
        arcadeEmbed.setAuthor("Arcade Stats (1)", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        arcadeEmbed.setColor(Color.GREEN);
        arcadeEmbed.setTitle(player.getUsername() + " Stats");
        arcadeEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        arcadeEmbed.addField("Coins : ", String.valueOf(arcade.getCoins()), true);
        arcadeEmbed.addField("Kills : ", String.valueOf(arcade.getKills()), true);
        arcadeEmbed.addField("Wins : ", String.valueOf(arcade.getWins()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Blocking Dead Wins : ", String.valueOf(arcade.getModes().getBlockingDead().getWins()), true);
        arcadeEmbed.addField("Blocking Dead Kills : ", String.valueOf(arcade.getModes().getBlockingDead().getZombieKills()), true);
        arcadeEmbed.addField("Blocking Dead Headshots : ", String.valueOf(arcade.getModes().getBlockingDead().getHeadshots()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Dragon Wars Wins : ", String.valueOf(arcade.getModes().getDragonWars().getWins()), true);
        arcadeEmbed.addField("Dragon Wars Kills : ", String.valueOf(arcade.getModes().getDragonWars().getKills()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Hypixel Says Wins : ", String.valueOf(arcade.getModes().getHypixelSays().getWins()), true);
        arcadeEmbed.addField("Hypixel Says Rounds : ", String.valueOf(arcade.getModes().getHypixelSays().getRounds()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Mini Walls Wins : ", String.valueOf(arcade.getModes().getMiniWalls().getWins()), true);
        arcadeEmbed.addField("Mini Walls Kills : ", String.valueOf(arcade.getModes().getMiniWalls().getKills()), true);
        arcadeEmbed.addField("Mini Walls Deaths : ", String.valueOf(arcade.getModes().getMiniWalls().getDeaths()), true);
        arcadeEmbed.addField("Mini Walls Final Kills : ", String.valueOf(arcade.getModes().getMiniWalls().getFinalKills()), true);
        arcadeEmbed.addField("Mini Walls Arrows Shot : ", String.valueOf(arcade.getModes().getMiniWalls().getArrowsShot()), true);
        arcadeEmbed.addField("Mini Walls Arrows Hit : ", String.valueOf(arcade.getModes().getMiniWalls().getArrowsHit()), true);
        arcadeEmbed.addField("Mini Walls Wither Damage : ", String.valueOf(arcade.getModes().getMiniWalls().getWitherDamge()), true);
        arcadeEmbed.addField("Mini Walls Wither Kills : ", String.valueOf(arcade.getModes().getMiniWalls().getWitherKills()), true);
        arcadeEmbed.addField("Mini Walls Kit : ", arcade.getModes().getMiniWalls().getKit(), true);



        return arcadeEmbed;
    }

    private EmbedBuilder getArcade2Stats(MessageReceivedEvent event, Player player, Arcade arcade) {

        EmbedBuilder arcadeEmbed = new EmbedBuilder();
        arcadeEmbed.setAuthor("Arcade Stats (2)", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        arcadeEmbed.setColor(Color.GREEN);
        arcadeEmbed.setTitle(player.getUsername() + " Stats");
        arcadeEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        arcadeEmbed.addField("Party Games 1st Place : ", String.valueOf(arcade.getModes().getPartyGames().getWins_1()), true);
        arcadeEmbed.addField("Party Games 2nd Place : ", String.valueOf(arcade.getModes().getPartyGames().getWins_2()), true);
        arcadeEmbed.addField("Party Games 3rd Place : ", String.valueOf(arcade.getModes().getPartyGames().getWins_3()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Bounty Hunters Wins : ", String.valueOf(arcade.getModes().getBountyHunters().getWins()), true);
        arcadeEmbed.addField("Bounty Hunters Kills : ", String.valueOf(arcade.getModes().getBountyHunters().getKills()), true);
        arcadeEmbed.addField("Bounty Hunters Deaths : ", String.valueOf(arcade.getModes().getBountyHunters().getDeaths()), true);
        arcadeEmbed.addField("Bounty Kills : ", String.valueOf(arcade.getModes().getBountyHunters().getBountyKills()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Galaxy Wars Wins : ", String.valueOf(arcade.getModes().getGalaxyWars().getWins()), true);
        arcadeEmbed.addField("Galaxy Wars Kills : ", String.valueOf(arcade.getModes().getGalaxyWars().getKills()), true);
        arcadeEmbed.addField("Galaxy Wars Deaths : ", String.valueOf(arcade.getModes().getGalaxyWars().getDeaths()), true);
        arcadeEmbed.addField("Galaxy Wars Shot Fired : ", String.valueOf(arcade.getModes().getGalaxyWars().getShotsFired()), true);


        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Farm Hunt Wins : ", String.valueOf(arcade.getModes().getFarmHunt().getWins()), true);
        arcadeEmbed.addField("Farm Hunt Poop Collected : ", String.valueOf(arcade.getModes().getFarmHunt().getPoopCollected()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Football Wins : ", String.valueOf(arcade.getModes().getFootball().getWins()), true);
        arcadeEmbed.addField("Football Goals : ", String.valueOf(arcade.getModes().getFootball().getGoals()), true);
        arcadeEmbed.addField("Football Power Kicks : ", String.valueOf(arcade.getModes().getFootball().getPowerkicks()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Best Wave Creeper Attack : ", String.valueOf(arcade.getModes().getCreeperAttack().getBestWave()), true);

        return arcadeEmbed;
    }

    private EmbedBuilder getArcade3Stats(MessageReceivedEvent event, Player player, Arcade arcade) {

        EmbedBuilder arcadeEmbed = new EmbedBuilder();
        arcadeEmbed.setAuthor("Arcade Stats (3)", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        arcadeEmbed.setColor(Color.GREEN);
        arcadeEmbed.setTitle(player.getUsername() + " Stats");
        arcadeEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        arcadeEmbed.addField("Hole in the Wall Wins : ", String.valueOf(arcade.getModes().getHoleInTheWall().getWins()), true);
        arcadeEmbed.addField("Hole in the Wall Rounds : ", String.valueOf(arcade.getModes().getHoleInTheWall().getRounds()), true);
        arcadeEmbed.addField("Hole in the Wall HS Qualification : ", String.valueOf(arcade.getModes().getHoleInTheWall().getHighestScoreQualification()), true);
        arcadeEmbed.addField("Hole in the Wall HS Finals : ", String.valueOf(arcade.getModes().getHoleInTheWall().getHighestScoreFinals()), true);

        arcadeEmbed.addBlankField(false);
        arcadeEmbed.addField("Zombies Wins : ", String.valueOf(arcade.getModes().getZombies().getWins()), true);
        arcadeEmbed.addField("Zombies Kills : ", String.valueOf(arcade.getModes().getZombies().getZombieKills()), true);
        arcadeEmbed.addField("Zombies Deaths : ", String.valueOf(arcade.getModes().getZombies().getDeaths()), true);
        arcadeEmbed.addField("Zombies Rounds Survived : ", String.valueOf(arcade.getModes().getZombies().getTotalRoundsSurvived()), true);
        arcadeEmbed.addField("Zombies Bullet Hit : ", String.valueOf(arcade.getModes().getZombies().getBulletsHit()), true);
        arcadeEmbed.addField("Zombies Headshots : ", String.valueOf(arcade.getModes().getZombies().getHeadshots()), true);
        arcadeEmbed.addField("Zombies Player Revived : ", String.valueOf(arcade.getModes().getZombies().getPlayersRevived()), true);
        arcadeEmbed.addField("Zombies Windows Repaired : ", String.valueOf(arcade.getModes().getZombies().getWindowsRepaired()), true);
        arcadeEmbed.addField("Zombies Doors Opened : ", String.valueOf(arcade.getModes().getZombies().getDoorsOpened()), true);
        arcadeEmbed.addField("Zombies Best Round : ", String.valueOf(arcade.getModes().getZombies().getBestRound()), true);

        return arcadeEmbed;
    }

}
