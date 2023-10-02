package component;

import util.Rect;

import java.awt.*;

public class Hitbox extends Component{

    public Rect hitboxBounds;

    public Hitbox(Rect rect){
        this.hitboxBounds = rect;
    }

    public Hitbox(int x, int y, int w, int h){
        this.hitboxBounds = new Rect(x,y,w,h);
    }



    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void init() {

    }

    public boolean overlaps(Rect hurtbox)
    {
        return (hitboxBounds.x     <= hurtbox.x + hurtbox.w) &&
                (hitboxBounds.x + hitboxBounds.w >= hurtbox.x) &&
                (hitboxBounds.y     <= hurtbox.y + hurtbox.h) &&
                (hitboxBounds.y + hitboxBounds.h >= hurtbox.y);
    }

}
