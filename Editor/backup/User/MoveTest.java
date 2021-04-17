package User;

import Preview.*;

import java.awt.event.KeyEvent;

public class MoveTest extends GameObject {

    public void Update() {

        if (KeyController.IsPressed("A")) {

            position.x -= 200 * EditorController.DeltaTime();
        }
        if (KeyController.IsPressed("D")) {

            position.x += 200 * EditorController.DeltaTime();
        }
        if (KeyController.IsPressed("W")) {

            position.y += 200 * EditorController.DeltaTime();
        }
        if (KeyController.IsPressed("S")) {

            position.y -= 200 * EditorController.DeltaTime();
        }

        if (KeyController.IsPressed("SPACE")) {

            GameObject gameObject = new GameObject();
            gameObject.setPosition(position);
            gameObject.setScale(scale);
            gameObject.setImage(imagePath);
        }

        if (KeyController.IsPressed(KeyEvent.VK_ADD)) {

            scale.Multiply(new Vector2(1.1F, 1.1F));
        }
        if (KeyController.IsPressed(109)) {

            scale.Divide(new Vector2(1.1F, 1.1F));
        }
    }

    public void LateUpdate() {

        Camera camera = EditorController.getCamera();

        if (KeyController.IsPressed("LEFT")) {

            camera.Move(new Vector2(-200 * EditorController.DeltaTime(), 0));
        }
        if (KeyController.IsPressed("RIGHT")) {

            camera.Move(new Vector2(200 * EditorController.DeltaTime(), 0));
        }
        if (KeyController.IsPressed("UP")) {

            camera.Move(new Vector2(0, 200 * EditorController.DeltaTime()));
        }
        if (KeyController.IsPressed("DOWN")) {

            camera.Move(new Vector2(0, -200 * EditorController.DeltaTime()));
        }
    }
}
