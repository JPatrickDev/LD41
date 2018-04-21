package me.jack.ld41.Level;

import me.jack.ld41.Level.Tile.DirtTile;
import me.jack.ld41.Level.Tile.GrassTile;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.State.InGameState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public class Level {

    private Tile[][] tiles = null;
    private int width,height;

    public Level(int w,int h){
        this.width = w;
        this.height = h;
        this.tiles = new Tile[w][h];
        for(int x = 0; x != w; x++){
            for(int y = 0; y != h; y++){
                if(new Random().nextInt(5) == 0){
                    try {
                        tiles[x][y] = new DirtTile(x,y);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                try {
                    tiles[x][y] = new GrassTile(x,y);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void render(Graphics g){
        for(Tile[] t : tiles){
            for(Tile tile : t){
                tile.render(g);
            }
        }
    }

    public void update(InGameState parent){
        for(Tile[] t : tiles){
            for(Tile tile : t){
                tile.update(this);
            }
        }
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
