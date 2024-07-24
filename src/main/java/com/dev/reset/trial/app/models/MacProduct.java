package com.dev.reset.trial.app.models;

import com.dev.reset.trial.app.view.AppView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MacProduct {
    private static final String SCRIPT_FILENAME = "mac.sh";

    public void resetProductMac(List<String> names) {
        try {
            createAndRunScriptMac(names);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MacProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createAndRunScriptMac(List<String> product) throws IOException, InterruptedException {
        List<String> bashCommands = generateBashCommandsMac(product);
        File scriptFile = createScriptFileMac(bashCommands);
        scriptFile.setExecutable(true);
        executeScriptMac();
        deleteScriptFileMac(scriptFile);
    }

    private List<String> generateBashCommandsMac(List<String> products) {
        List<String> bashCommands = new ArrayList<>();
        for (String product : products) {
            bashCommands.add(generateSingleBashCommandMac(product));
        }
        return bashCommands;
    }

    private String generateSingleBashCommandMac(String product) {
        return """
                 #!/bin/bash
                 product_name=%s
                                
                 prefs_file_one=~/Library/Preferences/com.apple.java.util.prefs.plist
                 prefs_file_two=~/Library/Preferences/com.jetbrains.*.plist
                 prefs_file_three=~/Library/Preferences/jetbrains.*.*.plist
                                
                 prefs_files=($prefs_file_one $prefs_file_two $prefs_file_three)
                                
                 echo "[ INFO ] Resetting period for $product_name"
                                
                    echo "Removing evaluation key..."
                    if rm -rf ~/Library/Preferences/$product_name*/eval; then
                       echo "[ OK ] Key removed successfully."\s
                    else
                       echo "[ ERROR ] Failed to remove key."
                    fi
                    
                    # Above path not working on latest version. Fixed below
                    if rm -rf ~/Library/Application\\ Support/JetBrains/$product_name/eval; then
                       echo "[ OK ] Latest version removed successfully."
                    else
                       echo "[ ERROR ] Failed to remove latest version."
                    fi
                    
                    echo "Removing evlsprt in options.xml..."
                    sed -i '' '/evlsprt/d' ~/Library/Preferences/$product_name*/options/other.xml
                    
                    if [ $? -eq 0 ]; then
                        echo "[ OK ] evlsprt removed successfully."
                    else
                        echo "[ ERROR ] Failed to remove evlsprt."
                    fi
                    
                    # Above path not working on latest version. Fixed below
                    sed -i '' '/evlsprt/d' ~/Library/Application\\ Support/JetBrains/$product_name*/options/other.xml
                    if [ $? -eq 0 ]; then
                        echo "[ OK ] Latest evlsprt removed successfully."
                    else
                        echo "[ ERROR ] Failed to remove latest evlsprt."
                    fi
                              
                                
                  echo "[ INFO ] removing additional plist files..."
                                
                    for file in ${prefs_files[@]}; do
                        echo "[ INFO ] Removing ${file##*/}..."
                          if rm "$file"; then
                             echo "[ OK ] ${file##*/} removed successfully."
                          else
                             echo "[ ERROR ] Failed to remove ${file##*/}."
                          fi
                    done
                                
                """.formatted(product);
    }

    private File createScriptFileMac(List<String> bashCommands) throws IOException {
        File scriptFile = new File(SCRIPT_FILENAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptFile))) {
            for (String command : bashCommands) {
                writer.write(command);
                writer.newLine();
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
