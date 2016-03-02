package sh.desktop;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * @author Sherhan
 */
public class Message {
    private Stage stage;

    public Message() {

    }

    public Message(Stage stage) {
        this.stage = stage;
    }

    public void showMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Предупреждение");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
