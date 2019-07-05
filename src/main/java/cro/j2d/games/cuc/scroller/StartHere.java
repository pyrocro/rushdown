/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import java.applet.Applet;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;

public class StartHere
extends Applet
implements Runnable {
    private static final long serialVersionUID = 3904678262931405113L;
    String dbHost = "free-mysql.BizHostNet.com";
    String dbPort = "3306";
    String dbName = "1109083684";
    String dbUser = "1109083684";
    String dbPass = "12345";
    boolean gameEnd = false;
    World world = null;
    LogicThread logicThread = null;

    public void init() {
    }

    public void destroy() {
        System.exit(0);
    }

    public void start() {
        Thread th = new Thread(this);
        th.start();
    }

    public void stop() {
        System.exit(0);
    }

    public void setupApp() {
        this.world.getOutput().setupFrame("RushDown [O_o] S.E.(ALpha by Yohance McDonald)");
        this.world.getOutput().setupCanvas();
    }

    public void run() {
        StartHere game = new StartHere();
        game.world = new World();
        game.world.build();
        this.setLayout(new BorderLayout());
        this.add("Center", game.world.getOutput());
        game.world.getOutput().setupCanvas();
        this.setVisible(true);
        this.setEnabled(true);
        boolean infLoop = true;
        while (infLoop) {
            game.gameLoop();
            game.world.reset();
        }
    }

    public static void main(String[] arg) {
        StartHere game = new StartHere();
        game.world = new World();
        game.world.build();
        game.setupApp();
        boolean infLoop = true;
        while (infLoop) {
            game.gameLoop();
            game.world.reset();
        }
    }
    
    private boolean gameLoop() {
        this.world.getSfxMan().loadClip("cro/sounds/loops/fruity_loop.au", "fanfare_");
        long lastLoopTime = 0L;
        long delta = System.currentTimeMillis();
        int loop = 0;
        double fps = 0.0;
        boolean i = false;
        int music_count = 0;
        long logicSpeed = 0L;
        long refreshRate = 0L;
        int tmp_int = 0;
        Object obj = null;
        PlayerObj pObj = (PlayerObj)this.world.getVecPlayer().get(0);
        this.world.msgPause("'Ctrl' to fire and arrow key to move", "fruity_loop");
        this.world.getSfxMan().loopClip("fruity_loop");
        logicThread = new LogicThread(this.world);
        logicThread.setName("RushdownLogicThread");
        logicThread.start();
        while (!this.gameEnd) {
            if (System.currentTimeMillis() >= delta) {
                fps = (long)loop * (System.currentTimeMillis() / delta);
                delta = System.currentTimeMillis() + 1000L;
                loop = 0;
                if (music_count++ > 10) {
                    music_count = 0;
                    this.world.moreEnemies(6);
                }
            }
            /*if (System.currentTimeMillis() >= logicSpeed) {
                logicSpeed = System.currentTimeMillis() + 16L;
                this.world.doLogicUpdate();
                tmp_int = pObj.getScore() + 1;
                pObj.setScore(tmp_int);
            }*/
            if (System.currentTimeMillis() >= refreshRate) {
                refreshRate = System.currentTimeMillis() + 8L;
                this.world.getOutput().clearGraphics();
                this.world.getOutput().render(this.world.getVecDebris());
                this.world.getOutput().render(this.world.getVecDebris2());
                this.world.getOutput().render(this.world.getVecPlayer());
                this.world.getOutput().render(this.world.getVecEnemies());
                this.world.getOutput().render(this.world.getVecEnemyShots());
                this.world.getOutput().render(this.world.getVecPlayerShots());
                this.world.doHud(pObj, " " + fps);
                this.world.getOutput().present();
                ++loop;
            }
            if (pObj.getHealth() <= 0) {
                this.world.getSfxMan().stopClip("fruity_loop");
                this.world.getSfxMan().stopClip("s_finish");
                this.world.msgPause("GAME OVER :(.....Score is " + pObj.getScore(), "rave");
                if (pObj.getScore() > this.world.getHighScore()) {
                    this.world.setHighScore(pObj.getScore());
                    this.world.getSfxMan().playClip("fanfare");
                    this.world.name = JOptionPane.showInputDialog(this, "enter you name", "New High Score :)", 1);
                    this.world.updateDBInfo(this.dbHost, this.dbPort, this.dbName, this.dbUser, this.dbPass);
                }
                return true;
            }                   
            try {
                //Thread.sleep(1L);
                Thread.yield();
            }
            catch (Exception e) {
                System.out.println(" exception in the sleep 'try method'");
            }
        }
        logicThread.StopMe();
        logicThread.stop();
        while(logicThread.isAlive()){
            //wait for logic thread to die
        }
        return false;
    }
}

