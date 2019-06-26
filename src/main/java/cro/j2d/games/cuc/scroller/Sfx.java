/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import java.applet.AudioClip;

public class Sfx {
    private AudioClip clip;

    public Sfx(AudioClip c) {
        this.clip = c;
    }

    public AudioClip getClip() {
        return this.clip;
    }

    public void setClip(AudioClip clip) {
        this.clip = clip;
    }

    public void play() {
        if (this.clip == null) {
            return;
        }
        this.clip.play();
    }

    public void stopClip() {
        if (this.clip == null) {
            return;
        }
        this.clip.stop();
    }

    public void loop() {
        if (this.clip == null) {
            return;
        }
        this.clip.loop();
    }
}

