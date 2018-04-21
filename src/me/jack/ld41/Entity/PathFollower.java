package me.jack.ld41.Entity;

import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;

import java.awt.*;

/**
 * Created by Jack on 21/04/2018.
 */
public abstract class PathFollower extends Entity {

    private int pathPos = 0;
    private boolean isMoving;
    private Point currentTarget = null;

    public PathFollower(float x, float y) {
        super(x, y);
    }

    public void nextStep(Level level) {
        pathPos++;
        isMoving = true;
        currentTarget = level.getPathPoint(pathPos);
        if(currentTarget == null){
            isMoving = false;
            dead = true;
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    @Override
    public void update(Level level) {
        if (isMoving) {
            if (currentTarget.x * Tile.TILE_SIZE > getX() && getxO() < Tile.TILE_SIZE) {
                setxO(getxO() + moveSpeed);
            }
            if (currentTarget.x * Tile.TILE_SIZE < getX() && Math.abs(getxO()) < Tile.TILE_SIZE) {
                setxO(getxO() - moveSpeed);
            }

            if (currentTarget.y * Tile.TILE_SIZE > getY() && getyO() < Tile.TILE_SIZE) {
                setyO(getyO() + moveSpeed);
            }
            if (currentTarget.y * Tile.TILE_SIZE < getY() && Math.abs(getyO()) < Tile.TILE_SIZE) {
                setyO(getyO() - moveSpeed);
            }

            if(Math.abs(getxO()) >= Tile.TILE_SIZE || Math.abs(getyO()) >= Tile.TILE_SIZE){
                setxO(0);
                setyO(0);
                setX(currentTarget.x * Tile.TILE_SIZE);
                setY(currentTarget.y * Tile.TILE_SIZE);
                System.out.println("Done!");
                isMoving = false;
            }
        }
    }


}
