package component;

import java.awt.*;
import util.Transform;
import util.*;
import entity.player.*;
import entity.*;


public class Projectile {
	public Transform body;
	public BulletType type;
	public int arrayPos;
	public Vector2D aimingTo;
	public Entity owner;
	
	public double baseCooldown;
	public double baseFlightSpeed;
	public double baseDamage;
	public double maxFlightTime;  
	public double currentFlightTime;
	public double maxFlightDistance;
	public double currentFlightDistance;
	//either time or distance to determine when bullet is destroyed
	
	
	public enum BulletType {
		Standard, Ricochet
	}
	public Projectile() {
		
	}
	public Projectile(BulletType type) {
		setType(type);
		
	}
	
	public void setType(BulletType type) {
		this.type = type;
		switch (type) {
		case Standard:
			body = new Transform(0, 0, 4, 4);
				
		default:
			break;
			
		}
	}
	
	public void setPos(double x, double y) {
		body.position.x = x;
		body.position.y = y;
	}
	
	public void onHit(Collider hit) {
		
	}
	
	public void draw(Graphics g) {
		
	}
	
	
}
