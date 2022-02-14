package ManagementProject;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
public class Main extends JFrame implements ActionListener{
    Connection conn;
    PreparedStatement selectUser;
    ResultSet rs;
    JLabel usJLabel, passLabel;
    JTextField username;
    JPasswordField password;
    String sql = "";
    JButton signIn, cancel;
    public Main() {
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            Container cont = this.getContentPane();
            cont.setLayout(new GridLayout(3, 2));
            usJLabel = new JLabel("User name");
            // usJLabel.setVisible(false);
            username = new JTextField();
            username.setPreferredSize(new Dimension(100, 40));
            passLabel = new JLabel("Password");
            password = new JPasswordField();
            password.setPreferredSize(new Dimension(100, 40));
            signIn = new JButton("Sign In");
            signIn.addActionListener(this);
            cancel = new JButton("Cancel");
            cancel.addActionListener(this);
            cont.add(usJLabel);
            cont.add(username);
            cont.add(passLabel);
            cont.add(password);
            cont.add(signIn);
            cont.add(cancel);
            this.setSize(250, 150);
            this.setVisible(true);
            this.setLocation(500, 100);
            // this.pack();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }   
        
    }

    public static void main(String[] args) {
        new Main();
    }

    public void checkLogin() {
        String na = username.getText();
        String pass = password.getText();
        if(na.equals("") || pass.equals(""))
        {
            JOptionPane.showMessageDialog(null, "There is one area you not fill yet", "Alert",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            try {
                conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-PNPO69N\\SQLEXPRESS;databaseName=managementProject;user=sa;password=12345");
                sql = "select * from userAccess where username = ? and password = ?";
                selectUser = conn.prepareStatement(sql);
                selectUser.setString(1, na);
                selectUser.setString(2, pass);
                rs = selectUser.executeQuery();
                if(rs.next())
                {
                    System.out.println("Success");
                    this.dispose();
                    new Management();
                }
                else
                {
                    System.out.println("Wrong username or password");
                }
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println(e.getMessage());
                System.out.println(sql);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("Sign In"))
        {
            checkLogin();
        }
        else
        {
            System.exit(0);
        }
    }
}
