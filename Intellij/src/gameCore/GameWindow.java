package gameCore;

import javax.swing.*;
import java.awt.*;
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

        List<GameObject> oldObjectList = new ArrayList<>(GameController.getObjectList());

        for (GameObject gameObject: oldObjectList) {

            int centerOffsetX = GameController.Width() / 2;
            int centerOffsetY = GameController.Height() / 2;

            double cornerPositionX = gameObject.getPosition().x - camera.getPosition().x * camera.getScale().x;
            double cornerPositionY = -(gameObject.getPosition().y - camera.getPosition().y * camera.getScale().y);

            double imageOffsetX = gameObject.getImageResolution().x * gameObject.getScale().x / 2;
            double imageOffsetY = gameObject.getImageResolution().y * gameObject.getScale().y / 2;

            g.drawImage(
                    gameObject.getImage(),
                    (int) (cornerPositionX + centerOffsetX - imageOffsetX),
                    (int) (cornerPositionY + centerOffsetY - imageOffsetY),
                    (int) (gameObject.getImageResolution().x * gameObject.getScale().x / camera.getScale().x),
                    (int) (gameObject.getImageResolution().y * gameObject.getScale().y / camera.getScale().y),
                    this
            );
        }
    }

}
