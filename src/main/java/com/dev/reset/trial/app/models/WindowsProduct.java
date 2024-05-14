package com.dev.reset.trial.app.models;

import com.dev.reset.trial.app.view.AppView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowsProduct {

    private static final String SCRIPT_FILENAME = "windows.bat";

    public void resetProductWindows() {
        try {
            createAndRunScriptWindows();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(WindowsProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createAndRunScriptWindows() throws IOException, InterruptedException {
        String bashCommands = generateSingleBashCommandWindows();
        File scriptFile = createScriptFileWindows(bashCommands);
        scriptFile.setExecutable(true);
        executeScriptWindows();
        deleteScriptFileWindows(scriptFile);
    }

    private String generateSingleBashCommandWindows() {

        return """
                @echo off
                echo [ INFO ] Removing JavaSoft key user...
                reg delete "HKEY_CURRENT_USER\\Software\\JavaSoft" /f
                if %ERRORLEVEL% equ 0 (
                echo [ OK ] Key removed successfully.
                ) else (
                echo [ ! ] key has been removed.
                )
                echo [ INFO ] Removing PermanentUserId...
                del /F /Q "%APPDATA%\\JetBrains\\PermanentUserId"
                if %ERRORLEVEL% equ 0 (
                echo [ OK ] PermanentUserId remove successfully.
                ) else (
                echo [ ! ] PermanentUserId has been removed.
                )""";
    }

    private File createScriptFileWindows(String bashCommands) throws IOException {
        File scriptFile = new File(SCRIPT_FILENAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptFile))) {
            if (bashCommands != null) {
                writer.write(bashCommands);
            }

        }
        return scriptFile;
    }



    private void executeScriptWindows() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                ".\\" + SCRIPT_FILENAME);
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        printProcessOutputWindows(process);
        process.waitFor();
    }

    private void printProcessOutputWindows(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append("<span style=\"color: #0066FF;\">")
                        .append(line)
                        .append("</span>")
                        .append("<br>");
            }
            AppView.showText(builder.toString());
        }
    }

    private void deleteScriptFileWindows(File scriptFile) {
        if (scriptFile.delete()) {
            System.out.println("Remove script successfully.");
        } else {
            System.out.println("Failed remove script.");
        }
    }

}
