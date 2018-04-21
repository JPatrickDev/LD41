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

    private final TowerUpgrades upgrades;
    private int x, y, width, height;


    private int shotsTaken = 0;
    private boolean turnOver = false;
    private boolean takingTurn = false;
    private int unlockedAt;
    private float cost;

    private int fireRateLevel = 0;
    private int shotsPerTurnLevel = 0;
    private int rangeLevel = 0;
    private int dmgLevel = 0;


    public Tower(int x, int y, int width, int height, TowerUpgrades upgrades, int unlockedAt, float cost, int fireRateLevel, int shotsPerTurnLevel, int rangeLevel, int dmgLevel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.unlockedAt = unlockedAt;
        this.cost = cost;
        this.upgrades = upgrades;
        this.fireRateLevel = fireRateLevel;
        this.shotsPerTurnLevel = shotsPerTurnLevel;
        this.rangeLevel = rangeLevel;
        this.dmgLevel = dmgLevel;
    }

    public abstract void render(Graphics g);

    Random r = new Random();

    int lastShotTime = 0;

    public Projectile update(Level level, int delta) {
        lastShotTime += delta;
        if (takingTurn) {
            if (shotsTaken < upgrades.getShotsPerTurn()) {
                if (r.nextInt(10) == 0 && lastShotTime >= this.upgrades.getFireSpeed()) {
                    System.out.println("Shot Fired");
                    shotsTaken++;
                    lastShotTime = 0;
                    ArrayList<PathFollower> valid = level.getTargets(getX(), getY(), this.upgrades.getRange());
                    if (valid.size() == 0) {
                        shotsTaken = upgrades.getShotsPerTurn();
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

    public int getFireRateLevel() {
        return fireRateLevel;
    }

    public int getShotsPerTurnLevel() {
        return shotsPerTurnLevel;
    }

    public int getRangeLevel() {
        return rangeLevel;
    }

    public int getDmgLevel() {
        return dmgLevel;
    }

    public TowerUpgrades getUpgrades() {
        return upgrades;
    }

    public void setFireRateLevel(int fireRateLevel) {
        this.fireRateLevel = fireRateLevel;
    }

    public void setShotsPerTurnLevel(int shotsPerTurnLevel) {
        this.shotsPerTurnLevel = shotsPerTurnLevel;
    }

    public void setRangeLevel(int rangeLevel) {
        this.rangeLevel = rangeLevel;
    }
}
