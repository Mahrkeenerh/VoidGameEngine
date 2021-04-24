package Game;

import gameCore.GameController;
import gameCore.GameObject;
import gameCore.KeyController;
import gameCore.Vector2;

public class Snek extends GameObject {

    private int orientation = -1;
    public Snek parent = null;
    private double fraction = 15;

    private boolean spaceReleased = false;
    private boolean canSpawn = true;

    public void Update() {

        if (parent == null) {

            if (KeyController.IsPressed("A")) {

                orientation = 0;
            }
            if (KeyController.IsPressed("D")) {

                orientation = 2;
            }
            if (KeyController.IsPressed("W")) {

                orientation = 1;
            }
            if (KeyController.IsPressed("S")) {

                orientation = 3;
            }

            if (orientation == 0) {

                position.x -= 500 * GameController.DeltaTime();
            }
            if (orientation == 1) {

                position.y += 500 * GameController.DeltaTime();
            }
            if (orientation == 2) {

                position.x += 500 * GameController.DeltaTime();
            }
            if (orientation == 3) {

                position.y -= 500 * GameController.DeltaTime();
            }
        }
        else {

            double distance = Math.sqrt(Math.pow(parent.getPosition().x - position.x, 2) + Math.pow(parent.getPosition().y - position.y, 2));
            Move(new Vector2((parent.getPosition().x - position.x) * fraction * GameController.DeltaTime(),
                    (parent.getPosition().y - position.y) * fraction * GameController.DeltaTime()));
        }

        if (KeyController.IsPressed("SPACE")) {

            if (canSpawn && spaceReleased) {
                Snek gameObject = new Snek();
                gameObject.setPosition(position);
                gameObject.setScale(scale);
                gameObject.setImage("src/res/laser.png");
                gameObject.parent = this;
                canSpawn = false;
            }
        } else {
            spaceReleased = true;
        }
    }
}
