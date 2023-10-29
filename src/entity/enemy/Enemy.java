package entity.enemy;

import component.Collider;
import component.Health;
import entity.Entity;
import entity.player.Player;
import util.Transform;
import util.Vector2D;
import window.WindowConstants;

import java.awt.*;

public class Enemy extends Entity {
    private double unit = WindowConstants.SCREEN_UNIT;

    private Transform playerTransform;

    public Enemy(Transform p){
        health = new Health(
                100.0,
                (int) (unit * 0.4),
                (int) - unit,
                this
        );
        playerTransform = p;
        this.transform = new Transform( 0, 0,EnemyConstants.ENEMY_WIDTH, EnemyConstants.ENEMY_HEIGHT);
    }

    @Override
    public void update(double dt) {
        Collider p = new Collider(
                (int) playerTransform.position.x,
                (int) playerTransform.position.y,
                (int) playerTransform.size.x,
                (int) playerTransform.size.y
        );
        Collider e = new Collider(
                (int) transform.position.x,
                (int) transform.position.y,
                (int) transform.size.x,
                (int) transform.size.y
        );

        if (p.overlaps(e)){
            Vector2D movementVector = transform.position.getVectorTo(playerTransform.position);
            movementVector.multiply(EnemyConstants.ENEMY_SPEED * dt);

            playerTransform.position.add(movementVector);
        }

        Vector2D movementVector = transform.position.getVectorTo(playerTransform.position);
        movementVector.multiply(EnemyConstants.ENEMY_SPEED * dt);
        transform.position.add(movementVector);
    }

    @Override
    public void draw(Graphics g) {
        health.drawHealthBar(g, (int) transform.position.x, (int) transform.position.y);
        g.fillRect((int) transform.position.x, (int) transform.position.y, (int) transform.size.x, (int) transform.size.y);
    }
}
