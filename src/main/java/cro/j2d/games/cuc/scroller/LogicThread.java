/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cro.j2d.games.cuc.scroller;

/**
 *
 * @author Yohance McDonald
 */
public class LogicThread extends Thread {

    World world = null;
    long logicSpeed = 0L;
    int tmp_int = 0;
    PlayerObj pObj = null;
    boolean stopMe = false;

    LogicThread(World w) {
        world = w;
        pObj = (PlayerObj) this.world.getVecPlayer().get(0);
    }

    public void StopMe() {
        //this.interrupt();
        this.stopMe = true;
    }

    public void run() {

        while (!this.stopMe) {
            if (System.currentTimeMillis() >= logicSpeed) {
                logicSpeed = System.currentTimeMillis() + 16L;
                this.world.doLogicUpdate();
                tmp_int = pObj.getScore() + 1;
                pObj.setScore(tmp_int);
                try {
                    Thread.yield();
                } catch (Exception e) {
                    System.out.println(" exception in the sleep 'try method'");
                }
            }
        }

    }
}
