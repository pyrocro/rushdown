/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Enemy2Obj;
import cro.j2d.games.cuc.scroller.Enemy3Obj;
import cro.j2d.games.cuc.scroller.InputClass;
import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.OutputClass;
import cro.j2d.games.cuc.scroller.PlayerObj;
import cro.j2d.games.cuc.scroller.Sfx;
import cro.j2d.games.cuc.scroller.SfxMan;
import cro.j2d.games.cuc.scroller.SpaceJunkObj;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.SpriteMan;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.PrintStream;
import java.util.Vector;

public class World {
    final String version = "Alpha 2";
    private int highScore = 20000;
    public String name = "Yohance";
    private OutputClass output = null;
    private String strWinName = "rushDown [0_o] Alpha(by Yohance Mcdonald)";
    private SpriteMan spriteMan = null;
    private SfxMan sfxMan = null;
    private Vector vecDebris = null;
    private Vector vecDebris2 = null;
    private Vector vecPlayer = null;
    private Vector vecEnemies = null;
    private Vector vecEnemyShots = null;
    private Vector vecPlayerShots = null;
    final int screenX = 1280;
    final int screenY = 720;
    private int playableX = 0;
    private int playableY = 40;
    private int playableXSize = screenX;
    private int playableYSize = screenY - this.playableY;
    private int playableXMax = this.playableXSize + this.playableX;
    private int playableYMax = this.playableYSize + this.playableY;
    int LEVEL_PIXEL_LENGTH = this.playableXSize * 4;
    int LEVEL_PIXEL_HEIGHT = this.playableYSize * 2;

    public void loadData() {
        this.getSpriteMan().loadSprite("cro/j2d/pics/hero-scarf-rakuten-giant-panda.png", "rakuten-panda");
        this.getSpriteMan().loadSprite("cro/j2d/pics/proud-panda.png", "proud-panda");
        this.getSpriteMan().loadSprite("cro/j2d/pics/nebula_2.png", "nebula");
        this.getSpriteMan().loadSprite("cro/j2d/pics/enemy3_dmg_1.gif", "spjunk1");
        this.getSpriteMan().loadSprite("cro/j2d/pics/enemy3.gif", "spjunk2");
        this.getSpriteMan().loadSprite("cro/j2d/pics/ship_e.png", "ship_e");
        this.getSpriteMan().loadSprite("cro/j2d/pics/rushdown_splash.png", "rushdown_splash");
        this.getSpriteMan().loadSprite("cro/j2d/pics/rushdown_menu.png", "rushdown_menu");
        this.getSpriteMan().loadSprite("cro/j2d/pics/rushdown_shields.png", "shields");
        this.getSpriteMan().loadSprite("cro/j2d/pics/rushdown_score.png", "score");
        this.getSpriteMan().loadSprite("cro/j2d/pics/highscore.png", "highscore");
        this.getSpriteMan().loadSprite("cro/j2d/pics/pickup1.gif", "pickup1");
        this.getSpriteMan().loadSprite("cro/j2d/pics/pickup2.gif", "pickup2");
        this.getSpriteMan().loadSprite("cro/j2d/pics/pickup3.gif", "pickup3");
        this.getSpriteMan().loadSprite("cro/j2d/pics/pickup4.gif", "pickup4");
        //this.getSpriteMan().loadSprite("cro/j2d/pics/enemy2.gif", "enemy2");
        this.getSpriteMan().loadSprite("cro/j2d/pics/rakuten-bee-small.png", "enemy2");
        //this.getSpriteMan().loadSprite("cro/j2d/pics/enemy3_dmg_1.gif", "enemy3");
        this.getSpriteMan().loadSprite("cro/j2d/pics/hero-panda-small.png", "enemy3");
        this.getSfxMan().setNullSound(this.getSfxMan().loadSfx("cro/sounds/sfx/null.au", "null"));
        this.getSfxMan().loadSfx("cro/sounds/loops/fruity_loop.au", "fruity_loop");
        this.getSfxMan().loadSfx("cro/sounds/loops/rave.au", "rave");
        this.getSfxMan().loadSfx("cro/sounds/loops/level.au", "level");
        this.getSfxMan().loadSfx("cro/sounds/loops/hum.au", "hum");
        this.getSfxMan().loadSfx("cro/sounds/loops/s_finish.au", "s_finish");
        this.getSfxMan().loadSfx("cro/sounds/loops/fanfare.au", "fanfare");
        this.getSfxMan().loadSfx("cro/sounds/sfx/s_laser.au", "s_laser");
        this.getSfxMan().loadSfx("cro/sounds/sfx/lboom.au", "lboom");
        this.getSfxMan().loadSfx("cro/sounds/sfx/explosion1.au", "explosion1");
        this.getSfxMan().loadSfx("cro/sounds/sfx/sbounce.au", "sbounce");
        this.getSfxMan().loadSfx("cro/sounds/sfx/m_laser.au", "m_laser");
        this.getSfxMan().loadSfx("cro/sounds/sfx/ricochet00.au", "ricochet00");
        this.getSfxMan().loadSfx("cro/sounds/sfx/ricochet01.au", "ricochet01");
        this.getSfxMan().loadSfx("cro/sounds/sfx/ricochet02.au", "ricochet02");
        this.getSfxMan().loadSfx("cro/sounds/sfx/engine.au", "engine");
        this.getSfxMan().loadSfx("cro/sounds/sfx/rev.au", "rev");
        this.getSfxMan().loadSfx("cro/sounds/sfx/suck_in.au", "suck_in");
        this.getSfxMan().loadSfx("cro/sounds/sfx/ding.au", "ding");
        this.getSfxMan().loadSfx("cro/sounds/sfx/hp_spawn.au", "hp_spawn");
        Sprite sprite = null;
        SpaceJunkObj spaceJunkObj = null;
        Graphics2D g2D = null;
        int amt_of_rocks = 0;
        Image img = null;
        Image img2 = null;
        int imgSize = -4;
        for (int j = 1; j < 6; ++j) {
            switch ((int)(2.0 * Math.random())) {
                case 0: {
                    img = this.getSpriteMan().getSprite("spjunk1").getImage();
                    break;
                }
                case 1: {
                    img = this.getSpriteMan().getSprite("spjunk1").getImage();
                    break;
                }
                default: {
                    img = this.getSpriteMan().getSprite("spjunk1").getImage();
                }
            }
            g2D = (Graphics2D)img.getGraphics();
            g2D.setColor(new Color(0.0f, 0.0f, 0.5f));
            g2D.setComposite(AlphaComposite.getInstance(3, 0.2f));
            g2D.fillRect(0, 0, img.getHeight(null), img.getWidth(null));
            img2 = img.getScaledInstance(img.getWidth(null) * 1 - (8 + imgSize * 4), img.getHeight(null) * 1 - (8 + imgSize * 4), 4);
            imgSize += 2;
            this.getSpriteMan().addSprite("spacejunk" + j, img2);
            if (++amt_of_rocks <= 2) continue;
            for (int i = 0; i < amt_of_rocks + 2; ++i) {
                sprite = this.getSpriteMan().getSprite("spacejunk" + j);
                spaceJunkObj = new SpaceJunkObj(this.getVecDebris(), sprite, (int)((double)this.getPlayableXSize() * Math.random()), (int)((double)this.getPlayableYSize() * Math.random()), this);
                spaceJunkObj.setXs(-0.5);
                spaceJunkObj.setXa(0.5);
                spaceJunkObj.setDxs(-0.2 - (double)(sprite.getWidth() / 10));
                spaceJunkObj.setDys(0.0);
                this.getVecDebris().remove(spaceJunkObj);
                this.getVecDebris().insertElementAt(spaceJunkObj, 0);
            }
        }
        for (int i = 0; i < 1; ++i) {
            sprite = this.getSpriteMan().getSprite("nebula");
            spaceJunkObj = new SpaceJunkObj(this.getVecDebris(), sprite, (int)((double)this.getPlayableXSize() * Math.random()), (int)((double)this.getPlayableYSize() * Math.random()), this);
            spaceJunkObj.setXs(0.0);
            spaceJunkObj.setXa(0.0);
            spaceJunkObj.setDxs(0.0);
            spaceJunkObj.setDys(0.0);
            this.getVecDebris().remove(spaceJunkObj);
            this.getVecDebris().insertElementAt(spaceJunkObj, 0);
        }
    }

    public void build() {
        this.output = new OutputClass(this);
        this.sfxMan = new SfxMan();
        this.spriteMan = new SpriteMan();
        this.vecDebris = new Vector();
        this.vecDebris2 = new Vector();
        this.vecPlayer = new Vector();
        this.vecEnemies = new Vector();
        this.vecEnemyShots = new Vector();
        this.vecPlayerShots = new Vector();
        this.loadData();
        this.reset();
    }

    public void reset() {
        int i;
        this.getVecDebris2().clear();
        this.getVecEnemies().clear();
        this.getVecEnemyShots().clear();
        this.getVecPlayer().clear();
        this.getVecPlayerShots().clear();
        InputClass.setKeyPressed(false);
        Object obj = null;
        Object sprite = null;
        SpaceJunkObj spaceJunkObj = null;
        for (i = 0; i < 1500; ++i) {
            spaceJunkObj = new SpaceJunkObj(this.getVecDebris2(), null, (int)((double)this.getPlayableXSize() * Math.random()), (int)((double)this.getLEVEL_PIXEL_HEIGHT() * Math.random()), this);
            spaceJunkObj.setColorR(0.2f);
            spaceJunkObj.setColorG((float)Math.random());
            spaceJunkObj.setColorB((float)Math.random());
        }
        for (i = 0; i < 50; ++i) {
            spaceJunkObj = new SpaceJunkObj(this.getVecDebris2(), null, 20, 2, (int)((double)this.getPlayableXSize() * Math.random()), (int)((double)this.getLEVEL_PIXEL_HEIGHT() * Math.random()), this);
            spaceJunkObj.setColorR((float)Math.random());
            spaceJunkObj.setColorG(0.2f);
            spaceJunkObj.setColorB((float)Math.random());
        }
        PlayerObj playerObj = new PlayerObj(this.getVecPlayer(), this.getSpriteMan().getSprite("ship_e"), 1, this.getPlayableYSize() / 2, this);
        playerObj.setVecShots(this.getVecPlayerShots());
        playerObj.setSfx_shoot1(this.getSfxMan().searchForClip("m_laser"));
        this.getSfxMan().loopClip("level");
        this.moreEnemies(6);
    }

    public void moreEnemies(int num) {
        Obj e;
        int i = 0;
        for (i = 0; i < num / 4; ++i) {
            e = new Enemy2Obj(this.getVecEnemies(), this.getSpriteMan().getSprite("enemy2"), 0, 0, this);
            e.setSfx_hit(this.getSfxMan().searchForClip("lboom"));
            e.setSfx_die(this.getSfxMan().searchForClip("explosion1"));
        }
        for (i = 0; i < num / 3; ++i) {
            e = new Enemy3Obj(this.getVecEnemies(), this.getSpriteMan().getSprite("enemy3"), 0, 0, this);
            e.setSfx_hit(this.getSfxMan().searchForClip("lboom"));
            e.setSfx_die(this.getSfxMan().searchForClip("explosion1"));
        }
    }

    public void doLogicUpdate() {
        Obj obj = null;
        int i = 0;
        for (i = 0; i < this.getVecDebris().size(); ++i) {
            obj = (Obj)this.getVecDebris().get(i);
            obj.doLogic();
        }
        for (i = 0; i < this.getVecDebris2().size(); ++i) {
            obj = (Obj)this.getVecDebris2().get(i);
            obj.doLogic();
        }
        for (i = 0; i < this.getVecEnemies().size(); ++i) {
            obj = (Obj)this.getVecEnemies().get(i);
            obj.doLogic();
            this.checkCollison(obj, this.getVecPlayer());
            this.checkCollison(obj, this.getVecEnemies());
        }
        for (i = 0; i < this.getVecPlayer().size(); ++i) {
            obj = (Obj)this.getVecPlayer().get(i);
            obj.doLogic();
            this.checkCollison(obj, this.getVecEnemies());
        }
        for (i = 0; i < this.getVecEnemyShots().size(); ++i) {
            obj = (Obj)this.getVecEnemyShots().get(i);
            obj.doLogic();
        }
        for (i = this.getVecPlayerShots().size() - 1; i >= 0; --i) {
            obj = (Obj)this.getVecPlayerShots().get(i);
            obj.doLogic();
            this.checkCollison(obj, this.getVecEnemies());
        }
        ++Obj.timer;
    }

    public void checkCollison(Obj self, Vector vec) {
        Obj obj = null;
        int i = 0;
        for (i = 0; i < vec.size(); ++i) {
            obj = (Obj)vec.get(i);
            if (!Obj.collisionCheck(self, obj)) continue;
            self.collideWith(obj);
        }
    }

    public void msgPause(String msg, String loop) {
        int selectMax;
        String msg2 = "Press Enter To Continue";
        String msg3 = "RushDown";
        boolean ready = false;
        boolean stop = false;
        int select = selectMax = 2;
        long waitFor = 0L;
        double selectX = 0.0;
        double selectY = 0.0;
        double selectSize = 0.0;
        this.getClass();
        double menuX = 640.0;
        this.getClass();
        double menuY = 480.0 * 0.35;
        this.getClass();
        double splashX = 640.0;
        double splashY = 360.0;
        double menuXPos = 10.0;
        double menuYPos = (double)this.playableYSize * 0.3;
        double splashXPos = this.playableXSize / 2 - this.spriteMan.getSprite("rushdown_splash").getWidth() / 2;
        double splashYPos = 0.0;
        Obj obj = null;
        InputClass.setKeyPressed(false);
        if (loop != null) {
            this.getSfxMan().loopClip(loop);
        }
        while (!stop) {
            int i;
            if (System.currentTimeMillis() >= waitFor) {
                waitFor = System.currentTimeMillis() + 100L;
                if ((InputClass.getPlayer1Up() || InputClass.getPlayer1Left()) && --select < 0) {
                    select = selectMax;
                }
                if ((InputClass.getPlayer1Down() || InputClass.getPlayer1Right()) && ++select > selectMax) {
                    select = 0;
                }
                if (InputClass.isKeyPressed()) {
                    switch (select) {
                        case 0: {
                            System.exit(0);
                            break;
                        }
                        case 1: {
                            msg = "High Score is " + this.highScore + " by " + this.name;
                            InputClass.setKeyPressed(false);
                            break;
                        }
                        case 2: {
                            stop = true;
                            break;
                        }
                    }
                }
            }
            switch (select) {
                case 0: {
                    selectX = menuX - 2.0;
                    selectY = menuY - 2.0;
                    selectSize = 72.0;
                    break;
                }
                case 1: {
                    selectX = menuX + 27.0;
                    selectY = menuY + 96.0;
                    selectSize = 84.0;
                    break;
                }
                case 2: {
                    selectX = menuX + 89.0;
                    selectY = menuY + 195.0;
                    selectSize = 124.0;
                    break;
                }
            }
            if (!ready) {
                if (splashX < splashXPos && (splashX += 40.0) > splashXPos) {
                    splashX = splashXPos;
                }
                if (splashY < splashYPos && (splashY += 20.0) > splashYPos) {
                    splashY = splashYPos;
                }
                if (splashX > splashXPos && (splashX -= 40.0) < splashXPos) {
                    splashX = splashXPos;
                }
                if (splashY > splashYPos && (splashY -= 20.0) < splashYPos) {
                    splashY = splashYPos;
                }
                if (menuX < menuXPos && (menuX += 70.0) > menuXPos) {
                    menuX = menuXPos;
                }
                if (menuY < menuYPos && (menuY += 20.0) > menuYPos) {
                    menuY = menuYPos;
                }
                if (menuX > menuXPos && (menuX -= 70.0) < menuXPos) {
                    menuX = menuXPos;
                }
                if (menuY > menuYPos && (menuY -= 20.0) < menuYPos) {
                    menuY = menuYPos;
                }
                if (menuX == menuXPos && menuY == menuYPos && splashX == splashXPos && splashY == splashYPos) {
                    ready = true;
                }
            }
            for (i = 0; i < this.getVecDebris().size(); ++i) {
                obj = (Obj)this.getVecDebris().get(i);
                obj.doLogic();
            }
            for (i = 0; i < this.getVecDebris2().size(); ++i) {
                obj = (Obj)this.getVecDebris2().get(i);
                obj.doLogic();
            }
            Graphics2D g = (Graphics2D)this.getOutput().getBufferStrategy().getDrawGraphics();
            this.getOutput().clearGraphics();
            this.getOutput().render(this.getVecDebris());
            this.getOutput().render(this.getVecDebris2());
            this.spriteMan.getSprite("rushdown_splash").draw(g, (int)splashX, (int)splashY);
            this.spriteMan.getSprite("rushdown_menu").draw(g, (int)menuX, (int)menuY);
            if (ready) {
                g.setColor(Color.GREEN);
                g.drawString(msg, this.playableXSize / 2 - msg.length() / 2 * 5, this.playableYSize / 2 - 5);
                g.drawString(msg2, 0, this.playableYMax - 11);
                g.drawString(msg3, 0, this.playableYMax - 22);
                g.drawRoundRect((int)selectX, (int)selectY, (int)selectSize, (int)selectSize, 40, 20);
            }
            this.getOutput().present();
            try {
                Thread.sleep(20L);
            }
            catch (Exception e) {
                System.out.println(" exception in the sleep 'try method'");
            }
        }
        InputClass.setKeyPressed(false);
        if (loop != null) {
            this.getSfxMan().stopClip(loop);
        }
    }

    public void doHud(PlayerObj pObj, String fps) {
        Graphics2D g = (Graphics2D)this.getOutput().getBufferStrategy().getDrawGraphics();
        g.setColor(Color.YELLOW);
        g.drawString("PRESS ESC TO EXIT", this.getPlayableXMax() - 140, this.getPlayableYMax() - 10);
        this.spriteMan.getSprite("highscore").draw(g, this.getPlayableXSize() - 160, 0);
        g.drawString(this.name, this.getPlayableXSize() - 160 + 80, 10);
        g.drawString("" + this.highScore, this.getPlayableXSize() - 160 + 80, 25);
        this.spriteMan.getSprite("score").draw(g, 10, 0);
        g.drawString("" + pObj.getScore(), 63, 25);
        g.setColor(Color.RED);
        g.fillRect((int)((double)(this.getPlayableXSize() / 2) - 108.5) + 62, 17, (int)((double)pObj.getHealth() / 2.857), 10);
        this.spriteMan.getSprite("shields").draw(g, (int)((double)(this.getPlayableXSize() / 2) - 108.5), 0);
        this.spriteMan.getSprite("proud-panda").draw(g, (int)((double)(this.getPlayableXSize()) - 100), (int)((double)(this.getPlayableYSize()) - 65));
        g.setColor(Color.WHITE);
        g.drawString("fps: " + fps, 10, this.getPlayableYMax() - 10);
        g.drawString("# of entities: " + (this.vecDebris.size() + this.vecDebris2.size() + this.vecEnemies.size() + this.vecEnemyShots.size() + this.vecPlayer.size() + this.vecPlayerShots.size()), 90, this.getPlayableYMax() - 10);
    }

    public int getLEVEL_PIXEL_HEIGHT() {
        return this.LEVEL_PIXEL_HEIGHT;
    }

    public int getLEVEL_PIXEL_LENGTH() {
        return this.LEVEL_PIXEL_LENGTH;
    }

    public OutputClass getOutput() {
        return this.output;
    }

    public void setOutput(OutputClass output) {
        this.output = output;
    }

    public int getPlayableXSize() {
        return this.playableXSize;
    }

    public int getPlayableYSize() {
        return this.playableYSize;
    }

    public SfxMan getSfxMan() {
        return this.sfxMan;
    }

    public void setSfxMan(SfxMan sfxMan) {
        this.sfxMan = sfxMan;
    }

    public SpriteMan getSpriteMan() {
        return this.spriteMan;
    }

    public void setSpriteMan(SpriteMan spriteMan) {
        this.spriteMan = spriteMan;
    }

    public String getStrWinName() {
        return this.strWinName;
    }

    public void setStrWinName(String strWinName) {
        this.strWinName = strWinName;
    }

    public Vector getVecDebris() {
        return this.vecDebris;
    }

    public void setVecDebris(Vector vecDebris) {
        this.vecDebris = vecDebris;
    }

    public Vector getVecDebris2() {
        return this.vecDebris2;
    }

    public void setVecDebris2(Vector vecDebris2) {
        this.vecDebris2 = vecDebris2;
    }

    public Vector getVecEnemies() {
        return this.vecEnemies;
    }

    public void setVecEnemies(Vector vecEnemies) {
        this.vecEnemies = vecEnemies;
    }

    public Vector getVecEnemyShots() {
        return this.vecEnemyShots;
    }

    public void setVecEnemyShots(Vector vecEnemyShots) {
        this.vecEnemyShots = vecEnemyShots;
    }

    public Vector getVecPlayer() {
        return this.vecPlayer;
    }

    public void setVecPlayer(Vector vecPlayer) {
        this.vecPlayer = vecPlayer;
    }

    public Vector getVecPlayerShots() {
        return this.vecPlayerShots;
    }

    public void setVecPlayerShots(Vector vecPlayerShots) {
        this.vecPlayerShots = vecPlayerShots;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void getDBInfo(String host, String port, String database, String user, String pass) {
    }

    public void updateDBInfo(String host, String port, String database, String user, String pass) {
    }

    public int getPlayableX() {
        return this.playableX;
    }

    public void setPlayableX(int playableX) {
        this.playableX = playableX;
    }

    public int getPlayableY() {
        return this.playableY;
    }

    public void setPlayableY(int playableY) {
        this.playableY = playableY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setPlayableXSize(int playableXSize) {
        this.playableXSize = playableXSize;
    }

    public void setPlayableYSize(int playableYSize) {
        this.playableYSize = playableYSize;
    }

    public int getPlayableXMax() {
        return this.playableXMax;
    }

    public void setPlayableXMax(int playableXMax) {
        this.playableXMax = playableXMax;
    }

    public int getPlayableYMax() {
        return this.playableYMax;
    }

    public void setPlayableYMax(int playableYMax) {
        this.playableYMax = playableYMax;
    }
}

