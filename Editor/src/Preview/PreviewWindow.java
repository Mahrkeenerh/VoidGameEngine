package Preview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PreviewWindow extends JPanel {

    public PreviewWindow() {

        setMinimumSize(new Dimension(EditorController.Width(), EditorController.Height()));
        setMaximumSize(new Dimension(EditorController.Width(), EditorController.Height()));
        setPreferredSize(new Dimension(EditorController.Width(), EditorController.Height()));
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Camera camera = EditorController.getCamera();

        List<GameObject> oldObjectList = new ArrayList<>(EditorController.getObjectList());

        for (GameObject gameObject: oldObjectList) {

            int centerOffsetX = EditorController.Width() / 2;
            int centerOffsetY = EditorController.Height() / 2;

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
