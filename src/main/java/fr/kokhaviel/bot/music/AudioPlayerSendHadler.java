package fr.kokhaviel.bot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class AudioPlayerSendHadler implements AudioSendHandler {

    final AudioPlayer audioPlayer;
    final ByteBuffer buffer;
    final MutableAudioFrame frame;

    public AudioPlayerSendHadler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
        return this.audioPlayer.provide(this.frame);

    }

    @Override
    public ByteBuffer provide20MsAudio() {
        final Buffer tmp = this.buffer.flip();

        return (ByteBuffer) tmp;
    }

    @Override
    public boolean isOpus() {
        return true;
    }

}
