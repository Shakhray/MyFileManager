package sh.desktop;/**
 * @author Sherhan
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FileManagerGUI extends Application {
    private Stage primaryStage;
    private RootLayoutController rootLayoutController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Файл менеджер Шахрая");
        initRootLayOut();
        showLeftFileTree();
        showRightFileTree();
    }

    private void initRootLayOut() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FileManagerGUI.class.getResource("rootLayout.fxml"));
        try {
            BorderPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            rootLayoutController = loader.getController();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLeftFileTree() {
        showFileTree("leftFileTree.fxml");
    }

    private void showRightFileTree() {
        showFileTree("rightFileTree.fxml");
    }

    private void showFileTree(String xmlName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FileManagerGUI.class.getResource(xmlName));
        try {
            AnchorPane fileTree = loader.load();
            FileTreeController controller = loader.getController();
            controller.setStage(primaryStage);
            AnchorPane.setBottomAnchor(fileTree, 0.0);
            AnchorPane.setTopAnchor(fileTree, 0.0);
            AnchorPane.setLeftAnchor(fileTree, 0.0);
            AnchorPane.setRightAnchor(fileTree, 0.0);
            rootLayoutController.getRightPane().getChildren().add(fileTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
