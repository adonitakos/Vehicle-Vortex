/* Project: Vehicle Vortex
* Class: JobPage.java
* Author: Antonios Takos, Summer Snyder, Teuta Elezaj, Christian Felix, Tahir Buksh, Jayden Kuprel
* Date: April 16th, 2023
* This program creates a job page, where users who have selected "Job" will
* be able to enter details and submit the job they would like completed.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

class Jobs extends JFrame implements ActionListener {
    // Initializing variables
    private JLabel welcome, infoLabel, jobIDLabel, jobDurationLabel, jobDeadlineLabel, jobDescriptionLabel;
    private final JTextField jobIDField, jobDurationField, jobDeadlineField, jobDescriptionField;
    private JButton submit, jobCompletion, back;
    private JPanel jobPage, welcomePanel, buttonPanel;
    private Socket socket;
    VCC vcc = VCC.getInstance();
    private User user;
    private int objectsPassed = 0;
    private Job pageJob;

    public void setUser(User user) {
        this.user = user;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    // ---------------------------------------------------------------------------------
    // This method creates the GUI for the JobWindow
    Jobs(User user) {
        // Assigning variables values
        // JobID
        setUser(user);

        // Welcome Label
        welcome = new JLabel("Welcome to the Job Page.", SwingConstants.CENTER);
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Inter", Font.BOLD, 26));

        // Info Label
        infoLabel = new JLabel("Please enter the following information, leaving no fields blank.",
                SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Inter", Font.PLAIN, 12));

        // Job ID Label
        jobIDLabel = new JLabel();
        jobIDLabel.setText("Job ID:");
        jobIDLabel.setForeground(Color.WHITE);

        // Job ID Field
        jobIDField = new JTextField(15);
        jobIDField.add(jobIDLabel);
        jobIDField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Job Duration Label
        jobDurationLabel = new JLabel();
        jobDurationLabel.setText("Job Duration (in hours):");
        jobDurationLabel.setForeground(Color.WHITE);

        // Job Duration Field
        jobDurationField = new JTextField(15);
        jobDurationField.add(jobDurationLabel);
        jobDurationField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Job Deadline Label
        jobDeadlineLabel = new JLabel();
        jobDeadlineLabel.setText("Job Deadline (mm/dd/yy):");
        jobDeadlineLabel.setForeground(Color.WHITE);

        // Job Deadline Field
        jobDeadlineField = new JTextField(15);
        jobDeadlineField.add(jobDeadlineLabel);
        jobDeadlineField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Job Description Label
        jobDescriptionLabel = new JLabel();
        jobDescriptionLabel.setText("Please describe the job:");
        jobDescriptionLabel.setForeground(Color.WHITE);

        // Job Description Field
        jobDescriptionField = new JTextField(15);
        jobDescriptionField.add(jobDescriptionLabel);
        jobDescriptionField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Job Completion Button
        jobCompletion = new JButton("Completion Time");
        jobCompletion.setBounds(110, 270, 100, 34);
        jobCompletion.setBackground(new Color(217, 217, 217));
        jobCompletion.setForeground(new Color(86, 53, 158));
        jobCompletion.setFont(new Font("Inter", Font.BOLD, 16));

        // Submit Button
        submit = new JButton("Submit");
        submit.setBounds(110, 270, 100, 34);
        submit.setBackground(new Color(217, 217, 217));
        submit.setForeground(new Color(86, 53, 158));
        submit.setFont(new Font("Inter", Font.BOLD, 16));

        // Back Button
        back = new JButton("Back");
        back.setBounds(110, 270, 100, 34);
        back.setBackground(new Color(217, 217, 217));
        back.setForeground(new Color(86, 53, 158));
        back.setFont(new Font("Inter", Font.BOLD, 16));

        // Creating welcome panel
        welcomePanel = new JPanel(new GridLayout(2, 1));
        welcomePanel.setBackground(new Color(86, 53, 158));
        // Adding variables to panel
        welcomePanel.add(welcome);
        welcomePanel.add(infoLabel);
        add(welcomePanel, BorderLayout.NORTH);

        // Creating a main panel
        jobPage = new JPanel(new GridLayout(8, 1));
        jobPage.setBackground(new Color(86, 53, 158));

        // Adding variables to the panel
        jobPage.add(jobIDLabel);
        jobPage.add(jobIDField);
        jobPage.add(jobDurationLabel);
        jobPage.add(jobDurationField);
        jobPage.add(jobDeadlineLabel);
        jobPage.add(jobDeadlineField);
        jobPage.add(jobDescriptionLabel);
        jobPage.add(jobDescriptionField);

        add(jobPage, BorderLayout.CENTER);

        // Creating button panel
        buttonPanel = new JPanel();
        // Adding buttons to the panel
        buttonPanel.add(submit);
        buttonPanel.add(jobCompletion);
        buttonPanel.add(back);
        add(buttonPanel, BorderLayout.SOUTH);

        // Creating action listener for the buttons
        submit.addActionListener(this);
        jobCompletion.addActionListener(this);
        back.addActionListener(this);

        // Setting title, size, and visibility
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Job Submission");
        setSize(450, 500);
        setVisible(true);
    } // <--- Jobs() constructor ends here

    // ---------------------------------------------------------------------------------
    // Action Listener method
    public void actionPerformed(ActionEvent e) {
        // Storing the ActionEvent as an Object
        Object obj = e.getSource();

        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            OutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            if (obj == submit) {
                // Assigning the information that will be inputted by the user as string
                // variables
                int jobID = Integer.parseInt(jobIDField.getText());
                int jobDuration = Integer.parseInt(jobDurationField.getText());
                String jobDeadline = jobDeadlineField.getText();
                String jobDescription = jobDescriptionField.getText();

                Job job = new Job(jobID, jobDuration, jobDeadline, jobDescription);
                job.setOwnerID(user.getUserID());
                try {

                    System.out.println("Sending job to VCC...");
                    if (objectsPassed == 0) {
                        ObjectOutputStream OOS = new ObjectOutputStream(outputStream);
                        objectsPassed += 1;
                        OOS.writeObject(job);
                    } else {
                        MyObjectOutputStream OOS = new MyObjectOutputStream(outputStream);
                        OOS.writeObject(job);
                    }
                    // server sends the message to client
                    if (inputStream.readBoolean()) {
                        vcc.addJob(job);
                        JobConfirmation form = new JobConfirmation(job);
                        pageJob = job;
                        form.setVisible(true);
                        form.setSize(800, 300);
                        System.out.println("Job submission has been approved by VCC. Writing to file...");
                        // Open pop-up Job Confirmation page
                    } else {
                        System.out.println("Job submission has been denied by VCC.");
                        RejectionPopUp form = new RejectionPopUp(job);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Clearing text fields once user submits to prepare for next input
                jobIDField.setText("");
                jobDurationField.setText("");
                jobDeadlineField.setText("");
                jobDescriptionField.setText("");

            } else if (obj == jobCompletion) {
                CompletionPopUp form = new CompletionPopUp(pageJob);                
            }

            else if (obj == back) {

                // if back button was clicked, reopen OptionPage
                OptionPage page = new OptionPage(user);
                // if back button was clicked, close current panel
                dispose();
            } else {
                System.out.println("Error.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } // <--- actionPerformed() method ends here

    } // <--- Jobs{} class ends here

} // <--- JobPage{} class ends here

class JobPage {
    public void start(User user) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.out.println("----------*** Attempting Job Owner Connection to Server ***--------");
                    Jobs form = new Jobs(user);
                    System.out.println(user.getUserID());
                    Socket socket = new Socket("localhost", 9806);
                    form.setSocket(socket);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
    } // <--- main() method ends here

    public JobPage() {
    }
    
} // <--- JobPage{} class ends here