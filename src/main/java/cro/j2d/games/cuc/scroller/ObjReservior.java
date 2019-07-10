// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ObjReservior.java

package cro.j2d.games.cuc.scroller;

import java.util.Vector;

public class ObjReservior
{

    ObjReservior(int max, Object obj)
    {
        maximum = 0;
        reserved = false;
        availible = null;
        reserve = null;
        maximum = max;
        availible = new Vector();
        reserve = new Vector();
    }

    public void putBack(Object object)
    {
        availible.add(object);
    }

    public Object takeOut(int index)
    {
        return reserve.remove(0);
    }

    public int maximum;
    private boolean reserved;
    private Vector availible;
    private Vector reserve;
}