package sh.desktop;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

/**
 * @author Sherhan
 */
public class LeftFileTreeController extends FileTreeController {
    @FXML
    private TableView<FileTableItem> fileTree;
    @FXML
    private TableColumn<FileTableItem, String> fileNames;
    @FXML
    private TableColumn<FileTableItem, VBox> fileIcon;
    @FXML
    private ToolBar buttonPane;

    @FXML
    protected void initialize() {
        super.initialize();
        this.fileTree = super.fileTree;
        this.fileNames = super.fileNames;
        this.fileIcon = super.fileIcon;
        this.buttonPane = super.buttonPane;
    }

    @Override
    public void createFolder(String folderName) {
        System.out.println("left index : " + index);
        super.createFolder(folderName);
    }
}
