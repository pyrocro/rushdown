// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ParticleDust.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.util.Vector;
import java.awt.Graphics;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Particle, Obj, PlayerObj, World, 
//            SfxMan, Sprite

public class ParticleDust extends Particle
{

    public ParticleDust(Vector vec, Sprite s, double x_tmp, double y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        dustType = 10;
        falloutTimmer = 0;
        tangible = true;
    }

    public void move()
    {
    	
        if(target != null)
        {
        	//xa = objWorld.getPlayableXMax()/(300+(x-target.x)*3);
        	//ya = objWorld.getPlayableYMax()/(300+(y-target.y)*3);
            if(target.x + (double)(target.width / 2) < x && dxs > 0.0D)
                dxs *= -1D;
            if(target.x + (double)(target.width / 2) > x && dxs < 0.0D)
                dxs *= -1D;
            if(target.y + (double)(target.height / 2) < y && dys > 0.0D)
                dys *= -1D;
            if(target.y + (double)(target.height / 2) > y && dys < 0.0D)
                dys *= -1D;
        }
        super.move();        
        if(target != null && Obj.collisionCheck(this, target))
            targetReached();
        if (this.dustType == ParticleDust.FAllOUT){
        	if (this.width>=2){this.width=this.width -.07;}
        	if (this.height>=2){this.height = this.height - .07;}
        }
    	if(this.dustType == ParticleDust.TYPE_HEALTH_UP)  		
            if (falloutTimmer++ >=2) {
            	falloutTimmer =0;
            	ParticleDust ex = new ParticleDust(this.vector, null, this.x , this.y , this.objWorld);
                ex.setDxs(this.dxs*.1);
                ex.setDys(this.dys*.1);
                ex.setHealth(10);
                ex.setColorR(this.colorR-0.15f);
                ex.setColorG(this.colorG-0.15f);
                ex.setColorB(1f);
                ex.setWidth(this.width);
                ex.setHeight(this.height);
                ex.setCRate(-0.015F);    	                
                ex.setXa(this.xa*0.1);
                ex.setYa(this.ya*0.1);
                ex.setXs(this.xs);
                ex.setYs(this.ys);
                ex.target = this;
                ex.worth = 4;
                ex.dustType = ParticleDust.FAllOUT;
                ex.owner = this;
                ex.tangible = false;
                /*
                 * make sure that the fallout particle is rendered before the particle
                 */                
                if ( vector.indexOf(this)>0){
                vector.insertElementAt(ex,0);
                }
            }

        
        
            }
    public void doLogic(){
    	super.doLogic();
    	if(x < (double)objWorld.getPlayableX() || x + (double)width > (double)objWorld.getPlayableXMax())
        {
            dxs *= -1D;
            xs *= -1D;            
        }
        if(y < (double)objWorld.getPlayableY() || y + (double)height > (double)objWorld.getPlayableYMax())
        {
            dys *= -1D;
            ys *= -1D;            
        }
    }
    
    
    /*public void draw(Graphics g){    	
    	    	super.draw(g);
    }*/
    private void isVisible(){
    	
    }

    private void targetReached()
    {
        switch(dustType)
        {
        default:
            break;

        case 1: // '\001'
            if(target instanceof PlayerObj)
            {
                PlayerObj pObj = (PlayerObj)target;
                pObj.incHealth(worth);
                objWorld.getSfxMan().playClip("ding");
                reset();
            }
            break;

        case TYPE_POINTS_UP: // '\n'
            if(target instanceof PlayerObj)
            {
                PlayerObj pObj = (PlayerObj)target;
                pObj.incScore(worth);
                reset();
            }
            break;

        case TYPE_DARK_PLAUGE: // '\024'
            if(target instanceof PlayerObj)
            {
                PlayerObj pObj = (PlayerObj)target;
                pObj.decHealth(worth);
                reset();
            }
            break;
        case ParticleDust.FAllOUT:
        	if (target == owner ){
        		//reset();
        	}
        }
    }

    public static void createHealthDust(int amt, Obj obj, Obj tget)
    {
        for(int i = 0; i < 1; i++)
        {
            ParticleDust ex = new ParticleDust(obj.vector, null, (int)((obj.x - (double)(obj.width * 2)) + Math.random() * (double)(obj.width * 4)), (int)((obj.y - (double)(obj.height * 2)) + Math.random() * (double)(obj.height * 4)), obj.objWorld);
            if(Math.random() > 0.5D)
                ex.setDxs(4D + 1D * Math.random());
            else
                ex.setDxs(4D + 1D * Math.random() * -1D);
            if(Math.random() > 0.5D)
                ex.setDys(4D + 1D * Math.random());
            else
                ex.setDys(4D + 1D * Math.random() * -1D);
            ex.setHealth(800);
            ex.setColorR(0.9f);
            ex.setColorG(1.0F);
            ex.setColorB(0.9f);
            ex.setWidth(8);
            ex.setHeight(8);
            ex.setCRate(-0.00001F);
            ex.setXa(.2D);
            ex.setYa(.2D);            
            ex.setXs(0);
            ex.setYs(0);
            ex.target = tget;
            ex.worth = 50;
            ex.setTangible(false);
            ex.dustType = TYPE_HEALTH_UP;
        }

        obj.objWorld.getSfxMan().playClip("suck_in");
    }

    public static void createPointsDust(int amt, Obj obj, Obj tget)
    {
        for(int i = 0; i < amt; i++)
        {
            ParticleDust ex = new ParticleDust(obj.vector, null, (int)((obj.x - (double)(obj.width * 2)) + Math.random() * (double)(obj.width * 4)), (int)((obj.y - (double)(obj.height * 2)) + Math.random() * (double)(obj.height * 4)), obj.objWorld);
            if(Math.random() > 0.5D)
                ex.setDxs(6D + 3D * Math.random());
            else
                ex.setDxs(6D + 3D * Math.random() * -1D);
            if(Math.random() > 0.5D)
                ex.setDys(6D + 3D * Math.random());
            else
                ex.setDys(6D + 3D * Math.random() * -1D);
            ex.setHealth(80);
            ex.setColorR(1.0F);
            ex.setColorG(0.5F);
            ex.setColorB(0.6F);
            ex.setWidth(3);
            ex.setHeight(3);
            ex.setCRate(-0.01F);
            ex.setXa(0.59999999999999998D);
            ex.setYa(0.59999999999999998D);
            ex.target = tget;
            ex.worth = 400;
            ex.dustType = TYPE_POINTS_UP;
        }

    }

    public static void createDarkDust(int amt, Obj obj, Obj tget)
    {
        for(int i = 0; i < amt; i++)
        {
            ParticleDust ex = new ParticleDust(obj.vector, null, (int)((obj.x - (double)(obj.width * 2)) + Math.random() * (double)(obj.width * 4)), (int)((obj.y - (double)(obj.height * 2)) + Math.random() * (double)(obj.height * 4)), obj.objWorld);
            if(Math.random() > 0.5D)
                ex.setDxs(2D + 3D * Math.random());
            else
                ex.setDxs(120D + 3D * Math.random() * -1D);
            if(Math.random() > 0.5D)
                ex.setDys(120D + 3D * Math.random());
            else
                ex.setDys(6D + 3D * Math.random() * -1D);
            ex.setHealth(80);
            ex.setColorR(0.4F);
            ex.setColorG(0.2F);
            ex.setColorB(0.2F);
            ex.setWidth(2);
            ex.setHeight(2);
            ex.setCRate(0.01F);
            ex.setXa(0.59999999999999998D);
            ex.setYa(0.59999999999999998D);
            ex.target = tget;
            ex.worth = 2;
            ex.dustType = TYPE_DARK_PLAUGE;
        }

    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
    private int falloutTimmer = 0;
    static final int TYPE_HEALTH_UP = 1;
    static final int TYPE_POINTS_UP = 10;    
    static final int TYPE_DARK_PLAUGE = 20;
    static final int FAllOUT = 30;
    protected int dustType;
    static final int exlosionSize = 4;
}

