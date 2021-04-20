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

        GameObject camera = EditorController.getCamera();

        List<GameObject> oldObjectList = new ArrayList<>(EditorController.getObjectList());

        for (GameObject gameObject: oldObjectList) {

            double absoluteCornerX = gameObject.getPosition().x - gameObject.getImageResolution().x * gameObject.getScale().x / 2;
            double absoluteCornerY = -gameObject.getPosition().y - gameObject.getImageResolution().y * gameObject.getScale().y / 2;

            double cameraCornerX = EditorController.getCamera().getPosition().x - EditorController.Width() * EditorController.getCamera().getScale().x / 2;
            double cameraCornerY = -EditorController.getCamera().getPosition().y - EditorController.Height() * EditorController.getCamera().getScale().y / 2;

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
