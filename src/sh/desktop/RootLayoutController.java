package sh.desktop;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * @author Sherhan
 */
public class RootLayoutController {
    public static int index = 0;
   // private GUICommander commander = new GUICommander();
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;
    private FileTreeController leftFileTreeController;
    private FileTreeController rightFileTreeController;
    private boolean isLeftPaineWork = true;

    public RootLayoutController() {
        System.out.println("root controler index : " + index);
        index++;
    }

    public AnchorPane getRightPane() {
        return rightPane;
    }

    public AnchorPane getLeftPane() {
        return leftPane;
    }

    @FXML
    private void handleExit() {
        //commander.exit();
    }

    @FXML
    private void handleCreateFolder() {
        //System.out.println("index : " + index);
        getFocusedController().createFolder("ololo");
    }

    private FileTreeController getFocusedController() {
        System.out.println("leftPane.isFocused() : " + leftPane.isFocused());
        System.out.println("rightPane.isFocused() : " + rightPane.isFocused());
        return leftPane.isFocused() ? leftFileTreeController : rightFileTreeController;
    }

    public FileTreeController getLeftFileTreeController() {
        return leftFileTreeController;
    }

    public void setLeftFileTreeController(FileTreeController leftFileTreeController) {
        this.leftFileTreeController = leftFileTreeController;
    }

    public FileTreeController getRightFileTreeController() {
        return rightFileTreeController;
    }

    public void setRightFileTreeController(FileTreeController rightFileTreeController) {
        this.rightFileTreeController = rightFileTreeController;
    }

    protected boolean isLeftPaineWork() {
        return isLeftPaineWork;
    }

    protected void setLeftPaineWork(boolean isLeftPaineWork) {
        this.isLeftPaineWork = isLeftPaineWork;
    }
}
