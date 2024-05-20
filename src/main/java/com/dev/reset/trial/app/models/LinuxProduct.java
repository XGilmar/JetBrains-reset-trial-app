package com.dev.reset.trial.app.models;

import com.dev.reset.trial.app.view.AppView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinuxProduct {

    private static final String CONFIG_PATH = System.getProperty("user.home") + "/.config/JetBrains/";
    private static final String SCRIPT_FILENAME = "linux.sh";

    public void resetProductLinux(List<String> names) {
        try {
            createAndRunScriptLinux(names);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(LinuxProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createAndRunScriptLinux(List<String> product) throws IOException, InterruptedException {
        List<String> bashCommands = generateBashCommandsLinux(product);
        File scriptFile = createScriptFileLinux(bashCommands);
        scriptFile.setExecutable(true);
        executeScriptLinux();
        deleteScriptFileLinux(scriptFile);
    }

    private List<String> generateBashCommandsLinux(List<String> products) {
        List<String> bashCommands = new ArrayList<>();
        for (String product : products) {
            bashCommands.add(generateSingleBashCommandLinux(product));
        }
        return bashCommands;
    }

    private String generateSingleBashCommandLinux(String product) {
        String resultPath = CONFIG_PATH + product;

        return """
           #!/bin/bash
           config_path=%s
           result_path=%s
           product_name=$(basename $result_path*)
           name_path=$config_path$product_name
           if [ -d $name_path ]; then
               echo "[ INFO ] Resetting trial period for [$product_name]"
               echo "[ INFO ] Removing Evaluation Key..."
               rm -rf $name_path/eval &>/dev/null
               if [ $? -eq 0 ]; then
                   echo "[ OK ] Evaluation Key removed successfully."
               else
                   echo "[ ERROR ] Failed to remove Evaluation Key."
               fi
               echo "[ INFO ] Removing all evlsprt properties in options.xml..."
               sed -i 's/evlsprt//' $name_path/options/other.xml &>/dev/null
               if [ $? -eq 0 ]; then
                   echo "[ OK ] evlsprt properties removed successfully."
               else
                   echo "[ ERROR ] Failed to remove evlsprt properties."
               fi
               echo "[ INFO ] Removing userPrefs files..."
               rm -rf ~/.java/.userPrefs &>/dev/null
               if [ $? -eq 0 ]; then
                   echo "[ OK ] userPrefs files removed successfully."
               else
                   echo "[ ERROR ] Failed to remove userPrefs files."
               fi
           else
               echo "[ WARN ] Directory for $product_name does not exist."
           fi
           """.formatted(CONFIG_PATH, resultPath);
    }

    private File createScriptFileLinux(List<String> bashCommands) throws IOException {
        File scriptFile = new File(SCRIPT_FILENAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptFile))) {
            for (String command : bashCommands) {
                writer.write(command);
                writer.newLine();
            }
        }
        return scriptFile;
    }



    private void executeScriptLinux() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("./" + SCRIPT_FILENAME);
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        printProcessOutputLinux(process);
        process.waitFor();
    }

    private void printProcessOutputLinux(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append("<span style=\"color: #C34CFF;\">")
                        .append(line)
                        .append("</span>")
                        .append("<br>");
            }
            AppView.showText(builder.toString());
        }
    }

    public void deleteScriptFileLinux(File scriptFile) {
        if (scriptFile.delete()) {
            System.out.println("Remove script successfully.");
        } else {
            System.out.println("Failed remove script.");
        }
    }

}
