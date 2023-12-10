package component;

import entity.Entity;
import util.Rect;
import window.WindowConstants;

import java.awt.*;

import static window.WindowConstants.SCREEN_UNIT;

public class Health extends Component{

    private Entity owner;
    private double maxHealth;
    private double health;
    private Rect bar;
    private Rect barFill;
    private Rect barOutline;
    private boolean isPlayer;
    public Health(double h, Entity owner, boolean isPlayer){
        this(h,1,0,owner,isPlayer);
    }

    public Health(double h, int xOffset, int yOffset, Entity owner, boolean isPlayer){
        double unit = SCREEN_UNIT;
        int BarWidth;
        int BarHeight;

        BarWidth= (int) unit * 6;
        BarHeight= (int ) unit/2;

        int borderUnit = (int) Math.max(1, unit/20);
        health    = h;
        maxHealth = h;
        bar     = new Rect(
                xOffset,
                yOffset,
                BarWidth,
                BarHeight
        );
        barFill = new Rect(
                xOffset,
                yOffset,
                BarWidth,
                BarHeight
        );
        barOutline = new Rect(
                xOffset         - borderUnit,
                yOffset         - borderUnit,
                BarWidth,
                BarHeight
        );
        this.owner = owner;
    }

    public double getHealth(){
        return health;
    }
    public double getMaxHealth(){
        return maxHealth;
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
    @Override
    public void draw(Graphics g){
        int x = (int) owner.transform.getX();
        int y = (int) owner.transform.getY();
        if(health >= maxHealth) return;

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
    public void update(double deltaTime) {

    }
}