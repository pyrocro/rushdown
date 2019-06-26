/*
 * Decompiled with CFR 0.139.
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.Obj;
import cro.j2d.games.cuc.scroller.Sprite;
import cro.j2d.games.cuc.scroller.World;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class Particle
extends Obj {
    static final int TYPE_FILLRECT = 1;
    static final int TYPE_RECT = 2;
    static final int TYPE_3DRECT = 3;
    static final int TYPE_ROUNDRECT = 4;
    static final int TYPE_LINE = 20;
    static final int TYPE_TEXT = 40;
    static final int FX_NONE = 1;
    static final int FX_FADE = 2;
    protected int drawFx = 2;
    int type = 1;
    String text = new String();
    float colorR;
    float colorG;
    float colorB;
    float cRate = -0.045f;
    Color color = null;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Particle(Vector vec, Sprite s, int x_tmp, int y_tmp, World w) {
        super(vec, s, x_tmp, y_tmp, w);
        if (this.sprite == null) {
            this.width = 2;
            this.height = 4;
            this.x = x_tmp;
            this.y = y_tmp;
            this.xa = 100.0;
            this.ya = 100.0;
            this.dxs = 0.0;
            this.dys = 0.0;
            this.health = 5;
            this.colorR = 1.0f;
            this.colorG = 0.1f;
            this.colorB = 0.2f;
            this.cRate = -0.045f;
        }
    }

    public void reset() {
        this.vector.remove(this);
    }

    private void isVisible() {
        if (this.x < (double)this.objWorld.getPlayableXSize() && this.x > 0.0) {
            this.visible = true;
        }
        if (this.x + (double)this.width < 0.0) {
            this.reset();
        }
    }

    public void doLogic() {
        this.move();
        if (--this.health <= 0 || (double)this.colorR <= 0.2 && (double)this.colorG <= 0.2 && (double)this.colorB <= 0.2) {
            this.reset();
        }
    }

    public void draw(Graphics g) {
        if (!this.visible) {
            return;
        }
        if (this.sprite != null) {
            this.sprite.draw(g, (int)this.x, (int)this.y);
            return;
        }
        switch (this.drawFx) {
            case 2: {
                if (this.colorR + this.cRate >= 0.0f && this.colorR + this.cRate <= 1.0f) {
                    this.colorR += this.cRate;
                }
                if (this.colorG + this.cRate >= 0.0f && this.colorG + this.cRate <= 1.0f) {
                    this.colorG += this.cRate;
                }
                if (this.colorB + this.cRate >= 0.0f && this.colorB + this.cRate <= 1.0f) {
                    this.colorB += this.cRate;
                }
                this.color = new Color(this.colorR, this.colorG, this.colorB);
                break;
            }
        }
        g.setColor(this.color);
        switch (this.type) {
            case 1: {
                g.fillRect((int)this.x, (int)this.y, this.width, this.height);
                break;
            }
            case 2: {
                g.drawRect((int)this.x, (int)this.y, this.width, this.height);
            }
            case 3: {
                g.fill3DRect((int)this.x, (int)this.y, this.width, this.height, true);
                break;
            }
            case 4: {
                g.drawRoundRect((int)this.x, (int)this.y, this.width, this.height, 10, 10);
                break;
            }
            case 20: {
                g.drawLine((int)this.x, (int)this.y, (int)(this.x + (double)this.width), (int)(this.y + (double)this.height));
                break;
            }
            case 40: {
                g.drawString(this.text, (int)this.x, (int)this.y);
                break;
            }
            default: {
                g.drawString("invalid drawing type", (int)this.x, (int)this.y);
            }
        }
    }

    public static void createHPText(Obj obj) {
        Particle ex = new Particle(obj.vector, null, (int)obj.x, (int)(obj.y + (double)(obj.height / 2) - 4.0 + Math.random() * 8.0), obj.objWorld);
        if (Math.random() > 0.5) {
            ex.setDxs(2.0 * Math.random());
        } else {
            ex.setDxs(2.0 * Math.random() * -1.0);
        }
        if (Math.random() > 0.5) {
            ex.setDys(2.0 * Math.random());
        } else {
            ex.setDys(2.0 * Math.random() * -1.0);
        }
        ex.setHealth(60);
        ex.setColorR(0.8f);
        ex.setColorG(1.0f);
        ex.setColorB(0.8f);
        ex.setCRate(-0.015f);
        ex.setType(40);
        ex.setText(String.valueOf(obj.health) + "+HP");
    }

    public static void createPTText(Obj obj) {
        Particle ex = new Particle(obj.vector, null, (int)obj.x, (int)(obj.y + (double)(obj.height / 2) - 4.0 + Math.random() * 8.0), obj.objWorld);
        if (Math.random() > 0.5) {
            ex.setDxs(2.0 * Math.random());
        } else {
            ex.setDxs(2.0 * Math.random() * -1.0);
        }
        if (Math.random() > 0.5) {
            ex.setDys(2.0 * Math.random());
        } else {
            ex.setDys(2.0 * Math.random() * -1.0);
        }
        ex.setHealth(60);
        ex.setColorR(1.0f);
        ex.setColorG(0.8f);
        ex.setColorB(0.8f);
        ex.setCRate(-0.015f);
        ex.setType(40);
        ex.setText(String.valueOf(obj.worth) + "+PT");
    }

    public static void createMissedText(Obj obj) {
        Particle ex = new Particle(obj.vector, null, (int)obj.x, (int)(obj.y + (double)(obj.height / 2) - 4.0 + Math.random() * 8.0), obj.objWorld);
        if (Math.random() > 0.5) {
            ex.setDxs(2.0 * Math.random());
        } else {
            ex.setDxs(2.0 * Math.random() * -1.0);
        }
        if (Math.random() > 0.5) {
            ex.setDys(2.0 * Math.random());
        } else {
            ex.setDys(2.0 * Math.random() * -1.0);
        }
        ex.setHealth(60);
        ex.setColorR(1.0f);
        ex.setColorG(1.0f);
        ex.setColorB(0.6f);
        ex.setCRate(-0.015f);
        ex.setType(40);
        ex.setText("Missed");
    }

    public void collideWith(Obj obj) {
    }

    protected float getColorB() {
        return this.colorB;
    }

    protected void setColorB(float colorB) {
        this.colorB = colorB;
    }

    protected float getColorG() {
        return this.colorG;
    }

    protected void setColorG(float colorG) {
        this.colorG = colorG;
    }

    protected float getColorR() {
        return this.colorR;
    }

    protected void setColorR(float colorR) {
        this.colorR = colorR;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCRate() {
        return this.cRate;
    }

    public void setCRate(float rate) {
        this.cRate = rate;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.colorR = this.color.getRed() / 255;
        this.colorG = this.color.getGreen() / 255;
        this.colorB = this.color.getBlue() / 255;
    }
}

