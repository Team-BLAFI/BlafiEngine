package component;

import entity.*;
import util.Vector2D;
import java.lang.Math;
import component.Weapon;

public class WeaponPresets {
    public WeaponPresets() {
    }

    public Weapon createDefault(Entity owner) {
        System.out.println("creating default weapon");
        return new Weapon(owner, 30, 0.1,
                2,100,100);


    }

    public Weapon createShotgun(Entity owner) {
        System.out.println("creating shotgun");


        Weapon shotgun = new Weapon(owner, 30, 1,
                2, 30, 100) {
            @Override
            public void shoot(double x, double y) {

                if(activeRC>0){
                    return;
                }
                if (fireCD <=0 && currentMag > 0) {
                    // shoot bullet
                    Vector2D v = new Vector2D(x, y);
                    Vector2D travelDirection = owner.transform.position.getVectorTo(v);
                    travelDirection.x = xRotate(travelDirection.x, travelDirection.y, 30);
                    travelDirection.y = yRotate(travelDirection.x, travelDirection.y, 30);

                    liveProjectiles.add(new Projectile(
                            (int) (owner.transform.position.x + owner.transform.size.x / 2),
                            (int) (owner.transform.position.y + owner.transform.size.y / 2),
                            travelDirection,
                            this.lifeTime)
                    );

                    fireCD = fireRate;
                    currentMag--;
                }else if (currentMag == 0) {
                    reload();
                }
            }

        };
        return shotgun;
    }
 /*   public double dotProduct(Vector2D vec1, Vector2D vec2) {
        return (vec1.x * vec2.x) + (vec1.y * vec2.y);
    }
    public Vector2D getVectorFromAngle(Vector2D initVec, double angle) {

    }*/
    public double xRotate(double x, double y, double angle) {
        return (x * java.lang.Math.cos(angle)) - (y * java.lang.Math.sin(angle));
    }
    public double yRotate(double x, double y, double angle) {
        return (x * java.lang.Math.sin(angle)) + (y * java.lang.Math.cos(angle));
    }






}
