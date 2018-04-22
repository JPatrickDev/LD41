package me.jack.ld41.Tower;

import me.jack.ld41.Entity.PathFollower;
import me.jack.ld41.Entity.Projectile.EntityProjectile;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Weapon.Common.WeaponGroup;
import me.jack.ld41.Weapon.Weapons.Weapon;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import java.awt.*;
import java.lang.reflect.Array;
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

    private ArrayList<WeaponGroup> groups = new ArrayList<>();

    public static SpriteSheet sheet;

    public Tower(int x, int y, int width, int height, TowerUpgrades upgrades, int unlockedAt, float cost, int fireRateLevel, int shotsPerTurnLevel, int rangeLevel, int dmgLevel) {
        if (sheet == null) {
            try {
                sheet = new SpriteSheet("res/towers.png", 32, 32);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
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
        try {
            setUpWeapons();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public abstract void setUpWeapons() throws SlickException;


    public void resetWeapons() throws SlickException {
        this.groups.clear();
        setUpWeapons();
    }
    public void render(Graphics g, boolean drawRange) {
        //System.out.println("render called");
        for (WeaponGroup group : this.groups) {
            //System.out.println("Drawing group");
            //System.out.println(group.getX() + ":" + group.getY());
            g.translate(group.getX(), group.getY());
            group.render(g);
            g.translate(-group.getX(), -group.getY());
        }

        if (drawRange) {
            g.setColor(new Color(255, 100, 100, 100));
            Circle c = new Circle(Tile.TILE_SIZE / 2, Tile.TILE_SIZE / 2, upgrades.getRange());
            g.fill(c);
            g.setColor(Color.white);
        }
    }

    Random r = new Random();

    int lastShotTime = 0;

    public ArrayList<EntityProjectile> update(Level level, int delta, Point mousePos) {
        lastShotTime += delta;
        if (takingTurn) {
            System.out.println("Taking turn");
            if (shotsTaken < upgrades.getShotsPerTurn()) {
                System.out.println("Rand?");
                if (r.nextInt(5) == 0 && lastShotTime >= this.upgrades.getFireSpeed()) {
                    System.out.println("Firing");
                    System.out.println("Shot Fired");
                    shotsTaken++;
                    ArrayList<Weapon> all = new ArrayList<>();
                    for (WeaponGroup group : this.groups)
                        all.addAll(group.getAllWeapons());
                    System.out.println("Found" + all.size() + " weapons.");
                    for (Weapon w : all) {
                        ArrayList<PathFollower> targets = level.getTargets(getX() + w.getX(), getY() + w.getY(), upgrades.getRange());
                        if (targets.size() != 0) {
                            try {
                                PathFollower t = targets.get(r.nextInt(targets.size()));
                                w.fire(getX(), getY(), level, (int) t.getX(), (int) t.getY());
                            } catch (SlickException e) {
                                e.printStackTrace();
                            }
                        }
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

    public void addWeaponGroup(WeaponGroup group) {
        this.groups.add(group);
    }

    public void setDmgLevel(int dmgLevel) {
        this.dmgLevel = dmgLevel;
    }
}
