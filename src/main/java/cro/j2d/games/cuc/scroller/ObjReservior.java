/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import java.util.Vector;

public class ObjReservior {
    public int maximum = 0;
    private boolean reserved = false;
    private Vector availible = null;
    private Vector reserve = null;

    ObjReservior(int max, Object obj) {
        this.maximum = max;
        this.availible = new Vector();
        this.reserve = new Vector();
    }

    public void putBack(Object object) {
        this.availible.add(object);
    }

    public Object takeOut(int index) {
        return this.reserve.remove(0);
    }
}

