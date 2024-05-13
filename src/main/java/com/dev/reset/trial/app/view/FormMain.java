package com.dev.reset.trial.app.view;

import com.dev.reset.trial.app.controllers.AppController;
import com.dev.reset.trial.app.utils.WrapLayout;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class FormMain extends javax.swing.JFrame {

    private final AppController appController;

    public FormMain() {
        initComponents();
        init();
        appController = new AppController(this);
        appController.initialize();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneMain = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        paneProducts = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JetBrains IDE trial reset");
        setMinimumSize(new java.awt.Dimension(400, 380));
        setResizable(false);

        javax.swing.GroupLayout paneProductsLayout = new javax.swing.GroupLayout(paneProducts);
        paneProducts.setLayout(paneProductsLayout);
        paneProductsLayout.setHorizontalGroup(
            paneProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        paneProductsLayout.setVerticalGroup(
            paneProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(paneProducts);

        btnStart.setBackground(new java.awt.Color(1, 164, 115));
        btnStart.setForeground(new java.awt.Color(255, 255, 255));
        btnStart.setText("Start");
        btnStart.setFocusable(false);

        textPane.setEditable(false);
        textPane.setFocusable(false);
        jScrollPane2.setViewportView(textPane);

        javax.swing.GroupLayout paneMainLayout = new javax.swing.GroupLayout(paneMain);
        paneMain.setLayout(paneMainLayout);
        paneMainLayout.setHorizontalGroup(
            paneMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnStart)))
                .addContainerGap())
        );
        paneMainLayout.setVerticalGroup(
            paneMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStart)
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
    }// </editor-fold>//GEN-END:initComponents

    private void init() {
        styleJScrollPane(paneProducts);
        paneProducts.setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
        styleJtextPane();
//        lblSystem.setText("[ " + System.getProperty("os.name") + " ]");
//        lblSystem.putClientProperty(FlatClientProperties.STYLE, ""
//                + "[light]foreground:tint(@foreground,40%);"
//                + "[dark]foreground:shade(@foreground,40%);");

    }

    private void styleJtextPane() {
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

    public static void styleJScrollPane(Component component) {
        JScrollPane scroll = (JScrollPane) component.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,2%);"
                + "track:lighten(@background,2%);"
                + "trackArc:999");

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnStart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel paneMain;
    public javax.swing.JPanel paneProducts;
    public static javax.swing.JTextPane textPane;
    // End of variables declaration//GEN-END:variables
}
