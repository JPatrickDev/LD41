package me.jack.ld41.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 21/04/2018.
 */
public class GUIArea {

    private int x, y, width, height;

    private ArrayList<GUIElement> elements = new ArrayList<>();

    public GUIArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void addElement(GUIElement element) {
        elements.add(element);
    }

    public void render(Graphics g) {
        g.translate(x, y);
        for (GUIElement element : elements) {
            g.translate(element.getX(), element.getY());
            element.render(g);
            g.translate(-element.getX(), -element.getY());
        }
        g.translate(-x, -y);
    }

    public void update(GameContainer container) {

    }

    public void mouseDown(int x, int y, int button) {
        for (GUIElement g : elements) {
            if (new Rectangle(g.getX(), g.getY(), g.getWidth(), g.getHeight()).contains(x, y))
            g.mouseDown(x, y, button);
        }
    }

    public void mouseUp(int x, int y) {
        for (GUIElement g : elements) {
            if (new Rectangle(g.getX(), g.getY(), g.getWidth(), g.getHeight()).contains(x, y))
                g.mouseUp(x, y);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<GUIElement> getElements() {
        return elements;
    }
}
