package window.scenes;

import util.Rect;
import util.io.KL;
import util.io.ML;
import window.Window;
import window.WindowConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameOverScene extends Scene {

    private ML mouseListener;


    private Rect gameOverSpritePos = new Rect(96,0,64,16);
    private double gameOverScreenWidth = (WindowConstants.SCREEN_WIDTH*(1.0/10));
    private double ButtonScreenXPos;


    private Rect holdThatButtonSpritePos = new Rect(96,16,32,16);
    private Rect holdThatButtonHoverSpritePos = new Rect(128,16,32,16);


    private Rect backgroundSpritePos = new Rect(0,32,64,32);

    private BufferedImage background, gameOver, holdThat, holdThatHover, holdThatCurrent;

    private double ScreenWidthMidPoint = (WindowConstants.SCREEN_WIDTH/2);
    private double ScreenHeightMidPoint = (WindowConstants.SCREEN_HEIGHT/2);

    private double windowWidth20per = WindowConstants.SCREEN_WIDTH*(1.0/5);
    private double windowHeight20per = WindowConstants.SCREEN_HEIGHT*(1.0/5);

    private Rect gameOverHitBox;
    private Color gameOverHitboxColor = Color.GREEN;
    private Rect holdThatButtonHitBox;
    private Color holdThatHitboxColor = Color.RED;

    public GameOverScene() {
        try {
            BufferedImage spritesheet = ImageIO.read(new File("src/assets/blafiAdMenuSprite.png"));
            gameOver = spritesheet.getSubimage(gameOverSpritePos.x, gameOverSpritePos.y, gameOverSpritePos.w, gameOverSpritePos.h);

            holdThat = spritesheet.getSubimage(holdThatButtonSpritePos.x, holdThatButtonSpritePos.y, holdThatButtonSpritePos.w, holdThatButtonSpritePos.h);
            holdThatHover = spritesheet.getSubimage(holdThatButtonHoverSpritePos.x, holdThatButtonHoverSpritePos.y, holdThatButtonHoverSpritePos.w, holdThatButtonHoverSpritePos.h);
            holdThatCurrent = holdThat;

            background = spritesheet.getSubimage(backgroundSpritePos.x, backgroundSpritePos.y, backgroundSpritePos.w, backgroundSpritePos.h);


        }catch(Exception e){
            e.printStackTrace();
        }

        mouseListener = ML.getMouseListener();

        ButtonScreenXPos = ScreenWidthMidPoint - gameOverScreenWidth;

        gameOverHitBox = new Rect((int) (ButtonScreenXPos - gameOverScreenWidth), (int) (windowHeight20per), (int) windowWidth20per * 2, (int) windowHeight20per);

        holdThatButtonHitBox = new Rect((int) ButtonScreenXPos, (int) (windowHeight20per) * 2 + 10, (int) windowWidth20per, (int) windowHeight20per);
    }

    @Override
    public void update(double deltaTime) {

        boolean isMouseInsideHoldThat = mouseListener.isMouseInsideRect(holdThatButtonHitBox.x,holdThatButtonHitBox.y,holdThatButtonHitBox.w,holdThatButtonHitBox.h);

        holdThatCurrent = isMouseInsideHoldThat ? holdThatHover : holdThat;

        if(isMouseInsideHoldThat && mouseListener.isPressed(MouseEvent.BUTTON1)){
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, WindowConstants.SCREEN_WIDTH, WindowConstants.SCREEN_HEIGHT );

        g.drawImage(background, 0,0,WindowConstants.SCREEN_WIDTH,WindowConstants.SCREEN_HEIGHT,null);

        g.drawImage(gameOver, gameOverHitBox.x,gameOverHitBox.y,gameOverHitBox.w,gameOverHitBox.h,null);
        g.drawImage(holdThatCurrent, holdThatButtonHitBox.x,holdThatButtonHitBox.y,holdThatButtonHitBox.w,holdThatButtonHitBox.h,null);

    }
}
