package me.jack.ld41.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public class EntityOne extends PathFollower {

    private Image img;
    public EntityOne(float x, float y) {
        super(x, y, 10);
        img = sprites.getSprite(0,0);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img,(getX() + getxO()) + 8, getY() + getyO() + 8);
        super.render(g);
    }
}
