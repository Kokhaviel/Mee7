package fr.kokhaviel.bot.commands.music;

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class JoinCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        final TextChannel channel = (TextChannel) event.getChannel();
        final Member selfMember = event.getGuild().getSelfMember();
        final GuildVoiceState voiceState = selfMember.getVoiceState();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final AudioManager audioManager = event.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();
        final Message message = event.getMessage();

        final String[] args = message.getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "join"))

            if (voiceState.inVoiceChannel()) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("I'm Already Connected To A Voice Channel !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));


            } else if (!memberVoiceState.inVoiceChannel()) {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                channel.sendMessage("You Need To Be In A Voice Channel For This Command Works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                message.delete().queueAfter(2, TimeUnit.SECONDS);

                audioManager.openAudioConnection(memberChannel);

                channel.sendMessage("Successfully Connected To " + memberChannel.getName()).queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            }


    }
}
