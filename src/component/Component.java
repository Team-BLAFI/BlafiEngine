package component;

import java.awt.*;

public abstract class Component {

    public abstract void init();
    public abstract void update(double deltaTime);

    public abstract void draw(Graphics g);
}
