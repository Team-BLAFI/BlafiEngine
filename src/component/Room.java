package component;

import assets.RoomLayouts;
import util.Door;
import util.Rect;
import util.RoomTile;
import util.Transform;
import window.WindowConstants;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static window.WindowConstants.SCREEN_UNIT;

public class Room {

    private int roomHeight, roomWidth;
    private int[][] roomLayout;
    private RoomTile[][] tiles;
    int tileNum;

    int screenUnit = (int) SCREEN_UNIT*4;
    int xOffset;
    int yOffset;
    private Door roomDoor;

    public Room(){
        loadMap();
    }

    public void loadMap(){
        Random r = new Random();
        int room = r.nextInt(3);
        roomLayout = RoomLayouts.rooms[room];

        roomHeight = roomLayout.length;
        roomWidth  = roomLayout[0].length;

        xOffset =  WindowConstants.SCREEN_WIDTH/2 - screenUnit * roomWidth/2;
        yOffset =  WindowConstants.SCREEN_HEIGHT/2 - screenUnit * roomHeight/2;

        tiles = new RoomTile[roomHeight][roomWidth];

        for(int i = 0; i < roomHeight; i++){
            for(int j = 0; j< roomWidth; j++){
                ImageIcon Img = RoomManager.tileSprites[roomLayout[i][j]];
                Transform t = new Transform();
                t.setX(xOffset + j*screenUnit);
                t.setY(yOffset + i*screenUnit);
                t.setWidth(screenUnit);
                t.setHeight(screenUnit);
                boolean isSolid = RoomLayouts.Solids[roomLayout[i][j]];
                tiles[i][j] = new RoomTile(Img,t, isSolid);

                if (roomLayout[i][j] == 22 ||
                        roomLayout[i][j] == 38 ||
                        roomLayout[i][j] == 30 ||
                        roomLayout[i][j] == 46)
                {
                    roomDoor = new Door(t);
                }
            }
        }
    }

    public boolean collidesWithTiles(Collider c){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {

                if (c.overlaps(tiles[i][j].getCollider()) && tiles[i][j].getIsSolid()){
                        tileNum = roomLayout[i][j];
                        return true;
                }
            }
        }
        tileNum = -1;
        return false;
    }


    public boolean isInDoor(Collider c){
        return roomDoor.getCollider().overlaps(c);
    }



    public void draw(Graphics g){
        for(int i = 0; i < roomHeight; i++){
            for(int j = 0; j< roomWidth; j++){
                tiles[i][j].draw(g);
            }
        }
        debugInfo(g);
    }

    public void debugInfo(Graphics g) {
        g.setColor(Color.WHITE);
        Font myFont = new Font ("Courier New", 1, 17);
        g.setFont(myFont);

        g.drawString(String.format("Collides with solid tile: %d ",
                tileNum),
                WindowConstants.SCREEN_WIDTH-300,
                (int) (WindowConstants.INSET_SIZE*1.5)
        );
    }



}
