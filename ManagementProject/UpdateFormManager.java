package ManagementProject;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
public class UpdateFormManager extends JFrame implements ActionListener, ItemListener{
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
    String dateMa;
    String sql = "";
    Manager manager;
    java.util.Date dates;

    public UpdateFormManager(String s, Manager ma,String id, String naMa, String add,String sala, String sub, String gen, String birth) {
        super(s);
        manager = ma;
        Container cont = this.getContentPane();
        cont.setLayout(new GridLayout(8,2));
        Idlb = new JLabel("ID"); // ID row
        Id = new JTextField(id);
        if(s.equals("Edit Form for manager")) Id.setEditable(false);  
        cont.add(Idlb);
        cont.add(Id);
        namelb = new JLabel("Name"); // Name row
        name = new JTextField(naMa);
        if(s.equals("Edit Form for manager")) name.setEditable(false);  
        cont.add(namelb);
        cont.add(name);

        addresslb = new JLabel("Address"); // Address row
        
        // combo box
        String[] country = {"Canada", "America", "Viet Nam", "China", "England", "Japan", "South Korea"};
        address = new JComboBox(country);
        if(s.equals("Edit Form for manager")){
            add = manager.model.getValueAt(manager.selectRow, 2).toString();
            for(int i = 0; i<address.getItemCount(); i++)
            {
                if(address.getItemAt(i).toString().equalsIgnoreCase(add))
                {
                    address.setSelectedIndex(i);
                }
            }
        }
        address.addItemListener(this);
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
        if(s.equals("Edit Form for manager")) {
            sub = manager.model.getValueAt(manager.selectRow, 4).toString();
            for(int i = 0; i<job.getItemCount(); i++)
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
        if(s.equals("Edit Form for manager")) {
            gen = manager.model.getValueAt(manager.selectRow, 5).toString();
            if(gen.equals("Male"))
            {
                malebtn.setSelected(true);
            }
            else if(gen.equals("Female"))
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
        birthdaylb = new JLabel("Birthdate");
        birthday = new JDateChooser();
        if(s.equals("Edit Form for manager")) {
            try {
                String temp = manager.model.getValueAt(manager.selectRow, 6).toString();
                dates = new SimpleDateFormat("yyyy-MM-dd").parse(temp);
                birthday.setDate(dates);
                System.out.println(dates);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
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
                String naMa = name.getText();
                String add = address.getSelectedItem().toString();
                Float sala = Float.valueOf(salary.getText());
                String sub = job.getSelectedItem().toString();
                if(malebtn.isSelected())
                {
                    gender = "Male";
                }
                else if(femalebtn.isSelected())
                {
                    gender = "Female";
                }
                SimpleDateFormat formatDates = new SimpleDateFormat("yyyy-MM-dd");
                dateMa = formatDates.format(birthday.getDate());
                
                if(this.getTitle().equals("Insert Form for manager"))
                {
                    sql = "insert into managers values(?,?,?,?,?,?,?)";
                    manager.insertCm = manager.conn.prepareStatement(sql);
                    manager.insertCm.setString(1, id);
                    manager.insertCm.setString(2, naMa);
                    manager.insertCm.setString(3, add);
                    manager.insertCm.setFloat(4, sala);
                    manager.insertCm.setString(5, sub);
                    manager.insertCm.setString(6, gender);
                    manager.insertCm.setString(7, dateMa);
                    manager.insertCm.executeUpdate();
                }
                else if(this.getTitle().equals("Edit Form for manager"))
                {
                    sql = "update managers set name = ?, address = ?, salary = ?, job = ?, gender = ?, birthday = ? where id = ?";
                    manager.editCm = conn.prepareStatement(sql);
                    manager.editCm.setString(1, naMa);
                    manager.editCm.setString(2, add);
                    manager.editCm.setFloat(3, sala);
                    manager.editCm.setString(4, sub);
                    manager.editCm.setString(5, gender);
                    manager.editCm.setString(6, dateMa);
                    manager.editCm.setString(7, id);
                    manager.editCm.executeUpdate();
                }
                // loading data
                manager.loadData();
                manager.model.fireTableDataChanged();
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
