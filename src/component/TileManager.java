package component;
import entity.player.PlayerConstants;
import util.Rect;
import util.Transform;
import util.Vector2D;
import window.WindowConstants;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static window.WindowConstants.SCREEN_UNIT;

public class TileManager {

    private Rect grassPos = new Rect(128,64,16,16);
    private Rect wallPos = new Rect(80, 64, 16, 16);
    // SCREEN CONSTANTS
    private int roomSize = 13;

    private Tile[] tileImage;
    int [][] mapTileNum;

    public TileManager(){
        tileImage = new Tile[2];
        mapTileNum = new int[WindowConstants.SCREEN_WIDTH][WindowConstants.SCREEN_HEIGHT];
        getTileImage();
        loadMap();
    }

    public void checkCollisions(Collider playerCollider) {
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
                    System.out.println("Collision detected!");
                }
            }
        }
    }

    /**<p>
     * Loads up map from text file called TileMap.txt
     * </p>
     * */
    public void loadMap() {
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/assets/TileMap.txt"));
            for (int row = 0; row < roomSize; row++){
              String line = br.readLine();
              for (int col = 0; col < roomSize; col++){
                  String[] numbers = line.split(" ");

                  int num = Integer.parseInt(numbers[col]);
                  mapTileNum[col][row] = num;

              }
            }
            br.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public void getTileImage(){
        try{
            BufferedImage sprite = ImageIO.read(new File("src/assets/grasstileset.png"));
            // Grass Tile
            tileImage[1] = new Tile(new ImageIcon(sprite.getSubimage(grassPos.x, grassPos.y, grassPos.w, grassPos.h)));

            // Wall Tile
            tileImage[0] = new Tile(new ImageIcon(sprite.getSubimage(wallPos.x, wallPos.y, wallPos.w, wallPos.h)));

        }catch (Exception e){e.printStackTrace();}
    }


    public void draw(Graphics g) {
        int inset = WindowConstants.INSET_SIZE;
        int screenUnit = (int) SCREEN_UNIT*4;
        int xOffset =  WindowConstants.SCREEN_WIDTH/2 - screenUnit * roomSize/2;
        int yOffset =  WindowConstants.SCREEN_HEIGHT/2 - screenUnit * roomSize/2;

        for(int i = 0; i < roomSize; i++){
            for(int j = 0; j< roomSize; j++){
                int tileNum = mapTileNum[j][i];
                g.drawImage(tileImage[tileNum].image.getImage(), j*screenUnit+ xOffset, i*screenUnit + yOffset ,screenUnit, screenUnit, null);

            }
        }

    }
    }