package gameCore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameObject implements Comparable<GameObject>{

    protected Vector2 position = new Vector2();
    protected Vector2 scale = new Vector2(1, 1);
    protected String imagePath = null;
    protected Image image = null;
    protected Vector2 imageResolution = new Vector2();
    protected Integer zOrder = 0;

    public GameObject() {

        GameController.addObjectToList(this);
        Start();
    }

    // Called once at the start
    public void Start() {

    }

    // Called fixed times per second
    public void PhysicsUpdate() {

    }

    // Called at the start of each frame
    public void Update() {

    }

    // Called at the end of each frame
    public void LateUpdate() {

    }


    // Compare to
    public int compareTo(GameObject gameObject) {

        return this.zOrder.compareTo(gameObject.getzOrder());
    }

    // Move by x, y pixels
    public void Move(Vector2 v2) {

        position.x += v2.x;
        position.y += v2.y;
    }

    // Getters and Setters
    public Vector2 getPosition() {

        return new Vector2(position);
    }

    public void setPosition(Vector2 position) {

        this.position.x = position.x;
        this.position.y = position.y;
    }

    public Vector2 getScale() {

        return new Vector2(scale);
    }

    public void setScale(Vector2 scale) {

        this.scale.x = scale.x;
        this.scale.y = scale.y;
    }

    public Image getImage() {

        return image;
    }

    public void setImage(String imagePath) {

        this.imagePath = imagePath;
        this.image = new ImageIcon(imagePath).getImage();

        try {

            BufferedImage img = ImageIO.read(new File(imagePath));
            imageResolution.x = img.getWidth();
            imageResolution.y = img.getHeight();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public Vector2 getImageResolution() {

        return imageResolution;
    }

    public void setImageResolution(Vector2 imageResolution) {

        this.imageResolution = imageResolution;
    }

    public Integer getzOrder() {

        return zOrder;
    }

    public void setzOrder(Integer zOrder) {

        this.zOrder = zOrder;
    }
}
