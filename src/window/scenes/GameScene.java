                                                                                                                                                                                                                                               package window.scenes;

import component.Collider;

import component.Hitbox;
import component.Sound;
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
    public static Player player = new Player();

    private static GameScene gameScene = null;

//    private Sound sound = new Sound();
    public static ArrayList<Enemy> enemies = new ArrayList<>();

    private TileManager tileManager = new TileManager();
 

    public GameScene(){
        Sound.playMusic(Sound.TRACK_2.getClip());
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

        for (int i  = 0; i < enemies.size(); i ++) {
            if(enemies.get(i).isToBeDestroy()){
                enemies.remove(i);
                continue;
            }

            enemies.get(i).update(deltaTime);


        }

        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_1)){
            Sound.setVolume(0.1f, Sound.TRACK_2);
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_2)){
            Sound.setVolume(0.2f, Sound.TRACK_2);
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_3)){
            Sound.setVolume(0.3f, Sound.TRACK_2);
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_4)){
            Sound.setVolume(0.4f, Sound.TRACK_2);
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_5)){
            Sound.setVolume(0.5f, Sound.TRACK_2);
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_6)){
            Sound.setVolume(0.6f, Sound.TRACK_2);
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_7)){
            Sound.setVolume(0.7f, Sound.TRACK_2);
        }

        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_M)){
            Sound.playMusic(Sound.TRACK_2.getClip());
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_N)){
            Sound.playMusic(Sound.TRACK_1.getClip());
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

        //Sound

    }

}