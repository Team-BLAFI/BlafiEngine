package entity.player;

import util.Animation;
import util.Rect;
import window.WindowConstants;

import java.awt.*;

public class PlayerConstants {

    //region Dimension variables
    //==============================================================================

    public final static int X_OFFSET = (int) (- 3.5 * WindowConstants.SCREEN_UNIT);
    public final static int Y_OFFSET = (int) (-4  * WindowConstants.SCREEN_UNIT);
    public final static double SCALE_FACTOR =   WindowConstants.SCREEN_UNIT * .2 ;
    public static final int PLAYER_HEIGHT = (int) (WindowConstants.SCREEN_UNIT*4);
    public static final int PLAYER_WIDTH = (int) (PLAYER_HEIGHT*(1.0/1.5));
    public final static String IDLE_A_ID = "idle";
    public final static String WALKING_A_ID = "walking";

    //endregion

    //Animation
    public final static String PLAYER_PATH = "src/assets/player.png";
    public final static Rect[] IDLE_ANIMATION_POS = {
            new Rect(48*0,0,48,48),
            new Rect(48*1,0,48,48),
            new Rect(48*2,0,48,48),
    };
    public final static Rect[] WALKING_ANIMATION_POS = {
            new Rect(48*3,48*1,48,48),
            new Rect(48*4,48*1,48,48),
            new Rect(48*5,48*1,48,48),
    };
    public final static Animation WALKING_ANIMATION = new Animation(
            PLAYER_PATH,
            WALKING_ANIMATION_POS,
            X_OFFSET,
            Y_OFFSET,
            SCALE_FACTOR
    );
    public final static Animation IDLE_ANIMATION = new Animation(
            PLAYER_PATH,
            IDLE_ANIMATION_POS,
            X_OFFSET,
            Y_OFFSET,
            SCALE_FACTOR
    );


    //region movement variables
    //==============================================================================
    public static final double PLAYER_SPEED = WindowConstants.SCREEN_WIDTH*(1.0/4);
    public static final Color characterColor = Color.GREEN;


    //endregion

}
