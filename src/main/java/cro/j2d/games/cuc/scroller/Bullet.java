/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintStream;
import java.util.Vector;

public class Bullet
extends Obj {
    public Bullet(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, null, x_tmp, y_tmp, w);
        this.width = 20;
        this.height = 2;
        this.x = x_tmp;
        this.y = y_tmp;
        this.xs = this.width;
        this.ys = 0.0;
    }

    public void reset() {
        this.vector.remove(this);
    }

    private void isVisible() {
        if (this.x < (double)this.objWorld.getPlayableXSize() && this.x + (double)this.width > 0.0 && this.y + (double)this.height > 0.0 && this.y < (double)this.objWorld.getPlayableYSize()) {
            this.visible = true;
        } else {
            this.reset();
            System.out.println("removed Laser....");
        }
    }

    public void doLogic() {
        this.move();
    }

    public void draw(Graphics g) {
        if (!this.visible) {
            return;
        }
        g.setColor(Color.CYAN);
        g.fill3DRect((int)this.x, (int)this.y, this.width, this.height, true);
    }

    public void collideWith(Obj obj) {
    }
}

