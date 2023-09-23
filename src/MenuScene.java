import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScene extends Scene{




    @Override
    public void update(double deltaTime) {
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(Constants.GAME_SCENE);
        }
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_F1)){
            Window.getWindow().changeState(Constants.EDITOR_SCENE);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT );

    }
}
