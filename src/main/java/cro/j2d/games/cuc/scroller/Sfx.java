// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Sfx.java

package cro.j2d.games.cuc.scroller;

import java.applet.AudioClip;

public class Sfx
{    
    public Sfx(AudioClip c)
    {        
                
        clip = c;
    }

    public AudioClip getClip()
    {
        return clip;
    }

    public void setClip(AudioClip clip)
    {
        this.clip = clip;
    }

    public void play()
    {
        if(clip == null)
        {
            return;
        } else
        {
            clip.play();
            return;
        }
    }

    public void stopClip()
    {
        if(clip == null)
        {
            return;
        } else
        {
            clip.stop();
            return;
        }
    }

    public void loop()
    {
        if(clip == null)
        {
            return;
        } else
        {
            clip.loop();
            return;
        }
    }

    private AudioClip clip;
}