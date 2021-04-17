package Preview;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;

public class EditorMain extends Application implements Runnable {

    @FXML
    private SwingNode previewPanel;

    private static PreviewWindow previewWindow;

    private static double updatePassedTime;

    @Override
    public void run() {

        int refreshRate = EditorController.RefreshRate();
        long lastFrameTime = System.nanoTime();
        long currentFrameTime;
        long passedTime = 0;

        // Editor loop
        while (EditorController.isRunning()) {

            currentFrameTime = System.nanoTime();
            passedTime = currentFrameTime - lastFrameTime;
            lastFrameTime = currentFrameTime;

            updatePassedTime += passedTime;

            // Render time
            if (updatePassedTime >= (double) 1_000_000_000 / refreshRate) {

                EditorController.setDeltaTime(updatePassedTime / 1_000_000_000);
                updatePassedTime = 0;

                Update();
                Render();
            }
        }
    }

    @FXML
    private void initialize() {

        previewWindow = new PreviewWindow();
        previewWindow.setFocusable(true);
        previewWindow.addKeyListener(new KeyController());
        previewWindow.requestFocusInWindow();

        previewPanel.setContent(previewWindow);

        EditorController.setCamera(new Camera());
        EditorController.clearObjectList();

        GameObject pozadie = new GameObject();
        pozadie.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/radiant_dire2.jpg");
        pozadie.setPosition(new Vector2(-250, 250));

        GameObject move = new GameObject();
        move.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/Logo.png");
        move.setScale(new Vector2(0.5F, 0.5F));

        new Thread(new EditorMain()).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../GUI/Editor.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add(getClass().getResource("/GUI/Styles.css").toExternalForm());
        primaryStage.getIcons().add(new Image("/res/Icon.png"));

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            EditorController.Stop();
            System.exit(0);
        });
    }

    public static void main(String[] args) {

        launch(args);
    }

    // Render stuff to the canvas
    private void Render() {

        previewWindow.repaint();
    }

    // Update stuff
    private void Update() {

    }
}
