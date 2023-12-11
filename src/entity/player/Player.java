package entity.player;

import component.Collider;

import component.Health;

import component.Weapon;

import map.RoomManager;

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

import static window.WindowConstants.SCREEN_UNIT;

public class Player extends Entity {

    public Weapon weapon;

    private double unit = WindowConstants.SCREEN_UNIT;

    private RoomManager roomManager;

    /**<p>
     * Saves a pointer to the singleton instance of the KeyListener class
     *</p>
     */
    private KL keyListener = KL.getKeyListener();
    private ML mouseListener = ML.getMouseListener();

    public Player(RoomManager rM){
        this.roomManager = rM;
        double w = WindowConstants.SCREEN_WIDTH;
        double h = WindowConstants.SCREEN_HEIGHT;

        transform = new Transform(w/2.0,h/2, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);

        collider = new Collider(
                (int) transform.getX(),
                (int) transform.getY(),
                PlayerConstants.PLAYER_WIDTH,
                PlayerConstants.PLAYER_HEIGHT
        );

//        thisShooting = new Shooting(this);
        weapon = new Weapon(this, 30, 0.1, 3,50,100);


        health = new Health(
                100.0,
                (int) (unit * 0.4),
                (int) - unit,
                this,
                true
        );
    }

    public void draw(Graphics g){
        g.setColor(PlayerConstants.characterColor);
        g.fillRect((int) transform.getX(), (int) transform.getY(), PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(collider.Bounds.x,collider.Bounds.y,collider.Bounds.w,collider.Bounds.h);

        g.setColor(Color.YELLOW);

        health.draw(g);
        weapon.draw(g);

    }

    public void update(double deltaTime){
        HandleMovement(deltaTime);
        Vector2D movementVector = GetMovementVector();
        collider.Bounds.setPos((int) transform.getX(), (int) transform.getY());


        if (mouseListener.isPressed(MouseEvent.BUTTON1)) {
            weapon.shoot(mouseListener.getX(), mouseListener.getY());
        }
        if (keyListener.isKeyDown(KeyEvent.VK_R)){
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
//            int screenUnit = (int) SCREEN_UNIT;
            Vector2D movementVector = GetMovementVector();

            movementVector.normalize();

            movementVector.multiply(PlayerConstants.PLAYER_SPEED * deltaTime);

            Transform newPos = new Transform(transform);

            newPos.moveXBy(movementVector.getX());
            newPos.moveYBy(movementVector.getY());


            if (!roomManager.collidesWithTiles(newPos.getAsCollider())){
                transform.setPosition(newPos.getPosition());
            }
//            transform.setPosition(newPos.getPosition());

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

}

