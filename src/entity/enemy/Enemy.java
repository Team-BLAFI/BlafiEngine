package entity.enemy;
import component.Animator;
import component.Health;
import entity.Entity;
import entity.player.Player;
import entity.player.PlayerConstants;

import map.RoomManager;

import util.Animation;

import util.Transform;
import util.Vector2D;
import util.io.KL;
import window.WindowConstants;
import window.scenes.GameScene;

import java.awt.*;
import java.awt.event.KeyEvent;

import static entity.enemy.EnemyConstants.*;


public class Enemy extends Entity {

    private final double damagePerSecond = 30.0;
    Animator animator = new Animator();

    private double stateLock = 0;
    private State stateIdle = new Idle();
    private State stateMoving = new Moving();
    private State stateAttacking = new Attacking();
    private State currentState;
    private double unit = WindowConstants.SCREEN_UNIT;
    private final double moveSpeed = 20 * unit;
    private final double reach = 4 * unit;
    private final double attackSpeed = 1.0;
    private Player p;
    boolean facingLeft = false;
    boolean isMoving = false;
    double recoveryTime = 0, windUp = 0;



    private RoomManager roomManager;
    public Enemy(Player p){

        roomManager = new RoomManager();

        setState(stateIdle);

        double w = WindowConstants.SCREEN_WIDTH;
        double h = WindowConstants.SCREEN_HEIGHT;
        this.p = p;
        this.transform = new Transform( w/3, h/2,ENEMY_WIDTH, ENEMY_HEIGHT);

        animator = new Animator();
        animator.addAnimation(IDLE_ANIMATION, IDLE_A_ID);
        animator.addAnimation(ATTACK_ANIMATION, ATTACK_A_ID);
        animator.addAnimation(WALKING_ANIMATION, WALKING_A_ID);



        health = new Health(
                100.0,
                (int) (unit * -.5),
                (int) - unit,
                this,
                false
        );
    }


    /*

        HandleCooldowns();
        HandleAI(){;
            HandleMovement()
            Attack();
        }

    */

    public void controlEnemy(double dt){
        Vector2D movementVector = new Vector2D();

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_UP)){
            movementVector.setY(movementVector.getY() - 1.0);
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_DOWN)){
            movementVector.setY(movementVector.getY() + 1.0);
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_LEFT)){
            movementVector.setX(movementVector.getX() - 1.0);
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_RIGHT)){
            movementVector.setX(movementVector.getX() + 1.0);
        }

        movementVector.normalize();

        movementVector.multiply(moveSpeed * dt);

//        transform.setX(transform.getX() + movementVector.getX());
//        transform.setY(transform.getY() + movementVector.getY());


        /*ADDING COLLISION FOR ENEMY AND TILE*/
        //*
        Transform newPos = new Transform(transform);

        newPos.moveXBy(movementVector.getX());
        newPos.moveYBy(movementVector.getY());

        if (!roomManager.collidesWithTiles(newPos.getAsCollider())){
            transform.setPosition(newPos.getPosition());
        }
        //*/
        Vector2D d = new Vector2D(transform.getCenterX(), transform.getCenterY());

    }

    public void aroundPLayer(double dt){
        Vector2D mv = new Vector2D(transform.getPosition());
        mv.rotate( dt * PlayerConstants.PLAYER_SPEED/10 ,p.transform.getCenterX(),p.transform.getCenterY());

        transform.setPosition(mv);


    }

    public void chasePlayer(double dt){

        isMoving = true;
        Vector2D v = getVectorToPlayer();
        v.normalize();
        transform.movePositionBy(v.multiply(moveSpeed * dt));

    }

    private Vector2D getVectorToPlayer() {
        return new Vector2D(transform.getCenterPoint().getVectorTo(p.transform.getCenterPoint()));
    }

    public void dealDamage(double dt){
        if(getVectorToPlayer().getMagnitude()<=reach){
            GameScene.player.health.takeDamage(damagePerSecond *dt);
        }
    }



    private void handleCD(double dt){
        recoveryTime -= dt;
        stateLock -= dt;
        windUp -= dt;
    }

    @Override
    public void update(double dt) {
        facingLeft = !(transform.getCenterX() < p.transform.getCenterX());
        handleCD(dt);
        currentState.stateUpdate(dt);
        animator.update(dt);
    }

    private void setState(State s){
        currentState = s;
        currentState.enterState();
    }

    @Override
    public void draw(Graphics g, Vector2D camera) {
        int x = (int)(transform.getX() - camera.getX());
        int y = (int)(transform.getY() - camera.getY());

        health.draw(g, camera);

        if (facingLeft){
            animator.RenderCurrentSpriteFlipVer(g,x,y);
        }else{
            animator.RenderCurrentSprite(g,x,y);
        }
    }

    public class Attacking implements entity.enemy.State{

        @Override
        public void enterState() {
            animator.changeAnimationTo(ATTACK_A_ID);
            stateLock = attackSpeed;
            windUp = attackSpeed * 0.1d;
            recoveryTime = attackSpeed * 0.7d;
        }

        @Override
        public void stateUpdate(double dt) {
            if (windUp<=0){
                if (getVectorToPlayer().getMagnitude() <= reach){
                    p.health.takeDamage(30 * dt);
                }
            }
            if (recoveryTime <= 0){
                setState(stateIdle);
            }

        }
    }
    public class Idle implements entity.enemy.State{
        @Override
        public void enterState() {
            animator.changeAnimationTo(IDLE_A_ID);
        }
        @Override
        public void stateUpdate(double dt) {
            if (stateLock <= 0){
                setState(stateMoving);
            }
        }
    }
    public class Moving implements entity.enemy.State{

        @Override
        public void enterState() {
            animator.changeAnimationTo(WALKING_A_ID);

        }

        @Override
        public void stateUpdate(double dt) {
            if (getVectorToPlayer().getMagnitude()<=reach){
                setState(stateAttacking);
            }else{
                chasePlayer(dt);
            }

        }
    }
}
