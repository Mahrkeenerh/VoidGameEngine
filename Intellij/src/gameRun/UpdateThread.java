package gameRun;

public class UpdateThread implements Runnable{

    public void run() {
        GameController.Update();
    }
}
