package util;

import entity.*;
import component.Projectile;
import component.Projectile.BulletType;

import java.awt.*;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map.Entry;

public class Shooting {
	public Entity shooter;
	public int cooldown;
	public Vector2D aimingTo;
	public HashMap<Integer, Projectile> allBullets;
	public int numBullets;
	public boolean bulletExists;
	public Projectile currentBullet;
	
	
	public Shooting(Entity shooter) {
		this.shooter = shooter;
		//this.cooldown = shooter.baseCooldown
		bulletExists = false;
		allBullets = new HashMap<>();
		numBullets = 0;
		cooldown = 1; // base cooldown of entity 
	}
	public void setAim(Vector2D mousePos, Projectile bullet) {
		bullet.aimingTo = mousePos;
	}
	
	public Projectile createBullet() {
		Projectile thisBullet = new Projectile();
		allBullets.put(numBullets, currentBullet);
		numBullets += 1;
//		currentBullet.owner = shooter;
		return thisBullet;
	}
	
	public void shoot(BulletType type, Vector2D mousePos) {
		currentBullet = createBullet();
		currentBullet.setPos(shooter.transform.position.x, shooter.transform.position.y);
		setAim(mousePos, currentBullet);
		
		
	}
	
	public void eraseProjectile(Projectile bullet) { 
		
	}
	
	public void moveProjectile(Projectile bullet, Vector2D direction, double deltaTime) {
		bullet.body.position.x += direction.x * deltaTime;
		bullet.body.position.y += direction.y * deltaTime;
	}
	
	public void update(double deltaTime) {
			for(Entry<Integer, Projectile> set: allBullets.entrySet()) {
				Projectile bullet = set.getValue();
				moveProjectile(bullet, bullet.aimingTo, deltaTime);
			
		}
		//make array of bullets and loop through them
	}
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)currentBullet.body.position.x, (int) currentBullet.body.position.y, (int) currentBullet.body.size.x, (int) currentBullet.body.size.y);
	}

	
	
}
