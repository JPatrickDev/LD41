package me.jack.ld41.Tower;

import me.jack.ld41.Weapon.Common.WeaponGroup;
import me.jack.ld41.Weapon.Weapons.AdvancedTurret;
import me.jack.ld41.Weapon.Weapons.BasicTurret;
import me.jack.ld41.Weapon.Weapons.Weapon;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 21/04/2018.
 */
public class TowerOne extends Tower {

    public TowerOne(int x, int y, int fireRateLevel, int shotsPerTurnLevel, int rangeLevel, int dmgLevel) {
        super(x, y, 1, 1,new TowerUpgrades(50,50,1,1,45 * (rangeLevel+1),45,1,1),0,8f,fireRateLevel,shotsPerTurnLevel,rangeLevel,dmgLevel);
    }

    @Override
    public void setUpWeapons() throws SlickException {
        if(getDmgLevel() >= 2){
            WeaponGroup group = new WeaponGroup(0, 0, getWidth(), getHeight());
            Weapon turret = new AdvancedTurret(getWidth() / 2 - 8, getHeight() / 2 - 8);
            group.addWeaponPart(turret);
            this.addWeaponGroup(group);
        }else {
            WeaponGroup group = new WeaponGroup(0, 0, getWidth(), getHeight());
            Weapon turret = new BasicTurret(getWidth() / 2 - 8, getHeight() / 2 - 8);
            group.addWeaponPart(turret);
            this.addWeaponGroup(group);
        }
    }

    @Override
    public void render(Graphics g,boolean range) {
        g.drawImage(Tower.sheet.getSprite(0,0),0,0);
        super.render(g,range);
    }


    @Override
    public Tower copy() {
        return new TowerOne(getX(), getY(),getFireRateLevel(),getShotsPerTurnLevel(),getRangeLevel(),getDmgLevel());
    }
}
