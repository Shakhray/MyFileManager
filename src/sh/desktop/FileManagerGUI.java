package sh.desktop;/**
 * @author Sherhan
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FileManagerGUI extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Файл менеджер Шахрая");
        showRootLayOut();
    }

    private void showRootLayOut() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FileManagerGUI.class.getResource("rootLayout.fxml"));
        try {
            BorderPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RootLayoutController controller = loader.getController();
            controller.setStage(primaryStage);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
