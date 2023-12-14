package weapons;

import entity.Entity;
import util.*;
import entity.player.Player;

import java.awt.*;


public class WeaponPickup extends Entity {

    public Weapon thisWep;

    public Player p;

    public boolean canBePickedUp = false;

    public WeaponPickup(Vector2D location, Weapon weapon, Player p) {
        transform = new Transform();
        transform.setSize(20, 20);
        transform.setPosition(location);
        thisWep = weapon;
        this.p = p;
    }
    public WeaponPickup(double x, double y, Weapon weapon, Player p) {
        this(new Vector2D(x, y),weapon,p);
    }

    public Weapon getWeapon() {
        return thisWep;
    }

    public void update(double deltaTime) {
        if (transform.getAsCollider().overlaps(p.transform.getAsCollider())) {

            this.canBePickedUp = true;
        } else {
            this.canBePickedUp = false;
        }
    }

    public void draw(Graphics g, Vector2D camera) {
        int x = (int)(transform.getX() - camera.getX());
        int y = (int)(transform.getY() - camera.getY());

        g.setColor(Color.blue);
        g.fillRect((int) x, (int) y, (int) transform.getWidth(), (int) transform.getHeight());
        g.setColor(Color.RED);
        g.drawRect(x,y,(int)transform.getWidth(),(int)transform.getHeight());

    }

}