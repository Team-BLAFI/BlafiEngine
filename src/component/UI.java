package component;
import java.awt.Graphics2D;

import entity.player.Player;
import window.Window;
import window.scenes.Scene;
import java.awt.*;
import static window.WindowConstants.SCREEN_UNIT;


import javax.swing.*;

public class UI {
    Scene gp;
    Font arial_40;
    Health PlayerHealth;
    double unit = SCREEN_UNIT;
//    double health = PlayerHealth.getHealth();
//    double maxHealth = PlayerHealth.getMaxHealth();

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
    public void drawHealth(Graphics g2, Health ph){
        // Health
        g2.setColor(new Color(0x1F1F1F));
        g2.fillRect((int)unit, (int)unit*3, 200, 25);

        g2.setColor(new Color(0xE23131));
        g2.fillRect((int)unit, (int)unit*3, (int)ph.getHealth()*2, 25
        );

        g2.setColor(new Color(0xFFFBFB));
        g2.drawRect((int)unit, (int)unit*3, 200, 25);

    }



}
