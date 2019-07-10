// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PlayerObj.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Graphics;
import java.util.Vector;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Obj, Particle, InputClass, Laser, 
//            World, Sprite

public class PlayerObj extends Obj
{

    protected int getScore()
    {
        return score;
    }

    protected void setScore(int score)
    {
        this.score = score;
    }

    public void setVecShots(Vector vec)
    {
        vecShots = vec;
    }

    public Vector getVecShots()
    {
        return vecShots;
    }
    
    public void removeFromScreen(){
        super.removeFromScreen();
    }

    public PlayerObj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        shot1Wait = 0L;
        shot2Wait = 0L;
        shot3Wait = 0L;
        thrustWait = 0L;
        vecShots = null;
        x = x_tmp;
        y = y_tmp;
        dxs = 25D;
        dys = 0.0D;
        health = HEALTH_MAX;
        xa = 1.5D;
        ya = 1.5D;
        tangible = true;
    }
    

    public void doLogic()
    {
        Particle ex = null;
        if(dxs > 0.0D)
        {
            dxs -= RETARDATION;
            if(dxs < 0.0D)
                dxs = 0.0D;
        } else
        if(dxs < 0.0D)
        {
            dxs += RETARDATION;
            if(dxs > 0.0D)
                dxs = 0.0D;
        }
        if(dys > 0.0D)
        {
            dys -= RETARDATION;
            if(dys < 0.0D)
                dys = 0.0D;
        } else
        if(dys < 0.0D)
        {
            dys += RETARDATION;
            if(dys > 0.0D)
                dys = 0.0D;
        }
        for(int i = 0; i < 10; i++)
        {
            ex = new Particle(vector, null, (int)x, (int)(((y + (double)(height / 2)) - 6D) + Math.random() * 8D), objWorld);
            ex.setColorR(0.6F);
            ex.setColorG(0.6F);
            ex.setColorB(1.0F);
            ex.setHealth(5);
            ex.setDxs(-9D);
            ex.setHeight(3);
            ex.setWidth(10);
            ex.setCRate(-0.06F);
        }

        if(Obj.timer >= thrustWait)
        {
            thrustWait = Obj.timer + 2L;
            ex = new Particle(vector, null, (int)x, (int)(((y + (double)(height / 2)) - 5D) + Math.random() * 8D), objWorld);
            ex.setColorR(0.9F);
            ex.setColorG(0.7F);
            ex.setColorB(0.1F);
            ex.setHealth(5);
            ex.setDxs(-0.5D);
            ex.setHeight(2);
            ex.setWidth(5);
            ex.setCRate(-0.06F);
            ex = new Particle(vector, null, (int)x, (int)(((y + (double)(height / 2)) - 4D) + Math.random() * 8D), objWorld);
            ex.setWidth(0);
            if(Math.random() > 0.5D)
                ex.setHeight(1 + (int)(2D * Math.random()));
            else
                ex.setHeight(-(1 + (int)(2D * Math.random())));
            ex.setDxs(ex.width);
            ex.setDys(ex.height);
            ex.setHealth(20);
            ex.setColorR(0.5F);
            ex.setColorG(1.0F);
            ex.setColorB(0.5F);
            ex.setCRate(-0.07F);
            ex.setType(20);
        }
        if(InputClass.getPlayer1Left() && dxs >= -MAX_SPEED)
            dxs -= (ACCELERATION);
        if(InputClass.getPlayer1Right() && dxs <= MAX_SPEED)
        {
            dxs += ACCELERATION;
            for(int i = 0; i < 30; i++)
            {
                ex = new Particle(vector, null, (int)x, (int)(((y + (double)(height / 2)) - 4D) + Math.random() * 8D), objWorld);
                ex.setWidth((int)(xs * Math.random() * -2.5D));
                if(Math.random() > 0.5D)
                    ex.setHeight(1 + (int)(2D * Math.random()));
                else
                    ex.setHeight(-(1 + (int)(2D * Math.random())));
                ex.setDxs(ex.width);
                ex.setDys(ex.height);
                ex.setHealth(20);
                ex.setColorR(0.5F);
                ex.setColorG(1.0F);
                ex.setColorB(0.5F);
                ex.setCRate(-0.04F);
                ex.setType(20);
            }

        }
        if(InputClass.getPlayer1Up() && dys >= -MAX_SPEED)
        {
            dys -= ACCELERATION;
            for(int i = 0; i < 10; i++)
            {
                ex = new Particle(vector, null, (int)(((x + (double)(width / 2)) - 4D) + Math.random() * 8D), (int)y + height, objWorld);
                ex.setHeight((int)(ys * Math.random() * -1.5D));
                if(Math.random() > 0.5D)
                    ex.setWidth(2 + (int)(3D * Math.random()));
                else
                    ex.setWidth(-(2 + (int)(3D * Math.random())));
                ex.setDxs(ex.width);
                ex.setDys(ex.height);
                ex.setHealth(20);
                ex.setColorR(0.5F);
                ex.setColorG(1.0F);
                ex.setColorB(0.5F);
                ex.setCRate(-0.1F);
                ex.setType(20);
            }

        }
        if(InputClass.getPlayer1Down() && dys <= MAX_SPEED)
        {
            dys += ACCELERATION;
            for(int i = 0; i < 10; i++)
            {
                ex = new Particle(vector, null, (int)(((x + (double)(width / 2)) - 4D) + Math.random() * 8D), (int)y, objWorld);
                ex.setHeight((int)(ys * Math.random() * -1.5D));
                if(Math.random() > 0.5D)
                    ex.setWidth(2 + (int)(3D * Math.random()));
                else
                    ex.setWidth(-(2 + (int)(3D * Math.random())));
                ex.setDxs(ex.width);
                ex.setDys(ex.height);
                ex.setHealth(20);
                ex.setColorR(0.5F);
                ex.setColorG(1.0F);
                ex.setColorB(0.5F);
                ex.setCRate(-0.1F);
                ex.setType(20);
            }

        }
        if(InputClass.getPlayer1Fire1() && Obj.timer >= shot1Wait)
        {
            shot1Wait = Obj.timer + 15L;
            Laser.createLaser(this, vecShots, x + (double)(width / 3), y - 5D).setShift(2);
            Laser.createLaser(this, vecShots, x + (double)(width / 3), y + (double)height).setShift(1);
            
            //Laser.createLaser(this, vecShots, x + (double)(width / 3), y - 5D).setShift(2);
        }
        if(InputClass.getPlayer1Fire2() && Obj.timer >= shot2Wait)
            shot1Wait = Obj.timer + 20L;
        if(InputClass.getPlayer1Fire3() && Obj.timer >= shot3Wait)
            shot1Wait = Obj.timer + 5000L;
        move();
    }

    public void move()
    {
        super.move();
        if(x < (double)objWorld.getPlayableX())
        {
            x = objWorld.getPlayableX();
            //xs = 0.0D;
        }
        if(x + (double)width > (double)objWorld.getPlayableXMax())
        {
            x = objWorld.getPlayableXMax() - width;
            //xs = 0.0D;
        }
        if(y < (double)objWorld.getPlayableY())
        {
            y = objWorld.getPlayableY();
            //ys = 0.0D;
        }
        if(y + (double)height > (double)objWorld.getPlayableYMax())
        {
            y = objWorld.getPlayableYMax() - height;
            //ys = 0.0D;
        }
    }

    public void incHealth(int amt)
    {
        getClass();
        if(health + amt == HEALTH_MAX)
        {
            incScore(amt);
            return;
        }
        getClass();
        if(health + amt < HEALTH_MAX)
        {
            health += amt;
        } else
        {
            getClass();
            health = HEALTH_MAX;
        }
    }

    public void decHealth(int amt)
    {
        if(health - amt > 0)
            health -= amt;
        else
            health = 1;
    }

    public void incScore(int amt)
    {
        score += amt;
    }

    public void reset()
    {
        //removeFromScreen();
    }

    public void move(long l1)
    {
    }

    public void collideWith(Obj obj)
    {
    }

    public static double ACCELERATION = .6D;
    public static double RETARDATION = 0.35D;
    public final int HEALTH_MAX = 400;
    private long shot1Wait;
    private long shot2Wait;
    private long shot3Wait;
    private long thrustWait;
    final double MAX_SPEED = 5;
    private Vector vecShots;
    private int score;
    private int pickups;

}