package util;

import entity.*;
import component.Projectile;
import component.Projectile.BulletType;

import java.awt.*;


public class Shooting {
	public Entity shooter;
	public int cooldown = 1;
	public Vector2D aimingTo;
	public Projectile bullet;
	public boolean bulletExists;
	
	public Shooting(Entity shooter) {
		this.shooter = shooter;
		//this.cooldown = shooter.baseCooldown
		bulletExists = false;
		
	}
	public void setAim(Vector2D mousePos) {
		aimingTo = mousePos;
	}
	
	public void shoot(BulletType type) {
		bullet = new Projectile(BulletType.Standard);
		bulletExists = true;
		bullet.setPos(shooter.transform.position.x, shooter.transform.position.y);
		aimingTo.normalize();
		
		
	}
	public void moveProjectile(Projectile bullet, Vector2D direction, double deltaTime) {
		bullet.body.position.x += direction.x * deltaTime;
		bullet.body.position.y += direction.y * deltaTime;
	}
	
	public void update(double deltaTime) {
		if (bulletExists) moveProjectile(bullet, aimingTo, deltaTime);
		//make array of bullets and loop through them
	}
	public void draw(Graphics g) { 
		g.fillRect((int)bullet.body.position.x, (int) bullet.body.position.y, (int) bullet.body.size.x, (int) bullet.body.size.y);
	}
	
	
	
}
