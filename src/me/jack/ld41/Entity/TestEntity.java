package me.jack.ld41.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public class TestEntity extends PathFollower {
    private Random r = new Random();
    private Color c = null;

    public TestEntity(float x, float y) {
        super(x, y, 20);
        c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillRect(getX() + getxO(), getY() + getyO(), 16, 16);
        g.setColor(Color.white);
    }
}
