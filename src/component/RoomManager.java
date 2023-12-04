package component;
import util.Rect;
import window.WindowConstants;
import entity.player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import static window.WindowConstants.*;

public class RoomManager {

    public static int roomWidth = 16;
    public static int roomHeight = 12;
    public static int [][] mapTileNum;
    // SCREEN CONSTANTS
    public static boolean isDoor = false;
    public static boolean isDoorOpen = false;
    private ArrayList<Room> rooms = new ArrayList<>();
    private Room currentRoom = new Room();

    int screenUnit = (int) SCREEN_UNIT*4;
    int xOffset =  WindowConstants.SCREEN_WIDTH/2 - screenUnit * roomWidth/2;
    int yOffset =  WindowConstants.SCREEN_HEIGHT/2 - screenUnit * roomHeight/2;

    public static ImageIcon[] tileSprites = new ImageIcon[48];


    public RoomManager(){
        Room currentRoom = new Room();
        rooms.add(currentRoom);
        mapTileNum = new int[WindowConstants.SCREEN_WIDTH][WindowConstants.SCREEN_HEIGHT];
        loadTileAtlas();
        currentRoom.loadMap();
    }

//    public boolean checkCollisions(Collider playerCollider) {
//        int screenUnit = (int) SCREEN_UNIT*4;
//        int xOffset =  WindowConstants.SCREEN_WIDTH/2 - screenUnit * roomWidth/2;
//        int yOffset =  WindowConstants.SCREEN_HEIGHT/2 - screenUnit * roomHeight/2;
//        for (int i = 0; i < roomHeight; i++) {
//            for (int j = 0; j < roomWidth; j++) {
//                int tileNum = mapTileNum[j][i];
//                Rect tileBounds = new Rect(j * screenUnit + xOffset, i * screenUnit + yOffset, screenUnit, screenUnit);
//                Collider tileCollider = new Collider(tileBounds);
//
//                if (playerCollider.overlaps(tileCollider)) {
//                    // Handle collision between player and tile here
//                    switch (tileNum){
//                        case 0:
//                        case 1:
//                        case 10:
//                        case 17:
//                        case 20:
//                        case 34:
//                            return playerCollider.overlaps(tileCollider);
//                        case 22:
//                        case 30:
//                        case 38:
//                        case 46:
//                            handleTileOpenDoor();
//                            return true;
//                        default:
//                            break;
//                    }
//
//                }
//            }
//        }
//        return false;
//    }


    private void handleTileOpenDoor() {
        Room currentRoom = new Room();
        isDoor = true;
    }


    public void loadTileAtlas(){
        try{
            BufferedImage sprite = ImageIO.read(new File("src/assets/roguelite_tileset.png"));
            for(int i = 0; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    int index = i * 8 + j;
                    tileSprites[index] =  new ImageIcon(sprite.getSubimage(j * 16, i * 16, 16, 16));
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void makeNewRoom(){
        currentRoom = new Room();
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void draw(Graphics g) {

        currentRoom.draw(g);

    }

    public void debugNewRoom(Player p){
        if (currentRoom.isInDoor(p.transform.getAsCollider())){
            currentRoom = new Room();
            p.transform.setPosition(SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
        }

    }
}