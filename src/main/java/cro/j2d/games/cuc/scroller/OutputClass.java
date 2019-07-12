// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/8/2006 1:52:32 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   OutputClass.java

package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Referenced classes of package cro.j2d.games.cuc.scroller:
//            InputClass, MouseInput, World, Obj, 
//            Sprite

public class OutputClass extends Canvas
{
    private static final long serialVersionUID = 0x3830323631333533L;
    Window win;
    World world;
    private Sprite backGround;
    private 
    ArrayList arrayList;
    Obj obj;
    boolean exitCondition;
    private BufferStrategy strategy;
    Graphics graphics;
    /*volatile*/ Graphics2D graphics2D;
    JFrame container;

	final boolean FULLSCREEN = false;

    public void setKeyListner(KeyAdapter input)
    {    
        addKeyListener(InputClass.get());
    }

    public OutputClass(World w)
    {    	 
        win = null;
        world = null;
        backGround = null;
        arrayList = null;
        obj = null;
        exitCondition = false;
        graphics = null;
        graphics2D = null;
        container = null;
        world = w;        
    }

    public Canvas setupCanvas()
    {
        addKeyListener(InputClass.get());
        //addMouseListener(MouseInput.getMouseInput());
        setBounds(0, 0, world.getScreenX(), world.getScreenY());
        setIgnoreRepaint(true);        
        createBufferStrategy(2);
        strategy = getBufferStrategy();        
        graphics = strategy.getDrawGraphics();
        graphics2D = (Graphics2D)graphics;        
        requestFocus();
        /*backGround = world.getSpriteMan().loadSprite("cro/j2d/pics/background.png","background");
        backGround.setImage( backGround.getImage().getScaledInstance(world.getScreenX(),world.getScreenY(),Image.SCALE_FAST));
        backGround.getImage().setAccelerationPriority(1);*/
        return this;
    }    
    /*
     * Compairs the systems list of support modes with the world's requested mode  
     */
    
    private DisplayMode getValidDisplay(){
    	System.out.println("Testing requested display mode....");
    	DisplayMode[] preferredDisplayModes = 
    		new DisplayMode[]
    		                {
                        new DisplayMode(world.getScreenX(), world.getScreenY(),32,120),
                        new DisplayMode(world.getScreenX(), world.getScreenY(),16,120),
                        new DisplayMode(world.getScreenX(), world.getScreenY(),8,120),
    			new DisplayMode(world.getScreenX(), world.getScreenY(),32,75),
    			new DisplayMode(world.getScreenX(), world.getScreenY(),16,75),
    			new DisplayMode(world.getScreenX(), world.getScreenY(),8,75),
                        new DisplayMode(world.getScreenX(), world.getScreenY(),32,60),
    			new DisplayMode(world.getScreenX(), world.getScreenY(),16,60),
    			new DisplayMode(world.getScreenX(), world.getScreenY(),8,60),
                        new DisplayMode(world.getScreenX(), world.getScreenY(),32,30),
    			new DisplayMode(world.getScreenX(), world.getScreenY(),16,30),
    			new DisplayMode(world.getScreenX(), world.getScreenY(),8,30)
    			};
    	
    	
    	DisplayMode sModes[] = this.getDrawGraphics().getDeviceConfiguration().getDevice().getDisplayModes(); //supported modes
    	
    	
    	for (int i=0; i<preferredDisplayModes.length;i++){
    		for(int j=0; j<sModes.length;j++){
    			if(preferredDisplayModes[i].getWidth() == sModes[j].getWidth())
    				if ( preferredDisplayModes[i].getHeight() == sModes[j].getHeight())
    					if (preferredDisplayModes[i].getBitDepth() == sModes[j].getBitDepth())
    						if (preferredDisplayModes[i].getRefreshRate() == sModes[j].getRefreshRate())
    						{
    							System.out.println("Using Resolution");
    							System.out.println("Width \t"+sModes[j].getWidth());
    							System.out.println("Height\t"+sModes[j].getHeight());
    							System.out.println("BitDepth\t"+sModes[j].getBitDepth());
    							System.out.println("Refreshrate\t"+sModes[j].getRefreshRate());
    							return sModes[j];
    						}
    		}
    	}
    	return null;
    }
    public boolean goFullscreen()    
    {                
    	
    	if (!FULLSCREEN)return false;
    	DisplayMode dM = getValidDisplay();
    	//Trying to get to fullscreen
    	if (dM == null) return false; // if a vald mode was not found
    	if (!this.getDrawGraphics().getDeviceConfiguration().getDevice().isFullScreenSupported()){            
    		return false;
    	}
        GraphicsDevice gD = this.getDrawGraphics().getDeviceConfiguration().getDevice();        
    	gD.setFullScreenWindow(world.getOutput().getContainer());
        //dM = new DisplayMode(world.getScreenX(), world.getScreenY(), 16, 60 );
        graphics2D.getDeviceConfiguration().getDevice().setDisplayMode(dM);
        //End of full screen attempt.
    	return true;
    }
    public void exitFullscreen(){
        if(!FULLSCREEN) return;
    	this.getDrawGraphics().getDeviceConfiguration().getDevice().setFullScreenWindow(null);
        this.setPreferredSize(new Dimension(world.getScreenX(), world.getScreenY()));
        this.getContainer().dispose();
        this.getContainer().setUndecorated(false);
        this.getContainer().setIgnoreRepaint(true);
        this.getContainer().setVisible(true);
        this.getContainer().pack();        
    }

    public void setupFrame(String strWinName)
    {
        container = new JFrame(strWinName);
        JPanel panel = (JPanel)container.getContentPane();
        panel.setPreferredSize(new Dimension(world.getScreenX(), world.getScreenY()));
        panel.setLayout(null);
             
        container.setResizable(false);
        
        if (FULLSCREEN){
            container.dispose();
            container.setUndecorated(true);
        } else{
            container.pack();
        }        
        
        container.setIgnoreRepaint(true);
        container.setVisible(true);    
        container.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                StartHere.infLoop = false;
                //world.exitGame();
            }

            //final OutputClass thisClass = OutputClass.this;
            //super();
            
        });        
        panel.add(this);
        //setups the drawing canvas 
        setupCanvas();
    }

    public Graphics2D getDrawGraphics()
    {
        return (Graphics2D)strategy.getDrawGraphics();
    }

    public void render(Vector vec)
    {
        synchronized (vec){
            Obj obj = null;
            int sz = vec.size();
            //vec.size();
            //getDrawGraphics().setXORMode(Color.RED);
            for(int i = 0; i < sz; i++)
            {
                obj = (Obj)vec.get(i);
                obj.draw(graphics);
            }
        }

    }

    public void render(Graphics g, Sprite sp, double x, double y)
    {        
        sp.draw(g, x, y);
    }

    public void render(Graphics g, String str, double x, double y)
    {
        graphics2D = (Graphics2D)graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        graphics2D.drawString(str, (int)x, (int)y);
    }

    public void present()
    {   
        strategy.show();
    }

    public boolean clearGraphics()
    {        
        graphics2D = (Graphics2D)graphics;        
        graphics2D.setColor(Color.BLACK);
        //graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
        graphics2D.fillRect(0, 0, world.getScreenX(), world.getScreenY());        
        //backGround.draw(graphics2D,0,0);
        //graphics2D.dispose();
        graphics = strategy.getDrawGraphics();
        return true;
    }
    
    public boolean clearGraphicsBackground()
    {
        graphics2D = (Graphics2D)graphics;
        //graphics2D.setColor(Color.BLACK);
        //graphics2D.fillRect(0, 0, world.getScreenX(), world.getScreenY());
        backGround.draw(graphics2D,0,0);
        //graphics2D.dispose();
        graphics = strategy.getDrawGraphics();
        return true;
    }

    public boolean clearPlayableGraphics()
    {        
        graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(world.getPlayableX(), world.getPlayableY(), world.getPlayableXMax(), world.getPlayableYMax());
        
        //graphics2D.dispose();
        graphics = strategy.getDrawGraphics();
        return true;
    }

    /**
	 * @return Returns the graphics2D.
	 */
	public Graphics2D getGraphics2D() {
		return graphics2D;
	}

	/**
	 * @return Returns the container.
	 */
	public JFrame getContainer() {
		return container;
	}	
}