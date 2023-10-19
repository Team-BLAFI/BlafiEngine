package entity;

import util.Transform;

import java.awt.*;

public abstract class Entity {

    public Transform transform;

    public abstract void update(double dt);

    public abstract void draw(Graphics g);

}
