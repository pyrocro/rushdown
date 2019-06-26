/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Particle;
import cro.j2d.games.cuc.scroller.PlayerObj;
import cro.j2d.games.cuc.scroller.SfxMan;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.util.Vector;

public class ParticleDust
extends Particle {
    static final int TYPE_HEALTH_UP = 1;
    static final int TYPE_POINTS_UP = 10;
    static final int TYPE_DARK_PLAUGE = 20;
    protected int dustType = 10;
    static final int exlosionSize = 4;

    public ParticleDust(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, s, x_tmp, y_tmp, w);
    }

    public void move() {
        if (this.target != null) {
            if (this.target.x + (double)(this.target.width / 2) < this.x && this.dxs > 0.0) {
                this.dxs *= -1.0;
            }
            if (this.target.x + (double)(this.target.width / 2) > this.x && this.dxs < 0.0) {
                this.dxs *= -1.0;
            }
            if (this.target.y + (double)(this.target.height / 2) < this.y && this.dys > 0.0) {
                this.dys *= -1.0;
            }
            if (this.target.y + (double)(this.target.height / 2) > this.y && this.dys < 0.0) {
                this.dys *= -1.0;
            }
        }
        super.move();
        if (this.target != null && Obj.collisionCheck(this, this.target)) {
            this.targetReached();
        }
    }

    private void targetReached() {
        switch (this.dustType) {
            case 1: {
                if (!(this.target instanceof PlayerObj)) break;
                PlayerObj pObj = (PlayerObj)this.target;
                pObj.incHealth(this.worth);
                this.objWorld.getSfxMan().playClip("ding");
                this.reset();
                break;
            }
            case 10: {
                if (!(this.target instanceof PlayerObj)) break;
                PlayerObj pObj = (PlayerObj)this.target;
                pObj.incScore(this.worth);
                this.reset();
                break;
            }
            case 20: {
                if (!(this.target instanceof PlayerObj)) break;
                PlayerObj pObj = (PlayerObj)this.target;
                pObj.decHealth(this.worth);
                this.reset();
            }
        }
    }

    public static void createHealthDust(int amt, Obj obj, Obj tget) {
        for (int i = 0; i < amt; ++i) {
            ParticleDust ex = new ParticleDust(obj.vector, null, (int)(obj.x - (double)(obj.width * 2) + Math.random() * (double)(obj.width * 4)), (int)(obj.y - (double)(obj.height * 2) + Math.random() * (double)(obj.height * 4)), obj.objWorld);
            if (Math.random() > 0.5) {
                ex.setDxs(9.0 + 3.0 * Math.random());
            } else {
                ex.setDxs(9.0 + 3.0 * Math.random() * -1.0);
            }
            if (Math.random() > 0.5) {
                ex.setDys(9.0 + 3.0 * Math.random());
            } else {
                ex.setDys(9.0 + 3.0 * Math.random() * -1.0);
            }
            ex.setHealth(80);
            ex.setColorR((float)Math.random());
            ex.setColorG(1.0f);
            ex.setColorB((float)Math.random());
            ex.setWidth(3);
            ex.setHeight(3);
            ex.setCRate(-0.01f);
            ex.setXa(1.2);
            ex.setYa(1.2);
            ex.setXs(obj.xs);
            ex.setYs(obj.ys);
            ex.target = tget;
            ex.worth = 4;
            ex.dustType = 1;
        }
        obj.objWorld.getSfxMan().playClip("suck_in");
    }

    public static void createPointsDust(int amt, Obj obj, Obj tget) {
        for (int i = 0; i < amt; ++i) {
            ParticleDust ex = new ParticleDust(obj.vector, null, (int)(obj.x - (double)(obj.width * 2) + Math.random() * (double)(obj.width * 4)), (int)(obj.y - (double)(obj.height * 2) + Math.random() * (double)(obj.height * 4)), obj.objWorld);
            if (Math.random() > 0.5) {
                ex.setDxs(6.0 + 3.0 * Math.random());
            } else {
                ex.setDxs(6.0 + 3.0 * Math.random() * -1.0);
            }
            if (Math.random() > 0.5) {
                ex.setDys(6.0 + 3.0 * Math.random());
            } else {
                ex.setDys(6.0 + 3.0 * Math.random() * -1.0);
            }
            ex.setHealth(80);
            ex.setColorR(1.0f);
            ex.setColorG(0.5f);
            ex.setColorB(0.6f);
            ex.setWidth(3);
            ex.setHeight(3);
            ex.setCRate(-0.01f);
            ex.setXa(0.6);
            ex.setYa(0.6);
            ex.target = tget;
            ex.worth = 400;
            ex.dustType = 10;
        }
    }

    public static void createDarkDust(int amt, Obj obj, Obj tget) {
        for (int i = 0; i < amt; ++i) {
            ParticleDust ex = new ParticleDust(obj.vector, null, (int)(obj.x - (double)(obj.width * 2) + Math.random() * (double)(obj.width * 4)), (int)(obj.y - (double)(obj.height * 2) + Math.random() * (double)(obj.height * 4)), obj.objWorld);
            if (Math.random() > 0.5) {
                ex.setDxs(6.0 + 3.0 * Math.random());
            } else {
                ex.setDxs(6.0 + 3.0 * Math.random() * -1.0);
            }
            if (Math.random() > 0.5) {
                ex.setDys(6.0 + 3.0 * Math.random());
            } else {
                ex.setDys(6.0 + 3.0 * Math.random() * -1.0);
            }
            ex.setHealth(80);
            ex.setColorR(0.4f);
            ex.setColorG(0.2f);
            ex.setColorB(0.2f);
            ex.setWidth(3);
            ex.setHeight(3);
            ex.setCRate(0.05f);
            ex.setXa(0.6);
            ex.setYa(0.6);
            ex.target = tget;
            ex.worth = 2;
            ex.dustType = 20;
        }
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

