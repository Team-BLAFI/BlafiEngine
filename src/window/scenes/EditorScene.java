package window.scenes;

import map.Texture;
import map2.Room;
import util.Rect;
import util.Vector2D;
import util.io.KL;
import util.io.ML;
import window.Window;
import window.WindowConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class EditorScene extends Scene {
    public double unit = WindowConstants.SCREEN_UNIT;
    ML ml = ML.getMouseListener();
    KL kl = KL.getKeyListener();
    Color c_layout = new Color(0xB04F4F);
    Color c_floor = new Color(0x76B04F);
    Color c_props = new Color(0x4F6AB0);
    Color c_doors = new Color(0x832FAD);
    Color c_enemySpawns = new Color(0xEEAAAA);
    Color c_save = new Color(0x48BB4A);
    Color c_shade = new Color(0x45000000, true);
    Color c_ligther = new Color(0x45FFFFFF, true);

    Rect r_layout = new Rect((int) unit * 3, (int) unit * 3, (int) unit * 6, (int) unit * 2);
    Rect r_floor = new Rect((int) (unit * 3 + unit * 7), (int) (unit * 3), (int) unit * 6, (int) unit * 2);
    Rect r_props = new Rect((int) (unit * 3 + unit * 14), (int) (unit * 3), (int) unit * 6, (int) unit * 2);
    Rect r_doors = new Rect((int) (unit * 3 + unit * 21), (int) (unit * 3), (int) unit * 6, (int) unit * 2);
    Rect r_enemySpawns = new Rect((int) (unit * 3 + unit * 28), (int) (unit * 3), (int) unit * 6, (int) unit * 2);
    Rect[][] r_texSelect;
    Rect[][] r_mapTiles;
    Rect r_save;
    Room currentRoom;
    int[][] currentEdit;
    int currentSection;
    double texTileSize = unit * 3;
    double tileEditorSize = unit * 1.5;
    int mapMarginX = (int) (unit * 3);
    int mapMarginY = (int) (unit * 5);
    int texMarginX = (int) (unit * 60);
    int texMarginY = (int) (unit * 5);
    int currentSelection;
    int texSelMaxRow = 10;
    int texSelMaxCol = 10;

    Vector2D pos1 = new Vector2D();
    Vector2D pos2 = new Vector2D();

    public EditorScene() {
        currentSelection = 0;
        currentSection = 0;

        currentRoom = new Room();
        currentEdit = currentRoom.roomData[currentSection];

        int xo, yo;
        r_mapTiles = new Rect[currentEdit.length][currentEdit.length];
        for (int y = 0; y < currentEdit.length; y++) {
            for (int x = 0; x < currentEdit[y].length; x++) {


                xo = (int) (x * tileEditorSize + mapMarginX);
                yo = (int) (y * tileEditorSize + mapMarginY);

                r_mapTiles[y][x] = new Rect(xo, yo, (int) tileEditorSize, (int) tileEditorSize);

            }
        }

        r_texSelect = new Rect[texSelMaxRow][texSelMaxCol];
        for (int y = 0; y < texSelMaxRow; y++) {
            for (int x = 0; x < texSelMaxCol; x++) {

                xo = (int) ((x * texTileSize) + (x * unit * .5) + texMarginX);
                yo = (int) ((y * texTileSize) + (y * unit * .5) + texMarginY);

                r_texSelect[y][x] = new Rect(xo, yo, (int) texTileSize, (int) texTileSize);


            }
        }

        r_save = new Rect(
                texMarginX,
                (int) ((texSelMaxRow * texTileSize) + (texSelMaxRow * unit * .5) + texMarginY),
                (int) unit * 6,
                (int) unit * 2
        );

    }

    private void drawButtons(Graphics g) {
        g.setColor(c_layout);
        g.fillRect(r_layout.x, r_layout.y, r_layout.w, r_layout.h);

        g.setColor(c_floor);
        g.fillRect(r_floor.x, r_floor.y, r_floor.w, r_floor.h);

        g.setColor(c_props);
        g.fillRect(r_props.x, r_props.y, r_props.w, r_props.h);

        g.setColor(c_doors);
        g.fillRect(r_doors.x, r_doors.y, r_doors.w, r_doors.h);

        g.setColor(c_enemySpawns);
        g.fillRect(r_enemySpawns.x, r_enemySpawns.y, r_enemySpawns.w, r_enemySpawns.h);

        g.setColor(c_save);
        g.fillRect(r_save.x, r_save.y, r_save.w, r_save.h);


        g.setColor(Color.black);
        g.drawString("Walls", r_layout.x + 20, r_layout.y + 20);

        g.drawString("Floor", r_floor.x + 20, r_floor.y + 20);

        g.drawString("Props", r_props.x + 20, r_props.y + 20);

        g.drawString("doors", r_doors.x + 20, r_doors.y + 20);

        g.drawString("enemies", r_enemySpawns.x + 20, r_enemySpawns.y + 20);

        g.drawString("Save", r_save.x + 20, r_save.y + 20);


    }

    private void drawCurrentEdit(Graphics g) {

        for (int y = 0; y < currentEdit.length; y++) {
            for (int x = 0; x < currentEdit[y].length; x++) {

                if (currentEdit[y][x] != 0) {
                    g.drawImage(
                            Texture.textures2[currentEdit[y][x]].img.getImage(),
                            r_mapTiles[y][x].x,
                            r_mapTiles[y][x].y,
                            (int) tileEditorSize,
                            (int) tileEditorSize,
                            null
                    );

                    // If drawing something other than the walls editor add shades to know where walls are

                    if (currentSection != 0) {
                        g.setColor(c_shade);
                        if (currentRoom.walls[y][x] != 0) {
                            g.fillRect(r_mapTiles[y][x].x, r_mapTiles[y][x].y, (int) tileEditorSize, (int) tileEditorSize);
                        }

                    }
                }
                g.setColor(Color.BLACK);
                g.drawRect(r_mapTiles[y][x].x, r_mapTiles[y][x].y, (int) tileEditorSize, (int) tileEditorSize);
            }
        }
    }

    private void drawTextureSelection(Graphics g) {
        for (int y = 0; y < texSelMaxRow; y++) {
            for (int x = 0; x < texSelMaxCol; x++) {
                try {
                    ImageIcon img = Texture.textures2[y * texSelMaxCol + x] == null ? Texture.t_missing : Texture.textures2[y * texSelMaxCol + x].img;
                    g.drawImage(
                            img.getImage(),
                            r_texSelect[y][x].x,
                            r_texSelect[y][x].y,
                            r_texSelect[y][x].w,
                            r_texSelect[y][x].h,
                            null
                    );
                } catch (Exception e) {

                }

//                if (y * texSelMaxCol + x == 0) {
//                    g.setColor(Color.white);
//                    g.fillRect(r_texSelect[y][x].x, r_texSelect[y][x].y, r_texSelect[y][x].w, r_texSelect[y][x].h);
//                }
                g.setColor(Color.BLACK);
                g.drawRect(r_texSelect[y][x].x, r_texSelect[y][x].y, r_texSelect[y][x].w, r_texSelect[y][x].h);
            }
        }
        g.setColor(Color.black);
    }

    private void drawDebugInfo(Graphics g) {
        g.setColor(Color.black);
        Font myFont = new Font("Courier New", 1, 17);
        g.setFont(myFont);
        String s = String.format("Current selection %d", currentSelection);

        g.drawString(s,
                WindowConstants.SCREEN_WIDTH - 300,
                (int) (WindowConstants.INSET_SIZE * 1.5)
        );
    }

    private void drawShortcutInfo(Graphics g) {
        g.setColor(Color.black);
        Font myFont = new Font("Courier New", 1, 17);
        g.setFont(myFont);
        String heading = String.format("Shortcut keys:");
        String shortcut_c = String.format("c = fill everything with current selection");
        String shortcut_v = String.format("v = fill everything but outer edge with current selection");
        String shortcut_w = String.format("w = fill outer layer with current selection");

        int insetOffset = 18;

        g.drawString(heading,
                WindowConstants.SCREEN_WIDTH - 650,
                (int) (WindowConstants.INSET_SIZE * 20.5)
        );
        g.drawString(shortcut_c,
                WindowConstants.SCREEN_WIDTH - 625,
                (int) (WindowConstants.INSET_SIZE * 20.5 + insetOffset)
        );
        g.drawString(shortcut_v,
                WindowConstants.SCREEN_WIDTH - 625,
                (int) (WindowConstants.INSET_SIZE * 20.5 + (insetOffset*2))
        );
        g.drawString(shortcut_w,
                WindowConstants.SCREEN_WIDTH - 625,
                (int) (WindowConstants.INSET_SIZE * 20.5 + (insetOffset * 3))
        );
    }

    @Override
    public void update(double deltaTime) {
        if (kl.isKeyDown(KeyEvent.VK_ESCAPE)) {
            Window.getWindow().changeState(WindowConstants.MENU_SCENE);
        }

        if (kl.isKeyDown(KeyEvent.VK_F1)) {
            Window.getWindow().changeState(WindowConstants.GAME_SCENE);
        }

        if (kl.isKeyDown(KeyEvent.VK_SHIFT) && ml.isPressed(MouseEvent.BUTTON1)) {
            for (int y = 0; y < currentEdit.length; y++) {
                for (int x = 0; x < currentEdit[y].length; x++) {
                    if (ml.isMouseInsideRect(r_mapTiles[y][x])) {
                        pos1.setX(x);
                        pos1.setY(y);
                        return;
                    }
                }
            }
        }

        if (kl.isKeyDown(KeyEvent.VK_SHIFT) && ml.isPressed(MouseEvent.BUTTON3)) {
            for (int y = 0; y < currentEdit.length; y++) {
                for (int x = 0; x < currentEdit[y].length; x++) {
                    if (ml.isMouseInsideRect(r_mapTiles[y][x])) {
                        pos2.setX(x);
                        pos2.setY(y);

                        return;
                    }
                }
            }
        }

        if (ml.isPressed(MouseEvent.BUTTON1)) {
            if (ml.isMouseInsideRect(r_layout)) {
                currentSection = 0;
                currentEdit = currentRoom.roomData[currentSection];
                return;
            }

            if (ml.isMouseInsideRect(r_floor)) {
                currentSection = 1;
                currentEdit = currentRoom.roomData[currentSection];
                return;
            }

            if (ml.isMouseInsideRect(r_props)) {
                currentSection = 2;
                currentEdit = currentRoom.roomData[currentSection];
                return;
            }

            if (ml.isMouseInsideRect(r_doors)) {
                currentSection = 3;
                currentEdit = currentRoom.roomData[currentSection];
                return;
            }

            if (ml.isMouseInsideRect(r_enemySpawns)) {
                currentSection = 4;
                currentEdit = currentRoom.roomData[currentSection];
                return;
            }

            for (int y = 0; y < currentEdit.length; y++) {
                for (int x = 0; x < currentEdit[y].length; x++) {
                    if (ml.isMouseInsideRect(r_mapTiles[y][x])) {
                        currentEdit[y][x] = currentSelection;
                        return;
                    }
                }
            }

            for (int y = 0; y < texSelMaxRow; y++) {
                for (int x = 0; x < texSelMaxCol; x++) {
                    if (ml.isMouseInsideRect(r_texSelect[y][x])) {
                        currentSelection = y * texSelMaxCol + x;
                        return;
                    }
                }
            }

        }



        if (ml.isMouseInsideRect(r_save)) {
            try {
                String save = String.format("src/assets/levels/Elevel%d.dat", 0);

                FileOutputStream fos = new FileOutputStream(save);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(currentRoom.roomData);

            } catch (Exception e) {
                System.out.println("fail to load");
            }
            return;

        }


        if (ml.isPressed(MouseEvent.BUTTON2)) {
            for (int y = 0; y < currentEdit.length; y++) {
                for (int x = 0; x < currentEdit[y].length; x++) {
                    if (ml.isMouseInsideRect(r_mapTiles[y][x])) {
                        currentSelection = currentEdit[y][x];
                        return;
                    }
                }
            }
        }


        if (ml.isPressed(MouseEvent.BUTTON3)) {
            for (int y = 0; y < currentEdit.length; y++) {
                for (int x = 0; x < currentEdit[y].length; x++) {
                    if (ml.isMouseInsideRect(r_mapTiles[y][x])) {
                        currentEdit[y][x] = 0;
                        return;
                    }
                }
            }
        }

        if (kl.isKeyDown(KeyEvent.VK_C)) {
            for (int y = 0; y < currentEdit.length; y++) {
                for (int x = 0; x < currentEdit[y].length; x++) {
                    currentEdit[y][x] = currentSelection;
                    return;
                }
            }
        }

        if (kl.isKeyDown(KeyEvent.VK_V)) {
            for (int y = 1; y < currentEdit.length - 1; y++) {
                for (int x = 1; x < currentEdit[y].length - 1; x++) {
                    currentEdit[y][x] = currentSelection;
                    return;
                }
            }
        }
        if (kl.isKeyDown(KeyEvent.VK_W)) {
            for (int y = 0; y < currentEdit.length; y++) {
                for (int x = 0; x < currentEdit[y].length; x++) {
                    if (y == 0 || x == 0 || y == currentEdit.length - 1 || x == currentEdit[y].length - 1) {
                        currentEdit[y][x] = currentSelection;
                        return;
                    }
                }
            }
        }
        if (kl.isKeyDown(KeyEvent.VK_F)) {
            if (pos1.getX() > pos2.getX()) {
                double temp = pos1.getX();
                pos1.setX(pos2.getX());
                pos2.setX(temp);
            }
            if (pos1.getY() > pos2.getY()) {
                double temp = pos1.getY();
                pos1.setY(pos2.getY());
                pos2.setY(temp);
            }
            for (int y = (int) pos1.getY(); y < (int) pos2.getY(); y++) {
                for (int x = (int) pos1.getX(); x < (int) pos2.getX(); x++) {
                    currentEdit[y][x] = currentSelection;
                    return;
                }
            }
        }

    }

    @Override
    public void draw(Graphics g) {
        drawCurrentEdit(g);
        drawTextureSelection(g);
        drawButtons(g);
        drawDebugInfo(g);
        drawShortcutInfo(g);
    }

}