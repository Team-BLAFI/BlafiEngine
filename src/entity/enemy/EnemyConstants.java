package entity.enemy;

import util.Rect;
import window.WindowConstants;
import java.awt.*;
public class EnemyConstants {

    public static final int ENEMY_HEIGHT = (int) (WindowConstants.SCREEN_WIDTH*(1.0/10));
    public static final int ENEMY_WIDTH = (int) (ENEMY_HEIGHT*(1.0/1.5));

    public static final double ENEMY_SPEED = WindowConstants.SCREEN_WIDTH*(1.0/4);
    public static final Color enemyColor = Color.RED;
}
