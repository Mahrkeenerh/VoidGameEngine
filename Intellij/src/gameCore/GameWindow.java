package gameCore;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {

    private final JFrame frame;

    public GameWindow() {

        setMinimumSize(new Dimension(GameController.Width(), GameController.Height()));
        setMaximumSize(new Dimension(GameController.Width(), GameController.Height()));
        setPreferredSize(new Dimension(GameController.Width(), GameController.Height()));

        frame = new JFrame(GameController.WindowName());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        Camera camera = GameController.getCamera();

        for (GameObject gameObject: GameController.getObjectList()) {

            g.drawImage(
                    gameObject.getImage(),
                    (int) (gameObject.getPosition().x - camera.getPosition().x * camera.getScale().x),
                    (int) -(gameObject.getPosition().y - camera.getPosition().y * camera.getScale().y),
                    (int) (gameObject.getImageResolution().x * gameObject.getScale().x / camera.getScale().x),
                    (int) (gameObject.getImageResolution().y * gameObject.getScale().y / camera.getScale().y),
                    this);
        }
    }

}
