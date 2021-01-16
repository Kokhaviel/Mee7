package fr.kokhaviel.bot.commands.music;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.GuildMusicManager;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class LeaveCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Member member = event.getMember();
        final Guild guild = event.getGuild();
        final Member selfMember = guild.getSelfMember();

        if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "leave")) {

            final GuildVoiceState voiceState = member.getVoiceState();
            final GuildVoiceState selfVoiceState = selfMember.getVoiceState();

            message.delete().queue();

            if(!voiceState.inVoiceChannel()) {

                channel.sendMessage("You need to be in a voice channel to this command works").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (!selfVoiceState.inVoiceChannel()) {

                channel.sendMessage("I need to be in a voice channel to this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else if (!voiceState.getChannel().equals(selfVoiceState.getChannel())) {

                channel.sendMessage("You need to be in the same voice channel as me for this command works !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            } else {

                final AudioManager audioManager = guild.getAudioManager();
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);

                musicManager.scheduler.repeating = false;
                musicManager.scheduler.queue.clear();
                musicManager.audioPlayer.stopTrack();

                audioManager.closeAudioConnection();

                channel.sendMessage("Successfully left the voice channel !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
            }
        }
    }
}
