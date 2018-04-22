package me.jack.ld41.Weapon.Projectiles;

import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 22/04/2018.
 */
public class AdvancedSmallBullet extends Projectile {
    public AdvancedSmallBullet() throws SlickException {
        super("P-T36A0", ProjectileType.SMALL_BULLET, 1, 0,10);
    }
}
