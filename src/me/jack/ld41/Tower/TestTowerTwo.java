package me.jack.ld41.Tower;

import me.jack.ld41.Level.Tile.Tile;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class TestTowerTwo extends Tower {

    public TestTowerTwo(int x, int y) {
        super(x, y, 1, 1, 3, 150, 5,15f);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(getX(), getY(), getWidth() * Tile.TILE_SIZE, getHeight() * Tile.TILE_SIZE);
        g.setColor(Color.white);
    }


    @Override
    public Tower copy() {
        return new TestTowerTwo(getX(), getY());
    }
}
