package me.jack.ld41.Tower;

import me.jack.ld41.Level.Level;
import org.newdawn.slick.Graphics;

import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class Tower {

    private int x, y, width, height;

    private int shotsPerTurn;
    private int shotsTaken = 0;
    private boolean turnOver = false;
    private boolean takingTurn = false;

    public Tower(int x, int y, int width, int height, int shotsPerTurn) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.shotsPerTurn = shotsPerTurn;
    }

    public abstract void render(Graphics g);

    Random r = new Random();

    public void update(Level level) {
        if (takingTurn) {
            if (r.nextInt(5) == 0) {
                if (shotsTaken < shotsPerTurn) {
                    System.out.println("Shot Fired");
                    shotsTaken++;
                } else {
                    turnOver = true;
                    takingTurn = false;
                }
            }
        }
    }

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

    public void newTurn(Level level) {
        this.shotsTaken = 0;
        turnOver = false;
        takingTurn = true;
    }

    public boolean isTurnOver() {
        return turnOver;
    }
}
