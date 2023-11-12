package window.scenes;

import component.Collider;

import component.Hitbox;
import component.TileManager;
import entity.enemy.Enemy;

import entity.player.Player;
import util.Rect;
import util.io.KL;
import window.Window;
import window.WindowConstants;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameScene extends Scene{


    private int frameRate = 0;
    private String displayInfo = "";
    private Player player = new Player();

    private TileManager tileManager = new TileManager();
    public ArrayList<Enemy> enemies = new ArrayList<>();

    public GameScene(){
        enemies.add(new Enemy(player.transform));
    }



    @Override
    public void update(double deltaTime) {
        frameRate = (int) (1/deltaTime);
        displayInfo = String.format("%d FPS (%.3f)", frameRate,deltaTime);

        player.update(deltaTime);
        for (Enemy e: enemies) {
            e.update(deltaTime);
        }

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }



    }

    @Override
    public void draw(Graphics g) {
        //Sets color to dark gray
        g.setColor(Color.decode("#23272a"));
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT);
        tileManager.draw(g);
        g.setColor(Color.GREEN);
        g.drawString(displayInfo,10, (int) (WindowConstants.INSET_SIZE*1.5));

        player.draw(g);
        for (Enemy e: enemies) {
            e.draw(g);
        }


    }
}
