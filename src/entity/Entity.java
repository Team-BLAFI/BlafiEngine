package entity;

import component.Collider;
import component.Health;
import util.Transform;
import window.scenes.GameScene;

import java.awt.*;

public abstract class Entity {

    public Transform transform;
    public Collider collider;
    public Health health = null;
    private boolean toBeDestroy = false;

    public boolean isToBeDestroy(){
        return  toBeDestroy;
    }


    public abstract void update(double dt);

    public abstract void draw(Graphics g);

    public void destroy() {
        toBeDestroy = true;
    }
}