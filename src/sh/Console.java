package sh;

import sh.controllers.AppController;
import sh.controllers.DirController;
import sh.exceptions.BadCommandException;
import sh.exceptions.IsNotDirException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static sh.Commands.*;

/**
 * @author Шерхан
 */
public class Console {
    public static final String ROOT = "C:/";
    private Scanner scanner = new Scanner(System.in);
    private DirController dirController = new DirController();
    private AppController appController = new AppController();
    private Viewer viewer = new Viewer();
    private File home = getHomeDirectory();

    public void run() {
        while (true) {
            printCommandLine(home);
            String input = scanner.nextLine();
            String command = InputUtils.getCommand(input);
            String arguments = InputUtils.getArgs(input);
            switch (command) {
                case EXIT:
                    exit();
                case DIR:
                    dir();
                    break;
                case CD:
                    cd(arguments);
                    break;
                case EDIT:
                    edit(arguments);
                    break;
                default:
                    try {
                        startApplication(command);
                    } catch (BadCommandException e) {
                        viewer.println("bad command");
                    }
            }
        }
    }

    private boolean isApplication(String command) {
        return command.length() > 4 && ".exe".equals(command.substring(command.length() - 4, command.length()));
    }

    private void startApplication(String command) {
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
        if (hasEndSlash(path)) {
            path = path.substring(0, path.length() - 2);
        }
        return path;
    }

    private boolean hasEndSlash(String path) {
        return path.lastIndexOf("/") == path.length() - 1 || path.lastIndexOf("\\") == path.length() - 1;
    }

    private void edit(String arguments) {
        if (arguments.isEmpty()) {
            viewer.println("Please, enter file name.\nFor example: edit example.txt");
        } else {
            File openFile = openFile(arguments);
            if (openFile.isFile()) {
                try {
                    appController.startNodePad(openFile.getAbsolutePath());
                } catch (IOException | InterruptedException e) {
                    viewer.println("Can not open file");
                }
            } else {
                viewer.println("Can not found this file");
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

    private void exit() {
        System.exit(0);
    }

    private void dir() {
        viewer.print(dirController.dir(home));
    }

    private void cd(String arguments) {
        try {
            home = dirController.cd(home, arguments);
        } catch (FileNotFoundException e) {
            viewer.println("Dir not found");
        } catch (IsNotDirException e) {
            viewer.println("Is not a dir");
        }
    }

    private File getHomeDirectory() {
        return new File(ROOT);
    }

    private void printCommandLine(File home) {
        viewer.print(home.getAbsolutePath() + "> ");
    }
}
