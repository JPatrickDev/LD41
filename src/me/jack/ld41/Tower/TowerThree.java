package me.jack.ld41.Tower;

import me.jack.ld41.Weapon.Common.WeaponGroup;
import me.jack.ld41.Weapon.Weapons.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 21/04/2018.
 */
public class TowerThree extends Tower {

    public TowerThree(int x, int y, int fireRateLevel, int shotsPerTurnLevel, int rangeLevel, int dmgLevel) {
        super(x, y, 1, 1,new TowerUpgrades(50,50,2,2,80 * (rangeLevel+1),80,1,1),0,50f,fireRateLevel,shotsPerTurnLevel,rangeLevel,dmgLevel);
    }

    @Override
    public void setUpWeapons() throws SlickException {
        if(getDmgLevel() >= 2){
            WeaponGroup group = new WeaponGroup(0, 0, getWidth(), getHeight());
            Weapon turret = new AdvancedMissleLauncher(getWidth() / 2 - 8, getHeight() / 2 - 8);
            group.addWeaponPart(turret);
            this.addWeaponGroup(group);
        }else {
            WeaponGroup group = new WeaponGroup(0, 0, getWidth(), getHeight());
            Weapon turret = new BasicMissleLauncher(getWidth() / 2 - 8, getHeight() / 2 - 8);
            group.addWeaponPart(turret);
            this.addWeaponGroup(group);
        }
    }

    @Override
    public void render(Graphics g,boolean range) {
        g.drawImage(Tower.sheet.getSprite(0,1),0,0);
        super.render(g,range);
    }


    @Override
    public Tower copy() {
        return new TowerThree(getX(), getY(),getFireRateLevel(),getShotsPerTurnLevel(),getRangeLevel(),getDmgLevel());
    }
}
