package me.jack.ld41.Weapon.Projectiles;

import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 22/04/2018.
 */
public class BasicSmallBullet extends Projectile {
    public BasicSmallBullet() throws SlickException {
        super("P-T36", ProjectileType.SMALL_BULLET, 0, 0,5);
    }
}
