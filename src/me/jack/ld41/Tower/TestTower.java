package me.jack.ld41.Tower;

import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class TestTower extends Tower {

    public TestTower(int x, int y) {
        super(x, y, 1, 1, 3);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(getX(), getY(), getWidth() * Tile.TILE_SIZE, getHeight() * Tile.TILE_SIZE);
        g.setColor(Color.white);
    }


    @Override
    public Tower copy() {
        return new TestTower(getX(), getY());
    }
}
