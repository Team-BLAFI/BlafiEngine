package component;

import entity.*;
public class WeaponPresets {
    public WeaponPresets() {
    }

    public Weapon createDefault(Entity owner) {
        System.out.println("creating default weapon");
        return new Weapon(owner, 30, 0.1,
                2,100,100);


    }











}
