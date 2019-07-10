// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/8/2006 1:52:32 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MouseInput.java

package cro.mouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class MouseInput extends MouseAdapter
{
	private Vector posVec;

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void mouseClicked(MouseEvent event)
    {
        x = event.getX();
        y = event.getY();        
        posVec.add(event);
        clicked = true;
    }
    public MouseEvent getClick(){
    	MouseEvent mEvent = (MouseEvent)posVec.get(0);
    	posVec.remove(0);
    	return mEvent;
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public MouseInput()
    {    	
    }

    public static MouseInput getMouseInput()
    {
        return mouseInput;
    }

    public static boolean isClicked()
    {
        return clicked;
    }

    public static void setClicked(boolean c)
    {
        clicked = c;
    }

    public static int getX()
    {
        return x;
    }

    public static int getY()
    {
        return y;
    }

    private static boolean clicked = false;
    private static int x = 0;
    private static int y = 0;
    private static MouseInput mouseInput = new MouseInput();

}

