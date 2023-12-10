package weapons;

import entity.Entity;
import util.*;
import entity.player.Player;

import java.awt.*;


public class WeaponPickup extends Entity {
    public Transform pickup = new Transform();

    public Weapon thisWep;

    public Player p;

    public boolean isPickedUp = false;

    public WeaponPickup(Vector2D location, Weapon weapon, Player p) {
        pickup.setSize(20, 20);
        pickup.setPosition(location);
        collider = pickup.getAsCollider();
        thisWep = weapon;
        this.p = p;
    }
    public WeaponPickup(double x, double y, Weapon weapon, Player p) {
        this(new Vector2D(x, y),weapon,p);
    }

    public Weapon giveWeapon() {
        return thisWep;
    }

    public void update(double deltaTime) {
        if (collider.overlaps(p.collider)) {
            p.addNewWeapon(giveWeapon());
            this.isPickedUp = true;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int) pickup.getX(), (int) pickup.getY(), (int) pickup.getWidth(), (int) pickup.getHeight());
        g.setColor(Color.RED);
        g.drawRect(collider.Bounds.x,collider.Bounds.y,collider.Bounds.w,collider.Bounds.h);
    }








}