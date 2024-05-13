package com.dev.reset.trial.app.controllers;

import com.dev.reset.trial.app.models.LinuxProduct;
import com.dev.reset.trial.app.models.WindowsProduct;
import com.dev.reset.trial.app.view.FormMain;
import com.dev.reset.trial.app.view.components.items.ItemComponent;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;

public class AppController implements ActionListener {
    
    private final FormMain formMain;
    private final List<JCheckBox> checkBoxList = new ArrayList<>();
    private final String osName = System.getProperty("os.name").toLowerCase();
    private final String[] itemsName = new String[]{
        "IntelliJIdea",
        "CLion",
        "DataGrip",
        "GoLand",
        "PhpStorm",
        "PyCharm",
        "ReSharper",
        "Rider",
        "RubyMine",
        "WebStorm"
    };
    
    public AppController(FormMain formMain) {
        this.formMain = formMain;
    }
    
    public void initialize() {
        listItem();
        btnAction();
        validateBoxCheck();
    }
    
    private void listItem() {
        formMain.paneProducts.removeAll();
        for (int i = 0; i < itemsName.length; i++) {
            ItemComponent items = createItem(itemsName[i], i);
            formMain.paneProducts.add(items);
        }
        formMain.paneProducts.revalidate();
        formMain.paneProducts.repaint();
    }
    
    private ItemComponent createItem(String name, int index) {
        ItemComponent itemComponent = new ItemComponent();
        itemComponent.labelIcon.setIcon(new FlatSVGIcon("icons/" + index + ".svg", 0.4f));
        itemComponent.labelText.setText(name);
        itemComponent.checkBox.setName(name);
        itemComponent.checkBox.setSelected(true);
        itemComponent.checkBox.setEnabled(false);
        checkBoxList.add(itemComponent.checkBox);
        return itemComponent;
    }
    
    private void btnAction() {
        this.formMain.btnStart.addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (formMain.btnStart == e.getSource()) {
            validateOs(getSelectBoxName());
        }
        
    }
    
    private void validateBoxCheck() {
        String systemOs = System.getProperty("os.name");
        String systemOsArch = System.getProperty("os.arch");
        StringBuilder newTextToShow = showNameTextPane();
        if (newTextToShow.length() == 0) {
            FormMain.showText("<span style=\"color: #C34CFF;\">[ " + systemOs.toUpperCase() + " " + systemOsArch.toUpperCase() + " ]</span>");
        } else {
            FormMain.showText(newTextToShow.toString());
        }
    }
    
    private StringBuilder showNameTextPane() {
        StringBuilder newTextToShow = new StringBuilder();
        for (JCheckBox cb : checkBoxList) {
            if (cb.isSelected()) {
                newTextToShow
                        .append("<span style=\"color: #01A473;\">[ + ] ")
                        .append(cb.getName())
                        .append("</span>")
                        .append("<br>");
            }
        }
        return newTextToShow;
    }
    
    private List<String> getSelectBoxName() {
        List<String> ses = new ArrayList<>();
        for (JCheckBox cb : checkBoxList) {
            if (cb.isSelected()) {
                ses.add(cb.getName());
            }
        }
        return ses;
    }
    
    private void validateOs(List<String> name) {
        
        if (osName.contains("win")) {
            new WindowsProduct().resetProductWindows();
        } else if (osName.contains("mac")) {
            formMain.showText("macOS not available.");
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            new LinuxProduct().resetProductLinux(name);
        } else {
            formMain.showText("operating system not recognized");
        }
        
    }
}
