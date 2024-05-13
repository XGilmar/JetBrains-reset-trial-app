package com.dev.reset.trial.app.models;

import com.dev.reset.trial.app.view.FormMain;
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
        makeScriptExecutableWindows(scriptFile);
        executeScriptWindows(scriptFile);
        deleteScriptFileWindows(scriptFile);
    }

    private String generateSingleBashCommandWindows() {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("@echo off\n");
        commandBuilder.append("echo [ INFO ] Removing JavaSoft key user...\n");
        commandBuilder.append("reg delete \"HKEY_CURRENT_USER\\Software\\JavaSoft\" /f\n");
        commandBuilder.append("if %ERRORLEVEL% equ 0 (\n");
        commandBuilder.append("echo [ OK ] Key removed successfully.\n");
        commandBuilder.append(") else (\n");
        commandBuilder.append("echo [ ! ] key has been removed.\n");
        commandBuilder.append(")\n");

        commandBuilder.append("echo [ INFO ] Removing PermanentUserId...\n");
        commandBuilder.append("del /F /Q \"%APPDATA%\\JetBrains\\PermanentUserId\"\n");
        commandBuilder.append("if %ERRORLEVEL% equ 0 (\n");
        commandBuilder.append("echo [ OK ] PermanentUserId remove successfully.\n");
        commandBuilder.append(") else (\n");
        commandBuilder.append("echo [ ! ] PermanentUserId has been removed.\n");
        commandBuilder.append(")");
        return commandBuilder.toString();
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

    private void makeScriptExecutableWindows(File scriptFile) {
        scriptFile.setExecutable(true);
    }

    private void executeScriptWindows(File scriptFile) throws IOException, InterruptedException {
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
            FormMain.showText(builder.toString());
        }
    }

    private void deleteScriptFileWindows(File scriptFile) {
        if (scriptFile.delete()) {
            System.out.println("El script se eliminó correctamente.");
        } else {
            System.out.println("No se pudo eliminar el script.");
        }
    }

}
