package me.jack.ld41.Entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 21/04/2018.
 */
public class EntityFour extends PathFollower {

    private Image img;
    public EntityFour(float x, float y) {
        super(x, y, 150);
        img = sprites.getSprite(1,1);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img,(getX() + getxO()) + 8, getY() + getyO() + 8);
    }
}
