package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 21/04/2018.
 */
public class LevelSelectElement extends GUIElement {
    private Image img = null;
    private Level level;
    private String name;
    private String pathName;

    public LevelSelectElement(String name, Level level, int x, int y, int width, int height) throws SlickException {
        super(x, y, width, height);
        this.pathName = name;
        this.name = name.replace(".png","");
        this.level = level;
        try {
            Image i = new Image(level.getWidth() * Tile.TILE_SIZE, level.getHeight() * Tile.TILE_SIZE);
            Graphics g = i.getGraphics();
            level.render(g);
            g.flush();
            float ratio = (float) (((double) getWidth() / 2) / ((double) i.getWidth()));
            i = i.getScaledCopy((int) (i.getWidth() * ratio), (int) (i.getHeight() * ratio));
            this.img = i;
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(img, getWidth() / 2 - img.getWidth() / 2, getHeight() / 2 - img.getHeight() / 2);
        graphics.drawString(name,0,0);

    }

    @Override
    public void update(GameContainer container) {

    }

    public String getPathName() {
        return pathName;
    }

}
