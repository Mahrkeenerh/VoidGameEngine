package gameCore;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Dictionary;
import java.util.Hashtable;

public class KeyController extends KeyAdapter {

    public static Dictionary<Integer, Boolean> pressedDict = new Hashtable<>();

    public KeyController() {

    }

    public void keyTyped(java.awt.event.KeyEvent e) {

        // YOU GET NOTHING
        // GOOD DAY SIR!
    }

    public void keyPressed(java.awt.event.KeyEvent e) {

        pressedDict.put(e.getKeyCode(), true);
    }

    public void keyReleased(java.awt.event.KeyEvent e) {

        pressedDict.put(e.getKeyCode(), false);
    }

    public static boolean IsPressed(int keyCode) {

        if (pressedDict.get(keyCode) == null) {
            return false;
        }

        return pressedDict.get(keyCode);
    }

    public static boolean IsPressed(String keyString) {

        if (pressedDict.get(GetKeyCode(keyString)) == null) {
            return false;
        }

        return pressedDict.get(GetKeyCode(keyString));
    }

    // Convert keyString to keyCode
    public static int GetKeyCode(String keyString) {

        try {
            return KeyEvent.class.getField("VK_" + keyString).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
