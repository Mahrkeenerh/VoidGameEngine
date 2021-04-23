package Game;

import gameCore.*;

import java.awt.event.KeyEvent;

public class MoveTest extends GameObject {

    private boolean canFire = true;

    public void Update() {

        if (KeyController.IsPressed("A")) {

            position.x -= 500 * GameController.DeltaTime();
        }
        if (KeyController.IsPressed("D")) {

            position.x += 500 * GameController.DeltaTime();
        }
        if (KeyController.IsPressed("W")) {

            position.y += 500 * GameController.DeltaTime();
        }
        if (KeyController.IsPressed("S")) {

            position.y -= 500 * GameController.DeltaTime();
        }

        if (KeyController.IsPressed("SPACE")) {

            if (canFire) {
                GameObject gameObject = new Laser();
                gameObject.setPosition(new Vector2(position.x - 25, position.y + 25));
                gameObject.setScale(new Vector2(0.02, 0.5));
                gameObject.setImage("src/res/laser.png");
                gameObject = new Laser();
                gameObject.setPosition(new Vector2(position.x + 25, position.y + 25));
                gameObject.setScale(new Vector2(0.02, 0.5));
                gameObject.setImage("src/res/laser.png");
                //canFire = false;
            }
        }
        else {
            canFire = true;
        }

        if (KeyController.IsPressed(KeyEvent.VK_ADD)) {

            scale.Multiply(new Vector2(1.1F, 1.1F));
        }
        if (KeyController.IsPressed(109)) {

            scale.Divide(new Vector2(1.1F, 1.1F));
        }
    }
}
