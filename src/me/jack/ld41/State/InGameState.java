package me.jack.ld41.State;

import com.sun.org.apache.regexp.internal.RE;
import me.jack.ld41.GUI.Elements.TextButton;
import me.jack.ld41.GUI.Elements.TowerElement;
import me.jack.ld41.GUI.GUIArea;
import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.GUI.GUIElementListener;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.DirtTile;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Level.Turn;
import me.jack.ld41.Tower.TestTower;
import me.jack.ld41.Tower.Tower;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jack on 21/04/2018.
 */
public class InGameState extends BasicGameState {

    Level level = null;
    private Rectangle gameArea = new Rectangle(0, 0, 420, 330), towerSelectArea = new Rectangle(420, 0, 160, 330), hud = new Rectangle(0, 330, 580, 150);

    private Tower inHand = null;
    ArrayList<Tower> towers = new ArrayList<>();
    private HashMap<java.awt.Rectangle, Tower> rectToTower;


    private GUIArea towersGUIArea;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        level = Level.fromImage(new Image("res/levels/level.png"));
        towersGUIArea = new GUIArea(420, 0, 160, 330);
        towersGUIArea.addElement(new TextButton("Towers",0,0,160,30));
        // inHand = new TestTower(0, 0);
        for (int i = 0; i != 5; i++)
            towers.add(new TestTower(0, 0));

        GUIElementListener listener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                System.out.println("Clicked");
                if(element instanceof TowerElement){
                    Tower t = ((TowerElement)element).getTower();
                    inHand = t.copy();
                }
            }

            @Override
            public void mouseEnter(int x, int y, GUIElement element) {

            }

            @Override
            public void mouseLeave(int x, int y, GUIElement element) {

            }
        };
        int startY = 30;
        int x = 0;
        int y = startY;
        int width = towersGUIArea.getWidth() / 4;
        int height = width;
        for(Tower t : towers){
            towersGUIArea.addElement(new TowerElement(t,x,y,width,height).setListener(listener));
            x += width;
            if(x >= width * 4){
                x = 0;
                y += height;
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.black);
        graphics.fill(gameArea);
        graphics.setColor(Color.red);
        graphics.fill(towerSelectArea);
        graphics.setColor(Color.blue);
        graphics.fill(hud);
        graphics.setColor(Color.white);
        graphics.translate(gameArea.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2, gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2);
        level.render(graphics);
        graphics.resetTransform();
        if (inHand != null) {
            Tile currentMouseTile = getCurrentMouseTile(gameContainer);
            if (currentMouseTile == null || currentMouseTile instanceof DirtTile) {
                int mX = gameContainer.getInput().getMouseX();
                int mY = gameContainer.getInput().getMouseY();
                graphics.translate(mX - inHand.getWidth() * Tile.TILE_SIZE, mY - inHand.getHeight() * Tile.TILE_SIZE);
                inHand.render(graphics);
            } else if (currentMouseTile != null) {
                graphics.translate(gameArea.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2, gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2);
                graphics.translate(currentMouseTile.getX() * Tile.TILE_SIZE, currentMouseTile.getY() * Tile.TILE_SIZE);
                inHand.render(graphics);
                graphics.setColor(Color.green);
                graphics.drawRect(0, 0, inHand.getWidth() * Tile.TILE_SIZE, inHand.getHeight() * Tile.TILE_SIZE);
            }
        }
        graphics.resetTransform();

        graphics.translate(towerSelectArea.getX(), towerSelectArea.getY());
       // graphics.drawString("Towers:", 0, 0);
        graphics.resetTransform();
        towersGUIArea.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        level.update(this, i);
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (key == Keyboard.KEY_SPACE && level.currentTurn == Turn.PLAYER_TURN)
            level.toggleTurn();
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        towersGUIArea.mouseDown(x, y, button);
        if (gameArea.contains(x, y)) {
            if (inHand != null) {
                Tile currentMouseTile = getCurrentMouseTile(x, y);
                if (currentMouseTile != null && !(currentMouseTile instanceof DirtTile)) {
                    Tower t = inHand.copy();
                    t.setX(currentMouseTile.getX() * Tile.TILE_SIZE);
                    t.setY(currentMouseTile.getY() * Tile.TILE_SIZE);
                    level.addTower(t);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        super.mouseReleased(button, x, y);
        towersGUIArea.mouseUp(x, y);
    }

    @Override
    public int getID() {
        return StateID.IN_GAME.getID();
    }

    public Tile getCurrentMouseTile(int mX, int mY) {
        mX -= gameArea.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2;
        mY -= gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2;
        mX /= Tile.TILE_SIZE;
        mY /= Tile.TILE_SIZE;
        return level.getTileAt(mX, mY);
    }

    public Tile getCurrentMouseTile(GameContainer gameContainer) {
        int mX = gameContainer.getInput().getMouseX();
        int mY = gameContainer.getInput().getMouseY();
        return getCurrentMouseTile(mX, mY);
    }


}
