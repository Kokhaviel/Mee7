package fr.kokhaviel.bot.commands.music;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayCommand extends ListenerAdapter {

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Member selfMember = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        final Member member = event.getMember();
        final GuildVoiceState voiceState = member.getVoiceState();
        final List<String> linkArgs = Arrays.asList(args).subList(0, 0);

        if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "play")) {

            message.delete().queue();

            if(args.length < 2) {

                channel.sendMessage("Please Use : " + Config.MUSIC_PREFIX + "play <Youtube Link>").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if(!selfVoiceState.inVoiceChannel()) {

                channel.sendMessage("I need to be in a voice channel to this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if(!voiceState.getChannel().equals(selfVoiceState.getChannel())) {

                channel.sendMessage("You need to be in the same voice channel as me for this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                String link = args[1];

                if(!isUrl(link)) {

                    link = "ytsearch : " + link;

                }

                PlayerManager.getInstance().loadAndPlay(channel, link);
            }
        }
    }

    private boolean isUrl(String url) {

        try {

            new URI(url);
            return true;

        } catch (URISyntaxException use) {

            return false;

        }

    }
}
