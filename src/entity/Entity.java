package entity;

import util.Transform;

import java.awt.*;

public abstract class Entity {

    protected Transform transform;

    public abstract void update(double dt);

    public abstract void draw(Graphics g);

}
