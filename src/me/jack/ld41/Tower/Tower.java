package me.jack.ld41.Tower;

import me.jack.ld41.Level.Level;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class Tower {

    private int x,y,width,height;

    public Tower(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(Graphics g);
    public abstract void update(Level level);
    public abstract Tower copy();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
