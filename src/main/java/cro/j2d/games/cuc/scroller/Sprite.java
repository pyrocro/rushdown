/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class Sprite {
    private Image image;
    private AffineTransform identity = new AffineTransform();

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
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(image, x,y, null);
        //g.drawImage(this.image, x, y, null);
    }
    public void draw(Graphics g, int x, int y,double angle) {
        if(angle > 360 || angle < 0 ) angle = 0;
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans = new AffineTransform();
        trans.setTransform(identity);
        trans.rotate( Math.toRadians(angle),(int)(x+this.image.getWidth(null)/2),(int)(y+this.image.getHeight(null)/2) );
        trans.translate(x, y);
        g2d.drawImage(image, trans, null);
    }

    public Image getImage() {
        return this.image;
    }
}

