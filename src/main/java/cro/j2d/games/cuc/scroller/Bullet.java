// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Bullet.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintStream;
import java.util.Vector;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Obj, World, Sprite

public class Bullet extends Obj
{

    public Bullet(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
    {
        super(vec, null, x_tmp, y_tmp, w);
        width = 20;
        height = 2;
        x = x_tmp;
        y = y_tmp;
        xs = width;
        ys = 0.0D;
    }

    public void reset()
    {
        removeFromScreen();
        vector.remove(this);
    }
    
    public void removeFromScreen(){
        super.removeFromScreen();        
    }

    private void isVisible()
    {
        if(x < (double)objWorld.getPlayableXSize() && x + (double)width > 0.0D && y + (double)height > 0.0D && y < (double)objWorld.getPlayableYSize())
        {
            visible = true;
        } else
        {
            reset();
            System.out.println("removed Laser....");
        }
    }

    public void doLogic()
    {
        move();
    }

    public void draw(Graphics g)
    {
        if(!visible)
        {
            return;
        } else
        {
            g.setColor(Color.CYAN);
            g.fill3DRect((int)x, (int)y, (int)width, (int)height, true);
            return;
        }
    }

    public void collideWith(Obj obj)
    {
    }
}