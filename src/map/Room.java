package map;

import component.Collider;
import entity.enemy.EnemyConstants;
import util.Transform;
import util.Vector2D;
import window.WindowConstants;

import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    public double unit = WindowConstants.SCREEN_UNIT;
    public double tileSize = 10*unit;

    public int[][][] roomData;
    public int[][] walls;
    public int[][] floor;
    public int[][] props;
    public int[][] enemySpawns;
    public int[][] doors;
    boolean isLock = true;
    Color c_lighter = new Color(0x6BBBBBBB, true);

    public Room(){
        try {
            Random ran = new Random();
            int rNum = ran.nextInt(6);
            String path = String.format("src/assets/levels/Level%d.dat",5);
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream iis = new ObjectInputStream(fis);
            roomData = (int[][][]) iis.readObject();
        }catch (Exception e){
            roomData = Rooms.room1;
        }
        walls = roomData[0];
        floor = roomData[1];
        props = roomData[2];
        doors = roomData[3];
        enemySpawns = roomData[4];
    }

    public Room(String path){
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream iis = new ObjectInputStream(fis);
            roomData = (int[][][]) iis.readObject();
        }catch (Exception e){
            roomData = Rooms.room1;
        }
        walls = roomData[0];
        floor = roomData[1];
        props = roomData[2];
        doors = roomData[3];
        enemySpawns = roomData[4];
    }

    public ArrayList<Transform> getTransformsForEnemies(){
        ArrayList<Transform> e = new ArrayList<>();

        for (int y = 0; y < enemySpawns.length; y++) {
            for (int x = 0; x < enemySpawns[y].length; x++){
                if(enemySpawns[y][x]==1){
                    Transform t = new Transform(x*tileSize,y*tileSize, EnemyConstants.ENEMY_WIDTH,EnemyConstants.ENEMY_HEIGHT);
                    e.add(t);
                }

            }
        }
        return e;
    }

    public boolean isColliding(Collider c){
        Collider wc;
        for (int y = 0; y < walls.length; y++) {
            for (int x = 0; x < walls[y].length; x++){
                if(walls[y][x]!=0 || doors[y][x]!=0){
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

    public Vector2D getPlayerSpawnPoint(){
        for (int y = 0; y < enemySpawns.length; y++) {
            for (int x = 0; x < enemySpawns[y].length; x++) {
                if (enemySpawns[y][x] == 2) {
                    return new Vector2D(x * tileSize, y * tileSize);
                }
            }
        }
        return new Vector2D();
    }

    public void draw(Graphics g, Vector2D camera){
        drawFloors(g, camera);
        drawProps(g, camera);
        drawWalls(g, camera);
        drawArray(g,camera,doors,false);
    }

    private void drawFloors(Graphics g, Vector2D camera) {
        drawArray(g,camera,floor,true);
    }

    private void drawProps(Graphics g, Vector2D camera) {
        drawArray(g,camera,props,false);
    }

    private void drawWalls(Graphics g, Vector2D camera) {
        drawArray(g,camera,walls,false);
    }

    private void drawArray(Graphics g, Vector2D camera,int[][] array , boolean useShader){

        int screenx = -(int) camera.getX();
        int screeny = -(int) camera.getY();

        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[y].length; x++){
                if(array[y][x]==0){
                    continue;
                }
                g.drawImage(
                        Texture.textures2[array[y][x]].img.getImage(),
                        (int) (x*tileSize + screenx),
                        (int) (y*tileSize + screeny),
                        (int) tileSize,
                        (int) tileSize,
                        null
                );
                if (useShader){
                    g.setColor(c_lighter);
                    g.fillRect(
                            (int) (x*tileSize + screenx),
                            (int) (y*tileSize + screeny),
                            (int) tileSize,
                            (int) tileSize
                    );
                }
            }

        }

    }
}
