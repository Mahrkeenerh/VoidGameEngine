package gameCore;

import Game.MoveTest;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

        GameController.setCamera(new GameObject());
        GameController.clearObjectList();

        load();
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

    private static void load() {

        try {
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\src\\objectList.json");

            List objectList = new Gson().fromJson(fileReader, List.class);

            GameObject camera = new GameObject();
            camera.setPosition(new Vector2((double) ((ArrayList) objectList.get(0)).get(1),
                    (double) ((ArrayList) objectList.get(0)).get(1)));
            camera.setPosition(new Vector2((double) ((ArrayList) objectList.get(0)).get(3),
                    (double) ((ArrayList) objectList.get(0)).get(4)));
            GameController.setCamera(camera);
            GameController.clearObjectList();

            for (int i = 1; i < objectList.size(); i++) {

                GameObject gameObject;

                if (((ArrayList) objectList.get(i)).get(0).equals("")) {
                    gameObject = new GameObject();
                }
                else {
                    String className = ((String) ((ArrayList) objectList.get(i)).get(0)).split("[.]")[0].replace('\\', '.');
                    gameObject = (GameObject) Class.forName(className).getConstructor().newInstance();
                }

                gameObject.setPosition(new Vector2((double) ((ArrayList) objectList.get(i)).get(1),
                        (double) ((ArrayList) objectList.get(i)).get(2)));
                gameObject.setScale(new Vector2((double) ((ArrayList) objectList.get(i)).get(3),
                        (double) ((ArrayList) objectList.get(i)).get(4)));

                if (((ArrayList) objectList.get(i)).get(5) != null) {
                    gameObject.setImage((String) ((ArrayList) objectList.get(i)).get(5));
                }

                gameObject.setzOrder((int) ((double) ((ArrayList) objectList.get(i)).get(6)));
            }
        } catch (Exception e) {
        }
    }
}
