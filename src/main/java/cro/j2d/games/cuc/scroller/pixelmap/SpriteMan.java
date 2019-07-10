// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SpriteMan.java

package cro.j2d.games.cuc.scroller.pixelmap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import javax.imageio.ImageIO;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Sprite

public class SpriteMan
{

    public Sprite getSprite(String str)
    {
        Sprite sp = (Sprite)sprites.get(str);
        if(sp != null)
        {
            return sp;
        } else
        {
            err("Sprite not found in loaded section(" + str + ")");
            return null;
        }
    }

    public Sprite loadSprite(String loc, String alias)
    {        
        System.out.println("\n attempting to load picture \""+ alias +"\" at " + loc);
        String str = getClass().getClassLoader().getResource(loc).toString();
        BufferedImage srcImage = null;
        try
        {
            java.net.URL url = new java.net.URL(str);
            if(url == null)
                err("could not find " + str);
            srcImage = ImageIO.read(url);
        }
        catch(IOException e)
        {
            err("could not find " + loc);
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage img = gc.createCompatibleImage(srcImage.getWidth(), srcImage.getHeight(), 2);
        img.getGraphics().drawImage(srcImage, 0, 0, null);
        Sprite sprite = new Sprite(img);
        sprites.put(alias, sprite);
        sprite.setName(alias);
        return sprite;
    }

    public void addSprite(String alias, BufferedImage img)
    {
        Sprite sprite = new Sprite(img);
        sprites.put(alias, sprite);
    }

    private void err(String msg)
    {
        System.out.println(msg);
        System.exit(1);
    }

    public SpriteMan()
    {
        sprites = new HashMap();
    }

    private HashMap sprites;
}