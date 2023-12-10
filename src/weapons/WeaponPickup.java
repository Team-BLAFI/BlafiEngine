package weapons;

import entity.player.PlayerConstants;
import util.*;
import component.Collider;
import entity.player.Player;

import java.awt.*;


public class WeaponPickup {
    public Transform pickup;

    public Collider collider;
    public Weapon thisWep;

    public Player p;

    public WeaponPickup(Vector2D location, Weapon weapon, Player p) {
        pickup.setSize(20, 20);
        pickup.setPosition(location);
        collider = pickup.getAsCollider();
        thisWep = weapon;
        this.p = p;
    }
    public WeaponPickup(double x, double y, Weapon weapon, Player p) {
        Vector2D vec = new Vector2D(x, y);
        new WeaponPickup(vec, weapon, p);
    }

    public Weapon giveWeapon() {
        return thisWep;
    }

    public void update(double deltaTime) {
        if (collider.overlaps(p.collider)) {
            p.addNewWeapon(giveWeapon());
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int) pickup.getX(), (int) pickup.getY(), (int) pickup.getWidth(), (int) gpickup.getHeight());
        g.setColor(Color.RED);
        g.drawRect(collider.Bounds.x,collider.Bounds.y,collider.Bounds.w,collider.Bounds.h);
    }








}