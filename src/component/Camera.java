package component;

import util.Transform;

public class Camera {
    Transform transform;
    int height;
    int width;

    public Camera(int width, int height, Transform transform){
        this.transform = transform;
        this.height = height;
        this.width = width;
    }
}
