package component;
import util.Animation;
import util.Rect;
import window.WindowConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TileManager extends Component {

    private Rect GrassPos = new Rect(80,64,16,16);
    private BufferedImage GrassSprite, DirtSprite, CurrentSprite;
    boolean buildable;
    // SCREEN CONSTANTS
    private double ScreenWidthMidPoint = (WindowConstants.SCREEN_WIDTH/2);
    private double ScreenHeightMidPoint = (WindowConstants.SCREEN_HEIGHT/2);
    private double windowWidth20per = WindowConstants.SCREEN_WIDTH*(1.0/5);
    private double windowHeight20per = WindowConstants.SCREEN_HEIGHT*(1.0/5);

    private Animation Sprite = null;

    public TileManager(Animation animation) {
        this.Sprite = animation;
    };

    public void readImage(){
        try{
            BufferedImage spritesheet = ImageIO.read(new File("src/assets/grasstileset.png"));
            GrassSprite = spritesheet.getSubimage(GrassPos.x, GrassPos.y, GrassPos.w, GrassPos.h);
            CurrentSprite = GrassSprite;
        }catch(Exception e){
            e.printStackTrace();
        }
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