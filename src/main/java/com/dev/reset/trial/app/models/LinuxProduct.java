package com.dev.reset.trial.app.models;

import com.dev.reset.trial.app.view.FormMain;
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
        makeScriptExecutableLinux(scriptFile);
        executeScriptLinux(scriptFile);
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
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("#!/bin/bash\n");
        commandBuilder.append("config_path=").append(CONFIG_PATH).append("\n");
        String resultPath = CONFIG_PATH + product;
        commandBuilder.append("result_path=").append(resultPath).append("\n");
        commandBuilder.append("product_name=$(basename $result_path*)\n");
        commandBuilder.append("name_path=$config_path$product_name\n");

        commandBuilder.append("if [ -d $name_path ]; then\n");
        commandBuilder.append("echo \"[ INFO ] Resetting trial period for [$product_name]\"\n");

        commandBuilder.append("echo \"[ INFO ] Removing Evaluation Key...\"\n");
        commandBuilder.append("rm -rf $name_path/eval &>/dev/null\n");
        commandBuilder.append("if [ $? -eq 0 ]; then\n");
        commandBuilder.append("echo \"[ OK ] Evaluation Key removed successfully.\"\n");
        commandBuilder.append("else\n");
        commandBuilder.append("echo \"[ ERROR ] Failed to remove Evaluation Key.\"\n");
        commandBuilder.append("fi\n");

        commandBuilder.append("echo \"[ INFO ] Removing all evlsprt properties in options.xml...\"\n");
        commandBuilder.append("sed -i 's/evlsprt//' $name_path/options/other.xml &>/dev/null\n");
        commandBuilder.append("if [ $? -eq 0 ]; then\n");
        commandBuilder.append("echo \"[ OK ] evlsprt properties removed successfully.\"\n");
        commandBuilder.append("else\n");
        commandBuilder.append("echo \"[ ERROR ] Failed to remove evlsprt properties.\"\n");
        commandBuilder.append("fi\n");

        commandBuilder.append("echo \"[ INFO ] Removing userPrefs files...\"\n");
        commandBuilder.append("rm -rf ~/.java/.userPrefs &>/dev/null\n");
        commandBuilder.append("if [ $? -eq 0 ]; then\n");
        commandBuilder.append("echo \"[ OK ] userPrefs files removed successfully.\"\n");
        commandBuilder.append("else\n");
        commandBuilder.append("echo \"[ ERROR ] Failed to remove userPrefs files.\"\n");
        commandBuilder.append("fi\n");

        commandBuilder.append("else\n");
        commandBuilder.append("echo \"[ WARN ] Directory for $product_name does not exist.\"\n");
        commandBuilder.append("fi");
        return commandBuilder.toString();
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

    private void makeScriptExecutableLinux(File scriptFile) {
        scriptFile.setExecutable(true);
    }

    private void executeScriptLinux(File scriptFile) throws IOException, InterruptedException {
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
            FormMain.showText(builder.toString());
        }
    }

    public void deleteScriptFileLinux(File scriptFile) {
        if (scriptFile.delete()) {
            System.out.println("El script se eliminó correctamente.");
        } else {
            System.out.println("No se pudo eliminar el script.");
        }
    }

}
