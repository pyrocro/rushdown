// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ObjMan.java

package cro.j2d.games.cuc.scroller;

import java.util.ArrayList;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Obj

public class ObjMan
{

    public ArrayList getOnScreen()
    {
        return onScreen;
    }

    public ArrayList getLiveList()
    {
        return live;
    }

    public ArrayList getDeadList()
    {
        return dead;
    }

    public ObjMan()
    {
        live = new ArrayList();
        dead = new ArrayList();
        onScreen = new ArrayList();
    }

    public void addObj(Obj obj)
    {
        live.add(obj);
    }

    private ArrayList live;
    private ArrayList dead;
    private ArrayList onScreen;
}