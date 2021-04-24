package gameCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends JPanel {

    private final JFrame frame;

    public GameWindow() {

        setMinimumSize(new Dimension(GameController.Width(), GameController.Height()));
        setMaximumSize(new Dimension(GameController.Width(), GameController.Height()));
        setPreferredSize(new Dimension(GameController.Width(), GameController.Height()));

        frame = new JFrame(GameController.WindowName());
        frame.setIconImage((new ImageIcon("res/Icon.png")).getImage());

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameController.Stop();
            }
        };

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(exitListener);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void closeFrame() {

        frame.dispose();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        GameObject camera = GameController.getCamera();

        List<GameObject> oldObjectList = new ArrayList<>(GameController.getObjectList());

        for (GameObject gameObject: oldObjectList) {

            double absoluteCornerX = gameObject.getPosition().x - gameObject.getImageResolution().x * gameObject.getScale().x / 2;
            double absoluteCornerY = -gameObject.getPosition().y - gameObject.getImageResolution().y * gameObject.getScale().y / 2;

            double cameraCornerX = GameController.getCamera().getPosition().x - GameController.Width() * GameController.getCamera().getScale().x / 2;
            double cameraCornerY = -GameController.getCamera().getPosition().y - GameController.Height() * GameController.getCamera().getScale().y / 2;

            g.drawImage(
                    gameObject.getImage(),
                    (int) ((absoluteCornerX - cameraCornerX) / camera.getScale().x),
                    (int) ((absoluteCornerY - cameraCornerY) / camera.getScale().y),
                    (int) (gameObject.getImageResolution().x * gameObject.getScale().x / camera.getScale().x),
                    (int) (gameObject.getImageResolution().y * gameObject.getScale().y / camera.getScale().y),
                    this
            );
        }
    }

}
