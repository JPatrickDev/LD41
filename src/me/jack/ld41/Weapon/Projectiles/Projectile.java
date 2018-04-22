package me.jack.ld41.Weapon.Projectiles;

import me.jack.ld41.Weapon.Weapons.WeaponType;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 22/04/2018.
 */
public abstract class Projectile{
    private ProjectileType type;
    private Image image;

    private String name;

    public static SpriteSheet sheet = null;
    private float damage
            ;

    public Projectile(String name,ProjectileType type, int x, int y,int dmg) throws SlickException {
        if (sheet == null) {
            sheet = new SpriteSheet("res/projectiles.png", 8, 8);
        }
        this.name = name;
        this.type = type;
        this.image = sheet.getSprite(x, y);
        this.damage = dmg;
    }

    public void render(Graphics g) {
        g.drawImage(image, 0, 0);
    }

    public float getDamage() {
        return damage;
    }
}
