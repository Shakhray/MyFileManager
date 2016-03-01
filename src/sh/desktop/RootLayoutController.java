package sh.desktop;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import sh.exceptions.*;

/**
 * @author Sherhan
 */
public class RootLayoutController {
    GUICommander commander = new GUICommander();
    @FXML
    private TableView<FileTableItem> fileArea;
    @FXML
    private TableColumn<FileTableItem, String> fileNames;
    @FXML
    private TableColumn<FileTableItem, VBox> fileIcon;

    @FXML
    private void handleExit() {
        commander.exit();
    }

    @FXML
    private void initialize() {
        fileArea.setItems(commander.getDir());
        fileNames.setCellValueFactory(cellData -> cellData.getValue().getName());
        fileIcon.setCellValueFactory(cellData -> {
            WritableImage image = cellData.getValue().getImage();
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            vBox.getChildren().add(imageView);
            return new SimpleObjectProperty<>(vBox);
        });

        fileArea.setRowFactory(tv -> {
            TableRow<FileTableItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    String itemName = row.getItem().getName().getValue();
                    try {
                        commander.cd(itemName);
                        fileArea.setItems(commander.getDir());
                    } catch (IsNotDirException e) {
                        if (commander.isApplication(itemName)) {
                            commander.startApplication(itemName);
                        } else {
                            try {
                                commander.edit(itemName);
                            } catch (FileNotOpenException e1) {
                                e1.printStackTrace();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (EmptyArgumentException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } catch (DiskNotFoundException | FileNotFoundException | InvalidedLinkException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

}
