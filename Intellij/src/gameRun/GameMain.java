package gameRun;

public class GameMain {

    private static double deltaTime;
    private static GameCanvas gameCanvas;

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
                new Thread(new RunThread(1)).start();

                deltaTime = (double) (currentFrameTime - lastFrameTime) / 1_000_000_000;
                lastFrameTime = currentFrameTime;

                // Some rendering
                Render();

                // Late Update
                new Thread(new RunThread(2)).start();
            }
        }

        gameCanvas.closeFrame();
    }

    // Render stuff to the canvas
    private static void Render() {

    }

    // Before start
    private static void Start() {

        gameCanvas = new GameCanvas();
        gameCanvas.addKeyListener(new KeyController());

        new Thread(new RunThread(0)).start();
    }

    public static double DeltaTime() {

        return deltaTime;
    }
}
