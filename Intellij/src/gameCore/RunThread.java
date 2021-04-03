package gameCore;

public class RunThread implements Runnable{

    private int methodSwitch;
    private GameObject gameObject;

    public RunThread(GameObject gameObject, int methodSwitch) {

        this.gameObject = gameObject;
        this.methodSwitch = methodSwitch;
    }

    public void run() {

        switch (methodSwitch) {
            case 0 -> gameObject.Start();
            case 1 -> gameObject.Update();
            case 2 -> gameObject.LateUpdate();
        }
    }
}
