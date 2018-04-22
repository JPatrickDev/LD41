package me.jack.ld41.Weapon.Common;

import me.jack.ld41.Weapon.Weapons.Weapon;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by Jack on 22/04/2018.
 */
public class WeaponGroup {

    private ArrayList<WeaponGroup> children = new ArrayList<WeaponGroup>();
    private ArrayList<Weapon> parts = new ArrayList<>();
    int x, y, w, h;


    public WeaponGroup(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void render(Graphics g) {
        for (Weapon w : parts) {
            g.translate(w.getX(), w.getY());
            w.render(g);
            g.translate(-w.getX(), -w.getY());
        }

        for (WeaponGroup c : children) {
            g.translate(c.getX(), c.getY());
            c.render(g);
            g.translate(-c.getX(), -c.getY());
        }
    }

    public void addWeaponGroup(WeaponGroup group) {
        this.children.add(group);
    }

    public void addWeaponPart(Weapon weapon) {
        this.parts.add(weapon);
    }

    public ArrayList<WeaponGroup> getChildren() {
        return children;
    }

    public ArrayList<Weapon> getParts() {
        return parts;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Weapon> getAllWeapons() {
        ArrayList<Weapon> output = new ArrayList<>();
        output.addAll(parts);
        for(WeaponGroup g : children){
            output.addAll(g.getAllWeapons());
        }
        return output;
    }
}
