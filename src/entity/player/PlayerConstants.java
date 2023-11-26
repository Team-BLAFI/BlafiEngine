package entity.player;

import util.Animation;
import util.Rect;
import window.WindowConstants;


import java.awt.*;

public class PlayerConstants {

    //region Dimension variables
    //==============================================================================

    public static final int PLAYER_HEIGHT = (int) (WindowConstants.SCREEN_UNIT*4);
    public static final int PLAYER_WIDTH = (int) (PLAYER_HEIGHT*(1.0/1.5));

    //endregion


    //region movement variables
    //==============================================================================
    public static final double PLAYER_SPEED = WindowConstants.SCREEN_WIDTH*(1.0/4);
    public static final Color characterColor = Color.GREEN;


    //endregion
    //200px per second





    //TODO: REPLACE DEMO SPRITE VARIABLES

    //region sprite variables
    //==============================================================================

    public final static String IDLE_ANIMATION_ID = "idle";
    public final static String RUN_ANIMATION_ID = "Run";


    public final static double IDLE_X_OFFSET = -WindowConstants.SCREEN_UNIT * 3.69;
    public final static double IDLE_Y_OFFSET = -WindowConstants.SCREEN_UNIT * 4.5;
    public final static double IDLE_SCALE_FACTOR = 3;
    public final static String IDLE_ANIMATION_PATH = "src/assets/blafi.png";
    public final static Rect[] IDLE_ANIMATION_POS = {
            new Rect(48*0,0,48,48),
            new Rect(48*1,0,48,48),
            new Rect(48*2,0,48,48)
    };


    public final static Animation PLAYER_IDLE = new Animation(
            IDLE_ANIMATION_PATH,
            IDLE_ANIMATION_POS,
            (int) IDLE_X_OFFSET,
            (int) IDLE_Y_OFFSET,
            IDLE_SCALE_FACTOR
    );




    //endregion



}
