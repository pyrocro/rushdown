/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Obj;
import java.util.ArrayList;

public class ObjMan {
    private ArrayList live = new ArrayList();
    private ArrayList dead = new ArrayList();
    private ArrayList onScreen = new ArrayList();

    public ArrayList getOnScreen() {
        return this.onScreen;
    }

    public ArrayList getLiveList() {
        return this.live;
    }

    public ArrayList getDeadList() {
        return this.dead;
    }

    public void addObj(Obj obj) {
        this.live.add(obj);
    }
}

