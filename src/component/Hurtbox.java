package component;

import util.Rect;

import java.awt.*;

public class Hurtbox extends Component {

    public Rect hurtboxBounds;

    public Hurtbox(Rect rect){
        this.hurtboxBounds = rect;
    }

    public Hurtbox(int x, int y, int w, int h){
        this.hurtboxBounds = new Rect(x,y,w,h);
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

    public boolean overlaps(Rect hitbox)
    {
        return (hurtboxBounds.x     <= hitbox.x + hitbox.w) &&
                (hurtboxBounds.x + hurtboxBounds.w >= hitbox.x) &&
                (hurtboxBounds.y     <= hitbox.y + hitbox.h) &&
                (hurtboxBounds.y + hurtboxBounds.h >= hitbox.y);
    }

}
