package util;

public class Transform {
    public Vector2D position = new Vector2D();
    public Vector2D size = new Vector2D();

    public Transform(){
        this(0,0,0,0);
    }

    public Transform(Vector2D p, Vector2D s){
        position = p;
        size = s;
    }

    public Transform(double x, double y, double w, double h){
        position.x = x;
        position.y = y;
        size.x = w;
        size.y = h;
    }

}
