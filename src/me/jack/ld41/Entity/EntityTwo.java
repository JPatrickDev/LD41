package me.jack.ld41.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public class EntityTwo extends PathFollower {

    private Image img;
    public EntityTwo(float x, float y) {
        super(x, y, 50);
        img = sprites.getSprite(1,0);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img,(getX() + getxO()) + 8, getY() + getyO() + 8);
    }
}
