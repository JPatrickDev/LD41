package me.jack.ld41.State;

import me.jack.ld41.GUI.Elements.TextArea;
import me.jack.ld41.GUI.Elements.TextButton;
import me.jack.ld41.GUI.Elements.TowerElement;
import me.jack.ld41.GUI.Elements.UpgradeElement;
import me.jack.ld41.GUI.GUIArea;
import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.GUI.GUIElementListener;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.DirtTile;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Level.Turn;
import me.jack.ld41.Tower.TestTower;
import me.jack.ld41.Tower.TestTowerTwo;
import me.jack.ld41.Tower.Tower;
import me.jack.ld41.Tower.Upgrades.RangeUpgrade;
import me.jack.ld41.Tower.Upgrades.ShotsPerTurn;
import me.jack.ld41.Tower.Upgrades.Upgrade;
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
    public ArrayList<Tower> towers = new ArrayList<>();
    private HashMap<java.awt.Rectangle, Tower> rectToTower;


    private GUIArea towersGUIArea, hudGUIArea;

    int turnCount = 0;
    TextArea turnCounter, turnDisplay, livesDisplay, expDisplay, moneyDisplay,roundDisplay;
    TextButton skip5Turns,skip10Turns;

    ArrayList<UpgradeElement> upgrades = new ArrayList<>();

    private Tower currentlySelected = null;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        level = Level.fromImage(new Image("res/levels/level.png"));
        towersGUIArea = new GUIArea(420, 0, 160, 330);
        towersGUIArea.addElement(new TextArea("Towers", 0, 0, 160, 30));

        hudGUIArea = new GUIArea(0, 330, 580, 150);
        turnCounter = new TextArea("Turn: " + turnCount, 0, 0, 200, 20, Color.yellow, Color.black);
        hudGUIArea.addElement(turnCounter);

        turnDisplay = new TextArea(level.getCurrentTurn().name(), 0, 20, 200, 20, Color.green, Color.black);
        hudGUIArea.addElement(turnDisplay);

        livesDisplay = new TextArea("Lives Remaining: " + level.getLivesLeft(), 0, 40, 200, 20, Color.red, Color.black);
        hudGUIArea.addElement(livesDisplay);

        expDisplay = new TextArea(level.getLevel() + ":" + level.getPoints() + "(" + Math.pow((level.getLevel() + 1) / 2, 2) + ")", 0, 60, 200, 20, Color.pink, Color.black);
        hudGUIArea.addElement(expDisplay);


        moneyDisplay = new TextArea("Money:" + level.getMoney(), 0, 80, 200, 20, Color.cyan, Color.black);
        hudGUIArea.addElement(moneyDisplay);

        roundDisplay = new TextArea("Round:" + level.getRound() + "(" + level.getToSpawn(level.getRound()) + ")", 0, 100, 200, 20, Color.magenta, Color.black);
        hudGUIArea.addElement(roundDisplay);


        GUIElementListener skipListener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                if(element instanceof TextButton){
                    switch(((TextButton) element).getText()){
                        case "Skip 5 Turns" : level.skipTurns(5); break;
                        case "Skip 10 Turns" : level.skipTurns(10); break;
                        case "Skip To Next Round":  level.skipTurns(-1); break;
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

        skip5Turns = new TextButton("Skip 5 Turns", hudGUIArea.getWidth() - 200, 0, 200, 20, Color.magenta, Color.black);
        skip5Turns.setListener(skipListener);
        hudGUIArea.addElement(skip5Turns);

        skip10Turns = new TextButton("Skip 10 Turns", hudGUIArea.getWidth() - 200, 20, 200, 20, Color.pink, Color.black);
        skip10Turns.setListener(skipListener);
        hudGUIArea.addElement(skip10Turns);

        skip10Turns = new TextButton("Skip To Next Round", hudGUIArea.getWidth() - 200, 40, 200, 20, Color.pink, Color.black);
        skip10Turns.setListener(skipListener);
        hudGUIArea.addElement(skip10Turns);
        GUIElementListener upgradesListener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                if (element instanceof UpgradeElement) {
                    if (InGameState.this.currentlySelected != null) {
                        System.out.println("Using");
                        Upgrade up = ((UpgradeElement) element).getUpgrade();
                        if (up != null && up.getNextForTower(currentlySelected) != null && level.getMoney() >= up.getCost()) {
                            up.use(InGameState.this.currentlySelected, InGameState.this);
                            level.setMoney(level.getMoney() - up.getCost());
                            ((UpgradeElement) element).setUpgrade(up);
                            setCurrentlySelectedTower(currentlySelected);
                            inHand = null;
                        }
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
        UpgradeElement shotRateUpgrade = new UpgradeElement(new ShotsPerTurn(0), 250, 0, 64, 64);
        shotRateUpgrade.setListener(upgradesListener);
        upgrades.add(shotRateUpgrade);
        hudGUIArea.addElement(shotRateUpgrade);
        UpgradeElement rangeUpgrade = new UpgradeElement(new RangeUpgrade(0), 250 + 64, 0, 64, 64);
        rangeUpgrade.setListener(upgradesListener);
        upgrades.add(rangeUpgrade);
        hudGUIArea.addElement(rangeUpgrade);


        // inHand = new TestTower(0, 0);
        towers.add(new TestTower(0, 0, 0, 0, 0, 0));
        towers.add(new TestTowerTwo(0, 0, 0, 0, 0, 0));
        GUIElementListener listener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                System.out.println("Clicked");
                if (element instanceof TowerElement) {
                    Tower t = ((TowerElement) element).getTower();
                    if (((TowerElement) element).isUnlocked && InGameState.this.level.getMoney() >= t.getCost()) {
                        inHand = t.copy();
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
        int startY = 30;
        int x = 0;
        int y = startY;
        int width = towersGUIArea.getWidth() / 4;
        int height = width;
        for (Tower t : towers) {
            towersGUIArea.addElement(new TowerElement(t, x, y, width, height).setListener(listener));
            x += width;
            if (x >= width * 4) {
                x = 0;
                y += height;
            }
        }
    }

    public void lockCheck() {

        for (GUIElement element : towersGUIArea.getElements()) {
            if (element instanceof TowerElement) {
                TowerElement t = ((TowerElement) element);
                Tower to = t.getTower();
                t.isUnlocked = this.level.getLevel() >= to.getLevel();
            }
        }
    }

    public void setCurrentlySelectedTower(Tower tower) {
        this.currentlySelected = tower;
        for (UpgradeElement e : upgrades) {
            Upgrade u = e.getUpgrade();
            if (u != null)
                e.setUpgrade(u.getNextForTower(tower));
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
        hudGUIArea.render(graphics);
        graphics.drawString(currentlySelected + " hi", 0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        lockCheck();
        turnCount = level.update(this, i);
        turnCounter.setText("Turn: " + turnCount);

        turnDisplay.setText(level.getCurrentTurn().name());
        livesDisplay.setText("Lives Remaining: " + level.getLivesLeft());
        expDisplay.setText(level.getLevel() + ":" + level.getPoints() + "(" + Math.pow((level.getLevel() + 1) / 2, 2) + ")");
        moneyDisplay.setText("Money:" + level.getMoney());
        roundDisplay.setText("Round:" + level.getRound() + "(" + level.getToSpawn(level.getRound()) + ")");
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
        towersGUIArea.mouseDown(x - towersGUIArea.getX(), y - towersGUIArea.getY(), button);
        hudGUIArea.mouseDown(x - hudGUIArea.getX(), y - hudGUIArea.getY(), button);
        if (gameArea.contains(x, y)) {
            Tower towerAt = getMouseTower(x, y);
            if (inHand != null && towerAt == null) {
                Tile currentMouseTile = getCurrentMouseTile(x, y);
                this.currentlySelected = towerAt;
                if (currentMouseTile != null && !(currentMouseTile instanceof DirtTile) && level.getMoney() >= inHand.getCost()) {
                    Tower t = inHand.copy();
                    t.setX(currentMouseTile.getX() * Tile.TILE_SIZE);
                    t.setY(currentMouseTile.getY() * Tile.TILE_SIZE);
                    level.addTower(t);
                    level.setMoney(level.getMoney() - inHand.getCost());
                }
            }
            if (towerAt != null) {
                setCurrentlySelectedTower(towerAt);
            }
        }
    }

    private Tower getMouseTower(int mX, int mY) {
        mX -= gameArea.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2;
        mY -= gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2;
        mX /= Tile.TILE_SIZE;
        mY /= Tile.TILE_SIZE;
        int x = mX;
        int y = mY;
        for (Tower t : level.getTowers()) {
            if (t.getX() == x * Tile.TILE_SIZE && t.getY() == y * Tile.TILE_SIZE) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        super.mouseReleased(button, x, y);
        towersGUIArea.mouseUp(x - towersGUIArea.getX(), y - towersGUIArea.getY());
        hudGUIArea.mouseUp(x - hudGUIArea.getX(), y - hudGUIArea.getY());
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
