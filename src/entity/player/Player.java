package entity.player;

import component.*;

import entity.Entity;

//import util.Shooting;
import util.Transform;
import util.Vector2D;
import util.io.KL;
import util.io.ML;
import window.WindowConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static entity.player.PlayerConstants.IDLE_ANIMATION_ID;
public class Player extends Entity {

    public Weapon weapon;

    private double unit = WindowConstants.SCREEN_UNIT;

    private TileManager tileManager;

    private Animator animator;



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
                (int) transform.getX(),
                (int) transform.getY(),
                PlayerConstants.PLAYER_WIDTH,
                PlayerConstants.PLAYER_HEIGHT
        );
        tileManager = new TileManager();


//        thisShooting = new Shooting(this);
        weapon = new Weapon(this, 30, 0.1, 2,100,100);


        health = new Health(
                100.0,
                (int) (unit * 0.4),
                (int) - unit,
                this
        );

        animator = new Animator();
        animator.addAnimation(PlayerConstants.PLAYER_IDLE, IDLE_ANIMATION_ID);
        animator.changeAnimationTo(IDLE_ANIMATION_ID);
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
        Vector2D movementVector = GetMovementVector();

        movementVector.normalize();

        movementVector.multiply(PlayerConstants.PLAYER_SPEED * deltaTime);

//        transform.movePositionBy(movementVector);

        Transform newPos = new Transform(transform);

        newPos.moveXBy(movementVector.getX());
        if(tileManager.checkCollisions(newPos.getAsCollider())){
            newPos.setX(transform.getX());
        }
        newPos.moveYBy(movementVector.getY());
        if(tileManager.checkCollisions(newPos.getAsCollider())){
            newPos.setY(transform.getY());
        }

        transform.setPosition(newPos.getPosition());

    }

    /**
     * <p>
     * Uses the KeyListener to get the information of how to move the player
     *</p>
     * @return Point2D.Double returns the movement keys pressed as a vector to move the player by
     */
    private Vector2D GetMovementVector() {

        Vector2D movementVector = new Vector2D();

        if (keyListener.isKeyDown(KeyEvent.VK_W)) {
            movementVector.setY(movementVector.getY() - 1.0);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_S)) {
            movementVector.setY(movementVector.getY() + 1.0);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_A)) {
            movementVector.setX(movementVector.getX() - 1.0);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_D)) {
            movementVector.setX(movementVector.getX() + 1.0);
        }

        return movementVector;
    }

    public void draw(Graphics g){
//        g.setColor(PlayerConstants.characterColor);
//        g.fillRect((int) transform.getX(), (int) transform.getY(), PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(collider.Bounds.x,collider.Bounds.y,collider.Bounds.w,collider.Bounds.h);
        health.draw(g);
        weapon.draw(g);
        animator.RenderCurrentSprite(g, (int) transform.getX(), (int) transform.getY());
    }

    public void update(double deltaTime){
        HandleMovement(deltaTime);
        collider.Bounds.setPos((int) transform.getX(), (int) transform.getY());


        if (mouseListener.isPressed(MouseEvent.BUTTON1)) {
            weapon.shoot(mouseListener.getX(), mouseListener.getY());
        }
        if (keyListener.isKeyDown(KeyEvent.VK_R)){
            weapon.reload();
        }

        /**
         * <p>Checks for collision in the the TileManager class</p>
         * returns true when player touches tileNum 1 (walls/dirt image)
         * then stops player's movement and speed when it touches tile
         */

        animator.update(deltaTime);
        weapon.update(deltaTime);
    }
}