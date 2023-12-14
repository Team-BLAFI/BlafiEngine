package entity.player;

import component.*;
import map.RoomManager;
import entity.Entity;

//import util.Shooting;
import util.Transform;
import util.Vector2D;
import util.io.KL;
import util.io.ML;
import weapons.*;
import window.WindowConstants;

import java.awt.*;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static window.WindowConstants.SCREEN_UNIT;

public class Player extends Entity {

    //private static final BulletType Standard = null;
    private ArrayList<Component> components = new ArrayList<>();
    public Vector2D mousePos = new Vector2D();
//    public Shooting thisShooting;
    public Weapon currWeapon;
    public int maxInventorySize = 3;
    public Weapon[] weaponInventory = new Weapon[maxInventorySize];
    public int currWeaponIndex =  0;
    public int currInventorySize = 0;
    public double switchWepCD;


    public WeaponPresets weaponPresets;
    private double unit = WindowConstants.SCREEN_UNIT;

    private RoomManager roomManager;

    public boolean isInteracting = false;


    /**<p>
     * Saves a pointer to the singleton instance of the KeyListener class
     *</p>
     */
    private KL keyListener = KL.getKeyListener();
    private ML mouseListener = ML.getMouseListener();

    public Player(RoomManager rM){
        this.roomManager = rM;
        double w = WindowConstants.SCREEN_WIDTH;
        double h = WindowConstants.SCREEN_HEIGHT;

        transform = new Transform(w/2.0,h/2, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);

        collider = new Collider(
                (int) transform.getX(),
                (int) transform.getY(),
                PlayerConstants.PLAYER_WIDTH,
                PlayerConstants.PLAYER_HEIGHT
        );

//        thisShooting = new Shooting(this);

        health = new Health(
                100.0,
                (int) (unit * 0.4),
                (int) - unit,
                this,
                true
        );
        weaponPresets = new WeaponPresets();
        switchWepCD = 1.5;
        addNewWeapon(new Pistol(this, 10, 0.3, 0.2, 6, 3));
       // new WeaponPickup(transform.getX(), transform.getY() + 40, new Shotgun(this), this);

    }

    public void draw(Graphics g){
        g.setColor(PlayerConstants.characterColor);
        g.fillRect((int) transform.getX(), (int) transform.getY(), PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(collider.Bounds.x,collider.Bounds.y,collider.Bounds.w,collider.Bounds.h);

        g.setColor(Color.YELLOW);

        health.draw(g);

        //currWeapon.draw(g);
        for (int i = 0; i < currInventorySize; i++) {
            weaponInventory[i].draw(g);
        }
    }



    public void update(double deltaTime){
        HandleMovement(deltaTime);

        Vector2D movementVector = GetMovementVector();
        collider.Bounds.setPos((int) transform.getX(), (int) transform.getY());



        if (mouseListener.isPressed(MouseEvent.BUTTON1)) {

            currWeapon.shoot(mouseListener.getX(), mouseListener.getY());
        }

        isInteracting = keyListener.isKeyDown(KeyEvent.VK_E);

        if (keyListener.isKeyDown(KeyEvent.VK_R)){
            currWeapon.reload();
        }
        if (keyListener.isKeyDown(KeyEvent.VK_N)){
            addNewWeapon(new Pistol(this, 10, 0.3, 0.2, 6, 3));
        }
        if (keyListener.isKeyDown(KeyEvent.VK_Z)){
            switchWeapon(-1);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_X)){
            switchWeapon(1);

        }

        for (int i = 0; i < currInventorySize; i++) {
            weaponInventory[i].update(deltaTime);
        }
        switchWepCD -= deltaTime;

    }

    /**
     * <p>
     * Uses the GetMovementVector() function to get information on how to move the player
     * <br>
     * Then normalizes that vector and moves the character by the unit vector multiplied by delta time and the player speed
     *</p>
     * @param deltaTime gets time since last frame to keep speed constant
     */
        private void HandleMovement(double deltaTime){
//            int screenUnit = (int) SCREEN_UNIT;
            Vector2D movementVector = GetMovementVector();

            movementVector.normalize();

            movementVector.multiply(PlayerConstants.PLAYER_SPEED * deltaTime);

            Transform newPos = new Transform(transform);

            newPos.moveXBy(movementVector.getX());
            newPos.moveYBy(movementVector.getY());


            if (!roomManager.collidesWithTiles(newPos.getAsCollider())){
                transform.setPosition(newPos.getPosition());
            }
//            transform.setPosition(newPos.getPosition());

        }

    /**
     * <p>
     * Uses the KeyListener to get the information of how to move the player
     *</p>
     * @return Point2D.Double returns the movement keys pressed as a vector to move the player by
     */
    private Vector2D GetMovementVector() {

        Vector2D movementVector = new Vector2D();

        if (keyListener.isKeyDown(KeyEvent.VK_W)) {
            movementVector.setY(movementVector.getY() - 1.0);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_S)) {
            movementVector.setY(movementVector.getY() + 1.0);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_A)) {
            movementVector.setX(movementVector.getX() - 1.0);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_D)) {
            movementVector.setX(movementVector.getX() + 1.0);
        }

        return movementVector;
    }

    public boolean isWeaponInventoryFull() {
        return currInventorySize >= maxInventorySize;
    }

    public void addNewWeapon(Weapon weapon) {
        System.out.println("initial activation");
        if (isWeaponInventoryFull()) {
            System.out.println("inventory full!");
            return;
        }
        //weaponInventory.add(weaponPresets.createShotgun(this));
        weaponInventory[currInventorySize] = weapon;
        currInventorySize++;
        currWeaponIndex = currInventorySize-1;

        setWeapon();
        System.out.println("player addNewWeapon invoked");
//        currWeapon.setRandomFireRateTest();
    }

    public void setWeapon() {
        currWeapon = weaponInventory[currWeaponIndex];
        System.out.println("setting weapon" + currWeaponIndex);
    }

    //FIXME Seems to cause bugs
    public void switchWeapon(int addIndex) {
        if (switchWepCD > 0){
            System.out.println("switch cd");
            return;
        }
        currWeaponIndex += addIndex;
        if (currWeaponIndex >= weaponInventory.length) {
            currWeaponIndex = 0;
        }
        if (currWeaponIndex < 0) {
            currWeaponIndex = weaponInventory.length - 1;
        }
        currWeapon = weaponInventory[currWeaponIndex];
        System.out.println("switched weapon!" + currWeaponIndex);
        switchWepCD = 1.5;
        Sound.EQUIP_WEP.play();

    }

    
}
