/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Enemy3Obj;
import cro.j2d.games.cuc.scroller.Laser;
import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Particle;
import cro.j2d.games.cuc.scroller.Pickup1Obj;
import cro.j2d.games.cuc.scroller.PlayerObj;
import cro.j2d.games.cuc.scroller.Sfx;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.util.Vector;

public class Enemy2Obj
extends Obj {
    public Enemy2Obj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, s, x_tmp, y_tmp, w);
        this.reset();
    }

    public void reset() {
        this.x = (int)((double)this.objWorld.getPlayableXSize() + (double)this.objWorld.getPlayableXSize() * Math.random());
        this.y = this.objWorld.getPlayableY() + (int)((double)(this.objWorld.getPlayableYSize() - this.height) * Math.random());
        this.xs = 0.0;
        this.ys = 0.0;
        this.dxs = -(this.width / 5);
        this.dys = 0.0;
        this.health = 100;
        this.worth = 2000;
        this.xa = 2.0;
        this.ya = 0.25;
    }

    private void isVisible() {
        if (this.x < (double)this.objWorld.getPlayableXSize() && this.x > 0.0) {
            this.visible = true;
        }
        if (this.x + (double)this.width < 0.0) {
            this.reset();
        }
    }

    public void doLogic() {
        Particle ex = null;
        this.move();
        for (int i = 0; i < 1; ++i) {
            ex = new Particle(this.vector, null, (int)this.x + this.width - 8, (int)(this.y + (double)(this.height / 2) - 6.0 + Math.random() * 6.0), this.objWorld);
            ex.setColorR(0.2f);
            ex.setColorG(0.2f);
            ex.setColorB(0.4f);
            ex.setHealth(5);
            ex.setXs(0.0);
            ex.setHeight(8);
            ex.setWidth(30);
            ex.setCRate(-0.04f);
        }
    }

    public void collideWith(Obj obj) {
        if (obj instanceof PlayerObj) {
            obj.setXs(obj.getXs() - 5.0);
            obj.setX(this.x - (double)obj.width);
            obj.setHealth(obj.getHealth() - 25);
            return;
        }
        if (obj instanceof Enemy2Obj || obj instanceof Enemy3Obj) {
            obj.setDxs(obj.dxs * 1.2);
            this.setDxs(this.dxs * 0.8);
            while (Obj.collisionCheck(obj, this)) {
                obj.x -= 1.0;
            }
            return;
        }
    }

    public void doExplosion() {
        int i;
        Particle ex;
        for (i = 0; i < 4; ++i) {
            ex = new Particle(this.vector, null, (int)(this.x + (double)(this.width / 2) * Math.random()), (int)(this.y + (double)(this.height / 4)) + (int)((double)(this.height / 2) * Math.random()), this.objWorld);
            ex.setDxs(this.xs * Math.random() * -1.5);
            ex.setDys(this.ys * Math.random() * 1.5);
            ex.setHealth(30);
            ex.setColorR(0.75f);
            ex.setColorG(0.75f);
            ex.setColorB(1.0f);
            ex.setWidth(2 + (int)(6.0 * Math.random()));
            ex.setHeight(2 + (int)(6.0 * Math.random()));
        }
        for (i = 0; i < 6; ++i) {
            ex = new Particle(this.vector, null, (int)(this.x + (double)(this.width / 2) * Math.random()), (int)(this.y + (double)(this.height / 4)) + (int)((double)this.height * Math.random()), this.objWorld);
            ex.setDxs(this.xs);
            if (Math.random() > 0.5) {
                ex.setDys(2.0 * Math.random());
            } else {
                ex.setDys(2.0 * Math.random() * -1.0);
            }
            ex.setHealth(90);
            ex.setColorR(0.5f);
            ex.setColorG(1.0f);
            ex.setColorB(1.0f);
            ex.setWidth(3 + (int)(4.0 * Math.random()));
            ex.setHeight(3 + (int)(4.0 * Math.random()));
        }
        this.sfx_die.play();
        Pickup1Obj.createHPPickup(1, this);
        Particle.createPTText(this);
    }

    public void doHit(Obj other) {
        if (other instanceof Laser) {
            this.setHealth(this.getHealth() - other.worth);
        }
        for (int i = 0; i < 3; ++i) {
            Particle ex = new Particle(this.vector, null, (int)other.x + other.width, (int)(other.y + (double)(other.height / 2) - 4.0 + Math.random() * 8.0), this.objWorld);
            ex.setDxs(other.xs / 2.0 * Math.random());
            if (Math.random() > 0.5) {
                ex.setDys(3.0 * Math.random());
            } else {
                ex.setDys(3.0 * Math.random() * -1.0);
            }
            ex.setHealth(10);
            ex.setColorR(0.9f);
            ex.setColorG(0.9f);
            ex.setColorB(0.9f);
            ex.setWidth(2 + (int)(5.0 * Math.random()));
            ex.setHeight(2 + (int)(5.0 * Math.random()));
        }
        this.sfx_hit.play();
    }
}

