package util;

import component.Collider;

import javax.swing.*;
import java.awt.*;

public class RoomTile {

    private Transform transform;
    private ImageIcon image;
    private Collider collider;
    private boolean solid;


    public RoomTile(ImageIcon i, Transform t, boolean s){
        this.image = i;
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
        g.drawImage(image.getImage(),
                (int)transform.getX(),
                (int)transform.getY(),
                (int)transform.getWidth(),
                (int)transform.getHeight(),
                null
        );
    }

    public boolean getIsSolid() {
        return solid;
    }
}
