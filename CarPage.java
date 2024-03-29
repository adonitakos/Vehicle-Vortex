/*  Project: Vehicle Vortex
*   Class: CarPage.java 
*   Author: Antonios Takos, Summer Snyder, Teuta Elezaj, Christian Felix, Tahir Buksh, Jayden Kuprel
*   Date: April 16th, 2023
*   This program creates the car page, where users who have selected "Car"
*   will be able to enter details and submit their car for use.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

class Cars extends JFrame implements ActionListener {

        // Intializing variables
        private JPanel carPage, welcomePanel, buttonPanel;
        private JLabel welcome, carIDLabel, infoLabel, carMakeLabel, carModelLabel, carLicensePlateLabel,
                        carResidencyTimeLabel;
        private final JTextField carIDField, carMakeField, carModelField, carLicensePlateField, carResidencyTimeField;
        JButton submit, back;
        private Socket socket;
        private User user;
        VCC vcc = VCC.getInstance();
        private int objectsPassed = 0;

        public void setSocket(Socket socket) {
                this.socket = socket;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        // ---------------------------------------------------------------------------------
        // This method creates the GUI for the Cars Window
        Cars(User user) {
                // Assigning variables values
                setUser(user);

                // Welcome Label
                welcome = new JLabel("Welcome to the Car Page.", SwingConstants.CENTER);
                // Sets the Welcome string to White text
                welcome.setForeground(Color.WHITE);
                welcome.setFont(new Font("Inter", Font.BOLD, 26));

                // Info Label
                infoLabel = new JLabel("Please enter the following information, leaving no fields blank.",
                                SwingConstants.CENTER);
                infoLabel.setForeground(Color.WHITE);
                infoLabel.setFont(new Font("Inter", Font.PLAIN, 12));

                // Car ID Label
                carIDLabel = new JLabel();
                carIDLabel.setText("Car ID:");
                carIDLabel.setForeground(Color.WHITE);

                // Car ID Field
                carIDField = new JTextField(15);
                carIDField.add(carIDLabel);
                carIDField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                // Car License Plate Label
                carLicensePlateLabel = new JLabel();
                carLicensePlateLabel.setText("License Plate:");
                carLicensePlateLabel.setForeground(Color.WHITE);

                // Car License Plate Field
                carLicensePlateField = new JTextField(15);
                carLicensePlateField.add(carLicensePlateLabel);
                carLicensePlateField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                // Car Make Label
                carMakeLabel = new JLabel();
                carMakeLabel.setText("Make:");
                carMakeLabel.setForeground(Color.WHITE);

                // Car Make Field
                carMakeField = new JTextField(15);
                carMakeField.add(carMakeLabel);
                carMakeField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                // Car Model Label
                carModelLabel = new JLabel();
                carModelLabel.setText("Model:");
                carModelLabel.setForeground(Color.WHITE);

                // Car Model Field
                carModelField = new JTextField(15);
                carModelField.add(carModelLabel);
                carModelField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                // Car Residency Time Label
                carResidencyTimeLabel = new JLabel();
                carResidencyTimeLabel.setText("Residency Time (in hours):");
                carResidencyTimeLabel.setForeground(Color.WHITE);

                // Car Residency Time Field
                carResidencyTimeField = new JTextField(15);
                carResidencyTimeField.add(carResidencyTimeLabel);
                carResidencyTimeField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(86, 53, 158)),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                // Submit Button
                submit = new JButton("Submit");
                submit.setBounds(110, 310, 100, 34);
                submit.setBackground(new Color(217, 217, 217));
                submit.setForeground(new Color(86, 53, 158));
                submit.setFont(new Font("Inter", Font.BOLD, 16));

                // Back Button
                back = new JButton("Back");
                back.setBounds(110, 310, 100, 34);
                back.setBackground(new Color(217, 217, 217));
                back.setForeground(new Color(86, 53, 158));
                back.setFont(new Font("Inter", Font.BOLD, 16));

                // Creating welcome panel
                welcomePanel = new JPanel(new GridLayout(2, 1));
                welcomePanel.setBackground(new Color(86, 53, 158));
                // Adding variables to the welcome panel
                welcomePanel.add(welcome);
                welcomePanel.add(infoLabel);
                add(welcomePanel, BorderLayout.NORTH);

                // Creating main panel
                carPage = new JPanel(new GridLayout(10, 1));
                carPage.setBackground(new Color(86, 53, 158));

                // Adding variables to the main panel
                carPage.add(carIDLabel);
                carPage.add(carIDField);
                carPage.add(carLicensePlateLabel);
                carPage.add(carLicensePlateField);
                carPage.add(carMakeLabel);
                carPage.add(carMakeField);
                carPage.add(carModelLabel);
                carPage.add(carModelField);
                carPage.add(carResidencyTimeLabel);
                carPage.add(carResidencyTimeField);
                carPage.add(submit);
                carPage.add(back);

                add(carPage, BorderLayout.CENTER);

                // Creating Button Panel
                buttonPanel = new JPanel();
                // Adding buttons to the panel
                buttonPanel.add(submit);
                buttonPanel.add(back);
                add(buttonPanel, BorderLayout.SOUTH);

                // creating action listener for the buttons
                submit.addActionListener(this);
                back.addActionListener(this);

                // Setting title, size, and visibility
                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("Car Submission");
                setSize(450, 500);
                setVisible(true);
        } // <--- Cars() constructor ends here

        // ---------------------------------------------------------------------------------
        // Creating action listener method
        public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();

                try {
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        OutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                        if (obj == submit) {
                                // Store user input as string variables
                                int carID = Integer.parseInt(carIDField.getText());
                                String carMake = carMakeField.getText();
                                String carModel = carModelField.getText();
                                String carLicensePlate = carLicensePlateField.getText();
                                String carResidencyTime = carResidencyTimeField.getText();

                                Car car = new Car(carID, carLicensePlate, user.getUserID(), carMake, carModel,
                                                carResidencyTime);

                                try {
                                        System.out.println("Sending car to VCC...");

                                        if (objectsPassed == 0) {
                                                ObjectOutputStream OOS = new ObjectOutputStream(outputStream);
                                                objectsPassed += 1;
                                                OOS.writeObject(car);
                                        } else {
                                                MyObjectOutputStream OOS = new MyObjectOutputStream(outputStream);
                                                OOS.writeObject(car);
                                        }
                                        if (inputStream.readBoolean()) {
                                                vcc.addCar(car, user);
                                                CarConfirmation form = new CarConfirmation(car);
                                                form.setVisible(true);
                                                form.setSize(800, 300);
                                                System.out.println(
                                                                "Car submission has been approved by VCC. Writing to file...");

                                        } else {
                                                System.out.println("Car submission has been denied by VCC.");
                                                RejectionPopUp form = new RejectionPopUp(car);
                                        }

                                } catch (Exception ex) {

                                }
                                // Clearing text fields once user submits to prepare for next input
                                carIDField.setText("");
                                carMakeField.setText("");
                                carModelField.setText("");
                                carLicensePlateField.setText("");
                                carResidencyTimeField.setText("");

                        } else if (obj == back) {
                                // if back button was clicked, reopen OptionPage
                                OptionPage page = new OptionPage(user);
                                page.setVisible(true);
                                // if back button was clicked, close CarPage
                                dispose();
                        } else {
                                System.out.println("Error.");
                        }
                } catch (Exception ex) {

                }
        } // <--- actionPerformed() method ends here
} // <--- Cars{} class ends here

class CarPage {
        public void start(User user) {
                try {
                        Cars form = new Cars(user);
                        System.out.println("----------*** Attempting Car Owner Connection to Server ***--------");
                        Socket socket = new Socket("localhost", 9806);
                        form.setSocket(socket);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                }
        } // <--- main() method ends here
} // <--- CarPage{} class ends here
