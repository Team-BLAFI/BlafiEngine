package component;
import java.awt.Graphics2D;

import entity.player.Player;
import util.Rect;
import window.Window;
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
    double unit = SCREEN_UNIT;
    //Health Variables
    Health PlayerHealth;
    private ImageIcon bar;
    private ImageIcon bullet;
    private ImageIcon barFill;
    private ImageIcon barOutline;
    public UI(Scene gp, Health ph){
        this.gp = gp;
        this.PlayerHealth = ph;
        arial_40 = new Font("Arial", Font.BOLD,30);
    }

    public void draw(Graphics g2 , String Text, int x, int y){
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        // Draws the string with coordinates in gamescene
        g2.drawString(Text,x,y);

    }
    public void drawBullet(Graphics g2, int x, int y){
        try{
            BufferedImage bulletSprite = ImageIO.read(new File("src/assets/bullet.png"));
            bullet = new ImageIcon(bulletSprite.getSubimage(10,0,11,31));
        }catch (Exception e) {
            e.printStackTrace();
        }
        g2.drawImage(
                bullet.getImage(),
                x,
                y,
                25,
                62,
                null);
    }
    public void drawHealth(Graphics g2, Health ph){
        // Read File
        try{
            BufferedImage healthBarSprite = ImageIO.read(new File("src/assets/UIpack2.png"));
            BufferedImage healthSprite = ImageIO.read(new File("src/assets/UIpack2.png"));

            bar = new ImageIcon(healthBarSprite.getSubimage(1,35,46,11));
            barFill = new ImageIcon(healthSprite.getSubimage(49,21,43,6));

        }catch (Exception e) {
            e.printStackTrace();
        }

        /* -- Health -- */
        // bar fill
        if (ph.getHealth() > 0){
            g2.drawImage(
                    barFill.getImage(),
                    (int)unit,
                    (int)unit*3,
                    (int)ph.getHealth()*4,
                    30,
                    null);

        }
        // bar
        g2.drawImage(
                bar.getImage(),
                (int)unit,
                (int)unit*3,
                400,
                35,
                null);



    }



}
