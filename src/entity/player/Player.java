package entity.player;

import component.Health;


import entity.Entity;

//import util.Shooting;
import map.Room;
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

public class Player extends Entity {

    private ArrayList<Component> components = new ArrayList<>();
    public Vector2D mousePos = new Vector2D();
    public Weapon currWeapon;
    public int maxInventorySize = 3;

    public Weapon[] weaponInventory = new Weapon[maxInventorySize];
    public int currWeaponIndex = 0;
    public int currInventorySize = 0;
    public double switchWepCD;

    public WeaponPresets weaponPresets;
    private double unit = WindowConstants.SCREEN_UNIT;

    public boolean isInteracting = false;


    /**
     * <p>
     * Saves a pointer to the singleton instance of the KeyListener class
     * </p>
     */
    private KL keyListener = KL.getKeyListener();
    private ML mouseListener = ML.getMouseListener();
    Room currentRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;

        double w = WindowConstants.SCREEN_WIDTH;
        double h = WindowConstants.SCREEN_HEIGHT;

        transform = new Transform(w / 2.0, h / 2, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);


        health = new Health(100.0, (int) (unit * 0.4), (int) -unit, this, true);
        weaponPresets = new WeaponPresets();
        switchWepCD = 1.5;
        addNewWeapon(new Pistol(this, 10, 0.3, 0.2, 6, 3));

    }

    public void draw(Graphics g, Vector2D camera) {

        int x = (int) (transform.getX() - camera.getX());
        int y = (int) (transform.getY() - camera.getY());

        g.setColor(PlayerConstants.characterColor);
        g.fillRect(x, y, PlayerConstants.PLAYER_WIDTH, PlayerConstants.PLAYER_HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(x, y, (int) transform.getWidth(), (int) transform.getHeight());

        g.setColor(Color.YELLOW);

        health.draw(g, camera);

        for (int i = 0; i < currInventorySize; i++) {
            weaponInventory[i].draw(g, camera);
        }
    }

    public void update(double deltaTime) {
        HandleMovement(deltaTime);

        if (mouseListener.isPressed(MouseEvent.BUTTON1)) {
            Vector2D v = new Vector2D(mouseListener.getX(), mouseListener.getY());
            v.subtract(new Vector2D(WindowConstants.SCREEN_WIDTH / 2, WindowConstants.SCREEN_HEIGHT / 2));
            v.normalize();
            currWeapon.shootT(v);
        }

        isInteracting = keyListener.isKeyDown(KeyEvent.VK_E);

        if (keyListener.isKeyDown(KeyEvent.VK_R)) {
            currWeapon.reload();
        }
        if (keyListener.isKeyDown(KeyEvent.VK_N)) {
            addNewWeapon(new Pistol(this, 10, 0.3, 0.2, 6, 3));
        }
        if (keyListener.isKeyDown(KeyEvent.VK_Z)) {
            switchWeapon(-1);
        }
        if (keyListener.isKeyDown(KeyEvent.VK_X)) {
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
     * </p>
     *
     * @param deltaTime gets time since last frame to keep speed constant
     */
    private void HandleMovement(double deltaTime) {

        Vector2D movementVector = GetMovementVector();

        movementVector.normalize();

        movementVector.multiply(PlayerConstants.PLAYER_SPEED * deltaTime);

        Transform newPos = new Transform(transform);

        newPos.moveXBy(movementVector.getX());
        newPos.moveYBy(movementVector.getY());

        //if it's not colliding set current position to new position
        if (!currentRoom.isColliding(newPos.getAsCollider())){
            transform.setPosition(newPos.getPosition());
        }
    }

    /**
     * <p>
     * Uses the KeyListener to get the information of how to move the player
     * </p>
     *
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
        currWeaponIndex = currInventorySize - 1;

        setWeapon();
        System.out.println("player addNewWeapon invoked");
    }

    public void setWeapon() {
        currWeapon = weaponInventory[currWeaponIndex];
        System.out.println("setting weapon" + currWeaponIndex);
    }

    //FIXME Seems to cause bugs
    public void switchWeapon(int addIndex) {
        if (switchWepCD > 0) {
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
    }


}
