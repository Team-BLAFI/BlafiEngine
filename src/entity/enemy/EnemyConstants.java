package entity.enemy;

import util.Rect;
import window.WindowConstants;
import java.awt.*;
public class EnemyConstants {

    public static final int ENEMY_HEIGHT = (int) (WindowConstants.SCREEN_UNIT * 10);
    public static final int ENEMY_WIDTH = (int) (WindowConstants.SCREEN_UNIT * 5);

    public static final double ENEMY_SPEED = WindowConstants.SCREEN_UNIT * 20;
    public static final Color enemyColor = Color.RED;
}
