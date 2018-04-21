package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class TextArea extends GUIElement {

    private String text;

    private Color backgroundColor, textColor;

    public TextArea(String text, int x, int y, int width, int height) {
        this(text, x, y, width, height, null, Color.black);
    }

    public TextArea(String text, int x, int y, int width, int height, Color backgroundColor, Color textColor) {
        super(x, y, width, height);
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    private int tX = -1, tY = -1;

    @Override
    public void render(Graphics graphics) {
        if (tX == -1 && tY == -1) {
            tX = (getWidth() / 2) - (graphics.getFont().getWidth(text) / 2);
            tY = (getHeight() / 2) - (graphics.getFont().getLineHeight() / 2);
        }
        if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
            graphics.fillRect(0, 0, getWidth(), getHeight());
        }
        graphics.setColor(textColor);
        graphics.drawString(text, tX, tY);
        graphics.setColor(Color.white);
    }

    @Override
    public void update(GameContainer container) {

    }

    public void setText(String newText) {
        this.text = newText;
        this.tX = -1;
        this.tY = -1;
    }

    public String getText() {
        return text;
    }

}
