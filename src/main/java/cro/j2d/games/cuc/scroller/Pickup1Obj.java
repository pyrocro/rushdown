// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Pickup1Obj.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Graphics;
import java.util.Vector;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Obj, World, Particle, SpriteMan, 
//            PlayerObj, Sfx, ParticleDust, SfxMan, 
//            Sprite

public class Pickup1Obj extends Obj
{

    public Pickup1Obj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        wallBounce = 0;
        x = (int)((double)objWorld.getPlayableXSize() + (double)objWorld.getPlayableXSize() * Math.random());
        y = (int)((double)(objWorld.getPlayableYSize() - width) * Math.random());
        xs = 0.0D;
        ys = 0.0D;
        health = 100;
        tangible = true;
    }

    public void reset()
    {
        removeFromScreen();
        vector.remove(this);
    }
    

    private void isVisible()
    {
        if(x < (double)objWorld.getPlayableXSize() && x > 0.0D && y < (double)objWorld.getPlayableYSize() && y + (double)width > 0.0D)
            visible = true;
    }

    private void bounced()
    {
        if(++wallBounce >= 3)
        {
            doExplosion(null);
            Particle.createMissedText(this);
            reset();
            return;
        } else
        {
            sprite = objWorld.getSpriteMan().getSprite("pickup" + (wallBounce + 1));
            health /= 2;
            worth /= 2;
            return;
        }
    }
    public void removeFromScreen(){
        super.removeFromScreen();
    }

    public void doLogic()
    {
        move();
        if( x < (double)objWorld.getPlayableX() )
        {
        	x = objWorld.getPlayableX();
            dxs *= -1D;
            xs *= -1D;
            bounced();
        }
        if(x + (double)width > (double)objWorld.getPlayableXMax())
        {
        	x = objWorld.getPlayableXMax()-width;
            dxs *= -1D;
            xs *= -1D;
            bounced();
        }
        if(y < (double)objWorld.getPlayableY() )
        {
        	y = objWorld.getPlayableY();
            dys *= -1D;
            ys *= -1D;
            bounced();
        }
        if(y + (double)height > (double)objWorld.getPlayableYMax())
        {
        	y = objWorld.getPlayableYMax()-height;
            dys *= -1D;
            ys *= -1D;
            bounced();
        }
        isVisible();
    }

    public void collideWith(Obj obj)
    {
        if(obj instanceof PlayerObj)
        {
            PlayerObj pObj = (PlayerObj)obj;
            sfx_hit.play();
            pObj.incScore(worth);
            doExplosion(pObj);
            Particle.createHPText(this);
            Particle.createPTText(this);
            reset();
            return;
        } else
        {
            return;
        }
    }

    public void doExplosion(Obj obj)
    {
        if(obj == null)
        {
            return;
        } else
        {
            //ParticleDust.createHealthDust(10, this, obj);
            return;
        }
    }

    public static void createHPPickup(int amt, Obj obj)
    {
        for(int i = 0; i < amt; i++)
        {
            Pickup1Obj ex = new Pickup1Obj(obj.objWorld.getVecEnemies(), obj.objWorld.getSpriteMan().getSprite("pickup1"), (int)obj.x, (int)obj.y, obj.objWorld);
            ex.setWorth(250);
            ex.setHealth(100);
            ex.x = obj.x;
            ex.y = obj.y;
            ex.setSfx_hit(obj.objWorld.getSfxMan().searchForClip("sbounce"));
            ex.setDxs(obj.xs * 0.3D);
            if(Math.random() > 0.3D)
                ex.setDys((4 * Math.random()) * -1D);
            else
                ex.setDys(4 * Math.random());
        }

    }

    public static void createPTPickup(int amt, Obj obj)
    {
        for(int i = 0; i < amt; i++)
        {
            Pickup1Obj ex = new Pickup1Obj(obj.objWorld.getVecEnemies(), obj.objWorld.getSpriteMan().getSprite("pickup2"), (int)obj.x, (int)obj.y, obj.objWorld);
            ex.setHealth(100);
            ex.setWorth(1000);
            ex.x = obj.x;
            ex.y = obj.y;
            ex.setSfx_hit(obj.objWorld.getSfxMan().searchForClip("sbounce"));
            ex.setDxs(obj.xs * 1.5D);
            if(Math.random() > 0.5D)
                ex.setDys((2D + 2D * Math.random()) * -1D);
            else
                ex.setDys(2D + 2D * Math.random());
        }

    }

    int wallBounce;
}