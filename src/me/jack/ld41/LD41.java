package me.jack.ld41;

import me.jack.ld41.State.InGameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 20/04/2018.
 */
public class LD41 extends StateBasedGame{
    public LD41(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new InGameState());
    }
}
