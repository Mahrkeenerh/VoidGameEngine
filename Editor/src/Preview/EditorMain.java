package Preview;

import com.google.gson.Gson;
import gameCore.GameThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditorMain extends Application implements Runnable {

    @FXML
    private SwingNode previewPanel;
    @FXML
    private ListView objectList;
    @FXML
    private TextField objectNameField;
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
    private Button clearButton;
    @FXML
    private TextArea consoleArea;
    @FXML
    private Rectangle rectangle0;
    @FXML
    private Rectangle rectangle1;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu editMenu;
    @FXML
    private Label objectLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label scaleLabel;
    @FXML
    private Label imageLabel;
    @FXML
    private Label zOrderLabel;
    @FXML
    private Label scriptLabel;

    private static String activeStyle;
    private static String activeLanguage;
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

        activeLanguage = "English";

        setFocusListeners();

        guiWindow = this;
        reloadObjectList();
        loadObject();
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

    // Set focus listeners
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

        objectNameField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                verifyObjectName();
            }
        });
    }

    private void verifyPositionX() {

        String text = "0" + positionXField.getText();
        if (text.matches("[0-9]+([.][0-9]+){0,1}")) {
            activeObject.setPosition(new Vector2(Double.parseDouble(text), activeObject.getPosition().y));
        }

        positionXField.setText(String.valueOf(activeObject.getPosition().x));
    }

    private void verifyPositionY() {

        String text = "0" + positionYField.getText();
        if (text.matches("[0-9]+([.][0-9]+){0,1}")) {
            activeObject.setPosition(new Vector2(activeObject.getPosition().x, Double.parseDouble(text)));
        }

        positionYField.setText(String.valueOf(activeObject.getPosition().y));
    }

    private void verifyScaleX() {

        String text = "0" + scaleXField.getText();
        if (text.matches("[0-9]+([.][0-9]+){0,1}")) {
            activeObject.setScale(new Vector2(Double.parseDouble(text), activeObject.getScale().y));
        }

        scaleXField.setText(String.valueOf(activeObject.getScale().x));
    }

    private void verifyScaleY() {

        String text = "0" + scaleYField.getText();
        if (text.matches("[0-9]+([.][0-9]+){0,1}")) {
            activeObject.setScale(new Vector2(activeObject.getScale().x, Double.parseDouble(text)));
        }

        scaleYField.setText(String.valueOf(activeObject.getScale().y));
    }

    private void verifyImage() {

        try {
            activeObject.setImage(imageField.getText());
        } catch (Exception e) {
            return;
        }

        loadObject();
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

        File file = new File(System.getProperty("user.dir") + "\\src\\" + guiWindow.getScriptField().getText());

        if (file.exists()) {
            activeObject.setScript(guiWindow.getScriptField().getText());
            return;
        }

        guiWindow.getScriptField().setText(activeObject.getScript());
    }

    private void verifyObjectName() {

        if (activeObject.getName().equals(objectNameField.getText())) {
            return;
        }

        if (activeObject.getName().equals("Camera")) {
            objectNameField.setText("Camera");
            return;
        }

        activeObject.setName(objectNameField.getText());
        reloadObjectList();
    }

    @FXML
    private void loseFocus() {

        previewPanel.requestFocus();
    }

    private void reloadObjectList() {

        Platform.runLater(() -> {
            objectList.getItems().clear();
            objectList.getItems().add(EditorController.getCamera().getName());

            for (int i = 0; i < EditorController.getObjectList().size(); i++) {
                objectList.getItems().add(EditorController.getObjectList().get(i).getName());
            }
        });
    }

    private void loadObject() {

        if (activeObject == null) {
            activeObject = EditorController.getCamera();
        }

        if (activeObject.getName().equals("Camera")) {

            Platform.runLater(() -> {
                guiWindow.getImageField().setText("");
                guiWindow.getzOrderField().setText(String.valueOf(activeObject.getzOrder()));
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
            guiWindow.getObjectNameField().setText(activeObject.getName());
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

            activeObject = EditorController.getObject(objectName);
            loadObject();
        });
    }

    @FXML
    private void addObject() {

        new GameObject();
        reloadObjectList();
    }

    @FXML
    private void removeObject() {

        Platform.runLater(() -> {
            String objectName = (String) guiWindow.getObjectList().getSelectionModel().getSelectedItem();

            if (objectName == null || activeObject.getName().equals("Camera)")) {
                return;
            }

            activeObject = EditorController.getObject(objectName);
            EditorController.removeObject(activeObject);
            activeObject = null;
            reloadObjectList();
            loadObject();
        });
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

    @FXML
    private void changeLanguage() {

        if (activeLanguage.equals("Slovak")) {

            pickButton.setText("Pick");
            createButton.setText("Create");
            runButton.setText("Run");
            clearButton.setText("Clear");
            fileMenu.setText("File");
            editMenu.setText("Edit");
            objectLabel.setText("GameObject:");
            positionLabel.setText("Position:");
            scaleLabel.setText("Scale:");
            imageLabel.setText("Image:");
            zOrderLabel.setText("z Order:");
            scriptLabel.setText("Script:");

            activeLanguage = "English";
        }
        else {

            pickButton.setText("Vyber");
            createButton.setText("Vytvor");
            runButton.setText("Spusti");
            clearButton.setText("Vycisti");
            fileMenu.setText("Subor");
            editMenu.setText("Uprav");
            objectLabel.setText("HernyObjekt:");
            positionLabel.setText("Pozicia:");
            scaleLabel.setText("Mierka:");
            imageLabel.setText("Obraz:");
            zOrderLabel.setText("z Poradie:");
            scriptLabel.setText("Skript:");

            activeLanguage = "Slovak";
        }
    }

    @FXML
    private void newProject() {

        activeObject = new GameObject();
        activeObject.setName("Camera");

        EditorController.setCamera(activeObject);
        EditorController.clearObjectList();

        reloadObjectList();
        loadObject();
    }

    private void save(String path) throws Exception {

        List outList = new ArrayList<>();

        List cameraObj = new ArrayList<>();
        cameraObj.add("");
        cameraObj.add(EditorController.getCamera().getPosition().x);
        cameraObj.add(EditorController.getCamera().getPosition().y);
        cameraObj.add(EditorController.getCamera().getScale().x);
        cameraObj.add(EditorController.getCamera().getScale().y);
        cameraObj.add(EditorController.getCamera().getImagePath());
        cameraObj.add(EditorController.getCamera().getzOrder());

        outList.add(cameraObj);

        for (GameObject gameObject: EditorController.getObjectList()) {

            List object = new ArrayList<>();

            object.add(gameObject.getScript());
            object.add(gameObject.getPosition().x);
            object.add(gameObject.getPosition().y);
            object.add(gameObject.getScale().x);
            object.add(gameObject.getScale().y);
            object.add(gameObject.getImagePath());
            object.add(gameObject.getzOrder());
            object.add(gameObject.getName());

            outList.add(object);
        }

        FileWriter fileWriter = new FileWriter(path + "\\objectList.json");
        fileWriter.write(new Gson().toJson(outList));
        fileWriter.close();
    }

    @FXML
    private void saveToFile() {

        try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Save project at");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = directoryChooser.showDialog(createButton.getScene().getWindow());

            if (file == null) {
                return;
            }

            save(file.getPath());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Project saved.");
            alert.showAndWait();
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("FAILURE");
            alert.setHeaderText(null);
            alert.setContentText("There was an error while saving.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void loadFromFile() {

        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load project");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

            File file = fileChooser.showOpenDialog(createButton.getScene().getWindow());

            if (file == null) {
                return;
            }

            FileReader fileReader = new FileReader(file);

            List objectList = new Gson().fromJson(fileReader, List.class);

            activeObject = new GameObject();
            activeObject.setName("Camera");
            activeObject.setPosition(new Vector2((double) ((ArrayList) objectList.get(0)).get(1),
                    (double) ((ArrayList) objectList.get(0)).get(1)));
            activeObject.setPosition(new Vector2((double) ((ArrayList) objectList.get(0)).get(3),
                    (double) ((ArrayList) objectList.get(0)).get(4)));
            EditorController.setCamera(activeObject);
            EditorController.clearObjectList();

            for (int i = 1; i < objectList.size(); i++) {

                GameObject gameObject = new GameObject();

                gameObject.setScript((String) ((ArrayList) objectList.get(i)).get(0));
                gameObject.setPosition(new Vector2((double) ((ArrayList) objectList.get(i)).get(1),
                        (double) ((ArrayList) objectList.get(i)).get(2)));
                gameObject.setScale(new Vector2((double) ((ArrayList) objectList.get(i)).get(3),
                        (double) ((ArrayList) objectList.get(i)).get(4)));

                if (((ArrayList) objectList.get(i)).get(5) != null) {
                    gameObject.setImage((String) ((ArrayList) objectList.get(i)).get(5));
                }

                gameObject.setzOrder((int) ((double) ((ArrayList) objectList.get(i)).get(6)));
                gameObject.setName((String) ((ArrayList) objectList.get(i)).get(7));
            }

            reloadObjectList();
            loadObject();
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("FAILURE");
            alert.setHeaderText(null);
            alert.setContentText("There was an error while loading.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void pickPicture() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        File file = fileChooser.showOpenDialog(createButton.getScene().getWindow());

        if (file == null) {
            return;
        }

        activeObject.setImage(file.getPath());
        loadObject();
    }

    @FXML
    private void createScript() {

        try {
            FileWriter newScriptWriter = new FileWriter("src\\Game\\NewScript.java");
            newScriptWriter.write("package Game;" +
                    "\n\n" +
                    "import gameCore.*;" +
                    "\n\n" +
                    "public class NewScript extends GameObject {" +
                    "\n\n" +
                    "}");
            newScriptWriter.close();

            activeObject.setScript("Game\\NewScript.java");
            scriptField.setText("Game\\NewScript.java");
            verifyScript();

        } catch (IOException e) {
        }
    }

    @FXML
    private void compile() {

        try {
            save("src");
        } catch (Exception e) {
            return;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        OutputStream out = new OutputStream() {

            private StringBuilder sb = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                this.sb.append((char) b);
            }

            @Override
            public String toString() {
                return this.sb.toString();
            }
        };

        int compiled = 0;

        for (GameObject gameObject: EditorController.getObjectList()) {

            if (!gameObject.getScript().isEmpty()) {
                compiled += compiler.run(null, null, out, System.getProperty("user.dir") + "\\src\\" + gameObject.getScript());
            }
        }

        String outError = "";
        if (!out.toString().isEmpty()) {
            outError = "\n" + out.toString();
        }

        consoleArea.setText(consoleArea.getText() + "\nCompilation: " + String.valueOf(compiled) + outError + "\n");

        if (compiled != 0) {
            return;
        }

        try {
            new Thread(new GameThread()).start();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("Logger");
            logger.log(Level.SEVERE, e.toString());
            consoleArea.setText(consoleArea.getText() + "\n" + e.toString());
        }
    }

    @FXML
    private void clearConsole() {

        consoleArea.setText("");
    }

    public static void consoleAdd(String text) {

        guiWindow.getConsoleArea().setText(guiWindow.getConsoleArea().getText() + text);
    }

    public SwingNode getPreviewPanel() {
        return previewPanel;
    }

    public ListView getObjectList() {
        return objectList;
    }

    public TextField getObjectNameField() {
        return objectNameField;
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
