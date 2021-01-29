package fr.kokhaviel.bot.commands.hypixel.server;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import zone.nora.slothpixel.bans.Bans;

import java.awt.*;

import static fr.kokhaviel.bot.Mee7.sloth;

public class BanStatsCommand extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();

        if (args[0].equalsIgnoreCase(Config.HYPIXEL_PREFIX + "bans")) {

            message.delete().queue();


            Bans bans = sloth.getBans();

            channel.sendMessage(getBansStats(event, bans).build()).queue();

        }
    }

    private EmbedBuilder getBansStats(MessageReceivedEvent event, Bans bans) {

        EmbedBuilder bansEmbed = new EmbedBuilder();
        bansEmbed.setAuthor("Hypixel Stats", null, "https://cdn.discordapp.com/icons/489529070913060867/b8fe7468a1feb1020640c200313348b0.webp?size=128");
        bansEmbed.setColor(Color.RED);
        bansEmbed.setTitle("Bans Stats");
        bansEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nAction Generated on " + event.getGuild().getName(), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

        bansEmbed.addField("Last Minute Watchdog Bans : ", String.valueOf(bans.getWatchdog().getLastMinute()), true);
        bansEmbed.addField("Daily Watchdog Bans : ", String.valueOf(bans.getWatchdog().getDaily()), true);
        bansEmbed.addField("Total Watchdog Bans : ", String.valueOf(bans.getWatchdog().getTotal()), true);

        bansEmbed.addBlankField(false);
        bansEmbed.addField("Daily Staff Bans : ", String.valueOf(bans.getStaff().getDaily()), true);
        bansEmbed.addField("Total Staff Bans : ", String.valueOf(bans.getStaff().getDaily()), true);

        return bansEmbed;
    }

}
