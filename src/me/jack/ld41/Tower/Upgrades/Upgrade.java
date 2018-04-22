package me.jack.ld41.Tower.Upgrades;

import me.jack.ld41.State.InGameState;
import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class Upgrade {

    private static SpriteSheet upgradeIcons = null;


    private String name, description;
    private Image icon;
    private float unlockMultiplier;
    private int level;
    private float cost;
    protected boolean validUpgrade = true;

    public Upgrade(String name, String description, float unlockMultiplier, int level, int yPos,float baseCost) throws SlickException {
        if (upgradeIcons == null) {
            upgradeIcons = new SpriteSheet("res/upgrades.png", 32, 32);
        }
        this.icon = upgradeIcons.getSprite(level, yPos);
        this.name = name;
        this.description = description;
        this.unlockMultiplier = unlockMultiplier;
        this.level = level;
        this.cost = baseCost;
    }

    public abstract Upgrade getNextForTower(Tower t);


    public void render(Graphics g) {
        g.drawImage(icon, 0, 0);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getIcon() {
        return icon;
    }

    public float getUnlockMultiplier() {
        return unlockMultiplier;
    }

    public abstract void use(Tower currentlySelected, InGameState parent);

    public float getCost(){
        return this.cost * (level + 1);
    }

    public boolean isValidUpgrade() {
        return validUpgrade;
    }
}
