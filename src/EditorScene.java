import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class EditorScene extends Scene {

    @Override
    public void update(double deltaTime) {
        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(Constants.MENU_SCENE);
        }

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT );

    }

}