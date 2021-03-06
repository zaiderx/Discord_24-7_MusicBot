package me.kaufisch.musicbot.audioCore;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.kaufisch.musicbot.utils.FileManager;
import me.kaufisch.musicbot.main.Main;
import net.dv8tion.jda.api.entities.Activity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * @author Kaufisch
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    void nextTrack() {
        FileManager data = new FileManager();
        data.randomSong();
        String identifier = data.currentSong.get(1);
        Music music = new Music();
        music.loadAndPlay(identifier);
        Main.jda.getPresence().setActivity(Activity.playing(data.currentSong.get(0)));
        data.currentSong.clear();
        Main.userAlreadyDid.clear();
        player.playTrack(queue.poll());
    }

    void skipTrack() {
        FileManager data = new FileManager();
        data.randomSong();
        String identifier = data.currentSong.get(1);
        Music music = new Music();
        music.loadAndPlay(identifier);
        Main.jda.getPresence().setActivity(Activity.playing(data.currentSong.get(0)));
        data.currentSong.clear();
        player.stopTrack();
        player.playTrack(queue.poll());
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

}
