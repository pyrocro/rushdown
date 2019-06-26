/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Enemy2Obj;
import cro.j2d.games.cuc.scroller.Laser;
import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Particle;
import cro.j2d.games.cuc.scroller.Pickup1Obj;
import cro.j2d.games.cuc.scroller.PlayerObj;
import cro.j2d.games.cuc.scroller.Sfx;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.util.Vector;

public class Enemy3Obj
extends Obj {
    public Enemy3Obj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
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
        this.health = 200;
        this.worth = 4000;
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
        this.move();
    }

    public void collideWith(Obj obj) {
        if (obj instanceof PlayerObj) {
            obj.setXs((obj.getXs() + 1.0) * -1.0);
            obj.setX(this.x - (double)obj.width);
            obj.setHealth(obj.getHealth() - 50);
            return;
        }
        if (obj instanceof Pickup1Obj) {
            return;
        }
        if (obj instanceof Enemy2Obj || obj instanceof Enemy3Obj) {
            obj.setXs(obj.getXs() * 1.2);
            this.setXs(this.xs * 0.8);
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
            ex.setDxs(this.dxs * Math.random() * -1.5);
            ex.setDys(this.dys * Math.random() * 1.5);
            ex.setHealth(30);
            ex.setColorR(1.0f);
            ex.setColorG(0.5f);
            ex.setColorB(0.5f);
            ex.setWidth(3 + (int)(5.0 * Math.random()));
            ex.setHeight(3 + (int)(5.0 * Math.random()));
        }
        for (i = 0; i < 7; ++i) {
            ex = new Particle(this.vector, null, (int)(this.x + (double)(this.width / 2) * Math.random()), (int)(this.y + (double)(this.height / 3)) + (int)((double)(this.height / 3) * Math.random()), this.objWorld);
            ex.setDxs(this.dxs * (4.0 * Math.random()) * -1.0);
            ex.setHealth(20);
            ex.setColorR(0.5f);
            ex.setColorG(0.9f);
            ex.setColorB(0.5f);
            ex.setWidth(5 + (int)(4.0 * Math.random()));
            ex.setHeight(5 + (int)(4.0 * Math.random()));
        }
        this.sfx_die.play();
        Pickup1Obj.createHPPickup(1, this);
        Particle.createPTText(this);
    }

    public void doHit(Obj other) {
        if (other instanceof Laser) {
            this.setHealth(this.getHealth() - 100);
        }
        for (int i = 0; i < 3; ++i) {
            Particle ex = new Particle(this.vector, null, (int)other.x, (int)(other.y + Math.random() * 3.0), this.objWorld);
            ex.setDxs(this.dxs * Math.random());
            if (Math.random() > 0.5) {
                ex.setDys((1.0 + 2.0 * Math.random()) * -1.0);
            } else {
                ex.setDys(1.0 + 2.0 * Math.random());
            }
            ex.setHealth(20);
            ex.setColorR(1.0f);
            ex.setColorG(0.4f);
            ex.setColorB(0.4f);
            ex.setCRate(-0.06f);
            ex.setWidth(3 + (int)(2.0 * Math.random()));
            ex.setHeight(3 + (int)(2.0 * Math.random()));
        }
        this.sfx_hit.play();
    }
}

