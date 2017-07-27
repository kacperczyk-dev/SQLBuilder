package com.sql;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by DKacperc on 2017-02-11.
 */
public class BackupWindow extends JFrame {
    GroupLayout layout;
    JPanel mainPanel;
    JLabel mainLab;
    JLabel ticketLab;
    JLabel queryLab;
    JLabel warningLab;
    JSearchableComboBox mainCombo;
    JTextArea queryText;
    JTextField ticketNr;
    JButton goBackBtn;
    JButton saveBtn;

    public BackupWindow() {

        super("Back-up Builder");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setPreferredSize(new Dimension(600, 300));
        super.setResizable(false);

        mainLab = new JLabel("Table");
        queryLab = new JLabel("Generated query");
        ticketLab = new JLabel("Ticket no.");
        warningLab = new JLabel(" ");
        queryText = new JTextArea();
        queryText.setPreferredSize(new Dimension(400, 590));
        queryText.setFont(new Font("Calibri", Font.PLAIN, 17));
        queryText.setEditable(false);
        ticketNr = new JTextField();
        ticketNr.setMaximumSize(new Dimension(150, 1));
        goBackBtn = new JButton("Go back");
        saveBtn = new JButton("Save");


        goBackBtn.addActionListener(e->{
            new MainWindow();
            this.dispose();
        });
        saveBtn.addActionListener(e-> SaveClass.saveFile(queryText.getText(), 4));

        try {
            mainCombo = new JSearchableComboBox();
            mainCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getTables()));
            mainCombo.setSelectedIndex(-1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        mainCombo.setMaximumSize(new Dimension(150, 1));
        mainCombo.addActionListener(e -> {
            String item = (String) mainCombo.getSelectedItem();
            String txNr = ticketNr.getText();
            if(txNr.length() > 0) {
                warningLab.setText(" ");
                queryText.setText("SELECT * \nINTO " + "U4CS_" + txNr + "_" + item + "_1" + "\nFROM " + item);
            }
            else{
                warningLab.setText("Enter the ticket number!");
                warningLab.setForeground(Color.RED);
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(setLayout());
        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setContentPane(mainPanel);
    }

    GroupLayout setLayout(){
        layout = new GroupLayout(mainPanel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(warningLab)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(ticketLab)
                                        .addComponent(ticketNr)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(mainLab)
                                        .addComponent(mainCombo)
                                )
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(queryLab, GroupLayout.Alignment.CENTER)
                                .addComponent(queryText)
                                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                        .addComponent(saveBtn)
                                        .addComponent(goBackBtn)
                                )
                        )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(warningLab)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(ticketLab)
                                        .addComponent(ticketNr)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(mainLab)
                                        .addComponent(mainCombo)
                                )
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(queryLab)
                                .addComponent(queryText)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(saveBtn)
                                        .addComponent(goBackBtn)
                                )
                        )
        );

        return layout;
    }
}
