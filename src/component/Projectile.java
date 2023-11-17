package component;

import java.awt.*;
import java.util.ArrayList;

import entity.enemy.Enemy;
import entity.player.PlayerConstants;
import util.Transform;
import util.*;
import entity.*;
import window.WindowConstants;
import window.scenes.GameScene;


public class Projectile extends Component{
	public Transform transform = new Transform();
	public BulletType type;
	public int arrayPos;
	private Vector2D travelDirection;
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



	private double lifeTime;
	private boolean toBeDestroy;

	private ArrayList<Entity> hit = new ArrayList<>();
	
	
	public enum BulletType {
		Standard, Ricochet
	}
	public Projectile(int x, int y, Vector2D travDir, double lifeTime) {
		transform = new Transform(x, y, unit, unit);
		this.travelDirection = travDir;
		this.lifeTime = lifeTime;
		this.toBeDestroy = false;
	}
	public Projectile(BulletType type) {
		setType(type);
	}
	
	public void setType(BulletType type) {
		this.type = type;
		switch (type) {
		case Standard:
			transform = new Transform(0, 0, 40, 40);
				
		default:
			break;
			
		}
	}
	
	public void setPos(double x, double y) {
		transform.position.x = x;
		transform.position.y = y;
	}
	
	public void onHit(Collider hit) {
		
	}

	public boolean getToBeDestroy(){
		return  toBeDestroy;
	}

	@Override
	public void update(double deltaTime) {

		lifeTime -=deltaTime;
		if (lifeTime <= 0){
			toBeDestroy = true;
		}


		if(!GameScene.enemies.isEmpty()){
			for (Enemy e : GameScene.enemies) {
				Collider bullet = new Collider(
						(int) transform.position.x,
						(int) transform.position.y,
						(int) transform.size.x,
						(int) transform.size.y
				);

				Collider enemy = new Collider(
						(int) e.transform.position.x,
						(int) e.transform.position.y,
						(int) e.transform.size.x,
						(int) e.transform.size.y
				);
				if (bullet.overlaps(enemy) && !hit.contains(e)){

					e.health.takeDamage(30);
					hit.add(e);
					toBeDestroy = true;
				}
			}


		}


		transform.position.x += travelDirection.x * unit * 50 * deltaTime;
		transform.position.y += travelDirection.y * unit * 50 * deltaTime;

	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) transform.position.x,(int) transform.position.y, (int) unit, (int) unit);
	}

	@Override
	public void init() {

	}


}
