package me.jack.ld41.GUI.Elements;

import me.jack.ld41.GUI.GUIElement;
import me.jack.ld41.Level.Turn;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 22/04/2018.
 */
public class TurnDisplayElement extends GUIElement {

    SpriteSheet sheet = null;
    Turn turn;

    private boolean changing = false;

    public TurnDisplayElement(int x, int y, int width, int height) {
        super(x, y, width, height);
        try {
            sheet = new SpriteSheet("res/turnDisplay.png", 94, 42);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentTurn(Turn turn) {
        if (this.turn != turn) {
            changing = true;
        }
        this.turn = turn;
    }


    int i = 0;
    @Override
    public void render(Graphics graphics) {
        if(turn == Turn.PLAYER_TURN){
            if(changing){
                i++;
                if(i > 10) {
                    i = 0;
                    changing = false;
                }
                graphics.drawImage(sheet.getSprite(0, 6), 0, 0);
            }else{
                graphics.drawImage(sheet.getSprite(0, 1), 0, 0);
            }
        }
        if(turn == Turn.TOWER_TURN){
            if(changing){
                i++;
                if(i > 10) {
                    i = 0;
                    changing = false;
                }
                graphics.drawImage(sheet.getSprite(0, 2), 0, 0);
            }else{
                graphics.drawImage(sheet.getSprite(0, 3), 0, 0);
            }
        }
        if(turn == Turn.COMPUTER_TURN){
            if(changing){
                i++;
                if(i > 10) {
                    i = 0;
                    changing = false;
                }
                graphics.drawImage(sheet.getSprite(0, 4), 0, 0);
            }else{
                graphics.drawImage(sheet.getSprite(0, 5), 0, 0);
            }
        }
    }

    @Override
    public void update(GameContainer container) {

    }
}
