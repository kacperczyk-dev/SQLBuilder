package com.sql;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by DKacperc on 2017-02-01.
 */
public class ConnectionClass {

    //static String user = "daw";
    //static String pw = "WtF@11!"; //wwwwwwwwwww
    //"jdbc:sqlserver://localhost;databaseName=agr572;";
    //"PLWR-L00428"

    static Connection con;

    public ConnectionClass() {
        con = null;
        try {
            String serverName = JOptionPane.showInputDialog(new JFrame(), "Specify the server name please.");
            String dbName = JOptionPane.showInputDialog(new JFrame(), "Specify the database name please.");
            if(serverName != null && serverName.length() != 0) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String dbUrl = "jdbc:sqlserver://;serverName=" + serverName + ";databaseName=" + dbName + ";integratedSecurity=true;";
                con = DriverManager.getConnection(dbUrl);
            }
            if(con != null) {}
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Something went wrong.\nYou either forgot the initial setup or gave wrong server name.\nPlease try again.");
                System.exit(1);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Something went wrong.\nYou either forgot the initial setup or gave wrong server name.\nPlease try again.");
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Something went wrong.\nYou either forgot the initial setup or gave wrong server name.\nPlease try again.");
            System.exit(1);
        }

    }

    public ResultSet getTables() throws SQLException {
        String query = "SELECT t.name FROM sys.tables t ORDER BY t.name ASC";
        Statement stm;
        ResultSet rs = null;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }

    public ArrayList<ResultSet> getColumns(ArrayList<String> tables) throws SQLException {
        ArrayList<ResultSet> rs = new ArrayList<>();
        for(String s: tables){
            String query = "SELECT c.name FROM sys.tables t INNER JOIN sys.columns c ON t.object_id = c.object_id where t.name = '" + s + "' ORDER BY c.name ASC";
            Statement stm;
            try {
                stm = con.createStatement();
                rs.add(stm.executeQuery(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;

    }
}
