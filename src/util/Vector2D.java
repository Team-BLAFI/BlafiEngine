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

    public void Normalize(){
        double movementVectorMagnitude = Math.sqrt(x * x + y * y);

        x = movementVectorMagnitude != 0 ? x / movementVectorMagnitude: x;
        y = movementVectorMagnitude != 0 ? y / movementVectorMagnitude: y;
    }

    public Vector2D GetNormalize(){
        Vector2D result = new Vector2D(x,y);
        result.Normalize();
        return result;
    }




}
