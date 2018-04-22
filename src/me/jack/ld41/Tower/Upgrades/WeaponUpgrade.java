package me.jack.ld41.Tower.Upgrades;

import me.jack.ld41.State.InGameState;
import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 21/04/2018.
 */
public class WeaponUpgrade extends Upgrade {


    public WeaponUpgrade(int level) throws SlickException {
        super("Weapon Upgrade", "More firepower can't hurt..", 1f, level, 2,25);
    }

    @Override
    public WeaponUpgrade getNextForTower(Tower t) {

        try {
            return new WeaponUpgrade(t.getRangeLevel() + 1);
        } catch (SlickException e) {
            e.printStackTrace();
        }catch (RuntimeException e1){
            try {
                return new WeaponUpgrade(3);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void use(Tower currentlySelected, InGameState parent) {
        currentlySelected.getUpgrades().setRange(currentlySelected.getUpgrades().getBaseRange() * (this.getLevel() + 1));
        currentlySelected.setRangeLevel((currentlySelected.getRangeLevel() + 1));
        for(Tower tower : parent.towers){
            if(tower.getClass().isInstance(currentlySelected)){
                System.out.println("True");
                tower.getUpgrades().setRange(currentlySelected.getUpgrades().getBaseRange() * (this.getLevel() + 1));
                tower.setRangeLevel((currentlySelected.getRangeLevel()));
            }
        }
    }
}
