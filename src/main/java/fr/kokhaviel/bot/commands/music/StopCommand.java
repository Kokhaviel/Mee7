package fr.kokhaviel.bot.commands.music;

import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.music.GuildMusicManager;
import fr.kokhaviel.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class StopCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        final Message message = event.getMessage();
        final String[] args = message.getContentRaw().split("\\s+");
        final TextChannel channel = (TextChannel) event.getChannel();
        final Member member = event.getMember();
        final Guild guild = event.getGuild();
        final Member selfMember = guild.getSelfMember();

        if(args[0].equalsIgnoreCase(Config.MUSIC_PREFIX + "stop")) {

            final GuildVoiceState selfVoiceState = selfMember.getVoiceState();
            final GuildVoiceState voiceState = member.getVoiceState();

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

                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);

                musicManager.scheduler.player.stopTrack();
                musicManager.scheduler.queue.clear();

                channel.sendMessage("The player has been stopped and the queue has been clear !").queue(
                        delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));

            }

        }


    }
}