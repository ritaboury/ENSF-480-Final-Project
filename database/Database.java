package database;

import util.*;
import role.*;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection dbConnection;
    private ResultSet results;
    private final String URL = "jdbc:mysql://localhost:3306/skyward_bound"; 
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private ArrayList<RegisteredCustomer> registeredUsers = new ArrayList<RegisteredCustomer>();

/**
 * Constructs a new Database object and connects to the database specified
 * by the URL, username, and password instance variables.
 * @throws SQLException if there is an error connecting to the database
 */
    public Database() throws SQLException {
        this.dbConnection = connectToDatabase();
    }

/**
 * Establishes a connection to the database using the URL, username, and password instance variables.
 * @return a Connection object representing the connection to the database
 * @throws SQLException if there is an error connecting to the database
 */
    public Connection connectToDatabase() throws SQLException {
        try{
            dbConnection = DriverManager.getConnection(getURL(), getUsername(), getPassword());
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return dbConnection;
    }


// /**
//  * Reads Registered User data from the database and populates the registeredUsers ArrayList.
//  * @throws SQLException if there is an error reading data from the database
//  */
    public void readRegisteredUsers() throws SQLException {
        try {
            Statement stmt = dbConnection.createStatement();
            String query = "SELECT FName, LName, Email, Password FROM REGISTEREDUSERS";
            ResultSet results = stmt.executeQuery(query);
            
            while (results.next()) {
                String first = results.getString("FName");
                String last = results.getString("LName");
                String email = results.getString("Email");
                String password = results.getString("Password");

                Name name = new Name(first, last);
                RegisteredCustomer user = new RegisteredCustomer(name, email, password);
                registeredUsers.add(user);
            }
            stmt.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

// /**
//  * Reads treatments table from the database and populates the treatments ArrayList.
//  * @throws SQLException if there is an error executing the SQL statement
//  */
//     public void readTreatmentsFromDatabase() throws SQLException {
//     try {
//         Statement stmt = dbConnection.createStatement();
//         String query = "SELECT TreatmentID, AnimalID, TaskID, StartHour FROM treatments";
//         ResultSet results = stmt.executeQuery(query);

//         while (results.next()) {
//             String id = results.getString("TreatmentID");
//             String animalID = results.getString("AnimalID");
//             String taskID = results.getString("TaskID");
//             String startHour = results.getString("StartHour");
//             treatments.add(new Treatment(id, animalID, taskID, startHour));
//         }
//         stmt.close();
//     }
//     catch(SQLException e){
//         e.printStackTrace();
//     }
// }

// /**
//  * Reads tasks table from the database and populates the tasks ArrayList.
//  * @throws SQLException if there is an error executing the SQL statement
//  */
//     public void readTasksFromDatabase() throws SQLException {
//         try{
//             Statement stmt = dbConnection.createStatement();
//             String query = "SELECT TaskID, Description, Duration, MaxWindow FROM tasks";
//             ResultSet results = stmt.executeQuery(query);

//             while (results.next()) {
//                 String id = results.getString("TaskID");
//                 String description = results.getString("Description");
//                 int duration = results.getInt("Duration");
//                 int maxWindow = results.getInt("MaxWindow");
//                 tasks.add(new Task(id, description, duration, maxWindow));
//             }
//             stmt.close();
//         }
//         catch(SQLException e){
//             e.printStackTrace();
//         }
//     }

//     public void updateTreatmentStartHour(int treatmentId, int newStartHour) throws SQLException {
//         String query = "UPDATE treatments SET startHour = ? WHERE TreatmentID = ?";
//         PreparedStatement statement = dbConnection.prepareStatement(query);
//         statement.setInt(1, newStartHour);
//         statement.setInt(2, treatmentId);
//         int rowsAffected = statement.executeUpdate();
//         System.out.println(rowsAffected + " rows updated.");
//         statement.close();
//     }

    /**
     * Gets the URL of the database.
     * @return the URL of the database
     */
    public String getURL(){
        return this.URL;
    }

    /**
    * Gets the username used for authentication.
    * @return the username used for authentication
    */
    public String getUsername(){
        return this.USERNAME;
    }

    /**
     * Gets the password used for authentication.
     * @return the password used for authentication
     */
    public String getPassword(){
        return this.PASSWORD;
    }

//     /**
//      * Getter for ArrayList containing all the animal data from the database.
//      * @return an ArrayList containing all the animal data from the database
//      * @throws SQLException if there is an error executing the SQL statement
//      */
//     public ArrayList<Animal> getAnimalsArrayL() throws SQLException {
//         readAnimalsFromDatabase();
//         return this.animals;
//     }

//     /**
//     * Getter for ArrayList containing all the task data from the database.
//     * @return an ArrayList containing all the task data from the database
//     * @throws SQLException if there is an error executing the SQL statement
//     */
//     public ArrayList<Task> getTasksArrayL() throws SQLException {
//         readTasksFromDatabase();
//         return this.tasks;
//     }

//     /**
//      * Getter for ArrayList containing all the treatment data from the database.
//      * @return an ArrayList containing all the treatment data from the database
//      * @throws SQLException if there is an error executing the SQL statement
//      */
//     public ArrayList<Treatment> getTreatmentsArrayL() throws SQLException {
//         readTreatmentsFromDatabase();
//         return this.treatments;
//     }

//     /**
//     * Closes the database connection and result set.
//     */
//     public void close() {
//         //close the connection and result set
//         try{
//             dbConnection.close();
//             results.close();
//         }
//         //catch exception if database cannot be accessed or any other error
//         catch(SQLException e){
//             e.printStackTrace();
//         }
//     }
}
