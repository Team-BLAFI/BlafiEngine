package component;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Iterator;

public class TileMap {
    private Image[][] tiles;
    private LinkedList sprites;

    /** Creates a new TileMap with the specified width and height (in number of tiles) of the map.*/

    public TileMap(int width, int height) {
        tiles = new Image[width][height];
        sprites = new LinkedList(); }

    // Gets Width of TILEMAP
    public int getWidth() {
        return tiles.length;
    }
    // Gets Height of TILEMAP
    public int getHeight() {
        return tiles[0].length;
    }
}
