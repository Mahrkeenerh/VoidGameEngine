package gameRun;

public class GameController {

    // Globals - config.json
    private static final int refreshRate = 100;
    private static final int width = 1920;
    private static final int height = 1080;
    private static final String windowName = "VOID ENGINE";

    private static boolean running = true;

    // No instances of this class
    private GameController() {

    }


    // Called at the start of game
    public static void Start() {

        x = System.currentTimeMillis() / 1000;
        counter = 0;
    }

    private static long x, counter;

    // Called each frame
    public static void Update() {

        counter++;
        long y = System.currentTimeMillis() / 1000;

        if (y > x) {
            System.out.println(counter);
            counter = 0;
            x = y;
        }

    }

    // Called each frame after rendering
    public static void LateUpdate() {

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
