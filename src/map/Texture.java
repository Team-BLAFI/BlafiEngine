package map;

import util.Rect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.server.ExportException;

public class Texture {

    public static Texture[] textures = new Texture[100];
    public static Texture[] textures2 = new Texture[100];
    public ImageIcon img;


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

    public static void loadNewTiles(){
        System.out.println("Loading New Tiles");
        //FIXME: Change name please
        try {
            BufferedImage atlas = ImageIO.read(new File("src/assets/Tilesets/TileSet v1.0.png"));

            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 8; x++){
                    ImageIcon img = new ImageIcon(atlas.getSubimage(32*x,32*y,32,32));
                    textures2[y* 8 +x] = new Texture(img);
                }

            }
        }catch (Exception e){

        }

    }
}
