package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class TextButton extends GUIElement {

    private String text;

    public TextButton(String text, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.text = text;
    }

    private int tX = -1, tY = -1;

    @Override
    public void render(Graphics graphics) {
        if (tX == -1 && tY == -1) {
            tX = (getWidth() / 2) - (graphics.getFont().getWidth(text) / 2);
            tY = (getHeight() / 2) - (graphics.getFont().getLineHeight() / 2);
        }
        graphics.drawString(text, tX, tY);
    }

    @Override
    public void update(GameContainer container) {

    }

}
