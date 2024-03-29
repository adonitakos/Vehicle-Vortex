/*  Project: Vehicle Vortex
*   Class: User.java 
*   Author: Antonios Takos, Summer Snyder, Teuta Elezaj, Christian Felix, Tahir Buksh, Jayden Kuprel
*   Date: April 16th, 2023
*   This file contains the classes that will be used for the User.
*/

import java.util.*;

public class User {
    private int userID;
    private String userName;
    private String userPassword;
    private String userType;

    public String getType() {
        return userType;
    }

    public void setType(String type) {
        this.userType = type;
    }

    public User(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    private static Set<Integer> generatedIDs = new HashSet<>();

    public static int generateUniqueUserID() {
        Random random = new Random();
        int userID = random.nextInt(900000000) + 100000000;

        while (generatedIDs.contains(userID)) {
            userID = random.nextInt(900000000) + 100000000;
        }

        generatedIDs.add(userID);
        return userID;
    } // <--- generateUniqueUserID() method ends here

} // <--- User{} class ends here