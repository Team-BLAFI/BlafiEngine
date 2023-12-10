package window.scenes;

import map.RoomManager;
import entity.enemy.Enemy;

import entity.player.Player;
import util.io.KL;
import window.Window;
import window.WindowConstants;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameScene extends Scene{


    private int frameRate = 0;
    private String displayInfo = "";


    public static Player player;
    private static GameScene gameScene = null;


    public static ArrayList<Enemy> enemies = new ArrayList<>();

    private RoomManager roomManager;
 

    public GameScene(){

        roomManager = new RoomManager();
        player = new Player(roomManager);

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

        roomManager.getCurrentRoom().collidesWithTiles(player.transform.getAsCollider());
        roomManager.debugNewRoom(player);

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_K)){
            roomManager.makeNewRoom();
        }





    }

    @Override
    public void draw(Graphics g) {
        //Sets color to dark gray
        g.setColor(Color.decode("#23272a"));
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT);
        roomManager.draw(g);
        g.setColor(Color.GREEN);
        g.drawString(displayInfo,10, (int) (WindowConstants.INSET_SIZE*1.5));

        player.draw(g);
        for (Enemy e: enemies) {
            e.draw(g);
        }


    }

}