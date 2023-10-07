package window.scenes;

import java.awt.Graphics;

public abstract class Scene {

    /**
     * <p>
     * Calls the Update Methods for the scenes components
     *</p>
     * @param deltaTime util.Time since last frame
     */
    public abstract void update(double deltaTime);

    /**
     * <p>
     * Calls the Draw Methods for the scenes components
     *</p>
     * @param g Graphics object that will be used to draw the component
     */
    public abstract void draw(Graphics g);
}
