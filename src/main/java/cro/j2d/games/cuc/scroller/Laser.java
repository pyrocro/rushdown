/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Enemy2Obj;
import cro.j2d.games.cuc.scroller.Enemy3Obj;
import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Particle;
import cro.j2d.games.cuc.scroller.PlayerObj;
import cro.j2d.games.cuc.scroller.Sfx;
import cro.j2d.games.cuc.scroller.SfxMan;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class Laser
extends Particle {
    final int TYPE_RICOCHET = 10;
    final int TYPE_NORMAL = 1;
    static final int SHIFT_UP = 1;
    static final int SHIFT_DOWN = 2;
    protected int shift = 0;
    protected int laserType = 1;

    public Laser(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, null, x_tmp, y_tmp, w);
        this.width = 10;
        this.height = 7;
        this.type = 1;
        this.xa = this.width;
        this.setColor(Color.CYAN);
        this.drawFx = 1;
        this.worth = 100;
    }

    public void reset() {
        this.vector.remove(this);
    }

    private void isVisible() {
        if (this.x < (double)this.objWorld.getPlayableXMax() && this.x + (double)this.width > (double)this.objWorld.getPlayableX() && this.y + (double)this.height > (double)this.objWorld.getPlayableY() && this.y < (double)this.objWorld.getPlayableYMax()) {
            this.visible = true;
        } else {
            this.visible = false;
            this.reset();
        }
    }

    public void doLogic() {
        this.move();
        if (this.laserType == 1) {
            this.dxs += 2.0;
            this.width = (int)this.dxs;
            if (this.height > 1) {
                this.height = (int)((double)this.height - 0.1);
                if (this.shift == 1) {
                    this.y -= 0.3;
                }
                if (this.shift == 2) {
                    this.y += 0.3;
                }
            }
        }
    }

    public void draw(Graphics g) {
        super.draw(g);
    }

    public void collideWith(Obj obj) {
        if ((obj instanceof Enemy2Obj || obj instanceof Enemy3Obj) && this.owner instanceof PlayerObj) {
            if (this.laserType == 10) {
                return;
            }
            obj.doHit(this);
            obj.xs += this.xs / 3.0;
            if (obj.getHealth() < 0) {
                obj.doExplosion();
                obj.reset();
                PlayerObj p = (PlayerObj)this.owner;
                p.incScore(obj.worth);
            }
            this.doExplosion();
            this.reset();
        }
    }

    public void doExplosion() {
        if (Math.random() > 0.8) {
            return;
        }
        double sp = 0.0;
        switch ((int)(Math.random() * 3.0)) {
            case 0: {
                this.objWorld.getSfxMan().playClip("ricochet00");
                sp = 0.5;
                break;
            }
            case 1: {
                this.objWorld.getSfxMan().playClip("ricochet01");
                sp = 1.0;
                break;
            }
            case 2: {
                this.objWorld.getSfxMan().playClip("ricochet02");
                sp = 1.5;
                break;
            }
            default: {
                this.objWorld.getSfxMan().playClip("ricochet00");
                sp = 0.5;
            }
        }
        for (int i = 0; i < 12; ++i) {
            Laser ex = new Laser(this.vector, null, (int)this.x, (int)(this.y + Math.random() * 3.0), this.objWorld);
            ex.setWidth((int)(this.xs * Math.random() * sp));
            if (Math.random() > 0.5) {
                ex.setHeight(1 + (int)(8.0 * Math.random()));
            } else {
                ex.setHeight(-(1 + (int)(8.0 * Math.random())));
            }
            float green = 0.7f + ((float)((3.0 * Math.random())/10f));
            ex.setDxs(ex.width);
            ex.setDys(ex.height);
            ex.setHealth(90);
            ex.setColorR(0.3f);
            ex.setColorG(green);
            ex.setColorB(1.0f);
            ex.setCRate(-0.03f);
            ex.setType(20);
            ex.laserType = 10;
            ex.drawFx = 2;
            ex.setOwner(this.owner);
        }
    }

    public static Laser createLaser(Obj obj, Vector vec, double lx, double ly) {
        Laser l1 = new Laser(vec, null, (int)lx, (int)ly, obj.objWorld);
        l1.setOwner(obj);
        l1.setXs((double)l1.width * 0.2);
        obj.sfx_shoot1.play();
        Laser.createLaserShockwave(l1);
        return l1;
    }

    public static void createLaserShockwave(Laser l1) {
        Particle ex = null;
        for (int i = 0; i < 20; ++i) {
            ex = new Particle(l1.vector, null, (int)l1.x, (int)l1.y, l1.objWorld);
            ex.setWidth(2);
            ex.setHeight(3);
            ex.setDxs(-1.0);
            if (i == 0) {
                ex.setDys(-3.0);
            } else {
                ex.setDys(3.0);
            }
            ex.setHealth(20);
            ex.setColorR(0.96f);
            ex.setColorG(0.8f);
            ex.setColorB(1.0f);
            ex.setCRate(-0.07f);
        }
    }

    public int getShift() {
        return this.shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}

