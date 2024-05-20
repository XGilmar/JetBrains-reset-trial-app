package com.dev.reset.trial.app.view.components.items;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

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

        MigLayout layout = new MigLayout("", "[][]push[]");
        putClientProperty(FlatClientProperties.STYLE, "arc:10;"
                + "background:lighten(@background,3%);");
        setMinimumSize(new Dimension(180, 38));
        setMaximumSize(new Dimension(180, 38));
        setPreferredSize(new Dimension(180, 38));
        setLayout(layout);

        labelText.putClientProperty(FlatClientProperties.STYLE, "font:bold +1");
        checkBox.setFocusable(false);

        add(labelIcon);
        add(labelText);
        add(checkBox);

    }

}
