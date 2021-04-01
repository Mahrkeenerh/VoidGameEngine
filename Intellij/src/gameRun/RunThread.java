package gameRun;

public class RunThread implements Runnable{

    public int updateSwitch;

    public RunThread(int updateSwitch) {
        this.updateSwitch = updateSwitch;
    }

    public void run() {

        switch (updateSwitch) {
            case 0 -> GameController.Start();
            case 1 -> GameController.Update();
            case 2 -> GameController.LateUpdate();
        }
    }
}
