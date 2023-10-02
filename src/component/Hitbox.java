package component;

import util.Rect;

import java.awt.*;

public class Hitbox extends Component{

    public Rect HitboxBounds;

    public Hitbox(Rect rect){
        this.HitboxBounds = rect;
    }

    public Hitbox(int x, int y, int w, int h){
        this.HitboxBounds = new Rect(x,y,w,h);
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
}
