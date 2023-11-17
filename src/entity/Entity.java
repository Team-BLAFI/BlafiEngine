package entity;

<<<<<<< HEAD
=======
import component.Collider;
>>>>>>> enemy
import component.Health;
import util.Transform;
import window.scenes.GameScene;

import java.awt.*;

public abstract class Entity {

    public Transform transform;
<<<<<<< HEAD
    protected Health health = null;

=======
    public Collider collider;
    protected Health health = null;


>>>>>>> enemy
    public abstract void update(double dt);

    public abstract void draw(Graphics g);

    public void destroy() {
        GameScene.enemies.remove(this);
    }
}
