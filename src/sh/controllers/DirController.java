package sh.controllers;

import sh.exceptions.DiskNotFoundException;
import sh.exceptions.FileNotFoundException;
import sh.exceptions.IsNotDirException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Sherhan
 */
public class DirController {
    private final static String DOUBLE_DOT = "..";

    public List<String> dir(File directory) {
        return Arrays.asList(directory.list());
    }

    public File cd(File homeDir, String targetDir)
            throws FileNotFoundException, IsNotDirException, DiskNotFoundException {
        if (isDiskDriver(targetDir)) {
            return changeDisk(targetDir);
        } else if (isParentDir(targetDir)) {
            return parentDir(homeDir);
        } else {
            return childDir(homeDir, targetDir);
        }
    }

    private boolean isDiskDriver(String arguments) {
        return Pattern.matches("^[A-Z]:(\\\\|/)$", arguments);
    }

    private File changeDisk(String diskName) throws DiskNotFoundException {
        File disk = new File(diskName);
        if (disk.exists()) {
            return disk;
        } else {
            throw new DiskNotFoundException();
        }
    }

    private boolean isParentDir(String targetDir) {
        return DOUBLE_DOT.equals(targetDir);
    }

    private File parentDir(File homeDir) {
        return homeDir.getParentFile() == null ? homeDir : homeDir.getParentFile();
    }

    private File childDir(File homeDir, String targetDir) throws IsNotDirException, FileNotFoundException {
        File childDir = findChild(homeDir, targetDir);
        if (childDir == null) {
            throw new FileNotFoundException();
        } else {
            return childDir;
        }
    }

    private File findChild(File homeDir, String targetDir) throws IsNotDirException {
        for (File dir : homeDir.listFiles()) {
            if (targetDir.equals(dir.getName())) {
                if (dir.isDirectory()) {
                    return dir;
                } else {
                    throw new IsNotDirException();
                }
            }
        }
        return null;
    }

    public void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
