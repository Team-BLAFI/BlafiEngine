package component;

import java.awt.*;
import java.util.ArrayList;

import entity.enemy.Enemy;
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
	public double baseDamage = 10;
	public double maxFlightTime;  
	public double currentFlightTime;
	
	public int maxHits;

	//either time or distance to determine when bullet is destroyed
	private double lifeTime;
	private boolean toBeDestroy;

	private ArrayList<Entity> hit = new ArrayList<>();
	public Collider overlappedCollider;
	public double prevX;
	public double prevY;
	public Boolean isActive;
	public double timeInactive;
	public BulletSpeed bulletSpeed;
	public Ricochet ricochet;
	public Piercing piercing;
	public Chrono chrono;
	public interface BulletType {}

	public enum BulletSpeed implements BulletType {
		Standard, Slow, Fast
	}
	public void setBaseFlightSpeed() {
		switch (bulletSpeed) {
			case Standard:
				baseFlightSpeed = 60;
				break;
			case Slow:
				baseFlightSpeed = 30;
				break;
			case Fast:
				baseFlightSpeed = 90;
				break;
		}

	}

	public enum Ricochet implements BulletType {
		Standard, StrongerOnBounce
	}
	public void Bounce(int code, Collider overlappedCollider) {
		switch (code) {
			case 1:
				transform.setX(overlappedCollider.Bounds.x + transform.getWidth() + 1);
				travelDirection.setX(-(travelDirection.getX()));
				break;
			case 2:
				transform.setX(overlappedCollider.Bounds.x - 1);
				travelDirection.setX(-(travelDirection.getX()));
				break;
			case 3:
				transform.setY(overlappedCollider.Bounds.y + transform.getHeight() + 1);
				travelDirection.setY(-(travelDirection.getY()));
				break;
			case 4:
				transform.setY(overlappedCollider.Bounds.y - 1);
				travelDirection.setY(-(travelDirection.getY()));
				break;
			default:
				break;
		}
	}

	public Boolean isRightOf(Collider c) {
		if (prevX > c.Bounds.x + c.Bounds.w) {
			System.out.println("bullet was right");
			return true;
		}
		else { return false;}

	}
	public Boolean isLeftOf(Collider c) {
		if (prevX + transform.getWidth() < c.Bounds.x) {
			System.out.println("bullet was left");
			return true;
		}
		else { return false;}
	}
	public Boolean isBelow(Collider c) {
		if (prevY > c.Bounds.y + c.Bounds.h) {
			System.out.println("bullet was below");
			return true;
		}
		else { return false;}
	}
	public Boolean isAbove(Collider c) {
		if (prevY + transform.getHeight() < c.Bounds.y) {
			System.out.println("bullet was above");
			return true;
		}
		else { return false;}
	}
	public enum Piercing implements BulletType {
		Standard, StrongerOnPierce
	}
	public void setMaxHits(int maxHits) {
		this.maxHits = maxHits;
	}
	public enum Chrono implements BulletType {
		ResumeOnWall, ResumeOnButtonPress
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setTypes(BulletType... incomingTypes) {
		for (BulletType type : incomingTypes) {
			if (type instanceof BulletSpeed) {
				System.out.println("bulletspeed is "+ type);
				this.bulletSpeed = (BulletSpeed) type;
				setBaseFlightSpeed();
			}
			if (type instanceof Ricochet) {
				System.out.println("ricochet is "+ type);
				this.ricochet = (Ricochet) type;
				setMaxHits(5);
			}
			if (type instanceof Piercing) {
				System.out.println("piercing is "+ type);
				this.piercing = (Piercing) type;
				setMaxHits(2);
			}
			if (type instanceof Chrono) {
				System.out.println("chrono"+ type);
				this.chrono = (Chrono) type;
				setIsActive(true);
				timeInactive = 0;
			}
		}
	}

	public void setOverlappedTile(Collider c) {
		overlappedCollider = c;
	}
	public Projectile(int x, int y, Vector2D travDir, double lifeTime) {
		transform = new Transform(x, y, unit/2, unit/2);
		this.travelDirection = travDir;
		this.lifeTime = lifeTime;
		this.toBeDestroy = false;
		setTypes(BulletSpeed.Standard, Piercing.Standard, Ricochet.Standard, Chrono.ResumeOnWall
		);

	}


	
	public void onHit(Enemy e) {
		e.health.takeDamage(this.baseDamage);
		hit.add(e);
		maxHits -=1;
		if (maxHits <= 0) {
			toBeDestroy = true;
		}

	}

	public boolean getToBeDestroy(){
		return  toBeDestroy;
	}

	@Override
	public void update(double deltaTime) {
		timeInactive = 0;

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

	public void draw(Graphics g, Vector2D camera) {

		int x = (int)(transform.getX() - camera.getX());
		int y = (int)(transform.getY() - camera.getY());

		g.setColor(Color.RED);
		g.fillRect(
				x,
				y,
				(int) transform.getWidth(),
				(int) transform.getHeight()
		);
	}
}
