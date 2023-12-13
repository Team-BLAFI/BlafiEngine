package map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.server.ExportException;

public class Texture {

    public static Texture[] textures = new Texture[100];
    ImageIcon img;


    public Texture(String path) {

        try {
            System.out.println("Loading..." + path);
            BufferedImage img =  ImageIO.read(new File(path));
            this.img = new ImageIcon(img);
        }catch (Exception e){

        }
    }

    public Texture(ImageIcon img){
        this.img = img;
    }

    public static void loadTileAtlas(){
        System.out.println("loading texture atlas");
        try{
            BufferedImage sprite = ImageIO.read(new File("src/assets/roguelite_tileset.png"));
            for(int i = 0; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    int index = i * 8 + j;
                    textures[index] = new Texture(new ImageIcon(sprite.getSubimage(j * 16, i * 16, 16, 16)));
                }
            }
        }catch (Exception e){
            System.out.println("fail to load tile atlas");
            e.printStackTrace();}
    }
}
