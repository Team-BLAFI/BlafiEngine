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

        movementVector.multiply(PlayerConstants.PLAYER_SPEED * dt);

        transform.setX(transform.getX() + movementVector.getX());
        transform.setY(transform.getY() + movementVector.getY());



        Vector2D d = new Vector2D(transform.getCenterX(), transform.getCenterY());

        d = d.getVectorToNotNorm(new Vector2D(p.transform.getCenterX(),p.transform.getCenterY()));

        if(d.getMagnitude()<unit*5){
            GameScene.player.health.takeDamage(30*dt);
        }

    }

    @Override
    public void draw(Graphics g) {


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

       g.drawOval(
               (int) (transform.getCenterX() - 4*unit),
               (int) (transform.getCenterY() - 4*unit),
               (int) (8*unit),
               (int) (8*unit)
       );

    }
}
