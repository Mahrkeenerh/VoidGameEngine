package gameCore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {

    // Globals - config.json
    private static final int refreshRate = 300;
    private static final int physicsRate = 75;

    private static double deltaTime = 0;
    private static double physicsDeltaTime = 0;

    private static double runningTime = 0;
    private static boolean running = true;

    private static final int width = 1920;
    private static final int height = 1080;
    private static final String windowName = "VOID ENGINE";

    private static List<GameObject> objectList = new ArrayList<>();
    private static List<GameObject> oldObjectList;
    private static GameObject camera;

    // No instances of this class
    private GameController() {

    }


    // Called at the start of game in zOrder
    public static void Start() {

        oldObjectList = new ArrayList<>(objectList);

        for (GameObject gameObject: oldObjectList) {

            gameObject.Start();
        }
    }

    // Called in fixed time intervals
    public static void PhysicsUpdate() {

        c1++;
        runTime += physicsDeltaTime;
        if (runTime > 1) {

            runTime = 0;
            System.out.format("Physics: %d FPS: %d\n", c1, c2);
            c1 = 0;
            c2 = 0;
        }

        oldObjectList = new ArrayList<>(objectList);

        for (GameObject gameObject: oldObjectList) {

            gameObject.PhysicsUpdate();
        }
    }

    // TODO REMOVE
    private static double runTime = 0;
    private static int c1 = 0;
    private static int c2 = 0;

    // Called each frame in zOrder
    public static void Update() {

        c2++;

        oldObjectList = new ArrayList<>(objectList);

        for (GameObject gameObject: oldObjectList) {

            gameObject.Update();
        }
    }

    // Called each frame after rendering in zOrder
    public static void LateUpdate() {

        oldObjectList = new ArrayList<>(objectList);

        for (GameObject gameObject: oldObjectList) {

            gameObject.LateUpdate();
        }
    }


    // Get set camera
    public static GameObject getCamera() {

        return camera;
    }

    public static void setCamera(GameObject newCamera) {

        camera = newCamera;
    }

    // Add new GameObject to objectList for Update and Render
    public static void addObjectToList(GameObject gameObject) {

        for (int i = 0; i < objectList.size(); i++) {

            if (objectList.get(i).getzOrder() > gameObject.getzOrder()) {

                objectList.add(i, gameObject);
                return;
            }
        }

        objectList.add(objectList.size(), gameObject);
    }

    // Get objectList
    public static List<GameObject> getObjectList() {

        Collections.sort(objectList);
        return objectList;
    }

    // Clear objectList
    public static void clearObjectList() {

        objectList = new ArrayList<GameObject>();
    }

    // Get running time
    public static double getRunningTime() {

        return runningTime;
    }

    // Add delta time to running time
    protected static void increaseRunningTime(double deltaTime) {

        runningTime += deltaTime;
    }

    // Return if game is running
    public static boolean isRunning() {

        return running;
    }

    // Stop the game
    public static void Stop() {

        running = false;
    }

    // Return max refresh rate
    public static int RefreshRate() {

        return refreshRate;
    }

    // Return physics update
    public static int PhysicsRate() {

        return physicsRate;
    }

    // Return current FPS
    public static long FPS() {

        return (long) (1 / DeltaTime());
    }

    // Return window sizes
    public static int Width() {

        return width;
    }

    public static int Height() {

        return height;
    }

    // Return canvas name
    public static String WindowName() {

        return windowName;
    }

    // Return Delta time
    public static double DeltaTime() {

        return deltaTime;
    }

    // Return Physics Delta time
    public static double PhysicsDeltaTime() {

        return physicsDeltaTime;
    }

    // Delta setters
    public static void setDeltaTime(double newDeltaTime) {

        deltaTime = newDeltaTime;
    }

    public static void setPhysicsDeltaTime(double newPhysicsDeltaTime) {

        physicsDeltaTime = newPhysicsDeltaTime;
    }
}
