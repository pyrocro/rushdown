// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Obj.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;
import cro.mouse.*;
import cro.j2d.games.cuc.scroller.grid.GridMan;
import cro.j2d.games.cuc.scroller.grid.Sector;
import java.awt.Image;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            World, Sprite, SfxMan, Sfx

public abstract class Obj
{

    public int getVecIndex()
    {
        return vecIndex;
    }

    private void isVisible()
    {
    	
        if(x < (double)objWorld.getPlayableXMax() && x + (double)width > 0.0D && y + (double)height > 0.0D && y < (double)objWorld.getPlayableYMax()){
            visible = true;
        }
            
        
        else{
        	if(this instanceof ParticleDust == false) reset();
        }
            
    }
    
    public Obj(Vector vec, Sprite s, double x_tmp, double y_tmp, World w)
    {
        mass = 0.0D;
        density = 0.0D;
        target = null;
        objWorld = w;
        owner = null;
        worth = 0;
        vector = null;
        self = new Rectangle();
        other = new Rectangle();
        health = 0;
        visible = false;
        vector = vec;
        sprite = s;
        x = x_tmp;
        y = y_tmp;
        objWorld = w;
        sectorList = new Vector();
        if(s != null)
        {
            width = s.getWidth();
            height = s.getHeight();
        }
        if (vector != null){
        	vector.add(this);
            vecIndex = vector.size()-1;
        }
        sectorVecIndex = -1; // -1 to throw an exception if I screw up something later on with this optimization
        
        xa = objWorld.getPlayableXSize();
        ya = objWorld.getPlayableYSize();
        dxa = xa;
        dya = ya;
        dxs = 0.0D;
        dys = 0.0D;
        sfx_die = w.getSfxMan().getNullSound();
        sfx_hit = w.getSfxMan().getNullSound();
        sfx_shoot1 = w.getSfxMan().getNullSound();
        sfx_shoot2 = w.getSfxMan().getNullSound();
        sfx_shoot3 = w.getSfxMan().getNullSound();
        tangible = false;
    }

    public void move(long delta)
    {
        x += (xs * (double)delta) / 1000D;
        y += (ys * (double)delta) / 1000D;
        isVisible();
    }

    public void move()
    {
        objWorld.getGrid().removeFromSectors(this);        
        if(xs < dxs)
        {
        	xs += xa;
            if(xs > dxs)
                xs = dxs;
        }
        if(xs > dxs)
        {
            xs -= xa;
            if(xs < dxs)
                xs = dxs;
        }
        if(ys < dys)
        {
            ys += ya;
            if(ys > dys)
                ys = dys;
        }
        if(ys > dys)
        {
            ys -= ya;
            if(ys < dys)
                ys = dys;
        }
        prevX = x; prevY = y; // stores the object previous co-ordinates.
        x += xs;
        y += ys;
       if ( this.tangible == true) {
            this.objWorld.getGrid().organiseObj(this);
            this.checkCollision();
       }
        isVisible();
    }
    final public void checkCollision()
    {
        Obj self = this;
        int trip = -1; // this is part of an optimization to reduse the number of calls to Vector.indexOf function
        int count =0;
    	Obj obj = null;
        Sector sec = null;
        Vector list = new Vector();
    	for (int i=0; i<self.getSectorList().size(); i++){ // going thru sector by sector
    		sec = (Sector)self.getSectorList().get(i);
                //System.out.println("Sector list size " + self.getSectorList().size());
                //sec.getObjList().remove(self); // removes the object from the object list reduces O in O^n
    		for (int j=0; j< sec.getObjList().size();j++){ // each going thru each sector object list                    
                        obj = (Obj) sec.getObjList().get(j);
                        //System.out.println("Object list size " + sec.getObjList().size());
                        //if (sec.getObjList().get(0) instanceof PlayerObj) System.out.println("The object is the player");
                        //if (sec.getObjList().size()>1 ) System.exit(0);
    			if ( !list.contains(obj) ) list.add(obj);
                        if (obj == self ) {trip = j;/*sec.getObjList().remove(j); if(j>0) j--;*/}// Optimization code(avoides having to call Vector.indexOf(element)
    		}
                //if (trip != -1) sec.getObjList().remove(trip);
                
    	}
        
    	for (int i=0; i<list.size();i++){
    		obj = (Obj)list.get(i);
    		if ( self.collisionCheck(obj)) {
    			self.collideWith(obj);
    			//obj.collideWith(self);
    		}
    	}   	

    }
     public static boolean collisionCheck(Obj s, Obj o)
    {
        if(s == o)
            return false;
        return s.x + (double)s.width >= o.x && s.x <= o.x + (double)o.width && s.y + (double)s.height >= o.y && s.y <= o.y + (double)o.height;
    }

    public boolean collisionCheck(Obj obj)
    {
        if(this == obj)
        {
            return false;
        }
                	
        //self.setBounds((int)x, (int)y, (int)width, (int)height);
        //other.setBounds((int)obj.getX(), (int)obj.getY(), (int)obj.width, (int)obj.height);
        //return self.intersects(other);        
        if (this.x + this.width >= obj.x && this.x <= obj.x + obj.width && this.y + this.height >= obj.y && this.y <= obj.y + obj.height){
            if (obj.sprite != null && this.sprite != null) return pixelPerfectCollision( obj ); //return true;
            else {return true;} // returns true if any of the objects are vector base avoiding a pixel check;            
        }
        return false;
    }
    public boolean pixelPerfectCollision(Obj obj){
        double x1Start = 0, x2Start = 0;
        double y1Start = 0, y2Start = 0;
        int x1Pos = 0, x2Pos = 0, y1Pos = 0, y2Pos = 0;
        
        // where to start in each bitmask
        if ( this.x < obj.x ) {x1Start = (int)obj.x - (int)this.x;x2Start = 0;} else {x2Start = (int)this.x - obj.x;x1Start =0;}
        if ( this.y < obj.y ) {y1Start = (int)obj.y - (int)this.y;y2Start = 0;} else {y2Start = (int)this.y - obj.y;y1Start = 0;}
        //assign the starting positions
        x1Pos = (int)x1Start;
        x2Pos = (int)x2Start;
        y1Pos = (int)y1Start;
        y2Pos = (int)y2Start;
        
        // search for collision with overlaping pixels
        while (y1Pos < this.height && y2Pos < obj.height ){
            while(x1Pos < this.width && x2Pos < obj.width){                
                if ( this.sprite.getBitmask()[x1Pos][y1Pos][0] > 0 && obj.sprite.getBitmask()[x2Pos][y2Pos][0] > 0) return true;
                x1Pos++;x2Pos++;
            }
            y1Pos++;y2Pos++;
            x1Pos = (int)x1Start; x2Pos = (int)x2Start;
        }
        return false;
        
    }

    public abstract void collideWith(Obj obj);


    public String getStr()
    {
        return str;
    }

    public void setStr(String s)
    {
        str = s;
    }

    public void setXs(double xspeed)
    {
        xs = xspeed;
    }

    public void setYs(double yspeed)
    {
        ys = yspeed;
    }

    public double getXs()
    {
        return xs;
    }

    public void setX(double xPos)
    {
        x = xPos;
    }

    public void setY(double yPos)
    {
        y = yPos;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public double getScreenX()
    {
        return (double)objWorld.getPlayableXSize();
    }

    public double getscreenY()
    {
        return (double)objWorld.getPlayableYSize();
    }
    public void draw(Graphics g)
    {    	 
    	if(visible = false) return;
        //if(sprite != null) g.drawImage((Image)sprite.getImage(), (int)x, (int)y, null);//sprite.draw(g, (int)x, (int)y);
        /*if(sprite != null) 
        {
            g.drawImage((Image)sprite.getImage(), (int)x, (int)y, null);            
        }//sprite.draw(g, (int)x, (int)y);
        */
        //this.angle += 1;
        if(angle > 360 ) angle =0;
        if (this.sprite != null) {
            this.sprite.draw(g, (int)this.x, (int)this.y,angle);            
        }
        
        /*if (this.sectorList.size()>0){
        	for ( int i= 0; i<this.sectorList.size();i++){
        	Sector sec = (Sector)this.sectorList.get(i);
        	g.drawString("secX"+sec.getX()+"-secY"+sec.getY(), (int)x, (int)y);
        	g.drawRect((int)sec.getX(),(int)sec.getY(),(int)sec.getWidth(),(int)sec.getHeight());
        	}
        }*/
        
    }
     

    public void reset()
    {    	
        x = (int)((double)objWorld.getPlayableXSize() + (double)objWorld.getPlayableXSize() * Math.random());
        y = (int)((double)objWorld.getPlayableYSize() * Math.random());
        width = (int)(7D * Math.random());
        height = (int)(3D * Math.random());
        xs = -width;
        ys = 0.0D;
        visible = false;
        angle = 0;
        removeFromScreen();
    }
    public void removeFromScreen(){
        if ( tangible != true) return;
        GridMan.removeFromSectors(this);
    }
        
    

    public abstract void doLogic();

    public void doExplosion()
    {
    }

    public void doHit(Obj obj1)
    {
    }

   

    protected int getHealth()
    {
        return health;
    }

    protected void setHealth(int health)
    {
        this.health = health;
    }

    public Sfx getSfx_die()
    {
        return sfx_die;
    }

    public void setSfx_die(Sfx sfx_die)
    {
        this.sfx_die = sfx_die;
    }

    public Sfx getSfx_hit()
    {
        return sfx_hit;
    }

    public void setSfx_hit(Sfx sfx_hit)
    {
        this.sfx_hit = sfx_hit;
    }

    public Sfx getSfx_shoot1()
    {
        return sfx_shoot1;
    }

    public void setSfx_shoot1(Sfx sfx_shoot1)
    {
        this.sfx_shoot1 = sfx_shoot1;
    }

    public Sfx getSfx_shoot2()
    {
        return sfx_shoot2;
    }

    public void setSfx_shoot2(Sfx sfx_shoot2)
    {
        this.sfx_shoot2 = sfx_shoot2;
    }

    public Sfx getSfx_shoot3()
    {
        return sfx_shoot3;
    }

    public void setSfx_shoot3(Sfx sfx_shoot3)
    {
        this.sfx_shoot3 = sfx_shoot3;
    }

    protected void setHeight(double height)
    {
        this.height = height;
    }

    protected void setWidth(double width)
    {
        this.width = width;
    }

    public Obj getOwner()
    {
        return owner;
    }

    public void setOwner(Obj owner)
    {
        this.owner = owner;
    }

    public int getWorth()
    {
        return worth;
    }

    public void setWorth(int worth)
    {
        this.worth = worth;
    }

    public double getDxs()
    {
        return dxs;
    }

    public void setDxs(double xs)
    {
        dxs = xs;
    }

    public double getDys()
    {
        return dys;
    }

    public void setDys(double ys)
    {
        dys = ys;
    }

    public double getXa()
    {
        return xa;
    }

    public void setXa(double xa)
    {
        this.xa = xa;
    }

    public double getYa()
    {
        return ya;
    }

    public void setYa(double ya)
    {
        this.ya = ya;
    }    

    protected Obj target;

    public World getObjWorld() {
        return objWorld;
    }
    static long timer = 0L;
    protected World objWorld; //volatile
    protected Obj owner;
    protected int worth;
    protected volatile double mass;
    protected volatile double density;
    protected volatile double x; //x co-ordinates    
    protected volatile double y; //y co-ordinates
    protected volatile double prevX; // previous x co-ordinates
    protected volatile double prevY; // previous y co-ordinates
    protected volatile double xs; //x speed
    protected volatile double ys; //y speed
    protected volatile double dxs; //x destination speed(acceleration is used to achive this speed)
    protected volatile double dys; //y destination speed(acceleration is used to achive this speed)
    protected volatile double xa; //x acceleration
    protected volatile double ya; //y acceleration
    protected volatile double dxa; //x destination acceleration
    protected volatile double dya; //x destination acceleration
    protected volatile double width;
    protected volatile double height;
    protected volatile Sprite sprite;
    protected volatile Sfx sfx_shoot1;
    protected volatile ClickableArea click;
    protected Sfx sfx_shoot2;
    protected Sfx sfx_shoot3;
    protected Sfx sfx_hit;
    protected Sfx sfx_die;
    protected Vector vector;
    protected Rectangle self;
    protected Rectangle other;
    protected int health;
    private String str;
    protected boolean visible;
    protected int vecIndex;
    protected Vector sectorList;
    protected boolean tangible;
    protected int sectorVecIndex;
    protected double angle = 0;
    /**
     * @return Returns the sectorList.
     */
    public int getSectorVecIndex(){
        return sectorVecIndex;
    }   
    /**
    * @param i the sectorVecIndex to set
    */
    public void setSectorVecIndex(int i){
        sectorVecIndex = i;
    }
	/**
	 * @return Returns the sectorList.
	 */
	public Vector getSectorList() {
		return sectorList;
	}

	/**
	 * @return the tangible
	 */
	public boolean isTangible() {
		return tangible;
	}

	/**
	 * @param tangible the tangible to set
	 */
	public void setTangible(boolean tangible) {
		this.tangible = tangible;
	}

	/**
	 * @return the dxa
	 */
	public double getDxa() {
		return dxa;
	}

	/**
	 * @param dxa the dxa to set
	 */
	public void setDxa(double dxa) {
		this.dxa = dxa;
	}

	/**
	 * @return the dya
	 */
	public double getDya() {
		return dya;
	}

	/**
	 * @param dya the dya to set
	 */
	public void setDya(double dya) {
		this.dya = dya;
	}
}