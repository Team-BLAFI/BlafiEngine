package entity;

import component.Health;
import util.Transform;
import window.scenes.GameScene;

import java.awt.*;

public abstract class Entity {

    public Transform transform;
    protected Health health = null;

    public abstract void update(double dt);

    public abstract void draw(Graphics g);

    public void destroy() {
        GameScene.enemies.remove(this);
    }
}
