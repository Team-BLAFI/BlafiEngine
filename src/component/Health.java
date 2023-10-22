package component;

import entity.Entity;
import util.Rect;
import window.WindowConstants;

import java.awt.*;

public class Health extends Component{

    private Entity owner;
    private double maxHealth;
    private double health;
    private Rect bar;
    private Rect barFill;
    private Rect barOutline;

    public Health(double h, Entity owner){
        this(h,1,0,owner);
    }

    public Health(double h, int xOffset, int yOffset, Entity owner){
        double unit = WindowConstants.SCREEN_UNIT;
        int borderUnit = (int) Math.max(1, unit/20);
        health    = h;
        maxHealth = h;

        bar     = new Rect(
                xOffset,
                yOffset,
                (int) unit * 6 ,
                (int) unit/2
        );
        barFill = new Rect(
                xOffset,
                yOffset,
                (int) unit * 6,
                (int) unit/2
        );
        barOutline = new Rect(
                xOffset         - borderUnit,
                yOffset         - borderUnit,
                (int) unit * 6  + borderUnit,
                (int) unit/2    + borderUnit
        );
        this.owner = owner;
    }


    public void takeDamage(double d){
        health -= d;

        setBarFill();

        if(health <= 0){
            owner.destroy();
        }
    }

    private void setBarFill(){
        double fillPercentage = health / maxHealth;
        barFill.w = (int) (bar.w * fillPercentage);
    }

    public void drawHealthBar(Graphics g, int x, int y){
        g.setColor(new Color(0x1F1F1F));
        g.fillRect(
                x + bar.x,
                y + bar.y,
                bar.w,
                bar.h
        );

        g.setColor(new Color(0xE23131));
        g.fillRect(
                x + barFill.x,
                y + barFill.y,
                barFill.w,
                barFill.h
        );

        g.setColor(new Color(0xFFFBFB));
        g.drawRect(
                x + barOutline.x,
                y + barOutline.y,
                barOutline.w,
                barOutline.h
        );

    }

    @Override
    public void init() {

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void draw(Graphics g) {

    }
}