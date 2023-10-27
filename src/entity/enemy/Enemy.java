package entity.enemy;

import component.Collider;
import component.Health;
import entity.Entity;
import entity.player.Player;
import entity.player.PlayerConstants;
import util.Transform;
import util.Vector2D;
import window.Window;
import window.WindowConstants;
import window.scenes.GameScene;

import java.awt.*;

public class Enemy extends Entity {

    private enum State{
        IDLE, FOLLOWING, ATTACKING
    }
    private double unit = WindowConstants.SCREEN_UNIT;

    private State state = State.IDLE;
    private final double attackSpeed = 1.0;
    private double attackActive;
    private Player p;

    public Enemy(Player p){
        this.p = p;
        this.transform = new Transform( 0, 0,EnemyConstants.ENEMY_WIDTH, EnemyConstants.ENEMY_HEIGHT);
        health = new Health(
                100.0,
                (int) (unit * -.5),
                (int) - unit,
                this
        );
    }

    public void changeState(State newState) {
        this.state = newState;
    }

    private void HandleMovement(double deltaTime){
        Vector2D movementVector = this.GetMovementVector();

//        Vector2D movementVector = new Vector2D(1,0);

        movementVector.normalize();
        this.transform.position.x += movementVector.x * PlayerConstants.PLAYER_SPEED * deltaTime;
        this.transform.position.y += movementVector.y * PlayerConstants.PLAYER_SPEED * deltaTime;

    }

    private Vector2D GetMovementVector(){

        Vector2D movementVector = new Vector2D();

        if(this.isBelow(p)){
            movementVector.y -= 1.0;
        }else if(this.isAbove(p)){
            movementVector.y += 1.0;
        }


        if(this.isRightOf(p)){
            movementVector.x -= 1.0;
        }else if(this.isLeftOf(p)){
            movementVector.x += 1.0;
        }

        return movementVector;
    }

    public boolean isLeftOf(Entity e) {
        return this.transform.position.x < e.transform.position.x;
    }

    public boolean isRightOf(Entity e) {
        return this.transform.position.x > e.transform.position.x;
    }

    public boolean isAbove(Entity e) {
        return this.transform.position.y < e.transform.position.y;
    }

    public boolean isBelow(Entity e) {
        return this.transform.position.y > e.transform.position.y;
    }

    public void attacking(){

    }


    @Override
    public void update(double dt) {


        HandleMovement(dt);
        System.out.println(this.transform.position.x + ", " + this.transform.position.y);

//        if(state == state.IDLE) {
//
//        } else if() {
//
//        }




    }

    @Override
    public void draw(Graphics g) {
        g.fillRect((int) this.transform.position.x, (int) this.transform.position.y, (int) this.transform.size.x, (int) this.transform.size.y);
        health.draw(g);
    }
}
