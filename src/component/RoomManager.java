package component;
import util.Rect;
import window.WindowConstants;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static window.WindowConstants.SCREEN_UNIT;

public class RoomManager {

    // SCREEN CONSTANTS
    private int roomSize = 13;

    private RoomTile[] roomImage;
    int [][] mapTileNum;

    public RoomManager(){
        roomImage = new RoomTile[28];
        mapTileNum = new int[WindowConstants.SCREEN_WIDTH][WindowConstants.SCREEN_HEIGHT];
        getTileImage();
        loadMap();
    }

    public boolean checkCollisions(Collider playerCollider) {
        int screenUnit = (int) SCREEN_UNIT*4;
        int xOffset =  WindowConstants.SCREEN_WIDTH/2 - screenUnit * roomSize/2;
        int yOffset =  WindowConstants.SCREEN_HEIGHT/2 - screenUnit * roomSize/2;
        for (int i = 0; i < roomSize; i++) {
            for (int j = 0; j < roomSize; j++) {
                int tileNum = mapTileNum[j][i];
                Rect tileBounds = new Rect(j * screenUnit + xOffset, i * screenUnit + yOffset, screenUnit, screenUnit);
                Collider tileCollider = new Collider(tileBounds);

                if (playerCollider.overlaps(tileCollider)) {
                    // Handle collision between player and tile here
                    switch (tileNum){
                        case 1:
                        case 7:
                        case 9:
                        case 14:
                        case 16:
                        case 22:
                            //System.out.println("Collision detected!");
                            return playerCollider.overlaps(tileCollider);

                        default:
                            break;
                    }

                }
            }
        }
        return false;
    }

    /**<p>
     * Loads up map from text file called TileMap.txt
     * </p>
     * */

    public void loadMap() {
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/assets/roguelite.txt"));
            for (int row = 0; row < roomSize; row++){
              String line = br.readLine();

              for (int col = 0; col < roomSize; col++){
                  String[] numbers = line.split(" ");
                  int num = Integer.parseInt(numbers[col]);
                  mapTileNum[col][row] = num;
              }
            }
            br.close();
        }catch(IOException | NumberFormatException e){e.printStackTrace();}
    }

    public void getTileImage(){
        try{
            BufferedImage sprite = ImageIO.read(new File("src/assets/tileset_roguelite.png"));
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 7; j++){
                    int index = i * 7 + j;
                    roomImage[index] = new RoomTile(new ImageIcon(sprite.getSubimage(j*16, i*16, 16, 16)));
                }
            }


        }catch (Exception e){e.printStackTrace();}
    }

    public void draw(Graphics g) {
        int screenUnit = (int) SCREEN_UNIT*4;
        int xOffset =  WindowConstants.SCREEN_WIDTH/2 - screenUnit * roomSize/2;
        int yOffset =  WindowConstants.SCREEN_HEIGHT/2 - screenUnit * roomSize/2;

        for(int i = 0; i < roomSize; i++){
            for(int j = 0; j< roomSize; j++){
                int tileNum = mapTileNum[j][i];
                g.drawImage(roomImage[tileNum].image.getImage(), j*screenUnit+ xOffset, i*screenUnit + yOffset ,screenUnit, screenUnit, null);

            }
        }

    }
    }