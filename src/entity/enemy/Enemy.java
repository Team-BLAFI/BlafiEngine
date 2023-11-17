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





    @Override
    public void update(double dt) {



    }

    @Override
    public void draw(Graphics g) {
        g.fillRect((int) this.transform.position.x, (int) this.transform.position.y, (int) this.transform.size.x, (int) this.transform.size.y);
        health.draw(g);
    }
}