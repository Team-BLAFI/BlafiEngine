package entity.enemy;
import component.Collider;
import component.Health;
import entity.Entity;
import entity.player.Player;
import entity.player.PlayerConstants;
import util.Transform;
import util.Vector2D;
import util.io.KL;
import window.Window;
import window.WindowConstants;
import window.scenes.GameScene;

import java.awt.*;
import java.awt.event.KeyEvent;

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

    @Override
    public void update(double dt) {
        Vector2D movementVector = new Vector2D();

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_UP)){
            movementVector.y -= 1.0;
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_DOWN)){
            movementVector.y += 1.0;
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_LEFT)){
            movementVector.x -= 1.0;
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_RIGHT)){
            movementVector.x += 1.0;
        }

        if(movementVector.x == 1.0 && movementVector.y == 1.0){
            double movementVectorMagnitude = Math.sqrt(movementVector.x * movementVector.x + movementVector.y * movementVector.y);

            movementVector.x = movementVector.x / movementVectorMagnitude;
            movementVector.y = movementVector.y / movementVectorMagnitude;
        }

        transform.position.x += movementVector.x * PlayerConstants.PLAYER_SPEED * dt;
        transform.position.y += movementVector.y * PlayerConstants.PLAYER_SPEED * dt;

        double px = p.transform.position.x + p.transform.size.x/2;
        double py = p.transform.position.y + p.transform.size.y/2;

        double x = transform.position.x + transform.size.x/2;
        double y = transform.position.y + transform.size.y/2;

        Vector2D d = new Vector2D(x,y);
        d = d.getVectorToNotNorm(new Vector2D(px,py));
        if(d.getMagnitude()<unit*3.5){
            GameScene.player.health.takeDamage(10*dt);
        }

    }

    @Override
    public void draw(Graphics g) {


        g.fillRect((int) this.transform.position.x, (int) this.transform.position.y, (int) this.transform.size.x, (int) this.transform.size.y);
        health.draw(g);

        int x = (int) (transform.position.x + transform.size.x/2);
        int y = (int) (transform.position.y + transform.size.y/2);

        g.setColor(Color.GREEN);
        g.drawLine(x,y, (int) (x+3.5*unit),y);
    }
}