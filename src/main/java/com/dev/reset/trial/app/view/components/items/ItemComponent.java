package com.dev.reset.trial.app.view.components.items;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ItemComponent extends JPanel {

    public JLabel labelIcon;
    public JLabel labelText;
    public JCheckBox checkBox;

    public ItemComponent() {
        initComponent();
    }

    private void initComponent() {
        labelIcon = new JLabel();
        labelText = new JLabel();
        checkBox = new JCheckBox();

        MigLayout layout = new MigLayout(null, "[][]push[]");
        setLayout(layout);

        putClientProperty(FlatClientProperties.STYLE, "arc:10;"
                + "background:lighten(@background,3%);");

        setMinimumSize(new Dimension(180, 38));
        setMaximumSize(new Dimension(180, 38));
        setPreferredSize(new Dimension(180, 38));

        labelText.putClientProperty(FlatClientProperties.STYLE, "font:bold +1");
        checkBox.setFocusable(false);

        add(labelIcon, "cell 0 0");
        add(labelText, "cell 1 0");
        add(checkBox, "cell 2 0");

        revalidate();
        repaint();
    }

}
