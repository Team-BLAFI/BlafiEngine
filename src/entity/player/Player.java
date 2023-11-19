package entity.player;

import component.Collider;
import component.Health;

import component.Weapon;
import entity.Entity;
import component.Projectile;

//import util.Shooting;
import util.Transform;
import util.Vector2D;
import util.io.KL;
import util.io.ML;
import window.WindowConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player extends Entity {

    //private static final BulletType Standard = null;
    private ArrayList<Component> components = new ArrayList<>();
    public Vector2D mousePos = new Vector2D();
//    public Shooting thisShooting;
    public Weapon weapon;

    private double unit = WindowConstants.SCREEN_UNIT;

    /**<p>
     * Saves a pointer to the singleton instance of the KeyListener class
     *</p>
     */
    private KL keyListener = KL.getKeyListener();
    private ML mouseListener = ML.getMouseListener();

    public Player(){
        double w = WindowConstants.SCREEN_WIDTH;
        double h = WindowConstants.SCREEN_HEIGHT;

        transform = new Transform(w/2.0,h/2, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);

        collider = new Collider(
                (int) transform.position.x,
                (int) transform.position.y,
                PlayerConstants.PLAYER_WIDTH,
                PlayerConstants.PLAYER_HEIGHT
        );

//        thisShooting = new Shooting(this);
        weapon = new Weapon(this, 10, 0.3, 3,10,100);


        health = new Health(
                100.0,
                (int) (unit * 0.4),
                (int) - unit,
                this
        );
    }

    public void draw(Graphics g){
        g.setColor(PlayerConstants.characterColor);
        g.fillRect((int) transform.position.x, (int) transform.position.y, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(collider.Bounds.x,collider.Bounds.y,collider.Bounds.w,collider.Bounds.h);

        g.setColor(Color.YELLOW);
        if(mousePos != null) {
            System.out.println("mouse x,y: " + mousePos.x + ", " + mousePos.y);
//            g.drawRect((int)mousePos.x, (int)mousePos.y, 40, 40);
        }
        health.draw(g);
        weapon.draw(g);
    }

    public void update(double deltaTime){
//        System.out.println(this.transform.position.x + ", " + this.transform.position.y);
        HandleMovement(deltaTime);
        collider.Bounds.setPos((int) transform.position.x, (int) transform.position.y);

        if (mouseListener.isPressed(MouseEvent.BUTTON1)) {
            weapon.shoot(mouseListener.getX(), mouseListener.getY());
        }
        if (keyListener.isKeyDown(KeyEvent.VK_R)){
            weapon.reload();
        }
        if (keyListener.isKeyDown(KeyEvent.VK_Z)){
            weapon.reload();
        }
        weapon.update(deltaTime);
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
