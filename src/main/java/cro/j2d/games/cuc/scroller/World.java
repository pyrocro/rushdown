// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   World.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import cro.j2d.games.cuc.scroller.pixelmap.SpriteMan;
import java.awt.*;
import java.util.Vector;
import cro.j2d.games.cuc.scroller.grid.*;
import cro.jdbc.MysqlJdbc;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            SpriteMan, SfxMan, Sprite, SpaceJunkObj, 
//            OutputClass, InputClass, PlayerObj, Enemy2Obj, 
//            Enemy3Obj, Obj

public class World
{
    MysqlJdbc db;
    String dbHost;
    String dbPort;
    String dbName;
    String dbTable;
    String dbUser;
    String dbPass;    
    final String version = "Alpha 2";
    private int highScore;
    public String name;
    private OutputClass output;
    private String strWinName;
    private SpriteMan spriteMan;
    private SfxMan sfxMan;
    private Vector vecDebris;
    private Vector vecDebris2;
    private Vector vecPlayer;
    private Vector vecEnemies;
    private Vector vecEnemyShots;
    private Vector vecPlayerShots;
    final int screenX = 1280;
    final int screenY = 800;
    private int playableX;
    private int playableY;
    private int playableXSize;
    private int playableYSize;
    private int playableXMax;
    private int playableYMax;
    int LEVEL_PIXEL_LENGTH;
    int LEVEL_PIXEL_HEIGHT;
    private GridMan grid;
    StartHere game;    
    public World(StartHere prog)
    {
        game = prog;
        //****************DATABASE STUFF******************
        dbHost = "mysql1006.ixwebhosting.com";
        dbPort = "3306";
        dbName = "C321930_rushdown";
        dbUser = "C321930_rushdown";
        dbTable = "highscore";
        dbPass = "Rushdown123";
        //*****tmp hi score, in the event that a database connection is not availible ******
        highScore = 20000;
        name = "_Pyro_";
        db = new MysqlJdbc(dbHost, dbPort, dbName, dbUser, dbPass);
        getDBInfo();
        //*************************************************
        output = null;
        strWinName = "rushDown [0_o] Alpha(by Yohance Mcdonald)";
        spriteMan = null;
        sfxMan = null;
        vecDebris = null;
        vecDebris2 = null;
        vecPlayer = null;
        vecEnemies = null;
        vecEnemyShots = null;
        vecPlayerShots = null;
        playableX = 0;
        playableY = 40;
        playableXSize = screenX;
        playableYSize = screenY - playableY;
        playableXMax = playableXSize + playableX;
        playableYMax = playableYSize + playableY;
        LEVEL_PIXEL_LENGTH = playableXSize * 1;
        LEVEL_PIXEL_HEIGHT = playableYSize * 1;
        
    }
    /**
	 * @return Returns the objWorld.
	 */
	public StartHere getGame() {
		return game;
	}

	/**
	 * @param objWorld The objWorld to set.
	 */
	public void setGame(StartHere g) {
		game = g;
	}
        public boolean exitGame(){
            return game.exitGame();
        }

    

    public void loadData()
    {
        getSpriteMan().loadSprite("cro/j2d/pics/nebula_2.png", "nebula");
        getSpriteMan().loadSprite("cro/j2d/pics/enemy3_dmg_1.gif", "spjunk1");
        getSpriteMan().loadSprite("cro/j2d/pics/enemy3.gif", "spjunk2");
        getSpriteMan().loadSprite("cro/j2d/pics/ship_e.png", "ship_e");
        getSpriteMan().loadSprite("cro/j2d/pics/rushdown_splash.png", "rushdown_splash");
        getSpriteMan().loadSprite("cro/j2d/pics/rushdown_menu.png", "rushdown_menu");
        getSpriteMan().loadSprite("cro/j2d/pics/rushdown_shields.png", "shields");
        getSpriteMan().loadSprite("cro/j2d/pics/rushdown_score.png", "score");
        getSpriteMan().loadSprite("cro/j2d/pics/highscore.png", "highscore");
        getSpriteMan().loadSprite("cro/j2d/pics/pickup1.gif", "pickup1");
        getSpriteMan().loadSprite("cro/j2d/pics/pickup2.gif", "pickup2");
        getSpriteMan().loadSprite("cro/j2d/pics/pickup3.gif", "pickup3");
        getSpriteMan().loadSprite("cro/j2d/pics/pickup4.gif", "pickup4");
        getSpriteMan().loadSprite("cro/j2d/pics/enemy2.gif", "enemy2");
        getSpriteMan().loadSprite("cro/j2d/pics/jetpackman_small.png", "enemy4");
        getSpriteMan().loadSprite("cro/j2d/pics/enemy3_dmg_1.gif", "enemy3");
        getSfxMan().setNullSound(getSfxMan().loadSfx("cro/sounds/sfx/null.au", "null"));
        //getSfxMan().loadSfx("cro/sounds/loops/fruity_loop.au", "fruity_loop");
        //getSfxMan().loadSfx("cro/sounds/loops/tri.wav", "fruity_loop");
        getSfxMan().loadSfx("cro/sounds/loops/my techno.wav", "fruity_loop");
        getSfxMan().loadSfx("cro/sounds/loops/dark techno_endgame.wav", "rave");
        getSfxMan().loadSfx("cro/sounds/loops/level.au", "level");
        getSfxMan().loadSfx("cro/sounds/loops/hum.au", "hum");
        getSfxMan().loadSfx("cro/sounds/loops/s_finish.au", "s_finish");
        getSfxMan().loadSfx("cro/sounds/loops/fanfare.au", "fanfare");
        getSfxMan().loadSfx("cro/sounds/sfx/s_laser.au", "s_laser");
        getSfxMan().loadSfx("cro/sounds/sfx/lboom.au", "lboom");
        getSfxMan().loadSfx("cro/sounds/sfx/explosion1.au", "explosion1");
        getSfxMan().loadSfx("cro/sounds/sfx/sbounce.au", "sbounce");
        getSfxMan().loadSfx("cro/sounds/sfx/m_laser.au", "m_laser");
        getSfxMan().loadSfx("cro/sounds/sfx/ricochet00.au", "ricochet00");
        getSfxMan().loadSfx("cro/sounds/sfx/ricochet01.au", "ricochet01");
        getSfxMan().loadSfx("cro/sounds/sfx/ricochet02.au", "ricochet02");
        getSfxMan().loadSfx("cro/sounds/sfx/engine.au", "engine");
        getSfxMan().loadSfx("cro/sounds/sfx/rev.au", "rev");
        getSfxMan().loadSfx("cro/sounds/sfx/suck_in.au", "suck_in");
        getSfxMan().loadSfx("cro/sounds/sfx/ding.au", "ding");
        getSfxMan().loadSfx("cro/sounds/sfx/hp_spawn.au", "hp_spawn");
        Sprite sprite = null;
        SpaceJunkObj spaceJunkObj = null;
        Graphics2D g2D = null;
        int amt_of_rocks = 0;
        BufferedImage img = null;
        BufferedImage img2 = null;
        double imgSize = 0.9;
        int rock_level = 1;    
        float transparency = 0.6F;
        for(int j = 1; j < 20; j++)
        {
            switch((int)(2D * Math.random()))
            {
            case 0: // '\0'
                img = getSpriteMan().getSprite("spjunk1").getImage();
                img = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
                img.getGraphics().drawImage(getSpriteMan().getSprite("spjunk1").getImage(), 0, 0, null);
                break;

            case 1: // '\001'
                img = getSpriteMan().getSprite("spjunk1").getImage();
                img = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
                img.getGraphics().drawImage(getSpriteMan().getSprite("spjunk1").getImage(), 0, 0, null);
                break;

            default:
                img = getSpriteMan().getSprite("spjunk1").getImage();
                img = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
                img.getGraphics().drawImage(getSpriteMan().getSprite("spjunk1").getImage(), 0, 0, null);
                break;
            }
            g2D = (Graphics2D)img.getGraphics();            
            g2D.setColor(new Color(0.0F, 0.0F, 0.5F));            
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
            //g2D.rotate(Math.ceil(Math.random()*3),img.getWidth()/2,img.getHeight()/2);
            g2D.fillRect(0, 0, img.getHeight(null), img.getWidth(null));            
            //g2D.setColor(new Color(0.2F, 0.2F, 0.5F,1F));
            
            //g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));            
            
            
            double scale_width =  Math.ceil((img.getWidth() * imgSize));
            double scale_height =  Math.ceil((img.getWidth() * imgSize));
            Image im = img.getScaledInstance((int)Math.ceil(scale_width), (int)Math.ceil(scale_height), Image.SCALE_SMOOTH);            
            BufferedImage after = new BufferedImage((int)Math.ceil(scale_width), (int)Math.ceil(scale_height), BufferedImage.TYPE_4BYTE_ABGR);
            
            
            
            
            after.getGraphics().drawImage(im, 0, 0, null);
            AffineTransform at = new AffineTransform();
            at.rotate(Math.toRadians(Math.random()*360),((double)after.getWidth(null))/2,((double)after.getHeight(null))/2);
            
            //g2D = (Graphics2D)after.getGraphics();
            //g2D.rotate(Math.toRadians(Math.random()*360),after.getWidth(null)/2,im.getHeight(null)/2);
            //after.getGraphics().drawImage(after, -10, -10, null);
            
            //at.scale(2.1, 2.1);            
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);            
            //img2 = after;//scaleOp.filter(after,img2);  
            img2 = scaleOp.filter(after,null);  

            //img2 = img.getScaledInstance(img.getWidth(null) * 1 - (8 + imgSize * 4), img.getHeight(null) * 1 - (8 + imgSize * 4), BufferedImage.SCALE_SMOOTH);
            
           
            getSpriteMan().addSprite("spacejunk" + j, img2);
            
            if(++amt_of_rocks > rock_level)
            {
                rock_level = amt_of_rocks+2;
                 imgSize *= .65;
                 transparency *= 1.2;
                 if(transparency>=0.8) transparency = 0.8f;
            }
                for(int i = 0; i < 6; i++)
                {
                    sprite = getSpriteMan().getSprite("spacejunk" + j);
                    spaceJunkObj = new SpaceJunkObj(getVecDebris(), sprite, (int)((double)getPlayableXSize() * Math.random()), (int)((double)getPlayableYSize() * Math.random()), this);
                    spaceJunkObj.setXs(-0.5D);
                    spaceJunkObj.setXa(0.5D);
                    spaceJunkObj.setDxs((-0.2)-(sprite.getWidth()/20)-(Math.random()));
                    spaceJunkObj.setDys(0.0D);
                    getVecDebris().remove(spaceJunkObj);
                    getVecDebris().insertElementAt(spaceJunkObj, 0);
                }

            
        }

        for(int i = 0; i < 1; i++)
        {
            sprite = getSpriteMan().getSprite("nebula");
            spaceJunkObj = new SpaceJunkObj(getVecDebris(), sprite, (int)((double)getPlayableXSize() * Math.random()), (int)((double)getPlayableYSize() * Math.random()), this);
            spaceJunkObj.setXs(-Math.random());
            spaceJunkObj.setXa(0.0D);
            spaceJunkObj.setDxs(0.0D);
            spaceJunkObj.setDys(0.0D);
            getVecDebris().remove(spaceJunkObj);
            getVecDebris().insertElementAt(spaceJunkObj, 0);
        }

    }

    public void build()
    {
        output = new OutputClass(this);
        sfxMan = new SfxMan();        
        spriteMan = new SpriteMan();
        vecDebris = new Vector();
        vecDebris2 = new Vector();
        vecPlayer = new Vector();
        vecEnemies = new Vector();
        vecEnemyShots = new Vector();
        vecPlayerShots = new Vector();
        grid = new GridMan(this);
        //loadData();
        //reset();
    }

    public void reset()
    {
        
        grid.clearSectors();
        getVecDebris2().clear();
        getVecEnemies().clear();
        getVecEnemyShots().clear();
        getVecPlayer().clear();
        getVecPlayerShots().clear();
        InputClass.setKeyPressed(false);
        Obj obj = null;
        Sprite sprite = null;
        SpaceJunkObj spaceJunkObj = null;
        for(int i = 0; i < (150*7); i++)
        {
            spaceJunkObj = new SpaceJunkObj(getVecDebris2(), null, (int)((double)getPlayableXSize() * Math.random()), (int)((double)getLEVEL_PIXEL_HEIGHT() * Math.random()), this);
            spaceJunkObj.setColorR(0.2F);
            spaceJunkObj.setColorG((float)Math.random());
            spaceJunkObj.setColorB((float)Math.random());
        }

        for(int i = 0; i < 500; i++)
        {
            spaceJunkObj = new SpaceJunkObj(getVecDebris2(), null, 20, 2, (int)((double)getPlayableXSize() * Math.random()), (int)((double)getLEVEL_PIXEL_HEIGHT() * Math.random()), this);
            spaceJunkObj.setColorR((float)Math.random());
            spaceJunkObj.setColorG(0.9F);
            spaceJunkObj.setColorB((float)Math.random());
        }

        PlayerObj playerObj = new PlayerObj(getVecPlayer(), getSpriteMan().getSprite("ship_e"), 1, getPlayableYSize() / 2, this);
        playerObj.setVecShots(getVecPlayerShots());
        playerObj.setSfx_shoot1(getSfxMan().searchForClip("m_laser"));
        getSfxMan().loopClip("level");
        moreEnemies(30);
    }

    public void moreEnemies(int num)
    {
        int i = 0;
        for(i = 0; i < num / 1; i++)
        {
            Enemy2Obj e = new Enemy2Obj(getVecEnemies(), getSpriteMan().getSprite("enemy2"), 0, 0, this);
            e.setSfx_hit(getSfxMan().searchForClip("lboom"));
            e.setSfx_die(getSfxMan().searchForClip("explosion1"));
        }

        for(i = 0; i < num / 3; i++)
        {
            Enemy3Obj e = new Enemy3Obj(getVecEnemies(), getSpriteMan().getSprite("enemy3"), 0, 0, this);
            e.setSfx_hit(getSfxMan().searchForClip("lboom"));
            e.setSfx_die(getSfxMan().searchForClip("explosion1"));
        }

    }

    public void doLogicUpdate()
    {
    	//grid.clearSectors();        
    	
        Obj obj = null;
        int i = 0;
        for(i = 0; i < getVecDebris().size(); i++)
        {
            obj = (Obj)getVecDebris().get(i);
            obj.doLogic();
        }

        for(i = 0; i < getVecDebris2().size(); i++)
        {
            obj = (Obj)getVecDebris2().get(i);
            obj.doLogic();
        }

        for(i = 0; i < getVecEnemies().size(); i++)
        {
            obj = (Obj)getVecEnemies().get(i);
            obj.doLogic();
        }

        for(i = 0; i < getVecPlayer().size(); i++)
        {
            obj = (Obj)getVecPlayer().get(i);
            obj.doLogic();            
        }

        for(i = 0; i < getVecEnemyShots().size(); i++)
        {
            obj = (Obj)getVecEnemyShots().get(i);
            obj.doLogic();
        }

        for(i = getVecPlayerShots().size() - 1; i >= 0; i--)
        {
            obj = (Obj)getVecPlayerShots().get(i);
            obj.doLogic();            
        }
        /*Sector sec = null;
        int objPos = 0;
        
        for (int row = 0; row<GridMan.ROW_MAX; row++){			
			for (int col=0; col<GridMan.COLUMN_MAX;col++){				
				sec = grid.getGrid()[col][row];
				// checking each and every object in the sectors
				for (objPos = 0; objPos<sec.getObjList().size(); objPos++)
				{
					obj = (Obj)sec.getObjList().get(objPos);
					checkCollision(obj);
                                        obj.getSectorList().clear(); // clears the sector list of all the objects
				}
			}			
		}*/
        
        Obj.timer++;
    }

    final public void checkCollision(Obj self)
    {
        int trip = -1; // this is part of an optimization to reduse the number of call to Vector.indexOf function
        int count =0;
    	Obj obj = null;
        Sector sec = null;
        Vector list = new Vector();        
    	for (int i=0; i<self.getSectorList().size(); i++){ // going thru sector by sector
    		sec = (Sector)self.getSectorList().get(i);
                //sec.getObjList().remove(self); // removes the object from the object list reduces O in O^n
    		for (int j=0; j< sec.getObjList().size();j++){ // each going thru each sector object list
                        obj = (Obj) sec.getObjList().get(j);
    			if ( !list.contains(obj) ) list.add(obj);
                        if (obj == self ) {trip = j;/*sec.getObjList().remove(j); if(j>0) j--;*/}// Optimization code(avoides having to call Vector.indexOf(element)
    		}
                if (trip != -1) sec.getObjList().remove(trip);
                
    	}
        
    	for (int i=0; i<list.size();i++){
    		obj = (Obj)list.get(i);
    		if ( self.collisionCheck(obj)) {
    			self.collideWith(obj);
    			obj.collideWith(self);
    		}
    	}   	

    }
    /*
     *  return true to continue game adn false to exit game.
     */    
    public boolean msgPause(String msg, String loop)
    {        
        
       
        String msg2 = "Press Enter To Continue";
        String msg3 = "RushDown";
        boolean ready = false;
        boolean stop = false;
        int selectMax = 2;
        int select = selectMax;
        long waitFor = 0L;
        double selectX = 0.0D;
        double selectY = 0.0D;
        double selectSize = 0.0D;
        getClass();
        double menuX = 640D;
        getClass();
        double menuY = 168D;
        getClass();
        double splashX = 640D;
        double splashY = 360D;
        double menuXPos = 10D;
        double menuYPos = (double)playableYSize * 0.29999999999999999D;
        double splashXPos = playableXSize / 2 - spriteMan.getSprite("rushdown_splash").getWidth() / 2;
        double splashYPos = 0.0D;
        Obj obj = null;
        InputClass.setKeyPressed(false);
        //if(loop != null) getSfxMan().loopClip(loop);
        while(!stop) 
        {
             if (!StartHere.infLoop) {                
                    return false;
            }
            if(System.nanoTime()>= waitFor)
            {
                waitFor = System.nanoTime()+ 20000000L;
                if((InputClass.getPlayer1Up() || InputClass.getPlayer1Left()) && --select < 0)
                    select = 0;
                if((InputClass.getPlayer1Down() || InputClass.getPlayer1Right()) && ++select > selectMax)
                    select = selectMax;
                if(InputClass.isKeyPressed())
                    switch(select)
                    {
                    case 0: // '\0'
                        //System.exit(0);
                        StartHere.infLoop = false;//game.exitGame();
                        break;

                    case 1: // '\001'
                        msg = "High Score is " + highScore + " by " + name;
                        InputClass.setKeyPressed(false);
                        break;

                    case 2: // '\002'                        
                        stop = true;                        
                        break;
                    }
            }
            switch(select)
            {
            case 0: // '\0'
                selectX = menuX - 2D;
                selectY = menuY - 2D;
                selectSize = 72D;
                break;

            case 1: // '\001'
                selectX = menuX + 27D;
                selectY = menuY + 96D;
                selectSize = 84D;
                break;

            case 2: // '\002'
                selectX = menuX + 89D;
                selectY = menuY + 195D;
                selectSize = 124D;
                break;
            }
            if(!ready)
            {
                if(splashX < splashXPos)
                {
                    splashX += 40D;
                    if(splashX > splashXPos)
                        splashX = splashXPos;
                }
                if(splashY < splashYPos)
                {
                    splashY += 20D;
                    if(splashY > splashYPos)
                        splashY = splashYPos;
                }
                if(splashX > splashXPos)
                {
                    splashX -= 40D;
                    if(splashX < splashXPos)
                        splashX = splashXPos;
                }
                if(splashY > splashYPos)
                {
                    splashY -= 20D;
                    if(splashY < splashYPos)
                        splashY = splashYPos;
                }
                if(menuX < menuXPos)
                {
                    menuX += 70D;
                    if(menuX > menuXPos)
                        menuX = menuXPos;
                }
                if(menuY < menuYPos)
                {
                    menuY += 20D;
                    if(menuY > menuYPos)
                        menuY = menuYPos;
                }
                if(menuX > menuXPos)
                {
                    menuX -= 70D;
                    if(menuX < menuXPos)
                        menuX = menuXPos;
                }
                if(menuY > menuYPos)
                {
                    menuY -= 20D;
                    if(menuY < menuYPos)
                        menuY = menuYPos;
                }
                if(menuX == menuXPos && menuY == menuYPos && splashX == splashXPos && splashY == splashYPos)
                    ready = true;
            }
            for(int i = 0; i < getVecDebris().size(); i++)
            {
                obj = (Obj)getVecDebris().get(i);
                obj.doLogic();
            }

            for(int i = 0; i < getVecDebris2().size(); i++)
            {
                obj = (Obj)getVecDebris2().get(i);
                obj.doLogic();
            }            
            Graphics2D g = (Graphics2D)getOutput().getBufferStrategy().getDrawGraphics();
            getOutput().clearGraphics();
            getOutput().render(getVecDebris());
            getOutput().render(getVecDebris2());
            spriteMan.getSprite("rushdown_splash").draw(g, (int)splashX, (int)splashY);
            spriteMan.getSprite("rushdown_menu").draw(g, (int)menuX, (int)menuY);
            if(ready)
            {
                g.setColor(Color.GREEN);
                g.drawString(msg, playableXSize / 2 - (msg.length() / 2) * 5, playableYSize / 2 - 5);
                g.drawString(msg2, 0, playableYMax - 11);
                g.drawString(msg3, 0, playableYMax - 22);
                g.drawRoundRect((int)selectX, (int)selectY, (int)selectSize, (int)selectSize, 40, 20);
            }
            getOutput().present();
            try
            {
                Thread.sleep(20L);
            }
            catch(Exception e)
            {
                System.out.println(" exception in the sleep 'try method'");
            }
        }        
        InputClass.setKeyPressed(false);
        if(loop != null) getSfxMan().stopClip(loop);
        return true;
    }

    public void doHud(PlayerObj pObj, String fps)
    {        
        int entities = (vecDebris.size() + vecDebris2.size() + vecEnemies.size() + vecEnemyShots.size() + vecPlayer.size() + vecPlayerShots.size());
        Graphics2D g = (Graphics2D)getOutput().getDrawGraphics();        
        g.setColor(Color.YELLOW);
        g.drawString("PRESS ESC TO EXIT", getPlayableXMax() - 140, getPlayableYMax() - 10);
        spriteMan.getSprite("highscore").draw(g, getPlayableXSize() - 160, 0);
        g.drawString(name, (getPlayableXSize() - 160) + 80, 10);
        g.drawString(highScore+"", (getPlayableXSize() - 160) + 80, 25);
        spriteMan.getSprite("score").draw(g, 10, 0);
        g.drawString(pObj.getScore()+"", 63, 25);
        g.setColor(Color.RED);
        g.fillRect((int)((double)(getPlayableXSize() / 2) - 108.5D) + 62, 17, (int)((double)pObj.getHealth() / 2.8570000000000002D), 10);
        spriteMan.getSprite("shields").draw(g, (int)((double)(getPlayableXSize() / 2) - 108.5D), 0);
        g.setColor(Color.WHITE);        
        
        //g.drawString(String.format("FPS %",fps), 10, getPlayableYMax() - 10);
        g.drawString("fps: " + fps, 3, getPlayableYMax() - 30);        
        g.drawString("# of entities: " + entities, 3, getPlayableYMax() - 15);
    }

    public int getLEVEL_PIXEL_HEIGHT()
    {
        return LEVEL_PIXEL_HEIGHT;
    }

    public int getLEVEL_PIXEL_LENGTH()
    {
        return LEVEL_PIXEL_LENGTH;
    }

    public OutputClass getOutput()
    {
        return output;
    }

    public void setOutput(OutputClass output)
    {
        this.output = output;
    }

    public int getPlayableXSize()
    {
        return playableXSize;
    }

    public int getPlayableYSize()
    {
        return playableYSize;
    }

    public SfxMan getSfxMan()
    {
        return sfxMan;
    }

    public void setSfxMan(SfxMan sfxMan)
    {
        this.sfxMan = sfxMan;
    }

    public SpriteMan getSpriteMan()
    {
        return spriteMan;
    }

    public void setSpriteMan(SpriteMan spriteMan)
    {
        this.spriteMan = spriteMan;
    }

    public String getStrWinName()
    {
        return strWinName;
    }

    public void setStrWinName(String strWinName)
    {
        this.strWinName = strWinName;
    }

    public Vector getVecDebris()
    {
        return vecDebris;
    }

    public void setVecDebris(Vector vecDebris)
    {
        this.vecDebris = vecDebris;
    }

    public Vector getVecDebris2()
    {
        return vecDebris2;
    }

    public void setVecDebris2(Vector vecDebris2)
    {
        this.vecDebris2 = vecDebris2;
    }

    public Vector getVecEnemies()
    {
        return vecEnemies;
    }

    public void setVecEnemies(Vector vecEnemies)
    {
        this.vecEnemies = vecEnemies;
    }

    public Vector getVecEnemyShots()
    {
        return vecEnemyShots;
    }

    public void setVecEnemyShots(Vector vecEnemyShots)
    {
        this.vecEnemyShots = vecEnemyShots;
    }

    public Vector getVecPlayer()
    {
        return vecPlayer;
    }

    public void setVecPlayer(Vector vecPlayer)
    {
        this.vecPlayer = vecPlayer;
    }

    public Vector getVecPlayerShots()
    {
        return vecPlayerShots;
    }

    public void setVecPlayerShots(Vector vecPlayerShots)
    {
        this.vecPlayerShots = vecPlayerShots;
    }

    public int getHighScore()
    {
        return highScore;
    }

    public void setHighScore(int highScore)
    {
        this.highScore = highScore;
    }

   public void getDBInfo()
    {
        db.openConnection();
        if (!db.isConnected()) return;
        db.select("SELECT * FROM "+ dbTable +" ORDER BY score DESC;");        
        name = db.getValue("name");
        highScore = Integer.parseInt(db.getValue("score"));
        db.closeConnection();
    }

    public void updateDBInfo()
    {
        db.openConnection();
        if (!db.isConnected()) return;
        getClass();
        db.update("INSERT INTO "+dbTable+"(score,name) VALUES("+highScore+","+"\""+name+"\");");
        db.closeConnection();
    }

    public int getPlayableX()
    {
        return playableX;
    }

    public void setPlayableX(int playableX)
    {
        this.playableX = playableX;
    }

    public int getPlayableY()
    {
        return playableY;
    }

    public void setPlayableY(int playableY)
    {
        this.playableY = playableY;
    }

    public int getScreenX()
    {
        return screenX;
    }

    public int getScreenY()
    {
        return screenY;
    }

    public void setPlayableXSize(int playableXSize)
    {
        this.playableXSize = playableXSize;
    }

    public void setPlayableYSize(int playableYSize)
    {
        this.playableYSize = playableYSize;
    }

    public int getPlayableXMax()
    {
        return playableXMax;
    }

    public void setPlayableXMax(int playableXMax)
    {
        this.playableXMax = playableXMax;
    }

    public int getPlayableYMax()
    {
        return playableYMax;
    }

    public void setPlayableYMax(int playableYMax)
    {
        this.playableYMax = playableYMax;
    }


	/**
	 * @return Returns the grid.
	 */
	public GridMan getGrid() {
		return grid;
	}

	/**
	 * @param grid The grid to set.
	 */
	public void setGrid(GridMan grid) {
		this.grid = grid;
	}
}