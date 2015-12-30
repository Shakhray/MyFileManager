package sh.controllers;

import sh.exceptions.DiskNotFoundException;
import sh.exceptions.FileNotFoundException;
import sh.exceptions.IsNotDirException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Sherhan
 */
public class DirController {
    public List<String> dir(File directory) {
        List<String> listFiles = new LinkedList<>();
        for (File file : directory.listFiles()) {
            listFiles.add(file.getName());
        }
        return listFiles;
    }

    public File cd(File homeDir, String goal) throws FileNotFoundException, IsNotDirException, DiskNotFoundException {
        if (isDiskDriver(goal)) {
            File disk = new File(goal);
            if (disk.exists()) {
                return disk;
            } else {
                throw new DiskNotFoundException();
            }
        } else if ("..".equals(goal)) {
            return homeDir.getParentFile() == null ? homeDir : homeDir.getParentFile();
        }
        for (File dir : homeDir.listFiles()) {
            if (goal.equals(dir.getName())) {
                if (dir.isFile()) {
                    throw new IsNotDirException();
                } else {
                    return dir;
                }
            }
        }
        throw new FileNotFoundException();
    }

    private boolean isDiskDriver(String arguments) {
        return Pattern.matches("^[A-Z]:(\\\\|/)$", arguments);
    }

    public void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
