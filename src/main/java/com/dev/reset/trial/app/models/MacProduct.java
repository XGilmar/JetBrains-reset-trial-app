package com.dev.reset.trial.app.models;

import com.dev.reset.trial.app.view.AppView;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MacProduct {
    private static final String SCRIPT_FILENAME = "mac.sh";

    public void resetProductMac() {
        try {
            createAndRunScriptMac();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MacProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createAndRunScriptMac() throws IOException, InterruptedException {
        String bashCommands = generateSingleBashCommandMac();
        File scriptFile = createScriptFileMac(bashCommands);
        scriptFile.setExecutable(true);
        executeScriptMac();
        deleteScriptFileMac(scriptFile);
    }

    private String generateSingleBashCommandMac() {

        return """
                #!/bin/bash
                prefs_file=~/Library/Preferences/com.apple.java.util.prefs.plist
                echo "[ INFO ] Removing prefs..."
                if rm "$prefs_file"; then
                    echo "[ OK ] Preferences removed successfully."
                else
                    echo "[ ERROR ] Failed to remove preferences."
                fi
               """;
    }

    private File createScriptFileMac(String bashCommands) throws IOException {
        File scriptFile = new File(SCRIPT_FILENAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptFile))) {
            if (bashCommands != null) {
                writer.write(bashCommands);
            }

        }
        return scriptFile;
    }



    private void executeScriptMac() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("./" + SCRIPT_FILENAME);
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        printProcessOutputMac(process);
        process.waitFor();
    }

    private void printProcessOutputMac(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append("<span style=\"color: #de45a6;\">")
                        .append(line)
                        .append("</span>")
                        .append("<br>");
            }
            AppView.showText(builder.toString());
        }
    }

    private void deleteScriptFileMac(File scriptFile) {
        if (scriptFile.delete()) {
            System.out.println("Remove script successfully.");
        } else {
            System.out.println("Failed remove script.");
        }
    }
}
