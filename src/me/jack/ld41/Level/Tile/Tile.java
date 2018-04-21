package me.jack.ld41.Level.Tile;

import me.jack.ld41.Level.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 21/04/2018.
 */
public class Tile {

    public static final int TILE_SIZE = 16;


    private int x, y;
    private Image image;
    private boolean solid;

    private static SpriteSheet sheet;

    public Tile(int x, int y, int tX, int tY, boolean solid) throws SlickException {
        if (sheet == null) {
            sheet = new SpriteSheet("res/tiles.png", TILE_SIZE,TILE_SIZE);
        }
        this.x = x;
        this.y = y;
        this.image = sheet.getSprite(tX, tY);
        this.solid = solid;
    }

    public void render(Graphics g) {
        g.drawImage(image, x * TILE_SIZE, y * TILE_SIZE);
    }

    public void update(Level parent){

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public boolean isSolid() {
        return solid;
    }
}
