package component;
import util.Animation;
import util.Rect;
import window.WindowConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {

    private Rect GrassPos = new Rect(128,64,16,16);
    private Rect WallPos = new Rect(80, 64, 16, 16);
    // SCREEN CONSTANTS
    private double ScreenWidthMidPoint = (WindowConstants.SCREEN_WIDTH/2);
    private double ScreenHeightMidPoint = (WindowConstants.SCREEN_HEIGHT/2);

    private BufferedImage[] tileImage;
    int [][] mapTileNum;


    public TileManager(){
        tileImage = new BufferedImage[2];
        mapTileNum = new int[WindowConstants.SCREEN_WIDTH][WindowConstants.SCREEN_HEIGHT];
        getTileImage();
        loadMap();

    }

    public void loadMap() {
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/assets/TileMap.txt"));
            for (int row = 0; row < 28; row++){
              String line = br.readLine();
              for (int col = 0; col < 50; col++){
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
            tileImage[1] = sprite.getSubimage(GrassPos.x, GrassPos.y, GrassPos.w, GrassPos.h);
            // Wall Tile
            tileImage[0] = sprite.getSubimage(WallPos.x, WallPos.y, WallPos.w, WallPos.h);
        }catch (Exception e){e.printStackTrace();}
    }

    public void draw(Graphics g) {
        g.fillRect(0, 0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT);
    int x = 0;
    int y = 0;
        for(int row = 0; row < WindowConstants.SCREEN_HEIGHT; row++){
            for(int col = 0; col < WindowConstants.SCREEN_WIDTH; col++){
                int tileNum = mapTileNum[col][row];
                g.drawImage(tileImage[tileNum], x, y, 32, 32, null);
                x+=16;
            }
            x=0;
            y += 16;
        }

        //    int y = 24;
//        for(int i= 0; i<2; i++){
//
//            g.drawImage(tileImage[i], i*64,y, 64, 64, null);
//        }

//        g.drawImage(tileImage[0], 0, 0, 64, 64, null);
//
//        g.drawImage(tileImage[1], 32, 0, 64,64, null);
    }
    }