package sh.desktop;

/**
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
    public static int INDEX = 0;
    private static final String LEFT_TREE = "leftFileTree.fxml";
    private static final String RIGHT_TREE = "rightFileTree.fxml";
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
        rootLayoutController.setLeftFileTreeController(getFileTreeController(LEFT_TREE));
        rootLayoutController.getLeftPane().getChildren().add(loadFileTree(LEFT_TREE));
    }

    private void showRightFileTree() {
        rootLayoutController.setRightFileTreeController(getFileTreeController(RIGHT_TREE));
        rootLayoutController.getRightPane().getChildren().add(loadFileTree(RIGHT_TREE));
    }

    private FileTreeController getFileTreeController(String xmlName) {
        FXMLLoader loader = getLoader(xmlName);
        FileTreeController controller = null;
        try {
            loader.load();
            controller = loader.getController();
            controller.setStage(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    private AnchorPane loadFileTree(String xmlName) {
        FXMLLoader loader = getLoader(xmlName);
        AnchorPane fileTree = null;
        try {
            fileTree = loader.load();
            AnchorPane.setBottomAnchor(fileTree, 0.0);
            AnchorPane.setTopAnchor(fileTree, 0.0);
            AnchorPane.setLeftAnchor(fileTree, 0.0);
            AnchorPane.setRightAnchor(fileTree, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileTree;
    }

    private FXMLLoader getLoader(String xmlName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FileManagerGUI.class.getResource(xmlName));
        return loader;
    }
}
