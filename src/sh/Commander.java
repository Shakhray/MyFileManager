package sh;

import sh.controllers.AppController;
import sh.controllers.DirController;
import sh.exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Sherhan
 */
public class Commander {
    public static final String ROOT = "C:/";
    private DirController dirController = new DirController();
    private AppController appController = new AppController();
    private File home = initHome();

    private File initHome() {
        return new File(ROOT);
    }

    public File getHomeDirectory() {
        return home;
    }

    public void cd(String arguments) throws FileNotFoundException, IsNotDirException, DiskNotFoundException, InvalidedLinkException {
        home = dirController.cd(home, arguments);
    }

    public List<String> dir() {
        return dirController.dir(home);
    }

    public void exit() {
        System.exit(0);
    }

    public void edit(String arguments) throws FileNotOpenException, FileNotFoundException, EmptyArgumentException {
        if (arguments.isEmpty()) {
            throw new EmptyArgumentException();
        } else {
            File openFile = openFile(arguments);
            if (openFile.isFile()) {
                try {
                    appController.startNodePad(openFile.getAbsolutePath());
                } catch (IOException | InterruptedException e) {
                    throw new FileNotOpenException();
                }
            } else {
                throw new FileNotFoundException();
            }
        }
    }

    private File openFile(String arguments) {
        File openFile = new File(arguments);
        if (!openFile.exists()) {
            openFile = new File(getHomePath() + arguments);
        }
        return openFile;
    }

    public void startApplication(String command) {
        try {
            appController.startProcess(command);
        } catch (IOException | InterruptedException e) {
            try {
                appController.startProcess(command + ".exe");
            } catch (IOException | InterruptedException e1) {
                try {
                    String path = getHomePath() + command;
                    File openFile = new File(path);
                    if (!openFile.exists()) {
                        openFile = new File(path + ".exe");
                    }
                    appController.startProcess(openFile.getAbsolutePath());
                } catch (InterruptedException | IOException e2) {
                    throw new BadCommandException();
                }
            }
        }
    }

    private String getHomePath() {
        String path = home.getAbsolutePath();
        if (!hasEndSlash(path)) {
            path += "/";
        }
        return path;
    }

    private boolean hasEndSlash(String path) {
        return path.lastIndexOf("/") == path.length() - 1 || path.lastIndexOf("\\") == path.length() - 1;
    }

    public boolean isApplication(String command) {
        return command.length() > 4 && ".exe".equals(command.substring(command.length() - 4, command.length()));
    }

    public void copy(String source, String copyTo) throws IOException, DirNotFoundException, FileNotFoundException {
        File sourceFile = getSourceFile(source);
        File copyDir = getTargetFile(source, copyTo);
        dirController.copy(sourceFile, copyDir);
    }

    private File getSourceFile(String source) throws FileNotFoundException {
        File sourceFile = new File(getHomePath() + source);
        if (!sourceFile.exists()) {
            throw new FileNotFoundException();
        } else {
            return sourceFile;
        }
    }

    private File getTargetFile(String fileName, String copyTo) throws DirNotFoundException {
        if (!new File(copyTo).isDirectory()) {
            throw new DirNotFoundException();
        } else {
            return hasEndSlash(copyTo) ? new File(copyTo + fileName) : new File(copyTo + "\\" + fileName);
        }
    }
}
