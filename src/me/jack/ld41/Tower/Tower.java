package me.jack.ld41.Tower;

import me.jack.ld41.Entity.PathFollower;
import me.jack.ld41.Entity.Projectile.Projectile;
import me.jack.ld41.Entity.Projectile.TestProjectile;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class Tower {

    private int x, y, width, height;
    private float range;

    private int shotsPerTurn;
    private int shotsTaken = 0;
    private boolean turnOver = false;
    private boolean takingTurn = false;
    private int unlockedAt;
    private float cost;


    public Tower(int x, int y, int width, int height, int shotsPerTurn, float range, int unlockedAt, float cost) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.shotsPerTurn = shotsPerTurn;
        this.range = range;
        this.unlockedAt = unlockedAt;
        this.cost = cost;
    }

    public abstract void render(Graphics g);

    Random r = new Random();

    int lastShotTime = 0;

    public Projectile update(Level level, int delta) {
        lastShotTime += delta;
        if (takingTurn) {
            if (shotsTaken < shotsPerTurn) {
                if (r.nextInt(10) == 0 && lastShotTime >= 50) {
                    System.out.println("Shot Fired");
                    shotsTaken++;
                    lastShotTime = 0;
                    ArrayList<PathFollower> valid = level.getTargets(getX(), getY(), this.range);
                    if (valid.size() == 0) {
                        lastShotTime = 0;
                        shotsTaken++;
                        return update(level, delta);
                    } else {
                        PathFollower target = valid.get(r.nextInt(valid.size()));
                        return new TestProjectile(getX(), getY(), new Point((int) target.getX() + Tile.TILE_SIZE / 2, (int) target.getY() + Tile.TILE_SIZE / 2));
                    }
                }
            } else {
                turnOver = true;
                takingTurn = false;
            }

        }
        return null;
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

    public int getLevel() {
        return unlockedAt;
    }

    public float getCost() {
        return cost;
    }
}
