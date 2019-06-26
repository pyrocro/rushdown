/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Sprite {
    private Image image;

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
        g.drawImage(this.image, x, y, null);
    }

    public Image getImage() {
        return this.image;
    }
}

