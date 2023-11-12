package component;
import window.WindowConstants;
import util.Transform;

import javax.swing.*;
import java.awt.*;

public class Tile {
    private int roomSize = 13;
    private int screenUnit = (int)WindowConstants.SCREEN_UNIT*4;
    private int xOffset =  WindowConstants.SCREEN_WIDTH/2 - screenUnit * roomSize/2;
    private int yOffset =  WindowConstants.SCREEN_HEIGHT/2 - screenUnit * roomSize/2;


    public ImageIcon image;
    Transform transform;

    public Tile(ImageIcon i){
        this(i,0,0,0,0);
    }

    public Tile(ImageIcon i, int x, int y){
        this(i,x,y,0,0);
    }

    public Tile(ImageIcon i, int x, int y, int w, int h){
        image = i;
        transform = new Transform(x,y,w,h);
    }


    public void draw(Graphics g){
        g.drawImage(image.getImage(), (int) transform.position.x, (int)transform.position.y ,(int)transform.size.x, (int)transform.size.x, null);
    }

}
