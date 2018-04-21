package me.jack.ld41.Entity.Projectile;

import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by Jack on 21/04/2018.
 */
public class TestProjectile extends Projectile {

    public TestProjectile(float x, float y, Point target) {
        super(x, y, 5f, 1f, target);
    }

    @Override
    public void render(Graphics g) {
        g.fillRect(getX(), getY(), 4, 4);
    }
}
