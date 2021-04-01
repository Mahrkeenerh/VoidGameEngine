package gameRun;

import java.awt.*;

public class GameMain extends Canvas {

    private static double deltaTime;

    public static void main(String[] args) {

        // Start the game
        new Thread(new RunThread(0)).start();

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

                // Late Update
                new Thread(new RunThread(2)).start();
            }
        }
    }

    public static double DeltaTime() {

        return deltaTime;
    }
}
