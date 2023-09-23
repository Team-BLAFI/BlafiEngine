import javax.swing.*;
import java.awt.*;



//todo: implement chapter 2 of the textbook to create double buffer in this window;




public class Window extends JFrame implements Runnable {

    public static Window window = null;

    private boolean _isRunning;

    private int _currentState;

    private Scene _currentScene;

    private double _windowsChangeCoolDown = 0.f;


    private Window(int width, int height, String title){
        setSize(width,height);
        setTitle(title);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _isRunning = true;
        changeState(Constants.MENU_SCENE);
        Constants.INSET_SIZE = getInsets().top;
        addKeyListener(KL.getKeyListener());

    }

    public static Window getWindow(){
        if(Window.window == null){
            Window.window = new Window(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT,Constants.SCREEN_TITLE);
        }
        return Window.window;
    }





     public void changeState(int newState){
        if(_windowsChangeCoolDown<=0.f){
            _windowsChangeCoolDown = 1.0;
            _currentState = newState;
            switch (_currentState){
                case 0:
                    _currentScene = new MenuScene();
                    break;
                case 1:
                    _currentScene = new GameScene();
                    break;
                case 2:
                    _currentScene = new EditorScene();
                    break;
                default:
                    System.out.println("Unknown Scene");
                    _currentScene = null;
                    break;

            }
        }

     }

    /**<p>
     * Calls the draw method for the current scene.
     * <br>
     * <br>
     * This calls always occur after the update methods
     * </p>
     * */
    private void update(double deltaTime) {
        _currentScene.update(deltaTime);



        Image dbImage = createImage(getWidth(),getHeight());
        Graphics dbg = dbImage.getGraphics();


        this.draw(dbg);


        getGraphics().drawImage(dbImage,0,0,this );

        _windowsChangeCoolDown = _windowsChangeCoolDown - deltaTime;


    }

    /**<p>
     * Calls the draw method for the current scene.
     * <br>
     * <br>
     * This calls always occur after the update methods
     * </p>
     * */
    private void draw(Graphics g){

        _currentScene.draw(g);

    }

    /**<p>
     * Entry point of the Window class(besides the constructor) starts executing when the thread is started
     * <br>
     * <br>
     * uses the Time class to calculate the time since last frame.
     * </p>
     * */
    @Override
    public void run() {
        this.requestFocus();
        double lastFrameTime = 0.0;
        try{
//          main game loop happens here & delta time gets calculated at the start of the first frame
            while(_isRunning){
                double time =  Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;


                update(deltaTime);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        this.dispose();
    }
}
