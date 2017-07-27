package com.sql;

import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


/**
 * Created by DKacperc on 2017-02-11.
 */
public class MainWindow extends JFrame {

    JPanel mainPanel;
    JLabel midLab;
    JLabel creditLab;
    JButton select;
    JButton update;
    JButton backUp;
    //JButton insert;
    GroupLayout layout;

    public MainWindow(){

        super("Query Builder");

        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setPreferredSize(new Dimension(340, 320));
        super.setResizable(false);

        midLab = new JLabel();
        creditLab = new JLabel("@2017, ver. 1.1, by Dawid Kacperczyk");
        creditLab.setFont(new Font("Calibri", Font.ITALIC, 9));
        try {
            URL sql = new ClassPathResource("sqlIcon.png").getURL();
            midLab.setIcon(new ImageIcon(sql));
        } catch (IOException e) {
            e.printStackTrace();
        }

        select = new JButton("Select");
        update = new JButton("Update");
        //insert = new JButton("Insert");
        backUp = new JButton("Back up");
        select.setMaximumSize(new Dimension(100, 50));
        update.setMaximumSize(new Dimension(100, 50));
        //insert.setMaximumSize(new Dimension(100, 50));
        backUp.setMaximumSize(new Dimension(100, 50));

        select.addActionListener(e->{
            new SelectWindow();
            this.dispose();
        });
        update.addActionListener(e->{
            new UpdateWindow();
            this.dispose();
        });
        backUp.addActionListener(e->{
            new BackupWindow();
            this.dispose();
        });

        //insert.setEnabled(false);

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
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(select)
                                .addGap(100)
                                .addComponent(update)
                        )
                        .addComponent(midLab, GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backUp)
                        )
                        .addGap(15)
                        .addComponent(creditLab)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(select)
                                .addComponent(update)
                        )
                        .addGap(10)
                        .addComponent(midLab)
                        .addGap(10)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(backUp)
                        )
                        .addGap(25)
                        .addComponent(creditLab)
        );

        return layout;
    }
}
