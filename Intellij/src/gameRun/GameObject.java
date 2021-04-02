package gameRun;

import java.awt.*;

public class GameObject {

    protected Vector2 position = new Vector2();
    protected Vector2 scale = new Vector2();
    protected Image image = null;

    public GameObject() {

    }

    // Called once at the start
    public void Start() {

    }

    // Called at the start of each frame
    public void Update() {

    }

    // Called at the end of each frame
    public void LateUpdate() {

    }

    // Getters
    public Vector2 getPosition() {

        return new Vector2(position);
    }

    public Vector2 getScale() {

        return new Vector2(scale);
    }
}
