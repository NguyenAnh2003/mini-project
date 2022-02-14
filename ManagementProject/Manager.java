package ManagementProject;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
public class Manager extends JFrame implements ActionListener, MouseListener{
    Connection conn;
    PreparedStatement insertCm;
    PreparedStatement editCm;
    PreparedStatement selectAllManager;
    ResultSet rst;
    Vector vData = new Vector();
    Vector vTitle = new Vector();
    JScrollPane tableResult;
    DefaultTableModel model;
    JTable tb = new JTable();
    JPanel p;
    String sql = "";
    JButton edit, delete, insert, search, cancel;
    int selectRow = 0;
    public Manager() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-PNPO69N\\SQLEXPRESS;databaseName=managementProject;user=sa;password=12345");
            insertCm = conn.prepareStatement(sql);
            editCm = conn.prepareStatement(sql);
            selectAllManager = conn.prepareStatement(sql);
            p = new JPanel();
            // Button
            edit = new JButton("Edit");
            edit.addActionListener(this);
            delete = new JButton("Delete");
            delete.addActionListener(this);
            insert = new JButton("Insert");
            insert.addActionListener(this);
            search = new JButton("Search");
            search.addActionListener(this);
            cancel = new JButton("Cancel");
            cancel.addActionListener(this);
            // 
            p.add(edit);
            p.add(delete);
            p.add(insert);
            p.add(search);
            p.add(cancel);
            // load data function
            loadData();
            model = new DefaultTableModel(vData, vTitle);
            tb = new JTable(model);
            tb.addMouseListener(this);
            tableResult = new JScrollPane(tb);

            // set frame
            this.setTitle("Manager");
            this.setSize(500, 500);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLocation(400, 100);
            this.setVisible(true);
            // add component
            this.getContentPane().add(tableResult, "North");
            this.add(p, "South");
            
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }

    }
    // loading function
    public void loadData() {
        try {
            vData.clear();
            vTitle.clear();
            String sql = "select * from managers";
            selectAllManager = conn.prepareStatement(sql);
            ResultSet rst = selectAllManager.executeQuery();
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
        }
    }
    // delete function
    public void deleteData() {
        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "The record will be deleted, Do you want to delete", "tut tut", JOptionPane.YES_NO_CANCEL_OPTION);
        System.out.println(showConfirmDialog);
        if(showConfirmDialog == JOptionPane.YES_OPTION)
        {
            try {
                Vector st = (Vector) vData.elementAt(selectRow);
                String sql = "DELETE FROM managers WHERE id = ? ";
                
                PreparedStatement deleStatement = conn.prepareStatement(sql);
                deleStatement.setString(1, (String) st.elementAt(0));
                deleStatement.executeUpdate();
                vData.remove(selectRow);
                model.fireTableDataChanged();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    // button
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Delete"))
        {
            deleteData();
        }
        else if(e.getActionCommand().equals("Edit"))
        {
            Vector st = (Vector) vData.elementAt(selectRow);
            new UpdateFormManager("Edit Form for manager",this, (String)st.elementAt(0),
                                                    (String)st.elementAt(1),
                                                    (String)st.elementAt(2),
                                                    (String)st.elementAt(3),
                                                    (String)st.elementAt(4),
                                                    (String)st.elementAt(5),
                                                    (String)st.elementAt(6));
        }
        else if(e.getActionCommand().equals("Insert"))
        {
            new UpdateFormManager("Insert Form for manager", this, "", "", "", "0", "", "", "");
        }
        else if(e.getActionCommand().equals("Search"))
        {
            new SearchManagers();
        }
        else 
        {
            this.dispose();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        selectRow = tb.getSelectedRow();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    public static void main(String[] args) {
        new Manager();
    }
}
