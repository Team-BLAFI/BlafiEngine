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

import static entity.player.PlayerConstants.*;
import static util.Vector2D.zero;

public class Player extends Entity {

    public Weapon weapon;

    //region private fields
    private double unit = WindowConstants.SCREEN_UNIT;
    private STATES state = STATES.IDLE;
    private TileManager tileManager;
    private Animator animator;
    private boolean isShooting = false;
    private boolean isFacingLeft = false;
    private Vector2D velocity = new Vector2D();
    Vector2D drag = new Vector2D();
    private double max_velocity = unit * 0.15;
    private double max_shootingVelocity = max_velocity * 0.3;
    private double currMaxVel = 0;
    private double friction = 0;
    private double acceleration = 0;

    //endregion


    /**<p>
     * Saves a pointer to the singleton instance of the KeyListener class
     *</p>
     */
    private KL keyListener = KL.getKeyListener();
    private ML mouseListener = ML.getMouseListener();

    private enum STATES {
        IDLE, RUN, RUNSHOOTING, ACCELERATING, RUNMAXSPEED
    }

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
        animator.addAnimation(PlayerConstants.PLAYER_RUN, RUN_ANIMATION_ID);
        animator.addAnimation(PlayerConstants.PLAYER_RUNMAXSPEED, RUNMAXSPEED_ANIMATION_ID);
        animator.addAnimation(PlayerConstants.PLAYER_RUNSHOOTING, RUNSHOOTING_ANIMATION_ID);

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
        currMaxVel = isShooting ? max_shootingVelocity: max_velocity;
        friction = currMaxVel * 12;
        acceleration = currMaxVel * 12;

        Vector2D movementVector = GetMovementVector();
        movementVector.normalize();

        movementVector.multiply(acceleration * deltaTime);

        velocity.add(movementVector);

        if (movementVector.getMagnitude() == 0) {

            if(velocity.getMagnitude() > 0.01){
                drag = new Vector2D(-velocity.getX(), -velocity.getY());
                drag.normalize();
                drag.multiply(friction * deltaTime);
                velocity.add(drag);
            }else{
                velocity = new Vector2D();
                drag = new Vector2D();
            }
        }
        velocity.clamp(0,currMaxVel);

        Vector2D oldPos = new Vector2D(transform.getPosition());

        this.transform.moveXBy(velocity.getX());
        if(tileManager.checkCollisions(this.transform.getAsCollider())){
            this.transform.setX(oldPos.getX());
        }
        this.transform.moveYBy(velocity.getY());
        if(tileManager.checkCollisions(this.transform.getAsCollider())){
            this.transform.setY(oldPos.getY());
        }
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
            isFacingLeft = true;
            movementVector.setX(movementVector.getX() - 1.0);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_D)) {
            isFacingLeft = false;
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

        if (isFacingLeft && !isShooting) {
            animator.RenderCurrentSpriteFlipVer(g, (int) transform.getX(), (int) transform.getY());
        } else if (!isShooting) {
            animator.RenderCurrentSprite(g, (int) transform.getX(), (int) transform.getY());
        }

        if (isShooting && mouseListener.getX() < transform.getCenterX()) {
            animator.RenderCurrentSpriteFlipVer(g, (int) transform.getX(), (int) transform.getY());
        } else if (isShooting) {
            animator.RenderCurrentSprite(g, (int) transform.getX(), (int) transform.getY());
        }

        debug(g);
    }

    public void update(double deltaTime){
        HandleMovement(deltaTime);
        collider.Bounds.setPos((int) transform.getX(), (int) transform.getY());

        if (mouseListener.isPressed(MouseEvent.BUTTON1) && !weapon.isReloading()) {
            this.isShooting = true;
            animator.changeAnimationTo(RUNSHOOTING_ANIMATION_ID);
            weapon.shoot(mouseListener.getX(), mouseListener.getY());
        }
        if (!mouseListener.isPressed(MouseEvent.BUTTON1)) {
            this.isShooting = false;
        }
        if (keyListener.isKeyDown(KeyEvent.VK_R)){
            weapon.reload();
        }

        /**
         * <p>Checks for collision in the the TileManager class</p>
         * returns true when player touches tileNum 1 (walls/dirt image)
         * then stops player's movement and speed when it touches tile
         */

        animator.changeAnimationTo(velocity.getMagnitude() != 0 ? RUN_ANIMATION_ID : IDLE_ANIMATION_ID);

        animator.update(deltaTime);
        weapon.update(deltaTime);
    }

    public void debug(Graphics g) {
        g.setColor(Color.WHITE);
        Font myFont = new Font ("Courier New", 1, 17);
        g.setFont(myFont);
        g.drawString(String.format("Velocity: %.3f", velocity.getMagnitude()),WindowConstants.SCREEN_WIDTH-300, (int) (WindowConstants.INSET_SIZE*1.5));
        g.drawString(String.format("Current Max Velocity: %.3f", currMaxVel),WindowConstants.SCREEN_WIDTH-300, (int) (WindowConstants.INSET_SIZE*1.5)+18);
        g.drawString(String.format("Drag: %.3f", drag.getMagnitude()),WindowConstants.SCREEN_WIDTH-300, (int) (WindowConstants.INSET_SIZE*1.5)+36);
    }
}