package map2;

import map.Texture;
import util.Vector2D;
import window.Window;
import window.WindowConstants;

import javax.swing.*;
import java.awt.*;

public class Room {
    public double unit = WindowConstants.SCREEN_UNIT;
    public double tileSize = 4*unit;

    int[][] walls;
    int[][] floor;
    int[][] props;
    Vector2D position;

    public Room(){
        walls = Rooms.room1[0];
        floor = Rooms.room1[1];
        props = Rooms.room1[2];
        position = new Vector2D();
    }

    public void draw(Graphics g, Vector2D camera){

        drawFloors(g, camera);
        drawWalls(g, camera);
//        drawProps(g, camera);
    }

    private void drawFloors(Graphics g, Vector2D camera) {
        int screenx = -(int) camera.getX();
        int screeny = -(int) camera.getY();

        for (int y = 0; y < floor.length; y++) {
            for (int x = 0; x < floor[y].length; x++){
                g.drawImage(
                        Texture.textures2[floor[y][x]].img.getImage(),
                        (int) (x*tileSize + screenx),
                        (int) (y*tileSize + screeny),
                        (int) tileSize,
                        (int) tileSize,
                        null
                );
            }

        }
    }
    private void drawProps(Graphics g, Vector2D camera) {
        int screenx = -(int) camera.getX();
        int screeny = -(int) camera.getY();

        for (int y = 0; y < floor.length; y++) {
            for (int x = 0; x < floor[y].length; x++){
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

        for (int y = 0; y < floor.length; y++) {
            for (int x = 0; x < floor[y].length; x++){
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
