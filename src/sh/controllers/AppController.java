package sh.controllers;

import sh.InputUtils;

import java.io.IOException;

/**
 * @author Шерхан
 */
public class AppController {
    public void startNodePad(String filePath) throws IOException, InterruptedException {
        startProcess("notepad.exe", filePath);
    }

    public void startProcess(String filePath) throws IOException, InterruptedException {
        startProcess(filePath, InputUtils.EMPTY);
    }

    public void startProcess(String filePath, String openFile) throws IOException, InterruptedException {
        ProcessBuilder procBuilder = openFile.isEmpty()
                ? new ProcessBuilder(filePath)
                : new ProcessBuilder(filePath, openFile);
        procBuilder.redirectErrorStream(true);
        procBuilder.start();
    }

    public void startApplication(String openFile) throws IOException {
        new ProcessBuilder("cmd", "/c", "start", openFile).start();
    }
}
