package me.jack.ld41.Weapon.Weapons;

import me.jack.ld41.Level.Level;
import me.jack.ld41.Weapon.Projectiles.BasicSmallBullet;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 22/04/2018.
 */
public class BasicTurret extends Weapon {

    private float rotation;

    public BasicTurret(int x, int y) throws SlickException {
        super("Z3I5-1", WeaponType.GUN, 0, 0, x, y);
    }

    float i = 0;
    int lookAtx, lookAtY;
    float targetRot = 0;

    @Override
    public void fire(int aX, int aY,Level level,int tX,int tY) throws SlickException {
        System.out.println("Firing " + name);
        int rX = aX + image.getWidth() / 2;
        int rY = aY + image.getHeight() / 2;
        level.addProjectile(new BasicSmallBullet(),rX,rY,tX,tY);
        lookAt(tX,tY);
    }

    @Override
    public void render(Graphics g) {
        rotation = 90 + i;
        i++;
        g.rotate(this.image.getWidth() / 2, this.image.getHeight() / 2, rotation);

        super.render(g);
       g.rotate(this.image.getWidth() / 2, this.image.getHeight() / 2, -rotation);
    }

    public void lookAt(int tx, int tY) {
        System.out.println("Looking at" + tx + ":" + tY);
        this.lookAtx = tx;
        this.lookAtY = tY;
        rotation = (float) Math.toDegrees(Math.atan2(getY() - tY, getX() - tx));
        System.out.println(rotation);
    }
}
