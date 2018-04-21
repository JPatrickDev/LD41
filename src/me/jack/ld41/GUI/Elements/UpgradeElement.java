package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Tower.Tower;
import me.jack.ld41.Tower.Upgrades.Upgrade;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class UpgradeElement extends GUIElement {
    private Upgrade upgrade;
    public boolean isUnlocked = false;

    public UpgradeElement(Upgrade upgrade, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.upgrade = upgrade;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.translate(getWidth() / 2 - (Tile.TILE_SIZE * 2) / 2, getHeight() / 2 - (Tile.TILE_SIZE * 2) / 2);
        if (upgrade != null) {
            upgrade.render(graphics);
        }
        graphics.translate(-1 * (getWidth() / 2 - (Tile.TILE_SIZE * 2) / 2), -1 * (getHeight() / 2 - (Tile.TILE_SIZE * 2) / 2));
        graphics.setColor(Color.white);
        if (!isUnlocked) {
            graphics.setColor(new Color(255, 0, 0, 180));
            graphics.fillRect(0, 0, getWidth(), getHeight());
        }
        if (upgrade != null) {
            graphics.setColor(Color.white);
            graphics.drawString(upgrade.getCost() + "", 0, 0);
        }
    }
    @Override
    public void update(GameContainer container) {

    }

    public void setUpgrade(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }
}
