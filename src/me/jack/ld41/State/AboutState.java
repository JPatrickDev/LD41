package me.jack.ld41.State;

import me.jack.ld41.GUI.Elements.TextButton;
import me.jack.ld41.GUI.GUIArea;
import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.GUI.GUIElementListener;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 23/04/2018.
 */
public class AboutState extends BasicGameState {
    private Image bg;

    @Override
    public int getID() {
        return StateID.ABOUT.getID();
    }

    Image about = null;
    GUIArea area = null;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        area = new GUIArea(14, gameContainer.getHeight() - 30 - 14, 462, 30);
        area.addElement(new TextButton("Back To Main Menu", 0, 0, area.getWidth(), area.getHeight(), Color.orange, Color.black).setListener(new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                stateBasedGame.enterState(StateID.MAIN_MENU.getID());
            }

            @Override
            public void mouseEnter(int x, int y, GUIElement element) {

            }

            @Override
            public void mouseLeave(int x, int y, GUIElement element) {

            }
        }));
        about = new Image("res/about.png");
        bg = new Image("res/mainMenuBG.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(bg, 0, 0);
        graphics.drawImage(about, 0, 0);
        area.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        area.mouseDown(x - area.getX(), y - area.getY(), button);
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        super.mouseReleased(button, x, y);
        area.mouseUp(x - area.getX(), y - area.getY());
    }
}
