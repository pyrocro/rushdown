/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cro.j2d.games.cuc.scroller;

import static cro.j2d.games.cuc.scroller.StartHere.framerate_delta;

/**
 *
 * @author pyroc
 */
public class ThreadRender extends Thread{
     World world = null;
    double refreshRate = 0L;    
    int tmp_int = 0;
    PlayerObj pObj = null;
    boolean stopMe = false;

    ThreadRender(World w) {
        world = w;
        pObj = (PlayerObj) this.world.getVecPlayer().get(0);
    }

    public void StopMe() {
        //this.interrupt();
        this.stopMe = true;
    }

    @Override
    public void run() {

        while (!this.stopMe) {
            if( System.nanoTime() >= refreshRate)//refreshRate)
            {
                refreshRate = System.nanoTime()+ framerate_delta;//16666666L;
                world.getOutput().clearGraphics();
                world.getOutput().render(world.getVecDebris());
                world.getOutput().render(world.getVecDebris2());
                world.getOutput().render(world.getVecPlayer());
                world.getOutput().render(world.getVecEnemies());
                world.getOutput().render(world.getVecEnemyShots());
                world.getOutput().render(world.getVecPlayerShots());
                world.doHud(pObj, " " + world.fps+" looptime:"+(world.lastLoopTimeAvg));
                world.getOutput().present();
                world.frames++;
            }
        }

    }
    
}
