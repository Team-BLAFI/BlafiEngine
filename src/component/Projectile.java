package component;
import util.Transform;


public class Projectile {
	public Transform body;
	public BulletType type;
	
	public double baseCooldown;
	public double baseFlightSpeed;
	public double baseDamage;
	
	
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
	
	
	
}
