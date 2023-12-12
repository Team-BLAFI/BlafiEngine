package weapons;

import component.Projectile;
import entity.Entity;
import util.Vector2D;

public class Pistol extends Weapon {


    public Pistol(Entity owner, double dmg, double fireRate, double reloadCooldown, int magSize, double lifeTime) {
        super(owner,dmg,fireRate,reloadCooldown,magSize,lifeTime);
    }

    public Pistol(Entity owner) {
        super(owner, 10, 0.3, 0.2, 8, 3);
    }
}
