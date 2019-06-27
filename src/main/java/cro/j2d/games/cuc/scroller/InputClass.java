/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.Vector;

public class InputClass
extends KeyAdapter {
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

    public static boolean getPlayer1Left() {
        return player1Left;
    }

    public static boolean getPlayer1Right() {
        return player1Right;
    }

    public static boolean getPlayer1Up() {
        return player1Up;
    }

    public static boolean getPlayer1Down() {
        return player1Down;
    }

    public static boolean getPlayer1Fire1() {
        return player1Fire1;
    }

    public static boolean getPlayer1Fire2() {
        return player1Fire2;
    }

    public static boolean getPlayer1Fire3() {
        return player1Fire3;
    }

    public void resetPlayer1KeyPressed() {
        player1Left = false;
        player1Right = false;
        player1Up = false;
        player1Down = false;
        player1Fire1 = false;
        player1Fire2 = false;
        player1Fire3 = false;
    }

    public Vector getKeyBuf() {
        return keyBuf;
    }

    public static int getKey() {
        return key;
    }

    public static void resetKey() {
        key = 0;
    }

    public InputClass() {
        System.out.println("Static input instanced");
    }

    public static InputClass get() {
        return input;
    }

    public void keyPressed(KeyEvent e) {
        
        switch (e.getKeyCode()) {
            case 37: {
                player1Left = true;
                break;
            }
            case 39: {
                player1Right = true;
                break;
            }
            case 38: {
                player1Up = true;
                break;
            }
            case 40: {
                player1Down = true;
                break;
            }
            case KeyEvent.VK_SHIFT: {
                player1Fire1 = true;
                break;
            }
            case 18: {
                player1Fire2 = true;
                break;
            }
            case 32: {
                player1Fire3 = true;
                break;
            }
            case 10: {
                keyPressed = true;
                break;
            }
        }
        key = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37: {
                player1Left = false;
                break;
            }
            case 39: {
                player1Right = false;
                break;
            }
            case 38: {
                player1Up = false;
                break;
            }
            case 40: {
                player1Down = false;
                break;
            }
            case KeyEvent.VK_SHIFT: {
                player1Fire1 = false;
                break;
            }
            case 18: {
                player1Fire2 = false;
                break;
            }
            case 32: {
                player1Fire3 = false;
                break;
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\u001b') {
            System.exit(0);
        }
    }

    protected static boolean isKeyPressed() {
        return keyPressed;
    }

    public static void setKeyPressed(boolean keyPressed) {
        InputClass.keyPressed = keyPressed;
    }
}

