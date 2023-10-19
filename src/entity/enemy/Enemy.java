package entity.enemy;

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
        playerTransform = p;
        this.transform = new Transform( 0, 0,EnemyConstants.ENEMY_WIDTH, EnemyConstants.ENEMY_HEIGHT);
    }

    @Override
    public void update(double dt) {
        Vector2D movementVector = transform.position.getVectorTo(playerTransform.position);

        movementVector.multiply(EnemyConstants.ENEMY_SPEED * dt);
        transform.position.add(movementVector);
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect((int) transform.position.x, (int) transform.position.y, (int) transform.size.x, (int) transform.size.y);
    }
}
