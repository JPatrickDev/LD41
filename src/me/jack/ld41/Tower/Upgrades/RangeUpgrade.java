package me.jack.ld41.Tower.Upgrades;

import me.jack.ld41.State.InGameState;
import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 21/04/2018.
 */
public class RangeUpgrade extends Upgrade {


    public RangeUpgrade(int level) throws SlickException {
        super("Range\nUpgrade", "Hit Targets Further Away!.", 1f, level, 1,25);
        if(level >= 3){
            this.validUpgrade = false;
        }
    }

    @Override
    public RangeUpgrade getNextForTower(Tower t) {

        try {
            return new RangeUpgrade(t.getRangeLevel() + 1);
        } catch (SlickException e) {
            e.printStackTrace();
        }catch (RuntimeException e1){
            try {
                return new RangeUpgrade(3);
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
