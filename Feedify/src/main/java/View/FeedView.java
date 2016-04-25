/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.AllArticlesModel;
import Model.GetArticleResponse.Articles;
import Model.UserModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Bastien
 */
public class FeedView extends javax.swing.JPanel {
    JList feedList;
    JList feedListContent;
    DefaultListModel model;
    DefaultListModel ContentModel;
    FeedListRenderer customCell = new FeedListRenderer();
    ArticleDetailView DetailPanel;
    JScrollPane spane;
    int currentPage;
    
    private OnFeedViewEventRaised FeedEvent;
    
    /**
     * Creates new form FeedView
     */
    public FeedView() {
        initComponents();
        this.setBackground(new Color(27, 193, 132));
        this.MenuPanel.setBackground(new Color(44, 62, 80));
        this.currentPage = 1;
        this.PageLabel.setText(String.valueOf(this.currentPage));
        initFeedListView();
        initContentFeedView();
    }
    
    public void initFeedListView() {
        this.MenuPanel.setLayout(new BorderLayout());
        model = new DefaultListModel();
        model.addElement("All articles");
        feedList = new JList(model);
        feedList.setBackground(new Color(44, 62, 80));
        feedList.setCellRenderer(customCell);
        
        feedList.setSelectedIndex(0);
        feedList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    setCurrentPage(1);
                    if (feedList.getSelectedIndex() == 0) {
                        FeedEvent.getAllFeed(-1, currentPage);
                    } else {
                        FeedEvent.getAllFeed(UserModel.Instance.getUserFeeds().get(feedList.getSelectedIndex() - 1).getId(), currentPage);
                    }
                    FeedName.setText(feedList.getSelectedValue().toString());
                }
            }
        });
        JScrollPane pane = new JScrollPane(feedList);
        JButton addButton = new JButton("Add Element");
        this.MenuPanel.add(pane, BorderLayout.CENTER);
        this.MenuPanel.add(addButton, BorderLayout.PAGE_END);
        addButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 DisplayAddFeedPopup();
             }
         });
    }
    
    public void reloadFeed() {
        for (int i = 0; i < UserModel.Instance.getUserFeeds().size(); i++) {
            //+ 1 to leave the "Tous" on top of the list
            model.add(i + 1, UserModel.Instance.getUserFeeds().get(i).getName());
        }
    }
    
    public void initContentFeedView() {
        FeedPanel.setLayout(new BorderLayout());
        ContentModel = new DefaultListModel();
        feedListContent = new JList(ContentModel);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer)feedListContent.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        spane = new JScrollPane(feedListContent);
        FeedPanel.add(spane, BorderLayout.CENTER);
        feedListContent.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() >= 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println(AllArticlesModel.Instance.getAllArticles().get(index).getTitle());
                    
                    DetailPanel = new ArticleDetailView();
                    DetailPanel.setDesignWithData(AllArticlesModel.Instance.getAllArticles().get(index));
                    FeedPanel.add(DetailPanel, BorderLayout.CENTER);
                    feedListContent.setVisible(false);
                    spane.setVisible(false);
                    FeedPanel.revalidate();
                }
            }
        });
    }

    public void RefreshAndDumpArticles() {
        ContentModel.removeAllElements();
        if (!feedListContent.isVisible()) {
            CloseDetailView();
        }
        for (int i = 0; i < AllArticlesModel.Instance.getAllArticles().size(); i++) {
            Articles article = AllArticlesModel.Instance.getAllArticles().get(i);
            ContentModel.addElement(article.getTitle());
        }
    }
    
    void CloseDetailView() {
        FeedPanel.remove(DetailPanel);
        DetailPanel = null;
        feedListContent.setVisible(true);
        spane.setVisible(true);
    }
    
    public void addThisFeedToList(String elem) {
        model.addElement(elem);
    }
    
    void DisplayAddFeedPopup() {
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Feed Name :"));
        panel.add(field1);
        panel.add(new JLabel("URL :"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Entrez les infos du feed",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (field1.getText().isEmpty() || field2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Un des champs est vide !", "Add feed", JOptionPane.ERROR_MESSAGE);
            } else {
                if (FeedEvent != null) {
                    FeedEvent.OnAddFeedComplete(field1.getText(), field2.getText());    
                }
            }
            MenuPanel.revalidate();
            MenuPanel.repaint();
        } else {
            System.out.println("Cancelled");
        }
    }
    
    //Raise Events
    public interface OnFeedViewEventRaised {
        void OnAddFeedComplete(String name, String URL);
        void getAllFeed(int id, int page);
    }
    
    public void setOnFeedViewEventRaised(OnFeedViewEventRaised connectEvent) {
        this.FeedEvent = connectEvent;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.PageLabel.setText(String.valueOf(currentPage));
        if (this.currentPage > 1) {
            this.PreviousPageButton.setEnabled(true);
        } else {
            this.PreviousPageButton.setEnabled(false);
        }
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
        FeedPanelHolder = new javax.swing.JPanel();
        FeedPanelHeader = new javax.swing.JPanel();
        Refresh = new javax.swing.JButton();
        FeedName = new javax.swing.JLabel();
        FeedPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        FeedPanelFooter = new javax.swing.JPanel();
        PreviousPageButton = new javax.swing.JButton();
        PageLabel = new javax.swing.JLabel();
        NextPageButton = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        MenuPanel.setPreferredSize(new java.awt.Dimension(50, 580));

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 609, Short.MAX_VALUE)
        );

        add(MenuPanel);

        FeedPanelHolder.setPreferredSize(new java.awt.Dimension(1000, 609));
        FeedPanelHolder.setLayout(new java.awt.BorderLayout());

        FeedPanelHeader.setBackground(new java.awt.Color(255, 255, 255));
        FeedPanelHeader.setLayout(new java.awt.GridBagLayout());

        Refresh.setBackground(new java.awt.Color(27, 193, 132));
        Refresh.setText("Refresh");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        FeedPanelHeader.add(Refresh, gridBagConstraints);

        FeedName.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        FeedName.setText("All articles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        FeedPanelHeader.add(FeedName, gridBagConstraints);

        FeedPanelHolder.add(FeedPanelHeader, java.awt.BorderLayout.PAGE_START);

        FeedPanel.setBackground(new java.awt.Color(255, 255, 255));
        FeedPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 0, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );

        FeedPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        FeedPanelHolder.add(FeedPanel, java.awt.BorderLayout.CENTER);

        FeedPanelFooter.setBackground(new java.awt.Color(255, 255, 255));
        FeedPanelFooter.setLayout(new java.awt.GridBagLayout());

        PreviousPageButton.setBackground(new java.awt.Color(27, 193, 132));
        PreviousPageButton.setForeground(new java.awt.Color(255, 255, 255));
        PreviousPageButton.setText("Previous Page");
        PreviousPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviousPageButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        FeedPanelFooter.add(PreviousPageButton, gridBagConstraints);

        PageLabel.setText("1");
        FeedPanelFooter.add(PageLabel, new java.awt.GridBagConstraints());

        NextPageButton.setBackground(new java.awt.Color(27, 193, 132));
        NextPageButton.setForeground(new java.awt.Color(255, 255, 255));
        NextPageButton.setText("Next Page");
        NextPageButton.setMaximumSize(new java.awt.Dimension(101, 23));
        NextPageButton.setMinimumSize(new java.awt.Dimension(101, 23));
        NextPageButton.setPreferredSize(new java.awt.Dimension(101, 27));
        NextPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextPageButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        FeedPanelFooter.add(NextPageButton, gridBagConstraints);

        FeedPanelHolder.add(FeedPanelFooter, java.awt.BorderLayout.PAGE_END);

        add(FeedPanelHolder);
    }// </editor-fold>//GEN-END:initComponents

    private void NextPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextPageButtonActionPerformed
        if (feedList.getSelectedIndex() == 0) {
            FeedEvent.getAllFeed(-1, currentPage + 1);
        } else {
            FeedEvent.getAllFeed(UserModel.Instance.getUserFeeds().get(feedList.getSelectedIndex() - 1).getId(), currentPage + 1);
        }
    }//GEN-LAST:event_NextPageButtonActionPerformed

    private void PreviousPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousPageButtonActionPerformed
        if (feedList.getSelectedIndex() == 0) {
            FeedEvent.getAllFeed(-1, currentPage - 1);
        } else {
            FeedEvent.getAllFeed(UserModel.Instance.getUserFeeds().get(feedList.getSelectedIndex() - 1).getId(), currentPage - 1);
        }
    }//GEN-LAST:event_PreviousPageButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FeedName;
    private javax.swing.JPanel FeedPanel;
    private javax.swing.JPanel FeedPanelFooter;
    private javax.swing.JPanel FeedPanelHeader;
    private javax.swing.JPanel FeedPanelHolder;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JButton NextPageButton;
    private javax.swing.JLabel PageLabel;
    private javax.swing.JButton PreviousPageButton;
    private javax.swing.JButton Refresh;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
