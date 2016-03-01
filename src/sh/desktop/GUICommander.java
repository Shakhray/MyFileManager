package sh.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import sh.Commander;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collectors;

/**
 * @author Sherhan
 */
public class GUICommander extends Commander {
    public ObservableList<FileTableItem> getDir() {
        ObservableList<FileTableItem> directories = FXCollections.observableArrayList();
        directories.addAll(super.dir().stream().map(this::createFileTableItem).collect(Collectors.toList()));
        if (getHomeDirectory().getParent() != null) {
            directories.add(0, createFileTableItem(".."));
        }
        return directories;
    }

    private FileTableItem createFileTableItem(String fileName) {
        String path;
        if (getHomeDirectory().getPath().charAt(getHomeDirectory().getPath().length() - 1) == '\\') {
            path = getHomeDirectory().getPath() + fileName;
        } else {
            path = getHomeDirectory().getPath() + "\\" + fileName;
        }
        WritableImage icon = getIcon(path);
        return new FileTableItem(fileName, icon);
    }

    private WritableImage getIcon(String fileName) {
        File file = new File(fileName);
        ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
        if (icon != null) {
            java.awt.Image awtImage = icon.getImage();
            BufferedImage bImg;
            if (awtImage instanceof BufferedImage) {
                bImg = (BufferedImage) awtImage;
            } else {
                bImg = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = bImg.createGraphics();
                graphics.drawImage(awtImage, 0, 0, null);
                graphics.dispose();
            }
            return SwingFXUtils.toFXImage(bImg, null);
        } else {
            return null;
        }
    }
}
