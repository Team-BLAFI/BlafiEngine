package component;

import java.awt.*;
import util.Transform;
import util.*;
import entity.player.*;
import entity.*;
import window.WindowConstants;


public class Projectile extends Component{
	public Transform body = new Transform();
	public BulletType type;
	public int arrayPos;
	public Vector2D aimingTo;
	public Entity owner;

	double unit = WindowConstants.SCREEN_UNIT;
	
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
	public Projectile(int x, int y, Vector2D vel) {
		body = new Transform(x, y, unit, unit);
		aimingTo = vel;
	}
	public Projectile(BulletType type) {
		setType(type);
	}
	
	public void setType(BulletType type) {
		this.type = type;
		switch (type) {
		case Standard:
			body = new Transform(0, 0, 40, 40);
				
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

	@Override
	public void update(double deltaTime) {
		// FIXME Need to work on math for bullet travel
		body.position.add(aimingTo.multiply(deltaTime));
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)body.position.x,(int)body.position.y, (int) unit, (int) unit);
	}

	@Override
	public void init() {

	}


}
