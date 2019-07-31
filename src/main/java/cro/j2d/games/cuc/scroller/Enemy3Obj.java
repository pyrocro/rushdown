// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Enemy3Obj.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Graphics;
import java.util.Vector;
import cro.j2d.games.cuc.scroller.grid.*;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Obj, World, Sprite, PlayerObj, 
//            Pickup1Obj, Enemy2Obj, Particle, Sfx, 
//            Laser

public class Enemy3Obj extends Enemy
{

    public Enemy3Obj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        reset();
    }

    public void reset()
    {        
        x = (int)(((double)objWorld.getPlayableXSize()) + (objWorld.getPlayableXSize() * Math.random()));
        y = objWorld.getPlayableY() + (int)((double)(objWorld.getPlayableYSize() - height) * Math.random());
        xs = 0.0D;
        ys = 0.0D;
        dxs = -(width / 60) - (5*Math.random() );
        dys = 0.0D;
        health = 200;
        worth = 4000;
        xa = 1D;
        ya = 0.25D;
        density = 10D;
        mass = (double)(sprite.getHeight() * sprite.getWidth()) * density;
        removeFromScreen();
    }    

    private void isVisible()
    {
        if(x < (double)objWorld.getPlayableXSize() && x > 0.0D)
            visible = true;
        if(x + (double)width < 0.0D)
            reset();
    }

    public void doLogic()
    {
        move();
    }
    public boolean collisionCheck(Obj other)
    {
    	if ( other instanceof Laser || other instanceof PlayerObj || other instanceof Enemy)    	
    	return super.collisionCheck(other);
    	return false;
    }
    public void collideWith(Obj obj)
    {
        if(obj instanceof PlayerObj)
        {
            obj.setXs((obj.getXs() + 1.0D) * -1D);
            obj.setX(x - (double)obj.width);
            obj.setHealth(obj.getHealth() - 50);
            return;
        }
        if(obj instanceof Pickup1Obj) return;
        if( obj instanceof Enemy )
        {
            //obj.setXs(obj.getXs() * 1.2D);
            //setXs(xs * 0.80000000000000004D);
            //while(collisionCheck(obj, this)) {}
            this.x = this.prevX; this.y = this.prevY;
            //obj.x = this.x - obj.width;
            return;
        } 
    }

    public void doExplosion()
    {
        double tmp_x = 0;
        double tmp_y = 0;
        double tmp_angle = 0;
        
       for(int i = 0; i < 500; i++)
        {
            tmp_angle = (Math.random()*360);
            tmp_x = (x+(width/2)) + (((width/10)+(2*Math.random())) * Math.cos(tmp_angle));
            tmp_y = (y+(height/2)) + (((height/10)+(2*Math.random())) * Math.sin(tmp_angle));
            Particle ex = new Particle(vector, null, (int)tmp_x, tmp_y, objWorld);
            ex.setDxs(/*this.dxs+*/((6)* Math.cos(tmp_angle)));
            ex.setDys(/*this.dys+*/((6)*Math.sin(tmp_angle)));
            ex.setXs(ex.getDxs()*5);
            ex.setYs(ex.getDys()*5);
            ex.setXa(3.0);
            ex.setYa(3.0);
            ex.setHealth(120);
            ex.setColorR(0.5F);
            ex.setColorG(1.0F);
            ex.setColorB(0.5F);
            ex.setWidth(3 + (int)(5D * Math.random()));
            ex.setHeight(3 + (int)(5D * Math.random()));
            ex.setCRate(-0.01f-(float)-(Math.random()/-100));
        }


        for(int i = 0; i < 20; i++)
        {
            Particle ex = new Particle(vector, null, (int)(x + (double)(width / 2) * Math.random()), (int)(y + (double)(height / 4)) + (int)((double)(height / 2) * Math.random()), objWorld);
            ex.setDxs(dxs * Math.random() * -1.5D);
            ex.setDys(dys * Math.random() * 1.5D);
            ex.setHealth(120);
            ex.setColorR(1.0F);
            ex.setColorG(0.5F);
            ex.setColorB(0.5F);
            ex.setWidth(3 + (int)(5D * Math.random()));
            ex.setHeight(3 + (int)(5D * Math.random()));
            ex.setCRate(-0.002f-(float)-(Math.random()/-100));
        }

        for(int i = 0; i < 20; i++)
        {
            Particle ex = new Particle(vector, null, (int)(x + (double)(width / 2) * Math.random()), (int)(y + (double)(height / 3)) + (int)((double)(height / 3) * Math.random()), objWorld);
            ex.setDxs(dxs * (4D * Math.random()) * -1D);
            if(Math.random() > 0.5D)
                ex.setDys(2D * Math.random());
            else
                ex.setDys(2D * Math.random() * -1D);
            ex.setHealth(60);
            ex.setColorR(0.5F);
            ex.setColorG(0.9F);
            ex.setColorB(0.5F);
            ex.setWidth(5 + (int)(4D * Math.random()));
            ex.setHeight(5 + (int)(4D * Math.random()));
            ex.setCRate(-0.008f-(float)-(Math.random()/-100));
        }
        sfx_die.play();
        Pickup1Obj.createHPPickup(1, this);
        Particle.createPTText(this);
    }

    public void doHit(Obj other)
    {
        if(other instanceof Laser)
            setHealth(getHealth() - 100);
        for(int i = 0; i < 1; i++)
        {
            Particle ex = new Particle(vector, null, (int)other.x, (int)(other.y + Math.random() * 3D), objWorld);
            ex.setDxs(dxs * Math.random());
            if(Math.random() > 0.5D)
                ex.setDys((1.0D + 2D * Math.random()) * -1D);
            else
                ex.setDys(1.0D + 2D * Math.random());
            ex.setHealth(20);
            ex.setColorR(1.0F);
            ex.setColorG(0.4F);
            ex.setColorB(0.4F);
            ex.setCRate(-0.06F);
            ex.setWidth(3 + (int)(2D * Math.random()));
            ex.setHeight(3 + (int)(2D * Math.random()));
        }

        sfx_hit.play();
    }
}