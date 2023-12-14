package map2;

import component.Collider;
import map.Texture;
import util.Vector2D;
import window.Window;
import window.WindowConstants;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Random;

public class Room {
    public double unit = WindowConstants.SCREEN_UNIT;
    public double tileSize = 4*unit;

    public int[][][] roomData;
    public int[][] walls;
    public int[][] floor;
    public int[][] props;
    public int[][] enemySpawns;
    public int[][] doors;
    Color c_ligther = new Color(0x6BBBBBBB, true);


    public Room(){
        try {
            Random ran = new Random();
//            int randomd
            String path = "src/assets/levels/Elevel0.dat";
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream iis = new ObjectInputStream(fis);
            roomData = (int[][][]) iis.readObject();
        }catch (Exception e){
            roomData = Rooms.room1;
        }
        walls = roomData[0];
        floor = roomData[1];
        props = roomData[2];
        enemySpawns = roomData[3];
        doors = roomData[4];
    }

    public boolean isColliding(Collider c){
        Collider wc;
        for (int y = 0; y < walls.length; y++) {
            for (int x = 0; x < walls[y].length; x++){
                if(walls[y][x]!=0){
                    wc = new Collider((int) (x*tileSize),
                            (int) (y*tileSize),
                            (int) tileSize,
                            (int) tileSize
                    );
                    if (wc.overlaps(c)){
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public void draw(Graphics g, Vector2D camera){

        drawFloors(g, camera);
        drawProps(g, camera);
        drawWalls(g, camera);
    }

    private void drawFloors(Graphics g, Vector2D camera) {
        int screenx = -(int) camera.getX();
        int screeny = -(int) camera.getY();

        for (int y = 0; y < floor.length; y++) {
            for (int x = 0; x < floor[y].length; x++){
                if(floor[y][x]==0){
                    continue;
                }
                g.drawImage(
                        Texture.textures2[floor[y][x]].img.getImage(),
                        (int) (x*tileSize + screenx),
                        (int) (y*tileSize + screeny),
                        (int) tileSize,
                        (int) tileSize,
                        null
                );
                g.setColor(c_ligther);
                g.fillRect(
                        (int) (x*tileSize + screenx),
                        (int) (y*tileSize + screeny),
                        (int) tileSize,
                        (int) tileSize
                );
            }

        }
    }
    private void drawProps(Graphics g, Vector2D camera) {
        int screenx = -(int) camera.getX();
        int screeny = -(int) camera.getY();

        for (int y = 0; y < props.length; y++) {
            for (int x = 0; x < props[y].length; x++){
                if(props[y][x]==0){
                    continue;
                }
                g.drawImage(
                        Texture.textures2[props[y][x]].img.getImage(),
                        (int) (x*tileSize + screenx),
                        (int) (y*tileSize + screeny),
                        (int) tileSize,
                        (int) tileSize,
                        null
                );
            }

        }
    }
    private void drawWalls(Graphics g, Vector2D camera) {
        int screenx = -(int) camera.getX();
        int screeny = -(int) camera.getY();

        for (int y = 0; y < walls.length; y++) {
            for (int x = 0; x < walls[y].length; x++){
                if(walls[y][x]==0){
                    continue;
                }
                g.drawImage(
                        Texture.textures2[walls[y][x]].img.getImage(),
                        (int) (x*tileSize + screenx),
                        (int) (y*tileSize + screeny),
                        (int) tileSize,
                        (int) tileSize,
                        null
                );
            }

        }
    }
}
