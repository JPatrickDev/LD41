package me.jack.ld41.Tower;

/**
 * Created by Jack on 21/04/2018.
 */
public class TowerUpgrades {

    private int fireSpeed = 0,baseFireSpeed = 0;
    private int shotsPerTurn,baseShotsPerTurn = 0;
    private float range = 0f, baseRange = 0f;
    private int dmgLevel = 0, baseDmgLevel = 0;

    public TowerUpgrades(int fireSpeed, int baseFireSpeed, int shotsPerTurn, int baseShotsPerTurn, float range, float baseRange, int dmgLevel, int baseDmgLevel) {
        this.fireSpeed = fireSpeed;
        this.baseFireSpeed = baseFireSpeed;
        this.shotsPerTurn = shotsPerTurn;
        this.baseShotsPerTurn = baseShotsPerTurn;
        this.range = range;
        this.baseRange = baseRange;
        this.dmgLevel = dmgLevel;
        this.baseDmgLevel = baseDmgLevel;
    }

    public int getFireSpeed() {
        return 10;
    }

    public int getShotsPerTurn() {
        return shotsPerTurn;
    }

    public float getRange() {
        return range;
    }

    public int getDmgLevel() {
        return dmgLevel;
    }

    public void setFireSpeed(int fireSpeed) {
        this.fireSpeed = fireSpeed;
    }

    public void setShotsPerTurn(int shotsPerTurn) {
        this.shotsPerTurn = shotsPerTurn;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setDmgLevel(int dmgLevel) {
        this.dmgLevel = dmgLevel;
    }

    public int getBaseFireSpeed() {
        return baseFireSpeed;
    }

    public int getBaseShotsPerTurn() {
        return baseShotsPerTurn;
    }

    public float getBaseRange() {
        return baseRange;
    }

    public int getBaseDmgLevel() {
        return baseDmgLevel;
    }
}
