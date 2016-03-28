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
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sherhan
 */
public class GUICommander extends Commander {
    public ObservableList<FileTableItem> getDir() {
        ObservableList<FileTableItem> directories = FXCollections.observableArrayList();
        if (getHomeDirectory().getParent() != null) {
            directories.add(createRootFileTableItem());
        }
        directories.addAll(super.dir().stream().map(this::createFileTableItem).collect(Collectors.toList()));
        return directories;
    }

    private FileTableItem createFileTableItem(File file) {
        WritableImage icon = getIcon(file);
        return new FileTableItem(file.getName(), icon);
    }

    private FileTableItem createRootFileTableItem() {
        WritableImage icon = getIcon(getHomeDirectory());
        return new FileTableItem("..", icon);
    }

    private WritableImage getIcon(File file) {
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

    public List<File> getDisks() {
        List<File> disks = new LinkedList<>();
        for (char word = 'A'; word <= 'Z'; word++) {
            File disk = new File(word + ":\\");
            if (Files.exists(disk.toPath())) {
                disks.add(disk);
            }
        }
        return disks;
    }

}
