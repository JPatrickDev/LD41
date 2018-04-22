package me.jack.ld41.State;

import me.jack.ld41.GUI.Elements.LevelSelectElement;
import me.jack.ld41.GUI.Elements.TextArea;
import me.jack.ld41.GUI.Elements.TextButton;
import me.jack.ld41.GUI.GUIArea;
import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.GUI.GUIElementListener;
import me.jack.ld41.Level.Level;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;

/**
 * Created by Jack on 21/04/2018.
 */
public class LevelSelectState extends BasicGameState {


    @Override
    public int getID() {
        return StateID.LEVEL_SELECT.getID();
    }

    private GUIArea area;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        area = new GUIArea(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        area.addElement(new TextArea("Select A Level:", 0, 10, gameContainer.getWidth(), 30, Color.black, Color.white));


        GUIElementListener listener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                if(element instanceof LevelSelectElement){
                    String path = ((LevelSelectElement) element).getPathName();
                    InGameState.path = "res/levels/" + path;
                    stateBasedGame.enterState(StateID.IN_GAME.getID());
                }
                if(element instanceof TextButton){
                    stateBasedGame.enterState(StateID.MAIN_MENU.getID());
                }
            }

            @Override
            public void mouseEnter(int x, int y, GUIElement element) {

            }

            @Override
            public void mouseLeave(int x, int y, GUIElement element) {

            }
        };

        String[] levels = new File("res/levels/").list();
        int width = (int) ((double)gameContainer.getWidth() / 2);
        int x = 0;
        int y = 0;
        int i = 0;
        for(String l : levels){
            area.addElement(new LevelSelectElement(l,Level.fromImage(new Image("res/levels/" + l)),x,y,width,width).setListener(listener));
            x+=width;
            i++;
            if(i >= 2){
                x = 0;
                y += width;
                i = 0;
            }
        }
        TextButton back = new TextButton("Back To Main Menu",0,area.getHeight() - 30,area.getWidth(),30,Color.orange,Color.black);
        back.setListener(listener);
        area.addElement(back);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
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
