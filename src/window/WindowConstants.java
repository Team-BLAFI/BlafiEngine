package window;

import util.Vector2D;

public class WindowConstants {

    public static int SCREEN_WIDTH = 1440;
    public static int SCREEN_HEIGHT = (int) (SCREEN_WIDTH * 0.5625);

    public static double SCREEN_UNIT = SCREEN_WIDTH * 0.01;

    public static int INSET_SIZE = 0;

    public static final String SCREEN_TITLE = "Blafi Adventure";

    public static final Vector2D MID_SCREENPOINT = new Vector2D(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);

    //=====================================window.Scene Constants=====================================
    public static final int MENU_SCENE = 0;
    public static final int GAME_SCENE = 1;
    public static final int EDITOR_SCENE = 2;


    public static final int tileSize = 16;
}
