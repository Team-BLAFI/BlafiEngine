package map;

import component.Collider;
import util.Door;
import util.Transform;
import window.WindowConstants;
import component.Projectile;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static window.WindowConstants.SCREEN_UNIT;

public class Room {

    private int roomHeight, roomWidth;

    private int[][] roomLayout;
    private Tile[][] tiles;

    int tileNum;

    int screenUnit = (int) SCREEN_UNIT*4;
    int xOffset;
    int yOffset;
    private Door roomDoor;

    public Collider overlappedCollider;

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

        tiles = new Tile[roomHeight][roomWidth];

//                ImageIcon Img = Texture.textures[i*roomHeight+j].img;
        for(int i = 0; i < roomHeight; i++){
            for(int j = 0; j< roomWidth; j++){
                Transform t = new Transform(xOffset + j*screenUnit, yOffset + i*screenUnit,screenUnit,screenUnit);
                boolean isSolid = RoomLayouts.Solids[roomLayout[i][j]];
                tiles[i][j] = new Tile(roomLayout[i][j], t, isSolid);

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
        boolean ret = false;

        for (Tile[] tArray:tiles) {
            for (Tile tile:tArray) {
                if(tile.getIsSolid() && tile.getCollider().overlaps(c)){
                    ret = true;
                    overlappedCollider = tile.getCollider();
                    if (c.bullet != null) {
                        Projectile bullet = c.getBullet();
                        bullet.setOverlappedTile(overlappedCollider);
                    }
                    overlappedCollider = null;
                }
            }
        }

        return ret;
    }


    public boolean isInDoor(Collider c){
        return roomDoor.getCollider().overlaps(c);
    }

    public void draw(Graphics g){
        for(int i = 0; i < roomHeight; i++){
            for(int j = 0; j< roomWidth; j++){
                try {

                    tiles[i][j].draw(g);
                }catch (Exception e){

                }
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
