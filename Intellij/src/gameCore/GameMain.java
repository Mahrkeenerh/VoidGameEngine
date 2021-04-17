package gameCore;

import User.MoveTest;

public class GameMain implements Runnable {

    private static GameWindow gameWindow;

    private static double updatePassedTime;
    private static double physicsPassedTime;

    private GameMain() {

    }

    public void run() {

        int physicsRate = GameController.PhysicsRate();
        long lastFrameTime = System.nanoTime();
        long currentFrameTime;
        long passedTime = 0;

        // Physics Game loop
        while (GameController.isRunning()) {

            currentFrameTime = System.nanoTime();
            passedTime = currentFrameTime - lastFrameTime;
            lastFrameTime = currentFrameTime;

            physicsPassedTime += passedTime;

            // Physics update
            if (physicsPassedTime >= (double) 1_000_000_000 / physicsRate) {

                GameController.setPhysicsDeltaTime(physicsPassedTime / 1_000_000_000);
                GameController.increaseRunningTime(physicsPassedTime / 1_000_000_000);
                physicsPassedTime = 0;

                PhysicsUpdate();
            }
        }
    }

    public static void main(String[] args) {

        // Start the game
        Start();

        // Physics thread
        new Thread(new GameMain()).start();

        int refreshRate = GameController.RefreshRate();
        long lastFrameTime = System.nanoTime();
        long currentFrameTime;
        long passedTime = 0;

        // Game loop
        while (GameController.isRunning()) {

            currentFrameTime = System.nanoTime();
            passedTime = currentFrameTime - lastFrameTime;
            lastFrameTime = currentFrameTime;

            updatePassedTime += passedTime;

            // Render time
            if (updatePassedTime >= (double) 1_000_000_000 / refreshRate) {

                GameController.setDeltaTime(updatePassedTime / 1_000_000_000);
                updatePassedTime = 0;

                Update();
                Render();
                LateUpdate();
            }
        }

        gameWindow.closeFrame();
    }

    // Initial setup
    private static void Start() {

        updatePassedTime = 0;
        physicsPassedTime = 0;

        gameWindow = new GameWindow();
        gameWindow.setFocusable(true);
        gameWindow.addKeyListener(new KeyController());
        gameWindow.requestFocusInWindow();

        GameController.setCamera(new Camera());
        GameController.clearObjectList();

        // TODO Load gameObjects from json to GameController
        GameObject pozadie = new GameObject();
        pozadie.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/radiant_dire2.jpg");
        pozadie.setPosition(new Vector2(-250, 250));

        GameObject move = new MoveTest();
        move.setImage("res/Logo.png");
        move.setScale(new Vector2(0.5F, 0.5F));

        GameController.Start();
    }

    // Fizik
    private static void PhysicsUpdate() {

        GameController.PhysicsUpdate();
    }

    // Before render
    private static void Update() {

        GameController.Update();
    }

    // Render stuff to the canvas
    private static void Render() {

        gameWindow.repaint();
    }

    // After render
    private static void LateUpdate() {

        GameController.LateUpdate();
    }
}
