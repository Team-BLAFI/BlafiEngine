package util.io;

import util.Rect;

import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Dictionary;
import java.util.Hashtable;

public class ML extends MouseAdapter implements MouseMotionListener {


    private static ML mouseListener = new ML();
    private Dictionary<Integer,Boolean> buttonPressedDictionary = new Hashtable<>();

    private boolean mouseDragging = false;


    private double x = 0.0, y = 0.0;

    public static ML getMouseListener(){
        if(ML.mouseListener == null){
            ML.mouseListener = new ML();
        }
        return ML.mouseListener;
    }


    @Override
    public void mousePressed(MouseEvent e){
        buttonPressedDictionary.put(e.getButton(),true);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        buttonPressedDictionary.put(e.getButton(),false);
        mouseDragging = false;

    }

    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e){
        mouseDragging = true;
        mouseMoved(e);
        e.consume();
    }

    public boolean isMouseInsideRect(Rect rect){


        boolean  leftEdge  = mouseListener.x >= rect.x;
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


    public boolean isPressed(int buttonCode) {
        try{
            return buttonPressedDictionary.get(buttonCode);
        }catch (Exception e){
            return  false;
        }
    }
    public boolean isMouseDragging(){return mouseDragging;}
}
