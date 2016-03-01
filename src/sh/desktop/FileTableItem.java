package sh.desktop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.WritableImage;

/**
 * @author Sherhan
 */
public class FileTableItem {
    private StringProperty name;
    private WritableImage image;

    public FileTableItem(String name, WritableImage image) {
        this.name = new SimpleStringProperty(name);
        this.image = image;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public WritableImage getImage() {
        return image;

    }

    public void setImage(WritableImage image) {
        this.image = image;
    }
}
