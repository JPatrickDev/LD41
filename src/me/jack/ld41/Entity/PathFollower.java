package me.jack.ld41.Entity;

import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class PathFollower extends Entity {

    private int pathPos = 0;
    private boolean isMoving;
    private Point currentTarget = null;
    private float health = 0;
    private float maxHealth = 0;
    private boolean wasKilled = false;



    public static SpriteSheet sprites = null;
    public static org.newdawn.slick.Image hit = null;
    boolean flashing = false;
    public PathFollower(float x, float y, float maxHealth) {
        super(x, y);
        if(sprites == null)
            try {
                sprites = new SpriteSheet("res/enemies.png",16,16);
                hit = new org.newdawn.slick.Image("res/hit.png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void nextStep(Level level) {
        pathPos++;
        isMoving = true;
        currentTarget = level.getPathPoint(pathPos);
        if (currentTarget == null) {
            isMoving = false;
            dead = true;
            level.removeLife();
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    @Override
    public void update(Level level) {
        if (isMoving) {
            if (currentTarget.x * Tile.TILE_SIZE > getX() && getxO() < Tile.TILE_SIZE) {
                setxO(getxO() + moveSpeed);
            }
            if (currentTarget.x * Tile.TILE_SIZE < getX() && Math.abs(getxO()) < Tile.TILE_SIZE) {
                setxO(getxO() - moveSpeed);
            }

            if (currentTarget.y * Tile.TILE_SIZE > getY() && getyO() < Tile.TILE_SIZE) {
                setyO(getyO() + moveSpeed);
            }
            if (currentTarget.y * Tile.TILE_SIZE < getY() && Math.abs(getyO()) < Tile.TILE_SIZE) {
                setyO(getyO() - moveSpeed);
            }

            if (Math.abs(getxO()) >= Tile.TILE_SIZE || Math.abs(getyO()) >= Tile.TILE_SIZE) {
                setxO(0);
                setyO(0);
                setX(currentTarget.x * Tile.TILE_SIZE);
                setY(currentTarget.y * Tile.TILE_SIZE);
                //System.out.println("Done!");
                isMoving = false;
            }
        }
    }


    int i = 0;
    @Override
    public void render(Graphics g) {
        if(flashing) {
            g.drawImage(hit, (getX() + getxO()) + 8, getY() + getyO() + 8);
            i++;
            if(i > 10){
                i = 0;
                flashing = false;
            }
        }
    }

    public Rectangle getCurrentHitbox() {
        return new Rectangle((int) (getX() + getxO()) + 8, (int) (getY() + getyO()) + 8, Tile.TILE_SIZE/2, Tile.TILE_SIZE/2);
    }

    public void damage(float damage) {
        health -= damage;
        flashing = true;
        if (health <= 0) {
            dead = true;
            wasKilled = true;
        }
    }

    public boolean wasKilled(){
        return wasKilled;
    }
}
