package me.jack.ld41.GUI;

/**
 * Created by Jack on 21/04/2018.
 */
public interface GUIElementListener {

    void mouseDown(int x, int y, int button, GUIElement element);

    void mouseUp(int x, int y, GUIElement element);

    void mouseEnter(int x, int y, GUIElement element);

    void mouseLeave(int x, int y, GUIElement element);

   // void mouseDown(int x, int y, int button, GUIElement element);

}
