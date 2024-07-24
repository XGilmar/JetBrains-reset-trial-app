package com.dev.reset.trial.app.controllers;

import com.dev.reset.trial.app.models.LinuxProduct;
import com.dev.reset.trial.app.models.MacProduct;
import com.dev.reset.trial.app.models.WindowsProduct;
import com.dev.reset.trial.app.view.AppView;
import com.dev.reset.trial.app.view.components.items.ItemComponent;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppController extends MouseAdapter implements ActionListener {

    private final AppView appView;
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
            "WebStorm",
            "Datalore",
            "ReSharperC"
    };

    public AppController(AppView appView) {
        this.appView = appView;
    }

    public void initialize() {
        listItem();
        btnAction();
        validateBoxCheck();
    }

    private void listItem() {
        appView.paneProducts.removeAll();
        for (int i = 0; i < itemsName.length; i++) {
            ItemComponent items = createItem(itemsName[i], i);
            appView.paneProducts.add(items);
        }
        appView.paneProducts.revalidate();
        appView.paneProducts.repaint();
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
        this.appView.btnStart.addActionListener(this);
        this.appView.labelGithub.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (appView.btnStart == e.getSource()) {
            validateOs(getSelectBoxName());
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (appView.labelGithub == e.getSource()) {
            setAddress("https://github.com/XGilmar/JetBrains-reset-trial-app/issues");
        }
    }

    private void validateBoxCheck() {
        String systemOs = System.getProperty("os.name");
        String systemOsArch = System.getProperty("os.arch");
        StringBuilder newTextToShow = showNameTextPane();
        if (newTextToShow.isEmpty()) {
            AppView.showText("<span style=\"color: #C34CFF;\">[ " + systemOs.toUpperCase() + " " + systemOsArch.toUpperCase() + " ]</span>");
        } else {
            AppView.showText(newTextToShow.toString());
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

    public void setAddress(String address) {
        try {
            URL url = new URL(address);
            URI uri = url.toURI();
            Desktop.getDesktop().browse(uri);
        } catch (IOException ignored) {
        } catch (URISyntaxException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void validateOs(List<String> name) {

        if (osName.contains("win")) {
            new WindowsProduct().resetProductWindows();
        } else if (osName.contains("mac")) {
            new MacProduct().resetProductMac(name);
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            new LinuxProduct().resetProductLinux(name);
        } else {
            AppView.showText("operating system not recognized");
        }

    }
}
