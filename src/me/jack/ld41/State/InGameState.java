package me.jack.ld41.State;

import me.jack.ld41.GUI.Elements.*;
import me.jack.ld41.GUI.GUIArea;
import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.GUI.GUIElementListener;
import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.DirtTile;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Level.Turn;
import me.jack.ld41.Tower.*;
import me.jack.ld41.Tower.Upgrades.RangeUpgrade;
import me.jack.ld41.Tower.Upgrades.ShotsPerTurn;
import me.jack.ld41.Tower.Upgrades.Upgrade;
import me.jack.ld41.Tower.Upgrades.WeaponUpgrade;
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

    public static String path;
    Level level = null;
    private Rectangle gameArea = new Rectangle(0, 0, 330, 330), towerSelectArea = new Rectangle(330, 0, 160, 170), hud = new Rectangle(0, 330, 580, 160), skipArea = new Rectangle(330, 170, 160, 330 - 100);

    private Tower inHand = null;
    public ArrayList<Tower> towers = new ArrayList<>();
    private HashMap<java.awt.Rectangle, Tower> rectToTower;


    private GUIArea towersGUIArea, hudGUIArea, skipGUIArea;

    int turnCount = 0;
    TextArea turnCounter, livesDisplay, expDisplay, moneyDisplay, roundDisplay;
    TextButton skip5Turns, skip10Turns, skipRoundTurns, skip20Turns, instructions, backToMain;

    EXPElement exp, round;

    TurnDisplayElement turnDisplay;

    ArrayList<UpgradeElement> upgrades = new ArrayList<>();

    public Tower currentlySelected = null;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    public void createUI() throws SlickException {
        towersGUIArea = new GUIArea(330, 0, 160, 170);
        towersGUIArea.addElement(new TextArea("Towers", 0, 7, 160, 30));

        hudGUIArea = new GUIArea(14, 344, 462, 132);
        turnCounter = new TextArea("Turn: " + turnCount, 6, 6, 75, 17);
        hudGUIArea.addElement(turnCounter);

        turnDisplay = new TurnDisplayElement(118, 45, 94, 42);
        hudGUIArea.addElement(turnDisplay);

        livesDisplay = new TextArea("Lives Remaining: " + level.getLivesLeft(), 6, 109, 206, 17);
        hudGUIArea.addElement(livesDisplay);

        expDisplay = new TextArea(level.getLevel() + ":" + level.getPoints() + "(" + Math.pow((level.getLevel() + 1) / 2, 2) + ")", 0, 60, 200, 20, Color.pink, Color.black);
        // hudGUIArea.addElement(expDisplay);


        moneyDisplay = new TextArea("Money:" + level.getMoney(), 93, 6, 119, 17);
        hudGUIArea.addElement(moneyDisplay);

        roundDisplay = new TextArea("Round:" + level.getRound() + "(" + level.getToSpawn(level.getRound()) + ")", 0, 100, 200, 20, Color.magenta, Color.black);
        // hudGUIArea.addElement(roundDisplay);


        GUIElementListener skipListener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                if (element instanceof TextButton) {
                    switch (((TextButton) element).getText()) {
                        case "5 Turns":
                            level.skipTurns(5);
                            break;
                        case "10 Turns":
                            level.skipTurns(10);
                            break;
                        case "20 Turns":
                            level.skipTurns(20);
                            break;
                        case "Next Round":
                            level.skipTurns(-1);
                            break;
                        case "Tutorial":
                            drawTut = !drawTut;
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

        skipGUIArea = new GUIArea((int) skipArea.getX(), (int) skipArea.getY(), (int) skipArea.getWidth(), (int) skipArea.getHeight());

        TextArea skipTitle = new TextArea("Skip Turns:", 0, 0, (int) skipArea.getWidth(), 20);
        skipGUIArea.addElement(skipTitle);
        skip5Turns = new TextButton("5 Turns", 14, 21, (int) skipArea.getWidth() - 28, 25, Color.pink, Color.black);
        skip5Turns.setListener(skipListener);
        skipGUIArea.addElement(skip5Turns);

        skip10Turns = new TextButton("10 Turns", 14, 56, (int) skipArea.getWidth() - 28, 25, Color.pink, Color.black);
        skip10Turns.setListener(skipListener);
        skipGUIArea.addElement(skip10Turns);


        skip20Turns = new TextButton("20 Turns", 14, 91, (int) skipArea.getWidth() - 28, 25, Color.pink, Color.black);
        skip20Turns.setListener(skipListener);
        skipGUIArea.addElement(skip20Turns);

        skipRoundTurns = new TextButton("Next Round", 14, 126, (int) skipArea.getWidth() - 28, 25, Color.pink, Color.black);
        skipRoundTurns.setListener(skipListener);
        skipGUIArea.addElement(skipRoundTurns);

        instructions = new TextButton("Tutorial", 234, 105, 110, 25, Color.pink, Color.black);
        instructions.setListener(skipListener);
        hudGUIArea.addElement(instructions);

        backToMain = new TextButton("Main Menu", 234 + 113 + 3, 105, 110, 25, Color.pink, Color.black);
        backToMain.setListener(skipListener);
        hudGUIArea.addElement(backToMain);

        GUIElementListener upgradesListener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                if (element instanceof UpgradeElement) {
                    if (InGameState.this.currentlySelected != null && level.currentTurn == Turn.PLAYER_TURN) {
                        //System.out.println("Using");
                        Upgrade up = ((UpgradeElement) element).getUpgrade();
                        if (up != null && up.getNextForTower(currentlySelected) != null && level.getMoney() >= up.getCost() && up.isValidUpgrade()) {
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
                System.out.println("Mouse Enter");
                if(element instanceof UpgradeElement){
                    ((UpgradeElement) element).mouseEntered();
                }
            }

            @Override
            public void mouseLeave(int x, int y, GUIElement element) {
                System.out.println("Mouse Leave");
                if(element instanceof UpgradeElement) {
                    ((UpgradeElement) element).mouseLeaved();
                }
            }
        };
        TextArea upgradesTitle = new TextArea("Upgrades:", 232, 0, 230, 17);
        hudGUIArea.addElement(upgradesTitle);
        UpgradeElement shotRateUpgrade = new UpgradeElement(new ShotsPerTurn(0), 250, 18, 64, 64);
        shotRateUpgrade.setListener(upgradesListener);
        upgrades.add(shotRateUpgrade);
        hudGUIArea.addElement(shotRateUpgrade);
        UpgradeElement rangeUpgrade = new UpgradeElement(new RangeUpgrade(0), 250 + 64, 18, 64, 64);
        rangeUpgrade.setListener(upgradesListener);
        upgrades.add(rangeUpgrade);
        hudGUIArea.addElement(rangeUpgrade);

        UpgradeElement weaponUpgrade = new UpgradeElement(new WeaponUpgrade(0), 250 + 64 * 2, 18, 64, 64);
        weaponUpgrade.setListener(upgradesListener);
        upgrades.add(weaponUpgrade);
        hudGUIArea.addElement(weaponUpgrade);


        // inHand = new TowerOne(0, 0);
        towers.clear();
        towers.add(new TowerOne(0, 0, 0, 0, 0, 0));
        towers.add(new TowerTwo(0, 0, 0, 0, 0, 0));
        towers.add(new TowerThree(0, 0, 0, 0, 0, 0));
        towers.add(new TowerFour(0, 0, 0, 0, 0, 0));
        GUIElementListener listener = new GUIElementListener() {
            @Override
            public void mouseDown(int x, int y, int button, GUIElement element) {

            }

            @Override
            public void mouseUp(int x, int y, GUIElement element) {
                //System.out.println("Clicked");
                if (element instanceof TowerElement) {
                    Tower t = ((TowerElement) element).getTower();
                    if (((TowerElement) element).isUnlocked && InGameState.this.level.getMoney() >= t.getCost()) {
                        inHand = t.copy();
                    }
                }
            }

            @Override
            public void mouseEnter(int x, int y, GUIElement element) {
                System.out.println("Mouse Enter");
                if(element instanceof UpgradeElement){
                    ((UpgradeElement) element).mouseEntered();
                }
            }

            @Override
            public void mouseLeave(int x, int y, GUIElement element) {
                System.out.println("Mouse Leave");
                if(element instanceof UpgradeElement) {
                    ((UpgradeElement) element).mouseLeaved();
                }
            }
        };
        int startY = 30;
        int x = 0;
        int y = startY;
        int width = (towersGUIArea.getWidth() - 60) / 2;
        x = 22;
        int height = width;
        for (Tower t : towers) {
            towersGUIArea.addElement(new TowerElement(t, x, y, width, height).setListener(listener));
            x += width + 24 - 8;
            if (x >= width * 2) {
                x = 22;
                y += height + 20;
            }
        }

        exp = new EXPElement(6, 45, 42, 42, Color.blue);
        hudGUIArea.addElement(exp);

        round = new EXPElement(62, 45, 42, 42, Color.red);
        hudGUIArea.addElement(round);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        level = Level.fromImage(new Image(path));
        createUI();

    }

    public void lockCheck() {

        for (GUIElement element : towersGUIArea.getElements()) {
            if (element instanceof TowerElement) {
                TowerElement t = ((TowerElement) element);
                Tower to = t.getTower();
                t.isUnlocked = this.level.getLevel() >= to.getLevel();
                t.canAfford = this.level.getMoney() >= to.getCost();
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

    Image upgradesOverlay = null, tutorialOverlay = null;

    boolean drawTut = false;

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        if (upgradesOverlay == null) {
            upgradesOverlay = new Image("res/upgradesOverlay.png");
            tutorialOverlay = new Image("res/tutorial.png");
        }

        graphics.translate(gameArea.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2, gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2);
        level.render(graphics, this);
        graphics.resetTransform();
      /*  graphics.setColor(Color.red);
        graphics.fill(towerSelectArea);
        graphics.setColor(Color.blue);
        graphics.fill(hud);
        graphics.setColor(Color.white);
        graphics.fill(skipArea);*/

        graphics.drawImage(new Image("res/guiOverlay.png"), 0, 0);
        if (inHand != null) {
            Tile currentMouseTile = getCurrentMouseTile(gameContainer);
            if (currentMouseTile == null ||  currentMouseTile.isSolid()) {
                int mX = gameContainer.getInput().getMouseX();
                int mY = gameContainer.getInput().getMouseY();
                graphics.translate(mX - inHand.getWidth() * Tile.TILE_SIZE, mY - inHand.getHeight() * Tile.TILE_SIZE);
                inHand.render(graphics, false);
            } else if (currentMouseTile != null) {
                graphics.translate(gameArea.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2, gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2);
                graphics.translate(currentMouseTile.getX() * Tile.TILE_SIZE, currentMouseTile.getY() * Tile.TILE_SIZE);
                inHand.render(graphics, false);
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
        skipGUIArea.render(graphics);
        graphics.resetTransform();
        if (currentlySelected == null) {
            graphics.drawImage(upgradesOverlay, 263, 362);
        }
        if (drawTut && tutorialOverlay != null) {
            graphics.drawImage(tutorialOverlay, 0, 0);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        lockCheck();
        turnCount = level.update(this, i, (int) (gameContainer.getInput().getMouseX() + gameArea.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2), (int) ((int) gameContainer.getInput().getMouseY() + (gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2)));
        turnCounter.setText("Turn:" + turnCount);

        //  turnDisplay.setText(level.getCurrentTurn().name());
        livesDisplay.setText("Lives Remaining: " + level.getLivesLeft());
        expDisplay.setText(level.getLevel() + ":" + level.getPoints() + "(" + Math.pow((level.getLevel() + 1) / 2, 2) + ")");
        exp.setNum(level.getLevel());
        exp.setScore(level.getPoints(), (float) Math.pow((level.getLevel() + 1) / 2, 2));
        moneyDisplay.setText("Money:" + (int) (level.getMoney()));
        roundDisplay.setText("Round:" + level.getRound() + "(" + level.getToSpawn(level.getRound()) + ")");
        round.setNum(level.getRound());
        round.setScore(level.getSpawnedThisRound(), level.getToSpawn(level.getRound()));
        turnDisplay.setCurrentTurn(level.currentTurn);
        if (level.getLivesLeft() <= 0) {
            GameOverState.level = level;
            stateBasedGame.enterState(StateID.GAME_OVER.getID());
        }
        towersGUIArea.update(gameContainer);
        hudGUIArea.update(gameContainer);
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
        if (level.currentTurn == Turn.PLAYER_TURN)
            skipGUIArea.mouseDown(x - skipGUIArea.getX(), y - skipGUIArea.getY(), button);
        if (gameArea.contains(x, y) && level.currentTurn == Turn.PLAYER_TURN) {
            Tower towerAt = getMouseTower(x, y);
            if (inHand != null && towerAt == null) {
                Tile currentMouseTile = getCurrentMouseTile(x, y);
                this.currentlySelected = towerAt;
                if (currentMouseTile != null && !currentMouseTile.isSolid() && level.getMoney() >= inHand.getCost()) {
                    Tower t = inHand.copy();
                    t.setX(currentMouseTile.getX() * Tile.TILE_SIZE);
                    t.setY(currentMouseTile.getY() * Tile.TILE_SIZE);
                    level.addTower(t);
                    level.setMoney(level.getMoney() - inHand.getCost());
                    inHand = null;
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
        skipGUIArea.mouseUp(x - skipGUIArea.getX(), y - skipGUIArea.getY());
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
