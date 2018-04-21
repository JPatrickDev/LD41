package me.jack.ld41.Tower.Upgrades;

import me.jack.ld41.State.InGameState;
import me.jack.ld41.Tower.Tower;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 21/04/2018.
 */
public class ShotsPerTurn extends Upgrade {


    public ShotsPerTurn(int level) throws SlickException {
        super("Shots Per Turn Upgrade", "SPT upgrades really help against fast enemies.", 1f, level, 0,20);
    }

    @Override
    public ShotsPerTurn getNextForTower(Tower t) {

        try {
            return new ShotsPerTurn(t.getShotsPerTurnLevel() + 1);
        } catch (SlickException e) {
            e.printStackTrace();
        }catch (RuntimeException e1){
            try {
                return new ShotsPerTurn(3);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void use(Tower currentlySelected, InGameState parent) {
        currentlySelected.getUpgrades().setShotsPerTurn(currentlySelected.getUpgrades().getBaseShotsPerTurn() * (this.getLevel() + 1));
        currentlySelected.setShotsPerTurnLevel((currentlySelected.getShotsPerTurnLevel() + 1));
        for(Tower tower : parent.towers){
            if(tower.getClass().isInstance(currentlySelected)){
                System.out.println("True");
                tower.getUpgrades().setShotsPerTurn(currentlySelected.getUpgrades().getBaseShotsPerTurn() * (this.getLevel() + 1));
                tower.setShotsPerTurnLevel((currentlySelected.getShotsPerTurnLevel()));
            }
        }
    }
}
