package fr.kokhaviel.bot.commands.hypixel.games.blitz;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.blitz.kits.BlitzKits;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BlitzKitLevelCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "blitzstats" ) && args[1].equalsIgnoreCase("kitlevel")) {

            if (args.length == 2) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "blitz <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[2]);
                    final BlitzKits blitzLevelsStats = player.getStats().getBlitz().getKitsLevels();

                    EmbedBuilder blitzLevelsEmbed = new EmbedBuilder();

                    blitzLevelsEmbed.setAuthor("Blitz Kit Levels", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
                    blitzLevelsEmbed.setColor(new Color(73, 240, 255));
                    blitzLevelsEmbed.setTitle(player.getUsername() + " Stats");
                    blitzLevelsEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

                    blitzLevelsEmbed.addField("Arachnologist : ", String.valueOf(blitzLevelsStats.getArachnologist()), true);
                    blitzLevelsEmbed.addField("Archer : ", String.valueOf(blitzLevelsStats.getArcher()), true);
                    blitzLevelsEmbed.addField("Armorer : ", String.valueOf(blitzLevelsStats.getArmorer()), true);
                    blitzLevelsEmbed.addField("Astronaut : ", String.valueOf(blitzLevelsStats.getAstronaut()), true);
                    blitzLevelsEmbed.addField("Blaze : ", String.valueOf(blitzLevelsStats.getBlaze()), true);
                    blitzLevelsEmbed.addField("Creeper Tamer : ", String.valueOf(blitzLevelsStats.getCreepertamer()), true);
                    blitzLevelsEmbed.addField("Diver : ", String.valueOf(blitzLevelsStats.getDiver()), true);
                    blitzLevelsEmbed.addField("Farmer : ", String.valueOf(blitzLevelsStats.getFarmer()), true);
                    blitzLevelsEmbed.addField("Fisherman : ", String.valueOf(blitzLevelsStats.getFisherman()), true);
                    blitzLevelsEmbed.addField("Florist : ", String.valueOf(blitzLevelsStats.getFlorist()), true);
                    blitzLevelsEmbed.addField("Golem : ", String.valueOf(blitzLevelsStats.getGolem()), true);
                    blitzLevelsEmbed.addField("Guardian : ", String.valueOf(blitzLevelsStats.getGuardian()), true);
                    blitzLevelsEmbed.addField("Horse Tamer : ", String.valueOf(blitzLevelsStats.getHorsetamer()), true);
                    blitzLevelsEmbed.addField("Hunter : ", String.valueOf(blitzLevelsStats.getHunter()), true);
                    blitzLevelsEmbed.addField("Hype Train : ", String.valueOf(blitzLevelsStats.getHypeTrain()), true);
                    blitzLevelsEmbed.addField("Jockey : ", String.valueOf(blitzLevelsStats.getJockey()), true);
                    blitzLevelsEmbed.addField("Knight : ", String.valueOf(blitzLevelsStats.getKnight()), true);
                    blitzLevelsEmbed.addField("Meat Master : ", String.valueOf(blitzLevelsStats.getMeatmaster()), true);
                    blitzLevelsEmbed.addField("Necromancer : ", String.valueOf(blitzLevelsStats.getNecromancer()), true);
                    blitzLevelsEmbed.addField("Paladin : ", String.valueOf(blitzLevelsStats.getPaladin()), true);
                    blitzLevelsEmbed.addField("Pigman : ", String.valueOf(blitzLevelsStats.getPigman()), true);
                    blitzLevelsEmbed.addField("Rambo : ", String.valueOf(blitzLevelsStats.getRambo()), true);
                    blitzLevelsEmbed.addField("Random : ", String.valueOf(blitzLevelsStats.getRandom()), true);
                    blitzLevelsEmbed.addField("Reaper : ", String.valueOf(blitzLevelsStats.getReaper()), true);
                    blitzLevelsEmbed.addField("Red Dragon : ", String.valueOf(blitzLevelsStats.getReddragon()), true);
                    blitzLevelsEmbed.addField("Rogue : ", String.valueOf(blitzLevelsStats.getRogue()), true);
                    blitzLevelsEmbed.addField("Scout : ", String.valueOf(blitzLevelsStats.getScout()), true);
                    blitzLevelsEmbed.addField("Shadow Knight : ", String.valueOf(blitzLevelsStats.getShadowKnight()), true);
                    blitzLevelsEmbed.addField("Slimey Slime : ", String.valueOf(blitzLevelsStats.getSlimeyslime()), true);
                    blitzLevelsEmbed.addField("Snowman : ", String.valueOf(blitzLevelsStats.getSnowman()), true);
                    blitzLevelsEmbed.addField("Speleologist : ", String.valueOf(blitzLevelsStats.getSpeleologist()), true);
                    blitzLevelsEmbed.addField("Tim : ", String.valueOf(blitzLevelsStats.getTim()), true);
                    blitzLevelsEmbed.addField("Toxicologist : ", String.valueOf(blitzLevelsStats.getToxicologist()), true);
                    blitzLevelsEmbed.addField("Troll : ", String.valueOf(blitzLevelsStats.getTroll()), true);
                    blitzLevelsEmbed.addField("Viking : ", String.valueOf(blitzLevelsStats.getViking()), true);
                    blitzLevelsEmbed.addField("Warlock : ", String.valueOf(blitzLevelsStats.getWarlock()), true);
                    blitzLevelsEmbed.addField("Wolf Tamer : ", String.valueOf(blitzLevelsStats.getWolftamer()), true);

                    channel.sendMessage(blitzLevelsEmbed.build()).queue();
                }
            }
        }
    }
}
