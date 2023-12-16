package component;

import util.Vector2D;

import java.awt.*;

public abstract class Component {

    public abstract void update(double deltaTime);
    public abstract void draw(Graphics g, Vector2D camera);

}
