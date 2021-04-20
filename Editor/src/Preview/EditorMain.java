package Preview;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditorMain extends Application implements Runnable {

    @FXML
    private SwingNode previewPanel;
    @FXML
    private ListView objectList;
    @FXML
    private Label objectLabel;
    @FXML
    private TextField positionXField;
    @FXML
    private TextField positionYField;
    @FXML
    private TextField scaleXField;
    @FXML
    private TextField scaleYField;
    @FXML
    private TextField imageField;
    @FXML
    private TextField zOrderField;
    @FXML
    private TextField scriptField;
    @FXML
    private Button pickButton;
    @FXML
    private Button createButton;
    @FXML
    private Button runButton;
    @FXML
    private TextArea consoleArea;
    @FXML
    private Rectangle rectangle0;
    @FXML
    private Rectangle rectangle1;

    private static String activeStyle;
    private static GameObject activeObject;

    private static PreviewWindow previewWindow;

    private static double updatePassedTime;

    private static EditorMain guiWindow;

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

        activeObject = new GameObject();
        activeObject.setName("Camera");
        EditorController.setCamera(activeObject);
        EditorController.clearObjectList();

        setFocusListeners();

        // TODO Load every object from a file

        GameObject pozadie = new GameObject();
        pozadie.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/radiant_dire2.jpg");
        pozadie.setPosition(new Vector2(-250, 250));

        try {
            GameObject move = (GameObject) Class.forName("Preview.GameObject").getConstructor().newInstance();
            move.setImage("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/Logo.png");
            move.setScale(new Vector2(0.5F, 0.5F));
        } catch (Exception e) {
            e.printStackTrace();
        }

        guiWindow = this;
        reloadObjectList();
        new Thread(new EditorMain()).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../GUI/Editor.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        activeStyle = "DarkStyle.css";
        scene.getStylesheets().add(getClass().getResource("/GUI/" + activeStyle).toExternalForm());
        primaryStage.getIcons().add(new Image("/res/Icon.png"));
        primaryStage.setTitle("Void editor");

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

        // Camera control
        GameObject camera = EditorController.getCamera();

        if (KeyController.IsPressed("LEFT")) {

            camera.Move(new Vector2(-200 * EditorController.DeltaTime() * camera.getScale().x, 0));
            loadObject();
        }
        if (KeyController.IsPressed("RIGHT")) {

            camera.Move(new Vector2(200 * EditorController.DeltaTime() * camera.getScale().x, 0));
            loadObject();
        }
        if (KeyController.IsPressed("UP")) {

            camera.Move(new Vector2(0, 200 * EditorController.DeltaTime() * camera.getScale().y));
            loadObject();
        }
        if (KeyController.IsPressed("DOWN")) {

            camera.Move(new Vector2(0, -200 * EditorController.DeltaTime() * camera.getScale().y));
            loadObject();
        }
        // Scale up: keypad +
        if (KeyController.IsPressed(109)) {

            double multiply = 1.001F;
            camera.setScale(new Vector2(camera.getScale().x * multiply, camera.getScale().y * multiply));
            loadObject();
        }
        // Scale up: keypad -
        if (KeyController.IsPressed(107)) {

            double multiply = 1.001F;
            camera.setScale(new Vector2(camera.getScale().x / multiply, camera.getScale().y / multiply));
            loadObject();
        }
    }

    private void setFocusListeners() {

        positionXField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyPositionX();
            }
        });

        positionYField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyPositionY();
            }
        });

        scaleXField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyScaleX();
            }
        });

        scaleYField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyScaleY();
            }
        });

        imageField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyImage();
            }
        });

        zOrderField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyZOrder();
            }
        });

        scriptField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyScript();
            }
        });
    }

    private void verifyPositionX() {

        String text = positionXField.getText();
        if (text.matches("[0-9]+(\\.[0-9]+)*")) {
            activeObject.setPosition(new Vector2(Double.parseDouble(text), activeObject.getPosition().y));
        }
        else {
            positionXField.setText(String.valueOf(activeObject.getPosition().x));
        }
    }

    private void verifyPositionY() {

        String text = positionYField.getText();
        if (text.matches("[0-9]+(\\.[0-9]+)*")) {
            activeObject.setPosition(new Vector2(activeObject.getPosition().x, Double.parseDouble(text)));
        }
        else {
            positionYField.setText(String.valueOf(activeObject.getPosition().y));
        }
    }

    private void verifyScaleX() {

        String text = scaleXField.getText();
        if (text.matches("[0-9]+(\\.[0-9]+)*")) {
            activeObject.setScale(new Vector2(Double.parseDouble(text), activeObject.getScale().y));
        }
        else {
            scaleXField.setText(String.valueOf(activeObject.getPosition().x));
        }
    }

    private void verifyScaleY() {

        String text = scaleYField.getText();
        if (text.matches("[0-9]+(\\.[0-9]+)*")) {
            activeObject.setScale(new Vector2(activeObject.getScale().x, Double.parseDouble(text)));
        }
        else {
            scaleYField.setText(String.valueOf(activeObject.getPosition().y));
        }
    }

    private void verifyImage() {

        // TODO something something
    }

    private void verifyZOrder() {

        String text = zOrderField.getText();
        if (text.matches("[0-9]+")) {
            activeObject.setzOrder(Integer.parseInt(text));
        }
        else {
            zOrderField.setText(String.valueOf(activeObject.getzOrder()));
        }
    }

    private void verifyScript() {

        // TODO something something
    }

    @FXML
    private void loseFocus() {

        previewPanel.requestFocus();
    }

    private void reloadObjectList() {

        objectList.getItems().clear();
        objectList.getItems().add(EditorController.getCamera().getName());

        for (GameObject gameObject: EditorController.getObjectList()) {
            objectList.getItems().add(gameObject.getName());
        }
    }

    private void loadObject() {

        if (activeObject == null) {
            activeObject = EditorController.getCamera();
        }

        if (activeObject.getName().equals("Camera")) {

            Platform.runLater(() -> {
                guiWindow.getImageField().setText("");
                guiWindow.getzOrderField().setText("");
                guiWindow.getScriptField().setText("");

                guiWindow.getImageField().setEditable(false);
                guiWindow.getzOrderField().setEditable(false);
                guiWindow.getScriptField().setEditable(false);

                guiWindow.getPickButton().setDisable(true);
                guiWindow.getCreateButton().setDisable(true);
            });
        }
        else {

            Platform.runLater(() -> {
                guiWindow.getImageField().setText(activeObject.getImagePath());
                guiWindow.getzOrderField().setText(String.valueOf(activeObject.getzOrder()));
                guiWindow.getScriptField().setText(activeObject.getScript());

                guiWindow.getImageField().setEditable(true);
                guiWindow.getzOrderField().setEditable(true);
                guiWindow.getScriptField().setEditable(true);

                guiWindow.getPickButton().setDisable(false);
                guiWindow.getCreateButton().setDisable(false);
            });
        }

        Platform.runLater(() -> {
            guiWindow.getObjectLabel().setText(activeObject.getName());
            guiWindow.getPositionXField().setText(String.valueOf(activeObject.getPosition().x));
            guiWindow.getPositionYField().setText(String.valueOf(activeObject.getPosition().y));
            guiWindow.getScaleXField().setText(String.valueOf(activeObject.getScale().x));
            guiWindow.getScaleYField().setText(String.valueOf(activeObject.getScale().y));
        });
    }

    @FXML
    private void selectObject() {

        Platform.runLater(() -> {
            String objectName = (String) guiWindow.getObjectList().getSelectionModel().getSelectedItem();

            if (objectName == null) {
                return;
            }

            GameObject object = EditorController.getObject(objectName);
            activeObject = object;
            loadObject();
        });
    }

    @FXML
    private void addObject() {

        System.out.println("OBJECT");
    }

    @FXML
    private void editName() {

    }

    @FXML
    private void removeObject() {

    }

    @FXML
    private void changeStyle() {

        String rectangleColor = "";

        if (activeStyle.equals("DarkStyle.css")) {
            activeStyle = "LightStyle.css";
            rectangleColor = "#e0e0e0";
        }
        else {
            activeStyle = "DarkStyle.css";
            rectangleColor = "#292929";
        }

        rectangle0.setFill(Paint.valueOf(rectangleColor));
        rectangle1.setFill(Paint.valueOf(rectangleColor));

        previewPanel.getScene().getStylesheets().clear();
        previewPanel.getScene().getStylesheets().add(getClass().getResource("/GUI/" + activeStyle).toExternalForm());
    }

    public SwingNode getPreviewPanel() {
        return previewPanel;
    }

    public ListView getObjectList() {
        return objectList;
    }

    public Label getObjectLabel() {
        return objectLabel;
    }

    public TextField getPositionXField() {
        return positionXField;
    }

    public TextField getPositionYField() {
        return positionYField;
    }

    public TextField getScaleXField() {
        return scaleXField;
    }

    public TextField getScaleYField() {
        return scaleYField;
    }

    public TextField getImageField() {
        return imageField;
    }

    public TextField getzOrderField() {
        return zOrderField;
    }

    public TextField getScriptField() {
        return scriptField;
    }

    public Button getPickButton() {
        return pickButton;
    }

    public Button getCreateButton() {
        return createButton;
    }

    public Button getRunButton() {
        return runButton;
    }

    public TextArea getConsoleArea() {
        return consoleArea;
    }

    public Rectangle getRectangle0() {
        return rectangle0;
    }

    public Rectangle getRectangle1() {
        return rectangle1;
    }
}
