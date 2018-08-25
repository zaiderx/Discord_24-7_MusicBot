package me.kaufisch.musicbot.events;

import me.kaufisch.musicbot.audioCore.Music;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.ResumedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @author Kaufisch
 */
public class onResume extends ListenerAdapter {

    @Override
    public void onResume(ResumedEvent event) {
        Music.skip();
    }

    @Override
    public void onReconnect(ReconnectedEvent event) {
        Music.skip();
    }
}
