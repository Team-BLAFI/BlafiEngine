package window.scenes;

import component.Collider;
import component.Hitbox;
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
    public Player player;
    private static GameScene gameScene = null;

    public static ArrayList<Enemy> enemies = new ArrayList<>();

    public GameScene(){
        player = new Player();

    }

    public static GameScene getGameScene(){
        if(GameScene.gameScene == null){
            GameScene.gameScene = new GameScene();
        }
        return GameScene.gameScene;
    }

    @Override
    public void update(double deltaTime) {
        if (enemies.isEmpty()){
            enemies.add(new Enemy(player));
        }
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
        g.setColor(Color.BLUE);
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT);
        g.setColor(Color.GREEN);
        g.drawString(displayInfo,10, (int) (WindowConstants.INSET_SIZE*1.5));


        player.draw(g);
        for (Enemy e: enemies) {
            e.draw(g);
        }


    }

}