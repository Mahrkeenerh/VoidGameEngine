package gameCore;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    // Globals - config.json
    private static final int refreshRate = 100;
    private static final int width = 1920;
    private static final int height = 1080;
    private static final String windowName = "VOID ENGINE";

    private static List<GameObject> objectList = new ArrayList<GameObject>();
    private static Camera camera = new Camera();

    private static boolean running = true;

    // No instances of this class
    private GameController() {

    }


    // TODO move stuff out
    // Called at the start of game in zOrder
    public static void Start() {

        for (GameObject gameObject: objectList) {

            new Thread(new RunThread(gameObject, 0)).start();
        }
    }

    // Called each frame in zOrder
    public static void Update() {

        for (GameObject gameObject: objectList) {

            new Thread(new RunThread(gameObject, 1)).start();
        }
    }

    // Called each frame after rendering in zOrder
    public static void LateUpdate() {

        for (GameObject gameObject: objectList) {

            new Thread(new RunThread(gameObject, 2)).start();
        }
    }


    // Get camera
    public static Camera getCamera() {

        return camera;
    }

    // TODO CONSTANT SORT
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

        return objectList;
    }

    // Return if game is running
    public static boolean isRunning() {

        return running;
    }

    // Stop the game
    public static void Stop() {

        running = false;
    }

    // Return target refresh rate
    public static int RefreshRate() {

        return refreshRate;
    }

    // Return current FPS
    public static long FPS() {

        return (long) (1 / DeltaTime());
    }

    // Return canvas sizes
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

        return GameMain.DeltaTime();
    }
}
