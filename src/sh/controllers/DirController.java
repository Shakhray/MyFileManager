package sh.controllers;

import sh.exceptions.IsNotDirException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Шерхан on 24.12.2015.
 */
public class DirController {
    public List<String> dir(File directory) {
        List<String> listFiles = new LinkedList<>();
        for (File file : directory.listFiles()) {
            listFiles.add(file.getName());
        }
        return listFiles;
    }

    public File cd(File homeDir, String goal) throws FileNotFoundException, IsNotDirException {
        if ("..".equals(goal)) {
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
}
