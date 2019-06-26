/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Particle;
import cro.j2d.games.cuc.scroller.ParticleDust;
import cro.j2d.games.cuc.scroller.PlayerObj;
import cro.j2d.games.cuc.scroller.Sfx;
import cro.j2d.games.cuc.scroller.SfxMan;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.SpriteMan;
import cro.j2d.games.cuc.scroller.World;
import java.util.Vector;

public class Pickup1Obj
extends Obj {
    int wallBounce = 0;

    public Pickup1Obj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, s, x_tmp, y_tmp, w);
        this.x = (int)((double)this.objWorld.getPlayableXSize() + (double)this.objWorld.getPlayableXSize() * Math.random());
        this.y = (int)((double)(this.objWorld.getPlayableYSize() - this.width) * Math.random());
        this.xs = 0.0;
        this.ys = 0.0;
        this.health = 100;
    }

    public void reset() {
        this.vector.remove(this);
    }

    private void isVisible() {
        if (this.x < (double)this.objWorld.getPlayableXSize() && this.x > 0.0 && this.y < (double)this.objWorld.getPlayableYSize() && this.y + (double)this.width > 0.0) {
            this.visible = true;
        }
    }

    private void bounced() {
        if (++this.wallBounce >= 3) {
            this.doExplosion(null);
            Particle.createMissedText(this);
            this.reset();
            return;
        }
        this.sprite = this.objWorld.getSpriteMan().getSprite("pickup" + (this.wallBounce + 1));
        this.health /= 2;
        this.worth /= 2;
    }

    public void doLogic() {
        this.move();
        if (this.x < (double)this.objWorld.getPlayableX() || this.x + (double)this.width > (double)this.objWorld.getPlayableXMax()) {
            this.dxs *= -1.0;
            this.xs *= -1.0;
            this.bounced();
        }
        if (this.y < (double)this.objWorld.getPlayableY() || this.y + (double)this.height > (double)this.objWorld.getPlayableYMax()) {
            this.dys *= -1.0;
            this.ys *= -1.0;
            this.bounced();
        }
        this.isVisible();
    }

    public void collideWith(Obj obj) {
        if (obj instanceof PlayerObj) {
            PlayerObj pObj = (PlayerObj)obj;
            this.sfx_hit.play();
            pObj.incScore(this.worth);
            this.doExplosion(pObj);
            Particle.createHPText(this);
            Particle.createPTText(this);
            this.reset();
            return;
        }
    }

    public void doExplosion(Obj obj) {
        if (obj == null) {
            return;
        }
        ParticleDust.createHealthDust(10, this, obj);
    }

    public static void createHPPickup(int amt, Obj obj) {
        for (int i = 0; i < amt; ++i) {
            Pickup1Obj ex = new Pickup1Obj(obj.objWorld.getVecEnemies(), obj.objWorld.getSpriteMan().getSprite("pickup1"), (int)obj.x, (int)obj.y, obj.objWorld);
            ex.setWorth(250);
            ex.setHealth(100);
            ex.x = obj.x;
            ex.y = obj.y;
            ex.setSfx_hit(obj.objWorld.getSfxMan().searchForClip("sbounce"));
            ex.setDxs(obj.xs * 1.5);
            if (Math.random() > 0.5) {
                ex.setDys((2.0 + 2.0 * Math.random()) * -1.0);
                continue;
            }
            ex.setDys(2.0 + 2.0 * Math.random());
        }
    }

    public static void createPTPickup(int amt, Obj obj) {
        for (int i = 0; i < amt; ++i) {
            Pickup1Obj ex = new Pickup1Obj(obj.objWorld.getVecEnemies(), obj.objWorld.getSpriteMan().getSprite("pickup2"), (int)obj.x, (int)obj.y, obj.objWorld);
            ex.setHealth(100);
            ex.setWorth(1000);
            ex.x = obj.x;
            ex.y = obj.y;
            ex.setSfx_hit(obj.objWorld.getSfxMan().searchForClip("sbounce"));
            ex.setDxs(obj.xs * 1.5);
            if (Math.random() > 0.5) {
                ex.setDys((2.0 + 2.0 * Math.random()) * -1.0);
                continue;
            }
            ex.setDys(2.0 + 2.0 * Math.random());
        }
    }
}

