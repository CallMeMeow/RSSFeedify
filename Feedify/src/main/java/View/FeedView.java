/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;

/**
 *
 * @author Bastien
 */
public class FeedView extends javax.swing.JPanel {

    /**
     * Creates new form FeedView
     */
    public FeedView() {
        initComponents();
        this.setBackground(new Color(27, 193, 132));
        this.FeedPanel.setBackground(new Color(27, 193, 132));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        MenuPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        FeedPanel = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        MenuPanel.setPreferredSize(new java.awt.Dimension(50, 580));

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPanelLayout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(MenuPanel);

        javax.swing.GroupLayout FeedPanelLayout = new javax.swing.GroupLayout(FeedPanel);
        FeedPanel.setLayout(FeedPanelLayout);
        FeedPanelLayout.setHorizontalGroup(
            FeedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );
        FeedPanelLayout.setVerticalGroup(
            FeedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 609, Short.MAX_VALUE)
        );

        add(FeedPanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FeedPanel;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}