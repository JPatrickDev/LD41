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
    public boolean isUnlocked = true;

    public UpgradeElement(Upgrade upgrade, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.upgrade = upgrade;
    }

    @Override
    public void render(Graphics graphics) {
        if(!upgrade.isValidUpgrade())
            return;
        graphics.setColor(Color.black);
        graphics.drawRect(0, 0, getWidth(), getHeight());
        graphics.translate(getWidth() / 2 - 16, getHeight() / 2 - 16);
        if (upgrade != null) {
            upgrade.render(graphics);
        }
        graphics.translate(-1 * (getWidth() / 2 - 16), -1 * (getHeight() / 2 - 16));
        graphics.setColor(Color.white);
        if (!isUnlocked) {
            graphics.setColor(new Color(255, 0, 0, 180));
            graphics.fillRect(0, 0, getWidth(), getHeight());
        }
        if (upgrade != null) {
            graphics.setColor(Color.white);
            graphics.drawString(upgrade.getCost() + "", getWidth()/2 - graphics.getFont().getWidth(upgrade.getCost() + "")/2, getHeight() - graphics.getFont().getLineHeight() + 5);
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
