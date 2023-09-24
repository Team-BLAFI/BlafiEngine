package window.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;
import util.io.KL;
import window.Window;
import window.WindowConstants;

public class MenuScene extends Scene{




    @Override
    public void update(double deltaTime) {
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(WindowConstants.GAME_SCENE);
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_F1)){
            Window.getWindow().changeState(WindowConstants.EDITOR_SCENE);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT );

    }
}
