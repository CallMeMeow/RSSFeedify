/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.GetArticleResponse.Articles;
import java.awt.Color;

/**
 *
 * @author Bastien
 */
public class ArticleDetailView extends javax.swing.JPanel {

    /**
     * Creates new form ArticleDetailView
     */
    public ArticleDetailView() {
        initComponents();
        this.setBackground(Color.yellow);
    }
    
    public void setDesignWithData(Articles article) {
        TextArticle.setContentType("text/html");
        TextArticle.setText(article.getFull()); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TextArticle = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(TextArticle);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane TextArticle;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
