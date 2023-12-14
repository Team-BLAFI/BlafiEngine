package component;

import util.Rect;
import util.Vector2D;

import java.awt.*;

public class Hitbox extends Component{

    public Rect Bounds;

    public Hitbox(Rect rect){
        this.Bounds = rect;
    }

    public Hitbox(int x, int y, int w, int h){
        this.Bounds = new Rect(x,y,w,h);
    }



    public void update(double deltaTime) {

    }

    public void draw(Graphics g, Vector2D camera) {

    }

    public void init() {

    }

    public boolean overlaps(Hurtbox h)
    {

        Boolean isC1ToTheLeftOfC2 = (h.Bounds.x + h.Bounds.w < this.Bounds.x),
                isC2ToTheLeftOfC1 = (this.Bounds.x + this.Bounds.w < h.Bounds.x),
                isC1AboveC2       = (h.Bounds.y + h.Bounds.h < this.Bounds.y),
                isC2AboveC1       = (this.Bounds.y + this.Bounds.h < h.Bounds.y);

        if (isC1ToTheLeftOfC2 || isC2ToTheLeftOfC1 ){
            return false;
        }
        if (isC1AboveC2 || isC2AboveC1 ){
            return false;
        }
        return true;
    }

}
