// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Laser.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Particle, World, Enemy2Obj, Enemy3Obj, 
//            PlayerObj, Obj, Sfx, Sprite

public class Laser extends Particle
{

    public Laser(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
    {
        super(vec, null, x_tmp, y_tmp, w);
        shift = 0;
        laserType = 1;
        width = 10;
        height = 3;
        type = 1;
        xa = width;
        setColor(Color.MAGENTA);
        drawFx = 1;
        worth = 100;
        tangible = true;
    }

    public void reset()
    {
        removeFromScreen();
        vector.remove(this);
    }

    private void isVisible()
    {
        if(x < (double)objWorld.getPlayableXMax() && x + (double)width > (double)objWorld.getPlayableX() && y + (double)height > (double)objWorld.getPlayableY() && y < (double)objWorld.getPlayableYMax())
        {
            visible = true;
        } else
        {
            visible = false;
            reset();
        }
    }

    public void doLogic()
    {
        move();
        if(laserType == 1)
        {
            dxs += .6;
            width = (int)dxs;
            if(height > 3)
            {
                height -= 0.10000000000000001D;
                if(shift == 1)
                    y -= 0.29999999999999999D;
                if(shift == 2)
                    y += 0.29999999999999999D;
                if(height<1) height =1;
            }
        }
    }

    /*public void draw(Graphics g)
    {
        super.draw(g);
    }*/

    public void collideWith(Obj obj)
    {
        if(((obj instanceof Enemy2Obj) || (obj instanceof Enemy3Obj)) && (owner instanceof PlayerObj))
        {
            if(laserType == 10)
                return;
            obj.doHit(this);
            obj.xs += xs / 3D;
            if(obj.getHealth() < 0)
            {
                obj.doExplosion();
                ParticleDust.createHealthDust(10, obj,this.owner );
                obj.reset();
                PlayerObj p = (PlayerObj)owner;
                p.incScore(obj.worth);
            }
            doExplosion();
            reset();
        }
    }

    public void doExplosion()
    {
        if(Math.random() > 0.80000000000000004D)
            return;
        double sp = 0.0D;
        switch((int)(Math.random() * 3D))
        {
        case 0: // '\0'
            sp = 0.5D;
            break;

        case 1: // '\001'
            sp = 1.0D;
            break;

        case 2: // '\002'
            sp = 1.5D;
            break;

        default:
            sp = 0.5D;
            break;
        }
        for(int i = 0; i < 5; i++)
        {
            Laser ex = new Laser(vector, null, (int)x, (int)(y + Math.random() * 3D), objWorld);
            ex.setWidth((int)(xs * Math.random() * sp));
            if(Math.random() > 0.5D)
                ex.setHeight(1 + (int)(8D * Math.random()));
            else
                ex.setHeight(-(1 + (int)(8D * Math.random())));
            ex.setDxs(ex.width/2);
            ex.setDys(ex.height/2);
            ex.setHealth(90);            
            ex.setColorR(0.3F);
            ex.setColorG(1.0F);
            ex.setColorB(1.0F);
            ex.setColor(Color.GREEN);
            ex.setCRate(-0.01F);
            ex.setType(20);
            ex.laserType = 10;
            ex.drawFx = 2;
            ex.setOwner(owner);
        }

    }

    public static Laser createLaser(Obj obj, Vector vec, double lx, double ly)
    {
        Laser l1 = new Laser(vec, null, (int)lx, (int)ly, obj.objWorld);
        l1.setOwner(obj);
        l1.setXs((double)l1.width * 0.10000000000000001D);
        obj.sfx_shoot1.play();
        createLaserShockwave(l1);
        return l1;
    }

    public static void createLaserShockwave(Laser l1)
    {
        Particle ex = null;
        for(int i = 0; i < 2; i++)
        {
            ex = new Particle(l1.vector, null, (int)l1.x, (int)l1.y, l1.objWorld);
            ex.setWidth(2);
            ex.setHeight(3);
            ex.setDxs(-1D);
            if(i == 0)
                ex.setDys(-3D);
            else
                ex.setDys(3D);
            ex.setHealth(20);
            ex.setColorR(0.96F);
            ex.setColorG(0.8F);
            ex.setColorB(1.0F);
            ex.setCRate(-0.07F);
        }

    }

    public int getShift()
    {
        return shift;
    }

    public void setShift(int k)
    {
        shift = k;
    }

    final int TYPE_RICOCHET = 10;
    final int TYPE_NORMAL = 1;
    static final int SHIFT_UP = 1;
    static final int SHIFT_DOWN = 2;
    protected int shift;
    protected int laserType;
}