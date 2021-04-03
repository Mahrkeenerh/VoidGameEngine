package gameCore;

public class GameMain {

    private static double deltaTime;
    private static GameWindow gameWindow;

    public static void main(String[] args) {

        // Start the game
        Start();

        int refreshRate = GameController.RefreshRate();
        long currentFrameTime = 0;
        long lastFrameTime = System.nanoTime();

        // Game loop
        while (GameController.isRunning()) {

            currentFrameTime = System.nanoTime();

            if (currentFrameTime - lastFrameTime >= 1_000_000_000 / refreshRate) {

                // Update
                GameController.Update();

                deltaTime = (double) (currentFrameTime - lastFrameTime) / 1_000_000_000;
                lastFrameTime = currentFrameTime;

                // Some rendering
                Render();

                // Late Update
                GameController.LateUpdate();
            }
        }

        gameWindow.closeFrame();
    }

    // Before start
    private static void Start() {

        System.setProperty("sun.java2d.opengl", "true");

        gameWindow = new GameWindow();
        gameWindow.setFocusable(true);
        KeyController keyController = new KeyController();
        gameWindow.addKeyListener(keyController);

        // Add all existing gameObjects to GameController
        GameObject pozadie = new GameObject();
        pozadie.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/radiant_dire2.jpg");
        pozadie.setPosition(new Vector2(-250, 250));

        GameController.Start();
    }

    // Render stuff to the canvas
    private static void Render() {

        gameWindow.repaint();
    }

    public static double DeltaTime() {

        return deltaTime;
    }
}
