package view;

import javax.swing.*;
import java.awt.*;

public class Ranking extends BasePanel{
    private static volatile Ranking sInstance = null;
    public static Ranking instance() {
        if (sInstance == null) {
            synchronized (BasePanel.class) {
                if (sInstance == null) {
                    sInstance = new Ranking();
                }
            }
        }
        return sInstance;
    }
    private Ranking(){
        setSize(1024,768);
        setLayout(null);
        addTitle();
        addRankingTable();
        addBackButton();
        addRankingPanel();
        add(buttonPanel);
        backButton.addActionListener(e -> {
            View.changePanel("Menu");
        });

    }
    private final JLabel title = new JLabel("Ranking",JLabel.CENTER);
    private final JTable RankingTable = new JTable();
    private final JScrollPane RankingScrollPane = new JScrollPane(RankingTable);
    private final JButton backButton = new MyButton("Back");
    private final JPanel RankingPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private void addTitle(){
        title.setLocation(0,0);
        title.setSize(1024,100);
        title.setFont(new Font("Rockwell", Font.BOLD, 50));
        add(title);
    }
    private void addRankingTable(){
        RankingTable.setRowHeight(30);
        RankingTable.setFont(new Font("Rockwell", Font.BOLD, 20));
        RankingTable.setRowSelectionAllowed(false);
        RankingTable.setCellSelectionEnabled(false);
        RankingTable.setShowGrid(false);
        RankingTable.setShowHorizontalLines(false);
        RankingTable.setShowVerticalLines(false);
        RankingTable.setTableHeader(null);
        RankingTable.setOpaque(false);
        RankingTable.setFillsViewportHeight(true);
        RankingTable.setRowSelectionAllowed(false);
        RankingTable.setCellSelectionEnabled(false);
        RankingTable.setShowGrid(false);
        RankingTable.setShowHorizontalLines(false);
        RankingTable.setShowVerticalLines(false);
        RankingTable.setTableHeader(null);
        RankingTable.setOpaque(false);
        RankingTable.setFillsViewportHeight(true);
        RankingScrollPane.setLocation(0,100);
        RankingScrollPane.setSize(1024,500);
        RankingScrollPane.setOpaque(false);
        RankingScrollPane.getViewport().setOpaque(false);
        RankingScrollPane.setBorder(null);
        RankingPanel.setLocation(0,100);
        RankingPanel.setSize(1024,500);
        RankingPanel.setOpaque(false);
        RankingPanel.add(RankingScrollPane);
        add(RankingPanel);
    }
    private void addBackButton(){
        backButton.setLocation(0,600);
        backButton.setSize(1024,100);
        backButton.setFont(new Font("Rockwell", Font.BOLD, 50));
        buttonPanel.setLocation(0,600);
        buttonPanel.setSize(1024,100);
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        add(buttonPanel);
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setForeground(new Color(247, 255, 0, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setForeground(Color.BLACK);
            }
        });
    }
    private void addRankingPanel(){
        RankingPanel.setLocation(0,100);
        RankingPanel.setSize(1024,500);
        RankingPanel.setOpaque(false);
        RankingPanel.add(RankingScrollPane);
        add(RankingPanel);
    }
    public JButton getBackButton() {
        return backButton;
    }
    public JTable getRankingTable() {
        return RankingTable;
    }
    public JPanel getRankingPanel() {
        return RankingPanel;
    }
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
}
