package map;

import component.Collider;
import util.Transform;

import javax.swing.*;
import java.awt.*;

public class Tile {

    private Transform transform;
    private ImageIcon image;
    private Collider collider;
    private boolean solid;


    public Tile(int val, Transform t, boolean s){
        this.image = Texture.textures[val].img;
        transform = t;
        this.collider = transform.getAsCollider();
        this.solid = s;
    }

    public Tile(ImageIcon img, Transform t, boolean s){
        this.image = img;
        transform = t;
        collider = transform.getAsCollider();
        this.solid = s;
    }


    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Collider getCollider() {
        return collider;
    }

    public void draw(Graphics g){
        try {
            g.drawImage(image.getImage(),
                    (int)transform.getX(),
                    (int)transform.getY(),
                    (int)transform.getWidth(),
                    (int)transform.getHeight(),
                    null
            );
        }catch (NullPointerException e){
            System.out.println("fail to render tile");
        }

    }

    public boolean getIsSolid() {
        return solid;
    }
}
