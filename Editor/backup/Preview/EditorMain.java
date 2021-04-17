package Preview;

import User.MoveTest;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditorMain extends Application {

    @FXML
    private SwingNode previewPanel;

    private static GameWindow gameWindow;

    private static double updatePassedTime;

    @FXML
    private void initialize() {

        gameWindow = new GameWindow();
        gameWindow.setFocusable(true);
        gameWindow.addKeyListener(new KeyController());
        gameWindow.requestFocusInWindow();

        previewPanel.setContent(gameWindow);

        EditorController.setCamera(new Camera());
        EditorController.clearObjectList();

        GameObject pozadie = new GameObject();
        pozadie.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/radiant_dire2.jpg");
        pozadie.setPosition(new Vector2(-250, 250));

        GameObject move = new MoveTest();
        move.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/Logo.png");
        move.setScale(new Vector2(0.5F, 0.5F));

        int refreshRate = EditorController.RefreshRate();
        long lastFrameTime = System.nanoTime();
        long currentFrameTime;
        long passedTime = 0;

        // Editor loop
        while (true) {

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

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../FXMLs/Editor.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

    // Render stuff to the canvas
    private void Render() {

        System.out.println(gameWindow.getSize());
        gameWindow.repaint();
    }

    // Update stuff
    private void Update() {

    }
}
