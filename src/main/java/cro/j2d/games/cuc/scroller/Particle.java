// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Particle.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.*;
import java.util.Vector;
import cro.j2d.games.cuc.scroller.grid.GridMan;
import cro.j2d.games.cuc.scroller.grid.Sector;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            Obj, World, Sprite

public class Particle extends Obj
{

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Particle(Vector vec, Sprite s, double x_tmp, double y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        drawFx = 2;
        type = 1;
        text = new String();
        cRate = -0.045F;
        color = null;
        if(sprite == null)
        {
            width = 2;
            height = 4;
            x = x_tmp;
            y = y_tmp;
            xa = 100D;
            ya = 100D;
            dxs = 0.0D;
            dys = 0.0D;
            health = 5;
            colorR = 1.0F;
            colorG = 0.1F;
            colorB = 0.2F;
            cRate = -0.025F;
        }
        particleFont = new Font("Times New Roman", 1, 20);
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
        if(x < (double)objWorld.getPlayableXSize() && x > 0.0D)
            visible = true;
        if(x + (double)width < 0.0D)
            reset();
    }

    public void doLogic()
    {
        move();
        if(--health <= 0 || (double)colorR <= 0.20000000000000001D && (double)colorG <= 0.20000000000000001D && (double)colorB <= 0.20000000000000001D)
            reset();
    }

    public void draw(Graphics g)
    {
        Font f = null;
        if(!visible) return;
        if(sprite != null)
        {
            //sprite.draw(g, (int)x, (int)y);
            return;
        }
        
        switch(drawFx)
        {
        case FX_FADE: // '\002'
            if(colorR + cRate >= 0.0F && colorR + cRate <= 1.0F)
                colorR += cRate;
            if(colorG + cRate >= 0.0F && colorG + cRate <= 1.0F)
                colorG += cRate;
            if(colorB + cRate >= 0.0F && colorB + cRate <= 1.0F)
                colorB += cRate;
            color = new Color(colorR, colorG, colorB);            
            break;
        }
        g.setColor(color);
        switch(type)
        {
        case 1: // '\001'
            g.fillRect((int)x, (int)y, (int)width, (int)height);
            break;

        case 2: // '\002'
            g.drawRect((int)x, (int)y, (int)width, (int)height);
            // fall through

        case 3: // '\003'
            g.fill3DRect((int)x, (int)y, (int)width, (int)height, true);
            break;

        case 4: // '\004'
            g.drawRect((int)x, (int)y, (int)width, (int)height);
            //g.drawRoundRect((int)x, (int)y, (int)width, (int)height, 10, 10);
            break;

        case 20: // '\024'
            g.drawLine((int)x, (int)y, (int)(x + width), (int)(y + height));
            break;

        case 40: // '('
            g.setFont(particleFont);
            g.drawString(text, (int)x, (int)y);
            break;
        default:
            g.drawString("invalid drawing type", (int)x, (int)y);
            break;
        }
        /*if (this.sectorList.size()>0){
        	for ( int i= 0; i<this.sectorList.size();i++){
        	Sector sec = (Sector)this.sectorList.get(i);
                g.setColor(Color.BLUE);
        	g.drawString("secX"+sec.getX()+"-secY"+sec.getY(), (int)x, (int)y);
        	g.drawRect((int)sec.getX(),(int)sec.getY(),(int)sec.getWidth(),(int)sec.getHeight());
        	}
        }*/
    }

    public static void createHPText(Obj obj)
    {
        Particle ex = new Particle(obj.vector, null, (int)obj.x, (int)(((obj.y + (double)(obj.height / 2)) - 4D) + Math.random() * 8D), obj.objWorld);
        if(Math.random() > 0.5D)
            ex.setDxs(2D * Math.random());
        else
            ex.setDxs(2D * Math.random() * -1D);
        if(Math.random() > 0.5D)
            ex.setDys(2D * Math.random());
        else
            ex.setDys(2D * Math.random() * -1D);
        ex.setHealth(60);
        ex.setColorR(0.8F);
        ex.setColorG(1.0F);
        ex.setColorB(0.8F);
        ex.setCRate(-0.015F);
        ex.setType(40);
        ex.setText("HP+"+obj.health);
    }

    public static void createPTText(Obj obj)
    {
        Particle ex = new Particle(obj.vector, null, (int)obj.x, (int)(((obj.y + (double)(obj.height / 2)) - 4D) + Math.random() * 8D), obj.objWorld);
        if(Math.random() > 0.5D)
            ex.setDxs(2D * Math.random());
        else
            ex.setDxs(2D * Math.random() * -1D);
        if(Math.random() > 0.5D)
            ex.setDys(2D * Math.random());
        else
            ex.setDys(2D * Math.random() * -1D);
        ex.setHealth(60);
        ex.setColorR(1.0F);
        ex.setColorG(0.8F);
        ex.setColorB(0.8F);
        ex.setCRate(-0.015F);
        ex.setType(40);
        ex.setText("PT+"+obj.worth);
    }

    public static void createMissedText(Obj obj)
    {
        Particle ex = new Particle(obj.vector, null, (int)obj.x, (int)(((obj.y + (double)(obj.height / 2)) - 4D) + Math.random() * 8D), obj.objWorld);
        if(Math.random() > 0.5D)
            ex.setDxs(2D * Math.random());
        else
            ex.setDxs(2D * Math.random() * -1D);
        if(Math.random() > 0.5D)
            ex.setDys(2D * Math.random());
        else
            ex.setDys(2D * Math.random() * -1D);
        ex.setHealth(60);
        ex.setColorR(1.0F);
        ex.setColorG(1.0F);
        ex.setColorB(0.6F);
        ex.setCRate(-0.015F);
        ex.setType(40);
        ex.setText("Missed");
    }

    public void collideWith(Obj obj)
    {
    }

    protected float getColorB()
    {
        return colorB;
    }

    protected void setColorB(float colorB)
    {
        this.colorB = colorB;
        if(this.colorB>1) this.colorB = 1;
        if(this.colorB<0) this.colorB = 0;
    }

    protected float getColorG()
    {
        return colorG;
    }

    protected void setColorG(float colorG)
    {
        this.colorG = colorG;
        if(this.colorG>1) this.colorG = 1;
        if(this.colorG<0) this.colorG = 0;
    }

    protected float getColorR()
    {
        return colorR;
    }

    protected void setColorR(float colorR)
    {
        this.colorR = colorR;
        if(this.colorR>1) this.colorR = 1;
        if(this.colorR<0) this.colorR = 0;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public float getCRate()
    {
        return cRate;
    }

    public void setCRate(float rate)
    {
        cRate = rate;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
        colorR = this.color.getRed() / 255;
        colorG = this.color.getGreen() / 255;
        colorB = this.color.getBlue() / 255;
    }

    static final int TYPE_FILLRECT = 1;
    static final int TYPE_RECT = 2;
    static final int TYPE_3DRECT = 3;
    static final int TYPE_ROUNDRECT = 4;
    static final int TYPE_LINE = 20;
    static final int TYPE_TEXT = 40;
    static final int FX_NONE = 1;
    static final int FX_FADE = 2;
    protected int drawFx;
    int type;
    String text;
    float colorR;
    float colorG;
    float colorB;
    float cRate;
    Color color;
    Font particleFont;
}