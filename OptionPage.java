/* Project: Vehicle Vortex
*  Class: OptionPage.java
*  Author: Antonios Takos, Summer Snyder, Teuta Elezaj, Christian Felix, Tahir Buksh, Jayden Kuprel
*  Date: April 16th, 2023 
*  This program creates an option page, where users will be able to decide
*  if they would like to submit a car or a job.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

class OptionPage extends JFrame implements ActionListener {
   // Initializing Java Swing Variables
   private JPanel clientUser, buttonPanel;
   private JButton back;
   String[] options = { "", "Car", "Job" };
   private final JComboBox<String> optionBox = new JComboBox<String>(options);
   private JLabel aboutLabel;
   private User user;

   public void setUser(User user) {
      this.user = user;
   }

   // ---------------------------------------------------------------------------------
   // This method creates the GUI for the optionPage
   OptionPage(User user) {
      // Assigning JSwing variables values & styling
      setUser(user);

      // Welcome Label
      JLabel welcomeLabel = new JLabel("Welcome to Vehicle Vortex.", SwingConstants.CENTER);
      welcomeLabel.setForeground(Color.WHITE);
      welcomeLabel.setFont(new Font("Inter", Font.BOLD, 26));
      welcomeLabel.setBackground(new Color(217, 217, 217));
      welcomeLabel.setBounds(0, -75, 450, 200);

      // About Label
      aboutLabel = new JLabel("<html><center>Vehicle Vortex is an application where car users and job users are"
            + " able to connect in real time. Please select from the drop down menu:</center></html>");
      aboutLabel.setForeground(Color.WHITE);
      aboutLabel.setFont(new Font("Inter", Font.ITALIC, 12));
      aboutLabel.setBounds(0, 20, 450, 100);

      // Option Box
      optionBox.setBackground(new Color(217, 217, 217));
      optionBox.setForeground(new Color(86, 53, 158));
      optionBox.setFont(new Font("Inter", Font.BOLD, 15));
      optionBox.setBounds(180, 120, 50, 50);

      // Back Button
      back = new JButton("Back");
      back.setBackground(new Color(217, 217, 217));
      back.setForeground(new Color(86, 53, 158));
      back.setFont(new Font("Inter", Font.BOLD, 16));

      // Creating new panel
      clientUser = new JPanel(new GridLayout(3, 1));
      clientUser.setBackground(new Color(86, 53, 158));

      // Adding variables to the panel
      clientUser.add(welcomeLabel);
      clientUser.add(aboutLabel);
      clientUser.add(optionBox);
      // Setting layout as null so we can choose our bounds.
      clientUser.setLayout(null);
      add(clientUser, BorderLayout.CENTER);

      // Button Panel
      buttonPanel = new JPanel();
      buttonPanel.add(back);
      add(buttonPanel, BorderLayout.SOUTH);

      // Creating action listeners for the two buttons
      back.addActionListener(this);
      optionBox.addActionListener(this);

      // Setting title, size, and visibility
      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Welcome");
      setSize(450, 500);
      setVisible(true);
   } // <--- optionPage() constructor ends here

   // ---------------------------------------------------------------------------------
   // Action Listener method
   public void actionPerformed(ActionEvent e) {
      // Finding the source of the action and assigning it to an object variable
      Object obj = e.getSource();
      if (obj == optionBox) {
         // IF the "car" button was pressed, it will take them to the carPage
         if (optionBox.getSelectedItem().equals("Car")) {
            dispose();
            CarPage page = new CarPage();
            page.start(user);
         }
         // IF the "job" button was pressed, it will take them to the jobPage
         else if (optionBox.getSelectedItem().equals("Job")) {
            dispose();
            JobPage page = new JobPage();
            page.start(user);
         }
      } else if (obj == back) {
         dispose();
         Login.main(null);
      } else {
         System.out.println("error");
      }
      
   } // <--- actionPerformed() method ends here

} // <--- OptionPage{} class ends here