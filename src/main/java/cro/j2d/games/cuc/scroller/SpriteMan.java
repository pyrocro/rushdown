/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Sprite;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteMan {
    private HashMap sprites = new HashMap();

    public Sprite getSprite(String str) {
        Sprite sp = (Sprite)this.sprites.get(str);
        if (sp != null) {
            return sp;
        }
        this.err("Sprite not found in loaded section(" + str + ")");
        return null;
    }

    public Sprite loadSprite(String loc, String alias) {
        BufferedImage srcImage = null;
        try {
            //URL url = new URL(this.getClass().getClassLoader().getResource(".").toString()+"/"+loc);           
            URL url = this.getClass().getClassLoader().getResource(loc);
            //URL url = new URL("file://Users/ts-yohance.mcdonald/NetBeansProjects/rushdown-marven/rushdown-maven/rushdown-maven/src/main/java/cro/j2d/pics/boom.gif");
            System.out.println(url+"/"+loc);
            if (url == null) {
                this.err("could not find " + loc);
            }
            srcImage = ImageIO.read(url);
        }
        catch (IOException e) {
            this.err(e.getMessage()+"---> could not find " + loc);
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage img = gc.createCompatibleImage(srcImage.getWidth(), srcImage.getHeight(), 2);
        ((Image)img).getGraphics().drawImage(srcImage, 0, 0, null);
        Sprite sprite = new Sprite(img);
        this.sprites.put(alias, sprite);
        return sprite;
    }

    public void addSprite(String alias, Image img) {
        Sprite sprite = new Sprite(img);
        this.sprites.put(alias, sprite);
    }

    private void err(String msg) {
        System.out.println(msg);
        System.exit(1);
    }
}

