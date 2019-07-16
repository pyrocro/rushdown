// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   StartHere.java

package cro.j2d.games.cuc.scroller;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.JOptionPane;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            World, OutputClass, SfxMan, PlayerObj

public class StartHere extends Applet
    implements Runnable
{
    //Timings
    static double desired_framerate = 120;
    static double desired_logic_rate = 60;
    static double framerate_delta = (1000000000/desired_framerate);        
    static double logic_rate_delta = (1000000000/desired_logic_rate);
    //**********************************************************************
    ThreadLogic logicThread = null;
    ThreadRender renderThread = null;

    public StartHere()
    {
        System.setProperty("sun.java2d.transaccel", "True");
        // System.setProperty("sun.java2d.trace", "timestamp,log,count");
        //System.setProperty("sun.java2d.opengl", "True");
        //System.setProperty("sun.java2d.d3d", "True");
        System.setProperty("sun.java2d.ddforcevram", "True");
        //System.getProperty()
        gameEnd = false;
        world = null;
        mainThread=null;
    }

    public void init()
    {
    }

    public void destroy()
    {
       stop();
    }

    public void start()
    {
       mainThread = new Thread(this);//Thread.currentThread();
       mainThread.start();
    }

    public void stop()
    {
        //mainThread.stop();
        //System.exit(0);
        infLoop = false;
    }

    public void setupApp()
    {
    	InputClass.setworld(world); // this has tobe done here in a non-static function
        world.setGame(this); // this has to be done here in a non-static function
    	world.getOutput().setupFrame("RushDown [O_o] S.E.(ALpha by Yohance McDonald)");
        world.getOutput().setEnabled(true);
        if (!world.getOutput().goFullscreen()){
        	JOptionPane.showMessageDialog(this, "Could not enter into fullscreen exclusive mode\n using decorating window...","Warning :(",  1);
        }
        //world.getOutput().setupFrame("RushDown [O_o] S.E.(ALpha by Yohance McDonald)");
        //world.getOutput().setupCanvas();
    }

    public void run()
    {
        
        StartHere game = new StartHere();
        game.world = new World(this);
        game.world.build();        
        setLayout(new BorderLayout());
        add("Center", game.world.getOutput());
        game.world.getOutput().setupCanvas();
        setVisible(true);
        setEnabled(true);
        StartHere.infLoop = true;
        while(StartHere.infLoop) 
        {
            if (game.gameLoop()){
                game.world.reset();                
            }
            else {
                if(game.exitGame()) break; else StartHere.infLoop = true;
            }
                
        }
    }

    public static void main(String arg[])
    {
        StartHere game = new StartHere();
        game.world = new World(game);
        game.world.build();
        //********** thread creation *****************
        Thread sd = new Thread(game.world.getSfxMan());
        sd.setName("rushdown Sound Thread");
        sd.setDaemon(true);
        sd.setPriority(Thread.MAX_PRIORITY);
        sd.start();
        //********************************************
        game.world.loadData();
        game.world.reset();        
        
        game.setupApp();
        StartHere.infLoop = true;
        while(StartHere.infLoop) 
        {
            if (game.gameLoop()){
                game.world.reset();                
            }
            else {
                if(game.exitGame()) break; else StartHere.infLoop = true;
            }
        }
        System.exit(0);
    }
    
    private boolean gameLoop()
    {        
        world.getSfxMan().loadClip("cro/sounds/loops/fruity_loop.au", "fanfare_");
        long lastLoopTime1 = 0L;
        long lastLoopTime2 = 0L;
        long lastLoopTime = 0L;
        
        
        
        
        int i = 0;
        int music_count = 0;
        double logicSpeed = 0L;
        double refreshRate = 0L;
        int tmp_int = 0;
        Obj obj = null;
        PlayerObj pObj = (PlayerObj)world.getVecPlayer().get(0);
        if (!world.msgPause("'Ctrl' to fire and arrow key to move", "fruity_loop")){
            return false; //return false to exit game if pause func return false;
        }
        world.getSfxMan().loopClip("fruity_loop");
        logicThread = new ThreadLogic(this.world);
        logicThread.setName("RushdownThreadLogic");
        logicThread.start();        
        renderThread = new ThreadRender(this.world);
        renderThread.setName("RushDownThreadRender");
        renderThread.start();
        while(!gameEnd)
        {            
            if(System.nanoTime() >= world.timer_delta){    
                lastLoopTime1 = System.nanoTime();
                world.fps = world.frames/((lastLoopTime1-world.last_timer)/1000000000);   
                System.out.println("FPS = " + world.fps);
                world.frames = 0;                
                
                world.lastLoopTimeAvg = (long)((lastLoopTime1-world.last_timer)/world.lastLoopTime_count);
                world.lastLoopTime_count = 0;
                
                world.last_timer = System.nanoTime();
                world.timer_delta = world.last_timer+1000000000;
            }
            
            if(System.nanoTime() >= world.delta)
            {
                
                world.delta = System.nanoTime()+ 1000000000L; 
                if(music_count++ > 10)
                {
                    music_count = 0;
                    world.moreEnemies(2);
                }
            }
            /*if(System.nanoTime()>= logicSpeed)
            {
                logicSpeed = System.nanoTime() + logic_rate_delta;
                world.doLogicUpdate();
                tmp_int = pObj.getScore() + 1;
                pObj.setScore(tmp_int);
            }*/
            /*if( System.nanoTime() >= refreshRate)//refreshRate)
            {
                refreshRate = System.nanoTime()+ framerate_delta;//16666666L;
                world.getOutput().clearGraphics();
                world.getOutput().render(world.getVecDebris());
                world.getOutput().render(world.getVecDebris2());
                world.getOutput().render(world.getVecPlayer());
                world.getOutput().render(world.getVecEnemies());
                world.getOutput().render(world.getVecEnemyShots());
                world.getOutput().render(world.getVecPlayerShots());
                world.doHud(pObj, " " + fps+" looptime:"+(lastLoopTimeAvg));
                world.getOutput().present();
                frames++;
            }*/
            if(pObj.getHealth() <= 0)
            {
                world.getSfxMan().stopClip("fruity_loop");
                world.getSfxMan().stopClip("s_finish");
                world.msgPause("GAME OVER :(.....Score is " + pObj.getScore(), "rave");
                if(pObj.getScore() > world.getHighScore())
                {
                    world.setHighScore(pObj.getScore());
                    world.getSfxMan().playClip("fanfare");
                    world.name = JOptionPane.showInputDialog(this, "enter you name", "New High Score :)", 1);
                    world.updateDBInfo();
                }
                return true;
            }
            /*try
            {
                Thread.yield();//sleep(0, 100);
            }
            catch(Exception e)
            {
                System.out.println(" exception in the sleep 'try method'"+e.toString());
            }*/
            if (!StartHere.infLoop) {
                stopThreads();
                return false;                
            }            
            world.lastLoopTime_count++;
        }
        this.stopThreads();
        return false;
    }
    public void stopThreads()
    {
        if(logicThread !=null ){
            logicThread.StopMe();
            //logicThread.stop();
            try{
                logicThread.join();
            } catch(Exception e){
                System.out.println("main thread was waiting on thread to finish" + e.toString());
            }
        }
        if(logicThread != null){
            renderThread.StopMe();
            //logicThread.stop();
            try{
                renderThread.join();
            } catch(Exception e){
                System.out.println("main thread was waiting on thread to finish" + e.toString());
            }
            /*while(logicThread.isAlive()){
                //wait for logic thread to die
            }*/
        }
    }
    public boolean exitGame(){
        world.getOutput().setEnabled(false);      
        world.getOutput().setupCanvas();
    	world.getOutput().exitFullscreen();        
    	switch(JOptionPane.showConfirmDialog(world.getOutput(),"Do you really want to exit?","Exit Confirmation", JOptionPane.YES_NO_OPTION))
    	{
    	case 0: 
            //System.exit(0);
            return true;
    	default:;break;
    	}
        this.infLoop = true; // turn back on infinite loop;
        //world.setGame(this); // this has to be done here in a non-static function
    	//world.getOutput().setupFrame("RushDown [O_o] S.E.(ALpha by Yohance McDonald)");
        world.getOutput().setupCanvas();
        world.getOutput().setEnabled(true);
    	world.getOutput().goFullscreen();
        return false;
    }

    private static final long serialVersionUID = 0x3630343138373939L;    
    boolean gameEnd;
    World world;
    volatile Thread mainThread;
    public static volatile boolean infLoop = true;
}