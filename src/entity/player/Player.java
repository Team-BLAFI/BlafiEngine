package entity.player;

import util.Rect;
import util.io.KL;
import util.io.ML;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player {

    private ArrayList<Component> components = new ArrayList<>();
    /**
     * <p>
     * Saves the position as a Point2D.Double object
     *</p>
     */
    private Point2D.Double position = null;


    /**<p>
     * Saves a pointer to the singleton instance of the KeyListener class
     *</p>
     */
    private KL keyListener = KL.getKeyListener();

    public Player(){
        position = new Point2D.Double(10.0,20.0);
    }


    public void draw(Graphics g){

        g.setColor(PlayerConstants.characterColor);
        g.fillRect((int) position.x, (int) position.y, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);

    }


    public void update(double deltaTime){

        HandleMovement(deltaTime);

    }



    /**
     * <p>
     * Uses the GetMovementVector() function to get information on how to move the player
     * <br>
     * Then normalizes that vector and moves the character by the unit vector multiplied by delta time and the player speed
     *</p>
     * @param deltaTime gets time since last frame to keep speed constant
     */
    private void HandleMovement(double deltaTime){
        Point2D.Double movementVector = GetMovementVector();


        if(movementVector.x == 1.0 && movementVector.y == 1.0){
            double movementVectorMagnitude = Math.sqrt(movementVector.x * movementVector.x + movementVector.y * movementVector.y);

            movementVector.x = movementVector.x / movementVectorMagnitude;
            movementVector.y = movementVector.y / movementVectorMagnitude;
        }


        position.x += movementVector.x * PlayerConstants.PLAYER_SPEED * deltaTime;
        position.y += movementVector.y * PlayerConstants.PLAYER_SPEED * deltaTime;

    }

    /**
     * <p>
     * Uses the KeyListener to get the information of how to move the player
     *</p>
     * @return Point2D.Double returns the movement keys pressed as a vector to move the player by
     */
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
