package weapons;

import component.Projectile;
import entity.Entity;
import util.Vector2D;

public class Shotgun extends Weapon {

    int pellets;

    public Shotgun(Entity owner, double dmg, double fireRate, double reloadCooldown, int magSize, double lifeTime, int pellets) {
        super(owner,dmg,fireRate,reloadCooldown,magSize,lifeTime);
        this.pellets = pellets;
    }

    public Shotgun(Entity owner) {
        super(owner, 10, 0.3, 0.2, 4, 3);
        this.pellets = 6;

    }

    @Override
    public void shoot(double x, double y) {
        if(activeRC>0){
            return;
        }
        if (fireCD <=0 && currentMag > 0) {
            // shoot bullet
            Vector2D origin = new Vector2D();
            Vector2D destination = new Vector2D(x,y);

            double totalArc = 60f;
            double arcOffset = 60f/pellets;

            destination.rotate(-totalArc/2,origin);

            for (int pNumber = 0; pNumber < pellets; pNumber++){

                Vector2D bulletTravelDirection = origin.getVectorTo(destination);
                bulletTravelDirection.normalize();

                liveProjectiles.add(new Projectile(
                        (int) (origin.getX()),
                        (int) (origin.getY()),
                        bulletTravelDirection,
                        this.lifeTime)
                );

                destination.rotate(arcOffset,origin);
            }

            fireCD = fireRate;
            currentMag--;
        } else if (currentMag == 0){
            reload();
        }
    }

        public void shootT(Vector2D travelDirection) {
        if(activeRC>0){
            return;
        }
        if (fireCD <=0 && currentMag > 0) {
            // shoot bullet
            double totalArc = 60f;
            double arcOffset = 60f/pellets;

            travelDirection.rotate(-totalArc/2,new Vector2D());

            for (int pNumber = 0; pNumber < pellets; pNumber++){


                liveProjectiles.add(new Projectile(
                        (int) (owner.transform.getCenterX()),
                        (int) (owner.transform.getCenterY()),
                        travelDirection,
                        this.lifeTime)
                );

                travelDirection.rotate(arcOffset,new Vector2D());
            }

            fireCD = fireRate;
            currentMag--;
        } else if (currentMag == 0){
            reload();
        }
    }
}
