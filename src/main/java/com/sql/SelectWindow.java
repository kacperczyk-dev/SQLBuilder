package com.sql;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by DKacperc on 2017-01-31.
 */
public class SelectWindow extends JFrame{

    JPanel mainPanel;
    JLabel mainLab;
    JLabel fromLab;
    JLabel joinLab;
    JLabel onLab;
    JLabel whereLab;
    JLabel baseTblLab;
    JLabel firstJoinLab;
    JLabel secondJoinLab;
    JLabel queryLab;

    JSeparator jsOne;
    JSeparator jsTwo;
    JSeparator jsThree;

    JSearchableComboBox mainCombo;
    JSearchableComboBox joinCombo;
    JSearchableComboBox onACombo;
    JSearchableComboBox onBCombo;
    JSearchableComboBox whereComboA;
    JSearchableComboBox whereComboB;
    JSearchableComboBox whereComboC;
    JSearchableComboBox subFromCombo;
    JSearchableComboBox subColumnCombo;

    JButton addJoinBtn;
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
    JButton goBackBtn;
    JButton saveBtn;
    JButton minJoinBtn;
    JButton minWhereBtn;
    JTextArea queryText;

    GroupLayout layout;

    Boolean first;
    int letter;
    String[] fromArray;
    ArrayList<String> joinArray;
    ArrayList<String> whereArray;
    ArrayList<JButton> btnsArray;

    public SelectWindow() {

        super("Select Builder");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setPreferredSize(new Dimension(700, 600));
        super.setResizable(false);

        queryText = new JTextArea();
        queryText.setMaximumSize(new Dimension(400, 590));
        queryText.setPreferredSize(new Dimension(400, 590));
        queryText.setFont(new Font("Calibri", Font.PLAIN, 17));
        queryText.setEditable(false);

        letter = 1;
        first = true;
        fromArray = new String[1];
        joinArray = new ArrayList<>();
        whereArray = new ArrayList<>();
        btnsArray = new ArrayList<>();

        jsOne = new JSeparator(SwingConstants.HORIZONTAL);
        jsTwo = new JSeparator(SwingConstants.HORIZONTAL);
        jsThree = new JSeparator(SwingConstants.HORIZONTAL);
        jsOne.setSize(new Dimension(100, 1));
        jsTwo.setSize(new Dimension(100, 1));
        jsThree.setSize(new Dimension(100, 1));

        mainLab = new JLabel("SELECT *");
        queryLab = new JLabel("Generated query");
        fromLab = new JLabel("FROM");
        joinLab = new JLabel("INNER JOIN");
        baseTblLab = new JLabel("Base table");
        firstJoinLab = new JLabel("First join");
        secondJoinLab = new JLabel("Second join");
        onLab = new JLabel("ON");
        whereLab = new JLabel("WHERE");

        try {
            mainCombo = new JSearchableComboBox();
            mainCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getTables()));
            mainCombo.setSelectedIndex(-1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        joinCombo = new JSearchableComboBox();
        onACombo = new JSearchableComboBox();
        onBCombo = new JSearchableComboBox();
        whereComboA = new JSearchableComboBox();
        whereComboB = new JSearchableComboBox();
        whereComboC = new JSearchableComboBox();
        subFromCombo = new JSearchableComboBox();
        subColumnCombo = new JSearchableComboBox();

        addJoinBtn = new JButton("Add join");
        addEqualsBtn = new JButton("=");
        addInBtn = new JButton("IN");
        addBetBtn = new JButton("BETWEEN");
        addBigBtn = new JButton(">");
        addLessBtn = new JButton("<");
        addNotBtn = new JButton("<>");
        addSubBtn = new JButton("Sub Query");
        minJoinBtn = new JButton("Remove last");
        minWhereBtn = new JButton("Remove last");
        addLikeBtn = new JButton("LIKE");
        addEqLessBtn = new JButton("<=");
        addEqBigBtn = new JButton(">=");
        goBackBtn = new JButton("Go back");
        saveBtn = new JButton("Save");

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
        addJoinBtn.setEnabled(false);

        for(JButton btn: btnsArray){
            btn.setEnabled(false);
        }

        mainCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        joinCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        onACombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        onBCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        whereComboA.setPrototypeDisplayValue("XXXXXXXXXXX");
        whereComboB.setPrototypeDisplayValue("XXXXXXXXXXX");
        whereComboC.setPrototypeDisplayValue("XXXXXXXXXXX");
        subFromCombo.setPrototypeDisplayValue("XXXXXXXXXXX");
        subColumnCombo.setPrototypeDisplayValue("XXXXXXXXXXX");

        mainCombo.setMaximumSize(new Dimension(150, 1));
        joinCombo.setMaximumSize(new Dimension(150, 1));
        onACombo.setMaximumSize(new Dimension(100, 1));
        onBCombo.setMaximumSize(new Dimension(100, 1));
        whereComboA.setMaximumSize(new Dimension(150, 1));
        whereComboB.setMaximumSize(new Dimension(150, 1));
        whereComboC.setMaximumSize(new Dimension(150, 1));
        subFromCombo.setMaximumSize(new Dimension(150, 1));
        subColumnCombo.setMaximumSize(new Dimension(150, 1));

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
        subColumnCombo.addActionListener(e-> {
            if(subColumnCombo.getSelectedIndex() != -1) {
                if(whereComboA.getSelectedIndex() != -1 || whereComboB.getSelectedIndex() != -1 ||
                        whereComboC.getSelectedIndex() != -1) {
                    addSubBtn.setEnabled(true);
                }
            }
        });
        subFromCombo.setSelectedIndex(-1);

        mainCombo.addActionListener(e -> {
            String item = (String) mainCombo.getSelectedItem();
            fromArray[0] = "SELECT * \nFROM " + item + " a\n";
            joinArray.clear();
            whereArray.clear();
            if(item != null) {
                addQueryToArea();
                ArrayList<String> tableList = new ArrayList<>();
                tableList.add(item);
                try {
                    joinCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getTables()));
                    whereComboA.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(tableList).get(0)));
                    joinCombo.setSelectedIndex(-1);
                    onACombo.setSelectedIndex(-1);
                    onBCombo.setSelectedIndex(-1);
                    whereComboA.setSelectedIndex(-1);
                    whereComboB.setSelectedIndex(-1);
                    whereComboC.setSelectedIndex(-1);
                } catch (Exception ee) {

                }
            }
        });

        joinCombo.addActionListener(e -> {
            String itemFrom = (String) mainCombo.getSelectedItem();
            String itemJoin = (String) joinCombo.getSelectedItem();
            ArrayList<String> tableList = new ArrayList<>();
            tableList.add(itemFrom);
            tableList.add(itemJoin);
            try {
                onACombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(tableList).get(0)));
                onBCombo.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(tableList).get(1)));
                onACombo.setSelectedIndex(-1);
                onBCombo.setSelectedIndex(-1);
            } catch(Exception ee){

            }
        });
        onBCombo.addActionListener(e->{
            if(joinCombo.getSelectedIndex() != -1 && onACombo.getSelectedIndex() != -1 && onBCombo.getSelectedIndex() != -1){
                addJoinBtn.setEnabled(true);
            }
        });
        addJoinBtn.addActionListener(e -> {
            String alias = "";
            String tbl = (String)joinCombo.getSelectedItem();
            if(letter == 1) {
                alias = "b"; letter++;
                try {
                    whereComboB.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(new ArrayList<>(Arrays.asList(tbl))).get(0)));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            else if (letter == 2) {
                alias = "c"; letter++;
                try {
                    whereComboC.setModel(Helper.newModel(SqlBuilderApplication.cc.getColumns(new ArrayList<>(Arrays.asList(tbl))).get(0)));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            if(letter == 3){
                addJoinBtn.setEnabled(false);
            }
            String table = (String) joinCombo.getSelectedItem();
            String onA = (String) onACombo.getSelectedItem();
            String onB = (String) onBCombo.getSelectedItem();
            joinArray.add("INNER JOIN " + table + " " + alias + "\nON a." + onA + " = " + alias + "." + onB + "\n");
            joinCombo.setSelectedIndex(-1);
            onACombo.setSelectedIndex(-1);
            onBCombo.setSelectedIndex(-1);
            whereComboA.setSelectedIndex(-1);
            whereComboB.setSelectedIndex(-1);
            whereComboC.setSelectedIndex(-1);
            subFromCombo.setSelectedIndex(-1);
            addJoinBtn.setEnabled(false);
            addQueryToArea();

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
        saveBtn.addActionListener(e-> SaveClass.saveFile(queryText.getText(), 1));

        whereComboA.addActionListener(e -> {
            if(whereComboA.getSelectedIndex() != -1){
                for(JButton btn: btnsArray){
                    btn.setEnabled(true);
                }
                whereComboB.setSelectedIndex(-1);
                whereComboC.setSelectedIndex(-1);
            }
        });
        whereComboB.addActionListener(e -> {
            if(whereComboB.getSelectedIndex() != -1){
                for(JButton btn: btnsArray){
                    btn.setEnabled(true);
                }
                whereComboA.setSelectedIndex(-1);
                whereComboC.setSelectedIndex(-1);
            }
        });
        whereComboC.addActionListener(e -> {
            if(whereComboC.getSelectedIndex() != -1){
                for(JButton btn: btnsArray){
                    btn.setEnabled(true);
                }
                whereComboA.setSelectedIndex(-1);
                whereComboB.setSelectedIndex(-1);
            }
        });

        minWhereBtn.addActionListener(e->{
            if(whereArray.size() > 0) {
                whereArray.remove(whereArray.size() - 1);
                if(whereArray.size() == 0){
                    first = true;
                }
                addQueryToArea();
            }
        });
        minJoinBtn.addActionListener(e->{
            if(joinArray.size() > 0) {
                joinArray.remove(joinArray.size() - 1);
                letter--;
                first = true;
                addJoinBtn.setEnabled(true);
                if(whereComboC.getItemCount() > 0){
                    whereComboC.removeAllItems();

                }
                else if(whereComboB.getItemCount() > 0){
                    whereComboB.removeAllItems();
                }
                whereArray.clear();
                addQueryToArea();
            }
        });
        addJoinBtn.setToolTipText("If you join a table, you will only see results if they exist in both the main table and the join table; Specify the columns to be used to joint the data");
        minJoinBtn.setToolTipText("If you delete the last join, you will also delete WHERE clause along with it");
        minWhereBtn.setToolTipText("Will remove the last constraint");
        addSubBtn.setToolTipText("This button creates a subquery as a constraint, it will become available once you fill both of the boxes to the right");
        mainCombo.setToolTipText("Select a main table for the SELECT statement");
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
        if(!(whereComboA.getSelectedIndex() == -1)) {
            column = "a." + whereComboA.getSelectedItem();
        }
        else if(!(whereComboB.getSelectedIndex() == -1)){
            column = "b." + whereComboB.getSelectedItem();
        }
        else if(!(whereComboC.getSelectedIndex() == -1)) {
            column = "c." + whereComboC.getSelectedItem();
        }
        if(first) {
            wh = "WHERE " + column;
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
                    "WHERE 'a column' = 'value'\n) ";
            subColumnCombo.removeAllItems();
            subFromCombo.setSelectedIndex(-1);

        }
        for(JButton btn: btnsArray){
            btn.setEnabled(false);
        }
        addSubBtn.setEnabled(false);
        whereArray.add(wh);
        addQueryToArea();
        whereComboA.setSelectedIndex(-1);
        whereComboB.setSelectedIndex(-1);
        whereComboC.setSelectedIndex(-1);
    }
    void addQueryToArea(){
        String f = "";
        String j = "";
        String w = "";
        String fin;

        for(String s: fromArray){
            f = f + s;
        }
        for(String s: joinArray){
            j = j + s;
        }
        for(String s: whereArray){
            w = w + s;
        }

        fin = f + j + w;
        queryText.setText(fin);
    }
    GroupLayout setLayout(){
        layout = new GroupLayout(mainPanel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(mainLab)
                                .addComponent(fromLab)
                                .addComponent(mainCombo)
                                .addComponent(jsOne)
                                .addComponent(joinLab)
                                .addComponent(joinCombo)
                                .addComponent(onLab)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(onACombo)
                                        .addComponent(onBCombo)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(addJoinBtn)
                                        .addComponent(minJoinBtn)
                                )
                                .addComponent(jsTwo)
                                .addComponent(whereLab)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(baseTblLab)
                                                .addComponent(whereComboA)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(firstJoinLab)
                                                .addComponent(whereComboB)
                                        )
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(secondJoinLab)
                                                .addComponent(whereComboC)
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
                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainLab)
                                .addComponent(fromLab)
                                .addComponent(mainCombo)
                                .addComponent(jsOne, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(joinLab)
                                .addComponent(joinCombo)
                                .addComponent(onLab)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(onACombo)
                                        .addComponent(onBCombo)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(addJoinBtn)
                                        .addComponent(minJoinBtn)
                                )
                                .addComponent(jsTwo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(whereLab)
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(baseTblLab)
                                                .addComponent(whereComboA)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(firstJoinLab)
                                                .addComponent(whereComboB)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(secondJoinLab)
                                                .addComponent(whereComboC)
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
                        )
        );

        return layout;
    }

}
