// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SpaceJunkObj.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Particle, World, Sprite, Obj

public class SpaceJunkObj extends Particle
{

    public SpaceJunkObj(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        reset();
        x = x_tmp;
        y = y_tmp;
    }

    public SpaceJunkObj(Vector vec, Sprite s, int w_tmp, int h_tmp, int x_tmp, int y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        reset();
        x = x_tmp;
        y = y_tmp;
        if(sprite == null)
        {
            width = w_tmp;
            height = h_tmp;
            xs = -width;
        }
    }

    public void reset()
    {    	
        if(sprite == null)
        {
            width = 1 + (int)(7D * Math.random());
            height = (int)(3D * Math.random());
            xs = -width;
            ys = 0.0D;
            xa = -width;
            ya = 0.0D;
            dxs = -width;
            dys = 0.0D;
        }
        x = objWorld.getPlayableXSize();
        y = objWorld.getPlayableY() + (int)((double)(objWorld.getPlayableYSize() - height) * Math.random());
        removeFromScreen();
    }

    private void isVisible()
    {
        if(x < (double)objWorld.getPlayableXSize() && x + (double)width > (double)objWorld.getPlayableX())
            visible = true;
        if(x + (double)width < 0.0D)
            reset();
    }

    public void doLogic()
    {
        move();
    }

    public void draw(Graphics g)
    {
        if(!visible)return;
        if(sprite != null)
        {
            sprite.draw(g, (int)x, (int)y);
            return;
        }        
        {
            color = new Color(colorR, colorG, colorB);
            g.setColor(color);
            g.fillRect((int)x, (int)y, (int)width, (int)height);
            return;
        }
    }

    public void collideWith(Obj obj)
    {
    }

    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    public static final int TYPE_4 = 3;
}