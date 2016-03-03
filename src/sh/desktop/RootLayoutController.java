package sh.desktop;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * @author Sherhan
 */
public class RootLayoutController {
    GUICommander commander = new GUICommander();
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;

    public AnchorPane getRightPane() {
        return rightPane;
    }

    public AnchorPane getLeftPane() {
        return leftPane;
    }

    @FXML
    private void handleExit() {
        commander.exit();
    }

}
