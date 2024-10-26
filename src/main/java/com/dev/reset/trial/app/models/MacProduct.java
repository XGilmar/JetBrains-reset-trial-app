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
//        for (String product : products) {
        bashCommands.add(generateSingleBashCommandMac());
//        }
        return bashCommands;
    }

    private String generateSingleBashCommandMac() {
        return """
                #!/bin/bash

                prefs_file_one=~/Library/Preferences/com.apple.java.util.prefs.plist
                prefs_file_two=~/Library/Preferences/com.jetbrains.*.plist
                prefs_file_three=~/Library/Preferences/jetbrains.*.*.plist
                            
                prefs_files=($prefs_file_one $prefs_file_two $prefs_file_three)
                  
                for product_name in IntelliJIdea WebStorm DataGrip PhpStorm CLion PyCharm GoLand RubyMine Rider; do
                                          
                    if [ -f "~/Library/Preferences/$product_name*/eval" ]; then      
                        rm -rf ~/Library/Preferences/$product_name*/eval
                        echo "[ OK ] $product_name old path evaluation key removed successfully."
                    fi
                    
                    # Above path not working on latest version. Fixed below
                    if [ -f "~/Library/Application\\ Support/JetBrains/$product_name/eval" ]; then      
                        rm -rf ~/Library/Preferences/$product_name*/eval
                        echo "[ OK ] $product_name old path evaluation key removed successfully."
                    fi

                    if [ -f "~/Library/Preferences/$product_name*/options/other.xml" ]; then
                        echo "Removing evlsprt in options.xml..."
                        sed -i '' '/evlsprt/d' ~/Library/Preferences/$product_name*/options/other.xml
                        
                        if [ $? -eq 0 ]; then
                            echo "[ OK ] $product_name old path evlsprt removed successfully."                   
                        fi
                    fi
                    
                    # Above path not working on latest version. Fixed below
                    if [ -f "~/Library/Application\\ Support/JetBrains/$product_name*/options/other.xml" ]; then
                        sed -i '' '/evlsprt/d' ~/Library/Application\\ Support/JetBrains/$product_name*/options/other.xml
                        if [ $? -eq 0 ]; then
                            echo "[ OK ] $product_name new path evlsprt removed successfully."     
                        fi      
                    fi     
                done                            
                            
                for file in ${prefs_files[@]}; do
                    echo "[ INFO ] Removing ${file##*/}..."
                      if rm "$file"; then
                         echo "[ OK ] ${file##*/} removed successfully."
                      else
                         echo "[ ERROR ] Failed to remove ${file##*/}."
                      fi
                done
                              
                echo "[ INFO ] restarting cfprefsd"
                
                killall cfprefsd                                
                """.formatted();
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
