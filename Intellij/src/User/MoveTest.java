package User;

import gameCore.*;

public class MoveTest extends GameObject {

    public void Update() {

        if (KeyController.IsPressed("A")) {

            position.x -= 200 * GameController.DeltaTime();
        }
        if (KeyController.IsPressed("D")) {

            position.x += 200 * GameController.DeltaTime();
        }
        if (KeyController.IsPressed("W")) {

            position.y += 200 * GameController.DeltaTime();
        }
        if (KeyController.IsPressed("S")) {

            position.y -= 200 * GameController.DeltaTime();
        }

        if (KeyController.IsPressed("SPACE")) {

            GameObject gameObject = new GameObject();
            gameObject.setPosition(position);
            gameObject.setScale(scale);
            gameObject.setImage(imagePath);
        }
    }

    public void LateUpdate() {

        Camera camera = GameController.getCamera();

        if (KeyController.IsPressed("LEFT")) {

            camera.Move(new Vector2(-200 * GameController.DeltaTime(), 0));
        }
        if (KeyController.IsPressed("RIGHT")) {

            camera.Move(new Vector2(200 * GameController.DeltaTime(), 0));
        }
        if (KeyController.IsPressed("UP")) {

            camera.Move(new Vector2(0, 200 * GameController.DeltaTime()));
        }
        if (KeyController.IsPressed("DOWN")) {

            camera.Move(new Vector2(0, -200 * GameController.DeltaTime()));
        }
    }
}
