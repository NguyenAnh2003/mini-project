package ManagementProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchManagers extends JFrame implements ActionListener{

    String sql = "";
    Connection conn;
    PreparedStatement searchName;
    ResultSet rst;
    ResultSetMetaData rmt;
    JLabel nameSearch;
    JTextField nameTextField;
    JButton search;
    
    JPanel p;
    public SearchManagers() {
        
        try {
                
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-PNPO69N\\SQLEXPRESS;databaseName=managementProject;user=sa;password=12345");

            
            p = new JPanel();
            
            nameSearch = new JLabel("Enter name to search");
            nameTextField = new JTextField();
            nameTextField.setPreferredSize(new Dimension(200, 30));
            search = new JButton("Search");
            search.addActionListener(this);
            p.add(nameSearch);
            p.add(nameTextField);
            p.add(search);  
            
            // loadData();
            

            this.add(p, "North");
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // this.setSize(500, 500);
            this.pack();
            this.setVisible(true);
            this.setLocation(500, 100);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void Search() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Search")) {
            String name = nameTextField.getText();
            System.out.println(name);
            new SearchedMana(name, this);
        }   
    }
    public static void main(String[] args) {
        new SearchManagers();
    }
    
}
