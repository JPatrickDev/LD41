package me.jack.ld41;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 20/04/2018.
 */
public class Main {

    public static void main(String[] args) throws SlickException {
        AppGameContainer agc = new AppGameContainer(new LD41("LD41 - Towered"));
        agc.setShowFPS(false);
        agc.setDisplayMode(490,490,false);
        agc.setTargetFrameRate(60);
        agc.start();
    }


}
