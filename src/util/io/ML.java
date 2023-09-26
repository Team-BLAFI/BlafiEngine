package util.io;

import util.Rect;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ML extends MouseAdapter implements MouseMotionListener {


    private static ML mouseListener = new ML();
    private boolean mousePressed = false;


    private double x = 0.0, y = 0.0, previous_x = 0.0, previous_y = 0;

    public static ML getMouseListener(){
        if(ML.mouseListener == null){
            ML.mouseListener = new ML();
        }
        return ML.mouseListener;
    }


    @Override
    public void mousePressed(MouseEvent e){
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e){
        mousePressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e){

        previous_x = x;
        previous_y = y;
        x = e.getX();
        y = e.getY();
    }

    public boolean isMouseInsideRect(Rect rect){


        boolean  leftEdge = mouseListener.x >= rect.x;
        boolean  RightEdge = mouseListener.x <= rect.x+ rect.w;
        boolean  UpperEdge = mouseListener.y >= rect.y;
        boolean  LowerEdge = mouseListener.y <= rect.y+rect.h;

        return (leftEdge && RightEdge && UpperEdge &&  LowerEdge );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getdx(){
        return x - previous_x;
    }

    public double getdy(){
        return y - previous_y;
    }


    public boolean isPressed() {
        return mousePressed;
    }
}
