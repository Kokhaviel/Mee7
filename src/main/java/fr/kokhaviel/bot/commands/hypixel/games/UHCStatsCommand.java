package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.uhc.UHC;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class UHCStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "uhc")) {


            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "uhc <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {


                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    UHC uhc = player.getStats().getUhc();

                    channel.sendMessage(getUHCStats(event, player, uhc).build()).queue();
                    channel.sendMessage(getModesUHCStats(event, player, uhc).build()).queue();
                    channel.sendMessage(getPerksStats(event, player, uhc).build()).queue();

                }
            }
        }
    }

    private EmbedBuilder getUHCStats(MessageReceivedEvent event, Player player, UHC uhc) {

        EmbedBuilder uhcEmbed = new EmbedBuilder();
        uhcEmbed.setAuthor("UHC Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        uhcEmbed.setColor(Color.YELLOW);
        uhcEmbed.setTitle(player.getUsername() + " Stats");
        uhcEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        uhcEmbed.addField("Coins : ", String.valueOf(uhc.getCoins()), true);
        uhcEmbed.addField("Wins : ", String.valueOf(uhc.getWins()), true);
        uhcEmbed.addField("Kills : ", String.valueOf(uhc.getKills()), true);
        uhcEmbed.addField("Deaths : ", String.valueOf(uhc.getDeaths()), true);
        uhcEmbed.addField("KDR : ", String.valueOf(uhc.getKd()), true);
        uhcEmbed.addField("Score : ", String.valueOf(uhc.getScore()), true);
        uhcEmbed.addField("Head Eaten : ", String.valueOf(uhc.getHeadsEaten()), true);
        uhcEmbed.addField("Win Loss : ", String.valueOf(uhc.getWinLoss()), true);
        uhcEmbed.addField("Win Percentage : ", String.valueOf(uhc.getWinPercentage()), true);
        uhcEmbed.addField("Equipped Kit : ", uhc.getSettings().getEquippedKit(), true);

        return uhcEmbed;
    }

    private EmbedBuilder getModesUHCStats(MessageReceivedEvent event, Player player, UHC uhc) {

        EmbedBuilder uhcEmbed = new EmbedBuilder();
        uhcEmbed.setAuthor("UHC Solo Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        uhcEmbed.setColor(Color.YELLOW);
        uhcEmbed.setTitle(player.getUsername() + " Stats");
        uhcEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        uhcEmbed.addField("Solo Wins : ", String.valueOf(uhc.getGamemodes().getSolo().getWins()), true);
        uhcEmbed.addField("Solo Kills : ", String.valueOf(uhc.getGamemodes().getSolo().getKills()), true);
        uhcEmbed.addField("Solo Deaths : ", String.valueOf(uhc.getGamemodes().getSolo().getDeaths()), true);
        uhcEmbed.addField("Solo KDR : ", String.valueOf(uhc.getGamemodes().getSolo().getKd()), true);
        uhcEmbed.addField("Solo Heads Eaten : ", String.valueOf(uhc.getGamemodes().getSolo().getHeadsEaten()), true);
        uhcEmbed.addField("Solo Win Percentage : ", String.valueOf(uhc.getGamemodes().getSolo().getWinPercentage()), true);

        uhcEmbed.addField("Red Vs Blue Wins : ", String.valueOf(uhc.getGamemodes().getRedVBlue().getWins()), true);
        uhcEmbed.addField("Red Vs Blue Kills : ", String.valueOf(uhc.getGamemodes().getRedVBlue().getKills()), true);
        uhcEmbed.addField("Red Vs Blue Deaths : ", String.valueOf(uhc.getGamemodes().getRedVBlue().getDeaths()), true);
        uhcEmbed.addField("Red Vs Blue KDR : ", String.valueOf(uhc.getGamemodes().getRedVBlue().getKd()), true);
        uhcEmbed.addField("Red Vs Blue Heads Eaten : ", String.valueOf(uhc.getGamemodes().getRedVBlue().getHeadsEaten()), true);
        uhcEmbed.addField("Red Vs Blue Win Loss : ", String.valueOf(uhc.getGamemodes().getRedVBlue().getWinLoss()), true);

        return  uhcEmbed;
    }

    private EmbedBuilder getPerksStats(MessageReceivedEvent event, Player player, UHC uhc) {

        EmbedBuilder uhcEmbed = new EmbedBuilder();
        uhcEmbed.setAuthor("UHC Perks Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        uhcEmbed.setColor(Color.YELLOW);
        uhcEmbed.setTitle(player.getUsername() + " Stats");
        uhcEmbed.setFooter("Developped by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        uhcEmbed.addField("Alchemy Perk : ", String.valueOf(uhc.getPerks().getAlchemy().getPerkAlchemyLineA()), true);
        uhcEmbed.addField("Apprentice Perk : ", String.valueOf(uhc.getPerks().getApprentice().getPerkApprenticeLineA()), true);
        uhcEmbed.addField("Armorsmith Perk : ", String.valueOf(uhc.getPerks().getArmorsmith().getPerkArmorsmithLineA()), true);
        uhcEmbed.addField("BloodCraft Perk : ", String.valueOf(uhc.getPerks().getBloodcraft().getPerkBloodcraftLineA()), true);
        uhcEmbed.addField("Cooking Perk : ", String.valueOf(uhc.getPerks().getCooking().getPerkCookingLineA()), true);
        uhcEmbed.addField("Enchanting Perk : ", String.valueOf(uhc.getPerks().getEnchanting().getPerkEnchantingLineA()), true);
        uhcEmbed.addField("Engineering Perk : ", String.valueOf(uhc.getPerks().getEngineering().getPerkEngineeringLineA()), true);
        uhcEmbed.addField("Hunter Perk : ", String.valueOf(uhc.getPerks().getHunter().getPerkHunterLineA()), true);
        uhcEmbed.addField("Survivalism Perk : ", String.valueOf(uhc.getPerks().getSurvivalism().getPerkSurvivalismLineA()), true);
        uhcEmbed.addField("ToolSmithing Perk : ", String.valueOf(uhc.getPerks().getToolsmithing().getPerkToolsmithingLineA()), true);
        uhcEmbed.addField("WeaponSmith Perk : ", String.valueOf(uhc.getPerks().getWeaponsmith().getPerkWeaponsmithLineA()), true);

        return uhcEmbed;
    }

}
