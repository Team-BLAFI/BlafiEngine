package entity.player;

import component.Collider;
import component.TileManager;
import entity.Entity;
import util.Transform;
import util.Vector2D;
import util.io.KL;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player extends Entity {

    public Collider h;
    private ArrayList<Component> components = new ArrayList<>();

    private TileManager tileManager;

    /**<p>
     * Saves a pointer to the singleton instance of the KeyListener class
     *</p>
     */
    private KL keyListener = KL.getKeyListener();

    public Player(){
        /**
         * Change position of Player to place inside tiles
         * */
        transform = new Transform(500.0,300.0, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        h = new Collider(
                (int) transform.position.x,
                (int) transform.position.y,
                PlayerConstants.PLAYER_WIDTH,
                PlayerConstants.PLAYER_HEIGHT
        );
        tileManager = new TileManager();
    }


    public void draw(Graphics g){

        g.setColor(PlayerConstants.characterColor);
        g.fillRect((int) transform.position.x, (int) transform.position.y, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(h.Bounds.x,h.Bounds.y,h.Bounds.w,h.Bounds.h);

    }


    public void update(double deltaTime){
        /*Need to refactor movementVector later*/
        /*Trying to handle collision detection in update method*/

        Point2D.Double movementVector = GetMovementVector();
        HandleMovement(deltaTime);
        h.Bounds.setPos((int) transform.position.x, (int) transform.position.y);

        /**
         * <p>Checks for collision in the the TileManager class</p>
         * returns true when player touches tileNum 1 (walls/dirt image)
         * then stops player's movement and speed when it touches tile
         */
        if(tileManager.checkCollisions(h)){
            transform.position.x -= movementVector.x * PlayerConstants.PLAYER_SPEED * deltaTime;
            transform.position.y -= movementVector.y * PlayerConstants.PLAYER_SPEED * deltaTime;
        }
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


        transform.position.x += movementVector.x * PlayerConstants.PLAYER_SPEED * deltaTime;
        transform.position.y += movementVector.y * PlayerConstants.PLAYER_SPEED * deltaTime;
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
