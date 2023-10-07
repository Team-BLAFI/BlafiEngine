package window.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import util.Rect;
import util.io.KL;
import util.io.ML;
import window.Window;
import window.WindowConstants;

import javax.imageio.ImageIO;

public class MenuScene extends Scene{


    private ML mouseListener;


    private Rect playButtonSpritePos = new Rect(0,0,32,16);
    private Rect playButtonHoverSpritePos = new Rect(32,0,32,16);
    private double playButtonScreenWidth = (WindowConstants.SCREEN_WIDTH*(1.0/10));
    private double ButtonScreenXPos;


    private Rect quitButtonSpritePos = new Rect(0,16,32,16);
    private Rect quitButtonHoverSpritePos = new Rect(32,16,32,16);


    private Rect backgroundSpritePos = new Rect(0,32,64,32);

    private BufferedImage background, play, playHover, playCurrent, quit, quitHover, quitCurrent;

    private double ScreenWidthMidPoint = (WindowConstants.SCREEN_WIDTH/2);
    private double ScreenHeightMidPoint = (WindowConstants.SCREEN_HEIGHT/2);

    private double windowWidth20per = WindowConstants.SCREEN_WIDTH*(1.0/5);
    private double windowHeight20per = WindowConstants.SCREEN_HEIGHT*(1.0/5);




    private Rect playButtonHitBox;
    private Color playHitboxColor = Color.GREEN;


    private Rect quitButtonHitBox;
    private Color quitHitboxColor = Color.RED;


    public MenuScene(){
        try{
            BufferedImage spritesheet = ImageIO.read(new File("src/assets/blafiAdMenuSprite.png"));
            play = spritesheet.getSubimage(playButtonSpritePos.x, playButtonSpritePos.y, playButtonSpritePos.w, playButtonSpritePos.h);
            playHover = spritesheet.getSubimage(playButtonHoverSpritePos.x, playButtonHoverSpritePos.y, playButtonHoverSpritePos.w, playButtonHoverSpritePos.h);
            playCurrent = play;

            quit = spritesheet.getSubimage(quitButtonSpritePos.x, quitButtonSpritePos.y, quitButtonSpritePos.w, quitButtonSpritePos.h);
            quitHover = spritesheet.getSubimage(quitButtonHoverSpritePos.x, quitButtonHoverSpritePos.y, quitButtonHoverSpritePos.w, quitButtonHoverSpritePos.h);
            quitCurrent = quit;

            background = spritesheet.getSubimage(backgroundSpritePos.x, backgroundSpritePos.y, backgroundSpritePos.w, backgroundSpritePos.h);


        }catch(Exception e){
            e.printStackTrace();
        }

        mouseListener = ML.getMouseListener();

        ButtonScreenXPos = ScreenWidthMidPoint - playButtonScreenWidth;
        playButtonHitBox = new Rect((int) ButtonScreenXPos, (int) (windowHeight20per), (int) windowWidth20per, (int) windowHeight20per);

        quitButtonHitBox = new Rect((int) ButtonScreenXPos, (int) (windowHeight20per) * 2 + 10, (int) windowWidth20per, (int) windowHeight20per);

    }




    @Override
    public void update(double deltaTime) {

        if(KL.getKeyListener().isKeyDown(KeyEvent.VK_F1)){
            Window.getWindow().changeState(WindowConstants.EDITOR_SCENE);
        }

        boolean isMouseInsidePlay = mouseListener.isMouseInsideRect(playButtonHitBox.x,playButtonHitBox.y,playButtonHitBox.w,playButtonHitBox.h) ;
        boolean isMouseInsideQuit = mouseListener.isMouseInsideRect(quitButtonHitBox.x,quitButtonHitBox.y,quitButtonHitBox.w,quitButtonHitBox.h);

        playCurrent = isMouseInsidePlay ? playHover : play;

        quitCurrent = isMouseInsideQuit ? quitHover : quit;

        if (isMouseInsidePlay && mouseListener.isPressed(MouseEvent.BUTTON1)){
            Window.getWindow().changeState(WindowConstants.GAME_SCENE);
        }

        if(isMouseInsideQuit && mouseListener.isPressed(MouseEvent.BUTTON1)){
            Window.getWindow().CloseWindow();
        }

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT );

        g.drawImage(background, 0,0,WindowConstants.SCREEN_WIDTH,WindowConstants.SCREEN_HEIGHT,null);

        g.drawImage(playCurrent, playButtonHitBox.x,playButtonHitBox.y,playButtonHitBox.w,playButtonHitBox.h,null);

        g.drawImage(quitCurrent, quitButtonHitBox.x,quitButtonHitBox.y,quitButtonHitBox.w,quitButtonHitBox.h,null);

    }
}
