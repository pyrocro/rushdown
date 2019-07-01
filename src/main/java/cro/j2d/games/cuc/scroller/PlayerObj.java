/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.InputClass;
import cro.j2d.games.cuc.scroller.Laser;
import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Particle;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.util.Vector;

public class PlayerObj
extends Obj {
    public static double ACCELERATION = 1.7;
    public static double RETARDATION = 1.1;
    public final int HEALTH_MAX = 400;
    private long shot1Wait = 0L;
    private long shot2Wait = 0L;
    private long shot3Wait = 0L;
    private long thrustWait = 0L;
    final double MAX_SPEED = 10.0;
    private Vector vecShots = null;
    private int score;
    private int pickups;

    protected int getScore() {
        return this.score;
    }

    protected void setScore(int score) {
        this.score = score;
    }

    public void setVecShots(Vector vec) {
        this.vecShots = vec;
    }

    public Vector getVecShots() {
        return this.vecShots;
    }

    public PlayerObj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, s, x_tmp, y_tmp, w);
        this.x = x_tmp;
        this.y = y_tmp;
        this.dxs = 25.0;
        this.dys = 0.0;
        this.health = 400;
        this.xa = 1.5;
        this.ya = 1.5;
    }

    public void doLogic() {
        int i;
        Particle ex = null;
        if (this.dxs > 0.0) {
            this.dxs -= RETARDATION;
            if (this.dxs < 0.0) {
                this.dxs = 0.0;
            }
        } else if (this.dxs < 0.0) {
            this.dxs += RETARDATION;
            if (this.dxs > 0.0) {
                this.dxs = 0.0;
            }
        }
        if (this.dys > 0.0) {
            this.dys -= RETARDATION;
            if (this.dys < 0.0) {
                this.dys = 0.0;
            }
        } else if (this.dys < 0.0) {
            this.dys += RETARDATION;
            if (this.dys > 0.0) {
                this.dys = 0.0;
            }
        }
        for (i = 0; i < 10; ++i) {
            ex = new Particle(this.vector, null, (int)this.x, (int)(this.y + (double)(this.height / 2) - 5.0 + Math.random() * 8.0), this.objWorld);
            ex.setColorR(0.6f);
            ex.setColorG(0.6f);
            ex.setColorB(1.0f);
            ex.setHealth(5);
            ex.setDxs(-2.0);
            ex.setHeight(3);
            ex.setWidth(5);
            ex.setCRate(-0.06f);
        }
        if (Obj.timer >= this.thrustWait) {
            this.thrustWait = Obj.timer + 2L;
            ex = new Particle(this.vector, null, (int)this.x, (int)(this.y + (double)(this.height / 2) - 5.0 + Math.random() * 8.0), this.objWorld);
            ex.setColorR(0.9f);
            ex.setColorG(0.7f);
            ex.setColorB(0.1f);
            ex.setHealth(5);
            ex.setDxs(-0.5);
            ex.setHeight(2);
            ex.setWidth(5);
            ex.setCRate(-0.06f);
            ex = new Particle(this.vector, null, (int)this.x, (int)(this.y + (double)(this.height / 2) - 4.0 + Math.random() * 8.0), this.objWorld);
            ex.setWidth(0);
            if (Math.random() > 0.5) {
                ex.setHeight(1 + (int)(2.0 * Math.random()));
            } else {
                ex.setHeight(-(1 + (int)(2.0 * Math.random())));
            }
            ex.setDxs(ex.width);
            ex.setDys(ex.height);
            ex.setHealth(20);
            ex.setColorR(0.5f);
            ex.setColorG(1.0f);
            ex.setColorB(0.5f);
            ex.setCRate(-0.07f);
            ex.setType(20);
        }
        if (InputClass.getPlayer1Left() && this.dxs >= -10.0) {
            this.dxs -= ACCELERATION;
        }
        if (InputClass.getPlayer1Right() && this.dxs <= 10.0) {
            this.dxs += ACCELERATION;
            for (i = 0; i < 100; ++i) {
                ex = new Particle(this.vector, null, (int)this.x, (int)(this.y + (double)(this.height / 2) - 4.0 + Math.random() * 8.0), this.objWorld);
                ex.setWidth((int)(this.xs * Math.random() * -2.5));
                if (Math.random() > 0.5) {
                    ex.setHeight(1 + (int)(2.0 * Math.random()));
                } else {
                    ex.setHeight(-(1 + (int)(2.0 * Math.random())));
                }
                ex.setDxs(ex.width);
                ex.setDys(ex.height);
                ex.setHealth(20);
                ex.setColorR(0.5f);
                ex.setColorG(1.0f);
                ex.setColorB(0.5f);
                ex.setCRate(-0.07f);
                ex.setType(20);
            }
        }
        if (InputClass.getPlayer1Up() && this.dys >= -10.0) {
            this.dys -= ACCELERATION;
            for (i = 0; i < 10; ++i) {
                ex = new Particle(this.vector, null, (int)(this.x + (double)(this.width / 2) - 4.0 + Math.random() * 8.0), (int)this.y + this.height, this.objWorld);
                ex.setHeight((int)(this.ys * Math.random() * -1.5));
                if (Math.random() > 0.5) {
                    ex.setWidth(2 + (int)(3.0 * Math.random()));
                } else {
                    ex.setWidth(-(2 + (int)(3.0 * Math.random())));
                }
                ex.setDxs(ex.width);
                ex.setDys(ex.height);
                ex.setHealth(20);
                ex.setColorR(0.5f);
                ex.setColorG(1.0f);
                ex.setColorB(0.5f);
                ex.setCRate(-0.1f);
                ex.setType(20);
            }
        }
        if (InputClass.getPlayer1Down() && this.dys <= 10.0) {
            this.dys += ACCELERATION;
            for (i = 0; i < 10; ++i) {
                ex = new Particle(this.vector, null, (int)(this.x + (double)(this.width / 2) - 4.0 + Math.random() * 8.0), (int)this.y, this.objWorld);
                ex.setHeight((int)(this.ys * Math.random() * -1.5));
                if (Math.random() > 0.5) {
                    ex.setWidth(2 + (int)(3.0 * Math.random()));
                } else {
                    ex.setWidth(-(2 + (int)(3.0 * Math.random())));
                }
                ex.setDxs(ex.width);
                ex.setDys(ex.height);
                ex.setHealth(20);
                ex.setColorR(0.5f);
                ex.setColorG(1.0f);
                ex.setColorB(0.5f);
                ex.setCRate(-0.1f);
                ex.setType(20);
            }
        }
        if (InputClass.getPlayer1Fire1() && Obj.timer >= this.shot1Wait) {
            this.shot1Wait = Obj.timer + 9L;
            Laser.createLaser(this, this.vecShots, this.x + (double)(this.width / 3), this.y - 5.0).setShift(2);
            Laser.createLaser(this, this.vecShots, this.x + (double)(this.width / 3), this.y + (double)this.height).setShift(1);
        }
        if (InputClass.getPlayer1Fire2() && Obj.timer >= this.shot2Wait) {
            this.shot1Wait = Obj.timer + 20L;
        }
        if (InputClass.getPlayer1Fire3() && Obj.timer >= this.shot3Wait) {
            this.shot1Wait = Obj.timer + 5000L;
        }
        this.move();
    }

    public void move() {
        super.move();
        if (this.x < (double)this.objWorld.getPlayableX()) {
            this.x = this.objWorld.getPlayableX();
            this.xs = 0.0;
        }
        if (this.x + (double)this.width > (double)this.objWorld.getPlayableXMax()) {
            this.x = this.objWorld.getPlayableXMax() - this.width;
            this.xs = 0.0;
        }
        if (this.y < (double)this.objWorld.getPlayableY()) {
            this.y = this.objWorld.getPlayableY();
            this.ys = 0.0;
        }
        if (this.y + (double)this.height > (double)this.objWorld.getPlayableYMax()) {
            this.y = this.objWorld.getPlayableYMax() - this.height;
            this.ys = 0.0;
        }
    }

    public void incHealth(int amt) {
        this.getClass();
        if (this.health + amt == 400) {
            this.incScore(amt);
            return;
        }
        this.getClass();
        if (this.health + amt < 400) {
            this.health += amt;
        } else {
            this.getClass();
            this.health = 400;
        }
    }

    public void decHealth(int amt) {
        this.health = this.health - amt > 0 ? (this.health -= amt) : 1;
    }

    public void incScore(int amt) {
        this.score += amt;
    }

    public void reset() {
    }

    public void move(long delta) {
    }

    public void collideWith(Obj obj) {
    }
}

