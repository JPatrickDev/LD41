package me.jack.ld41.Tower;

import me.jack.ld41.Level.Tile.Tile;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class TestTowerTwo extends Tower {

    public TestTowerTwo(int x, int y, int fireRateLevel, int shotsPerTurnLevel, int rangeLevel, int dmgLevel) {
        super(x, y, 1, 1, new TowerUpgrades(50, 50, 3, 3, 40, 40, 1, 1), 5, 15f, fireRateLevel, shotsPerTurnLevel, rangeLevel, dmgLevel);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(getX(), getY(), getWidth() * Tile.TILE_SIZE, getHeight() * Tile.TILE_SIZE);
        g.setColor(Color.white);
    }


    @Override
    public Tower copy() {
        return new TestTowerTwo(getX(), getY(),getFireRateLevel(),getShotsPerTurnLevel(),getRangeLevel(),getDmgLevel());
    }
}
