package map2;

import component.Collider;
import entity.Entity;
import util.Rect;
import util.Transform;
import window.WindowConstants;

public class Camera{
    public Transform transform;
    Entity player;
    public Camera(Entity ow){
        transform = new Transform();
        this.player = ow;
        transform.setWidth(WindowConstants.SCREEN_WIDTH);
        transform.setHeight(WindowConstants.SCREEN_HEIGHT);

    }
    public boolean isInFrame(Collider collider){
        Collider c = transform.getAsCollider();
        return c.overlaps(collider);
    }
    public void update(double dt){
        setPosition(player.transform);
    }
    public void setPosition(Transform origin){
        transform.setX(origin.getCenterX() - transform.getWidth()/2);
        transform.setY(origin.getCenterY() - transform.getHeight()/2);
    }
}
