package gameCore;

public class TestVoid extends GameObject {

    public TestVoid() {

        super();
    }

    public void Update() {

        if (KeyController.IsPressed("A")) {
            position.x--;
        }

        if (KeyController.IsPressed("D")) {
            position.x++;
        }

        if (KeyController.IsPressed("W")) {
            position.y++;
        }
        if (KeyController.IsPressed("S")) {
            position.y--;
        }

        if (KeyController.IsPressed("LEFT")) {
            GameController.getCamera().position.x--;
        }

        if (KeyController.IsPressed("RIGHT")) {
            GameController.getCamera().position.x++;
        }

        if (KeyController.IsPressed("UP")) {
            GameController.getCamera().position.y++;
        }
        if (KeyController.IsPressed("DOWN")) {
            GameController.getCamera().position.y--;
        }
    }
}
