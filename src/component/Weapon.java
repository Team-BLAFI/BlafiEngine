package component;

import entity.Entity;
import util.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class Weapon extends Component{
    Entity owner;
    Projectile bullet;

    double dmg;
    double fireRate;
    double fireCD = 0;
    double reloadCooldown;
    double activeRC = -0.01;
    int magSize;
    int currentMag;
    ArrayList<Projectile> liveProjectiles = new ArrayList<>();
    double lifeTime;

    public Weapon(Entity owner, double dmg, double fireRate, double reloadCooldown, int magSize, double lifeTime) {
        this.owner = owner;
        this.dmg = dmg;
        this.fireRate = fireRate;
        this.reloadCooldown = reloadCooldown;
        this.magSize = magSize;
        this.currentMag = magSize;
        this.lifeTime = lifeTime;
    }

    public void shoot(double x, double y) {
        if(activeRC>0){
            return;
        }
        if (fireCD <=0) {
            // shoot bullet
            Vector2D v = new Vector2D(x,y);
            v = owner.transform.position.getVectorTo(v);

            liveProjectiles.add(new Projectile(
                    (int) owner.transform.position.x,
                    (int) owner.transform.position.y,
                    v)
            );
            fireCD = fireRate;
            currentMag--;
        } else {
            reload();
        }
    }

    public void reload() {
        // Put weapon into cooldown state
        activeRC = reloadCooldown;
        currentMag = magSize;
    }

    @Override
    public void update(double deltaTime) {
        fireCD -= deltaTime;
        activeRC -= deltaTime;
        System.out.println(fireCD);
        for (Projectile p:liveProjectiles) {
            p.update(deltaTime);
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Projectile p:liveProjectiles) {
            p.draw(g);
        }
    }

    @Override
    public void init() {

    }
}
