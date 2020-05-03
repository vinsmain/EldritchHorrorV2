package ru.mgusev.eldritchhorror.model;

import ru.mgusev.eldritchhorror.api.json_model.Files;

public class AudioState {

    private Files audio;
    private boolean playing;
    private long totalDuration;
    private long currentPosition;

    public AudioState(Files audio, boolean playing, long totalDuration, long currentPosition) {
        this.audio = audio;
        this.playing = playing;
        this.totalDuration = totalDuration;
        this.currentPosition = currentPosition;
    }

    public Files getAudio() {
        return audio;
    }

    public void setAudio(Files audio) {
        this.audio = audio;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public long getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(long currentPosition) {
        this.currentPosition = currentPosition;
    }
}