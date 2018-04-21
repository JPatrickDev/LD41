package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 21/04/2018.
 */
public class TextButton extends TextArea {

    public TextButton(String text, int x, int y, int width, int height,Color background, Color textC) {
        super(text,x, y, width, height,background,textC);
    }

}
