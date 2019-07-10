// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/17/2006 7:44:56 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   InputClass.java

package cro.j2d.games.cuc.scroller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.Vector;

public class InputClass extends KeyAdapter
{

    public static boolean getPlayer1Left()
    {
        return player1Left;        
    }

    public static boolean getPlayer1Right()
    {
        return player1Right;
    }

    public static boolean getPlayer1Up()
    {
        return player1Up;
    }

    public static boolean getPlayer1Down()
    {
        return player1Down;
    }

    public static boolean getPlayer1Fire1()
    {
        return player1Fire1;
    }

    public static boolean getPlayer1Fire2()
    {
        return player1Fire2;
    }

    public static boolean getPlayer1Fire3()
    {
        return player1Fire3;
    }

    public void resetPlayer1KeyPressed()
    {
        player1Left = false;
        player1Right = false;
        player1Up = false;
        player1Down = false;
        player1Fire1 = false;
        player1Fire2 = false;
        player1Fire3 = false;
    }

    public Vector getKeyBuf()
    {
        return keyBuf;
    }

    public static int getKey()
    {
        return key;
    }

    public static void resetKey()
    {
        key = 0;
    }

    public InputClass()
    {
        System.out.println("Static input instanced");
    }

    public static InputClass get()
    {
        return input;
    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
        case 37: // '%'
            player1Left = true;
            break;

        case 39: // '\''
            player1Right = true;
            break;

        case 38: // '&'
            player1Up = true;
            break;

        case 40: // '('
            player1Down = true;
            break;

        case KeyEvent.VK_SHIFT: // '\021'
            player1Fire1 = true;
            break;

        case 18: // '\022'
            player1Fire2 = true;
            break;

        case 32: // ' '
            player1Fire3 = true;
            break;

        case 10: // '\n'
            keyPressed = true;
            break;
        }
        key = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e)
    {
        
        switch(e.getKeyCode())
        {
        case KeyEvent.VK_LEFT: // '%'
            player1Left = false;
            break;

        case KeyEvent.VK_RIGHT: // '\''
            player1Right = false;
            break;

        case KeyEvent.VK_UP: // '&'
            player1Up = false;
            break;

        case KeyEvent.VK_DOWN: // '('
            player1Down = false;
            break;

        case KeyEvent.VK_SHIFT: // '\021'
            player1Fire1 = false;
            break;

        case KeyEvent.VK_SPACE: // '\022'
            player1Fire2 = false;
            break;

        case KeyEvent.VK_ALT: // ' '
            player1Fire3 = false;
            break;
        }
    }

    public void keyTyped(KeyEvent e)
    {
        if(e.getKeyChar() == '\033') //ESC key is pressed
        {
            StartHere.infLoop = false;
            //world.exitGame();
        }
            
    }

    protected static boolean isKeyPressed()
    {
        return keyPressed;
    }

    public static void setKeyPressed(boolean k)
    {
        keyPressed = k;
    }    
	/**
	 * @return Returns the objWorld.
	 */
	public static World getWorld() {
		return world;
	}

	/**
	 * @param objWorld The objWorld to set.
	 */
	public static void setworld(World w) {
		world = w;
	}
    private static World world;
    private static boolean keyPressed = false;
    private static boolean player1Left = false;
    private static boolean player1Right = false;
    private static boolean player1Up = false;
    private static boolean player1Down = false;
    private static boolean player1Fire1 = false;
    private static boolean player1Fire2 = false;
    private static boolean player1Fire3 = false;
    private static InputClass input = new InputClass();
    private static Vector keyBuf = new Vector();
    private static int key = 0;

}