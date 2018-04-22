package me.jack.ld41.State;

import me.jack.ld41.GUI.Elements.TextArea;
import me.jack.ld41.GUI.Elements.TextButton;
import me.jack.ld41.GUI.GUIArea;
import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.GUI.GUIElementListener;
import me.jack.ld41.Level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 22/04/2018.
 */
public class MainMenuState extends BasicGameState {


    @Override
    public int getID() {
        return StateID.MAIN_MENU.getID();
    }

    private GUIArea area;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);

        area = new GUIArea(container.getWidth()/2 - 150, 100, 300, container.getHeight() - 200);
        GUIElementListener listener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                if (element instanceof TextButton) {
                    switch (((TextButton) element).getText()) {
                        case "Quit":
                            System.exit(0);
                            break;
                        case "Play":
                            game.enterState(StateID.LEVEL_SELECT.getID());
                            break;
                    }
                }
            }

            @Override
            public void mouseEnter(int x, int y, GUIElement element) {

            }

            @Override
            public void mouseLeave(int x, int y, GUIElement element) {

            }
        };
        TextButton backToLevelSelect = new TextButton("Play", 0, 100, area.getWidth(), 40, Color.orange, Color.black);
        backToLevelSelect.setListener(listener);
        TextButton backToMain = new TextButton("Settings", 0, 150, area.getWidth(), 40, Color.orange, Color.black);
        backToMain.setListener(listener);
        TextButton quit = new TextButton("About", 0, 200, area.getWidth(), 40, Color.orange, Color.black);
        quit.setListener(listener);
        TextButton exit = new TextButton("Quit", 0, 250, area.getWidth(), 40, Color.orange, Color.black);
        exit.setListener(listener);

        area.addElement(backToLevelSelect);
        area.addElement(backToMain);
        area.addElement(quit);
        area.addElement(exit);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (area != null)
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
