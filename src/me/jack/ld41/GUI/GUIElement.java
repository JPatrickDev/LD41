package me.jack.ld41.GUI;

import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class GUIElement {

    private int x;
    int y;
    int width;
    int height;
    private GUIElementListener listener;

    public GUIElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GUIElement setListener(GUIElementListener listener) {
        this.listener = listener;
        return this;
    }

    public abstract void render(Graphics graphics);

    public abstract void update(GameContainer container);

    public void mouseDown(int x, int y, int button) {
        if (listener != null) {
            listener.mouseDown(x, y, button, this);
        }
    }

    public void mouseUp(int x, int y) {
        if (listener != null) {
            listener.mouseUp(x, y, this);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



}
