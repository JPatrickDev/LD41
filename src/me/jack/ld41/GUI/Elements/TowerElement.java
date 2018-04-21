package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class TowerElement extends GUIElement {
    private Tower tower;

    public TowerElement(Tower tower, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.tower = tower;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.translate(getWidth() / 2 - (tower.getWidth() * Tile.TILE_SIZE) / 2, getHeight() / 2 - (tower.getHeight() * Tile.TILE_SIZE) / 2);
        tower.render(graphics);
        graphics.translate(-1 * (getWidth() / 2 - (tower.getWidth() * Tile.TILE_SIZE) / 2), -1 * (getHeight() / 2 - (tower.getHeight() * Tile.TILE_SIZE) / 2));
        graphics.setColor(Color.white);
    }

    @Override
    public void update(GameContainer container) {

    }

    public Tower getTower() {
        return tower;
    }
}
