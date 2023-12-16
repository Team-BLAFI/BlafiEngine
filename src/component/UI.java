package component;
import java.awt.Graphics2D;

import entity.player.Player;
import util.Rect;
import window.Window;
import window.scenes.GameScene;
import window.scenes.Scene;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import static window.WindowConstants.SCREEN_UNIT;
import javax.imageio.ImageIO;
import javax.swing.*;

public class UI {
    Scene gp;
    Font arial_40;
    Font arcadeFont;
    double unit = SCREEN_UNIT;
    //Health Variables
    Health PlayerHealth;
    private ImageIcon bar;
    private ImageIcon bullet;
    private ImageIcon greenCore;
    private ImageIcon barFill;
    private ImageIcon barOutline;
    public static String bitFontName;



    public UI(Scene gp, Health ph){
        // importing Custom Font
        File greenCoreFile = new File("src/assets/blafiGreenCore2.png");

        try {
            Font customFont = (Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/04B_30__.ttf")));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            bitFontName = customFont.getFontName();

            ge.registerFont(customFont);

        } catch (IOException | FontFormatException e) {
            //Handle exception
        }
        this.gp = gp;
        this.PlayerHealth = ph;
        arial_40 = new Font("Arial", Font.BOLD,25);
        arcadeFont = new Font(bitFontName, Font.TRUETYPE_FONT, 30);

        try{
            BufferedImage bulletSprite = ImageIO.read(new File("src/assets/bullet.png"));
            bullet = new ImageIcon(bulletSprite.getSubimage(10,0,11,31));
        }catch (Exception e) {
            e.printStackTrace();
        }

        // Read File
        try{
            BufferedImage healthBarSprite = ImageIO.read(new File("src/assets/uiPack2.png"));
            BufferedImage healthSprite = ImageIO.read(new File("src/assets/uiPack2.png"));

            bar = new ImageIcon(healthBarSprite.getSubimage(288,35,46,11));
            barFill = new ImageIcon(healthSprite.getSubimage(243,21,43,6));

        }catch (Exception e) {
            e.printStackTrace();
        }

        try{
            BufferedImage greenCoreSprite = ImageIO.read(greenCoreFile);
            greenCore= new ImageIcon(greenCoreSprite.getSubimage(3,4,122,119));
        }catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void draw(Graphics g , String Text, int x, int y,boolean FPS){
        if(FPS){
            g.setFont(arial_40);
        }else{
            g.setFont(arcadeFont);
        }
        g.setColor(Color.WHITE);

        // Draws the string with coordinates in gamescene
        g.drawString(Text,x,y);

    }
    public void drawBullet(Graphics g){
        g.drawImage(
                bullet.getImage(),
                (int)unit*4,
                (int)unit*53,
                25,
                62,
                null);
    }

    public void drawCore(Graphics g){
        g.drawImage(
                greenCore.getImage(),
                (int)unit-5,
                (int)unit*3-5,
                135,
                129,
                null
        );
    }

    public void drawRemainingEnemies(Graphics g){
       g.setColor(Color.WHITE);
       g.setFont(arcadeFont);
       g.drawString(String.format("%d",GameScene.getEnemyCount()),(int)unit * 50, (int) (unit * 5));
    }
    public void drawHealth(Graphics g, Health ph){

        /* -- Health -- */
        // bar fill
        if (ph.getHealth() > 0){
            g.drawImage(
                    barFill.getImage(),
                    (int)unit*7,
                    (int)unit*4,
                    (int)ph.getHealth()*4,
                    30,
                    null);

        }
        // bar
        g.drawImage(
                bar.getImage(),
                (int)unit*7,
                (int)unit*4,
                400,
                35,
                null
        );

    }
}
