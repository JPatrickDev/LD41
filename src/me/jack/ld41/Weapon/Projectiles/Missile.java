package me.jack.ld41.Weapon.Projectiles;

import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 22/04/2018.
 */
public class Missile extends Projectile {
    public Missile() throws SlickException {
        super("FFBM-1", ProjectileType.MISSILE, 0, 1,100);
    }
}
