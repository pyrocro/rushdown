// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SfxMan.java

package cro.j2d.games.cuc.scroller;

import java.applet.Applet;
import java.io.PrintStream;
import java.util.HashMap;
//import java.lang.Runnable;
import java.io.File;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.DataLine.*;
import javax.sound.sampled.Port.*;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Sfx

public class SfxMan implements Runnable
{
    
    public void run(){        
        AudioInputStream aIs = null; 
        AudioInputStream aIs2 = null; 
        AudioInputStream aIs3 = null;
        AudioInputStream aIs4 = null;
        AudioInputStream aIs5 = null;
        AudioInputStream aIs6 = null;
        AudioFormat aF = null; // Playback format
        DataLine.Info info = null; // Dataline from mixer
        Clip m_clip  = null;
        Clip m_clip2  = null;
        Clip m_clip3  = null;
        Clip m_clip4  = null;
        Clip m_clip5  = null;
        Clip m_clip6  = null;
        /*java.net.URL url = getClass().getClassLoader().getResource("cro/sounds/loops/techno1.wav");
        File soundFile = new File(url.getFile());
        try{
            aIs = AudioSystem.getAudioInputStream(soundFile);
            aIs2 = AudioSystem.getAudioInputStream(soundFile);
            aIs3 = AudioSystem.getAudioInputStream(soundFile);
            aIs4 = AudioSystem.getAudioInputStream(soundFile);
            aIs5 = AudioSystem.getAudioInputStream(soundFile);
            aIs6 = AudioSystem.getAudioInputStream(soundFile);
        } catch(Exception e){
            e.printStackTrace();
        }*/
        
        
/*        aF = aIs.getFormat();        
        info = new DataLine.Info (Clip.class, aF);
        try{
            m_clip = (Clip) AudioSystem.getMixer(null).getLine(info);            
            m_clip2 = (Clip) AudioSystem.getMixer(null).getLine(info);
            m_clip3 = (Clip) AudioSystem.getMixer(null).getLine(info);
            m_clip4 = (Clip) AudioSystem.getMixer(null).getLine(info);
            m_clip5 = (Clip) AudioSystem.getMixer(null).getLine(info);
            m_clip6 = (Clip) AudioSystem.getMixer(null).getLine(info);
            //m_clip.open(aIs);
            //m_clip2.open(aIs2);
            //m_clip3.open(aIs3);
            //m_clip4.open(aIs4);            
            //m_clip5.open(aIs5);
            //m_clip6.open(aIs6);
        }catch(Exception e){
            e.printStackTrace();
        }*/
        
        System.out.println("Number Of mixers:"+AudioSystem.getMixerInfo().length);
        //m_clip.start();        
        int num_of_lines = AudioSystem.getMixer(AudioSystem.getMixerInfo()[0]).getSourceLines().length;
        String name = AudioSystem.getMixer(null).getMixerInfo().getName();
        String description = AudioSystem.getMixer(null).getMixerInfo().getDescription();
        String Vendor = AudioSystem.getMixer(null).getMixerInfo().getVendor();
        System.out.println( "*************Sound Device**************");
        System.out.println( "name: " + name );
        System.out.println( "Description: " + description );
        System.out.println( "Vendor: " + Vendor );
        System.out.println( "num_of_lines:" + num_of_lines );
        System.out.println( "***************************************");        
        /*while (1<2){
            try
            {                
               Thread.sleep(5000L);
                //m_clip.stop();
                //m_clip2.start();
                if (m_clip.isRunning()){
                    System.out.println("The clip is still running");
                }
                //m_clip2.start();
            }
            catch(Exception e)
            {
                System.out.println("exception in the sleep 'try method'");
            }
        }*/
        
    }

    public Sfx searchForClip(String str)
    {
        Sfx c = (Sfx)clips.get(str);
        if(c != null)
        {
            return c;
        } else
        {
            err("Audio clip [" + str + "] not found in loaded section");
            return null;
        }
    }

    public void playClip(String str)
    {
        Sfx c = searchForClip(str);
        if(c != null)
            c.play();
    }

    public void loopClip(String str)
    {
        Sfx c = searchForClip(str);
        if(c != null)
            c.loop();
    }

    public void stopClip(String str)
    {
        Sfx c = searchForClip(str);
        if(c != null)
            c.stopClip();
    }

    public void loadClip(String s2, String s3)
    {
    }

    public Sfx loadSfx(String loc, String alias)
    {
        java.applet.AudioClip srcClip = null;
        java.net.URL url = getClass().getClassLoader().getResource(loc);
        if(url == null)
        {
            err("could not find " + loc);
            return null;
        }
        srcClip = Applet.newAudioClip(url);
        if(srcClip == null)
            err("could not load file " + loc);
        Sfx sfx = new Sfx(srcClip);
        clips.put(alias, sfx);
        return sfx;
    }

    private void err(String msg)
    {
        System.out.println(msg);
    }

    public SfxMan()
    {
        nullSound = null;
        clips = new HashMap();
        this.run();
    }

    public Sfx getNullSound()
    {
        return nullSound;
    }

    public void setNullSound(Sfx nullSound)
    {
        this.nullSound = nullSound;
    }

    private Sfx nullSound;
    private HashMap clips;
}