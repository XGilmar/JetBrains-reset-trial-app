package com.dev.reset.trial.app;

import com.dev.reset.trial.app.view.AppView;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Font;
import javax.swing.UIManager;

public class ResetTrialApp {

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.BOLD, 13));
        FlatMacDarkLaf.setup();
        java.awt.EventQueue.invokeLater(() -> new AppView().setVisible(true));
    }
}
