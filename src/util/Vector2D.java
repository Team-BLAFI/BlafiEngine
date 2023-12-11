package util;

public class Vector2D{
    private double x;
    private double y;

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
    public void rotate(double angle, double rotationX, double rotationY){
        double RadiantsAngle = (double) Math.toRadians(angle);

        //place vector at origin
        setX(this.x - rotationX);
        setY(this.y - rotationY);

        //rotate
        //x` = xcos(θ)−ysin(θ)
        //y` = xsin(θ)+ycos(θ)
        double Ox = getX();
        double Oy = getY();

        setX((double) (Ox * Math.cos(RadiantsAngle) - Oy * Math.sin(RadiantsAngle)));
        setY((double) (Ox * Math.sin(RadiantsAngle) + Oy * Math.cos(RadiantsAngle)));

        //setVectorBackToLocation

        setX(this.x + rotationX);
        setY(this.y + rotationY);
    }
    public void rotate(double angle, Vector2D rotationPoint){
        rotate(angle, rotationPoint.x, rotationPoint.y);
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
