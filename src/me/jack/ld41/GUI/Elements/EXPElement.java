package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 22/04/2018.
 */
public class EXPElement extends GUIElement {
    private int n;
    private float score, target;

    private Color c;
    public EXPElement(int x, int y, int width, int height,Color c) {
        super(x, y, width, height);
        this.c = c;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawString(n + "", getWidth()/2 - graphics.getFont().getWidth(n + "")/2, getHeight()/2 - graphics.getFont().getLineHeight()/2);
        graphics.setLineWidth(2f);
        graphics.drawArc(4, 4, getWidth() - 8, getHeight() - 8, 0, 360);
        graphics.setColor(c);
        graphics.drawArc(4, 4, getWidth() - 8, getHeight() - 8, 0, 360 * (score / target));
    }

    @Override
    public void update(GameContainer container) {

    }


    public void setNum(int n) {
        this.n = n;
    }

    public void setScore(float score, float target) {
        this.score = score;
        this.target = target;
    }
}
