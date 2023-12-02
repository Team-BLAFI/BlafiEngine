package component;
import java.awt.Graphics2D;

import entity.player.Player;
import window.Window;
import window.scenes.Scene;
import java.awt.*;
import component.Health;

import javax.swing.*;

public class UI {
    Scene gp;
    Font arial_40;
    Health PlayerHealth;
//    double health = PlayerHealth.getHealth();
//    double maxHealth = PlayerHealth.getMaxHealth();

    public UI(Scene gp){
        this.gp = gp;
//        this.PlayerHealth = ph;
        arial_40 = new Font("Arial", Font.BOLD,30);
    }

    public void draw(Graphics g2 , String Text, int x, int y){
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        // Draws the string with coordinates in gamescene
        g2.drawString(Text,x,y);

    }
    // Health


}
