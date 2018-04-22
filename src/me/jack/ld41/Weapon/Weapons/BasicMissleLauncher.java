package me.jack.ld41.Weapon.Weapons;

import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Weapon.Projectiles.BasicSmallBullet;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 22/04/2018.
 */
public class BasicMissleLauncher extends Weapon {

    private float rotation;

    public BasicMissleLauncher(int x, int y) throws SlickException {
        super("BXDA-1", WeaponType.GUN, 0, 1, x, y);
        image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);
    }

    float i = 0;
    int lookAtx, lookAtY;
    float targetRot = 0;

    @Override
    public void fire(int aX, int aY, Level level, int tX, int tY) throws SlickException {
        tX += Tile.TILE_SIZE / 2;
        tY += Tile.TILE_SIZE / 2;
        System.out.println("Firing " + name);
        int rX = aX + image.getWidth() / 2;
        int rY = aY + image.getHeight() / 2;
        level.addProjectile(new BasicSmallBullet(), rX - 16, rY, tX, tY);
        level.addProjectile(new BasicSmallBullet(), rX + 16, rY, tX, tY);
        lookAt(rX, rY, tX, tY);
    }

    @Override
    public void render(Graphics g) {
        //  rotation = 90 + i;
        // i++;
        // g.rotate(this.image.getWidth() / 2, this.image.getHeight() / 2, rotation);
        image.setRotation(rotation);
        super.render(g);
        // g.rotate(this.image.getWidth() / 2, this.image.getHeight() / 2, -rotation);
    }

    public void lookAt(int rX, int rY, int tx, int tY) {
        System.out.println("Looking at" + tx + ":" + tY + "(" + rX + ":" + rY + ")");
        this.lookAtx = tx;
        this.lookAtY = tY;
        rotation = (float) -(Math.atan2(rX - tx, rY - tY) * 180 / Math.PI);
        System.out.println(rotation);
    }
}
