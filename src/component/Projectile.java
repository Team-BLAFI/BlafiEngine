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
	
	public int maxHits;
	//either time or distance to determine when bullet is destroyed



	private double lifeTime;
	private boolean toBeDestroy;

	private ArrayList<Entity> hit = new ArrayList<>();
	
	
	public enum BulletType {
		Standard, Slow, Piercing, Ricochet
	}
	public Projectile(int x, int y, Vector2D travDir, double lifeTime) {
		transform = new Transform(x, y, unit/2, unit/2);
		this.travelDirection = travDir;
		this.lifeTime = lifeTime;
		this.toBeDestroy = false;
		setType(BulletType.Standard);
	}
	public Projectile(BulletType type) {
		setType(type);
	}
	
	public void setType(BulletType type) {
		this.type = type;
		switch (type) {
		case Standard:
			this.baseDamage = 30;
			this.baseFlightSpeed = 70;
			this.maxHits = 1;
			break;
		case Slow:
			this.baseDamage = 40;
			this.baseFlightSpeed = 30;
			this.maxHits = 1;
			break;
		case Piercing:
			this.baseDamage = 20;
			this.baseFlightSpeed = 40;
			this.maxHits = 5;
			break;
		case Ricochet:
			this.baseDamage = 20;
			this.baseFlightSpeed = 40;
			this.maxHits = 3;
			break;
		default:
			setType(BulletType.Standard);
			break;
			
		}
	}

	
	public void onHit(Enemy e) {
		e.health.takeDamage(this.baseDamage);
		hit.add(e);
		maxHits -=1;
		if (maxHits <= 0) {
			toBeDestroy = true;
		}
		if (type == BulletType.Ricochet) {
			Bounce();
		}
	}
	
	public void Bounce() {
		this.baseDamage +=20;
		this.lifeTime += 50;
		//change travel direction
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

				Collider bulletC = this.transform.getAsCollider();
				Collider enemyC = e.transform.getAsCollider();

				if (bulletC.overlaps(enemyC) && !hit.contains(e)){
					onHit(e);
				}
			}
		}

		Vector2D moveVector = new Vector2D(travelDirection.getX(), travelDirection.getY());
		moveVector.multiply(unit * baseFlightSpeed * deltaTime);

		transform.movePositionBy( moveVector );

	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(
				(int) transform.getX(),
				(int) transform.getY(),
				(int) transform.getWidth(),
				(int) transform.getHeight()
		);
	}


}
