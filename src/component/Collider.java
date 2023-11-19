package component;

import util.Rect;

import java.awt.*;

public class Collider extends Component{

    public Rect Bounds;

    public Collider(Rect rect){
        this.Bounds = rect;
    }

    public Collider(int x, int y, int w, int h){
        this.Bounds = new Rect(x,y,w,h);
    }



    public void update(double deltaTime) {

    }

    public void draw(Graphics g) {

    }

    public void init() {

    }

    public boolean overlaps(Collider collider){

        Boolean isC1ToTheLeftOfC2 = (collider.Bounds.x + collider.Bounds.w < this.Bounds.x),
                isC2ToTheLeftOfC1 = (this.Bounds.x + this.Bounds.w < collider.Bounds.x),
                isC1AboveC2 = (collider.Bounds.y + collider.Bounds.h < this.Bounds.y),
                isC2AboveC1 = (this.Bounds.y + this.Bounds.h < collider.Bounds.y);

        if (isC1ToTheLeftOfC2 || isC2ToTheLeftOfC1 ){
            return false;
        }
        if (isC1AboveC2 || isC2AboveC1 ){
            return false;
        }
        return true;
    }


}
