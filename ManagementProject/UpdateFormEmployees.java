package ManagementProject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.*;

// Update form for employee
public class UpdateFormEmployees extends JFrame implements ActionListener, ItemListener {
    
    Connection conn;
    // 
    JLabel Idlb;
    JTextField Id;
    JLabel namelb;
    JTextField name;
    JLabel addresslb;
    JComboBox address;
    JLabel salarylb;
    JTextField salary; 
    JLabel joblb;
    JComboBox job;
    JLabel genderlb;
    JRadioButton malebtn, femalebtn;
    JPanel genform;
    ButtonGroup group;
    JLabel birthdaylb;
    JDateChooser birthday;
    JButton ok;
    JButton cancel;
    // temp
    String gender;
    String date;
    String sql = "";
    Employee employee;
    Date dates;
    public UpdateFormEmployees(String s, Employee em, String id, String na, String add, String sala, String sub, String gen, String birth) {
        super(s);
        employee = em;
        Container cont = this.getContentPane();
        cont.setLayout(new GridLayout(8,2));
        Idlb = new JLabel("ID"); // ID row
        Id = new JTextField(id);
        if(s.equals("Edit Form for employees")) Id.setEditable(false);  
        cont.add(Idlb);
        cont.add(Id);
        namelb = new JLabel("Name"); // Name row
        name = new JTextField(na);
        if(s.equals("Edit Form for employees")) name.setEditable(false);  
        cont.add(namelb);
        cont.add(name);

        addresslb = new JLabel("Address"); // Address row
        
        // combo box
        String[] country = {"Canada", "America", "Viet Nam", "China", "England", "Japan", "South Korea"};
        address = new JComboBox(country);
        address.addItemListener(this);
        if(this.getTitle().equals("Edit Form for employees"))
        {
            add = employee.model.getValueAt(employee.selectRow, 2).toString();
            for(int i = 0; i < address.getItemCount(); i++)
            {
                if(address.getItemAt(i).toString().equalsIgnoreCase(add))
                {
                    address.setSelectedIndex(i);
                }
            }
        }
        cont.add(addresslb);
        cont.add(address);
        // salary label
        salarylb = new JLabel("Salary");
        salary = new JTextField(sala);
        cont.add(salarylb);
        cont.add(salary);
        // job
        joblb = new JLabel("Subject");
        String[] subject = {"AI Engineer", "Software Engineer", "Web developer", "Seller", "Architect", "Mobile Programmer"};
        job = new JComboBox(subject);
        // set selected item
        if(this.getTitle().equals("Edit Form for employees"))
        {
            sub = employee.model.getValueAt(employee.selectRow, 4).toString();
            for(int i = 0; i < job.getItemCount(); i++)
            {
                if(job.getItemAt(i).toString().equalsIgnoreCase(sub))
                {
                    job.setSelectedIndex(i);
                }
            }
        }
        cont.add(joblb);
        cont.add(job);
        // gender
        genderlb = new JLabel("Gender");
        malebtn = new JRadioButton("Male");
        femalebtn = new JRadioButton("Female");
        // set radio true when click edit
        if(this.getTitle().equals("Edit Form for employees"))
        {
            String tablegender = employee.model.getValueAt(employee.selectRow, 5).toString();
            if(tablegender.equals("Male"))
            {
                malebtn.setSelected(true);
            }
            else if(tablegender.equals("Female"))
            {
                femalebtn.setSelected(true);
            }
        }

        genform = new JPanel();
        group = new ButtonGroup();
        group.add(malebtn);
        group.add(femalebtn);
        genform.setLayout(new GridLayout(1,2));
        genform.add(malebtn);
        genform.add(femalebtn);
        cont.add(genderlb);
        cont.add(genform);
        // birthday
        birthdaylb = new JLabel("Birthdaty");
        birthday = new JDateChooser();
        if(this.getTitle().equals("Edit Form for employees"))
        {
            try {
                birth = employee.model.getValueAt(employee.selectRow, 6).toString();
                dates = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
                birthday.setDate(dates);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        
        cont.add(birthdaylb);
        cont.add(birthday);
        // button
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        cont.add(ok);
        cont.add(cancel);
        ok.setFocusable(false);
        cancel.setFocusable(false);
        ok.addActionListener(this);
        cancel.addActionListener(this);

        this.setSize(350, 350);
        this.setVisible(true);
    }
    // insertDB function
    public void insertData() {
        if(Id.getText().equals("") || name.getText().equals("") || salary.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "There is one area you not fill yet", "Alert",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-PNPO69N\\SQLEXPRESS;databaseName=managementProject;user=sa;password=12345");
                
                String id = Id.getText();
                String na = name.getText();
                String add = address.getSelectedItem().toString();
                Float salar = Float.valueOf(salary.getText());
                String sub = job.getSelectedItem().toString();
                if(malebtn.isSelected())
                {
                    gender = "Male";
                }
                else if(femalebtn.isSelected())
                {
                    gender = "Female";
                }
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                date = formatDate.format(birthday.getDate());
                
                if(this.getTitle().equals("Insert Form for employees"))
                {
                    sql = "insert into employees values(?,?,?,?,?,?,?)";
                    employee.insertCm = employee.conn.prepareStatement(sql);
                    employee.insertCm.setString(1, id);
                    employee.insertCm.setString(2, na);
                    employee.insertCm.setString(3, add);
                    employee.insertCm.setFloat(4, salar);
                    employee.insertCm.setString(5, sub);
                    employee.insertCm.setString(6, gender);
                    employee.insertCm.setString(7, date);
                    employee.insertCm.executeUpdate();
                }
                else if(this.getTitle().equals("Edit Form for employees"))
                {
                    sql = "update employees set name = ?, address = ?, salary = ?, job = ?, gender = ?, birthday = ? where id = ?";
                    employee.editCm = conn.prepareStatement(sql);
                    employee.editCm.setString(1, na);
                    employee.editCm.setString(2, add);
                    employee.editCm.setFloat(3, salar);
                    employee.editCm.setString(4, sub);
                    employee.editCm.setString(5, gender);
                    employee.editCm.setString(6, date);
                    employee.editCm.setString(7, id);
                    employee.editCm.executeUpdate();
                }
                // employees
                employee.loadData();
                System.out.println(na);
                employee.model.fireTableDataChanged();
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // System.out.println(date);
                System.out.println(sql);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("OK")) // return data 
        {
            insertData();
        }
        else this.dispose(); // 
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}