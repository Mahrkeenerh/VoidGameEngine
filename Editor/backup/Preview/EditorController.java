package Preview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditorController {

    private static final int refreshRate = 300;

    private static double deltaTime = 0;

    private static final int width = 1920;
    private static final int height = 1080;
    private static final String windowName = "VOID EDITOR";

    private static List<GameObject> objectList = new ArrayList<>();
    private static Camera camera;

    // No instances of this class
    private EditorController() {

    }


    // Called each frame
    public static void Update() {

    }


    // Get set camera
    public static Camera getCamera() {

        return camera;
    }

    public static void setCamera(Camera newCamera) {

        camera = newCamera;
    }

    // Add new GameObject to objectList
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

    // Return max refresh rate
    public static int RefreshRate() {

        return refreshRate;
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

    // Delta setters
    public static void setDeltaTime(double newDeltaTime) {

        deltaTime = newDeltaTime;
    }
}
