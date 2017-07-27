package com.sql;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DKacperc on 2017-02-11.
 */
public class UpdateWindow extends JFrame {
    GroupLayout layout;
    JPanel mainPanel;
    JLabel mainLab;
    JLabel setLab;
    JLabel whereLab;
    JLabel queryLab;
    JSearchableComboBox mainCombo;
    JSearchableComboBox whereCombo;
    JSearchableComboBox setCombo;
    JSearchableComboBox subFromCombo;
    JSearchableComboBox subColumnCombo;
    JButton addEqualsBtn;
    JButton addInBtn;
    JButton addBetBtn;
    JButton addBigBtn;
    JButton addLessBtn;
    JButton addNotBtn;
    JButton addSubBtn;
    JButton addLikeBtn;
    JButton addEqLessBtn;
    JButton addEqBigBtn;
    JButton minWhereBtn;
    JButton goBackBtn;
    JButton saveBtn;
    JButton saveCu;
    JButton saveWe;
    JTextArea queryText;
    JSeparator jsOne;
    JSeparator jsTwo;

    Boolean first;
    String[] fromArray;
    ArrayList<String> setArray;
    ArrayList<String> whereArray;
    ArrayList<JButton> btnsArray;

    public UpdateWindow(){
        super("Update Builder");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setPreferredSize(new Dimension(700, 600));
        super.setResizable(false);

        first = true;
        queryText = new JTextArea();
        queryText.setMaximumSize(new Dimension(400, 590));
        queryText.setPreferredSize(new Dimension(400, 590));
        queryText.setFont(new Font("Calibri", Font.PLAIN, 17));
        queryText.setEditable(false);
        fromArray = new String[1];
        setArray = new ArrayList<>();
        whereArray = new ArrayList<>();
        btnsArray = new ArrayList<>();

        jsOne = new JSeparator(SwingConstants.HORIZONTAL);
        jsTwo = new JSeparator(SwingConstants.HORIZONTAL);
        jsOne.setSize(new Dimension(100, 1));
        jsTwo.setSize(new Dimension(100, 1));

        mainLab = new JLabel("UPDATE ");
        queryLab = new JLabel("Generated query");
        setLab = new JLabel("SET ");
        whereLab = new JLabel("WHERE");

        try {
            mainCombo = new JSearchableComboBox();
            mainCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getTables()));
            mainCombo.setSelectedIndex(-1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        whereCombo = new JSearchableComboBox();
        setCombo = new JSearchableComboBox();
        subFromCombo = new JSearchableComboBox();
        subColumnCombo = new JSearchableComboBox();
        addEqualsBtn = new JButton("=");
        addInBtn = new JButton("IN");
        addBetBtn = new JButton("BETWEEN");
        addBigBtn = new JButton(">");
        addLessBtn = new JButton("<");
        addNotBtn = new JButton("<>");
        addSubBtn = new JButton("Sub Query");
        minWhereBtn = new JButton("Remove last");
        addLikeBtn = new JButton("LIKE");
        addEqLessBtn = new JButton("<=");
        addEqBigBtn = new JButton(">=");
        goBackBtn = new JButton("Go back");
        saveBtn = new JButton("Save");
        saveWe = new JButton("Discl. we run");
        saveCu = new JButton("Discl. cu runs");

        btnsArray.add(addEqualsBtn);
        btnsArray.add(addInBtn);
        btnsArray.add(addBetBtn);
        btnsArray.add(addBigBtn);
        btnsArray.add(addLessBtn);
        btnsArray.add(addNotBtn);
        btnsArray.add(addLikeBtn);
        btnsArray.add(addEqBigBtn);
        btnsArray.add(addEqLessBtn);
        addSubBtn.setEnabled(false);

        for(JButton btn: btnsArray){
            btn.setEnabled(false);
        }


        mainCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        whereCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        subFromCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        subColumnCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        setCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        mainCombo.setMaximumSize(new Dimension(150, 1));
        whereCombo.setMaximumSize(new Dimension(150, 1));
        subFromCombo.setMaximumSize(new Dimension(150, 1));
        subColumnCombo.setMaximumSize(new Dimension(150, 1));
        setCombo.setMaximumSize(new Dimension(150, 1));
        try {
            subFromCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getTables()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        subFromCombo.addActionListener(e->{
            try {
                subColumnCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(
                        new ArrayList<>(Arrays.asList((String)subFromCombo.getSelectedItem()))).get(0)));
                subColumnCombo.setSelectedIndex(-1);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        subColumnCombo.addActionListener(e->{
            if(subFromCombo.getSelectedIndex() != -1 && subColumnCombo.getSelectedIndex() != -1){
                addSubBtn.setEnabled(true);
            }
        });
        subFromCombo.setSelectedIndex(-1);
        mainCombo.addActionListener(e -> {
            String item = (String) mainCombo.getSelectedItem();
            fromArray[0] = "UPDATE " + item + "\n";
            whereArray.clear();
            if(item != null) {
                addQueryToArea();
                ArrayList<String> tableList = new ArrayList<>();
                tableList.add(item);
                try {
                    whereCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(tableList).get(0)));
                    whereCombo.setSelectedIndex(-1);
                    setCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(tableList).get(0)));
                    setCombo.setSelectedIndex(-1);
                } catch (Exception ee) {

                }
            }
        });
        setCombo.addActionListener(e -> {
            String item = (String) setCombo.getSelectedItem();
            setArray.clear();
            if(setCombo.getSelectedIndex() != -1) {
                setArray.add("SET " + item + " = 'value'");
            }
            addQueryToArea();
        });
        whereCombo.addActionListener(e-> {
            if(whereCombo.getSelectedIndex() != -1) {
                for (JButton btn : btnsArray) {
                    btn.setEnabled(true);
                }
            }
        });
        minWhereBtn.addActionListener(e->{
            int size = whereArray.size();
            if(size > 0) {
                whereArray.remove(whereArray.size() - 1);
                if(size - 1 == 0){
                    first = true;
                }
                addQueryToArea();
            }
        });

        addEqualsBtn.addActionListener(e -> addWhere("="));
        addInBtn.addActionListener(e-> addWhere("IN"));
        addBetBtn.addActionListener(e-> addWhere("BETWEEN"));
        addLessBtn.addActionListener(e-> addWhere("<"));
        addBigBtn.addActionListener(e-> addWhere(">"));
        addNotBtn.addActionListener(e-> addWhere("<>"));
        addSubBtn.addActionListener(e-> addWhere("SUB"));
        addLikeBtn.addActionListener(e-> addWhere("LIKE"));
        addEqLessBtn.addActionListener(e-> addWhere("<="));
        addEqBigBtn.addActionListener(e-> addWhere(">="));
        goBackBtn.addActionListener(e->{
            new MainWindow();
            this.dispose();
        });
        saveBtn.addActionListener(e-> SaveClass.saveFile(queryText.getText(), 2));
        saveWe.addActionListener(e-> SaveClass.saveWe(queryText.getText()));
        saveCu.addActionListener(e-> SaveClass.saveCu(queryText.getText()));

        mainPanel = new JPanel();
        mainPanel.setLayout(setLayout());
        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setContentPane(mainPanel);
    }

    void addWhere(String operator){
        String column = "";
        String wh;
        if(!(whereCombo.getSelectedIndex() == -1)) {
            column = (String) whereCombo.getSelectedItem();
        }
        if(first) {
            wh = "\nWHERE " + column;
            first = false;
        }
        else{
            wh = "\nAND " + column;
        }

        if(operator == "="){
            wh = wh  + " " + operator + " " + "'value'";
        }
        else if(operator == "<>"){
            wh = wh + " " + operator + " " +  "'value'";
        }
        else if(operator == ">"){
            wh = wh + " " + operator + " " +  "'value'";
        }
        else if(operator == "<"){
            wh = wh + " " + operator + " " +  "'value'";
        }
        else if(operator == "<="){
            wh = wh + " " + operator + " " +  "'value'";
        }
        else if(operator == ">="){
            wh = wh + " " + operator + " " +  "'value'";
        }
        else if(operator == "LIKE"){
            wh = wh + " " + operator + " " +  "'%value%'";
        }
        else if(operator == "IN"){
            wh = wh + " " + operator + " " +  "( \n'value',\n'value'\n)";
        }
        else if(operator == "BETWEEN"){
            wh = wh + " " + operator + " " +  " 'value1' AND 'value2'";
        }
        else if(operator == "SUB"){
            wh = wh + " IN (\nSELECT " + subColumnCombo.getSelectedItem()
                    + "\nFROM " + subFromCombo.getSelectedItem() + "\n" +
                    "WHERE 'a column' = 'value'\n)";
            subColumnCombo.removeAllItems();
            subFromCombo.setSelectedIndex(-1);
        }
        for(JButton btn: btnsArray){
            btn.setEnabled(false);
        }
        addSubBtn.setEnabled(false);
        whereArray.add(wh);
        addQueryToArea();
        whereCombo.setSelectedIndex(-1);
    }
    void addQueryToArea(){
        String f = "";
        String j = "";
        String w = "";
        String fin;

        for(String s: fromArray){
            f = f + s;
        }
        for(String s: setArray){
            j = j + s;
        }
        for(String s: whereArray){
            w = w + s;
        }

        fin = "BEGIN TRAN\n" + f + j + w + "\n\n--COMMIT\n--ROLLBACK";
        queryText.setText(fin);
    }
    GroupLayout setLayout(){
        layout = new GroupLayout(mainPanel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(mainLab)
                                        .addComponent(mainCombo)
                                )
                                .addComponent(jsOne)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(setLab)
                                        .addComponent(setCombo)
                                )
                                .addComponent(jsTwo)
                                .addComponent(whereLab)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(whereCombo)
                                        )
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(addSubBtn)
                                        .addComponent(subFromCombo)
                                        .addComponent(subColumnCombo)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(addEqualsBtn)
                                        .addComponent(addNotBtn)
                                        .addComponent(addBetBtn)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(addBigBtn)
                                        .addComponent(addLessBtn)
                                        .addComponent(addEqLessBtn)
                                        .addComponent(addEqBigBtn)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(addInBtn)
                                        .addComponent(addLikeBtn)
                                )
                                .addComponent(minWhereBtn)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(queryLab, GroupLayout.Alignment.CENTER)
                                .addComponent(queryText)
                                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                        .addComponent(saveBtn)
                                        .addComponent(goBackBtn)
                                )
                                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                        .addComponent(saveWe)
                                        .addComponent(saveCu)
                                )
                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(mainLab)
                                        .addComponent(mainCombo)
                                )
                                .addComponent(jsOne, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(setLab)
                                        .addComponent(setCombo)
                                )
                                .addComponent(jsTwo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(whereLab)
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(whereCombo)
                                        )
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(addSubBtn)
                                        .addComponent(subFromCombo)
                                        .addComponent(subColumnCombo)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(addEqualsBtn)
                                        .addComponent(addNotBtn)
                                        .addComponent(addBetBtn)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(addBigBtn)
                                        .addComponent(addLessBtn)
                                        .addComponent(addEqLessBtn)
                                        .addComponent(addEqBigBtn)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(addInBtn)
                                        .addComponent(addLikeBtn)
                                )
                                .addComponent(minWhereBtn)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(queryLab)
                                .addComponent(queryText)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(saveBtn)
                                        .addComponent(goBackBtn)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(saveWe)
                                        .addComponent(saveCu)
                                )
                        )
        );

        return layout;
    }
}
