package me.jack.ld41.Entity;

import me.jack.ld41.Level.Level;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class Entity{

    private float x,y;
    private float xO,yO;
    protected float moveSpeed = 0.5f;
    protected boolean dead;
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getxO() {
        return xO;
    }

    public float getyO() {
        return yO;
    }

    public void setxO(float xo) {
        this.xO = xo;
    }

    public void setyO(float yO) {
        this.yO = yO;
    }

    public boolean isDead() {
        return dead;
    }

    public abstract void render(Graphics g);
    public abstract void update(Level level);

}
