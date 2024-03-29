/* Project: Vehicle Vortex
*  Class: ClientHandler.java
*  Author: Antonios Takos, Summer Snyder, Teuta Elezaj, Christian Felix, Tahir Buksh, Jayden Kuprel
*  Date: April 16th, 2023 
*/

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    final DataInputStream inputStream;
    final DataOutputStream outputStream;
    final Socket socket;

    // Constructor
    public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    } // <--- ClientHandler() constructor ends here

    public void run() {
        try (ObjectInputStream OIS = new ObjectInputStream(inputStream)) {
            while (true) {
                try {
                    // Receive the answer from client
                    Object object = OIS.readObject();
                    if (object instanceof Job) {// Job type object being recieved
                        System.out.println("Job request recieved...");
                        Job job = (Job) object;
                        System.out.println("Object converted to job...");
                        CreateAdminForm jobForm = new CreateAdminForm(job, socket, inputStream, outputStream);
                        jobForm.setSize(400, 300);
                        jobForm.setVisible(true);

                    } else if (object instanceof Car) {// Car type object being recieved
                        System.out.println("Car request recieved...");
                        Car car = (Car) object;
                        System.out.println("Object converted to car...");
                        CreateAdminForm carForm = new CreateAdminForm(car, socket, inputStream, outputStream);
                        carForm.setSize(400, 300);
                        carForm.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } // <--- while(true) loop ends here
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // <--- run() method ends here

} // <--- ClientHandler{} class ends here