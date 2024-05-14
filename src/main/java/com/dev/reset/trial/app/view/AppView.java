package com.dev.reset.trial.app.view;

import com.dev.reset.trial.app.controllers.AppController;
import com.dev.reset.trial.app.utils.WrapLayout;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class AppView extends javax.swing.JFrame {

    public AppView() throws HeadlessException {
        initComponents();
        AppController appController = new AppController(this);
        appController.initialize();
    }

    private void initComponents() {
        javax.swing.JPanel paneMain = new javax.swing.JPanel();
        javax.swing.JPanel paneGroupBtn = new javax.swing.JPanel();
        JScrollPane jScrollProduct = new JScrollPane();
        paneProducts = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        labelGithub = new javax.swing.JLabel();
        JScrollPane jScrollTextPane = new JScrollPane();
        textPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JetBrains trial reset - " + System.getProperty("os.name"));
        setMinimumSize(new java.awt.Dimension(400, 405));
        setResizable(false);

        paneProducts.setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
        jScrollProduct.setViewportView(paneProducts);
        styleJScrollPane(paneProducts);

        labelGithub.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:tint(@foreground,40%);"
                        + "[dark]foreground:shade(@foreground,40%);");

        labelGithub.setIcon(new FlatSVGIcon("icons/github.svg", 0.6f));
        labelGithub.setText("XGilmarDev");
        labelGithub.setCursor(new Cursor(Cursor.HAND_CURSOR));


        btnStart.setBackground(new java.awt.Color(1, 164, 115));
        btnStart.setForeground(new java.awt.Color(255, 255, 255));
        btnStart.setText("Start");
        btnStart.setFocusable(false);
        btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));

        textPane.setEditable(false);
        textPane.setFocusable(false);
        jScrollTextPane.setViewportView(textPane);
        styleTextPane();

        javax.swing.GroupLayout paneGroupBtnLayout = new javax.swing.GroupLayout(paneGroupBtn);
        paneGroupBtn.setLayout(paneGroupBtnLayout);
        paneGroupBtnLayout.setHorizontalGroup(
                paneGroupBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paneGroupBtnLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelGithub, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnStart)
                                .addContainerGap())
        );
        paneGroupBtnLayout.setVerticalGroup(
                paneGroupBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneGroupBtnLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(paneGroupBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelGithub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout paneMainLayout = new javax.swing.GroupLayout(paneMain);
        paneMain.setLayout(paneMainLayout);
        paneMainLayout.setHorizontalGroup(
                paneMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paneMainLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(paneMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                        .addComponent(jScrollTextPane)
                                        .addComponent(paneGroupBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        paneMainLayout.setVerticalGroup(
                paneMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paneMainLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollTextPane, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paneGroupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(paneMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(paneMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>


    private void styleTextPane() {
        styleJScrollPane(textPane);
        textPane.putClientProperty(FlatClientProperties.STYLE, "font:plain -2;"
                + "[light]foreground:tint(@foreground,40%);"
                + "[dark]foreground:shade(@foreground,40%);"
                + "background:lighten(@background,0%);");
        textPane.setContentType("text/html");
    }

    public static void showText(String text) {
        textPane.setText("<html>" + text + "</html>");
    }

    private void styleJScrollPane(Component component) {
        JScrollPane scroll = (JScrollPane) component.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,2%);"
                        + "track:lighten(@background,2%);"
                        + "trackArc:999");

    }

    // Variables declaration - do not modify
    public javax.swing.JLabel labelGithub;
    public javax.swing.JButton btnStart;
    public javax.swing.JPanel paneProducts;
    private static javax.swing.JTextPane textPane;

}
