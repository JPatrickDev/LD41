package me.jack.ld41.Entity.Projectile;

import me.jack.ld41.Entity.Entity;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class Projectile extends Entity {
    private float moveSpeed, damage;
    private float xVel, yVel;
    private boolean dead = false;
    Random r = new Random();
    private float age = 0;

    public Projectile(float x, float y, float moveSpeed, float damage, Point target) {
        super(x, y);
        float xSpeed = target.x - x;
        float ySpeed = target.y - y;
        float factor = (float) (moveSpeed / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
        xVel = xSpeed * factor;
        yVel = ySpeed * factor;
    }

    @Override
    public void update(Level level) {
        age++;
        if(age >= 100 + r.nextInt(50))
            dead = true;
        setX(getX() + xVel);
        setY(getY() + yVel);
        if(getX() < 0 || getY() < 0 || getX() > level.getWidth() * Tile.TILE_SIZE || getY() > level.getHeight() * Tile.TILE_SIZE){
            dead = true;
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }
}
