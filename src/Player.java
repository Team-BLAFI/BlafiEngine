import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class Player {
    //private Point _position = new Point(10,20);

    private Point2D.Double _position = null;

    private Color _characterColor = Color.GREEN;
    private KL keyListener = KL.getKeyListener();

    public Player(){
        _position = new Point2D.Double(10.0,20.0);

    }


    public void draw(Graphics g){

        g.setColor(_characterColor);
        g.fillRect((int) _position.x, (int) _position.y,Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);

    }


    public void update(double deltaTime){

        HandleMovement(deltaTime);

    }




    private void HandleMovement(double deltaTime){
        Point2D.Double movementVector = GetMovementVector();

        System.out.println(movementVector.toString());


        if(movementVector.x == 1.0 && movementVector.y == 1.0){
            double movementVectorMagnitude = Math.sqrt(movementVector.x * movementVector.x + movementVector.y * movementVector.y);

            movementVector.x = movementVector.x / movementVectorMagnitude;
            movementVector.y = movementVector.y / movementVectorMagnitude;
        }


        _position.x += movementVector.x * Constants.PLAYER_SPEED * deltaTime;
        _position.y += movementVector.y * Constants.PLAYER_SPEED * deltaTime;

    }

    private Point2D.Double GetMovementVector(){

        Point2D.Double movementVector = new Point2D.Double();

        if(keyListener.isKeyDown(KeyEvent.VK_W)){
            movementVector.y -= 1.0;
        }
        if(keyListener.isKeyDown(KeyEvent.VK_S)){
            movementVector.y += 1.0;
        }
        if(keyListener.isKeyDown(KeyEvent.VK_A)){
            movementVector.x -= 1.0;
        }
        if(keyListener.isKeyDown(KeyEvent.VK_D)){
            movementVector.x += 1.0;
        }

        return movementVector;
    }





}
