package window.scenes;

import map.RoomManager;
import component.Collider;

import component.Hitbox;
import component.UI;

import entity.enemy.Enemy;
import map2.Camera;
import map2.Room;
import weapons.Pistol;
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


    public static Player player;
    private static GameScene gameScene = null;

    public static ArrayList<WeaponPickup> allWeaponPickups = new ArrayList<>();
    int pickupTest = 1;
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    Camera mainCam;
    Room room;


    private UI ui;

    public GameScene(){
        room = new Room();
        player = new Player();
        mainCam = new Camera(player);
        ui = new UI(this, player.health);
    }

    public static GameScene getGameScene(){
        if(GameScene.gameScene == null){
            GameScene.gameScene = new GameScene();
        }
        return GameScene.gameScene;
    }

    @Override
    public void update(double deltaTime) {
        mainCam.update(deltaTime);
        for (Enemy e: enemies){
            if (e.health.getHealth() <= 0){
                allWeaponPickups.add(new WeaponPickup(e.transform.getX(), e.transform.getY(), new Shotgun(player, 69, 0.069, 0.69, 69, 1,69), player));
            }
        }
        if (enemies.isEmpty()){
            enemies.add(new Enemy(player));
        }

        //FIXME Need to figure out logic for properly spawning weaponpickups in different spots
        if (pickupTest == 1) {
            allWeaponPickups.add(new WeaponPickup(700, 500, new Shotgun(player), player));
            allWeaponPickups.add(new WeaponPickup(500, 300, new Pistol(player, 69, 0.069, 0.69, 69, 1), player));
            allWeaponPickups.add(new WeaponPickup(800, 600, new Shotgun(player, 69, 0.069, 0.69, 69, 1,69), player));

            pickupTest-=1;
        }

        frameRate = (int) (1/deltaTime);
        try {
            weaponInfo = player.currWeapon.toString();
        } catch(Exception e) {
            weaponInfo = "";
        }
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


        handleWeaponPickups(deltaTime);

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }


    }

    @Override
    public void draw(Graphics g) {
        //Sets color to dark gray
        g.setColor(Color.decode("#23272a"));
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT);
        g.setColor(Color.GREEN);

        room.draw(g,mainCam.transform.getPosition());
        debugWepInfo(g);

        // Player
        player.draw(g,mainCam.transform.getPosition());
        for (Enemy e: enemies) {
            e.draw(g,mainCam.transform.getPosition());
        }
        if (!allWeaponPickups.isEmpty()) {
            for (int i = 0; i < allWeaponPickups.size(); i++) {
                allWeaponPickups.get(i).draw(g,mainCam.transform.getPosition());
            }
        }
        //-- UI --
        //FPS
        ui.draw(g, displayInfo, WindowConstants.SCREEN_WIDTH-(int)WindowConstants.SCREEN_UNIT*15, (int)WindowConstants.SCREEN_UNIT*5,true);
        //Health
        ui.drawHealth(g, player.health);
        ui.drawCore(g);

        //Weapon
        ui.drawBullet(g);
        ui.draw(g, weaponInfo, (int)WindowConstants.SCREEN_UNIT*5, ((int)WindowConstants.SCREEN_UNIT*53),false);



    }

    public void handleWeaponPickups(double deltaTime) {
        if (!allWeaponPickups.isEmpty()) {
            for (int i = 0; i < allWeaponPickups.size(); i++) {
                if (player.isInteracting && allWeaponPickups.get(i).canBePickedUp && player.isWeaponInventoryFull()) {

                    WeaponPickup temp = new WeaponPickup(player.transform.getX()+80, player.transform.getY(), player.weaponInventory[player.currWeaponIndex], player);

                    player.weaponInventory[player.currWeaponIndex] = allWeaponPickups.get(i).getWeapon();
                    allWeaponPickups.set(i, temp);

                    player.setWeapon();
                    player.isInteracting = false;
                } else if (allWeaponPickups.get(i).canBePickedUp && !player.isWeaponInventoryFull()) {
                    player.addNewWeapon(allWeaponPickups.get(i).getWeapon());
                    allWeaponPickups.get(i).destroy();
                    player.setWeapon();
                }

                if (allWeaponPickups.get(i).isToBeDestroy()) {
                    allWeaponPickups.remove(i);
                    continue;
                }

                allWeaponPickups.get(i).update(deltaTime);
            }
        }
    }

    public void debugWepInfo(Graphics g) {
        g.setColor(Color.WHITE);
        Font myFont = new Font ("Courier New", 1, 20);
        g.setFont(myFont);

        g.drawString(String.format("Curr/Max WepInv: %d/%d", player.currWeaponIndex+1, player.maxInventorySize),WindowConstants.SCREEN_WIDTH-300, (int) (WindowConstants.INSET_SIZE*3.5));

    }

}