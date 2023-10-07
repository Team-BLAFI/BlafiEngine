package blafi.collision;

import util.Rect;

import java.awt.*;

public class Projectile extends Hitbox{
	public Double speed;
	public Rect size;
	public String type;
	
	public Projectile(String type) {
		switch (type) {
			case "standard": 
				this.Bounds = (1,1,1,1);
				this.type = type;
				break;
			default:
				System.out.println("invalid type");
				break;
			
		}
	}
	
	public void shoot(String type) {
		switch(type) {
			case "standard":
				//move
				break;
			default:
				System.out.println("invalid type");
				break;
		}
	}
	
}
