package ManagementProject;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Management extends JFrame implements ActionListener{

    // image
    JLabel imageLabel, mainLabel, choosingLabel;
    ImageIcon image;
    JButton emButton, maButton;
    JPanel panel, subpanel;
    public Management() {

        // label
        // title
        mainLabel = new JLabel("PERSONEL MANAGEMENT PROJECT");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setForeground(Color.RED);
        mainLabel.setFont(new Font(null, Font.BOLD, 25));

        // panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        subpanel = new JPanel();
        subpanel.setLayout(new GridLayout(2, 1, 10, 10));
        // image
        image = new ImageIcon(getClass().getResource("company.png"));
        Image dabImage = image.getImage();
        Image modiImage = dabImage.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(modiImage);
        imageLabel = new JLabel(image);
        // choosing
        choosingLabel = new JLabel("Choose to manage");
        choosingLabel.setFont(new Font(null, Font.CENTER_BASELINE, 20));
        choosingLabel.setHorizontalAlignment(JLabel.CENTER);
        // employee
        emButton = new JButton("Employee");
        emButton.setFocusable(false);
        emButton.addActionListener(this);
        // manager
        maButton = new JButton("Manager");
        maButton.setFocusable(false);
        maButton.addActionListener(this);

        // set Frame
        this.setLayout(new FlowLayout()); // layout
        this.setSize(550, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(500, 150);
        this.setResizable(false);

        // subpanel
        subpanel.add(maButton);
        subpanel.add(emButton);
        // panel adding
        panel.add(imageLabel);
        panel.add(choosingLabel);
        panel.add(subpanel);
        // add components
        this.add(mainLabel);
        this.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("Employee"))
        {
            new Employee();
        }
        else if(e.getActionCommand().equals("Manager"))
        {
            new Manager();
        }
    }

}


