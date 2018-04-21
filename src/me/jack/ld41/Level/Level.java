package me.jack.ld41.Level;

import me.jack.ld41.Entity.PathFollower;
import me.jack.ld41.Entity.Projectile.Projectile;
import me.jack.ld41.Entity.TestEntity;
import me.jack.ld41.Level.Tile.DirtTile;
import me.jack.ld41.Level.Tile.GrassTile;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.State.InGameState;
import me.jack.ld41.Tower.Tower;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Jack on 21/04/2018.
 */
public class Level {

    private Tile[][] tiles = null;
    private int width, height;

    private static String startTile = "FF0000";
    private static String endTile = "0026FF";

    private Point[] path = null;
    private Point startPoint, endPoint;
    public static final HashMap<String, String> colourToTile = new HashMap<>();

    public Turn currentTurn = Turn.PLAYER_TURN;

    private ArrayList<PathFollower> pathFollowers = new ArrayList<>();
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();


    static {
        colourToTile.put("3B9415", "GrassTile");
        colourToTile.put("702400", "DirtTile");
    }

    public Level(int w, int h) {
        this.width = w;
        this.height = h;
        this.tiles = new Tile[w][h];

    }

    public void render(Graphics g) {
        for (Tile[] t : tiles) {
            for (Tile tile : t) {
                if (tile != null)
                    tile.render(g);
            }
        }

        for (PathFollower p : pathFollowers) {
            p.render(g);
        }

        for (Tower t : towers) {
            t.render(g);
        }

        for (Projectile p : projectiles) {
            p.render(g);
        }
        g.drawString(currentTurn.name(), -40, -40);
    }

    public static final Random r = new Random();

    public void update(InGameState parent, int delta) {
        for (Tile[] t : tiles) {
            for (Tile tile : t) {
                if (tile != null)
                    tile.update(this);
            }
        }

        if (currentTurn == Turn.TOWER_TURN) {
            boolean allDone = true;
            for (Tower t : towers) {
                Projectile p = t.update(this, delta);
                if (p != null)
                    projectiles.add(p);
                if (!t.isTurnOver())
                    allDone = false;
            }
            for (Projectile p : projectiles) {
                p.update(this);
                if (!p.isDead())
                    allDone = false;
            }
            if (allDone) {
                System.out.println("Tower turn over");
                toggleTurn();
            }
        }

        if (currentTurn == Turn.COMPUTER_TURN) {
            boolean allDone = true;
            for (PathFollower t : pathFollowers) {
                t.update(this);
                if (t.isMoving())
                    allDone = false;
            }
            if (allDone) {
                System.out.println("Computer turn over");
                toggleTurn();
            }
        }

        Iterator<PathFollower> pI = pathFollowers.iterator();
        while (pI.hasNext()) {
            if (pI.next().isDead())
                pI.remove();
        }


        Iterator<Projectile> eI = projectiles.iterator();
        while (eI.hasNext()) {
            if (eI.next().isDead())
                eI.remove();
        }

    }

    public void toggleTurn() {
        if (currentTurn == Turn.PLAYER_TURN) {
            System.out.println("Starting towers");

            currentTurn = Turn.TOWER_TURN;
            for (Tower t : towers) {
                t.newTurn(this);
            }
        } else if (currentTurn == Turn.TOWER_TURN) {
            currentTurn = Turn.COMPUTER_TURN;
            if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
                pathFollowers.add(new TestEntity(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
            }
            for (PathFollower p : pathFollowers)
                p.nextStep(this);
        } else {
            currentTurn = Turn.PLAYER_TURN;
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTileAt(Tile t, int x, int y) {
        tiles[x][y] = t;
    }

    public Tile getTileAt(int x, int y) {
        if (x < 0 || y < 0)
            return null;
        if (x > getWidth() - 1 || y > getHeight() - 1)
            return null;
        return tiles[x][y];
    }


    public ArrayList<PathFollower> getTargets(float tX, float tY, float range) {
        ArrayList<PathFollower> e = new ArrayList<>();
        for (PathFollower p : pathFollowers) {
            float xSpeed = p.getX() - tX;
            float ySpeed = p.getY() - tY;
            float factor = (float) (Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
            if (factor <= range) {
                e.add(p);
            }
        }
        return e;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public static Level fromImage(Image image) throws SlickException {
        Level level = new Level(image.getWidth(), image.getHeight());
        for (int x = 0; x != level.getWidth(); x++) {
            for (int y = 0; y != level.getHeight(); y++) {
                Color c = image.getColor(x, y);
                System.out.println(c.r);
                String hex = String.format("%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
                System.out.println(hex);
                if (hex.equals(startTile)) {
                    level.setTileAt(new DirtTile(x, y), x, y);
                    level.setStartPoint(new Point(x, y));
                    continue;
                } else if (hex.equals(endTile)) {
                    level.setTileAt(new DirtTile(x, y), x, y);
                    level.setEndPoint(new Point(x, y));
                    continue;
                }
                String tile = colourToTile.get(hex);
                if (tile != null) {
                    Tile finalTile = Tile.fromName(tile, x, y);
                    level.setTileAt(finalTile, x, y);
                } else {
                    System.out.println("Null tile");
                }
            }
        }
        level.setPath(calculatePath(level));
        return level;
    }

    public static Point[] calculatePath(Level level) {
        ArrayList<Point> points = new ArrayList<>();
        Point startPoint = level.getStartPoint();
        Point endPoint = level.getEndPoint();
        Point currentPoint = startPoint;
        while (!currentPoint.equals(endPoint)) {
            points.add(currentPoint);
            if (level.getTileAt(currentPoint.x, currentPoint.y - 1) instanceof DirtTile && !points.contains(new Point(currentPoint.x, currentPoint.y - 1))) {
                currentPoint = new Point(currentPoint.x, currentPoint.y - 1);
            } else if (level.getTileAt(currentPoint.x, currentPoint.y + 1) instanceof DirtTile && !points.contains(new Point(currentPoint.x, currentPoint.y + 1))) {
                currentPoint = new Point(currentPoint.x, currentPoint.y + 1);
            } else if (level.getTileAt(currentPoint.x + 1, currentPoint.y) instanceof DirtTile && !points.contains(new Point(currentPoint.x + 1, currentPoint.y))) {
                currentPoint = new Point(currentPoint.x + 1, currentPoint.y);
            } else if (level.getTileAt(currentPoint.x - 1, currentPoint.y) instanceof DirtTile && !points.contains(new Point(currentPoint.x - 1, currentPoint.y))) {
                currentPoint = new Point(currentPoint.x - 1, currentPoint.y);
            }
        }
        points.add(currentPoint);
        return points.toArray(new Point[]{});
    }

    private Point getEndPoint() {
        return endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setPath(Point[] path) {
        this.path = path;
    }

    public Point getPathPoint(int pathPos) {
        if (pathPos >= path.length)
            return null;
        return path[pathPos];
    }

    public void addTower(Tower t) {
        this.towers.add(t);
    }
}
