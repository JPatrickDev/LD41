package me.jack.ld41.Level;

import me.jack.ld41.Entity.*;
import me.jack.ld41.Entity.Projectile.EntityProjectile;
import me.jack.ld41.Level.Tile.DirtTile;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.State.InGameState;
import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;
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
    private ArrayList<EntityProjectile> entityProjectiles = new ArrayList<>();


    private int livesLeft = 8;


    private int level = 1;
    private int points;

    private float money = 10f;

    private int round = 1;
    private int spawnedThisRound = 0;

    static {
        colourToTile.put("3B9415", "GrassTile");
        colourToTile.put("702400", "DirtTile");
        colourToTile.put("404040", "StoneTile");
    }

    public Level(int w, int h) {
        this.width = w;
        this.height = h;
        this.tiles = new Tile[w][h];

    }

    public void render(Graphics g, InGameState parent) {
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
            g.translate(t.getX(), t.getY());
            if (parent != null)
                t.render(g, parent.currentlySelected == t);
            else
                t.render(g, false);
            g.translate(-t.getX(), -t.getY());
        }

        for (EntityProjectile p : entityProjectiles) {
            p.render(g);
        }
        g.drawString(currentTurn.name(), -40, -40);
    }

    public static final Random r = new Random();

    int i = 0;

    public int update(InGameState parent, int delta, int mX, int mY) {
        //System.out.println(skipping);
        for (Tile[] t : tiles) {
            for (Tile tile : t) {
                if (tile != null)
                    tile.update(this);
            }
        }

        if (currentTurn == Turn.TOWER_TURN) {
            boolean allDone = true;
            for (Tower t : towers) {
                ArrayList<EntityProjectile> p = t.update(this, delta, new Point(mX, mY));
                if (p != null)
                    entityProjectiles.addAll(p);
                if (!t.isTurnOver())
                    allDone = false;
            }
            for (EntityProjectile p : entityProjectiles) {
                p.update(this);
                if (!p.isDead())
                    allDone = false;
            }
            if (allDone) {
                //System.out.println("Tower turn over");
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
                //System.out.println("Computer turn over");
                toggleTurn();
            }
        }

        Iterator<PathFollower> pI = pathFollowers.iterator();
        while (pI.hasNext()) {
            PathFollower next = pI.next();
            if (next.isDead()) {
                pI.remove();
                if (next.wasKilled())
                    pathfinderKilled();
            }
        }


        Iterator<EntityProjectile> eI = entityProjectiles.iterator();
        while (eI.hasNext()) {
            if (eI.next().isDead())
                eI.remove();
        }

        if (currentTurn == Turn.PLAYER_TURN) {
            if (skipping > 0 || skipping == -1) {
                toggleTurn();
                if (skipping > 0)
                    skipping--;
            }
        }
        return i;
    }

    public void toggleTurn() {
        if (currentTurn == Turn.PLAYER_TURN) {
            //System.out.println("Starting towers");
            i++;
            currentTurn = Turn.TOWER_TURN;
            for (Tower t : towers) {
                t.newTurn(this);
            }
        } else if (currentTurn == Turn.TOWER_TURN) {
            currentTurn = Turn.COMPUTER_TURN;
            for (PathFollower p : pathFollowers)
                p.nextStep(this);
        } else {
            attemptSpawn();
            currentTurn = Turn.PLAYER_TURN;
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    private int skipping;

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

    public ArrayList<PathFollower> getPathFollowers() {
        return pathFollowers;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public void removeLife() {
        this.livesLeft--;
    }

    public void pathfinderKilled() {
        addPoints(1);
        float i = (float) (2 + r.nextFloat()/2);
        addMoney(Float.valueOf(roundOffTo2DecPlaces(i)));
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void addPoints(int points) {
        this.points += points;
        int nextLevel = (int) Math.pow((level + 1) / 2, 2);
        if (this.points >= Math.pow((level + 1) / 2, 2)) {
            int diff = (int) ((this.points + points) - (Math.pow((level + 1) / 2, 2)));
            this.level += 1;
            this.points = diff;
        }
    }

    public void attemptSpawn() {
        for (PathFollower f : this.getPathFollowers()) {
            if (f.getX() == startPoint.x * Tile.TILE_SIZE && f.getY() == startPoint.y * Tile.TILE_SIZE) {
                return;
            }
        }
        if (r.nextInt(7) >= 5)
            return;
        if (round >= 5 && round < 10) {
            if (r.nextInt(5) == 0) {
                pathFollowers.add(new EntityTwo(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
                spawned();
                return;
            } else {
                pathFollowers.add(new EntityOne(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
                spawned();
                return;
            }
        }
        if (round >= 10 && round < 15) {
            if (r.nextInt(5) == 0) {
                pathFollowers.add(new EntityTwo(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
                spawned();
                return;
            } else {
                pathFollowers.add(new EntityThree(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
                spawned();
                return;
            }
        }
        if (round >= 15) {
            if (r.nextInt(5) == 0) {
                pathFollowers.add(new EntityThree(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
                spawned();
                return;
            } else {
                pathFollowers.add(new EntityFour(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
                spawned();
                return;
            }
        } else {
            pathFollowers.add(new EntityOne(startPoint.x * Tile.TILE_SIZE, startPoint.y * Tile.TILE_SIZE));
            spawned();
            return;
        }

    }

    public void spawned() {
        spawnedThisRound++;
        if (spawnedThisRound >= getToSpawn(round)) {
            spawnedThisRound = 0;
            round++;
            if (skipping == -1)
                skipping = 0;
        }
    }

    public int getPoints() {
        return this.points;
    }

    public int getLevel() {
        return this.level;
    }

    public void addMoney(float money) {
        this.money += money;
    }

    public float getMoney() {
        return this.money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    String roundOffTo2DecPlaces(float val) {
        return String.format("%.2f", val);
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public int getToSpawn(int round) {
        return (int) Math.ceil(0.5 * Math.pow(1.5, round));
    }

    public int getRound() {
        return round;
    }


    public void skipTurns(int skipping) {
        this.skipping = skipping;
    }

    public void addProjectile(me.jack.ld41.Weapon.Projectiles.Projectile basicSmallBullet, int x, int y, int tX, int tY) {
        entityProjectiles.add(new EntityProjectile(basicSmallBullet, x, y, tX, tY));
    }

    public int getSpawnedThisRound() {
        return spawnedThisRound;
    }
}
