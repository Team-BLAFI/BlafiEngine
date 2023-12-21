package window.scenes;

import util.io.Sound;

import window.UI;

import entity.enemy.Enemy;
import map.Camera;
import map.Room;
import util.Transform;
import util.Vector2D;
import weapons.WeaponPickup;

import entity.player.Player;
import util.io.KL;
import window.Window;
import window.WindowConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameScene extends Scene {

    private int frameRate = 0;
    private String weaponInfo = "";
    private String displayInfo = "";

    boolean pickupTest = true;

    Camera mainCam;
    Room room;

    private UI ui;


    private boolean isMute = false;
    private double muteCD= 0.0;
//    private Sound sound = new Sound();

    public static Player player;
    private static GameScene gameScene = null;

    public static ArrayList<WeaponPickup> allWeaponPickups;

    public static ArrayList<Enemy> enemies;


    public GameScene(){
        room = new Room();
        Vector2D playerSpawn = room.getPlayerSpawnPoint();
        player = new Player(room,playerSpawn.getX(), playerSpawn.getY());
        enemies = new ArrayList<>();
        mainCam = new Camera(player);
        Sound.setVolume(-15f, Sound.TRACK_1);
        Sound.playMusic(Sound.TRACK_1.getClip());

        allWeaponPickups = new ArrayList<>();

        ui = new UI(this, player.health);
        generateEnemies();
    }

    public static GameScene getGameScene(){
        if(GameScene.gameScene == null){
            GameScene.gameScene = new GameScene();

        }
        return GameScene.gameScene;
    }

    @Override
    public void update(double deltaTime) {
        frameRate = (int) (1 / deltaTime);
        displayInfo = String.format("%d FPS (%.3f)", frameRate, deltaTime);

        if (player.isDead) {
            window.Window.getWindow().changeState(WindowConstants.GAMEOVER_SCENE);
        }

        mainCam.update(deltaTime);

        muteCD -= deltaTime;

        try {
            weaponInfo = player.currWeapon.toString();
        } catch (Exception e) {
            weaponInfo = "";
        }

        player.update(deltaTime);

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isToBeDestroy()) {
                enemies.remove(i);
                continue;
            }
            enemies.get(i).update(deltaTime);
        }

        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_1)){
            Sound.VolumeUp(Sound.TRACK_1, deltaTime);
            System.out.println(Sound.getVolume(Sound.TRACK_1));
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_2)){
            Sound.VolumeDown(Sound.TRACK_1, deltaTime);
            System.out.println(Sound.getVolume(Sound.TRACK_1));
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_M) && muteCD < 0){
            muteCD = 0.2;
            if(isMute){
                isMute = false;
                Sound.setVolume(-15f,Sound.TRACK_1);

            }else{
                isMute = true;
                Sound.setVolume(-80f,Sound.TRACK_1);
            }
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_U)){
            Sound.playMusic(Sound.TRACK_2.getClip());
        }
        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_I)){
            Sound.playMusic(Sound.TRACK_1.getClip());
        }

        handleWeaponPickups(deltaTime);

        if (KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)) {
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }

    }

    public static int getEnemyCount(){
        try {
            return enemies.size();
        }catch (Exception e){
            return 0;
        }
    }

    private void generateEnemies() {
        ArrayList<Transform> e;
        e = room.getTransformsForEnemies();
        for (Transform t: e){
            Enemy ne = new Enemy(player, room);
            ne.transform = t;
            enemies.add(ne);
        }
    }
    @Override
    public void draw(Graphics g) {
        //Sets color to dark gray
        g.setColor(Color.decode("#23272a"));
        g.fillRect(0, 0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT);
        g.setColor(Color.GREEN);

        room.draw(g, mainCam.transform.getPosition());

        // Player
        player.draw(g, mainCam.transform.getPosition());
        for (Enemy e : enemies) {
            e.draw(g, mainCam.transform.getPosition());
        }
        if (!allWeaponPickups.isEmpty()) {
            for (int i = 0; i < allWeaponPickups.size(); i++) {
                allWeaponPickups.get(i).draw(g, mainCam.transform.getPosition());
            }
        }
        //-- UI --
        //FPS
        ui.draw(g, displayInfo, WindowConstants.SCREEN_WIDTH - (int) WindowConstants.SCREEN_UNIT * 15, (int) WindowConstants.SCREEN_UNIT * 5, true);
        //Health
        ui.drawHealth(g, player.health);
        ui.drawCore(g);
        ui.drawRemainingEnemies(g);

        //Sound

        //Weapon
        ui.drawBullet(g);
        ui.draw(g, weaponInfo, (int) WindowConstants.SCREEN_UNIT * 5, ((int) WindowConstants.SCREEN_UNIT * 53), false);

    }

    public void handleWeaponPickups(double deltaTime) {
        if (!allWeaponPickups.isEmpty()) {
            for (int i = 0; i < allWeaponPickups.size(); i++) {
                if (player.isInteracting && allWeaponPickups.get(i).canBePickedUp && player.isWeaponInventoryFull()) {

                    WeaponPickup temp = new WeaponPickup(
                            player.transform.getCenterX(),
                            player.transform.getCenterY(),
                            player.weaponInventory[player.currWeaponIndex],
                            player
                    );

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
        Font myFont = new Font("Courier New", 1, 20);
        g.setFont(myFont);

        g.drawString(String.format("Curr/Max WepInv: %d/%d", player.currWeaponIndex + 1, player.maxInventorySize), WindowConstants.SCREEN_WIDTH - 300, (int) (WindowConstants.INSET_SIZE * 3.5));

    }

}