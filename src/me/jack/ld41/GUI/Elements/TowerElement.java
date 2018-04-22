package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.*;

/**
 * Created by Jack on 21/04/2018.
 */
public class TowerElement extends GUIElement {
    private Tower tower;
    public boolean isUnlocked = false;
    private static Image padlock;
    public TowerElement(Tower tower, int x, int y, int width, int height) {
        super(x, y, width, height);
        if(padlock == null)
            try {
                padlock = new Image("res/padlock.png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        this.tower = tower;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.translate(getWidth() / 2 - (tower.getWidth() * Tile.TILE_SIZE) / 2, getHeight() / 2 - (tower.getHeight() * Tile.TILE_SIZE) / 2);
        tower.render(graphics,false);
        graphics.translate(-1 * (getWidth() / 2 - (tower.getWidth() * Tile.TILE_SIZE) / 2), -1 * (getHeight() / 2 - (tower.getHeight() * Tile.TILE_SIZE) / 2));
        graphics.setColor(Color.white);
        //TODO: Red text if player can't afford
        graphics.drawString(tower.getCost() + "",0,getHeight());
        if(!isUnlocked){
          //  graphics.setColor(new Color(255,0,0,180));
           // graphics.fillRect(0,0,getWidth(),getHeight());
            graphics.drawImage(padlock,getWidth()/2 - padlock.getWidth()/2,getHeight()/2 - padlock.getHeight()/2);
        }
    }

    @Override
    public void update(GameContainer container) {

    }

    public Tower getTower() {
        return tower;
    }
}
