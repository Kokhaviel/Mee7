package fr.kokhaviel.bot.commands.music;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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


        if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "play")) {

            message.delete().queue();

            if(!selfVoiceState.inVoiceChannel()) {

                channel.sendMessage("I need to be in a voice channel to this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if(!voiceState.getChannel().equals(selfVoiceState.getChannel())) {

                channel.sendMessage("You need to be in the same voice channel as me for this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                PlayerManager.getInstance().loadAndPlay(channel, "https://www.youtube.com/watch?v=-SeVN2u5Axc");
            }

        }
    }
}
