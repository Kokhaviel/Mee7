package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.vampirez.VampireZ;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class VampireZStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if(args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "vampirez")) {

            if(args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "vampirez <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final VampireZ vampirez = player.getStats().getVampireZ();

                    channel.sendMessage(getVampireZStats(event, player, vampirez).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getVampireZStats(MessageReceivedEvent event, Player player, VampireZ vampirez) {

        EmbedBuilder vampirezEmbed = new EmbedBuilder();
        vampirezEmbed.setAuthor("VampireZ Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        vampirezEmbed.setColor(new Color(252, 84, 84));
        vampirezEmbed.setTitle(player.getUsername() + " Stats");
        vampirezEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        vampirezEmbed.addField("Coins : ", String.valueOf(vampirez.getCoins()), true);
        vampirezEmbed.addField("Gold Bought : ", String.valueOf(vampirez.getGoldBought()), true);

        vampirezEmbed.addBlankField(false);
        vampirezEmbed.addField("Vampire Kills : ", String.valueOf(vampirez.getVampireStats().getKills()), true);
        vampirezEmbed.addField("Vampire Deaths : ", String.valueOf(vampirez.getVampireStats().getDeaths()), true);
        vampirezEmbed.addField("Vampire KDR : ", String.valueOf(vampirez.getVampireStats().getKd()), true);
        vampirezEmbed.addField("Vampire Wins : ", String.valueOf(vampirez.getVampireStats().getWins()), true);
        vampirezEmbed.addField("Zomibie Kills : ", String.valueOf(vampirez.getZombieKills()), true);

        vampirezEmbed.addBlankField(false);
        vampirezEmbed.addField("Human Kills : ", String.valueOf(vampirez.getHumanStats().getKills()), true);
        vampirezEmbed.addField("Human Deaths : ", String.valueOf(vampirez.getHumanStats().getDeaths()), true);
        vampirezEmbed.addField("Human KDR : ", String.valueOf(vampirez.getHumanStats().getKd()), true);
        vampirezEmbed.addField("Human Wins : ", String.valueOf(vampirez.getHumanStats().getWins()), true);

        vampirezEmbed.addBlankField(false);
        vampirezEmbed.addField("Explosive Killer : ", String.valueOf(vampirez.getPerks().getExplosiveKiller()), true);
        vampirezEmbed.addField("Fire Proofing : ", String.valueOf(vampirez.getPerks().getFireproofing()), true);
        vampirezEmbed.addField("Frankeinstein Monster : ", String.valueOf(vampirez.getPerks().getFrankensteinsMonster()), true);
        vampirezEmbed.addField("Gold Booster : ", String.valueOf(vampirez.getPerks().getGoldBooster()), true);
        vampirezEmbed.addField("Gold Starter : ", String.valueOf(vampirez.getPerks().getGoldStarter()), true);
        vampirezEmbed.addField("Renfield : ", String.valueOf(vampirez.getPerks().getRenfield()), true);
        vampirezEmbed.addField("Transfusion : ", String.valueOf(vampirez.getPerks().getTransfusion()), true);
        vampirezEmbed.addField("Vampire Doubler : ", String.valueOf(vampirez.getPerks().getVampireDoubler()), true);
        vampirezEmbed.addField("Vampiric Minion : ", String.valueOf(vampirez.getPerks().getVampiricMinion()), true);

        return vampirezEmbed;
    }

}
