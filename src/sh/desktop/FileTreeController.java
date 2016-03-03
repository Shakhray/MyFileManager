package sh.desktop;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sh.exceptions.*;

/**
 * @author Sherhan
 */
public class FileTreeController {
    @FXML
    protected TableView<FileTableItem> fileTree;
    @FXML
    protected TableColumn<FileTableItem, String> fileNames;
    @FXML
    protected TableColumn<FileTableItem, VBox> fileIcon;
    @FXML
    protected ToolBar buttonPane;
    GUICommander commander = new GUICommander();
    private Stage stage;
    private Message message = new Message(stage);

    @FXML
    protected void initialize() {
        fillTable();
        addOnMouseDoubleClickListenerToTable();
        addChangeDiskButtons();
    }

    private void fillTable() {
        fileTree.setItems(commander.getDir());
        fileNames.setCellValueFactory(cellData -> cellData.getValue().getName());
        fileIcon.setCellValueFactory(this::icon);
    }

    private SimpleObjectProperty icon(TableColumn.CellDataFeatures<FileTableItem, VBox> cellData) {
        WritableImage image = cellData.getValue().getImage();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        vBox.getChildren().add(imageView);
        return new SimpleObjectProperty<>(vBox);
    }

    private void addOnMouseDoubleClickListenerToTable() {
        fileTree.setRowFactory(tv -> {
            TableRow<FileTableItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleMouseClickedToRow(event, row));
            return row;
        });
    }

    private void handleMouseClickedToRow(MouseEvent event, TableRow<FileTableItem> row) {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
            String itemName = row.getItem().getName().getValue();
            try {
                changeFileTree(itemName);
            } catch (FileNotFoundException | IsNotDirException e) {
                tryToStartApplication(itemName);
            }
        }
    }

    private void tryToStartApplication(String itemName) {
        if (commander.isApplication(itemName)) {
            commander.startApplication(itemName);
        } else {
            try {
                commander.edit(itemName);
            } catch (FileNotOpenException e) {
                showMessage("Невозможно открыть файл", e);
            } catch (FileNotFoundException e) {
                showMessage("Выбранный файл не найден", e);
            } catch (EmptyArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void addChangeDiskButtons() {
        for (String diskName : commander.getDisks()) {
            Button button = new Button();
            button.setText(diskName);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                String path = button.getText();
                try {
                    changeFileTree(path);
                } catch (FileNotFoundException e) {
                    showMessage("Выбранный файл не найден", e);
                } catch (IsNotDirException e) {
                    e.printStackTrace();
                }
            });
            buttonPane.getItems().add(button);
        }
    }

    private boolean changeFileTree(String path) throws FileNotFoundException, IsNotDirException {
        boolean success = false;
        try {
            commander.cd(path);
            fileTree.setItems(commander.getDir());
            success = true;
        } catch (DiskNotFoundException e) {
            showMessage("Диск не найден", e);
        } catch (InvalidedLinkException e) {
            showMessage("Ярлык поврежден", e);
        }
        return success;
    }

    private void showMessage(String messageText, Exception e) {
        message.showMessage(messageText);
        e.printStackTrace();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
