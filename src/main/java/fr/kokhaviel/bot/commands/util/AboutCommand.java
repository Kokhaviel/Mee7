package fr.kokhaviel.bot.commands.util;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserImpl;

public class AboutCommand extends ListenerAdapter {


    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final Member member = event.getMember();
        final JDA jda = event.getJDA();
        final MessageChannel channel = event.getChannel();
        final User author = event.getAuthor();

        if (args[0].equalsIgnoreCase(Config.PREFIX + "about")) {

            message.delete().queue();

            User user = member.getUser();

            EmbedBuilder aboutEmbed = new EmbedBuilder();

            aboutEmbed.setTitle("About Menu")
                    .setColor(Color.GREEN)
                    .setAuthor("Mee7 : A Simple Java Discord Bot ")
                    .setThumbnail(jda.getSelfUser().getAvatarUrl())

                    .addField("Developer : ", "Kokhaviel.java#0001", false)
                    .addField("Github : ", "[Github Repository](https://github.com/Kokhaviel/Mee7)", false)
                    .addField("Prefix : ", Config.PREFIX, false)
                    .addField("Help : ", Config.PREFIX + "help", false)
                    .addField("Library : ", "[JDA](https://github.com/DV8FromTheWorld/JDA)", false);

            channel.sendMessage(author.getAsMention() + ", a message will be send to your DM !").queue();

            if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();

            ((UserImpl) user).getPrivateChannel().sendMessage(aboutEmbed.build()).queue();


        }

    }

}
