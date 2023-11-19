package entity.player;

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
    public final static String ATTACK_1_ANIMATION_ID = "Attack1";


    public final static int IDLE_X_OFFSET = -235;
    public final static int IDLE_Y_OFFSET = -200;
    public final static double IDLE_SCALE_FACTOR = 3.8;
    public final static String IDLE_ANIMATION_PATH = "src/com/angel/CombatPlatformer/assets/mainCharacter/Idle.png";
    public final static Rect[] IDLE_ANIMATION_POS = {
            new Rect(160*0,0,160,111),
            new Rect(160*1,0,160,111),
            new Rect(160*2,0,160,111),
            new Rect(160*3,0,160,111),
            new Rect(160*4,0,160,111),
            new Rect(160*5,0,160,111),
            new Rect(160*6,0,160,111),
            new Rect(160*7,0,160,111)
    };

    public final static int RUN_X_OFFSET = -235;
    public final static int RUN_Y_OFFSET = -200;
    public final static double RUN_SCALE_FACTOR = 3.8;
    public final static String RUN_ANIMATION_PATH = "src/com/angel/CombatPlatformer/assets/mainCharacter/run.png";
    public final static Rect[] RUN_ANIMATION_POS = {
            new Rect(160*0,0,160,111),
            new Rect(160*1,0,160,111),
            new Rect(160*2,0,160,111),
            new Rect(160*3,0,160,111),
            new Rect(160*4,0,160,111),
            new Rect(160*5,0,160,111),
            new Rect(160*6,0,160,111),
            new Rect(160*7,0,160,111)
    };

    public final static int ATTACK_1_X_OFFSET = -235;
    public final static int ATTACK_1_Y_OFFSET = -200;
    public final static double ATTACK_1_SCALE_FACTOR = 3.8;
    public final static String ATTACK_1_ANIMATION_PATH = "src/com/angel/CombatPlatformer/assets/mainCharacter/Attack1.png";
    public final static Rect[] ATTACK_1_ANIMATION_POS = {
            new Rect(160*0,0,160,111),
            new Rect(160*1,0,160,111),
            new Rect(160*2,0,160,111),
            new Rect(160*3,0,160,111)
    };






    //endregion



}
