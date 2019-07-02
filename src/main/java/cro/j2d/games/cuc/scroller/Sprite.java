/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Sprite {
    private Image image;
    private double rot = 0;

    public Sprite(Image img) {
        this.image = img;
        this.image.setAccelerationPriority(0.99f);
    }

    public int getHeight() {
        return this.image.getHeight(null);
    }

    public int getWidth() {
        return this.image.getWidth(null);
    }

    public void draw(Graphics g, int x, int y) {
        AffineTransform identity = new AffineTransform();
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans = new AffineTransform();
        trans.setTransform(identity);        
        rot += 1;
        if (rot >= 360) rot = 0;
        trans.rotate( Math.toRadians(rot),(int)(x+this.image.getWidth(null)/2),(int)(y+this.image.getHeight(null)/2) );
        trans.translate(x, y);
        g2d.drawImage(image, trans, null);
        //g.drawImage(this.image, x, y, null);
    }

    public Image getImage() {
        return this.image;
    }
}

