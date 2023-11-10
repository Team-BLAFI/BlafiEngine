package entity.player;

import component.Collider;
import entity.Entity;
import util.Transform;
import util.Vector2D;
import util.io.KL;
import util.io.ML;
import util.Shooting;
import component.Projectile;
import component.Projectile.BulletType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player extends Entity {

    //private static final BulletType Standard = null;
	public Collider h;
    private ArrayList<Component> components = new ArrayList<>();
    public Vector2D mousePos;
    public Shooting thisShooting;
    

    /**<p>
     * Saves a pointer to the singleton instance of the KeyListener class
     *</p>
     */
    private KL keyListener = KL.getKeyListener();
    private ML mouseListener = ML.getMouseListener();

    public Player(){
        transform = new Transform(10.0,20.0, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        h = new Collider(
                (int) transform.position.x,
                (int) transform.position.y,
                PlayerConstants.PLAYER_WIDTH,
                PlayerConstants.PLAYER_HEIGHT
        );
        initShooting();
    }


    public void draw(Graphics g){

        g.setColor(PlayerConstants.characterColor);
        g.fillRect((int) transform.position.x, (int) transform.position.y, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(h.Bounds.x,h.Bounds.y,h.Bounds.w,h.Bounds.h);

    }


    public void update(double deltaTime){

        HandleMovement(deltaTime);
        h.Bounds.setPos((int) transform.position.x, (int) transform.position.y);
        if (mouseListener.isPressed(0)) {
        	onClick();
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

    public void initShooting() {
    	thisShooting = new Shooting(this);
  
    }

    public void onClick() {
    	mousePos.x = mouseListener.getX();
    	mousePos.y = mouseListener.getY();
    	thisShooting.shoot(Projectile.BulletType.Standard, mousePos);
    	
    }

    
}
