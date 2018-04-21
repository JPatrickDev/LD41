package me.jack.ld41.State;

import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 21/04/2018.
 */
public class InGameState extends BasicGameState {

    Level level = null;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        level = Level.fromImage(new Image("res/levels/level.png"));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.translate(290 - (level.getWidth() * Tile.TILE_SIZE) / 2, 240 - (level.getHeight() * Tile.TILE_SIZE) / 2);
        level.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        level.update(this);
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if(key == Keyboard.KEY_SPACE)
            level.toggleTurn();
    }

    @Override
    public int getID() {
        return StateID.IN_GAME.getID();
    }

}
