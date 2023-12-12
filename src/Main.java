import map.Texture;
import window.Window;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Texture.loadTileAtlas();
        Window window = Window.getWindow();

        Thread thread = new Thread(window);
        thread.start();

    }
}