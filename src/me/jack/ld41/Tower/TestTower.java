package me.jack.ld41.Tower;

import me.jack.ld41.Level.Level;
import me.jack.ld41.Level.Tile.Tile;
import me.jack.ld41.Weapon.Common.WeaponGroup;
import me.jack.ld41.Weapon.Weapons.BasicTurret;
import me.jack.ld41.Weapon.Weapons.Weapon;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 21/04/2018.
 */
public class TestTower extends Tower {

    public TestTower(int x, int y,int fireRateLevel, int shotsPerTurnLevel,int rangeLevel, int dmgLevel) {
        super(x, y, 1, 1,new TowerUpgrades(20,50,1,1,40,40,1,1),0,8f,fireRateLevel,shotsPerTurnLevel,rangeLevel,dmgLevel);
    }

    @Override
    public void setUpWeapons() throws SlickException {
        WeaponGroup group = new WeaponGroup(0,0,getWidth(),getHeight());
        Weapon turret = new BasicTurret(getWidth()/2 - 8,getHeight()/2 - 8);
        group.addWeaponPart(turret);
        this.addWeaponGroup(group);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Tower.sheet.getSprite(0,0),0,0);
        super.render(g);
    }


    @Override
    public Tower copy() {
        return new TestTower(getX(), getY(),getFireRateLevel(),getShotsPerTurnLevel(),getRangeLevel(),getDmgLevel());
    }
}
