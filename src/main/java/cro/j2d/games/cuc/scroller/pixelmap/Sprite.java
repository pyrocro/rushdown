// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Sprite.java

package cro.j2d.games.cuc.scroller.pixelmap;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import static java.util.function.LongUnaryOperator.identity;

public class Sprite
{
    private AffineTransform identity = new AffineTransform();
    public Sprite(BufferedImage img)
    {        
        name = new String();
        image = img;
        image.setAccelerationPriority(0.99f);
        bitmask = new int[img.getWidth(null)] [img.getHeight(null)] [4];
        makeBitmask();
    }
    private int [] processPixel(int pixel){
        int [] pixelvalue = new int[4];
        int alpha = (pixel >> 24) & 0xff;
        int red   = (pixel >> 16) & 0xff;
        int green = (pixel >> 8 ) & 0xff;
        int blue  = (pixel      ) & 0xff;
        pixelvalue[0] = alpha;
        pixelvalue[1] = red;
        pixelvalue[2] = green;
        pixelvalue[3] = blue;
        return pixelvalue;
        
    }
    private void makeBitmask(){
        int x = 0;
        int y = 0;
        int w = getWidth();
        int h = getHeight();
        int[] pixels = new int[w*h];
        PixelGrabber pg = new PixelGrabber(image, x, y, w, h, pixels, 0, w);
        
        try{
            pg.grabPixels();
        } catch (InterruptedException e){
            System.err.println("interrupted waiting for pixels"); 
            return;
        }
        if ( (pg.getStatus() & ImageObserver.ABORT) != 0){
          System.err.println("image fetch aborted or errored");
          return;
        }        
        for (int j = 0; j<h; j++){            
            for (int i = 0; i<w; i++){
                bitmask[x+i] [y+j] = processPixel(pixels[j*w+i]);                
                if (bitmask[x+i] [y+j] [0] <=0) System.out.print("0"); else System.out.print("1");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    

    public int getHeight()
    {
        return image.getHeight(null);
    }

    public int getWidth()
    {
        return image.getWidth(null);
    }

    public void draw(Graphics g, double x, double y)
    {
        Graphics2D g2D = (Graphics2D) g;
        //g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05F));
        g2D.drawImage(image, (int)x, (int)y, null);
        //g.drawImage(image, (int)x, (int)y, null);        
    }
    public void draw(Graphics g, int x, int y,double angle) {
        if(angle > 360 || angle < 0 ) angle = 0;
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans = new AffineTransform();
        trans.setTransform(identity);
        trans.rotate( Math.toRadians(angle),(int)(x+this.image.getWidth(null)/2),(int)(y+this.image.getHeight(null)/2) );
        trans.translate(x, y);
        g2d.drawImage(image, trans, null);
    }

    public BufferedImage getImage()
    {
        return image;
    }
    public void setImage(BufferedImage img){
        image = img;
    }
     public int[][][] getBitmask() {
        return bitmask;
    }     
    private BufferedImage image;
    private String name;

   
    private int [][][]bitmask;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}