/*
 * Copyright (C) 2021 Kokhaviel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.kokhaviel.bot.commands.hypixel.games;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.cvc.CvC;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class CopsAndCrimsStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "copsandcrims")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "copsandcrims <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {


                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();

                    final Player player = sloth.getPlayer(args[1]);
                    final CvC cvc = player.getStats().getCvC();

                    channel.sendMessage(getCvCStats(event, player, cvc).build()).queue();
                    channel.sendMessage(getCvcPerksStats(event, player, cvc).build()).queue();
                    channel.sendMessage(getCvcPerks2Stats(event, player, cvc).build()).queue();
                    channel.sendMessage(getCvcCosmetics(event, player, cvc).build()).queue();

                }
            }

        }


    }

    private EmbedBuilder getCvCStats(MessageReceivedEvent event, Player player, CvC cvc) {


        EmbedBuilder cvcStats = new EmbedBuilder();
        cvcStats.setAuthor("Cops and Crims Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        cvcStats.setColor(Color.DARK_GRAY);
        cvcStats.setTitle(player.getUsername() + " Stats");
        cvcStats.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        cvcStats.addField("Coins : ", String.valueOf(cvc.getCoins()), true);
        cvcStats.addField("Wins : ", String.valueOf(cvc.getWins()), true);
        cvcStats.addField("Rounds Win : ", String.valueOf(cvc.getRoundWins()), true);
        cvcStats.addField("Kills : ", String.valueOf(cvc.getKills()), true);
        cvcStats.addField("Deaths : ", String.valueOf(cvc.getDeaths()), true);
        cvcStats.addField("Cop Kills :", String.valueOf(cvc.getCopKills()), true);
        cvcStats.addField("Criminal Kills", String.valueOf(cvc.getCriminalKills()), true);
        cvcStats.addField("KDR : ", String.valueOf(cvc.getKd()), true);
        cvcStats.addField("Bombs Planted : ", String.valueOf(cvc.getBombsPlanted()), true);
        cvcStats.addField("Bombs Defused : ", String.valueOf(cvc.getBombsDefused()), true);
        cvcStats.addField("Shots Fired : ", String.valueOf(cvc.getShotsFired()), true);
        cvcStats.addField("Headshots Kills : ", String.valueOf(cvc.getHeadshotKills()), true);
        cvcStats.addField("Grenade Kills : ", String.valueOf(cvc.getGrenadeKills()), true);

        cvcStats.addBlankField(false);
        cvcStats.addField("Deathmatch Wins : ", String.valueOf(cvc.getDeathmatch().getWins()), true);
        cvcStats.addField("Deathmatch Kills : ", String.valueOf(cvc.getDeathmatch().getKills()), true);
        cvcStats.addField("Deathmatch Cop Kills :", String.valueOf(cvc.getDeathmatch().getCopKills()), true);
        cvcStats.addField("Deathmatch Criminal Kills :", String.valueOf(cvc.getDeathmatch().getCriminalKills()), true);
        cvcStats.addField("Deathmatch Deaths : ", String.valueOf(cvc.getDeathmatch().getDeaths()), true);
        cvcStats.addField("Deathmatch KDR : ", String.valueOf(cvc.getDeathmatch().getKd()), true);

        return cvcStats;
    }

    private EmbedBuilder getCvcPerksStats(MessageReceivedEvent event, Player player, CvC cvc) {

        EmbedBuilder cvcStats = new EmbedBuilder();
        cvcStats.setAuthor("Cops and Crims Perks Stats (1)", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        cvcStats.setColor(Color.DARK_GRAY);
        cvcStats.setTitle(player.getUsername() + " Stats");
        cvcStats.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        cvcStats.addField("Player Body Armor Cost : ", String.valueOf(cvc.getPerks().getPlayer().getBodyArmorCost()), true);
        cvcStats.addField("Player Bounty Hunter : ", String.valueOf(cvc.getPerks().getPlayer().getBountyHunter()), true);
        cvcStats.addField("Player Strength Training : ", String.valueOf(cvc.getPerks().getPlayer().getStrengthTraining()), true);
        cvcStats.addField("Player Pocket Change : ", String.valueOf(cvc.getPerks().getPlayer().getPocketChange()), true);

        cvcStats.addBlankField(false);
        cvcStats.addField("Carbine Cost Reduction : ", String.valueOf(cvc.getPerks().getCarbine().getCostReduction()), true);
        cvcStats.addField("Carbine Damage Increase : ", String.valueOf(cvc.getPerks().getCarbine().getDamageIncrease()), true);
        cvcStats.addField("Carbine Recoil Reduction : ", String.valueOf(cvc.getPerks().getCarbine().getRecoilReduction()), true);
        cvcStats.addField("Carbine Reload Speed Reduction : ", String.valueOf(cvc.getPerks().getCarbine().getReloadSpeedReduction()), true);

        cvcStats.addBlankField(false);
        cvcStats.addField("Knife Attack Delay : ", String.valueOf(cvc.getPerks().getKnife().getAttackDelay()), true);
        cvcStats.addField("Knife Damage Increase : ", String.valueOf(cvc.getPerks().getKnife().getDamageIncrease()), true);

        cvcStats.addBlankField(false);
        cvcStats.addField("Magnum Cost Reduction : ", String.valueOf(cvc.getPerks().getMagnum().getCostReduction()), true);
        cvcStats.addField("Magnum Damage Increase : ", String.valueOf(cvc.getPerks().getMagnum().getDamageIncrease()), true);
        cvcStats.addField("Magnum Recoil Reduction : ", String.valueOf(cvc.getPerks().getMagnum().getRecoilReduction()), true);
        cvcStats.addField("Magnum Reload Speed Reduction : ", String.valueOf(cvc.getPerks().getMagnum().getReloadSpeedReduction()), true);

        return cvcStats;
    }

    private EmbedBuilder getCvcPerks2Stats(MessageReceivedEvent event, Player player, CvC cvc) {

        EmbedBuilder cvcStats = new EmbedBuilder();
        cvcStats.setAuthor("Cops and Crims Perks Stats (2)", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        cvcStats.setColor(Color.DARK_GRAY);
        cvcStats.setTitle(player.getUsername() + " Stats");
        cvcStats.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        cvcStats.addField("Pistol Damage Increase : ", String.valueOf(cvc.getPerks().getPistol().getDamageIncrease()), true);
        cvcStats.addField("Pistol Recoil Reduction : ", String.valueOf(cvc.getPerks().getPistol().getRecoilReduction()), true);
        cvcStats.addField("Pistol Reload Speed Reduction : ", String.valueOf(cvc.getPerks().getPistol().getReloadSpeedReduction()), true);

        cvcStats.addBlankField(false);
        cvcStats.addField("Rifle Cost Reduction : ", String.valueOf(cvc.getPerks().getRifle().getCostReduction()), true);
        cvcStats.addField("Rifle Damage Increase : ", String.valueOf(cvc.getPerks().getRifle().getDamageIncrease()), true);
        cvcStats.addField("Rifle Recoil Reduction : ", String.valueOf(cvc.getPerks().getRifle().getRecoilReduction()), true);
        cvcStats.addField("Rifle Reload Speed Reduction : ", String.valueOf(cvc.getPerks().getRifle().getReloadSpeedReduction()), true);

        cvcStats.addBlankField(false);
        cvcStats.addField("SMG Cost Reduction : ", String.valueOf(cvc.getPerks().getSmg().getCostReduction()), true);
        cvcStats.addField("SMG Damage Increase : ", String.valueOf(cvc.getPerks().getSmg().getDamageIncrease()), true);
        cvcStats.addField("SMG Recoil Reduction : ", String.valueOf(cvc.getPerks().getSmg().getRecoilReduction()), true);
        cvcStats.addField("SMG Reload Speed Reduction : ", String.valueOf(cvc.getPerks().getSmg().getReloadSpeedReduction()), true);

        cvcStats.addBlankField(false);
        cvcStats.addField("Sniper Charge Bonus : ", String.valueOf(cvc.getPerks().getSniper().getChargeBonus()), true);

        return cvcStats;
    }

    private EmbedBuilder getCvcCosmetics(MessageReceivedEvent event, Player player, CvC cvc) {

        EmbedBuilder cvcStats = new EmbedBuilder();
        cvcStats.setAuthor("Cops and Crims Selected Cosmetics Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        cvcStats.setColor(Color.DARK_GRAY);
        cvcStats.setTitle(player.getUsername() + " Stats");
        cvcStats.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");


        cvcStats.addField("Carbine : ", cvc.getSelectedCosmetics().getCarbine(), true);
        cvcStats.addField("Creeper Helmet : ", cvc.getSelectedCosmetics().getCreeperHelmet(), true);
        cvcStats.addField("Creeper Chestplate : ", cvc.getSelectedCosmetics().getCreeperChestplate(), true);
        cvcStats.addField("Knife : ", cvc.getSelectedCosmetics().getKnife(), true);
        cvcStats.addField("Magnum : ", cvc.getSelectedCosmetics().getMagnum(), true);
        cvcStats.addField("Ocelot Helmet : ", cvc.getSelectedCosmetics().getOcelotHelmet(), true);
        cvcStats.addField("Ocelot Chestplate", cvc.getSelectedCosmetics().getOcelotChestplate(), true);
        cvcStats.addField("Pistol : ", cvc.getSelectedCosmetics().getPistol(), true);
        cvcStats.addField("Rifle : ", cvc.getSelectedCosmetics().getRifle(), true);
        cvcStats.addField("Shotgun : ", cvc.getSelectedCosmetics().getShotgun(), true);
        cvcStats.addField("SMG : ", cvc.getSelectedCosmetics().getSmg(), true);

        return cvcStats;
    }

}
