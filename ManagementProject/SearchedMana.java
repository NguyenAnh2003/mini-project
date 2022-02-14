package ManagementProject;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class SearchedMana extends JFrame{
    
    Connection conn;
    Vector vData = new Vector(), vTitle = new Vector();
    DefaultTableModel model;
    JScrollPane tableResult;
    JTable tb;
    String sql = "";
    SearchManagers searchManagers;

    public SearchedMana(String nameGet, SearchManagers s) {
        try {
            searchManagers = s;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-PNPO69N\\SQLEXPRESS;databaseName=managementProject;user=sa;password=12345");

            nameGet = s.nameTextField.getText();

            loadData(nameGet);
            model = new DefaultTableModel(vData, vTitle);
            tb = new JTable(model);
            tableResult = new JScrollPane(tb);
            

            // this.add(p, "North");
            this.getContentPane().add(tableResult, "North");
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // this.setSize(500, 500);
            this.pack();
            this.setVisible(true);
            this.setLocation(500, 100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // loading function
    public void loadData(String nameGet) { // 
        try {
            vData.clear();
            vTitle.clear();
            String sql = "select * from managers where name = ?";
            PreparedStatement selectAll = conn.prepareStatement(sql);
            selectAll.setString(1, nameGet);
            ResultSet rst = selectAll.executeQuery();
            ResultSetMetaData rsm = rst.getMetaData();
            int num_column = rsm.getColumnCount();
            for(int i = 1; i<=num_column; i++)
            {
                vTitle.add(rsm.getColumnLabel(i));
            }
            while(rst.next())
            {
                Vector row = new Vector();
                for(int i = 1; i<=num_column; i++)
                {
                    row.add(rst.getString(i));
                }
                vData.add(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
    }
}
