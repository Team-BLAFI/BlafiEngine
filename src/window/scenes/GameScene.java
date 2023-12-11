package window.scenes;

import map.RoomManager;
import component.Collider;

import component.Hitbox;
import component.UI;
import component.Weapon;

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
    private String weaponInfo = "";
    private String displayInfo = "";


    public static Player player;
    private static GameScene gameScene = null;


    public static ArrayList<Enemy> enemies = new ArrayList<>();
    private RoomManager roomManager;

    private final UI ui = new UI(this, player.health);

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
        weaponInfo = player.weapon.toString();
        // Displays the INFO to UI
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

        // Player
        player.draw(g);
        for (Enemy e: enemies) {
            e.draw(g);
        }
        //-- UI --
        //FPS
        ui.draw(g, displayInfo, WindowConstants.SCREEN_WIDTH-(int)WindowConstants.SCREEN_UNIT*15, (int)WindowConstants.SCREEN_UNIT*5,true);
        //Health
        ui.drawHealth(g, player.health);
        ui.drawCore(g);

        //Weapon
        ui.drawBullet(g,(int)WindowConstants.SCREEN_UNIT*2, (int)WindowConstants.SCREEN_UNIT*50 );
        ui.draw(g, weaponInfo, (int)WindowConstants.SCREEN_UNIT*5, ((int)WindowConstants.SCREEN_UNIT*53),false);

    }

}