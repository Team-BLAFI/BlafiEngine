package entity.enemy;

import util.Animation;
import util.Rect;
import window.WindowConstants;
import java.awt.*;
public class EnemyConstants {

    public static final int ENEMY_HEIGHT = (int) (WindowConstants.SCREEN_UNIT*4);
    public static final int ENEMY_WIDTH = (int) (ENEMY_HEIGHT*(1.0/1.5));

    public final static String IDLE_A_ID = "idle";
    public final static String ATTACK_A_ID = "attack";
    public final static String WALKING_A_ID = "walking";



    public final static int X_OFFSET = (int) (- 5 * WindowConstants.SCREEN_UNIT);
    public final static int Y_OFFSET = (int) (- 5.5  * WindowConstants.SCREEN_UNIT);
    public final static double SCALE_FACTOR =   WindowConstants.SCREEN_UNIT * .2 ;
    public final static String ANIMATION_PATH = "src/assets/knight.png";

    public final static Rect[] IDLE_ANIMATION_POS = {
            new Rect(64*0,0,64,64),
            new Rect(64*1,0,64,64),
            new Rect(64*2,0,64,64),
            new Rect(64*3,0,64,64),
            new Rect(64*4,0,64,64),
    };

    public final static Animation IDLE_ANIMATION = new Animation(
            ANIMATION_PATH,
            IDLE_ANIMATION_POS,
            X_OFFSET,
            Y_OFFSET,
            SCALE_FACTOR
    );
    public final static Rect[] WALKING_ANIMATION_POS = {
            new Rect(64*0,64*1,64,64),
            new Rect(64*1,64*1,64,64),
            new Rect(64*2,64*1,64,64),
            new Rect(64*3,64*1,64,64),
            new Rect(64*4,64*1,64,64),
            new Rect(64*5,64*1,64,64),
            new Rect(64*6,64*1,64,64),
            new Rect(64*7,64*1,64,64),
    };
    public final static Animation WALKING_ANIMATION = new Animation(
            ANIMATION_PATH,
            WALKING_ANIMATION_POS,
            X_OFFSET,
            Y_OFFSET,
            SCALE_FACTOR
    );

    public final static Rect[] ATTACK_ANIMATION_POS = {
            new Rect(64*0,64*4,64,64),
            new Rect(64*1,64*4,64,64),
            new Rect(64*2,64*4,64,64),
            new Rect(64*3,64*4,64,64),
            new Rect(64*4,64*4,64,64),
            new Rect(64*5,64*4,64,64)
    };

    public final static Animation ATTACK_ANIMATION = new Animation(
            ANIMATION_PATH,
            ATTACK_ANIMATION_POS,
            X_OFFSET,
            Y_OFFSET,
            SCALE_FACTOR
    );
}
