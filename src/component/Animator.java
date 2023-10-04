package component;

import util.Animation;
import util.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Animator extends Component{


    private Map<String, Animation> Animations = new HashMap<>();
    private Animation currentAnimation = null;
    private BufferedImage currentFrame = null;
    private int currentFrameIndex = 0;
    private double frameTime = 0.15;
    private double lastFrame = 0;


    public Animator(){
    }

    public void createAnimation(String AnimationName, String path, Rect[] rects){

        Animations.put(AnimationName, new Animation(path, rects));
        if (currentAnimation == null){
            currentAnimation = Animations.get(AnimationName);
            currentFrame = currentAnimation.getFrame(currentFrameIndex);
        }
    }

    public void createAnimation(String AnimationName, String path, Rect[] rects, int xOffset, int yOffset){

        Animations.put(AnimationName, new Animation(path, rects));
        if (currentAnimation == null){
            currentAnimation = Animations.get(AnimationName);
            currentFrame = currentAnimation.getFrame(currentFrameIndex);
        }
        Animations.get(AnimationName).xOffset = xOffset;
        Animations.get(AnimationName).yOffset = yOffset;
    }

    public void createAnimation(String AnimationName, String path, Rect[] rects, int xOffset, int yOffset, double scaleFactor){

        Animations.put(AnimationName, new Animation(path, rects));
        if (currentAnimation == null){
            currentAnimation = Animations.get(AnimationName);
            currentFrame = currentAnimation.getFrame(currentFrameIndex);
        }
        Animations.get(AnimationName).xOffset = xOffset;
        Animations.get(AnimationName).yOffset = yOffset;
        Animations.get(AnimationName).scaleFactor = scaleFactor;
    }

    public void changeAnimationTo(String AnimationName){
        if(Animations.containsKey(AnimationName)){
            currentAnimation = Animations.get(AnimationName);
            currentFrameIndex = 0;
        }
    }

    public void changeAnimationNotReset(String AnimationName){
        if(Animations.containsKey(AnimationName)){
            currentAnimation = Animations.get(AnimationName);
        }
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = frameTime;
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }

    public boolean hasAnimations(){
        return (!Animations.isEmpty());
    }

    public void setCurrentAnimationOffset(int xOffset, int yOffset) {
        currentAnimation.xOffset = xOffset;
        currentAnimation.yOffset = yOffset;
    }

    public void RenderCurrentSprite(Graphics g, int x, int y){
        g.drawImage(
                currentFrame,
                (int) (x + currentAnimation.xOffset),
                (int) (y + currentAnimation.yOffset),
                (int) (currentFrame.getWidth() * currentAnimation.scaleFactor),
                (int) (currentFrame.getHeight() * currentAnimation.scaleFactor),
                null
        );
    }

    public void RenderCurrentSpriteFlipVer(Graphics g,int x, int y){
        g.drawImage(
                (Image) currentFrame,
                (int) (x + currentAnimation.xOffset + currentFrame.getWidth() * currentAnimation.scaleFactor),
                (int) (y + currentAnimation.yOffset),
                (int) (-currentFrame.getWidth() * currentAnimation.scaleFactor),
                (int) (currentFrame.getHeight() * currentAnimation.scaleFactor),
                null
        );
    }


    public void RenderCurrentSpriteFlipHor(Graphics g,int x, int y){
        g.drawImage(
                (Image) currentFrame,
                (int) (x + currentAnimation.xOffset),
                (int) (y + currentAnimation.yOffset + currentFrame.getHeight() * currentAnimation.scaleFactor),
                (int) (currentFrame.getWidth() * currentAnimation.scaleFactor),
                (int) (-currentFrame.getHeight() * currentAnimation.scaleFactor),
                null
        );
    }

    public void RenderCurrentSpriteFlipBoth(Graphics g,int x, int y){
        g.drawImage(
                (Image) currentFrame,
                (int) (x + currentAnimation.xOffset + currentFrame.getWidth() * currentAnimation.scaleFactor),
                (int) (y + currentAnimation.yOffset + currentFrame.getHeight() * currentAnimation.scaleFactor),
                (int) (-currentFrame.getWidth() * currentAnimation.scaleFactor),
                (int) (-currentFrame.getHeight() * currentAnimation.scaleFactor),
                null
        );
    }

    @Override
    public void init() {

    }

    @Override
    public void update(double deltaTime) {
        lastFrame += deltaTime;

        if (currentAnimation!=null){
            if(lastFrame>frameTime){
                boolean arrayOverFlow = currentFrameIndex + 1 > currentAnimation.AnimationLength() - 1 ;

                currentFrameIndex = arrayOverFlow ? 0 : currentFrameIndex + 1;
                currentFrame = currentAnimation.getFrame(currentFrameIndex);
                lastFrame = 0;
            }
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}
