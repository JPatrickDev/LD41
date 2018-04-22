package me.jack.ld41.State;

/**
 * Created by Jack on 21/04/2018.
 */
public enum StateID {
    IN_GAME(1),MAIN_MENU(0),ABOUT(2),GAME_OVER(4),LEVEL_SELECT(5);

    private int i;
    StateID(int i) {
        this.i = i;
    }

    public int getID(){
        return i;
    }
}
