package window.scenes;

import component.Collider;
import component.Hitbox;
import entity.player.Player;
import util.Rect;
import util.io.KL;
import window.Window;
import window.WindowConstants;


import java.awt.*;
import java.awt.event.KeyEvent;

public class GameScene extends Scene{


    private int _frameRate = 0;
    private String _displayInfo = "";
    private Player _player = new Player();


    Rect R = new Rect(400,150,40,40);
    private Collider h = new Collider(R);





    @Override
    public void update(double deltaTime) {
        _frameRate = (int) (1/deltaTime);
        _displayInfo = String.format("%d FPS (%.3f)", _frameRate,deltaTime);

        _player.update(deltaTime);

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }



    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT);
        g.setColor(Color.GREEN);
        g.drawString(_displayInfo,10, (int) (WindowConstants.INSET_SIZE*1.5));

        if(_player.h.overlaps(h)){
            System.out.println("Hurt!!!");
            g.setColor(Color.RED);
            g.drawRect(R.x,R.y,R.w,R.h);
        }else {
            g.setColor(Color.BLACK);
            g.drawRect(R.x,R.y,R.w,R.h);
        }


        _player.draw(g);


    }
}
