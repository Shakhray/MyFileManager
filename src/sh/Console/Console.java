package sh.console;

import sh.Commander;
import sh.InputUtils;
import sh.exceptions.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

import static sh.console.Commands.*;
import static sh.console.Messages.*;

/**
 * @author Шерхан
 */
public class Console {
    private Scanner scanner = new Scanner(System.in);
    private Commander commander = new Commander();
    private Viewer viewer = new Viewer();

    public void run() {
        while (true) {
            printCommandLine();
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
                case COPY:
                    String args[] = InputUtils.splitArgs(arguments);
                    String source = args[0], copyTo = args[1];
                    copy(source, copyTo);
                    break;
                default:
                    try {
                        commander.startApplication(command);
                    } catch (BadCommandException e) {
                        viewer.println(BAD_COMMAND);
                    }
            }
        }
    }

    private void copy(String source, String copyTo) {
        try {
            commander.copy(source, copyTo);
        } catch (FileAlreadyExistsException e) {
            viewer.println(FILE_ALREADY_EXISTS + " in " + copyTo);
        } catch (IOException e) {
            viewer.println(COPY_ERROR);
        } catch (FileNotFoundException e) {
            viewer.println(NOT_FOUND_FILE);
        } catch (DirNotFoundException e) {
            viewer.println(DIR_NOT_FOUND);
        }
    }

    private void printCommandLine() {
        viewer.print(commander.getHomeDirectory().getAbsolutePath() + "> ");
    }

    private void exit() {
        commander.exit();
    }

    private void dir() {
        viewer.print(commander.dir());
    }

    private void cd(String arguments) {
        try {
            commander.cd(arguments);
        } catch (FileNotFoundException e) {
            viewer.println(DIR_NOT_FOUND);
        } catch (IsNotDirException e) {
            viewer.println(IS_NOT_A_DIR);
        } catch (DiskNotFoundException e) {
            viewer.println(DISK_NOT_FOUND);
        }
    }

    private void edit(String arguments) {
        try {
            commander.edit(arguments);
        } catch (FileNotOpenException e) {
            viewer.println(NOT_OPEN_FILE);
        } catch (FileNotFoundException e) {
            viewer.println(NOT_FOUND_FILE);
        } catch (EmptyArgumentException e) {
            viewer.println(EMPTY_ARGUMENT);
        }
    }
}
