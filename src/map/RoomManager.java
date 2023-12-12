package map;
import component.Collider;
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

    private ArrayList<Room> rooms = new ArrayList<>();
    private Room currentRoom;



    public RoomManager(){
        currentRoom = new Room();
        rooms.add(currentRoom);
        currentRoom.loadMap();
    }

    public boolean collidesWithTiles(Collider c){
        return currentRoom.collidesWithTiles(c);
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