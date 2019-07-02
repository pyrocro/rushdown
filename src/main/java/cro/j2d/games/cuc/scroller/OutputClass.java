/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.InputClass;
import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OutputClass
extends Canvas {
    private static final long serialVersionUID = 4048791273341138227L;
    World world = null;
    private Sprite background = null;
    ArrayList arrayList = null;
    Obj obj = null;
    boolean exitCondition = false;
    private BufferStrategy strategy;
    Graphics graphics = null;
    Graphics2D graphics2D = null;

    public void setKeyListner(KeyAdapter input) {
        this.addKeyListener(InputClass.get());
    }

    public OutputClass(World w) {
        this.world = w;
    }

    public Canvas setupCanvas() {
        this.addKeyListener(InputClass.get());
        this.setBounds(0, 0, this.world.getScreenX(), this.world.getScreenY());        
        this.setIgnoreRepaint(true);
        this.createBufferStrategy(2);
        this.strategy = this.getBufferStrategy();
        this.graphics = this.strategy.getDrawGraphics();
        this.graphics2D = (Graphics2D)this.graphics;
        this.requestFocus();
        return this;
    }

    public void setupFrame(String strWinName) {
        JFrame container = new JFrame(strWinName);
        JPanel panel = (JPanel)container.getContentPane();
        panel.setPreferredSize(new Dimension(this.world.getScreenX(), this.world.getScreenY()));
        panel.setLayout(null);
        //https://stackoverflow.com/questions/45722445/how-to-set-jframe-to-full-screen
        //container.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //container.setUndecorated(true);
        //**********************************************************************
        container.pack();
        container.setResizable(false);
        container.setVisible(true);        
        container.addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        panel.add(this);
    }

    public Graphics2D getDrawGraphics() {
        return (Graphics2D)this.strategy.getDrawGraphics();
    }

    public void render(Vector vec) {
        Obj obj = null;
        vec.size();
        for (int i = 0; i < vec.size(); ++i) {
            obj = (Obj)vec.get(i);
            obj.draw(this.graphics);
        }
    }

    public void present() {
        this.strategy.show();
    }

    public boolean clearGraphics() {
        this.graphics2D = (Graphics2D)this.graphics;
        this.graphics2D.setColor(Color.BLACK);
        this.graphics2D.fillRect(0, 0, this.world.getScreenX(), this.world.getScreenY());
        this.graphics2D.dispose();
        this.graphics = this.strategy.getDrawGraphics();
        return true;
    }

    public boolean clearPlayableGraphics() {
        this.graphics2D = (Graphics2D)this.graphics;
        this.graphics2D.setColor(Color.BLACK);
        this.graphics2D.fillRect(this.world.getPlayableX(), this.world.getPlayableY(), this.world.getPlayableXMax(), this.world.getPlayableYMax());
        this.graphics2D.dispose();
        this.graphics = this.strategy.getDrawGraphics();
        return true;
    }

}

