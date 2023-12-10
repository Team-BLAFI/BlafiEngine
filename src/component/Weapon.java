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

    @Override
    public String toString() {
        if(activeRC < 0){
            return String.format("%d/%s",
                    currentMag, magSize);
        }
        return "reloading...";


    }

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
        if (fireCD <=0 && currentMag > 0) {
            // shoot bullet

            Vector2D origin = new Vector2D(owner.transform.getCenterX(),owner.transform.getCenterY());
            Vector2D destination = new Vector2D(x,y);

            Vector2D bulletTravelDirection = origin.getVectorTo(destination);


            liveProjectiles.add(new Projectile(
                    (int) (origin.getX()),
                    (int) (origin.getY()),
                    bulletTravelDirection,
                    this.lifeTime)
            );
            fireCD = fireRate;
            currentMag--;
        } else if (currentMag == 0){
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
        for (int i =0; i < liveProjectiles.size(); i++) {

            if (liveProjectiles.get(i) == null){
                continue;
            }
            if (liveProjectiles.get(i).getToBeDestroy()){
                liveProjectiles.remove(i);
                continue;
            }
            liveProjectiles.get(i).update(deltaTime);

        }

    }

    @Override
    public void draw(Graphics g) {
        for (Projectile p:liveProjectiles) {
            p.draw(g);
        }
    }

}

//specific fire rates based on bullet(add weapon entity for projectile class, to edit weapon properties on projectile creation?)
//refactor "projectile type" into broader set of properties that can be combined
//




