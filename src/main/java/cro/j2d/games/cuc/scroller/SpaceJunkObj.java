/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Particle;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class SpaceJunkObj
extends Particle {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    public static final int TYPE_4 = 3;

    public SpaceJunkObj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, s, x_tmp, y_tmp, w);
        this.reset();
        this.x = x_tmp;
        this.y = y_tmp;
    }

    public SpaceJunkObj(Vector vec, Sprite s, int w_tmp, int h_tmp, int x_tmp, int y_tmp, World w) {
        super(vec, s, x_tmp, y_tmp, w);
        this.reset();
        this.x = x_tmp;
        this.y = y_tmp;
        if (this.sprite == null) {
            this.width = w_tmp;
            this.height = h_tmp;
            this.xs = -this.width;
        }
    }

    public void reset() {
        if (this.sprite == null) {
            this.width = 1 + (int)(7.0 * Math.random());
            this.height = (int)(3.0 * Math.random());
            this.xs = -this.width;
            this.ys = 0.0;
            this.xa = -this.width;
            this.ya = 0.0;
            this.dxs = -this.width;
            this.dys = 0.0;
        }
        this.x = this.objWorld.getPlayableXSize();
        this.y = this.objWorld.getPlayableY() + (int)((double)(this.objWorld.getPlayableYSize() - this.height) * Math.random());
    }

    private void isVisible() {
        if (this.x < (double)this.objWorld.getPlayableXSize() && this.x + (double)this.width > (double)this.objWorld.getPlayableX()) {
            this.visible = true;
        }
        if (this.x + (double)this.width < 0.0) {
            this.reset();
        }
    }

    public void doLogic() {
        this.move();
    }

    public void draw(Graphics g) {
        if (!this.visible) {
            return;
        }
        if (this.sprite != null) {
            this.sprite.draw(g, (int)this.x, (int)this.y);
            return;
        }
        this.color = new Color(this.colorR, this.colorG, this.colorB);
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
    }

    public void collideWith(Obj obj) {
    }
}

