package com.sql;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DKacperc on 2017-01-31.
 */
public class Helper {

    static List<String> tableList;


    static JComboBox newBox(ResultSet rs) throws SQLException {
        tableList = new ArrayList<>();
        while(rs.next()) {
            tableList.add(rs.getString("name"));
        }
        JComboBox jb = new JComboBox();
        jb.setModel(new DefaultComboBoxModel(tableList.toArray()));
        jb.setMaximumSize(new Dimension(150, 1));

        jb.addActionListener(e -> {
            String item = (String) jb.getSelectedItem();

        });

        return jb;
    }

    static DefaultComboBoxModel newModel(ResultSet rs) throws SQLException {
        tableList = new ArrayList<>();
        while(rs.next()) {
            tableList.add(rs.getString("name"));
        }
        return new DefaultComboBoxModel(tableList.toArray());

    }
}
