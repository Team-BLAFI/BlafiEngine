package window.scenes;

import component.TileManager;
import component.UI;
import entity.enemy.Enemy;
import weapons.WeaponPickup;
import weapons.Shotgun;

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
    public static Player player = new Player();
    private static GameScene gameScene = null;

    public static ArrayList<WeaponPickup> allWeaponPickups = new ArrayList<>();
    int pickupTest = 1;
    public static ArrayList<Enemy> enemies = new ArrayList<>();

    private TileManager tileManager = new TileManager();
    private final UI ui = new UI(this, player.health);


    public GameScene(){

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
        if (pickupTest == 1) {
            allWeaponPickups.add(new WeaponPickup(player.transform.getX(), player.transform.getY() + 120, new Shotgun(player), player));
            pickupTest-=1;
        }

        frameRate = (int) (1/deltaTime);
        weaponInfo = player.currWeapon.toString();
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
        if (!allWeaponPickups.isEmpty()) {
            for (int i = 0; i < allWeaponPickups.size(); i++) {
                if (allWeaponPickups.get(i).isPickedUp && player.isWeaponInventoryFull()) {

                } else if (allWeaponPickups.get(i).isPickedUp && !player.isWeaponInventoryFull()) {
                    allWeaponPickups.get(i).destroy();
                }

                if (allWeaponPickups.get(i).isToBeDestroy()) {
                    allWeaponPickups.remove(i);
                    continue;
                }

                allWeaponPickups.get(i).update(deltaTime);
            }
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

        // Player
        player.draw(g);
        for (Enemy e: enemies) {
            e.draw(g);
        }
        if (!allWeaponPickups.isEmpty()) {
            for (int i = 0; i < allWeaponPickups.size(); i++) {
                allWeaponPickups.get(i).draw(g);
            }
        }
        //-- UI --
        //FPS
        ui.draw(g, displayInfo, WindowConstants.SCREEN_WIDTH-(int)WindowConstants.SCREEN_UNIT*20, (int)WindowConstants.SCREEN_UNIT*5);
        //Health
        ui.drawHealth(g, player.health);
        //Weapon
        ui.drawBullet(g,(int)WindowConstants.SCREEN_UNIT*2, (int)WindowConstants.SCREEN_UNIT*7 );
        ui.draw(g, weaponInfo, (int)WindowConstants.SCREEN_UNIT*5, (WindowConstants.INSET_SIZE*5));

    }

}