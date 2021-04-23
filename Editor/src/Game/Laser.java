package Game;

import gameCore.GameController;
import gameCore.GameObject;

public class Laser extends GameObject {

    public void Update() {

        position.y += 1000 * GameController.DeltaTime();
    }
}
