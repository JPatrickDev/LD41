package me.jack.ld41.Entity.Projectile;

import me.jack.ld41.Entity.Entity;
import me.jack.ld41.Entity.PathFollower;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Weapon.Projectiles.Projectile;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public class EntityProjectile extends Entity {
    private float moveSpeed = 5f, damage;
    private float xVel, yVel;
    private boolean dead = false;
    Random r = new Random();
    private float age = 0;
    Projectile projectile;

    public EntityProjectile(Projectile projectile, int x, int y, int tX, int tY) {
        super(x, y);
        this.projectile = projectile;
        this.damage = projectile.getDamage();
        float xSpeed = tX - x;
        float ySpeed = tY - y;
        float factor = (float) (moveSpeed / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
        xVel = xSpeed * factor;
        yVel = ySpeed * factor;
    }

    @Override
    public void update(Level level) {
        age++;
        if (age >= 50 + r.nextInt(50))
            dead = true;
        setX(getX() + xVel);
        setY(getY() + yVel);
        if (getX() < 0 || getY() < 0 || getX() > level.getWidth() * Tile.TILE_SIZE || getY() > level.getHeight() * Tile.TILE_SIZE) {
            dead = true;
        }

        Rectangle me = new Rectangle((int) getX(), (int) getY(), 4, 4);
        for (PathFollower f : level.getPathFollowers()) {
            if (f.getCurrentHitbox().intersects(me)) {
                dead = true;
                f.damage(damage);
            }
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void render(Graphics g) {
        System.out.println("Drawing");
        g.translate(getX(), getY());
        System.out.println(getX() +":" + getY());
        projectile.render(g);
        g.translate(-getX(), -getY());
    }
}
