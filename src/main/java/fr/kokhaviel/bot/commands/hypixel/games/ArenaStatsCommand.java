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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.player.Player;
import zone.nora.slothpixel.player.stats.arena.Arena;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static fr.kokhaviel.bot.Mee7.sloth;

public class ArenaStatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "arena")) {

            if (args.length == 1) {
                channel.sendMessage("You need to specify a player : " + Config.HYPIXEL_PREFIX + "arena <Player>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            } else {
                if (!args[1].matches("^\\w{3,16}$")) {
                    channel.sendMessage("You must specify a valid Minecraft username !").queue(
                            delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
                } else {

                    message.delete().queue();
                    final Player player = sloth.getPlayer(args[1]);
                    final Arena arena = player.getStats().getArena();
                    channel.sendMessage(getArenaStats(player, arena).build()).queue();
                    channel.sendMessage(getArena1v1Stats(player, arena).build()).queue();
                    channel.sendMessage(getArena2v2Stats(player, arena).build()).queue();
                    channel.sendMessage(getArena4v4Stats(player, arena).build()).queue();
                }
            }
        }
    }

    private EmbedBuilder getArenaStats(Player player, Arena arena) {
        EmbedBuilder arenaEmbed = new EmbedBuilder();
        arenaEmbed.setAuthor("Arena Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        arenaEmbed.setColor(Color.ORANGE);
        arenaEmbed.setTitle(player.getUsername() + " Stats");
        arenaEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

        arenaEmbed.addField("Coins : ", String.valueOf(arena.getCoins()), true);
        arenaEmbed.addField("Coins Spent : ", String.valueOf(arena.getCoinsSpent()), true);
        arenaEmbed.addField("Keys : ", String.valueOf(arena.getKeys()), true);
        arenaEmbed.addField("Selected Sword : ", arena.getSelectedSword(), true);
        arenaEmbed.addField("Penalty : ", String.valueOf(arena.getPenalty()), true);
        arenaEmbed.addField("Active Rune : ", arena.getActiveRune(), true);

        arenaEmbed.addBlankField(false);
        arenaEmbed.addField("Offensive : ", arena.getSkills().getOffensive(), true);
        arenaEmbed.addField("Support : ", arena.getSkills().getSupport(), true);
        arenaEmbed.addField("Ultimate : ", arena.getSkills().getUltimate(), true);
        arenaEmbed.addField("Utility : ", arena.getSkills().getUtility(), true);

        arenaEmbed.addBlankField(false);
        arenaEmbed.addField("Melee Level : ", String.valueOf(arena.getCombatLevels().getMelee()), true);
        arenaEmbed.addField("Health Level : ", String.valueOf(arena.getCombatLevels().getHealth()), true);
        arenaEmbed.addField("Cooldown Level : ", String.valueOf(arena.getCombatLevels().getCooldown()), true);
        arenaEmbed.addField("Melee Level : ", String.valueOf(arena.getCombatLevels().getMelee()), true);

        arenaEmbed.addBlankField(false);
        arenaEmbed.addField("Damage Rune Level : ", String.valueOf(arena.getRuneLevels().getDamage()), true);
        arenaEmbed.addField("Speed Rune Level : ", String.valueOf(arena.getRuneLevels().getSpeed()), true);
        arenaEmbed.addField("Energy Rune Level : ", String.valueOf(arena.getRuneLevels().getEnergy()), true);
        arenaEmbed.addField("Slowing Rune Level : ", String.valueOf(arena.getRuneLevels().getSlowing()), true);

        return arenaEmbed;
    }

    private EmbedBuilder getArena1v1Stats(Player player, Arena arena) {
        EmbedBuilder arenaEmbed = new EmbedBuilder();

        arenaEmbed.setAuthor("1v1 Arena Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        arenaEmbed.setColor(Color.ORANGE);
        arenaEmbed.setTitle(player.getUsername() + " Stats");
        arenaEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

        arenaEmbed.addField("1v1 Wins : ", String.valueOf(arena.getGamemodes().getOneVOne().getWins()), true);
        arenaEmbed.addField("1v1 Losses : ", String.valueOf(arena.getGamemodes().getOneVOne().getLosses()), true);
        arenaEmbed.addField("1v1 Kills : ", String.valueOf(arena.getGamemodes().getOneVOne().getKills()), true);
        arenaEmbed.addField("1v1 Deaths : ", String.valueOf(arena.getGamemodes().getOneVOne().getDeaths()), true);
        arenaEmbed.addField("1v1 Games : ", String.valueOf(arena.getGamemodes().getOneVOne().getGames()), true);
        arenaEmbed.addField("1v1 KDR : ", String.valueOf(arena.getGamemodes().getOneVOne().getKd()), true);
        arenaEmbed.addField("1v1 Heal : ", String.valueOf(arena.getGamemodes().getOneVOne().getHealed()), true);
        arenaEmbed.addField("1v1 Damage : ", String.valueOf(arena.getGamemodes().getOneVOne().getDamage()), true);
        arenaEmbed.addField("1v1 Win Streak : ", String.valueOf(arena.getGamemodes().getOneVOne().getWinStreaks()), true);
        arenaEmbed.addField("1v1 Win Loss : ", String.valueOf(arena.getGamemodes().getOneVOne().getWinLoss()), true);
        arenaEmbed.addField("1v1 Win percentage : ", String.valueOf(arena.getGamemodes().getOneVOne().getWinPercentage()), true);

        return arenaEmbed;
    }

    private EmbedBuilder getArena2v2Stats(Player player, Arena arena) {
        EmbedBuilder arenaEmbed = new EmbedBuilder();

        arenaEmbed.setAuthor("2v2 Arena Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        arenaEmbed.setColor(Color.ORANGE);
        arenaEmbed.setTitle(player.getUsername() + " Stats");
        arenaEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

        arenaEmbed.addField("2v2 Wins : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getWins()), true);
        arenaEmbed.addField("2v2 Losses : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getLosses()), true);
        arenaEmbed.addField("2v2 Kills : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getKills()), true);
        arenaEmbed.addField("2v2 Deaths : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getDeaths()), true);
        arenaEmbed.addField("2v2 Games : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getGames()), true);
        arenaEmbed.addField("2v2 KDR : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getKd()), true);
        arenaEmbed.addField("2v2 Heal : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getHealed()), true);
        arenaEmbed.addField("2v2 Damage : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getDamage()), true);
        arenaEmbed.addField("2v2 Win Streak : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getWinStreaks()), true);
        arenaEmbed.addField("2v2 Win Loss : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getWinLoss()), true);
        arenaEmbed.addField("2v2 Win percentage : ", String.valueOf(arena.getGamemodes().getTwoVTwo().getWinPercentage()), true);

        return arenaEmbed;
    }

    private EmbedBuilder getArena4v4Stats(Player player, Arena arena) {
        EmbedBuilder arenaEmbed = new EmbedBuilder();

        arenaEmbed.setAuthor("4v4 Arena Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        arenaEmbed.setColor(Color.ORANGE);
        arenaEmbed.setTitle(player.getUsername() + " Stats");
        arenaEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAPI by SlothPixel (docs.slothpixel.me)");

        arenaEmbed.addField("4v4 Wins : ", String.valueOf(arena.getGamemodes().getFourVFour().getWins()), true);
        arenaEmbed.addField("4v4 Losses : ", String.valueOf(arena.getGamemodes().getFourVFour().getLosses()), true);
        arenaEmbed.addField("4v4 Kills : ", String.valueOf(arena.getGamemodes().getFourVFour().getKills()), true);
        arenaEmbed.addField("4v4 Deaths : ", String.valueOf(arena.getGamemodes().getFourVFour().getDeaths()), true);
        arenaEmbed.addField("4v4 Games : ", String.valueOf(arena.getGamemodes().getFourVFour().getGames()), true);
        arenaEmbed.addField("4v4 KDR : ", String.valueOf(arena.getGamemodes().getFourVFour().getKd()), true);
        arenaEmbed.addField("4v4 Heal : ", String.valueOf(arena.getGamemodes().getFourVFour().getHealed()), true);
        arenaEmbed.addField("4v4 Damage : ", String.valueOf(arena.getGamemodes().getFourVFour().getDamage()), true);
        arenaEmbed.addField("4v4 Win Streak : ", String.valueOf(arena.getGamemodes().getFourVFour().getWinStreaks()), true);
        arenaEmbed.addField("4v4 Win Loss : ", String.valueOf(arena.getGamemodes().getFourVFour().getWinLoss()), true);
        arenaEmbed.addField("4v4 Win percentage : ", String.valueOf(arena.getGamemodes().getFourVFour().getWinPercentage()), true);

        return arenaEmbed;
    }
}
