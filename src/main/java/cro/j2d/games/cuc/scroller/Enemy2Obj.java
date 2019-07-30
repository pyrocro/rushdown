// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Enemy2Obj.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Graphics;
import java.util.Vector;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Obj, World, Sprite, Particle, 
//            PlayerObj, Enemy3Obj, Sfx, Pickup1Obj, 
//            Laser

public class Enemy2Obj extends Enemy
{

    public Enemy2Obj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
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
        dxs = -(width / 60) - (5*Math.random() );//-(width / 5);
        dys = 0.0D;
        health = 500;
        worth = 2000;
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
        Particle ex = null;
        move();
        for(int i = 0; i < 1; i++)
        {
            ex = new Particle(vector, null, ((int)x + width) - 8, (int)(((y + (double)(height / 2)) - 6D) + Math.random() * 6D), objWorld);
            ex.setColorR(0.4F);
            ex.setColorG(0.4F);
            ex.setColorB(0.6F);
            ex.setHealth(5);
            ex.setXs(0.0D);
            ex.setHeight(8);
            ex.setWidth(30);
            ex.setCRate(-0.04F);
        }

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
            obj.setXs(obj.getXs() - 5D);
            obj.setX(x - obj.width);
            obj.setHealth(obj.getHealth() - 25);
            return;
        }
        if(obj instanceof Enemy)
        {
            //obj.setDxs(obj.dxs * 1.2D);
            //setDxs(dxs * 0.80000000000000004D);
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
        
       for(int i = 0; i < 50; i++)
        {
            tmp_angle = (Math.random()*360);
            tmp_x = (x+(width/2)) + (((width/5)+(6*Math.random())) * Math.cos(tmp_angle));
            tmp_y = (y+(height/2)) + (((height/5)+(6*Math.random())) * Math.sin(tmp_angle));
            Particle ex = new Particle(vector, null, (int)tmp_x, tmp_y, objWorld);
            ex.setDxs(this.dxs+((6)* Math.cos(tmp_angle)));
            ex.setDys(this.dys+((6)*Math.sin(tmp_angle)));
            ex.setHealth(120);
            ex.setColorR(1.0F);
            ex.setColorG(1.0F);
            ex.setColorB(0.5F);
            ex.setWidth(3 + (int)(5D * Math.random()));
            ex.setHeight(3 + (int)(5D * Math.random()));
            ex.setCRate(-0.01f-(float)-(Math.random()/-100));
        }
        
        for(int i = 0; i < 20; i++)
        {
            Particle ex = new Particle(vector, null, (int)(x + (double)(width / 2) * Math.random()), (int)(y + (double)(height / 4)) + (int)((double)(height / 2) * Math.random()), objWorld);
            ex.setDxs(xs / (Math.random() ));
            ex.setDys(ys / (Math.random() ));
            ex.setHealth(120);
            ex.setColorR(0.75F);
            ex.setColorG(0.75F);
            ex.setColorB(1.0F);
            ex.setWidth(2 + (int)(6D * Math.random()));
            ex.setHeight(2 + (int)( 6D * Math.random()));
            ex.setCRate(-0.005f-(float)-(Math.random()/-100));
            ex.drawFx = Particle.FX_FADE;
            ex.setTangible(true);
        }

        for(int i = 0; i < 40; i++)
        {
            Particle ex = new Particle(vector, null, (int)(x + (double)(width / 2) * Math.random()), (int)(y + (double)(height / 4)) + (int)((double)height * Math.random()), objWorld);
            ex.setDxs(-xs*Math.random());
            if(Math.random() > 0.5D)
                ex.setDys(2D * Math.random());
            else
                ex.setDys(2D * Math.random() * -1D);
            ex.setHealth(90);
            ex.setColorR(0.5F);
            ex.setColorG(1.0F);
            ex.setColorB(1.0F);
            ex.setWidth(3 + (int)(4D * Math.random()));
            ex.setHeight(3 + (int)(4D * Math.random()));
            ex.setCRate(-0.005f-(float)-(Math.random()/-100));
            ex.setTangible(true);
        }

        for(int i = 0; i < 50; i++)
        {
            Particle ex = new Particle(vector, null, (int)(x + (double)(width / 2) * Math.random()), (int)(y + (double)(height / 3)) + (int)((double)(height / 3) * Math.random()), objWorld);
            ex.setDxs(dxs * (4D * Math.random()) * -1D);
            ex.setHealth(90);
            ex.setColorR(0.7F);
            ex.setColorG(1.0F);
            ex.setColorB(1.0F);
            ex.setWidth(5 + (int)(4D * Math.random()));
            ex.setHeight(5 + (int)(4D * Math.random()));
            ex.setCRate(-0.005f-(float)-(Math.random()/-100));
            ex.setTangible(true);
        }        
        sfx_die.play();
        Pickup1Obj.createHPPickup(1, this);
        Particle.createPTText(this);
    }

    public void doHit(Obj other)
    {
        if(other instanceof Laser){
            setHealth(getHealth() - other.worth);
            this.angle += 80;
        }
        for(int i = 0; i < 10; i++)
        {
            Particle ex = new Particle(vector, null, (int)other.x + other.width, (int)(((other.y + (double)(other.height / 2)) - 4D) + Math.random() * 8D), objWorld);
            ex.setDxs((other.xs / 2D) * Math.random());
            if(Math.random() > 0.5D)
                ex.setDys(3D * Math.random()*2D);
            else
                ex.setDys(3D * Math.random() * -2D);
            ex.setHealth(40);
            ex.setColorR(0.6F);
            ex.setColorG(0.9F);
            ex.setColorB(0.6F);
            ex.setWidth(2 + (int)(5D * Math.random()));
            ex.setHeight(2 + (int)(5D * Math.random()));
            ex.setCRate(-0.01F);
            
        }
        sfx_hit.play();
    }
}