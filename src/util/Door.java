package util;

import component.Collider;

import javax.swing.*;

public class Door {
    private Transform transform;
    private Collider collider;
    private boolean isOpen;

    public  Door(Transform t){
        transform = t;
        collider = t.getAsCollider();
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Collider getCollider() {
        return collider;
    }
}
