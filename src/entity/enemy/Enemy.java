package entity.enemy;
import component.Health;
import entity.Entity;
import entity.player.Player;
import entity.player.PlayerConstants;
import util.Transform;
import util.Vector2D;
import util.io.KL;
import window.WindowConstants;
import window.scenes.GameScene;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Enemy extends Entity {

    private final double damagePerSecond = 30.0;


    private double unit = WindowConstants.SCREEN_UNIT;
    private final double moveSpeed = 17.5 * unit;
    private final double reach = 4 * unit;
    private final double attackSpeed = 1.0;
    private double attackActive;
    private Player p;

    public Enemy(Player p){
        double w = WindowConstants.SCREEN_WIDTH;
        double h = WindowConstants.SCREEN_HEIGHT;
        this.p = p;
        this.transform = new Transform( w/3, h/2,EnemyConstants.ENEMY_WIDTH, EnemyConstants.ENEMY_HEIGHT);
        health = new Health(
                100.0,
                (int) (unit * -.5),
                (int) - unit,
                this,
                false
        );
    }



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

        transform.setX(transform.getX() + movementVector.getX());
        transform.setY(transform.getY() + movementVector.getY());

    }


    public void aroundPLayer(double dt){
        Vector2D mv = new Vector2D(transform.getPosition());
        mv.rotate( dt * PlayerConstants.PLAYER_SPEED/10 ,p.transform.getCenterX(),p.transform.getCenterY());

        transform.setPosition(mv);

    }

    public void chasePlayer(double dt){

        Vector2D v = getVectorToPlayer();
        if (v.getMagnitude() > reach){
            v.normalize();
            transform.movePositionBy(v.multiply(moveSpeed * dt));
        }
    }

    private Vector2D getVectorToPlayer() {
        return new Vector2D(transform.getCenterPoint().getVectorTo(p.transform.getCenterPoint()));
    }


    public void dealDamage(double dt){
        if(getVectorToPlayer().getMagnitude()<=reach){
            GameScene.player.health.takeDamage(damagePerSecond *dt);
        }
    }

    public void stateMachine(double deltaTime) {
        if (nextState != null && stateLock <= 0){
            state = nextState;
            nextState = null;
        }

        switch (state) {
            case Idle:
            case Moving:
                determineMovingDir(deltaTime);
                handleAI(deltaTime);
            case Attacking:
                handleAttack(deltaTime);
            case Recovering:
                handleRecovery(deltaTime);
                break;
            default:
                break;
        }
    }

    //TODO animations
    private void determineMovingDir(double deltaTime) {

    }
    private void handleAI(double deltaTime) {
        Vector2D v = getVectorToPlayer();
        if (v.getMagnitude() <= reach){
            nextState = State.Attacking;
        }else{
            chasePlayer(deltaTime);
        }

    }
    double recoveryTime = 0, windUp = 0;
    private void handleAttack(double deltaTime) {
        if (stateLock<=0){
            stateLock = 1;
            nextState = State.Idle;
            windUp = 0.5;
        }else if (windUp <= 0) {
            System.out.println("ATTACK");
        }

    }

    private void handleRecovery(double dt){

    }

    private void handleCD(double dt){
        recoveryTime -= dt;
        stateLock -= dt;
        windUp -= dt;
    }

    @Override
    public void update(double dt) {
//        aroundPLayer(dt);
        chasePlayer(dt);
        aroundPLayer(dt);
        dealDamage(dt);
    }

    @Override
    public void draw(Graphics g) {
        Vector2D s = WindowConstants.MID_SCREENPOINT;


        g.fillRect(
                (int) this.transform.getX(),
                (int) this.transform.getY(),
                (int) this.transform.getWidth(),
                (int) this.transform.getHeight()
        );
        health.draw(g);

        int x = (int) (transform.getCenterX());
        int y = (int) (transform.getCenterY());

        g.setColor(Color.GREEN);
        g.drawLine(x, y, (int) (x+4*unit),y);
        g.drawLine(x, y, x, (int) (y-4*unit));
//        g.drawLine((int) s.getX(), (int) s.getY(), (int) (s.getX() + unit), (int) s.getY());


        g.drawOval(
                (int) (transform.getCenterX() - 4*unit),
                (int) (transform.getCenterY() - 4*unit),
                (int) (8*unit),
                (int) (8*unit)
        );
    }
}
