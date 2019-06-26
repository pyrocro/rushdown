/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Sfx;
import cro.j2d.games.cuc.scroller.SfxMan;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public abstract class Obj {
    protected Obj target = null;
    static long timer = 0L;
    volatile World objWorld = null;
    protected Obj owner = null;
    protected int worth = 0;
    protected volatile double x;
    protected volatile double y;
    protected volatile double xs;
    protected volatile double ys;
    protected volatile double dxs;
    protected volatile double dys;
    protected volatile double xa;
    protected volatile double ya;
    protected volatile int width;
    protected volatile int height;
    protected volatile Sprite sprite;
    protected volatile Sfx sfx_shoot1;
    protected Sfx sfx_shoot2;
    protected Sfx sfx_shoot3;
    protected Sfx sfx_hit;
    protected Sfx sfx_die;
    protected Vector vector = null;
    protected Rectangle self = new Rectangle();
    protected Rectangle other = new Rectangle();
    protected int health = 0;
    private String str;
    protected boolean visible = false;
    protected int vecIndex;

    public int getVecIndex() {
        return this.vecIndex;
    }

    private void isVisible() {
        if (this.x < (double)this.objWorld.getPlayableXMax() && this.x + (double)this.width > 0.0 && this.y + (double)this.height > 0.0 && this.y < (double)this.objWorld.getPlayableYMax()) {
            this.visible = true;
        } else {
            this.reset();
        }
    }

    public Obj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        this.vector = vec;
        this.sprite = s;
        this.x = x_tmp;
        this.y = y_tmp;
        this.objWorld = w;
        if (s != null) {
            this.width = s.getWidth();
            this.height = s.getHeight();
        }
        this.vector.add(this);
        this.vecIndex = this.vector.indexOf(this);
        this.xa = this.objWorld.getPlayableXSize();
        this.ya = this.objWorld.getPlayableYSize();
        this.dxs = 0.0;
        this.dys = 0.0;
        this.sfx_die = w.getSfxMan().getNullSound();
        this.sfx_hit = w.getSfxMan().getNullSound();
        this.sfx_shoot1 = w.getSfxMan().getNullSound();
        this.sfx_shoot2 = w.getSfxMan().getNullSound();
        this.sfx_shoot3 = w.getSfxMan().getNullSound();
    }

    public void move(long delta) {
        this.x += this.xs * (double)delta / 1000.0;
        this.y += this.ys * (double)delta / 1000.0;
        this.isVisible();
    }

    public void move() {
        if (this.xs < this.dxs) {
            this.xs += this.xa;
            if (this.xs > this.dxs) {
                this.xs = this.dxs;
            }
        }
        if (this.xs > this.dxs) {
            this.xs -= this.xa;
            if (this.xs < this.dxs) {
                this.xs = this.dxs;
            }
        }
        if (this.ys < this.dys) {
            this.ys += this.ya;
            if (this.ys > this.dys) {
                this.ys = this.dys;
            }
        }
        if (this.ys > this.dys) {
            this.ys -= this.ya;
            if (this.ys < this.dys) {
                this.ys = this.dys;
            }
        }
        this.x += this.xs;
        this.y += this.ys;
        this.isVisible();
    }

    private String getStr() {
        return this.str;
    }

    private void setStr(String s) {
        this.str = s;
    }

    public void setXs(double xspeed) {
        this.xs = xspeed;
    }

    public void setYs(double yspeed) {
        this.ys = yspeed;
    }

    public double getXs() {
        return this.xs;
    }

    public void setX(double xPos) {
        this.x = xPos;
    }

    public void setY(double yPos) {
        this.y = yPos;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public double getScreenX() {
        return this.objWorld.getPlayableXSize();
    }

    public double getscreenY() {
        return this.objWorld.getPlayableYSize();
    }

    public void draw(Graphics g) {
        this.visible = false;
        if (false) {
            return;
        }
        if (this.sprite != null) {
            this.sprite.draw(g, (int)this.x, (int)this.y);
            return;
        }
    }

    public void reset() {
        this.x = (int)((double)this.objWorld.getPlayableXSize() + (double)this.objWorld.getPlayableXSize() * Math.random());
        this.y = (int)((double)this.objWorld.getPlayableYSize() * Math.random());
        this.width = (int)(7.0 * Math.random());
        this.height = (int)(3.0 * Math.random());
        this.xs = -this.width;
        this.ys = 0.0;
        this.visible = false;
    }

    public abstract void doLogic();

    public void doExplosion() {
    }

    public void doHit(Obj other) {
    }

    public static boolean collisionCheck(Obj s, Obj o) {
        if (s == o) {
            return false;
        }
        return s.x + (double)s.width >= o.x && s.x <= o.x + (double)o.width && s.y + (double)s.height >= o.y && s.y <= o.y + (double)o.height;
    }

    public boolean collisionCheck(Obj obj) {
        if (this == obj) {
            return false;
        }
        this.self.setBounds((int)this.x, (int)this.y, this.width, this.height);
        this.other.setBounds((int)obj.getX(), (int)obj.getY(), obj.width, obj.height);
        return this.self.intersects(this.other);
    }

    public abstract void collideWith(Obj var1);

    protected int getHealth() {
        return this.health;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    public Sfx getSfx_die() {
        return this.sfx_die;
    }

    public void setSfx_die(Sfx sfx_die) {
        this.sfx_die = sfx_die;
    }

    public Sfx getSfx_hit() {
        return this.sfx_hit;
    }

    public void setSfx_hit(Sfx sfx_hit) {
        this.sfx_hit = sfx_hit;
    }

    public Sfx getSfx_shoot1() {
        return this.sfx_shoot1;
    }

    public void setSfx_shoot1(Sfx sfx_shoot1) {
        this.sfx_shoot1 = sfx_shoot1;
    }

    public Sfx getSfx_shoot2() {
        return this.sfx_shoot2;
    }

    public void setSfx_shoot2(Sfx sfx_shoot2) {
        this.sfx_shoot2 = sfx_shoot2;
    }

    public Sfx getSfx_shoot3() {
        return this.sfx_shoot3;
    }

    public void setSfx_shoot3(Sfx sfx_shoot3) {
        this.sfx_shoot3 = sfx_shoot3;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    public Obj getOwner() {
        return this.owner;
    }

    public void setOwner(Obj owner) {
        this.owner = owner;
    }

    public int getWorth() {
        return this.worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }

    public double getDxs() {
        return this.dxs;
    }

    public void setDxs(double xs) {
        this.dxs = xs;
    }

    public double getDys() {
        return this.dys;
    }

    public void setDys(double ys) {
        this.dys = ys;
    }

    public double getXa() {
        return this.xa;
    }

    public void setXa(double xa) {
        this.xa = xa;
    }

    public double getYa() {
        return this.ya;
    }

    public void setYa(double ya) {
        this.ya = ya;
    }
}

