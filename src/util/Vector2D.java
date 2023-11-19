package util;

public class Vector2D{
    public double x;
    public double y;

    public Vector2D(){
        x = 0;
        y = 0;
    }
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getMagnitude(){
        return Math.sqrt(x * x + y * y);
    }

    public void normalize(){
        double movementVectorMagnitude = Math.sqrt(x * x + y * y);

        x = movementVectorMagnitude != 0 ? x / movementVectorMagnitude: x;
        y = movementVectorMagnitude != 0 ? y / movementVectorMagnitude: y;
    }

    public Vector2D getNormalize(){
        Vector2D result = new Vector2D(x,y);
        result.normalize();
        return result;
    }


    public void add(Vector2D v){
        x += v.x;
        y += v.y;
    }

    public void subtract(Vector2D v){
        x -= v.x;
        y -= v.y;
    }

    public Vector2D multiply(double s){
        x *= s;
        y *= s;
        return this;
    }


    public Vector2D getVectorTo(Vector2D v){
        Vector2D ret = new Vector2D(v.x - x, v.y - y);
        ret.normalize();


        return ret;
    }

    public Vector2D getVectorToNotNorm(Vector2D v){
        Vector2D ret = new Vector2D(v.x - x, v.y - y);

        return ret;
    }



}
