/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Sfx;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;

public class SfxMan {
    private Sfx nullSound = null;
    private HashMap clips = new HashMap();

    public Sfx searchForClip(String str) {
        Sfx c = (Sfx)this.clips.get(str);
        if (c != null) {
            return c;
        }
        this.err("Audio clip [" + str + "] not found in loaded section");
        return null;
    }

    public void playClip(String str) {
        Sfx c = this.searchForClip(str);
        if (c != null) {
            c.play();
        }
    }

    public void loopClip(String str) {
        Sfx c = this.searchForClip(str);
        if (c != null) {
            c.loop();
        }
    }

    public void stopClip(String str) {
        Sfx c = this.searchForClip(str);
        if (c != null) {
            c.stopClip();
        }
    }

    public void loadClip(String loc, String alias) {
    }

    public Sfx loadSfx(String loc, String alias) {
        AudioClip srcClip = null;
        URL url = this.getClass().getClassLoader().getResource(loc);
        if (url == null) {
            this.err("could not find " + loc);
            return null;
        }
        srcClip = Applet.newAudioClip(url);
        if (srcClip == null) {
            this.err("could not load file " + loc);
        }
        Sfx sfx = new Sfx(srcClip);
        this.clips.put(alias, sfx);
        return sfx;
    }

    private void err(String msg) {
        System.out.println(msg);
    }

    public Sfx getNullSound() {
        return this.nullSound;
    }

    public void setNullSound(Sfx nullSound) {
        this.nullSound = nullSound;
    }
}

