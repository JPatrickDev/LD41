package me.jack.ld41.Weapon.Weapons;

import me.jack.ld41.Level.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 22/04/2018.
 */
public abstract class Weapon {

    private WeaponType type;
    public Image image;

    public String name;

    public static SpriteSheet sheet = null;

    private int xPos, yPos;
    private float x;
    private float y;

    public Weapon(String name, WeaponType type, int x, int y, int xPos, int yPos) throws SlickException {
        if (sheet == null) {
            sheet = new SpriteSheet("res/weapons.png", 32, 32);
        }
        this.name = name;
        this.type = type;
        this.image = sheet.getSprite(x, y);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public abstract void fire(int aX, int aY, Level level,int tX,int tY) throws SlickException;

    public void render(Graphics g) {
        g.drawImage(image, 0, 0);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
