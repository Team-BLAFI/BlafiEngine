package window.scenes;

import util.Rect;
import util.io.KL;
import util.io.ML;
import window.Window;
import window.WindowConstants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class EditorScene extends Scene {

    Rect addRect = new Rect(Window.getWindow().getWidth()/2, Window.getWindow().getHeight()/2, 30,30);
    LinkedList<Rect> rectList = new LinkedList<>();

    ML mouseListener = ML.getMouseListener();
    private int mx = (int) mouseListener.getX();
    private int my = (int) mouseListener.getY();

    @Override
    public void update(double deltaTime) {
        int nx = (int) mouseListener.getX();
        int ny = (int) mouseListener.getY();


        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }

        if (mouseListener.isMouseInsideRect(addRect) && mouseListener.isPressed(MouseEvent.BUTTON1)){
            rectList.push(new Rect( 50, 50,30 ,30));
        }

        for (Rect rect: rectList) {

            if (mouseListener.isMouseDragging() && mouseListener.isMouseInsideRect(rect)) {
               rect.moveBy((nx - mx), (ny - my));
            }
        }

        mx = nx;
        my = ny;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT );

        g.setColor(Color.green);
        g.fillRect(addRect.x, addRect.y, addRect.w, addRect.h);

        g.setColor(Color.blue);
        for (Rect rectangle : rectList) {
            g.fillRect(rectangle.x, rectangle.y, rectangle.w, rectangle.h);
        }


    }

}